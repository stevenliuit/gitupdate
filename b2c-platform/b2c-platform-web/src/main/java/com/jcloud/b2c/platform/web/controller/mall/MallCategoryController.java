/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: MallCategoryController.java project: jcloud-b2c-platform
 * @creator: lidongxing
 * @date: 2017/2/25
 */

package com.jcloud.b2c.platform.web.controller.mall;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.common.common.web.security.ControllerContext;
import com.jcloud.b2c.mall.service.client.enums.AdPrincipalTypeEnum;
import com.jcloud.b2c.mall.service.client.enums.ClientTypeEnum;
import com.jcloud.b2c.mall.service.client.enums.StateEnum;
import com.jcloud.b2c.mall.service.client.vo.MallCategoryVo;
import com.jcloud.b2c.mall.service.client.vo.MallPrincipalItemVo;
import com.jcloud.b2c.platform.domain.PageTypeEnum;
import com.jcloud.b2c.platform.service.feign.MallCategoryClient;
import com.jcloud.b2c.platform.service.feign.MallPrincipalItemClient;
import com.jcloud.b2c.platform.service.mall.MallCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: MALL CATEGORY CONTROLLER 
 * @author: lidongxing
 * @requireNo:
 * @createdate: 2017-02-25 12:34
 * @lastdate:
 */

@Controller
@RequestMapping("/mall/category")
public class MallCategoryController {

    @Autowired
    MallCategoryClient mallCategoryClient;

    @Autowired
    MallPrincipalItemClient mallPrincipalItemClient;

    @Autowired
    private MallCategoryService mallCategoryService;
    
    @RequestMapping(value = "/main/{clientType}/{channelId}" ,method = RequestMethod.GET)
    public ModelAndView list(HttpServletRequest request, @PathVariable(value = "clientType") String clientType,
                             @PathVariable(value = "channelId") Long channelId){
        ClientTypeEnum ct = ClientTypeEnum.getClientTypeByName(clientType);
        if(ct == null || (ct!=ClientTypeEnum.PC && ct!=ClientTypeEnum.ANDROID) ){
            return null;
        }
        channelId = channelId==null?0L:channelId;
        PageTypeEnum pageType = PageTypeEnum.INDEX;
        if(channelId>0L){
            pageType = PageTypeEnum.CHANNEL;
        }

        //ModelAndView modelAndView = new ModelAndView("mall/category/"+ct.getName().toLowerCase()+"_"+pageType.getName()+"_main");
        ModelAndView modelAndView = new ModelAndView("mall/category/pc_index_main");
        MallCategoryVo mallCategoryVo = new MallCategoryVo();
        mallCategoryVo.setTenantId(ControllerContext.getTenantId());
        mallCategoryVo.setClientType(ct.getValue());
        mallCategoryVo.setChannelId(channelId);
        Map<String, MallCategoryVo> catMap = mallCategoryService.getCategoryMap(mallCategoryVo);
        modelAndView.addObject("catMap", JSON.toJSONString(catMap));
        modelAndView.addObject("maxLevel", mallCategoryService.getCategoryMaxLevel(ct, pageType));
        modelAndView.addObject("clientType", ct.getValue());
        modelAndView.addObject("clientTypeName", ct.getName());
        modelAndView.addObject("channelId", channelId);
        return modelAndView;
    }

