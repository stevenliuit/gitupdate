/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: EsClient.java project: jcloud-b2c-mall-service
 * @creator: wangxin17
 * @date: 2017/3/2
 */
package com.jcloud.b2c.mall.service.service.feign;

import com.jcloud.b2c.common.common.web.feign.ElasticsearchFeginInterface;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @description:
 * @author: wangxin17
 * @requireNo:
 * @createdate: 2017-03-02 18:07
 * @lastdate:
 */
@FeignClient(name = "SEARCH-SERVICE")
public interface EsClient extends ElasticsearchFeginInterface {
}
