/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: CacheFeignClient.java project: jcloud-b2c-mall-service
 * @creator: wangxin17
 * @date: 2017/2/22
 */
package com.jcloud.b2c.platform.service.feign;

import com.jcloud.b2c.common.common.web.feign.CacheFeignInterface;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @description:
 * @author: wangxin17
 * @requireNo:
 * @createdate: 2017-02-22 15:36
 * @lastdate:
 */
@FeignClient(name = "CACHE-SERVICE")
public interface CacheFeignClient extends CacheFeignInterface {

}
