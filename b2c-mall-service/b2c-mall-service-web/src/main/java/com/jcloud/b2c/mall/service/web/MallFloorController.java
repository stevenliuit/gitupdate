/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: MallFloorController.java project: jcloud-b2c-mall-service
 * @creator: lidongxing
 * @date: 2017/2/14
 */

package com.jcloud.b2c.mall.service.web;

import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.mall.service.domain.MallAd;
import com.jcloud.b2c.mall.service.domain.MallFloor;
import com.jcloud.b2c.mall.service.service.MallFloorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description: 楼层控制
 * @author: lidongxing
 * @requireNo:
 * @createdate: 2017-02-14 16:31
 * @lastdate:
 */

@RestController
@RequestMapping("/mallFloor")
public class MallFloorController {

    @Autowired
    private MallFloorService mallFloorService;

    @RequestMapping(value = "/get" ,method = RequestMethod.GET)
    public BaseResponseVo<MallFloor> getById(@RequestParam Long id, @RequestParam Long tenantId){
        BaseResponseVo<MallFloor> responseVo = new BaseResponseVo<MallFloor>();
        MallFloor mallFloor = mallFloorService.get(id, tenantId);
        responseVo.setIsSuccess(true);
        responseVo.setData(mallFloor);
        return responseVo;
    }

    @RequestMapping(value = "/query" ,method = RequestMethod.POST)
    public BaseResponseVo<List<MallFloor>> query(@RequestBody MallFloor mallFloor){
        BaseResponseVo<List<MallFloor>> responseVo = new BaseResponseVo<List<MallFloor>>();
        List<MallFloor> list = mallFloorService.query(mallFloor);
        responseVo.setIsSuccess(true);
        responseVo.setData(list);
        return responseVo;
    }

    @RequestMapping(value = "/queryPost" ,method = RequestMethod.POST)
    public BaseResponseVo<MallFloor> queryPost(@RequestBody MallFloor mallFloor){
        BaseResponseVo<MallFloor> responseVo = new BaseResponseVo<MallFloor>();
        List<MallFloor> mf = mallFloorService.query(mallFloor);
        responseVo.setIsSuccess(true);
        responseVo.setData(mallFloor);
        return responseVo;
    }

    @RequestMapping(value = "/update" ,method = RequestMethod.POST)
    public BaseResponseVo<Void> update(@RequestBody MallFloor mallFloor){
        BaseResponseVo<Void> responseVo = new BaseResponseVo<Void>();
        boolean success = mallFloorService.update(mallFloor);
        responseVo.setIsSuccess(success);
        return responseVo;
    }

    @RequestMapping(value = "/add" ,method = RequestMethod.POST)
    public BaseResponseVo<Void> add(@RequestBody MallFloor mallFloor){
        BaseResponseVo<Void> responseVo = new BaseResponseVo<Void>();
        boolean success = mallFloorService.add(mallFloor);
        responseVo.setIsSuccess(success);
        return responseVo;
    }

    @RequestMapping(value = "/delete" ,method = RequestMethod.POST)
    public BaseResponseVo<Void> delete(@RequestBody MallFloor mallFloor){
        BaseResponseVo<Void> responseVo = new BaseResponseVo<Void>();
        boolean success = mallFloorService.delete(mallFloor);
        responseVo.setIsSuccess(success);
        return responseVo;
    }
}
