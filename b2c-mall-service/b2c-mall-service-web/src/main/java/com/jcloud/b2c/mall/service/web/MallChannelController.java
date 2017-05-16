/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: MallChannelController.java project: jcloud-b2c-mall-service
 * @creator: lidongxing
 * @date: 2017/2/15
 */

package com.jcloud.b2c.mall.service.web;

import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.mall.service.domain.MallChannel;
import com.jcloud.b2c.mall.service.service.MallChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description: 频道CONTROLLER
 * @author: lidongxing
 * @requireNo:
 * @createdate: 2017-02-15 18:02
 * @lastdate:
 */

@RestController
@RequestMapping("/mallChannel")
public class MallChannelController {

    @Autowired
    private MallChannelService mallChannelService;

    @RequestMapping(value = "/get" ,method = RequestMethod.GET)
    public BaseResponseVo<MallChannel> getById(@RequestParam Long id, @RequestParam Long tenantId){
        BaseResponseVo<MallChannel> responseVo = new BaseResponseVo<MallChannel>();
        MallChannel mallChannel = mallChannelService.get(id, tenantId);
        responseVo.setIsSuccess(true);
        responseVo.setData(mallChannel);
        return responseVo;
    }

    @RequestMapping(value = "/query" ,method = RequestMethod.POST)
    public BaseResponseVo<List<MallChannel>> query(@RequestBody MallChannel mallChannel){
        BaseResponseVo<List<MallChannel>> responseVo = new BaseResponseVo<List<MallChannel>>();
        List<MallChannel> list = mallChannelService.query(mallChannel);
        responseVo.setIsSuccess(true);
        responseVo.setData(list);
        return responseVo;
    }

    @RequestMapping(value = "/update" ,method = RequestMethod.POST)
    public BaseResponseVo<Void> update(@RequestBody MallChannel mallChannel){
        BaseResponseVo<Void> responseVo = new BaseResponseVo<Void>();
        boolean success = mallChannelService.update(mallChannel);
        responseVo.setIsSuccess(success);
        return responseVo;
    }

    @RequestMapping(value = "/add" ,method = RequestMethod.POST)
    public BaseResponseVo<Void> add(@RequestBody MallChannel mallChannel){
        BaseResponseVo<Void> responseVo = new BaseResponseVo<Void>();
        boolean success = mallChannelService.add(mallChannel);
        responseVo.setIsSuccess(success);
        return responseVo;
    }

    @RequestMapping(value = "/delete" ,method = RequestMethod.POST)
    public BaseResponseVo<Void> delete(@RequestBody MallChannel mallChannel){
        BaseResponseVo<Void> responseVo = new BaseResponseVo<Void>();
        boolean success = mallChannelService.delete(mallChannel);
        responseVo.setIsSuccess(success);
        return responseVo;
    }
}
