/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: MallBannerClient.java project: jcloud-b2c-platform
 * @creator: lidongxing
 * @date: 2017/2/15
 */

package com.jcloud.b2c.platform.service.feign;

import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.mall.service.client.vo.MallAdRecommendVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * @description: 轮播图+广告位CLIENT
 * @author: lidongxing
 * @requireNo:
 * @createdate: 2017-02-15 18:10
 * @lastdate:
 */
//@FeignClient(name="b2c-mall-service", url = "http://localhost:8083")
@FeignClient(name="b2c-mall-service")
public interface MallAdRecommendClient {
    @RequestMapping(value = "/MallAdRecommend/get", method = GET)
    BaseResponseVo<MallAdRecommendVo> get(@RequestParam(value = "id") Long id, @RequestParam(value = "tenantId") Long tenantId);

    @RequestMapping(value = "/MallAdRecommend/query", method = POST)
    BaseResponseVo<List<MallAdRecommendVo>> query(@RequestBody MallAdRecommendVo MallAdRecommend);

    @RequestMapping(value = "/MallAdRecommend/update", method = POST)
    BaseResponseVo<Void> update(@RequestBody MallAdRecommendVo MallAdRecommend);

    @RequestMapping(value = "/MallAdRecommend/add", method = POST)
    BaseResponseVo<Void> add(@RequestBody MallAdRecommendVo MallAdRecommend);

    @RequestMapping(value = "/MallAdRecommend/delete", method = POST)
    BaseResponseVo<Void> delete(@RequestBody MallAdRecommendVo MallAdRecommend);
}
