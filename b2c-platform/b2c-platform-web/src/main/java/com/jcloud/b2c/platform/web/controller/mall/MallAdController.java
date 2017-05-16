/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: MallAdController.java project: b2c-platform
 * @creator: wangxin17
 * @date: 2017/2/25
 */
package com.jcloud.b2c.platform.web.controller.mall;

import com.jcloud.b2c.common.common.dictionary.ImageSizeEnum;
import com.jcloud.b2c.common.common.util.ImageUtils;
import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.common.common.web.security.ControllerContext;
import com.jcloud.b2c.item.client.vo.item.ItemBasicInfoVo;
import com.jcloud.b2c.item.client.vo.item.ItemDetailVo;
import com.jcloud.b2c.mall.service.client.enums.AdPrincipalTypeEnum;
import com.jcloud.b2c.mall.service.client.enums.AdTypeEnum;
import com.jcloud.b2c.mall.service.client.enums.ClientTypeEnum;
import com.jcloud.b2c.mall.service.client.enums.StateEnum;
import com.jcloud.b2c.mall.service.client.vo.*;
import com.jcloud.b2c.platform.service.feign.*;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @description: 设置广告
 * @author: wangxin17
 * @requireNo:
 * @createdate: 2017-02-25 10:36
 * @lastdate:
 */
