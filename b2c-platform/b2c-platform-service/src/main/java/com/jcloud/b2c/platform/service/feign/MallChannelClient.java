/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: MallChannelClient.java project: jcloud-b2c-platform
 * @creator: lidongxing
 * @date: 2017/2/15
 */

package com.jcloud.b2c.platform.service.feign;

import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.mall.service.client.vo.MallChannelVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * @description: 频道CLIENT
 * @author: lidongxing
 * @requireNo:
 * @createdate: 2017-02-15 18:11
 * @lastdate:
 */
@FeignClient(name="b2c-mall-service")
public interface MallChannelClient {
    @RequestMapping(value = "/mallChannel/get", method = GET)
    BaseResponseVo<MallChannelVo> get(@RequestParam(value = "id") Long id, @RequestParam(value = "tenantId") Long tenantId);

    @RequestMapping(value = "/mallChannel/query", method = POST)
    BaseResponseVo<List<MallChannelVo>> query(@RequestBody MallChannelVo mallChannel);

    @RequestMapping(value = "/mallChannel/update", method = POST)
    BaseResponseVo<Void> update(@RequestBody MallChannelVo mallChannel);

    @RequestMapping(value = "/mallChannel/add", method = POST)
    BaseResponseVo<Void> add(@RequestBody MallChannelVo mallChannel);

    @RequestMapping(value = "/mallChannel/delete", method = POST)
    BaseResponseVo<Void> delete(@RequestBody MallChannelVo mallChannel);
}
