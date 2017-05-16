/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: MallAdCategoryClient.java project: b2c-platform
 * @creator: wangxin17
 * @date: 2017/2/28
 */
package com.jcloud.b2c.platform.service.feign;

import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.mall.service.client.vo.MallAdCategoryVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @description:
 * @author: wangxin17
 * @requireNo:
 * @createdate: 2017-02-28 20:47
 * @lastdate:
 */
@FeignClient(name = "b2c-mall-service")
public interface MallAdCategoryClient {

    @RequestMapping(value = "/mallAdCategory/query" ,method = RequestMethod.GET)
    BaseResponseVo<List<MallAdCategoryVo>> query(@RequestParam(value = "channelId") Long channelId, @RequestParam(value = "tenantId") Long tenantId);

    @RequestMapping(value = "/mallAdCategory/add" ,method = RequestMethod.POST)
    BaseResponseVo<Void> add(@RequestBody MallAdCategoryVo mallAdCategory);

    @RequestMapping(value = "/mallAdCategory/get" ,method = RequestMethod.GET)
    BaseResponseVo<MallAdCategoryVo> getById(@RequestParam(value = "id") Long id, @RequestParam(value = "tenantId") Long tenantId);

    @RequestMapping(value = "/mallAdCategory/update" ,method = RequestMethod.POST)
    BaseResponseVo<Void> update(@RequestBody MallAdCategoryVo mallAdCategory, @RequestParam(value = "beforeSort") Integer beforeSort);

    @RequestMapping(value = "/mallAdCategory/delete" ,method = RequestMethod.POST)
    BaseResponseVo<Void> delete(@RequestBody MallAdCategoryVo mallAdCategory);
}