@Controller
@RequestMapping("/mall/mallAd")
public class MallAdController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MallAdController.class);

    private static int AD_TYPE_CHANNEL = 1;
    private static int AD_TYPE_FLOOR = 2;
    private static int AD_TYPE_CATEGORY = 3;
    private static List<String> IMG_CONTENTY_TYPE = Arrays.asList(new String[]{"IMAGE/JPG", "IMAGE/JPEG", "IMAGE/GIF", "IMAGE/BMP", "IMAGE/X-PNG", "IMAGE/PNG", "IMAGE/WEBP"});
    @Resource
    private MallAdClient mallAdClient;

    @Resource
    private MallAdFloorClient mallAdFloorClient;

    @Resource
    private MallAdChannelClient mallAdChannelClient;

    @Resource
    private MallAdCategoryClient mallAdCategoryClient;

    @Resource
    private MallChannelClient mallChannelClient;

    @Resource
    private MallFloorClient mallFloorClient;

    @Resource
    private MallCategoryClient mallCategoryClient;

    @Resource
    private OssClient ossClient;

    @Resource
    private ItemClient itemClient;

    @Value("${mall.domain}")
    private String mallDomain;

    /**
     * 进入设置广告页
     * @param type 主体类型 1、频道  2、楼层
     * @param principalId  主体id
     * @return
     */
    @RequestMapping(value = "/toAdIndex" ,method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView toAdIndex(@RequestParam Integer type, @RequestParam Long principalId) {
        Long tenantId = ControllerContext.getTenantId();
        ModelAndView mv = new ModelAndView("pageManage/adManage");
        AdPrincipalTypeEnum principalTypeEnum = null;
        type = type == null ? 1 : type;
        int clientType = 0;

        LOGGER.info("go ad index, type={}, principalId={}, tenantId={}", type, principalId, tenantId);

        if (type == AD_TYPE_CHANNEL) {
            principalTypeEnum = AdPrincipalTypeEnum.CHANNEL;
            MallChannelVo mallChannelVo = mallChannelClient.get(principalId, tenantId).getData();
            clientType = mallChannelVo.getClientType();
        } else if (type == AD_TYPE_FLOOR){
            principalTypeEnum = AdPrincipalTypeEnum.FLOOR;
            MallFloorVo mallFloorVo = mallFloorClient.get(principalId, tenantId).getData();
            clientType = mallFloorVo.getClientType();
        } else if (type == AD_TYPE_CATEGORY) {
            principalTypeEnum = AdPrincipalTypeEnum.CATEGORY;
            MallCategoryVo mallCategoryVo = mallCategoryClient.get(principalId, tenantId).getData();
            clientType = mallCategoryVo.getClientType();
        }
        mv.addObject("type", type);
        mv.addObject("principalId", principalId);

        MallAdVo mallAdVo = new MallAdVo();
        mallAdVo.setTenantId(tenantId);
        mallAdVo.setClientType(clientType);
        mallAdVo.setState(StateEnum.ON_SHELF.getValue());
        mallAdVo.setAdType(AdTypeEnum.NORMAL.getValue());

        List<MallAdVo> mallAdVos = mallAdClient.query(mallAdVo, principalTypeEnum.getCode(), principalId).getData();
        mv.addObject("mallAdVos", mallAdVos);
        return mv;
    }

    @RequestMapping(value = "/toEdit" ,method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> toEdit(@RequestParam Integer type, Long rId) {
        Map<String, Object> result = new HashedMap();
        if (type == null) {
            /*result.put("success", -1);
            return result;*/
            type = AD_TYPE_FLOOR;
        }
        Long tenantId = ControllerContext.getTenantId();
        LOGGER.info("go ad index, type={}, rId={}, tenantId={}", type, rId, tenantId);
        MallAdVo mallAdVo = null;
        if (type == AD_TYPE_CHANNEL) {
            MallAdChannelVo mallAdChannelVo = mallAdChannelClient.getById(rId, tenantId).getData();
            mallAdVo = mallAdClient.get(mallAdChannelVo.getAdId(), tenantId).getData();
            mallAdVo.setSortNum(mallAdChannelVo.getSortNum());
        } else if (type == AD_TYPE_FLOOR) {
            MallAdFloorVo mallAdFloorVo = mallAdFloorClient.getById(rId, tenantId).getData();
            mallAdVo = mallAdClient.get(mallAdFloorVo.getAdId(), tenantId).getData();
            mallAdVo.setSortNum(mallAdFloorVo.getSortNum());
        } else if (type == AD_TYPE_CATEGORY) {
            MallAdCategoryVo mallAdCategoryVo = mallAdCategoryClient.getById(rId, tenantId).getData();
            mallAdVo = mallAdClient.get(mallAdCategoryVo.getAdId(), tenantId).getData();
            mallAdVo.setSortNum(mallAdCategoryVo.getSortNum());
        }
        result.put("success", 1);
        result.put("data", mallAdVo);
        return result;
    }

    /**
     * @description 功能描述: 更新或新增广告
     * @author wangxin17
     * @date
     * @param
     * @throws
     * @return
     * @requireNo: ${REQUIRENO}
     */
    @RequestMapping(value = "/add" ,method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> add(@RequestParam Integer type, @RequestParam Long principalId,
                                   @RequestParam String name, @RequestParam Integer sortNum,
                                   @RequestParam String subName1, @RequestParam String subName2,
                                   @RequestParam String imgSrc, @RequestParam String link) {
        Map<String, Object> result = new HashedMap();
        if (type == null) {
            result.put("success", -1);
            return result;
        }
        Long tenantId = ControllerContext.getTenantId();
        String adWords = name + ";";
        if (StringUtils.isNoneBlank(subName1)) {
            adWords += subName1;
        }
        if (StringUtils.isNoneBlank(subName2)) {
            adWords += ";" + subName2;
        }

        MallAdVo mallAdVo = new MallAdVo();
        mallAdVo.setState(StateEnum.ON_SHELF.getValue());
        mallAdVo.setAdType(AdTypeEnum.NORMAL.getValue());

        mallAdVo.setTenantId(tenantId);
        mallAdVo.setAdWords(adWords);
        mallAdVo.setImgSrc(imgSrc);
        mallAdVo.setLink(link);

        if (type == AD_TYPE_CHANNEL) {
            MallChannelVo mallChannelVo = mallChannelClient.get(principalId, tenantId).getData();
            mallAdVo.setClientType(mallChannelVo.getClientType());

            MallAdChannelVo mallAdChannelVo = new MallAdChannelVo();
            mallAdChannelVo.setSortNum(sortNum);
            mallAdChannelVo.setClientType(mallChannelVo.getClientType());
            mallAdChannelVo.setTenantId(tenantId);
            mallAdChannelVo.setChannelId(principalId);
            mallAdChannelVo.setMallAd(mallAdVo);

            LOGGER.info("add mallAd:{}", mallAdChannelVo);
            mallAdChannelClient.add(mallAdChannelVo);
        } else if (type == AD_TYPE_FLOOR) {
            MallFloorVo mallFloorVo = mallFloorClient.get(principalId, tenantId).getData();
            mallAdVo.setClientType(mallFloorVo.getClientType());

            MallAdFloorVo mallAdFloorVo = new MallAdFloorVo();
            mallAdFloorVo.setTenantId(tenantId);
            mallAdFloorVo.setFloorId(principalId);
            mallAdFloorVo.setClientType(mallFloorVo.getClientType());
            mallAdFloorVo.setSortNum(sortNum);

            mallAdFloorVo.setMallAd(mallAdVo);

            LOGGER.info("add mallAd:{}", mallAdFloorVo);
            mallAdFloorClient.add(mallAdFloorVo);
        } else if (type == AD_TYPE_CATEGORY) {
            MallCategoryVo mallCategoryVo = mallCategoryClient.get(principalId, tenantId).getData();
            mallAdVo.setClientType(mallCategoryVo.getClientType());

            MallAdCategoryVo mallAdCategoryVo = new MallAdCategoryVo();
            mallAdCategoryVo.setTenantId(tenantId);
            mallAdCategoryVo.setCategoryId(principalId);
            mallAdCategoryVo.setClientType(mallCategoryVo.getClientType());
            mallAdCategoryVo.setSortNum(sortNum);

            mallAdCategoryVo.setMallAd(mallAdVo);

            LOGGER.info("add mallAd:{}", mallAdCategoryVo);
            mallAdCategoryClient.add(mallAdCategoryVo);
        }
        result.put("success", 1);
        return result;
    }

    @RequestMapping(value = "/edit" ,method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> edit(@RequestParam Integer type, @RequestParam Long principalId, @RequestParam Long rId,
                                    @RequestParam String name, @RequestParam Integer sortNum,
                                    @RequestParam String subName1, @RequestParam String subName2,
                                    @RequestParam String imgSrc, @RequestParam String link) {
        Map<String, Object> result = new HashedMap();
        if (type == null) {
            result.put("success", -1);
            return result;
        }
        Long tenantId = ControllerContext.getTenantId();
        String adWords = name + ";";
        if (StringUtils.isNoneBlank(subName1)) {
            adWords += subName1;
        }
        if (StringUtils.isNoneBlank(subName2)) {
            adWords += ";" + subName2;
        }

        MallAdVo mallAdVo = new MallAdVo();
        mallAdVo.setState(StateEnum.ON_SHELF.getValue());
        mallAdVo.setAdType(AdTypeEnum.NORMAL.getValue());
        mallAdVo.setTenantId(tenantId);
        mallAdVo.setAdWords(adWords);
        mallAdVo.setImgSrc(imgSrc);
        mallAdVo.setLink(link);

        if (type == AD_TYPE_CHANNEL) {
            MallAdChannelVo mallAdChannelVo = new MallAdChannelVo();
            mallAdChannelVo.setSortNum(sortNum);
            mallAdChannelVo.setTenantId(tenantId);
            mallAdChannelVo.setChannelId(principalId);
            mallAdChannelVo.setMallAd(mallAdVo);
            mallAdChannelVo.setId(rId);

            MallAdChannelVo oldMallAdChannelVo = mallAdChannelClient.getById(rId, tenantId).getData();
            mallAdChannelVo.setClientType(oldMallAdChannelVo.getClientType());
            mallAdVo.setClientType(oldMallAdChannelVo.getClientType());
            Long adId = mallAdClient.get(oldMallAdChannelVo.getAdId(), tenantId).getData().getId();
            mallAdVo.setId(adId);
            LOGGER.info("edit mallAd:{}", mallAdChannelVo);
            mallAdChannelClient.update(mallAdChannelVo, oldMallAdChannelVo.getSortNum());
        } else if (type == AD_TYPE_FLOOR) {
            MallAdFloorVo mallAdFloorVo = new MallAdFloorVo();
            mallAdFloorVo.setTenantId(tenantId);
            mallAdFloorVo.setFloorId(principalId);
            mallAdFloorVo.setSortNum(sortNum);
            mallAdFloorVo.setId(rId);
            mallAdFloorVo.setMallAd(mallAdVo);

            MallAdFloorVo oldMallAdFloorVo = mallAdFloorClient.getById(rId, tenantId).getData();
            mallAdFloorVo.setClientType(oldMallAdFloorVo.getClientType());
            mallAdVo.setClientType(oldMallAdFloorVo.getClientType());
            Long adId = mallAdClient.get(oldMallAdFloorVo.getAdId(), tenantId).getData().getId();
            mallAdVo.setId(adId);
            LOGGER.info("edit mallAd:{}", mallAdFloorVo);
            mallAdFloorClient.update(mallAdFloorVo, oldMallAdFloorVo.getSortNum());
        } else if (type == AD_TYPE_CATEGORY) {
            MallAdCategoryVo mallAdCategoryVo = new MallAdCategoryVo();
            mallAdCategoryVo.setTenantId(tenantId);
            mallAdCategoryVo.setCategoryId(principalId);
            mallAdCategoryVo.setClientType(ClientTypeEnum.PC.getValue());
            mallAdCategoryVo.setSortNum(sortNum);
            mallAdCategoryVo.setId(rId);
            mallAdCategoryVo.setMallAd(mallAdVo);

            MallAdCategoryVo oldMallAdCategoryVo = mallAdCategoryClient.getById(rId, tenantId).getData();
            mallAdCategoryVo.setClientType(oldMallAdCategoryVo.getClientType());
            mallAdVo.setClientType(oldMallAdCategoryVo.getClientType());
            Long adId = mallAdClient.get(oldMallAdCategoryVo.getAdId(), tenantId).getData().getId();
            mallAdVo.setId(adId);
            LOGGER.info("edit mallAd:{}", mallAdCategoryVo);
            mallAdCategoryClient.update(mallAdCategoryVo, oldMallAdCategoryVo.getSortNum());
        }
        result.put("success", 1);
        return result;
    }

    /**
     * @description 功能描述: 删除广告
     * @author wangxin17
     * @date
     * @param
     * @throws
     * @return
     * @requireNo: ${REQUIRENO}
     */
    @RequestMapping(value = "/delete" ,method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> delete(@RequestParam Integer type, @RequestParam Long id) {
        Map<String, Object> result = new HashedMap();
        if (type == null) {
            result.put("success", -1);
            return result;
        }
        Long tenantId = ControllerContext.getTenantId();
        MallAdVo mallAdVo = new MallAdVo();
        mallAdVo.setTenantId(tenantId);

        if (type == AD_TYPE_CHANNEL) {
            MallAdChannelVo mallAdChannelVo = mallAdChannelClient.getById(id, tenantId).getData();
            mallAdVo.setId(mallAdChannelVo.getAdId());

            MallAdChannelVo mallAdChannel = new MallAdChannelVo();
            mallAdChannel.setId(id);
            mallAdChannel.setTenantId(tenantId);
            mallAdChannel.setMallAd(mallAdVo);

            LOGGER.info("delete mallAd:{}", mallAdChannel);
            mallAdChannelClient.delete(mallAdChannel);
        } else if (type == AD_TYPE_FLOOR) {
            MallAdFloorVo mallAdFloorVo = mallAdFloorClient.getById(id, tenantId).getData();
            mallAdVo.setId(mallAdFloorVo.getAdId());

            MallAdFloorVo mallAdFloor = new MallAdFloorVo();
            mallAdFloor.setId(id);
            mallAdFloor.setTenantId(tenantId);
            mallAdFloor.setMallAd(mallAdVo);

            LOGGER.info("delete mallAd:{}", mallAdFloor);
            mallAdFloorClient.delete(mallAdFloor);
        } else if (type == AD_TYPE_CATEGORY) {
            MallAdCategoryVo mallAdCategoryVo = mallAdCategoryClient.getById(id, tenantId).getData();
            mallAdVo.setId(mallAdCategoryVo.getAdId());

            MallAdCategoryVo mallAdCategory = new MallAdCategoryVo();
            mallAdCategory.setId(id);
            mallAdCategory.setTenantId(tenantId);
            mallAdCategory.setMallAd(mallAdVo);

            LOGGER.info("delete mallAd:{}", mallAdCategory);
            BaseResponseVo<Void> baseResponseVo=mallAdCategoryClient.delete(mallAdCategory);
        }
        result.put("success", 1);
        return result;
    }

    @RequestMapping(value = "/uploadImg" ,method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> uploadImg(@RequestParam MultipartFile fileImage) {
        Map<String, Object> result = new HashedMap();
        Long tenantId = ControllerContext.getTenantId();
        try {
            String contentType = fileImage.getContentType();
            contentType=contentType.toUpperCase();
            if (IMG_CONTENTY_TYPE.contains(contentType)) {
                Map<String, String> fileUrl = ossClient.uploadImageInternal(tenantId, fileImage.getBytes());
                result.put("success", 1);
                result.put("fileUrl", fileUrl.get("cdn"));
                return result;
            }
            result.put("info", "请检查格式和文件大小后重试");
        } catch (Exception e) {
            LOGGER.error("upload error", e);
            result.put("info", "系统发生错误，联系管理员");
            result.put("success", -1);
        }
        return result;
    }

    /**
     * 进入设置楼层推荐页
     * @param principalId  主体id
     * @return
     */
    @RequestMapping(value = "/toRecommendIndex" ,method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView toRecommendIndex(@RequestParam Integer type, @RequestParam Long principalId) {
        Long tenantId = ControllerContext.getTenantId();
        ModelAndView mv = new ModelAndView("pageManage/adRecommendManage");

        LOGGER.info("go ad recommend index, principalId={}, tenantId={}", principalId, tenantId);

        type = type == null ? 1 : type;
        int clientType = 0;

        LOGGER.info("go ad index, type={}, principalId={}, tenantId={}", type, principalId, tenantId);
        AdPrincipalTypeEnum principalTypeEnum = null;
        if (type == AD_TYPE_CHANNEL) {
            principalTypeEnum = AdPrincipalTypeEnum.CHANNEL;
            MallChannelVo mallChannelVo = mallChannelClient.get(principalId, tenantId).getData();
            clientType = mallChannelVo.getClientType();
        } else if (type == AD_TYPE_FLOOR){
            principalTypeEnum = AdPrincipalTypeEnum.FLOOR;
            MallFloorVo mallFloorVo = mallFloorClient.get(principalId, tenantId).getData();
            clientType = mallFloorVo.getClientType();
        }

        mv.addObject("principalId", principalId);

        MallAdVo mallAdVo = new MallAdVo();
        mallAdVo.setTenantId(tenantId);
        mallAdVo.setClientType(clientType);
        mallAdVo.setState(StateEnum.ON_SHELF.getValue());
        mallAdVo.setAdType(AdTypeEnum.RECOMMEND.getValue());

        List<MallAdVo> mallAdVos = mallAdClient.query(mallAdVo, principalTypeEnum.getCode(), principalId).getData();
        mv.addObject("type", type);
        mv.addObject("mallAdVos", mallAdVos);
        return mv;
    }

    /**
     * 获取sku信息
     * @param skuId  sku id
     * @return
     */
    @RequestMapping(value = "/getSkuInfo/{skuId}" ,method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> getSkuInfo(@PathVariable Long skuId) {
        Map<String, Object> result = new HashedMap();
        Long tenantId = ControllerContext.getTenantId();
        Long shopId = ControllerContext.getShopId();
        result.put("success", 1);
        ItemBasicInfoVo itemDetailVo = itemClient.getItemBasicInfo(tenantId, shopId, skuId).getData();
        if(itemDetailVo == null){
            result.put("success", 2);
        }
        result.put("itemDetailVo", itemDetailVo);
        return result;
    }

    /**
     * 获取sku信息
     * @return
     */
    @RequestMapping(value = "/addRecommend" ,method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> addRecommend(@RequestParam Integer type, @RequestParam Long principalId, @RequestParam Integer sortNum, @RequestParam Long skuId,
                                            @RequestParam String name, @RequestParam String subName1) {
        Map<String, Object> result = new HashedMap();
        Long tenantId = ControllerContext.getTenantId();

        String adWords = name + ";";
        if (StringUtils.isNoneBlank(subName1)) {
            adWords += subName1 + ";";
        }

        MallAdVo mallAdVo = new MallAdVo();
        mallAdVo.setState(StateEnum.ON_SHELF.getValue());
        mallAdVo.setAdType(AdTypeEnum.RECOMMEND.getValue());
        mallAdVo.setTenantId(tenantId);
        mallAdVo.setItemId(skuId);
        //TODO 调取商品详情接口
        Long shopId = ControllerContext.getShopId();
        ItemBasicInfoVo itemDetailVo = itemClient.getItemBasicInfo(tenantId, shopId, skuId).getData();
        if (itemDetailVo == null) {
            result.put("success", 0);
            result.put("msg", "未查询到该sku");
            return result;
        }
        mallAdVo.setImgSrc(ImageUtils.getImageUrl4Jfs(itemDetailVo.getImgUrl(), ImageSizeEnum.IMAGE_SIZE_N1.getCode()));
        mallAdVo.setLink(mallDomain + "/shop/item?itemId=" + skuId);
        adWords += itemDetailVo.getPrice() + ";";
        adWords += itemDetailVo.getMarketprice();
        mallAdVo.setAdWords(adWords);
        if (type == AD_TYPE_CHANNEL) {
            MallChannelVo mallChannelVo = mallChannelClient.get(principalId, tenantId).getData();
            mallAdVo.setClientType(mallChannelVo.getClientType());

            MallAdChannelVo mallAdChannelVo = new MallAdChannelVo();
            mallAdChannelVo.setTenantId(tenantId);
            mallAdChannelVo.setChannelId(principalId);
            mallAdChannelVo.setClientType(mallChannelVo.getClientType());
            mallAdChannelVo.setSortNum(sortNum);

            mallAdChannelVo.setMallAd(mallAdVo);

            LOGGER.info("add mallRecommend:{}", mallAdChannelVo);
            mallAdChannelClient.add(mallAdChannelVo);
        } else if (type == AD_TYPE_FLOOR) {
            MallFloorVo mallFloorVo = mallFloorClient.get(principalId, tenantId).getData();
            mallAdVo.setClientType(mallFloorVo.getClientType());

            MallAdFloorVo mallAdFloorVo = new MallAdFloorVo();
            mallAdFloorVo.setTenantId(tenantId);
            mallAdFloorVo.setFloorId(principalId);
            mallAdFloorVo.setClientType(mallFloorVo.getClientType());
            mallAdFloorVo.setSortNum(sortNum);

            mallAdFloorVo.setMallAd(mallAdVo);

            LOGGER.info("add mallRecommend:{}", mallAdFloorVo);
            mallAdFloorClient.add(mallAdFloorVo);
        }

        result.put("success", 1);
        return result;
    }

    /**
     * 获取sku信息
     * @return
     */
    @RequestMapping(value = "/editRecommend" ,method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> editRecommend(@RequestParam Integer type, @RequestParam Long principalId, @RequestParam Long rId, @RequestParam Integer sortNum, @RequestParam Long skuId,
                                             @RequestParam String name, @RequestParam String subName1) {
        Map<String, Object> result = new HashedMap();
        Long tenantId = ControllerContext.getTenantId();
        String adWords = name + ";";
        if (StringUtils.isNoneBlank(subName1)) {
            adWords += subName1 + ";";
        }

        MallAdVo mallAdVo = new MallAdVo();
        mallAdVo.setState(StateEnum.ON_SHELF.getValue());
        mallAdVo.setAdType(AdTypeEnum.RECOMMEND.getValue());
        mallAdVo.setTenantId(tenantId);

        //TODO 调取商品详情接口
        Long shopId = ControllerContext.getShopId();
        ItemBasicInfoVo itemDetailVo = itemClient.getItemBasicInfo(tenantId, shopId, skuId).getData();
        if (itemDetailVo == null) {
            result.put("success", 0);
            result.put("msg", "未查询到该sku");
            return result;
        }
        mallAdVo.setImgSrc(ImageUtils.getImageUrl4Jfs(itemDetailVo.getImgUrl(), ImageSizeEnum.IMAGE_SIZE_N1.getCode()));
        mallAdVo.setLink(mallDomain + "/shop/item?itemId=" + skuId);
        adWords += itemDetailVo.getPrice() + ";";
        adWords += itemDetailVo.getMarketprice();
        mallAdVo.setAdWords(adWords);
        mallAdVo.setItemId(skuId);
        if (type == AD_TYPE_CHANNEL) {
            MallAdChannelVo mallAdChannelVo = new MallAdChannelVo();
            mallAdChannelVo.setTenantId(tenantId);
            mallAdChannelVo.setChannelId(principalId);
            mallAdChannelVo.setSortNum(sortNum);
            mallAdChannelVo.setId(rId);
            mallAdChannelVo.setMallAd(mallAdVo);

            MallAdChannelVo oldMallAdChannelVo = mallAdChannelClient.getById(rId, tenantId).getData();
            mallAdVo.setClientType(oldMallAdChannelVo.getClientType());
            mallAdChannelVo.setClientType(oldMallAdChannelVo.getClientType());

            Long adId = mallAdClient.get(oldMallAdChannelVo.getAdId(), tenantId).getData().getId();
            mallAdVo.setId(adId);
            LOGGER.info("edit recommend:{}", mallAdChannelVo);
            mallAdChannelClient.update(mallAdChannelVo, oldMallAdChannelVo.getSortNum());
        } else if (type == AD_TYPE_FLOOR) {
            MallAdFloorVo mallAdFloorVo = new MallAdFloorVo();
            mallAdFloorVo.setTenantId(tenantId);
            mallAdFloorVo.setFloorId(principalId);
            mallAdFloorVo.setSortNum(sortNum);
            mallAdFloorVo.setId(rId);
            mallAdFloorVo.setMallAd(mallAdVo);

            MallAdFloorVo oldMallAdFloorVo = mallAdFloorClient.getById(rId, tenantId).getData();
            mallAdVo.setClientType(oldMallAdFloorVo.getClientType());
            mallAdFloorVo.setClientType(oldMallAdFloorVo.getClientType());

            Long adId = mallAdClient.get(oldMallAdFloorVo.getAdId(), tenantId).getData().getId();
            mallAdVo.setId(adId);
            LOGGER.info("edit recommend:{}", mallAdFloorVo);
            mallAdFloorClient.update(mallAdFloorVo, oldMallAdFloorVo.getSortNum());
        }

        result.put("success", 1);
        return result;
    }

    @RequestMapping(value = "/getMallAd" ,method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public MallAdVo getMallAd(@RequestParam Long id){
        Long tenantId = ControllerContext.getTenantId();
        BaseResponseVo<MallAdVo> result = mallAdClient.get(id, tenantId);
        return result.getData();
    }

    @RequestMapping(value = "/updateMallAd" ,method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public boolean updateMallAd(MallAdVo mallAdVo){
        Long tenantId = ControllerContext.getTenantId();
        mallAdVo.setTenantId(tenantId);
        BaseResponseVo<Void> result = mallAdClient.updateByMall(mallAdVo);
        return result.isSuccess();
    }
}
