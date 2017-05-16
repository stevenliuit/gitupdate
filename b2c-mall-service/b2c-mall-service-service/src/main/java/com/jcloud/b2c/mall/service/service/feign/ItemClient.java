/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: ItemClient.java project: jcloud-b2c-mall-service
 * @creator: wangxin17
 * @date: 2017/3/1
 */
package com.jcloud.b2c.mall.service.service.feign;

import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.item.client.vo.item.*;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @description:
 * @author: wangxin17
 * @requireNo:
 * @createdate: 2017-03-01 18:11
 * @lastdate:
 */
@FeignClient(name = "B2C-ITEM-SERVICE")
public interface ItemClient {

    @RequestMapping("/getNewSkuList")
    BaseResponseVo<List<MallSkuListVo>> getNewSkuList(@RequestBody MallQueryNewSkuListCriteria criteria);

    @RequestMapping("/getItemDetail")
    BaseResponseVo<ItemDetailVo> getItemDetail(@RequestParam(value = "tenantId") Long tenantId, @RequestParam(value = "shopId") Long shopId, @RequestParam(value = "skuId") Long skuId);

    @RequestMapping("/getItemBasicInfo")
    BaseResponseVo<ItemBasicInfoVo> getItemBasicInfo(@RequestParam(value = "tenantId") Long tenantId, @RequestParam(value = "shopId") Long shopId, @RequestParam(value = "skuId") Long skuId);

    @RequestMapping("/findItemsPrice")
    BaseResponseVo<List<ItemPriceVo>> findItemsPrice(@RequestParam(value = "tenantId")Long tenantId, @RequestParam(value = "shopId")Long shopId, @RequestParam(value = "skuIds")String skuIds, @RequestParam(value = "area")String area);

    @RequestMapping("/findItemStock")
    BaseResponseVo<String> findItemStock(@RequestParam(value = "tenantId")Long tenantId, @RequestParam(value = "shopId")Long shopId, @RequestParam(value = "skuId")Long skuId, @RequestParam(value = "area")String area);
}
