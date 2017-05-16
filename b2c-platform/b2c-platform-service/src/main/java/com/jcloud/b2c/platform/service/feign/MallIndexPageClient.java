/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: MallIndexPageClient.java project: b2c-platform
 * @creator: wangxin17
 * @date: 2017/3/1
 */
package com.jcloud.b2c.platform.service.feign;

import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @description:
 * @author: wangxin17
 * @requireNo:
 * @createdate: 2017-03-01 13:58
 * @lastdate:
 */
//@FeignClient(name = "b2c-mall-service-local", url = "http://localhost:8083/")
@FeignClient(name = "b2c-mall-service")
public interface MallIndexPageClient {

    @RequestMapping(value = "/mallIndexPage/createIndexPage" ,method = {RequestMethod.GET})
    BaseResponseVo<Boolean> createIndexPage(@RequestParam(value = "tenantId") Long tenantId);

    @RequestMapping(value = "/mallIndexPage/createIndexPageReloadTempate" ,method = {RequestMethod.GET})
    BaseResponseVo<Boolean> createIndexPageReloadTempate(@RequestParam(value = "tenantId") Long tenantId,@RequestParam(value = "reloadTempate") Boolean reloadTempate );

    @RequestMapping(value = "/mallIndexPage/getCacheKey" ,method = RequestMethod.GET)
    List<String> getCacheKey(@RequestParam(value = "tenantId") Long tenantId) ;

    @RequestMapping(value = "/mallIndexPage/viewIndexPage" ,method = RequestMethod.GET)
    BaseResponseVo<String> viewIndexPage(@RequestParam(value = "tenantId") Long tenantId);
    @RequestMapping(value = "/mallIndexPage/viewMIndexPage" ,method = RequestMethod.GET)
    BaseResponseVo<String> viewMIndexPage(@RequestParam(value = "tenantId") Long tenantId);
    @RequestMapping(value = "/mallIndexPage/createMIndexPage" ,method = RequestMethod.GET)
    BaseResponseVo<String> createMIndexPage(@RequestParam(value = "tenantId") Long tenantId);
    @RequestMapping(value = "/mallIndexPage/reloadTemplate" ,method = RequestMethod.GET)
    Boolean reloadTemplate();
    @RequestMapping(value = "/mallIndexPage/getCommonHeader" ,method = RequestMethod.GET)
    BaseResponseVo<String> getCommonHeader(@RequestParam(value = "tenantId") Long tenantId);

    @RequestMapping(value = "/mallIndexPage/getCommonHeaderFragment" ,method = RequestMethod.GET)
    BaseResponseVo<String> getCommonHeaderFragment(@RequestParam(value = "tenantId") Long tenantId);

    @RequestMapping(value = "/mallIndexPage/getCommonFooter" ,method = RequestMethod.GET)
    BaseResponseVo<String> getCommonFooter(@RequestParam(value = "tenantId") Long tenantId);

    @RequestMapping(value = "/mallIndexPage/createChannelIndexPage" ,method = RequestMethod.GET)
    BaseResponseVo<Void> createChannelIndexPage(@RequestParam(value = "tenantId") Long tenantId, @RequestParam(value = "channelCode") String channelCode);

    @RequestMapping(value = "/mallIndexPage/viewChannelIndexPage" ,method = RequestMethod.GET)
    BaseResponseVo<String> viewChannelIndexPage(@RequestParam(value = "tenantId") Long tenantId, @RequestParam(value = "channelCode") String channelCode);

    @RequestMapping(value = "/mallIndexPage/getChannelIndexPage" ,method = RequestMethod.GET)
    BaseResponseVo<String> getChannelIndexPage(@RequestParam(value = "tenantId") Long tenantId, @RequestParam(value = "channelCode") String channelCode);

    @RequestMapping(value = "/mallIndexPage/deleteChannelIndexPageFromCache" ,method = RequestMethod.GET)
    BaseResponseVo<Void> deleteChannelIndexPageFromCache(@RequestParam(value = "tenantId") Long tenantId, @RequestParam(value = "channelCode") String channelCode);
}
