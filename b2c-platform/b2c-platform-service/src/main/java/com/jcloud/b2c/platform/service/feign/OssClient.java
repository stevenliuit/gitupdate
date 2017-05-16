/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: OssClient.java project: b2c-platform
 * @creator: wangxin17
 * @date: 2017/2/25
 */
package com.jcloud.b2c.platform.service.feign;

import com.jcloud.b2c.common.common.web.feign.OssFeignInterface;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @description:
 * @author: wangxin17
 * @requireNo:
 * @createdate: 2017-02-25 16:43
 * @lastdate:
 */
//@FeignClient(name = "SERVICE-OSS")
@FeignClient(name="OSS-SERVICE")
public interface OssClient extends OssFeignInterface {
}
