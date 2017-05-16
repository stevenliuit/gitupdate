package com.jcloud.b2c.platform.web.controller.mall;

import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.common.common.web.security.ControllerContext;

import com.jcloud.b2c.mall.service.client.enums.StateEnum;
import com.jcloud.b2c.mall.service.client.vo.MallActiveVo;


import com.jcloud.b2c.platform.service.feign.MallActiveClient;
import com.jcloud.b2c.platform.service.feign.OssClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * liuhao
 * Created by issuser on 2017/3/3.
 * 活动页
 */
@Controller
@RequestMapping("/mall/mallActive")
public class MallActiveController {

    @Autowired
    private MallActiveClient mallActiveClient;

    @Autowired
    private OssClient ossClient;



    @RequestMapping(value = "/query" ,method = {RequestMethod.GET, RequestMethod.POST})
    public String queryActiveList(ModelMap map, @RequestParam Integer clientType){
        Long tenantId = ControllerContext.getTenantId();
        Integer state= StateEnum.ON_SHELF.getValue();
        MallActiveVo mallActiveVo=new MallActiveVo();
        mallActiveVo.setTenantId(tenantId);
        mallActiveVo.setState(state);
        mallActiveVo.setClientType(clientType);
        BaseResponseVo<List<MallActiveVo>> mallActiveVoList=mallActiveClient.queryActiveList(mallActiveVo);
        map.put("baseResponseVo",mallActiveVoList);
        return "mall/active/active";
    }


    @RequestMapping(value = "/add" ,method = {RequestMethod.POST})
    @ResponseBody
    public BaseResponseVo addActive(MallActiveVo mallActiveVo){
        Long tenantId = ControllerContext.getTenantId();
        Integer state= StateEnum.ON_SHELF.getValue();
        mallActiveVo.setTenantId(tenantId);
        mallActiveVo.setState(state);
        if (mallActiveVo.getIsHead() == null){
            mallActiveVo.setIsHead(0);
        }else {
            mallActiveVo.setIsHead(1);
        }
        BaseResponseVo<Void> responseVo =mallActiveClient.addActive(mallActiveVo);
        return responseVo;
    }


    @RequestMapping(value = "/previewActive" ,method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ModelAndView previewActive(@RequestParam String contentUrl){
        String savePath =contentUrl;
        Long platformId = ControllerContext.getTenantId();
        String htmlStr=ossClient.getTxtContent(platformId,savePath);
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("html",htmlStr);
        modelAndView.setViewName("/mall/active/previewActive");
        return modelAndView;
    }

    @RequestMapping(value = "/showUpdateActive" ,method = {RequestMethod.POST})
    @ResponseBody
    public BaseResponseVo<MallActiveVo> showUpdateActive(@RequestParam Long id,@RequestParam String code){
        Long tenantId = ControllerContext.getTenantId();
        MallActiveVo mallActiveVo= new MallActiveVo();
        mallActiveVo.setId(id);
        mallActiveVo.setTenantId(tenantId);
        mallActiveVo.setCode(code);
        BaseResponseVo<MallActiveVo> baseResponseVo=mallActiveClient.getByCode(mallActiveVo);
        return baseResponseVo;
    }

    @RequestMapping(value = "/update" ,method = {RequestMethod.POST})
    @ResponseBody
    public BaseResponseVo update(MallActiveVo mallActiveVo){
        Long tenantId = ControllerContext.getTenantId();
        mallActiveVo.setTenantId(tenantId);
        if (mallActiveVo.getIsHead() == null){
            mallActiveVo.setIsHead(0);
        }else {
            mallActiveVo.setIsHead(1);
        }
        BaseResponseVo<Void> responseVo =mallActiveClient.update(mallActiveVo);
        return responseVo;
    }

    @RequestMapping(value = "/delete" ,method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public BaseResponseVo delete(@RequestParam Long id){
        MallActiveVo mallActiveVo=new MallActiveVo();
        Long tenantId = ControllerContext.getTenantId();
        mallActiveVo.setTenantId(tenantId);
        mallActiveVo.setId(id);
        BaseResponseVo<Void> responseVo =mallActiveClient.delete(mallActiveVo);
        return responseVo;
    }



}
