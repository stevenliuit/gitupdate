/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: MallCategoryClient.java project: jcloud-b2c-platform
 * @creator: lidongxing
 * @date: 2017/2/16
 */

package com.jcloud.b2c.platform.service.feign;

import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.mall.service.client.vo.MallCategoryVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * @description: 类目CLIENT
 * @author: lidongxing
 * @requireNo:
 * @createdate: 2017-02-16 14:55
 * @lastdate:
 */
//@FeignClient(name="b2c-mall-service-local", url = "http://localhost:8083")
@FeignClient(name="b2c-mall-service")
public interface MallCategoryClient {
    @RequestMapping(value = "/mallCategory/get", method = GET)
    BaseResponseVo<MallCategoryVo> get(@RequestParam(value = "id") Long id, @RequestParam(value = "tenantId") Long tenantId);

    @RequestMapping(value = "/mallCategory/query", method = POST)
    BaseResponseVo<List<MallCategoryVo>> query(@RequestBody MallCategoryVo mallCategory);

    @RequestMapping(value = "/mallCategory/update", method = POST)
    BaseResponseVo<Void> update(@RequestBody MallCategoryVo mallCategory, @RequestParam(value = "beforeSort") Integer beforeSort);

    @RequestMapping(value = "/mallCategory/add", method = POST)
    BaseResponseVo<Void> add(@RequestBody MallCategoryVo mallCategory);

    @RequestMapping(value = "/mallCategory/delete", method = POST)
    BaseResponseVo<Void> delete(@RequestBody MallCategoryVo mallCategory);
    @RequestMapping(value = "/mallCategory/isUniqueName", method = POST)
    Boolean isUniqueName(@RequestBody MallCategoryVo mallCategoryVo);
    @RequestMapping(value = "/mallCategory/move", method = POST)
    BaseResponseVo<Void> moveCate(@RequestBody MallCategoryVo mallCategoryVo, @RequestParam(value = "action") String action);
    @RequestMapping(value = "/mallCategory/verifySortNum", method = POST)
    Boolean verifySortNum(@RequestBody MallCategoryVo mallCategory);
    @RequestMapping(value = "/mallCategory/updateMoveUp", method = POST)
    BaseResponseVo updateMoveUp(@RequestBody MallCategoryVo mallCategory, @RequestParam(value = "action") String action);
}
