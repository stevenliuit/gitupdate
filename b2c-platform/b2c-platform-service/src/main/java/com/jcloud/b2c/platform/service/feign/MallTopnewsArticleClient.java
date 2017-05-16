/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: MallBannerClient.java project: jcloud-b2c-platform
 * @creator: lidongxing
 * @date: 2017/2/15
 */

package com.jcloud.b2c.platform.service.feign;

import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.mall.service.client.vo.MallTopnewsArticleVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * 头条
 */
@FeignClient(name="b2c-mall-service")
//@FeignClient(name="b2c-mall-service")
public interface MallTopnewsArticleClient {
    @RequestMapping(value = "/mallTopnewsArticle/get", method = GET)
    BaseResponseVo<MallTopnewsArticleVo> get(@RequestParam(value = "id") Long id, @RequestParam(value = "tenantId") Long tenantId);

    @RequestMapping(value = "/mallTopnewsArticle/query", method = POST)
    BaseResponseVo<List<MallTopnewsArticleVo>> query(@RequestBody MallTopnewsArticleVo mallTopnewsArticleVo);

    @RequestMapping(value = "/mallTopnewsArticle/update", method = POST)
    BaseResponseVo<Void> update(@RequestBody MallTopnewsArticleVo mallTopnewsArticleVo);

    @RequestMapping(value = "/mallTopnewsArticle/add", method = POST)
    BaseResponseVo<Void> add(@RequestBody MallTopnewsArticleVo mallTopnewsArticleVo);

    @RequestMapping(value = "/mallTopnewsArticle/delete", method = POST)
    BaseResponseVo<Void> delete(@RequestBody MallTopnewsArticleVo mallTopnewsArticleVo);
}
