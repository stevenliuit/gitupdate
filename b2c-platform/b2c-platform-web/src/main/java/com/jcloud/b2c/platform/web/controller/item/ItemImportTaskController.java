/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: ItemImportTaskController.java   project: b2c-jcloud
 * @creator: huangning1
 * @date: 2017/2/17
 */

package com.jcloud.b2c.platform.web.controller.item;

import com.alibaba.fastjson.JSON;
import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.common.common.web.security.ControllerContext;
import com.jcloud.b2c.item.client.vo.common.Pager;
import com.jcloud.b2c.item.client.vo.item.ItemImportTaskResult;
import com.jcloud.b2c.item.client.vo.item.ItemImportTaskVo;
import com.jcloud.b2c.platform.common.constant.ItemConstants;
import com.jcloud.b2c.platform.common.util.json.JsonUtils;
import com.jcloud.b2c.platform.domain.vo.RunResults;
import com.jcloud.b2c.platform.service.feign.ImportItemClient;
import com.jcloud.b2c.platform.service.feign.RunJobDetailClient;
import com.jcloud.b2c.platform.service.item.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @description: 商品导入任务查询
 * @author: huangning1
 * @requireNo: none
 * @createdate: 2017-02-17 16:50
 * @lastdate:
 */

@Controller
@RequestMapping(value="/importTask")
public class ItemImportTaskController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ItemImportTaskController.class);

    @Autowired
    private ItemService itemService;

    @Autowired
    private ImportItemClient importItemClient;

    @Autowired
    private RunJobDetailClient runJobDetailClient;
    /**
     * 商品导入任务页面
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/findTask", method = {RequestMethod.GET, RequestMethod.POST})
    public String toImportTaskList(ModelMap modelMap) {
        LOGGER.info("进入查询导入任务的服务");
        //调用验证参数
        //调用服务
        LOGGER.info("查询导入任务的服务结束");
        return "item/importTaskList";
    }

    /**
     * 商品导入任务列表条件搜索
     * @return
     */
    @RequestMapping(value = "/searchImportTaskList", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String searchImportTaskList(String skuId,String batchCode,String beginDate,String endDate,Integer status ,Integer page,Integer size) {
        LOGGER.info("开始平台条件查询商品导入列表，skuId：[" + skuId + "],batchCode：[" +batchCode+"],beginDate：["+beginDate +"],endDate：["+endDate +"],status：["+status+"],page：["+page+"],pageSize：["+size+"]");
        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put(ItemConstants.SUCCESS_FLAG, false);
        ItemImportTaskVo itemImportTaskVo = new ItemImportTaskVo();
        try{
            Long tenantId = ControllerContext.getTenantId();
            if(null == tenantId){
                returnMap.put(ItemConstants.MESSAGE_FLAG, "平台id不能为空");
                return JsonUtils.toJSON(returnMap);
            }
            itemImportTaskVo.setTenantId(tenantId);
            Long shopId = ControllerContext.getShopId();
            if(null == shopId){
                returnMap.put(ItemConstants.MESSAGE_FLAG, "店铺id不能为空");
                return JsonUtils.toJSON(returnMap);
            }
            itemImportTaskVo.setShopId(shopId);
//            Long userId = ControllerContext.getUserId();
            //Long userId = 1L;
//            if(null == userId){
//                returnMap.put(ItemConstants.MESSAGE_FLAG, "用户id不能为空");
//                return JsonUtils.toJSON(returnMap);
//            }
////            itemImportTaskVo.setUserId(userId);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if(null != skuId && !skuId.equals("")){
                itemImportTaskVo.setSkuId(Long.parseLong(skuId));
            }
            if(null != batchCode && !batchCode.equals("")){
                itemImportTaskVo.setBatchCode(batchCode);
            }
            if(null != beginDate && !beginDate.equals("")){
                Date begin = sdf.parse(beginDate);
               itemImportTaskVo.setBeginDate(begin);
            }
            if(null != endDate && !endDate.equals("")){
                Date end = sdf.parse(endDate);
               itemImportTaskVo.setEndDate(end);
            }
            if(null != status){
                itemImportTaskVo.setStatus(status);
            }
            if(null != page){
                itemImportTaskVo.setPage(page);
            }
            if(null != size){
                itemImportTaskVo.setSize(size);
            }
            itemImportTaskVo.setSize(20);
            BaseResponseVo<ItemImportTaskResult> responseVo = importItemClient.findItemImportTask(itemImportTaskVo);
            if(responseVo.isSuccess()) {
                ItemImportTaskResult itemImportTaskResult =  responseVo.getData();
                if (itemImportTaskResult != null){
                    List<ItemImportTaskVo> taskList = itemImportTaskResult.getTaskList();
                    Pager pager = itemImportTaskResult.getPager();
                    returnMap.put("list", taskList);
                    returnMap.put("totalCount",pager.getTotalCount());
                    returnMap.put("totalPages", pager.getTotalPages());
                    returnMap.put(ItemConstants.SUCCESS_FLAG, true);
                    returnMap.put(ItemConstants.MESSAGE_FLAG, "查询成功");
                }else{
                    returnMap.put(ItemConstants.SUCCESS_FLAG, false);
                    returnMap.put(ItemConstants.MESSAGE_FLAG,"查询失败");
                }
                // 接口调用失败的场合
            } else {
                returnMap.put(ItemConstants.SUCCESS_FLAG, false);
                returnMap.put(ItemConstants.MESSAGE_FLAG, responseVo.getMessage());
            }


        }catch (Exception e){
            returnMap.put(ItemConstants.MESSAGE_FLAG, "查询失败");
            LOGGER.error("【searchItemList】: " + "出现异常： ", e);
        }
        return JsonUtils.toJSON(returnMap);
    }

    /**
     * 重新导入
     * @param id
     * @return
     */
    @RequestMapping(value = "/reImportTask", method = {RequestMethod.POST})
    @ResponseBody
    public String reImportTask(String id) {
        LOGGER.info("重新导入任务的服务");
        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put(ItemConstants.SUCCESS_FLAG, false);
        if(id == null ){
            returnMap.put(ItemConstants.MESSAGE_FLAG, "参数不能为空");
            return JsonUtils.toJSON(returnMap);
        }
        try {
            String result = runJobDetailClient.runJobDetail(Long.parseLong(id));
            RunResults categories = JSON.parseObject(result, RunResults.class);
            returnMap.put(ItemConstants.SUCCESS_FLAG, categories.isSuccess());
            returnMap.put(ItemConstants.MESSAGE_FLAG, categories.getReason());

        }catch(Exception e){
            returnMap.put(ItemConstants.MESSAGE_FLAG, "重新导入失败");
            LOGGER.error("重新导入出现异常： ", e);
        }
        LOGGER.info("重新导入任务的服务结束");
        return JsonUtils.toJSON(returnMap);
    }

    /**
     * 重新导入所有失败
     * @param
     * @return
     */
    @RequestMapping(value = "/reImportAllFailedTask", method = {RequestMethod.POST})
    @ResponseBody
    public String reImportAllFailedTask() {
        LOGGER.info("重新导入任务的服务");
        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put(ItemConstants.SUCCESS_FLAG, false);
        ItemImportTaskVo itemImportTaskVo = new ItemImportTaskVo();
        try {
            Long tenantId = ControllerContext.getTenantId();
            if(null == tenantId){
                returnMap.put(ItemConstants.MESSAGE_FLAG, "平台id不能为空");
                return JsonUtils.toJSON(returnMap);
            }
            itemImportTaskVo.setTenantId(tenantId);
            Long shopId = ControllerContext.getShopId();
            if(null == shopId){
                returnMap.put(ItemConstants.MESSAGE_FLAG, "店铺id不能为空");
                return JsonUtils.toJSON(returnMap);
            }
            itemImportTaskVo.setShopId(shopId);
            Long userId = ControllerContext.getUserId();
            //Long userId = 1L;
            itemImportTaskVo.setPage(1);
            itemImportTaskVo.setSize(100000);
            if(null == userId){
                returnMap.put(ItemConstants.MESSAGE_FLAG, "用户id不能为空");
                return JsonUtils.toJSON(returnMap);
            }
            itemImportTaskVo.setUserId(userId);
            itemImportTaskVo.setStatus(2);
            BaseResponseVo<ItemImportTaskResult> responseVo = importItemClient.findItemImportTask(itemImportTaskVo);
            Integer failed = 0;
            Integer success =0;
            if(responseVo.isSuccess()) {
                ItemImportTaskResult itemImportTaskResult =  responseVo.getData();
                if (itemImportTaskResult != null){
                    List<ItemImportTaskVo> taskList = itemImportTaskResult.getTaskList();
                    for (int i=0;i<=taskList.size();i++){
                        Long id = taskList.get(i).getId();
                        String result = runJobDetailClient.runJobDetail(id);
                        RunResults categories = JSON.parseObject(result, RunResults.class);
                        if(!categories.isSuccess()){
                            failed++;
                        }
                    }
                    if(failed==0){
                        returnMap.put(ItemConstants.SUCCESS_FLAG, true);
                        returnMap.put(ItemConstants.MESSAGE_FLAG, "重新导入成功");
                    }else if(failed < taskList.size()){
                        success=taskList.size()-failed;
                        returnMap.put(ItemConstants.SUCCESS_FLAG, false);
                        returnMap.put(ItemConstants.MESSAGE_FLAG, "导入成功:"+success+"条，导入失败#"+failed+".");
                    }else{
                        returnMap.put(ItemConstants.SUCCESS_FLAG, false);
                        returnMap.put(ItemConstants.MESSAGE_FLAG, "重新导入失败");
                    }
                }
            }
        }catch(Exception e){
            returnMap.put(ItemConstants.MESSAGE_FLAG, "重新导入失败");
            LOGGER.error("重新导入出现异常： ", e);
        }
        LOGGER.info("重新导入任务的服务结束");
        return JsonUtils.toJSON(returnMap);
    }
}
