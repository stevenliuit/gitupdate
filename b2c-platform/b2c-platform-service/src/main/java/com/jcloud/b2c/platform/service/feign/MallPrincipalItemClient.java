/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: MallPrincipalItemClient.java project: jcloud-b2c-platform
 * @creator: lidongxing
 * @date: 2017/3/2
 */

package com.jcloud.b2c.platform.service.feign;

import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.mall.service.client.vo.MallPrincipalItemVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * @description: MALL PRINCINPAL ITEM CLIENT 
 * @author: lidongxing
 * @requireNo:
 * @createdate: 2017-03-02 16:01
 * @lastdate:
 */

@FeignClient(name="b2c-mall-service")
//@FeignClient(name="b2c-mall-service")
public interface MallPrincipalItemClient {
    @RequestMapping(value = "/mallPrincipalItem/get", method = GET)
    BaseResponseVo<MallPrincipalItemVo> get(@RequestParam(value = "id") Long id, @RequestParam(value = "tenantId") Long tenantId);

    @RequestMapping(value = "/mallPrincipalItem/query", method = POST)
    BaseResponseVo<List<MallPrincipalItemVo>> query(@RequestBody MallPrincipalItemVo mallPrincipalItem);

    @RequestMapping(value = "/mallPrincipalItem/queryPage", method = POST)
    BaseResponseVo<List<MallPrincipalItemVo>> queryPage(@RequestBody MallPrincipalItemVo mallPrincipalItem);

    @RequestMapping(value = "/mallPrincipalItem/update", method = POST)
    BaseResponseVo<Void> update(@RequestBody MallPrincipalItemVo mallPrincipalItem, @RequestParam(value = "beforeSort") Integer beforeSort);

    @RequestMapping(value = "/mallPrincipalItem/add", method = POST)
    BaseResponseVo<Long> add(@RequestBody MallPrincipalItemVo mallPrincipalItem);

    @RequestMapping(value = "/mallPrincipalItem/delete", method = POST)
    BaseResponseVo<Void> delete(@RequestBody MallPrincipalItemVo mallPrincipalItem);

    @RequestMapping(value = "/mallPrincipalItem/addBatch", method = POST)
    BaseResponseVo<Void> addBatch(@RequestBody List<MallPrincipalItemVo> mallPrincipalItemList);

    @RequestMapping(value = "/mallPrincipalItem/isBindItem" ,method = RequestMethod.GET)
    public BaseResponseVo<Boolean> isBindItem(@RequestParam(value = "skuId") Long skuId, @RequestParam(value = "tenantId") Long tenantId);

    @RequestMapping(value = "/mallPrincipalItem/isBindItems", method = RequestMethod.POST)
    BaseResponseVo<Map<Long,Boolean>> isBindItems(@RequestParam(value = "tenantId") Long tenantId, @RequestParam(value = "shopId") Long shopId, @RequestBody List<Long> skuIds);

    @RequestMapping(value = "/mallPrincipalItem/batchDelete", method = RequestMethod.POST)
    BaseResponseVo<Integer> batchDelete(@RequestParam(value = "tenantId") Long tenantId, @RequestParam(value = "skuIds") String skuIds);

    @RequestMapping(value = "/mallPrincipalItem/queryDistinctItemIds", method = RequestMethod.GET)
    BaseResponseVo<List<Long>> queryDistinctItemIds(@RequestParam(value = "tenantId")Long tenantId, @RequestParam(value = "pageIndex")int pageIndex, @RequestParam(value = "pageSize")int pageSize);

    @RequestMapping(value = "/mallPrincipalItem/countDistinctItemId", method = RequestMethod.GET)
    BaseResponseVo<Long> countDistinctItemId(@RequestParam(value = "tenantId")Long tenantId);
}
