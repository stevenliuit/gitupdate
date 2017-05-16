/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: EsItemController.java project: b2c-platform
 * @creator: wangxin17
 * @date: 2017/4/10
 */
package com.jcloud.b2c.platform.web.controller.superAdmin;

import com.jcloud.b2c.platform.service.feign.EsItemClient;
import com.jcloud.b2c.platform.service.feign.MallPrincipalItemClient;
import com.jcloud.b2c.platform.web.controller.mall.MallAdController;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @description:
 * @author: wangxin17
 * @requireNo:
 * @createdate: 2017-04-10 17:33
 * @lastdate:
 */
@Controller
@RequestMapping("/super/esItem")
public class EsItemController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EsItemController.class);

    @Resource
    private MallPrincipalItemClient mallPrincipalItemClient;

    @Resource
    private EsItemClient esItemClient;

    private static ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(10);

    @ResponseBody
    @RequestMapping(value = "/updateAll",method = {RequestMethod.GET})
    public Map<String, Object> updateAll(@RequestParam Long tenantId) {
        long count = mallPrincipalItemClient.countDistinctItemId(tenantId).getData();
        int pageSize = 200;
        long totalPage = count % pageSize == 0 ? count / pageSize : (count / pageSize) + 1;
        int curPage = 1;
        while (totalPage >= curPage) {
            List<Long> itemIds =  mallPrincipalItemClient.queryDistinctItemIds(tenantId, (curPage - 1) * pageSize, pageSize).getData();

            EXECUTOR_SERVICE.submit(()-> {
                LOGGER.info("{}===================:{}", Thread.currentThread().getName(), itemIds.size());
                esItemClient.batchPutIntoEs(itemIds, tenantId);
            });
            curPage += 1;
        }
        Map<String, Object> result = new HashedMap();
        result.put("success", true);
        result.put("count", count);
        return result;
    }
}
