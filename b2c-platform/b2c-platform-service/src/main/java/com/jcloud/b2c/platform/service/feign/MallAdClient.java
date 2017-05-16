/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: MallAdClient.java project: jcloud-b2c-platform
 * @creator: lidongxing
 * @date: 2017/2/16
 */

package com.jcloud.b2c.platform.service.feign;

import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.mall.service.client.vo.MallAdVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * @description: 广告CLIENT
 * @author: lidongxing
 * @requireNo:
 * @createdate: 2017-02-16 15:34
 * @lastdate:
 */
//@FeignClient(name = "b2c-mall-service")
@FeignClient(name = "b2c-mall-service")
public interface MallAdClient {
    @RequestMapping(value = "/mallAd/get", method = GET)
    BaseResponseVo<MallAdVo> get(@RequestParam(value = "id") Long id, @RequestParam(value = "tenantId") Long tenantId);

    @RequestMapping(value = "/mallAd/query", method = POST)
    BaseResponseVo<List<MallAdVo>> query(@RequestBody MallAdVo mallAd, @RequestParam(value = "principalType") String principalType,
                                         @RequestParam(value = "principalId") Long principalId);

    @RequestMapping(value = "/mallAd/update", method = POST)
    BaseResponseVo<Void> update(@RequestBody MallAdVo mallAd, @RequestParam(value = "principalType") String principalType,
                                @RequestParam(value = "principalId") Long principalId, @RequestParam(value = "principalSort") Integer principalSort);

    @RequestMapping(value = "/mallAd/add", method = POST)
    BaseResponseVo<Void> add(@RequestBody MallAdVo mallAd, @RequestParam(value = "principalType") String principalType,
                             @RequestParam(value = "principalId") Long principalId, @RequestParam(value = "principalSort") Integer principalSort);

    @RequestMapping(value = "/mallAd/delete", method = POST)
    BaseResponseVo<Void> delete(@RequestBody MallAdVo mallAd, @RequestParam(value = "principalType") String principalType,
                                @RequestParam(value = "principalId") Long principalId);


    @RequestMapping(value = "/mallAd/queryByMall", method = POST)
    BaseResponseVo<List<MallAdVo>> queryByMall(@RequestBody MallAdVo mallAd);

    @RequestMapping(value = "/mallAd/updateByMall", method = POST)
    BaseResponseVo<Void> updateByMall(@RequestBody MallAdVo mallAd);

    @RequestMapping(value = "/mallAd/addByMall", method = POST)
    BaseResponseVo<Void> addByMall(@RequestBody MallAdVo mallAd);

    @RequestMapping(value = "/mallAd/deleteByMall", method = POST)
    BaseResponseVo<Void> deleteByMall(@RequestBody MallAdVo mallAd);
}
