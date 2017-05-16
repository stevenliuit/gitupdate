/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: MallBannerController.java project: jcloud-b2c-mall-service
 * @creator: lidongxing
 * @date: 2017/2/15
 */

package com.jcloud.b2c.mall.service.web;

import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.mall.service.client.vo.MallBannerVo;
import com.jcloud.b2c.mall.service.domain.MallBanner;
import com.jcloud.b2c.mall.service.service.MallBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description: 轮播图+广告位CONTROLLER
 * @author: lidongxing
 * @requireNo:
 * @createdate: 2017-02-15 18:00
 * @lastdate:
 */

@RestController
@RequestMapping("/mallBanner")
public class MallBannerController {

    @Autowired
    private MallBannerService mallBannerService;

    @RequestMapping(value = "/get" ,method = RequestMethod.GET)
    public BaseResponseVo<MallBanner> getById(@RequestParam Long id, @RequestParam Long tenantId){
        BaseResponseVo<MallBanner> responseVo = new BaseResponseVo<MallBanner>();
        MallBanner mallBanner = mallBannerService.get(id, tenantId);
        responseVo.setIsSuccess(true);
        responseVo.setData(mallBanner);
        return responseVo;
    }

    @RequestMapping(value = "/query" ,method = RequestMethod.POST)
    public BaseResponseVo<List<MallBanner>> query(@RequestBody MallBanner mallBanner){
        BaseResponseVo<List<MallBanner>> responseVo = new BaseResponseVo<List<MallBanner>>();
        List<MallBanner> list = mallBannerService.query(mallBanner);
        responseVo.setIsSuccess(true);
        responseVo.setData(list);
        return responseVo;
    }

    @RequestMapping(value = "/update" ,method = RequestMethod.POST)
    public BaseResponseVo<Void> update(@RequestBody MallBanner mallBanner){
        BaseResponseVo<Void> responseVo = new BaseResponseVo<Void>();
        boolean success = mallBannerService.update(mallBanner);
        responseVo.setIsSuccess(success);
        return responseVo;
    }

    @RequestMapping(value = "/add" ,method = RequestMethod.POST)
    public BaseResponseVo<Void> add(@RequestBody MallBanner mallBanner){
        BaseResponseVo<Void> responseVo = new BaseResponseVo<Void>();
    boolean success = mallBannerService.add(mallBanner);
        responseVo.setIsSuccess(success);
        return responseVo;
}

    @RequestMapping(value = "/delete" ,method = RequestMethod.POST)
    public BaseResponseVo<Void> delete(@RequestBody MallBanner mallBanner){
        BaseResponseVo<Void> responseVo = new BaseResponseVo<Void>();
        boolean success = mallBannerService.delete(mallBanner);
        responseVo.setIsSuccess(success);
        return responseVo;
    }

    @RequestMapping(value = "/queryWithSkuId" ,method = RequestMethod.POST)
    public BaseResponseVo<MallBannerVo> queryWithSkuId(@RequestBody MallBanner mallBanner){
        BaseResponseVo<MallBannerVo> responseVo = new BaseResponseVo<MallBannerVo>();
        MallBannerVo data = mallBannerService.queryWithSkuId(mallBanner);
        responseVo.setIsSuccess(true);
        responseVo.setData(data);
        return responseVo;
    }

    @RequestMapping(value = "/queryByFlash" ,method = RequestMethod.POST)
    public BaseResponseVo<MallBanner> queryByFlash(@RequestBody MallBanner mallBanner){
        BaseResponseVo<MallBanner> responseVo = new BaseResponseVo<MallBanner>();
        MallBanner data = mallBannerService.queryByFlash(mallBanner);
        responseVo.setIsSuccess(true);
        responseVo.setData(data);
        return responseVo;
    }

    @RequestMapping(value = "/checkByBeginTime" ,method = RequestMethod.POST)
    public BaseResponseVo<Void> checkByBeginTime(@RequestBody MallBanner mallBanner){
        BaseResponseVo<Void> responseVo = new BaseResponseVo<Void>();
        boolean success = mallBannerService.checkByBeginTime(mallBanner);
        responseVo.setIsSuccess(success);
        return responseVo;
    }
}
