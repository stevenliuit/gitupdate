/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: MallIndexPageServiceImpl.java project: jcloud-b2c-mall-service
 * @creator: wangxin17
 * @date: 2017/2/16
 */
package com.jcloud.b2c.mall.service.service.impl;

import com.jcloud.b2c.common.common.util.PriceUtils;
import com.jcloud.b2c.mall.service.client.enums.*;
import com.jcloud.b2c.mall.service.domain.*;
import com.jcloud.b2c.mall.service.service.*;
import com.jcloud.b2c.mall.service.service.feign.CacheFeignClient;
import com.jcloud.b2c.mall.service.service.feign.OssClient;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.CharEncoding;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * @description: 首页静态化服务
 * @author: wangxin17
 * @requireNo:
 * @createdate: 2017-02-16 11:19
 * @lastdate:
 */
@Service("mallIndexPageService")
public class MallIndexPageServiceImpl implements MallIndexPageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MallIndexPageServiceImpl.class);

    private static Integer EXPIRE_SECOND = 0;
    private static Long PC_INDEX_DEFAULT_CHANNEL = 0L;


    @Value("${static.vm.path}")
    private String staticVmPath;

    @Value("${mall.web.domain}")
    private String mallWebDomain;

    @Value("${static.web.domain}")
    private String staticWebDomain;
    @Value("${static.version}")
    private String staticVersion;

    @Resource
    private MallFloorService mallFloorService;

    @Resource
    private MallChannelService mallChannelService;

    @Resource
    private MallCategoryService mallCategoryService;

    @Resource
    private MallBannerService mallBannerService;

    @Autowired
    private MallTopnewsArticleService mallTopnewsArticleService;

    @Resource
    private MallAdService mallAdService;

    @Resource
    private MallHotWordsService mallHotWordsService;

    @Resource
    private CacheFeignClient cacheFeignClient;

    @Resource
    private MallAdRecommendService mallAdRecommendService;
    @Autowired
    private MallAdFloorService mallAdFloorService;


    @Resource
    private OssClient ossClient;

    static String ENCODING = "UTF-8";
    static VelocityEngine ve = null;

    static Template indexPageTmpl;
    static Template commonHeaderTmpl;
    static Template commonFooterTmpl;
    static Template commonFragmentTmpl;
    static Template channelIndexPageTmpl;
    static Template personOperationToolbarTmpl;
    static Template simpleHeaderTmpl;
    static Template h5IndexPageTmpl;

    private static void init() {
        ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        ve.setProperty(Velocity.ENCODING_DEFAULT, ENCODING);
        ve.setProperty(Velocity.INPUT_ENCODING, ENCODING);
        ve.setProperty(Velocity.OUTPUT_ENCODING, ENCODING);
        ve.init();

        indexPageTmpl = ve.getTemplate("/templates/MallIndexPage.vm");
        commonHeaderTmpl = ve.getTemplate("/templates/commonHeader.vm");
        commonFooterTmpl = ve.getTemplate("/templates/commonFooter.vm");
        commonFragmentTmpl = ve.getTemplate("/templates/commonHeaderFragment.vm");
        channelIndexPageTmpl = ve.getTemplate("/templates/channelIndexPage.vm");
        personOperationToolbarTmpl = ve.getTemplate("/templates/payHeader.vm");
        simpleHeaderTmpl = ve.getTemplate("/templates/simpleHeader.vm");
        h5IndexPageTmpl = ve.getTemplate("/templates/h5/index.vm");
    }

    static {
        init();
    }

    private VelocityContext createVelocityContext(){
        VelocityContext ctx = new VelocityContext();
        ctx.put("mallWebDomain", mallWebDomain);
        ctx.put("staticWebDomain", staticWebDomain);
        ctx.put("staticVersion", staticVersion);
        ctx.put("createTime", new Date());
        return ctx;
    }
    private void writeToDiskAndCache(Long tenantId, String fileName, String html, String key) {
        try {
            LOGGER.info("create index page, tenantId={}, fileName={}, key={}", tenantId, fileName, key);
            cacheFeignClient.saveBytes2Cache(tenantId, getIndexPageCacheKey(tenantId, key), html.getBytes(ENCODING), EXPIRE_SECOND);
            try {
                ossClient.uploadFileBySourceFileNameInternal(tenantId, html.getBytes(ENCODING), fileName);
            } catch (Exception e) {
                LOGGER.info("create index page error, tenantId={}, fileName={}, key={}, e={}", tenantId, fileName, key, e);
            }

            try {
                FileUtils.write(new File(staticVmPath), html, ENCODING);
            } catch (IOException e) {
                LOGGER.error("生成静态页失败, fileName=" + fileName, e);
            }
        } catch (Exception e) {
            LOGGER.error("create index page error, tenantId=" + tenantId + ", fileName=" + fileName + ", key=" + key, e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Boolean reloadTemplate() {
        init();
        return true;
    }

    @Override
    public Boolean createH5IndexPage(Long tenantId) {
        String html = this.viewH5IndexPage(tenantId);
        String fileName = "index_h5_" + tenantId + ".html";
        writeToDiskAndCache(tenantId, fileName, html, "h5");
        return true;
    }

    /**
     *
     * @param tenantId
     * @param reloadTemplate  是否重新加载模板，默认false
     * @return
     */
    @Override
    public Boolean createIndexPage(Long tenantId, Boolean reloadTemplate) {
        if(reloadTemplate){
            init();//是否重新加载模板
        }
        String html = this.viewIndexPage(tenantId);
        String fileName = "index_page_" + tenantId + ".html";

        try {
            writeToDiskAndCache(tenantId, fileName, html, ClientTypeEnum.PC.getName());

            this.createCommonHeader(tenantId);
            this.createCommonFooter(tenantId);
            this.createCommonHeaderFragment(tenantId);
            this.cachePayHeader(tenantId);
            this.createSimpleHeader(tenantId);
        } catch (Exception e) {
            LOGGER.error("create pc index or common footer or header error, tenantId=" + tenantId, e);
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public String viewH5IndexPage(Long tenantId) {
        VelocityContext ctx = createVelocityContext();
        StringWriter sw = new StringWriter();

        //轮播图
        MallBanner mallBanner = new MallBanner();
        mallBanner.setState(StateEnum.ON_SHELF.getValue());
        mallBanner.setTenantId(tenantId);
        mallBanner.setChannelId(PC_INDEX_DEFAULT_CHANNEL);
        mallBanner.setClientType(ClientTypeEnum.H5.getValue());
        mallBanner.setBannerType(BannerTypeEnum.BANNER.getValue());
        List<MallBanner> mallBanners = mallBannerService.query(mallBanner);
        if (CollectionUtils.isNotEmpty(mallBanners) && mallBanners.size() > 5) {
            mallBanners = mallBanners.subList(0, 5);
        }
        ctx.put("mallBanners", mallBanners);

        //广告位
        mallBanner.setBannerType(BannerTypeEnum.ADSENSE.getValue());
        List<MallBanner> appads = mallBannerService.query(mallBanner);
        if (CollectionUtils.isNotEmpty(appads) && appads.size() > 2) {
            appads = appads.subList(0, 2);
        }
        ctx.put("appads", appads);
        //快捷rukou
        mallBanner.setBannerType(BannerTypeEnum.SHORTCUT.getValue());
        List<MallBanner> shortCut = mallBannerService.query(mallBanner);
        if (CollectionUtils.isNotEmpty(shortCut) && shortCut.size() > 4) {
            shortCut = shortCut.subList(0, 4);
        }
        ctx.put("shortCut", shortCut);
        //智能头条
        MallTopnewsArticle mallTopnewsArticleVo = new MallTopnewsArticle();
        mallTopnewsArticleVo.setTenantId(tenantId);
        mallTopnewsArticleVo.setClientType(ClientTypeEnum.H5.getValue());
        mallTopnewsArticleVo.setState(StateEnum.ON_SHELF.getValue());
        List<MallTopnewsArticle> topnews = mallTopnewsArticleService.query(mallTopnewsArticleVo);

        if (CollectionUtils.isNotEmpty(topnews) && topnews.size() > 6) {
            topnews = topnews.subList(0, 6);
        }
        ctx.put("topnews", topnews);


        //楼层
        MallFloor mallFloor = new MallFloor();
        mallFloor.setState(StateEnum.ON_SHELF.getValue());
        mallFloor.setClientType(ClientTypeEnum.H5.getValue());
        mallFloor.setTenantId(tenantId);
        mallFloor.setChannelId(PC_INDEX_DEFAULT_CHANNEL);
        mallFloor.setFloorType(FloorTypeEnum.NORMAL.getValue());
        List<MallFloor> mallFloors = mallFloorService.query(mallFloor);
        ctx.put("mallFloors", mallFloors);
        //楼层关联的商品
        //楼层广告
        MallAd mallAd = new MallAd();
        mallAd.setState(StateEnum.ON_SHELF.getValue());
        mallAd.setTenantId(tenantId);
        mallAd.setClientType(ClientTypeEnum.H5.getValue());

        Map<Long, List<MallAdFloor>> floorAdsNormalMap = new HashMap();
        Map<Long, List<MallAd>> floorAdsRecommendMap = new HashMap();
        MallAdFloor mallAdFloor = new MallAdFloor();
        mallAdFloor.setMallAd(new MallAd());
        mallAdFloor.setMallFloor(new MallFloor());
        mallAdFloor.setTenantId(tenantId);
        mallAdFloor.setClientType(ClientTypeEnum.H5.getValue());
        mallAdFloor.getMallAd().setAdType(AdTypeEnum.NORMAL.getValue());
        mallAdFloor.getMallFloor().setFloorType(FloorTypeEnum.NORMAL.getValue());
        mallAdFloor.getMallFloor().setChannelId(ChannelFieldEnum.DEFAULT.getValue());
        for (MallFloor mf : mallFloors) {
            if (FloorTypeEnum.NEW_ITEM.getValue() == mf.getFloorType() || FloorTypeEnum.ACTIVE.getValue() == mf.getFloorType()) {
                continue;
            }

            mallAdFloor.setFloorId(mf.getId());
            List<MallAdFloor> specials = mallAdFloorService.queryBySpecial(mallAdFloor);
            if (CollectionUtils.isNotEmpty(specials) && specials.size() > 1) {
                specials = specials.subList(0, 1);
            }
            floorAdsNormalMap.put(mf.getId(), specials);

            mallAd.setAdType(AdTypeEnum.RECOMMEND.getValue());
            List<MallAd> floorAdsRecommend = mallAdService.query(mallAd, AdPrincipalTypeEnum.FLOOR.getCode(), mf.getId());
            if (CollectionUtils.isNotEmpty(floorAdsRecommend) && floorAdsRecommend.size() > 8) {
                floorAdsRecommend = floorAdsRecommend.subList(0, 8);
            }

            if(floorAdsRecommend!=null && floorAdsRecommend.size()>2 && floorAdsRecommend.size()%2==1){
                floorAdsRecommend.remove(floorAdsRecommend.size()-1);
            }
            floorAdsRecommendMap.put(mf.getId(), floorAdsRecommend);
        }
        ctx.put("floorAdsNormalMap", floorAdsNormalMap);
        ctx.put("floorAdsRecommendMap", floorAdsRecommendMap);

        //明星单品-推荐位

        MallAdRecommend mallAdRecommendVo = new MallAdRecommend();
        mallAdRecommendVo.setTenantId(tenantId);
        mallAdRecommendVo.setRecommendType(RecommendTypeEnum.RECOMMEND.getValue());
        mallAdRecommendVo.setClientType(ClientTypeEnum.H5.getValue());
        List<MallAdRecommend> starRcmd = mallAdRecommendService.queryWithMallAd(mallAdRecommendVo);

        if(starRcmd!=null && starRcmd.size()>2 && starRcmd.size()%2==1){
            starRcmd.remove(starRcmd.size()-1);
        }
        ctx.put("starRcmd", starRcmd);
        //新品上市-通栏推荐

        mallAdRecommendVo.setRecommendType(RecommendTypeEnum.FULLRECOMMEND.getValue());
        List<MallAdRecommend> fullRecommends = mallAdRecommendService.queryWithMallAd(mallAdRecommendVo);
        ctx.put("fullRecommends", fullRecommends);


        h5IndexPageTmpl.merge(ctx, sw);

        return sw.toString();
    }


    @Override
    public String viewIndexPage(Long tenantId) {
        VelocityContext ctx = createVelocityContext();
        ctx.put("escUtils", new StringEscapeUtils());
        ctx.put("stringUtils", new StringUtils());
        ctx.put("priceUtils", new PriceUtils());
        ctx.put("escapeTool", new org.apache.velocity.tools.generic.EscapeTool());
        StringWriter sw = new StringWriter();

        createHeaderCtx(ctx, tenantId);

        MallAd mallAd = new MallAd();
        mallAd.setState(StateEnum.ON_SHELF.getValue());
        mallAd.setTenantId(tenantId);
        mallAd.setClientType(ClientTypeEnum.PC.getValue());

        //类目
        MallCategory mallCategory = new MallCategory();
        mallCategory.setClientType(ClientTypeEnum.PC.getValue());
        mallCategory.setTenantId(tenantId);
        mallCategory.setChannelId(PC_INDEX_DEFAULT_CHANNEL);
        mallCategory.setState(StateEnum.ON_SHELF.getValue());
        List<MallCategory> mallCategories = mallCategoryService.query(mallCategory);
        List<Map<Long, List<MallCategory>>> categoryMaps = this.handleCategories(mallCategories);
        ctx.put("categoryMaps", categoryMaps);

        //类目广告
        if (CollectionUtils.isNotEmpty(categoryMaps)) {
            Map<Long, List<MallCategory>> level1Map = categoryMaps.get(0);
            if (level1Map != null && level1Map.size() > 0) {
                List<MallCategory> level1 = level1Map.get(0L);
                if (CollectionUtils.isNotEmpty(level1)) {
                    Map<Long, List<MallAd>> categoryAdMap = new LinkedHashMap();
                    for (MallCategory mc : level1) {
                        List<MallAd> mallAds = mallAdService.query(mallAd, AdPrincipalTypeEnum.CATEGORY.getCode(), mc.getId());
                        if (CollectionUtils.isNotEmpty(mallAds) && mallAds.size() > 11) {
                            mallAds = mallAds.subList(0, 11);
                        }
                        categoryAdMap.put(mc.getId(), mallAds);
                    }
                    ctx.put("categoryAdMap", categoryAdMap);
                }
            }
        }

        //轮播图
        MallBanner mallBanner = new MallBanner();
        mallBanner.setState(StateEnum.ON_SHELF.getValue());
        mallBanner.setTenantId(tenantId);
        mallBanner.setChannelId(PC_INDEX_DEFAULT_CHANNEL);
        mallBanner.setClientType(ClientTypeEnum.PC.getValue());
        mallBanner.setBannerType(BannerTypeEnum.BANNER.getValue());
        List<MallBanner> mallBanners = mallBannerService.query(mallBanner);
        if (CollectionUtils.isNotEmpty(mallBanners) && mallBanners.size() > 6) {
            mallBanners = mallBanners.subList(0, 6);
        }
        ctx.put("mallBanners", mallBanners);

        //轮播图下方广告
        mallBanner.setBannerType(BannerTypeEnum.ADSENSE.getValue());
        List<MallBanner> bannerAds = mallBannerService.query(mallBanner);
        if (CollectionUtils.isNotEmpty(bannerAds) && bannerAds.size() > 15) {
            bannerAds = bannerAds.subList(0, 15);
        }
        ctx.put("bannerAds", bannerAds);

        //楼层
        MallFloor mallFloor = new MallFloor();
        mallFloor.setState(StateEnum.ON_SHELF.getValue());
        mallFloor.setClientType(ClientTypeEnum.PC.getValue());
        mallFloor.setTenantId(tenantId);
        mallFloor.setChannelId(PC_INDEX_DEFAULT_CHANNEL);
        List<MallFloor> mallFloors = mallFloorService.query(mallFloor);
        if (CollectionUtils.isNotEmpty(mallFloors) && mallFloors.size() > 15) {
            mallFloors = mallFloors.subList(0, 15);
        }
        ctx.put("mallFloors", mallFloors);

        //楼层广告
        Map<Long, List<MallAd>> floorAdsNormalMap = new HashMap();
        Map<Long, List<MallAd>> floorAdsRecommendMap = new HashMap();
        for (MallFloor mf : mallFloors) {
            if (FloorTypeEnum.NEW_ITEM.getValue() == mf.getFloorType() || FloorTypeEnum.ACTIVE.getValue() == mf.getFloorType()) {
                continue;
            }
            mallAd.setAdType(AdTypeEnum.NORMAL.getValue());
            List<MallAd> floorAdsNormal = mallAdService.query(mallAd, AdPrincipalTypeEnum.FLOOR.getCode(), mf.getId());
            LOGGER.info("floor name:{}, ad size:{}", mf.getName(), floorAdsNormal.size());
            if (CollectionUtils.isNotEmpty(floorAdsNormal) && floorAdsNormal.size() > 9) {
                floorAdsNormal = floorAdsNormal.subList(0, 9);
            }
            floorAdsNormalMap.put(mf.getId(), floorAdsNormal);

            mallAd.setAdType(AdTypeEnum.RECOMMEND.getValue());
            List<MallAd> floorAdsRecommend = mallAdService.query(mallAd, AdPrincipalTypeEnum.FLOOR.getCode(), mf.getId());
            if (CollectionUtils.isNotEmpty(floorAdsRecommend) && floorAdsRecommend.size() > 8) {
                floorAdsRecommend = floorAdsRecommend.subList(0, 8);
            }
            floorAdsRecommendMap.put(mf.getId(), floorAdsRecommend);
        }
        ctx.put("floorAdsNormalMap", floorAdsNormalMap);
        ctx.put("floorAdsRecommendMap", floorAdsRecommendMap);
        indexPageTmpl.merge(ctx, sw);
        return sw.toString();
    }

    @Override
    public String getMIndexPage(Long tenantId) {
        String html = cacheFeignClient.get(tenantId, this.getIndexPageCacheKey(tenantId, ClientTypeEnum.H5.getName()));
        return html;
    }

    @Override
    public String getCommonHeader(Long tenantId) {
        String header = cacheFeignClient.get(tenantId, this.getCommonHeaderKey(tenantId));
        if (StringUtils.isBlank(header)) {
            header = createCommonHeader(tenantId);
        }
        return header;
    }

    @Override
    public String getCommonFooter(Long tenantId) {
        String footer = cacheFeignClient.get(tenantId, this.getCommonFooterKey(tenantId));
        if (StringUtils.isBlank(footer)) {
            footer = createCommonFooter(tenantId);
        }
        return footer;
    }

    @Override
    public String getCommonHeaderFragment(Long tenantId) {
        String header = cacheFeignClient.get(tenantId, this.getCommonHeaderFragmentKey(tenantId));
        if (StringUtils.isBlank(header)) {
            header = createCommonHeaderFragment(tenantId);
        }
        return header;
    }

    @Override
    public Boolean createChannelIndexPage(Long tenantId, String channelCode) {
        String html = this.viewChannelIndexPage(tenantId, channelCode);
        String fileName = "b2c_channel_index_page_" + tenantId + "_" + channelCode + ".html";
        String key = this.getChannelIndexPageKey(tenantId, channelCode);
        LOGGER.info("index channel page, tenantId={}, channelCode={}, key={}", tenantId, channelCode, key);
        try {
            cacheFeignClient.saveBytes2Cache(tenantId, key, html.getBytes(CharEncoding.UTF_8), EXPIRE_SECOND);
            try {
                ossClient.uploadFileBySourceFileNameInternal(tenantId, html.getBytes(ENCODING), fileName);
            } catch (UnsupportedEncodingException e) {
                LOGGER.info("create channel index page error, tenantId={}, fileName={}, key={}, e={}", tenantId, fileName, key, e);
            }
        } catch (Exception e) {
            LOGGER.info("create index page error, tenantId={}, fileName={}, key={}, e={}", tenantId, fileName, key, e);
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public String viewChannelIndexPage(Long tenantId, String channelCode) {
        MallChannel mallChannel = new MallChannel();
        mallChannel.setHomeCode(channelCode);
        mallChannel.setTenantId(tenantId);
        mallChannel.setClientType(ClientTypeEnum.PC.getValue());
        mallChannel.setState(StateEnum.ON_SHELF.getValue());
        List<MallChannel> mallChannels = mallChannelService.query(mallChannel);

        if (CollectionUtils.isEmpty(mallChannels)) {
            LOGGER.warn("未查询到该频道：tenantId={}, channelCode={}", tenantId, channelCode);
            return "";
        }
        Long channelId = mallChannels.get(0).getId();
        String header = this.getCommonHeader(tenantId);
        String footer = this.getCommonFooter(tenantId);
        String headerFragment = this.getCommonHeaderFragment(tenantId);

//        channelIndexPageTmpl = ve.getTemplate("/templates/channelIndexPage.vm");

        VelocityContext ctx = createVelocityContext();
        ctx.put("escUtils", new StringEscapeUtils());
        ctx.put("stringUtils", new StringUtils());
        ctx.put("priceUtils", new PriceUtils());
        ctx.put("escapeTool", new org.apache.velocity.tools.generic.EscapeTool());
        ctx.put("header", header);
        ctx.put("footer", footer);
        ctx.put("headerFragment", headerFragment);

        //类目
        MallCategory mallCategory = new MallCategory();
        mallCategory.setClientType(ClientTypeEnum.PC.getValue());
        mallCategory.setTenantId(tenantId);
        mallCategory.setChannelId(channelId);
        mallCategory.setState(StateEnum.ON_SHELF.getValue());
        List<MallCategory> mallCategories = mallCategoryService.query(mallCategory);
        List<Map<Long, List<MallCategory>>> categoryMaps = this.handleCategories(mallCategories);
        ctx.put("categoryMaps", categoryMaps);

        //轮播图
        MallBanner mallBanner = new MallBanner();
        mallBanner.setState(StateEnum.ON_SHELF.getValue());
        mallBanner.setTenantId(tenantId);
        mallBanner.setChannelId(channelId);
        mallBanner.setClientType(ClientTypeEnum.PC.getValue());
        mallBanner.setBannerType(BannerTypeEnum.BANNER.getValue());
        List<MallBanner> mallBanners = mallBannerService.query(mallBanner);
        if (CollectionUtils.isNotEmpty(mallBanners) && mallBanners.size() > 6) {
            mallBanners = mallBanners.subList(0, 6);
        }
        ctx.put("mallBanners", mallBanners);

        //轮播图下方广告
        mallBanner.setBannerType(BannerTypeEnum.ADSENSE.getValue());
        List<MallBanner> bannerAds = mallBannerService.query(mallBanner);
        if (CollectionUtils.isNotEmpty(bannerAds) && bannerAds.size() > 7) {
            bannerAds = bannerAds.subList(0, 7);
        }
        ctx.put("bannerAds", bannerAds);

        //楼层
        MallFloor mallFloor = new MallFloor();
        mallFloor.setState(StateEnum.ON_SHELF.getValue());
        mallFloor.setClientType(ClientTypeEnum.PC.getValue());
        mallFloor.setTenantId(tenantId);
        mallFloor.setChannelId(channelId);
        List<MallFloor> mallFloors = mallFloorService.query(mallFloor);
        if (CollectionUtils.isNotEmpty(mallFloors) && mallFloors.size() > 15) {
            mallFloors = mallFloors.subList(0, 15);
        }
        ctx.put("mallFloors", mallFloors);

        MallAd mallAd = new MallAd();
        mallAd.setState(StateEnum.ON_SHELF.getValue());
        mallAd.setTenantId(tenantId);
        mallAd.setClientType(ClientTypeEnum.PC.getValue());

        //楼层广告
        Map<Long, List<MallAd>> floorAdsNormalMap = new HashMap();
        Map<Long, List<MallAd>> floorAdsRecommendMap = new HashMap();
        for (MallFloor mf : mallFloors) {
            if (mf.getFloorType() == null || FloorTypeEnum.NEW_ITEM.getValue() == mf.getFloorType() || FloorTypeEnum.ACTIVE.getValue() == mf.getFloorType()) {
                continue;
            }
            mallAd.setAdType(AdTypeEnum.NORMAL.getValue());
            List<MallAd> floorAdsNormal = mallAdService.query(mallAd, AdPrincipalTypeEnum.FLOOR.getCode(), mf.getId());
            if (CollectionUtils.isNotEmpty(floorAdsNormal) && floorAdsNormal.size() > 8) {
                floorAdsNormal = floorAdsNormal.subList(0, 8);
            }
            floorAdsNormalMap.put(mf.getId(), floorAdsNormal);

            mallAd.setAdType(AdTypeEnum.RECOMMEND.getValue());
            List<MallAd> floorAdsRecommend = mallAdService.query(mallAd, AdPrincipalTypeEnum.FLOOR.getCode(), mf.getId());
            if (CollectionUtils.isNotEmpty(floorAdsRecommend) && floorAdsRecommend.size() > 9) {
                floorAdsRecommend = floorAdsRecommend.subList(0, 9);
            }
            floorAdsRecommendMap.put(mf.getId(), floorAdsRecommend);
        }
        ctx.put("floorAdsNormalMap", floorAdsNormalMap);
        ctx.put("floorAdsRecommendMap", floorAdsRecommendMap);

        StringWriter sw = new StringWriter();

        channelIndexPageTmpl.merge(ctx, sw);

        return sw.toString();
    }

    @Override
    public String getChannelIndexPage(Long tenantId, String channelCode) {
        String html = cacheFeignClient.get(tenantId, this.getChannelIndexPageKey(tenantId, channelCode));
        //TODO: 本地调试暂时写死
//        html = "";
        /*if (StringUtils.isBlank(html)) {//拿不到有可能是该频道从后台删除了，不再重新生成
            this.createChannelIndexPage(tenantId, channelCode);
            html = cacheFeignClient.get(tenantId, this.getChannelIndexPageKey(tenantId, channelCode));
        }*/
        return html;
    }

    private String createCommonHeaderFragment(Long tenantId) {
        VelocityContext ctx = createVelocityContext();
        ctx.put("escUtils", new StringEscapeUtils());
        ctx.put("stringUtils", new StringUtils());
        ctx.put("priceUtils", new PriceUtils());
        ctx.put("escapeTool", new org.apache.velocity.tools.generic.EscapeTool());
        StringWriter sw = new StringWriter();

        commonFragmentTmpl.merge(ctx, sw);

        String fragment = sw.toString();
        try {
            cacheFeignClient.saveBytes2Cache(tenantId, this.getCommonHeaderFragmentKey(tenantId), fragment.getBytes(ENCODING), EXPIRE_SECOND);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return fragment;
    }


    private String createCommonHeader(Long tenantId) {
        VelocityContext ctx = createVelocityContext();
        ctx.put("escUtils", new StringEscapeUtils());
        ctx.put("stringUtils", new StringUtils());
        ctx.put("priceUtils", new PriceUtils());
        ctx.put("escapeTool", new org.apache.velocity.tools.generic.EscapeTool());
        StringWriter sw = new StringWriter();

        this.createHeaderCtx(ctx, tenantId);

        commonHeaderTmpl.merge(ctx, sw);

        String header = sw.toString();
        try {
            cacheFeignClient.saveBytes2Cache(tenantId, this.getCommonHeaderKey(tenantId), header.getBytes(ENCODING), EXPIRE_SECOND);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return header;
    }

    private String createCommonFooter(Long tenantId) {
        VelocityContext ctx = createVelocityContext();
        ctx.put("escUtils", new StringEscapeUtils());
        ctx.put("stringUtils", new StringUtils());
        ctx.put("priceUtils", new PriceUtils());
        ctx.put("escapeTool", new org.apache.velocity.tools.generic.EscapeTool());
        StringWriter sw = new StringWriter();

        commonFooterTmpl.merge(ctx, sw);

        String footer = sw.toString();
        try {
            cacheFeignClient.saveBytes2Cache(tenantId, this.getCommonFooterKey(tenantId), footer.getBytes(ENCODING), EXPIRE_SECOND);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
        return footer;
    }

    private void createHeaderCtx(VelocityContext ctx, Long tenantId) {
        //吸顶广告
        MallBanner mallBanner = new MallBanner();
        mallBanner.setState(StateEnum.ON_SHELF.getValue());
        mallBanner.setTenantId(tenantId);
        mallBanner.setClientType(ClientTypeEnum.PC.getValue());
        mallBanner.setBannerType(BannerTypeEnum.CEILING_ADSENSE.getValue());
        List<MallBanner> ceilingAds = mallBannerService.query(mallBanner);
        if (CollectionUtils.isNotEmpty(ceilingAds)) {
            MallBanner ceilingAd = ceilingAds.get(0);
            ctx.put("ceilingAd", ceilingAd);
        }

        //顶部搜索热词
        MallHotWords mallHotWords = new MallHotWords();
        mallHotWords.setClientType(ClientTypeEnum.PC.getValue());
        mallHotWords.setState(StateEnum.ON_SHELF.getValue());
        mallHotWords.setTenantId(tenantId);
        mallHotWords.setWordsType(HotWordsTypeEnum.TOP.getValue());
        List<MallHotWords> topHotWords = mallHotWordsService.query(mallHotWords);
        if (CollectionUtils.isNotEmpty(topHotWords) && topHotWords.size() > 4) {
            topHotWords = topHotWords.subList(0, 4);
        }
        ctx.put("topHotWords", topHotWords);

        mallHotWords.setWordsType(HotWordsTypeEnum.DEFAULT_SEARCH.getValue());
        List<MallHotWords> defaultHotWords = mallHotWordsService.query(mallHotWords);
        if (CollectionUtils.isNotEmpty(defaultHotWords) && topHotWords.size() > 10) {
            defaultHotWords = defaultHotWords.subList(0, 10);
        }
        ctx.put("defaultHotWords", defaultHotWords);

        //查询频道
        MallChannel mallChannel = new MallChannel();
        mallChannel.setTenantId(tenantId);
        mallChannel.setClientType(ClientTypeEnum.PC.getValue());
        mallChannel.setState(StateEnum.ON_SHELF.getValue());

        List<MallChannel> mallChannels = mallChannelService.query(mallChannel);
        ctx.put("mallChannels", mallChannels);

        MallAd mallAd = new MallAd();
        mallAd.setState(StateEnum.ON_SHELF.getValue());
        mallAd.setTenantId(tenantId);
        mallAd.setClientType(ClientTypeEnum.PC.getValue());

        Map<Long, List<MallAd>> channelAdMap = new LinkedHashMap();
        //查询频道广告
        for (MallChannel mc : mallChannels) {
            List<MallAd> mallAds = mallAdService.query(mallAd, AdPrincipalTypeEnum.CHANNEL.getCode(), mc.getId());
            if (CollectionUtils.isNotEmpty(mallAds) && mallAds.size() > 6) {
                mallAds = mallAds.subList(0, 6);
            }
            channelAdMap.put(mc.getId(), mallAds);
        }
        ctx.put("channelAdMap", channelAdMap);
    }

    /**
     * 处理类目,将查询出来的所有类目进行等级划分
     *
     * @param categories
     * @return
     */
    private List<Map<Long, List<MallCategory>>> handleCategories(List<MallCategory> categories) {
        List<MallCategory> level1 = new ArrayList();

        List<Long> level1Ids = new ArrayList();
        Map<Long, List<MallCategory>> categoryMap = new LinkedHashMap();
        for (MallCategory category : categories) {
            if (category.getParentId() == 0) {
                level1.add(category);
                level1Ids.add(category.getId());
            } else {
                if (categoryMap.containsKey(category.getParentId())) {
                    categoryMap.get(category.getParentId()).add(category);
                } else {
                    List<MallCategory> msc = new ArrayList();
                    msc.add(category);
                    categoryMap.put(category.getParentId(), msc);
                }
            }
        }
        if (level1Ids.size() > 8) {
            level1Ids = level1Ids.subList(0, 8);
        }
        if (level1.size() > 8) {
            level1 = level1.subList(0, 8);
        }
        Map<Long, List<MallCategory>> level2Map = new LinkedHashMap();
        for (Long id : level1Ids) {
            List<MallCategory> mc = categoryMap.get(id);
            if (mc == null) {
                continue;
            }
            if (level2Map.containsKey(id)) {
                level2Map.get(id).addAll(mc);
            } else {
                List<MallCategory> mcs = new ArrayList();
                mcs.addAll(mc);
                level2Map.put(id, mcs);
            }
            categoryMap.remove(id);
        }

        /*Map<Long, List<MallCategory>> level3Map = new LinkedHashMap();
        Collection<List<MallCategory>> coll2 = level2Map.values();

        for (List<MallCategory> mcs : coll2) {
            for (MallCategory mc : mcs) {
                *//*if (level3Map.containsKey(mc.getId())) {
                    level3Map.get(mc.getId()).add(mc);
                } else {
                    List<MallCategory> temp = new ArrayList();
                    temp.add(mc);
                    level3Map.put(mc.getId(), temp);
                }*//*
                List<MallCategory> categoryList = categoryMap.get(mc.getId());
                if (level3Map.containsKey(mc.getId())) {
                    level3Map.get(mc.getId()).addAll(categoryList);
                } else {
                    List<MallCategory> temp = new ArrayList();
                    temp.addAll(categoryList);
                    level3Map.put(mc.getId(), temp);
                }
            }
        }*/
        List<Map<Long, List<MallCategory>>> result = new ArrayList();
        Map<Long, List<MallCategory>> level1Map = new HashedMap();
        level1Map.put(0L, level1);

        result.add(level1Map);
        result.add(level2Map);
        result.add(categoryMap);
        return result;
    }

    /**
     * 缓存个人操作工具条
     *
     * @return
     */
    @Override
    public Boolean cachePayHeader(Long tenantId) {
        StringWriter sw = new StringWriter();
        VelocityContext ctx = createVelocityContext();
        personOperationToolbarTmpl.merge(ctx, sw);

        try {
            cacheFeignClient.saveBytes2Cache(tenantId, getPayHeaderKey(tenantId), sw.toString().getBytes(CharEncoding.UTF_8), EXPIRE_SECOND);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
        return true;
    }

    public Boolean createSimpleHeader(Long tenantId) {
        StringWriter sw = new StringWriter();
        VelocityContext ctx = createVelocityContext();
        simpleHeaderTmpl.merge(ctx, sw);
        try {
            cacheFeignClient.saveBytes2Cache(tenantId, getSimpleHeaderKey(tenantId), sw.toString().getBytes(CharEncoding.UTF_8), EXPIRE_SECOND);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public String getSimpleHeader(Long tenantId) {
        String html = cacheFeignClient.get(tenantId, getSimpleHeaderKey(tenantId));
        if (StringUtils.isBlank(html)) {
            this.createSimpleHeader(tenantId);
            html = cacheFeignClient.get(tenantId, getSimpleHeaderKey(tenantId));
        }
        return html;
    }

    @Override
    public void deleteChannelIndexPageFromCache(Long tenantId, String channelCode) {
        String key = this.getChannelIndexPageKey(tenantId, channelCode);
        LOGGER.info("delete channel index page, tenantId={}, channelCode={}, key={}", tenantId, channelCode, key);
        cacheFeignClient.deleteObjKey(tenantId, key);
        LOGGER.info("delete channel index page success, tenantId={}, channelCode={}, key={}", tenantId, channelCode, key);
    }

    /**
     * 返回个人操作模板
     *
     * @param tenantId
     * @return
     */
    @Override
    public String getPayHeader(Long tenantId) {
        String html = cacheFeignClient.get(tenantId, getPayHeaderKey(tenantId));
        if (StringUtils.isBlank(html)) {
            this.cachePayHeader(tenantId);
            html = cacheFeignClient.get(tenantId,  getPayHeaderKey(tenantId));
        }
        return html;
    }

    @Override
    public String getIndexPage(Long tenantId) {
        String html = cacheFeignClient.get(tenantId, this.getIndexPageCacheKey(tenantId, ClientTypeEnum.PC.getName()));
        return html;
    }


    private String getCommonHeaderFragmentKey(Long tenantId) {
        return CommonHtmlCacheKeyEnum.COMMON_HEADER_FRAGMENT_KEY.getKey() + tenantId;
    }

    private String getCommonHeaderKey(Long tenantId) {
        return CommonHtmlCacheKeyEnum.COMMON_HEADER_KEY.getKey() + tenantId;
    }
    private String getPayHeaderKey(Long tenantId) {
        return CommonHtmlCacheKeyEnum.COMMON_PAY_HEADER_KEY.getKey()+ tenantId;
    }
    private String getSimpleHeaderKey(Long tenantId) {
        return CommonHtmlCacheKeyEnum.SIMPLE_HEADER_KEY.getKey()  + tenantId;
    }
    private String getCommonFooterKey(Long tenantId) {
        return CommonHtmlCacheKeyEnum.COMMON_FOOTER_KEY.getKey() + tenantId;
    }


    private String getChannelIndexPageKey(Long tenantId, String channelCode) {
        return CommonHtmlCacheKeyEnum.COMMON_CHANNEL_KEY.getKey() + tenantId + "_" + channelCode;
    }


    private String getIndexPageCacheKey(Long tenantId, String key) {
        if (ClientTypeEnum.PC.getName().equals(key)) {
            return CommonHtmlCacheKeyEnum.B2C_INDEX_PAGE_CACHE_KEY.getKey() + tenantId;
        }
        return CommonHtmlCacheKeyEnum.B2C_H5_INDEX_PAGE_CACHE_KEY.getKey() + tenantId;
    }

}
