/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: TenantService.java project: jcloud-b2c-mall-service
 * @creator: wangxin17
 * @date: 2017/3/8
 */
package com.jcloud.b2c.mall.service.web;

import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.mall.service.rpc.service.OpenService;
import com.jcloud.b2c.mall.service.service.feign.CacheFeignClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @description:
 * @author: wangxin17
 * @requireNo:
 * @createdate: 2017-03-08 14:58
 * @lastdate:
 */
@RestController
@RequestMapping("/tenant")
public class TenantController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TenantController.class);

    private static String KEY_KPL_ACCESS_TOKEN = "KPL_ACCESS_TOKEN_";
    private static String KEY_APP_KEY = "APP_KEY";

    private static ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(5);

    @Resource
    private CacheFeignClient cacheFeignClient;

    @Resource
    private OpenService openService;

    @Value("${cdn.domain}")
    private String cdnDomain;

    @RequestMapping(value = "/getKplAppKey", method = RequestMethod.GET)
    public String getKplAppKey(@RequestParam Long tenantId) {
        Map<String, String> accessTokenMap = cacheFeignClient.hgetAll(tenantId, KEY_KPL_ACCESS_TOKEN + tenantId);
        String appKey = accessTokenMap.get(KEY_APP_KEY);
        return appKey;
    }

    @RequestMapping(value = "/cdnPurgeDir", method = RequestMethod.GET)
    public BaseResponseVo<String> cdnPurgeDir(@RequestParam String urls) {
        String[] urlArray = urls.split(",");
        BaseResponseVo<String> responseVo =  new BaseResponseVo();
        for (String url : urlArray) {
            String res = openService.cdnPurgeDir(url);
            responseVo.setData(res);
        }
        responseVo.setIsSuccess(true);
        return responseVo;
    }

    @RequestMapping(value = "/cdnPurgeAllDir", method = RequestMethod.GET)
    public BaseResponseVo<Void> cdnPurgeAllDir() {
        EXECUTOR_SERVICE.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String res = openService.cdnPurgeDir(cdnDomain);
                    LOGGER.info("clear all dns, res = {}", res);
                } catch (Exception e) {
                    LOGGER.error("clear all dns error", e);
                }
            }
        });
        BaseResponseVo<Void> responseVo =  new BaseResponseVo();
        responseVo.setIsSuccess(true);
        return responseVo;
    }
}
