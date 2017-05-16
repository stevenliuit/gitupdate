/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: MallChannelClient.java project: jcloud-b2c-platform
 * @creator: lidongxing
 * @date: 2017/2/15
 */

package com.jcloud.b2c.platform.service.feign;

import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.mall.service.client.vo.MallArticleCategoryVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * @description: 文章CLIENT
 * @author: yuezheng
 * @requireNo:
 * @createdate: 2017-03-02 18:11
 * @lastdate:
 */

@FeignClient(name="b2c-mall-service")
public interface MallArticleCategoryClient {
    @RequestMapping(value = "/mallArticleCategory/get", method = GET)
    BaseResponseVo<MallArticleCategoryVo> get(@RequestParam(value = "id") Long id, @RequestParam(value = "tenantId") Long tenantId);

    @RequestMapping(value = "/mallArticleCategory/query", method = POST)
    BaseResponseVo<List<MallArticleCategoryVo>> query(@RequestBody MallArticleCategoryVo mallArticleCategory);

    @RequestMapping(value = "/mallArticleCategory/update", method = POST)
    BaseResponseVo<Void> update(@RequestBody MallArticleCategoryVo mallArticleCategory);

    @RequestMapping(value = "/mallArticleCategory/add", method = POST)
    BaseResponseVo<Void> add(@RequestBody MallArticleCategoryVo mallArticleCategory);

    @RequestMapping(value = "/mallArticleCategory/delete", method = POST)
    BaseResponseVo<Void> delete(@RequestBody MallArticleCategoryVo mallArticleCategory);


}
