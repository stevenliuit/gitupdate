package com.jcloud.b2c.platform.web.controller.superAdmin;

import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.common.common.web.security.ControllerContext;
import com.jcloud.b2c.item.client.vo.item.ItemDetailVo;
import com.jcloud.b2c.item.client.vo.item.SkuVo;
import com.jcloud.b2c.platform.common.constant.ItemConstants;
import com.jcloud.b2c.platform.common.util.json.JsonUtils;
import com.jcloud.b2c.platform.service.feign.ItemClient;
import com.jcloud.b2c.platform.service.feign.OssClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/super/oss")
public class OssManagerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(OssManagerController.class);

    @Resource
    private OssClient ossClient;

    @Resource
    private ItemClient itemClient;

    @RequestMapping(value = "/index",method = {RequestMethod.GET})
    public String oss(){
        return "/super/oss";
    }

    @RequestMapping(value = "/itemDesc",method = {RequestMethod.GET})
    public String itemDesc(){
        return "/super/itemDescribeOss";
    }
    @ResponseBody
    @RequestMapping(value = "/select",method = {RequestMethod.GET})
    public String selectCahce(@RequestParam Long skuId){
        Map<String, Object> returnMap = new HashMap<String, Object>();
        Long tenantId = ControllerContext.getTenantId();
        Long shopId = ControllerContext.getShopId();
        if(null == tenantId){
            returnMap.put(ItemConstants.MESSAGE_FLAG, "平台id不能为空");
            return JsonUtils.toJSON(returnMap);
        }
        if(null == shopId){
            returnMap.put(ItemConstants.MESSAGE_FLAG, "店铺id不能为空");
            return JsonUtils.toJSON(returnMap);
        }
        try{
            BaseResponseVo<ItemDetailVo> baseResponseVo = itemClient.getItemDetailFromDB(tenantId, shopId, skuId);
            if(baseResponseVo.isSuccess()) {
                ItemDetailVo itemDetailVo = baseResponseVo.getData();
                returnMap.put(ItemConstants.SUCCESS_FLAG, true);
                byte[] describe = null;
                byte[] mobiledescribe = null;
                if(!"".equals(itemDetailVo.getDescribeUrl()) && itemDetailVo.getDescribeUrl()!=null){
                    describe = ossClient.getObjBytes(tenantId, itemDetailVo.getDescribeUrl(), 10000);
                    String desc = new String(describe);
                    returnMap.put("itemDescribe",desc);
                }
                if(!"".equals(itemDetailVo.getMobileDescUrl()) && itemDetailVo.getMobileDescUrl()!=null){
                    mobiledescribe = ossClient.getObjBytes(tenantId, itemDetailVo.getMobileDescUrl(), 10000);
                    String mobiledesc = new String(mobiledescribe);
                    returnMap.put("mobileItemDescribe",mobiledesc);
                }
            }else{
                returnMap.put(ItemConstants.SUCCESS_FLAG, false);
                returnMap.put("itemDescribe",null);
                returnMap.put("mobileItemDescribe",null);
            }
        } catch (Exception e) {
            returnMap.put(ItemConstants.SUCCESS_FLAG, false);
            returnMap.put(ItemConstants.MESSAGE_FLAG, "查询错误");
        }
        return JsonUtils.toJSON(returnMap);
    }


    @ResponseBody
    @RequestMapping(value = "/selectItemDesc",method = {RequestMethod.GET})
    public String selectItem(@RequestParam String itemType, @RequestParam String targetStr){
        long beginTime = System.currentTimeMillis();
        Map<String, Object> returnMap = new HashMap<String, Object>();
        Long tenantId = ControllerContext.getTenantId();
        Long shopId = ControllerContext.getShopId();
        if(null == tenantId){
            returnMap.put(ItemConstants.MESSAGE_FLAG, "平台id不能为空");
            return JsonUtils.toJSON(returnMap);
        }
        if(null == shopId){
            returnMap.put(ItemConstants.MESSAGE_FLAG, "店铺id不能为空");
            return JsonUtils.toJSON(returnMap);
        }
        if(null == itemType || null == targetStr || "".equals(itemType) || "".equals(targetStr)){
            returnMap.put(ItemConstants.MESSAGE_FLAG, "查询条件不能为空");
            return JsonUtils.toJSON(returnMap);
        }
        try{
            BaseResponseVo<List<SkuVo>> baseResponseVo = itemClient.getItemSkuList(tenantId, shopId);
            if(baseResponseVo.isSuccess()) {
                List<SkuVo> skuList = baseResponseVo.getData();
                returnMap.put(ItemConstants.SUCCESS_FLAG, true);
                StringBuilder skuIds = new StringBuilder("");
                StringBuilder failSkuIds = new StringBuilder("");
                if (skuList != null && skuList.size() > 0){
                    int skuNum = skuList.size();
                    Long jdSkuId = null;
                    for (int i=0;i<skuNum;i++){
                        SkuVo skuVo = skuList.get(i);
                        jdSkuId = skuVo.getJdSkuId();
                        byte[] describe = null;
                        String desc_url = skuVo.getDescribeUrl(); //默认查询pc端的描述
                        if(itemType.equals("2")){
                            desc_url = skuVo.getMobileDescribeUrl();
                        }
                        if(!"".equals(desc_url) && desc_url != null){
                            String desc = null;
                            //默认查询缓存
                            BaseResponseVo<ItemDetailVo> skuDetailVo = itemClient.getItemDetail(tenantId, shopId, jdSkuId);
                            if(skuDetailVo != null && skuDetailVo.getIsSuccess()){
                                ItemDetailVo itemVo = skuDetailVo.getData();
                                if(itemVo != null){
                                    if(itemType.equals("1")){
                                        desc = itemVo.getDescribe();
                                    }else{
                                        desc = itemVo.getMobileDesc();
                                    }
                                }else{
                                    describe = ossClient.getObjBytes(tenantId, desc_url, 10000);
                                    desc = new String(describe, "utf-8");
                                }
                            }else{
                                describe = ossClient.getObjBytes(tenantId, desc_url, 10000);
                                desc = new String(describe, "utf-8");
                            }
                            if(!"".equals(desc) && desc !=null && desc.indexOf(targetStr) >= 0){
                                if (i != skuNum - 1){
                                    skuIds.append(jdSkuId).append(",");
                                }else{
                                    skuIds.append(jdSkuId);
                                }
                            }else{
                                if (i != skuNum - 1){
                                    failSkuIds.append(jdSkuId).append(",");
                                }else{
                                    failSkuIds.append(jdSkuId);
                                }
                            }
                        }else{
                            if (i != skuNum - 1){
                                failSkuIds.append(jdSkuId).append(",");
                            }else{
                                failSkuIds.append(jdSkuId);
                            }
                        }
                    }
                }
                returnMap.put("skuIds",skuIds.toString());
                returnMap.put("failSkuIds",failSkuIds.toString());
                LOGGER.info("存在目标字符串的JDSKU：{}", skuIds.toString());
                LOGGER.info("不存在目标字符串的JDSKU：{}", failSkuIds.toString());
            }else{
                returnMap.put(ItemConstants.SUCCESS_FLAG, false);
                returnMap.put("skuIds",null);
                returnMap.put("failSkuIds", null);
            }
            long endTime = System.currentTimeMillis();
            LOGGER.info("筛选描述总耗时长：{}", (endTime - beginTime));
        } catch (Exception e) {
            returnMap.put(ItemConstants.SUCCESS_FLAG, false);
            returnMap.put(ItemConstants.MESSAGE_FLAG, "查询错误");
        }
        return JsonUtils.toJSON(returnMap);
    }
}
