/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: EsItemClient.java project: jcloud-b2c-platform
 * @creator: lidongxing
 * @date: 2017/3/3
 */

package com.jcloud.b2c.platform.service.feign;

import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.mall.service.client.vo.EsItemVo;
import com.jcloud.b2c.mall.service.client.vo.MallAdCategoryVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @description: ES ITEM CLIENT  
 * @author: lidongxing
 * @requireNo:
 * @createdate: 2017-03-03 15:25
 * @lastdate:
 */

@FeignClient(name = "b2c-mall-service")
public interface EsItemClient {
    @RequestMapping(value = "/esItem/putIntoEsTask" ,method = RequestMethod.POST)
    BaseResponseVo<Void> putIntoEsTask(@RequestBody List<Long> itemIds, @RequestParam("tenantId") Long tenantId);
    @RequestMapping(value = "/esItem/batchPutIntoEs" ,method = RequestMethod.GET)
    BaseResponseVo<Void> batchPutIntoEs(@RequestParam(value = "itemIds") List<Long> itemIds, @RequestParam("tenantId") Long tenantId);
}
