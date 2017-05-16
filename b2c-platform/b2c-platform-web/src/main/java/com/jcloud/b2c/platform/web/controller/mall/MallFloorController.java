/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: MallFloorController.java project: b2c-platform
 * @creator: lidongxing
 * @date: 2017/2/14
 */

package com.jcloud.b2c.platform.web.controller.mall;

import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.common.common.web.security.ControllerContext;
import com.jcloud.b2c.mall.service.client.enums.ChannelFieldEnum;
import com.jcloud.b2c.mall.service.client.enums.ClientTypeEnum;
import com.jcloud.b2c.mall.service.client.vo.MallAdVo;
import com.jcloud.b2c.mall.service.client.vo.MallFloorVo;
import com.jcloud.b2c.platform.service.feign.MallFloorClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description: 楼层控制
 * @author: lidongxing
 * @requireNo:
 * @createdate: 2017-02-14 20:11
 * @lastdate:
 */

@Controller
@RequestMapping("/mall/mallFloor")
public class MallFloorController {

    @Autowired
    private MallFloorClient mallFloorClient;

    @RequestMapping(value = "/list" ,method = RequestMethod.POST)
    public List<MallFloorVo> list(@RequestBody MallFloorVo mallFloorVo){
        List<MallFloorVo> list = mallFloorClient.query(mallFloorVo).getData();
        return list;
    }

    @ResponseBody
    @RequestMapping(value = "/queryPost" ,method = RequestMethod.GET)
    public MallFloorVo queryPost(@RequestParam Long id, @RequestParam Long tenantId){
        MallFloorVo mf = new MallFloorVo();
        mf.setId(121l);
        mf.setName("等级");
        MallFloorVo mallFloor = mallFloorClient.queryPost(mf).getData();
        return mallFloor;
    }

    @ResponseBody
    @RequestMapping(value = "/getFloor" ,method = RequestMethod.POST)
    public MallFloorVo get(@RequestParam Long id){
        Long tenantId = ControllerContext.getTenantId();
        MallFloorVo mallFloor = mallFloorClient.get(id, tenantId).getData();
        return mallFloor;
    }

    @ResponseBody
    @RequestMapping(value = "/updateFloor" ,method = RequestMethod.POST)
    public boolean update(MallFloorVo mallFloor){
        Long tenantId = ControllerContext.getTenantId();
        mallFloor.setTenantId(tenantId);
        boolean success = mallFloorClient.update(mallFloor).isSuccess();
        return success;
    }
    /**
     * 新建楼层
     * @param mallFloorVo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addFloor" ,method = {RequestMethod.POST})
    public boolean addFloor(MallFloorVo mallFloorVo){
        Long tenantId = ControllerContext.getTenantId();
        mallFloorVo.setTenantId(tenantId);
        Integer clientType = mallFloorVo.getClientType();
        mallFloorVo.setClientType(clientType==null?ClientTypeEnum.PC.getValue():clientType);
        boolean success = mallFloorClient.add(mallFloorVo).isSuccess();
        return success;
    }


    @ResponseBody
    @RequestMapping(value = "/deleteFloor" ,method = {RequestMethod.POST})
    public boolean deleteFloor(@RequestParam Long id){
        Long tenantId = ControllerContext.getTenantId();
        MallFloorVo mallFloorVo = new MallFloorVo();
        mallFloorVo.setTenantId(tenantId);
        mallFloorVo.setId(id);
        BaseResponseVo<Void> result = mallFloorClient.delete(mallFloorVo);
        return result.isSuccess();
    }
}
