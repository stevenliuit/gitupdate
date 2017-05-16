/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: OssClient.java project: jcloud-b2c-mall-service
 * @creator: wangxin17
 * @date: 2017/3/3
 */
package com.jcloud.b2c.mall.service.service.feign;

import com.jcloud.b2c.common.common.web.feign.OssFeignInterface;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @description:
 * @author: wangxin17
 * @requireNo:
 * @createdate: 2017-03-03 13:48
 * @lastdate:
 */
@FeignClient(name = "OSS-SERVICE")
public interface OssClient extends OssFeignInterface {
}