    @RequestMapping(value = "/get" ,method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> edit(HttpServletRequest request, @RequestParam Long id){
        Map<String, Object> data = new HashMap<String, Object>();
        MallCategoryVo mallCategoryVo = mallCategoryService.getCategory(id, ControllerContext.getTenantId());
        data.put("data", mallCategoryVo==null?new MallCategoryVo() : mallCategoryVo);
        return data;
    }

    @RequestMapping(value = "/save" ,method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> save(HttpServletRequest request, MallCategoryVo mallCategoryVo,
                                    @RequestParam(value = "beforeSort") Integer beforeSort){
        Map<String, Object> data = new HashMap<String, Object>();
        if(mallCategoryVo == null){
            data.put("success", "0");
            return data;
        }
        //mallCategoryVo.setClientType(type);
        mallCategoryVo.setTenantId(ControllerContext.getTenantId());
        if(mallCategoryVo.getHeighlight()==null){
            mallCategoryVo.setHeighlight(0);
        }

        BaseResponseVo baseResponseVo = null;
        if(mallCategoryVo.getId() == null|| mallCategoryVo.getId() == 0L){
            baseResponseVo = mallCategoryClient.add(mallCategoryVo);
        }else{
            baseResponseVo = mallCategoryClient.update(mallCategoryVo, beforeSort);
        }
        data.put("success", baseResponseVo.getIsSuccess()?1:0);
        return data;
    }

    @RequestMapping(value = "/del" ,method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> del(HttpServletRequest request, MallCategoryVo mallCategoryVo) {
        Map<String, Object> data = new HashMap<String, Object>();
        if (mallCategoryVo == null) {
            data.put("success", "0");
            return data;
        }
        mallCategoryVo.setTenantId(ControllerContext.getTenantId());
        MallCategoryVo query = new MallCategoryVo();
        query.setTenantId(ControllerContext.getTenantId());
        query.setParentId(mallCategoryVo.getId());
        BaseResponseVo<List<MallCategoryVo>> queryResponseVo = mallCategoryClient.query(query);
        List<MallCategoryVo> list = queryResponseVo.getData();
        if(!queryResponseVo.isSuccess() || (list!=null && list.size()>0)){
            data.put("success", -1);
            return data;
        }

        MallPrincipalItemVo mallPrincipalItem = new MallPrincipalItemVo();
        mallPrincipalItem.setTenantId(ControllerContext.getTenantId());
        mallPrincipalItem.setState(StateEnum.ON_SHELF.getValue());
        mallPrincipalItem.setPrincipalType(AdPrincipalTypeEnum.CATEGORY.getValue());
        mallPrincipalItem.setPrincipalId(mallCategoryVo.getId());
        BaseResponseVo<List<MallPrincipalItemVo>> principalRes = mallPrincipalItemClient.query(mallPrincipalItem);
        if(!principalRes.isSuccess() || (principalRes.getData()!=null && principalRes.getData().size()>0)){
            data.put("success", -2);
            return data;
        }

        BaseResponseVo baseResponseVo = mallCategoryClient.delete(mallCategoryVo);
        data.put("success", baseResponseVo.getIsSuccess()?1:0);
        return data;
    }

    @RequestMapping(value = "/saveBatch" ,method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> saveBatch(HttpServletRequest request, @RequestParam(value = "catMapStr") String catMapStr) {
        Map<String, Object> data = new HashMap<String, Object>();
        Map<String,MallCategoryVo> map = JSON.parseObject(catMapStr, new TypeReference<Map<String, MallCategoryVo>>() {});
        for(Map.Entry<String, MallCategoryVo> entry : map.entrySet()){
            mallCategoryClient.update(entry.getValue(), entry.getValue().getSortNum());
        }
        data.put("success", 1);
        return data;
    }
    @RequestMapping(value = "/isUniqueName" , method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Boolean isUniqueName(HttpServletRequest request, MallCategoryVo mallCategoryVo) {
        mallCategoryVo.setTenantId(ControllerContext.getTenantId());
        Boolean isUnique = mallCategoryClient.isUniqueName(mallCategoryVo);
        return isUnique;

    }
    @RequestMapping(value = "/verifySortNum" , method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Boolean verifySortNum(HttpServletRequest request, MallCategoryVo mallCategoryVo) {mallCategoryVo.setTenantId(ControllerContext.getTenantId());
        Boolean isUnique = mallCategoryClient.verifySortNum(mallCategoryVo);
        return isUnique;

    }


    @RequestMapping(value = "/move" ,method = {RequestMethod.POST})
    @ResponseBody
    public BaseResponseVo move_cate(@RequestParam String data,@RequestParam String action, @RequestParam String newSort){
        String[] d=data.split("-");
        Long id=Long.parseLong(d[0]);
        Long pid=Long.parseLong(d[1]);
        Integer sort=Integer.parseInt(d[2]);
        Integer newSortInt=Integer.parseInt(newSort);
        MallCategoryVo mallCategoryVo=new MallCategoryVo(id,pid,newSortInt);
        mallCategoryVo.setTenantId(ControllerContext.getTenantId());
        //BaseResponseVo baseResponseVo = mallCategoryClient.moveCate(mallCategoryVo,action);
        BaseResponseVo baseResponseVo = mallCategoryClient.update(mallCategoryVo, sort);
        return baseResponseVo;
    }


    @RequestMapping(value = "/updateMoveUp" ,method = {RequestMethod.POST})
    @ResponseBody
    public BaseResponseVo updateMoveUp(@RequestParam String data,@RequestParam String action){
        String[] d=data.split("-");
        Long id=Long.parseLong(d[0]);
        Long pid=Long.parseLong(d[1]);
        Integer sort=Integer.parseInt(d[2]);
        MallCategoryVo mallCategoryVo=new MallCategoryVo(id,pid,sort);
        mallCategoryVo.setTenantId(ControllerContext.getTenantId());
        BaseResponseVo baseResponseVo = mallCategoryClient.updateMoveUp(mallCategoryVo,action);
        return baseResponseVo;
    }
}
