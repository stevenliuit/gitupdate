/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: MallIndexPageController.java project: jcloud-b2c-mall-service
 * @creator: wangxin17
 * @date: 2017/2/16
 */
package com.jcloud.b2c.mall.service.web;

import com.jcloud.b2c.common.common.util.ImageUtils;
import com.jcloud.b2c.common.common.util.JacksonUtil;
import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.item.client.vo.item.MallQueryNewSkuListCriteria;
import com.jcloud.b2c.item.client.vo.item.MallSkuListVo;
import com.jcloud.b2c.mall.service.client.enums.CommonHtmlCacheKeyEnum;
import com.jcloud.b2c.mall.service.rpc.service.OpenService;
import com.jcloud.b2c.mall.service.service.MallIndexPageService;
import com.jcloud.b2c.mall.service.service.feign.CacheFeignClient;
import com.jcloud.b2c.mall.service.service.feign.ItemClient;
import com.jd.ump.profiler.CallerInfo;
import com.jd.ump.profiler.proxy.Profiler;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @description:
 * @author: wangxin17
 * @requireNo:
 * @createdate: 2017-02-16 14:06
 * @lastdate:
 */
@RestController
@RequestMapping("/mallIndexPage")
public class MallIndexPageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MallIndexPageController.class);

    private static String NEW_ITEMS_CACHE_KEY = "mall_index_page_new_items_key_";

    @Resource
    private MallIndexPageService mallIndexPageService;

    @Resource
    private ItemClient itemClient;

    @Resource
    private OpenService orgpenService;

    @Resource
    private CacheFeignClient cacheFeignClient;

    @RequestMapping(value = "/getMIndexPage" ,method = RequestMethod.GET)
    public  BaseResponseVo<String> getMIndexPage(@RequestParam Long tenantId) {
        BaseResponseVo<String> responseVo = new BaseResponseVo();
        try {
            String header = mallIndexPageService.getMIndexPage(tenantId);
            responseVo.setData(header);
            responseVo.setIsSuccess(true);
        }catch (Exception e){
            LOGGER.warn("getMIndexPage error", tenantId, e);
            responseVo.setIsSuccess(false);
        }
        return responseVo;
    }

    @RequestMapping(value = "/createMIndexPage" ,method = {RequestMethod.POST, RequestMethod.GET})
    public BaseResponseVo<Boolean> createH5IndexPage(@RequestParam Long tenantId) {
        BaseResponseVo<Boolean> responseVo = new BaseResponseVo();
        boolean h5 = mallIndexPageService.createH5IndexPage(tenantId);
        responseVo.setData(h5);responseVo.setMessage("h5 create  "+h5);
        responseVo.setIsSuccess(true);
        return responseVo;
    }



    @RequestMapping(value = "/viewMIndexPage" ,method = RequestMethod.GET)
    public BaseResponseVo<String> view5HIndexPage(@RequestParam Long tenantId) {
        BaseResponseVo<String> responseVo = new BaseResponseVo();
        String body = mallIndexPageService.viewH5IndexPage(tenantId);
        String html = orgpenService.getMIndexPage();
        html = html.replace("dexPage.init_body()","dexPage.init_lunbotu()");
        Document doc = Jsoup.parse(html);
        Element element = doc.select("#body_div").first();
        element.append(body);
        responseVo.setData(doc.html());
        responseVo.setIsSuccess(true);
        return responseVo;
    }

    @RequestMapping(value = "/createIndexPageReloadTempate" ,method = {RequestMethod.GET})
    public BaseResponseVo<Boolean> createIndexPageReloadTempate(@RequestParam(value = "tenantId") Long tenantId,@RequestParam(value = "reloadTempate") Boolean reloadTempate ){
        BaseResponseVo<Boolean> responseVo = new BaseResponseVo();
        boolean h5 = mallIndexPageService.createIndexPage(tenantId,reloadTempate);
        responseVo.setData(h5);
        responseVo.setIsSuccess(true);
        return responseVo;
    }

    @RequestMapping(value = "/createIndexPage" ,method = {RequestMethod.POST, RequestMethod.GET})
    public BaseResponseVo<Boolean> createIndexPage(@RequestParam Long tenantId) {
        return createIndexPageReloadTempate(tenantId,false);
    }


    @RequestMapping(value = "/viewIndexPage" ,method = RequestMethod.GET)
    public BaseResponseVo<String> viewIndexPage(@RequestParam Long tenantId) {
        BaseResponseVo<String> responseVo = new BaseResponseVo();
        String html = mallIndexPageService.viewIndexPage(tenantId);
        responseVo.setData(html);
        responseVo.setIsSuccess(true);
        return responseVo;
    }

    @RequestMapping(value = "/getCommonHeader" ,method = RequestMethod.GET)
    public BaseResponseVo<String> getCommonHeader(@RequestParam Long tenantId) {
        BaseResponseVo<String> responseVo = new BaseResponseVo();
        CallerInfo callerInfo = null;
        try {
            callerInfo = Profiler.registerInfo("com.jcloud.b2c.mall.service.web.MallIndexPageController.getCommonHeader", false, true);
            String header = mallIndexPageService.getCommonHeader(tenantId);
            responseVo.setData(header);
            responseVo.setIsSuccess(true);
        }catch (Exception e) {
            Profiler.functionError(callerInfo);
            LOGGER.error("loginBack error:", e);
            throw  new RuntimeException(e);
        } finally {
            Profiler.registerInfoEnd(callerInfo);
        }
        return responseVo;
    }

    @RequestMapping(value = "/getSimpleHeader" ,method = RequestMethod.GET)
    public BaseResponseVo<String> getSimpleHeader(@RequestParam Long tenantId) {
        BaseResponseVo<String> responseVo = new BaseResponseVo();
        CallerInfo callerInfo = null;
        try {
            callerInfo = Profiler.registerInfo("com.jcloud.b2c.mall.service.web.MallIndexPageController.getSimpleHeader", false, true);
            String header = mallIndexPageService.getSimpleHeader(tenantId);
            responseVo.setData(header);
            responseVo.setIsSuccess(true);
        }catch (Exception e) {
            Profiler.functionError(callerInfo);
            LOGGER.error("loginBack error:", e);
            throw  new RuntimeException(e);
        } finally {
            Profiler.registerInfoEnd(callerInfo);
        }
        return responseVo;
    }
    @RequestMapping(value = "/getCommonHeaderFragment" ,method = RequestMethod.GET)
    public BaseResponseVo<String> getCommonHeaderFragment(@RequestParam Long tenantId) {
        BaseResponseVo<String> responseVo = new BaseResponseVo();
        CallerInfo callerInfo = null;
        try {
            callerInfo = Profiler.registerInfo("com.jcloud.b2c.mall.service.web.MallIndexPageController.getCommonHeaderFragment", false, true);
            String header = mallIndexPageService.getCommonHeaderFragment(tenantId);
            responseVo.setData(header);
            responseVo.setIsSuccess(true);
        }catch (Exception e) {
            Profiler.functionError(callerInfo);
            LOGGER.error("loginBack error:", e);
            throw  new RuntimeException(e);
        } finally {
            Profiler.registerInfoEnd(callerInfo);
        }
        return responseVo;
    }

    @RequestMapping(value = "/getCommonFooter" ,method = RequestMethod.GET)
    public BaseResponseVo<String> getCommonFooter(@RequestParam Long tenantId) {
        BaseResponseVo<String> responseVo = new BaseResponseVo();
        CallerInfo callerInfo = null;
        try {
            callerInfo = Profiler.registerInfo("com.jcloud.b2c.mall.service.web.MallIndexPageController.getCommonFooter", false, true);
            String footer = mallIndexPageService.getCommonFooter(tenantId);
            responseVo.setData(footer);
            responseVo.setIsSuccess(true);
        }catch (Exception e) {
            Profiler.functionError(callerInfo);
            LOGGER.error("loginBack error:", e);
            throw  new RuntimeException(e);
        } finally {
            Profiler.registerInfoEnd(callerInfo);
        }
        return responseVo;
    }

    @RequestMapping(value = "/createChannelIndexPage" ,method = RequestMethod.GET)
    public BaseResponseVo<Void> createChannelIndexPage(@RequestParam Long tenantId, @RequestParam String channelCode) {
        BaseResponseVo<Void> responseVo = new BaseResponseVo();
        Boolean flag = mallIndexPageService.createChannelIndexPage(tenantId, channelCode);
        responseVo.setIsSuccess(flag);
        return responseVo;
    }

    @RequestMapping(value = "/viewChannelIndexPage" ,method = RequestMethod.GET)
    public BaseResponseVo<String> viewChannelIndexPage(@RequestParam Long tenantId, @RequestParam String channelCode) {
        BaseResponseVo<String> responseVo = new BaseResponseVo();
        String html = mallIndexPageService.viewChannelIndexPage(tenantId, channelCode);
        responseVo.setData(html);
        responseVo.setIsSuccess(true);
        return responseVo;
    }

    @RequestMapping(value = "/getChannelIndexPage" ,method = RequestMethod.GET)
    public BaseResponseVo<String> getChannelIndexPage(@RequestParam Long tenantId, @RequestParam String channelCode) {
        BaseResponseVo<String> responseVo = new BaseResponseVo();
        String html = mallIndexPageService.getChannelIndexPage(tenantId, channelCode);
        responseVo.setData(html);
        responseVo.setIsSuccess(true);
        return responseVo;
    }

    @RequestMapping(value = "/deleteChannelIndexPageFromCache" ,method = RequestMethod.GET)
    public BaseResponseVo<Void> deleteChannelIndexPageFromCache(@RequestParam Long tenantId, @RequestParam String channelCode) {
        BaseResponseVo<Void> responseVo = new BaseResponseVo();
        mallIndexPageService.deleteChannelIndexPageFromCache(tenantId, channelCode);
        responseVo.setIsSuccess(true);
        return responseVo;
    }

    @RequestMapping(value = "/cacheNewItems" ,method = RequestMethod.GET)
    public BaseResponseVo<Void> cacheNewItems() {
        LOGGER.info("begin cache new items");
        MallQueryNewSkuListCriteria criteria = new MallQueryNewSkuListCriteria();
        //TODO:暂时写死租户
        Long tenantId = 1L;
        criteria.setTenantId(tenantId);
        criteria.setShopId(tenantId);
        criteria.setLimit(50);
        BaseResponseVo<List<MallSkuListVo>> responseVo = itemClient.getNewSkuList(criteria);
        if (responseVo.isSuccess()) {
            for (MallSkuListVo mallSkuListVo : responseVo.getData()) {
                try {
                    mallSkuListVo.setMainPictureUrl(ImageUtils.getImageUrl4Jfs(mallSkuListVo.getMainPictureUrl(), 220, 220));
                } catch (Exception e) {
                    LOGGER.error("create item main img error, skuId=" + mallSkuListVo.getSkuId(), e);
                }
            }
            String newItemsJson = JacksonUtil.convert(responseVo.getData());
            try {
                cacheFeignClient.saveBytes2Cache(tenantId, NEW_ITEMS_CACHE_KEY + tenantId, newItemsJson.getBytes("utf-8"), 0);
            } catch (UnsupportedEncodingException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
        BaseResponseVo<Void> result = new BaseResponseVo();
        result.setIsSuccess(true);

        LOGGER.info("end cache new items, tenantId={}", tenantId);
        return result;
    }


    @RequestMapping(value = "/getPayHeader" ,method = RequestMethod.GET)
    public BaseResponseVo<String> getPayHeader(@RequestParam Long tenantId) {
        BaseResponseVo<String> responseVo = new BaseResponseVo();
        String html = mallIndexPageService.getPayHeader(tenantId);
        responseVo.setData(html);
        responseVo.setIsSuccess(true);
        return responseVo;
    }

    @RequestMapping(value = "/getIndexPage" ,method = RequestMethod.GET)
    public BaseResponseVo<String> getIndexPage(@RequestParam Long tenantId) {
        BaseResponseVo<String> responseVo = new BaseResponseVo();
        String html = mallIndexPageService.getIndexPage(tenantId);
        responseVo.setData(html);
        responseVo.setIsSuccess(true);
        return responseVo;
    }

    @RequestMapping(value = "/reloadTemplate" ,method = RequestMethod.GET)
    public Boolean reloadTemplate(){
        mallIndexPageService.reloadTemplate();
        return false;
    }
    @RequestMapping(value = "/getCacheKey" ,method = RequestMethod.GET)
    public List<String> getCacheKey(@RequestParam Long tenantId) {
        return CommonHtmlCacheKeyEnum.getKeys(tenantId);
    }


}
