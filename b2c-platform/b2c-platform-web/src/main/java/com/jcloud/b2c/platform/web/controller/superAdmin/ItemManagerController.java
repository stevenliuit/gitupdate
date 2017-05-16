package com.jcloud.b2c.platform.web.controller.superAdmin;

import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.common.common.web.security.ControllerContext;
import com.jcloud.b2c.platform.common.constant.ItemConstants;
import com.jcloud.b2c.platform.common.util.json.JsonUtils;
import com.jcloud.b2c.platform.service.feign.ItemClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/super/kpl")
public class ItemManagerController {

    @Resource
    private ItemClient itemClient;

    @RequestMapping(value = "/index",method = {RequestMethod.GET})
    public String kplitem(){
        return "/super/kplitem";
    }

    @RequestMapping(value = "/itemManage",method = {RequestMethod.GET, RequestMethod.POST})
    public String itemManage(ModelMap modelMap){
        return "/super/itemManage";
    }
    @ResponseBody
    @RequestMapping(value = "/searchItem",method = {RequestMethod.GET})
    public String searchItem(@RequestParam Long skuId){
        Map<String, Object> returnMap = new HashMap<String, Object>();
        Long tenantId = ControllerContext.getTenantId();
        if(null == tenantId){
            returnMap.put(ItemConstants.MESSAGE_FLAG, "平台id不能为空");
            return JsonUtils.toJSON(returnMap);
        }
        if(null == skuId){
            returnMap.put(ItemConstants.MESSAGE_FLAG, "商品Id不能为空");
            return JsonUtils.toJSON(returnMap);
        }
        try{
            BaseResponseVo<List<String>> baseResponseVo = itemClient.getItemFromKpl(tenantId, skuId);
            if(baseResponseVo.isSuccess()) {
                List<String> items = baseResponseVo.getData();
                String baseInfos = items.get(0);
                String bigInfos = items.get(1);
                returnMap.put("itembase",baseInfos);
                returnMap.put("itemDescribe",bigInfos);
                returnMap.put(ItemConstants.SUCCESS_FLAG, true);
            }else{
                returnMap.put(ItemConstants.SUCCESS_FLAG, false);
                returnMap.put("itembase",null);
                returnMap.put("itemDescribe",null);
            }
        } catch (Exception e) {
            returnMap.put(ItemConstants.SUCCESS_FLAG, false);
            returnMap.put(ItemConstants.MESSAGE_FLAG, "查询错误");
        }
        return JsonUtils.toJSON(returnMap);
    }
    @ResponseBody
    @RequestMapping(value = "/updateItem",method = {RequestMethod.POST})
    public String updateItem(@RequestParam Long skuId){
        Map<String, Object> returnMap = new HashMap<String, Object>();
        Long tenantId = ControllerContext.getTenantId();
        if(null == tenantId){
            returnMap.put(ItemConstants.MESSAGE_FLAG, "平台id不能为空");
            return JsonUtils.toJSON(returnMap);
        }
        if(null == skuId){
            returnMap.put(ItemConstants.MESSAGE_FLAG, "商品Id不能为空");
            return JsonUtils.toJSON(returnMap);
        }
        try{
            BaseResponseVo<Boolean> baseResponseVo = itemClient.updateItemFromKpl(tenantId, skuId);
            if(baseResponseVo.isSuccess()) {
                returnMap.put(ItemConstants.SUCCESS_FLAG, baseResponseVo.getData());
                returnMap.put(ItemConstants.MESSAGE_FLAG, "更新成功");
            }else{
                returnMap.put(ItemConstants.SUCCESS_FLAG, false);
                returnMap.put(ItemConstants.MESSAGE_FLAG,"更新错误");
            }
        } catch (Exception e) {
            returnMap.put(ItemConstants.SUCCESS_FLAG, false);
            returnMap.put(ItemConstants.MESSAGE_FLAG, "更新错误");
        }
        return JsonUtils.toJSON(returnMap);
    }
}
