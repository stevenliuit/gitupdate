/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: ItemClient.java project: jcloud-b2c-mall-web
 * @creator: lidongxing
 * @date: 2017/2/20
 */

package com.jcloud.b2c.platform.service.feign;

import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.item.client.vo.item.ItemBasicInfoVo;
import com.jcloud.b2c.item.client.vo.item.ItemDetailVo;
import com.jcloud.b2c.item.client.vo.item.SkuVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @description: 商品信息
 * @author: lidongxing
 * @requireNo:
 * @createdate: 2017-02-20 20:17
 * @lastdate:
 */

@FeignClient(name = "b2c-item-service")
public interface ItemClient {
    @RequestMapping(value = "/getItemDetail", method = GET)
    BaseResponseVo<ItemDetailVo> getItemDetail(@RequestParam(value = "tenantId") Long tenantId,
                                               @RequestParam(value = "shopId") Long shopId, @RequestParam(value = "skuId") Long skuId);

    @RequestMapping("/getItemBasicInfo")
    BaseResponseVo<ItemBasicInfoVo> getItemBasicInfo(@RequestParam(value = "tenantId") Long tenantId, @RequestParam(value = "shopId") Long shopId, @RequestParam(value = "skuId") Long skuId);

    @RequestMapping("/getItemDetailFromDB")
    BaseResponseVo<ItemDetailVo> getItemDetailFromDB(@RequestParam(value = "tenantId") Long tenantId,
                                               @RequestParam(value = "shopId") Long shopId, @RequestParam(value = "skuId") Long skuId);
    @RequestMapping("/getItemFromKpl")
    BaseResponseVo<List<String>> getItemFromKpl(@RequestParam(value = "tenantId") Long tenantId, @RequestParam(value = "skuId") Long skuId);

    @RequestMapping("/updateItemFromKpl")
    BaseResponseVo<Boolean> updateItemFromKpl(@RequestParam(value = "tenantId") Long tenantId, @RequestParam(value = "skuId") Long skuId);

    @RequestMapping("/getItemSkuList")
    BaseResponseVo<List<SkuVo>> getItemSkuList(@RequestParam(value = "tenantId") Long tenantId, @RequestParam(value = "shopId") Long shopId);
}
