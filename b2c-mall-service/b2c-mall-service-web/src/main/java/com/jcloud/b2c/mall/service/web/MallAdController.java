/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: MallAdController.java project: jcloud-b2c-mall-service
 * @creator: lidongxing
 * @date: 2017/2/16
 */

package com.jcloud.b2c.mall.service.web;

import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.mall.service.domain.MallAd;
import com.jcloud.b2c.mall.service.service.MallAdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description: 广告
 * @author: lidongxing
 * @requireNo:
 * @createdate: 2017-02-16 15:16
 * @lastdate:
 */

@RestController
@RequestMapping("/mallAd")
public class MallAdController {
    @Autowired
    private MallAdService mallAdService;

    @RequestMapping(value = "/get" ,method = RequestMethod.GET)
    public BaseResponseVo<MallAd> getById(@RequestParam(value="id") Long id, @RequestParam(value="tenantId") Long tenantId){
        BaseResponseVo<MallAd> responseVo = new BaseResponseVo<MallAd>();
        MallAd mallAd = mallAdService.get(id, tenantId);
        responseVo.setIsSuccess(true);
        responseVo.setData(mallAd);
        return responseVo;
    }

    @RequestMapping(value = "/query" ,method = RequestMethod.POST)
    public BaseResponseVo<List<MallAd>> query(@RequestBody MallAd mallAd, @RequestParam String principalType,
                                              @RequestParam Long principalId){
        BaseResponseVo<List<MallAd>> responseVo = new BaseResponseVo<List<MallAd>>();
        List<MallAd> list = mallAdService.query(mallAd, principalType, principalId);
        responseVo.setIsSuccess(true);
        responseVo.setData(list);
        return responseVo;
    }

    @RequestMapping(value = "/update" ,method = RequestMethod.POST)
    public BaseResponseVo<Void> update(@RequestBody MallAd mallAd, @RequestParam String principalType,
                                       @RequestParam Long principalId, @RequestParam Integer principalSort){
        BaseResponseVo<Void> responseVo = new BaseResponseVo<Void>();
        boolean success = mallAdService.update(mallAd, principalType, principalId, principalSort);
        responseVo.setIsSuccess(success);
        return responseVo;
    }

    @RequestMapping(value = "/add" ,method = RequestMethod.POST)
    public BaseResponseVo<Void> add(@RequestBody MallAd mallAd, @RequestParam String principalType,
                                    @RequestParam Long principalId, @RequestParam Integer principalSort){
        BaseResponseVo<Void> responseVo = new BaseResponseVo<Void>();
        boolean success = mallAdService.add(mallAd, principalType, principalId, principalSort);
        responseVo.setIsSuccess(success);
        return responseVo;
    }

    @RequestMapping(value = "/delete" ,method = RequestMethod.POST)
    public BaseResponseVo<Void> delete(@RequestBody MallAd mallAd, @RequestParam String principalType,
                                       @RequestParam Long principalId){
        BaseResponseVo<Void> responseVo = new BaseResponseVo<Void>();
        boolean success = mallAdService.delete(mallAd, principalType, principalId);
        responseVo.setIsSuccess(success);
        return responseVo;
    }

    /**
     * 直接查询mall
     */
    @RequestMapping(value = "/updateByMall" ,method = RequestMethod.POST)
    public BaseResponseVo<Void> updateByMall(@RequestBody MallAd mallAd){
        BaseResponseVo<Void> responseVo = new BaseResponseVo<Void>();
        boolean success = mallAdService.update(mallAd);
        responseVo.setIsSuccess(success);
        return responseVo;
    }

    @RequestMapping(value = "/addByMall" ,method = RequestMethod.POST)
    public BaseResponseVo<Void> addByMall(@RequestBody MallAd mallAd){
        BaseResponseVo<Void> responseVo = new BaseResponseVo<Void>();
        boolean success = mallAdService.add(mallAd);
        responseVo.setIsSuccess(success);
        return responseVo;
    }

    @RequestMapping(value = "/deleteByMall" ,method = RequestMethod.POST)
    public BaseResponseVo<Void> deleteByMall(@RequestBody MallAd mallAd){
        BaseResponseVo<Void> responseVo = new BaseResponseVo<Void>();
        boolean success = mallAdService.delete(mallAd);
        responseVo.setIsSuccess(success);
        return responseVo;
    }

    @RequestMapping(value = "/queryByMall" ,method = RequestMethod.POST)
    public BaseResponseVo<List<MallAd>> queryByMall(@RequestBody MallAd mallAd){
        BaseResponseVo<List<MallAd>> responseVo = new BaseResponseVo<List<MallAd>>();
        List<MallAd> list = mallAdService.query(mallAd);
        responseVo.setIsSuccess(true);
        responseVo.setData(list);
        return responseVo;
    }
}
