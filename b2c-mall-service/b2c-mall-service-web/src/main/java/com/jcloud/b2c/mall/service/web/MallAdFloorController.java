package com.jcloud.b2c.mall.service.web;

import com.fasterxml.jackson.databind.deser.Deserializers;
import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.mall.service.domain.MallAd;
import com.jcloud.b2c.mall.service.domain.MallAdChannel;
import com.jcloud.b2c.mall.service.domain.MallAdFloor;
import com.jcloud.b2c.mall.service.service.MallAdFloorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description:
 * @author: YueZheng
 * @requireNo:
 * @createdate: 2017/2/21 10:56
 * @lasdate
 */
@RestController
@RequestMapping("/mallAdFloor")
public class MallAdFloorController {

    @Autowired
    private MallAdFloorService mallAdFloorService;

    @RequestMapping(value = "/query" ,method = RequestMethod.GET)
    public BaseResponseVo<List<MallAdFloor>> query(@RequestParam Long floorId, @RequestParam Integer adType,
                                                   @RequestParam Long tenantId) {
        BaseResponseVo<List<MallAdFloor>> responseVo = new BaseResponseVo<List<MallAdFloor>>();
        List<MallAdFloor> list = mallAdFloorService.query(floorId, adType, tenantId);
        responseVo.setIsSuccess(true);
        responseVo.setData(list);
        return responseVo;
    }

    @RequestMapping(value = "/add" ,method = RequestMethod.POST)
    public BaseResponseVo<Void> add(@RequestBody MallAdFloor mallAdFloor) {
        BaseResponseVo<Void> responseVo = new BaseResponseVo<Void>();
        boolean success = mallAdFloorService.add(mallAdFloor);
        responseVo.setIsSuccess(success);
        return responseVo;
    }

    @RequestMapping(value = "/delete" ,method = RequestMethod.POST)
    public BaseResponseVo<Void> delete(@RequestBody MallAdFloor mallAdFloor){
        BaseResponseVo<Void> responseVo = new BaseResponseVo<Void>();
        boolean success = mallAdFloorService.delete(mallAdFloor);
        responseVo.setIsSuccess(success);
        return responseVo;
    }

    @RequestMapping(value = "/get" ,method = RequestMethod.GET)
    public BaseResponseVo<MallAdFloor> getById(@RequestParam Long id, @RequestParam Long tenantId){
        BaseResponseVo<MallAdFloor> responseVo = new BaseResponseVo<MallAdFloor>();
        MallAdFloor mallAdFloor = mallAdFloorService.get(id, tenantId);
        responseVo.setIsSuccess(true);
        responseVo.setData(mallAdFloor);
        return responseVo;
    }

    @RequestMapping(value = "/update" ,method = RequestMethod.POST)
    public BaseResponseVo<Void> update(@RequestBody MallAdFloor mallAdFloor, @RequestParam Integer beforeSort){
        BaseResponseVo<Void> responseVo = new BaseResponseVo<Void>();
        boolean success = mallAdFloorService.update(mallAdFloor, beforeSort);
        responseVo.setIsSuccess(success);
        return responseVo;
    }

    /**
     * 查询专题
     */
    @RequestMapping(value = "/queryBySpecial" ,method = RequestMethod.POST)
    public BaseResponseVo<List<MallAdFloor>> queryBySpecial(@RequestBody MallAdFloor mallAdFloor) {
        BaseResponseVo<List<MallAdFloor>> responseVo = new BaseResponseVo<List<MallAdFloor>>();
        List<MallAdFloor> list = mallAdFloorService.queryBySpecial(mallAdFloor);
        responseVo.setIsSuccess(true);
        responseVo.setData(list);
        return responseVo;
    }

    @RequestMapping(value = "/addBySpecial", method = RequestMethod.POST)
    public BaseResponseVo<Void> addBySpecial(@RequestBody MallAdFloor mallAdFloor){
        BaseResponseVo<Void> responseVo = new BaseResponseVo<>();
        boolean success = mallAdFloorService.addBySpecial(mallAdFloor);
        responseVo.setIsSuccess(success);
        return responseVo;
    }

    @RequestMapping(value = "/updateBySpecial", method = RequestMethod.POST)
    public BaseResponseVo<Void> updateBySpecial(@RequestBody MallAdFloor mallAdFloor){
        BaseResponseVo<Void> responseVo = new BaseResponseVo<>();
        boolean success = mallAdFloorService.updateBySpecial(mallAdFloor);
        responseVo.setIsSuccess(success);
        return responseVo;
    }

    @RequestMapping(value = "/deleteBySpecial", method = RequestMethod.POST)
    public BaseResponseVo<Void> deleteBySpecial(@RequestBody MallAdFloor mallAdFloor){
        BaseResponseVo<Void> responseVo = new BaseResponseVo<>();
        boolean success = mallAdFloorService.deleteBySpecial(mallAdFloor);
        responseVo.setIsSuccess(success);
        return responseVo;
    }
}
