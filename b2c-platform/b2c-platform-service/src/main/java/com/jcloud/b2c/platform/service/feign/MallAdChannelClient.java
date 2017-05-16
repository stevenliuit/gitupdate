/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: MallAdChannelClient.java project: b2c-platform
 * @creator: wangxin17
 * @date: 2017/2/25
 */
package com.jcloud.b2c.platform.service.feign;

import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.mall.service.client.vo.MallAdChannelVo;
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
 * @createdate: 2017-02-25 12:56
 * @lastdate:
 */
@FeignClient(name = "b2c-mall-service")
//@FeignClient(name = "b2c-mall-service")
public interface MallAdChannelClient {

    /**
     * 频道广告列表
     * @param channelId
     * @param tenantId
     * @return
     */
    @RequestMapping(value = "/mallAdChannel/query" ,method = RequestMethod.GET)
    BaseResponseVo<List<MallAdChannelVo>> query(@RequestParam(value = "channelId") Long channelId, @RequestParam(value = "tenantId") Long tenantId);

    @RequestMapping(value = "/mallAdChannel/add" ,method = RequestMethod.POST)
    BaseResponseVo<Void> add(@RequestBody MallAdChannelVo mallAdChannel);

    @RequestMapping(value = "/mallAdChannel/get" ,method = RequestMethod.GET)
    BaseResponseVo<MallAdChannelVo> getById(@RequestParam(value = "id") Long id, @RequestParam(value = "tenantId") Long tenantId);

    @RequestMapping(value = "/mallAdChannel/update" ,method = RequestMethod.POST)
    BaseResponseVo<Void> update(@RequestBody MallAdChannelVo mallAdChannel, @RequestParam(value = "beforeSort") Integer beforeSort);

    @RequestMapping(value = "/mallAdChannel/delete" ,method = RequestMethod.POST)
    BaseResponseVo<Void> delete(@RequestBody MallAdChannelVo mallAdChannel);
}
