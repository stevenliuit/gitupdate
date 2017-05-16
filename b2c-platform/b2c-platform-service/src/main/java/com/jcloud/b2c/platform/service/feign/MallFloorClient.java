/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: MallFloorClient.java project: jcloud-b2c-mall-web
 * @creator: lidongxing
 * @date: 2017/2/14
 */

package com.jcloud.b2c.platform.service.feign;

import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.mall.service.client.vo.MallAdVo;
import com.jcloud.b2c.mall.service.client.vo.MallFloorVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * @description: 楼层CLIENT
 * @author: lidongxing
 * @requireNo:
 * @createdate: 2017-02-14 17:50
 * @lastdate:
 */
@FeignClient(name = "b2c-mall-service")
public interface MallFloorClient {
    @RequestMapping(value = "/mallFloor/get", method = GET)
    BaseResponseVo<MallFloorVo> get(@RequestParam(value = "id") Long id, @RequestParam(value = "tenantId") Long tenantId);

    @RequestMapping(value = "/mallFloor/query", method = POST)
    BaseResponseVo<List<MallFloorVo>> query(@RequestBody MallFloorVo mallFloor);

    @RequestMapping(value = "/mallFloor/queryPost", method = POST)
    BaseResponseVo<MallFloorVo> queryPost(@RequestBody MallFloorVo mallFloor);

    @RequestMapping(value = "/mallFloor/update", method = POST)
    BaseResponseVo<Void> update(@RequestBody MallFloorVo mallFloor);

    @RequestMapping(value = "/mallFloor/add", method = POST)
    BaseResponseVo<Void> add(@RequestBody MallFloorVo mallFloor);

    @RequestMapping(value = "/mallFloor/delete", method = POST)
    BaseResponseVo<Void> delete(@RequestBody MallFloorVo mallFloor);
}
