/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: FeignClientConfig.java project: jcloud-b2c-mall-service
 * @creator: wangxin17
 * @date: 2017/2/22
 */
package com.jcloud.b2c.mall.service.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: wangxin17
 * @requireNo:
 * @createdate: 2017-02-22 18:46
 * @lastdate:
 */
@Configuration
public class FeignClientConfig {

    /**
     * feign 日志配置
     *
     * @return
     */
    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}

