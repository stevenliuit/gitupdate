/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: MallAdFloorClient.java project: b2c-platform
 * @creator: wangxin17
 * @date: 2017/2/25
 */
package com.jcloud.b2c.platform.service.feign;

import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.mall.service.client.vo.MallAdFloorVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @description:
 * @author: wangxin17
 * @requireNo:
 * @createdate: 2017-02-25 12:36
 * @lastdate:
 */
//@FeignClient(name = "b2c-mall-service-local")
@FeignClient(name = "b2c-mall-service")
public interface MallAdFloorClient {

    @RequestMapping(value = "/mallAdFloor/query" ,method = RequestMethod.GET)
    BaseResponseVo<List<MallAdFloorVo>> query(@RequestParam(value = "floorId") Long floorId, @RequestParam(value = "adType") Integer adType,
                                                   @RequestParam(value = "tenantId") Long tenantId);

    @RequestMapping(value = "/mallAdFloor/add" ,method = RequestMethod.POST)
    BaseResponseVo<Void> add(@RequestBody MallAdFloorVo mallAdFloor);

    @RequestMapping(value = "/mallAdFloor/delete" ,method = RequestMethod.POST)
    BaseResponseVo<Void> delete(@RequestBody MallAdFloorVo mallAdFloor);

    @RequestMapping(value = "/mallAdFloor/get" ,method = RequestMethod.GET)
    BaseResponseVo<MallAdFloorVo> getById(@RequestParam(value = "id") Long id, @RequestParam(value = "tenantId") Long tenantId);

    @RequestMapping(value = "/mallAdFloor/update" ,method = RequestMethod.POST)
    BaseResponseVo<Void> update(@RequestBody MallAdFloorVo mallAdFloor, @RequestParam(value = "beforeSort") Integer beforeSort);

    @RequestMapping(value = "/mallAdFloor/queryBySpecial" ,method = RequestMethod.POST)
    BaseResponseVo<List<MallAdFloorVo>> queryBySpecial(@RequestBody MallAdFloorVo mallAdFloor);

    @RequestMapping(value = "/mallAdFloor/addBySpecial" ,method = RequestMethod.POST)
    BaseResponseVo<Void> addBySpecial(@RequestBody MallAdFloorVo mallAdFloor);

    @RequestMapping(value = "/mallAdFloor/updateBySpecial" ,method = RequestMethod.POST)
    BaseResponseVo<Void> updateBySpecial(@RequestBody MallAdFloorVo mallAdFloor);

    @RequestMapping(value = "/mallAdFloor/deleteBySpecial" ,method = RequestMethod.POST)
    BaseResponseVo<Void> deleteBySpecial(@RequestBody MallAdFloorVo mallAdFloor);
}
