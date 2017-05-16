package com.jcloud.b2c.platform.web.controller.item;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.jcloud.b2c.common.client.vo.CommonClientRequest;
import com.jcloud.b2c.common.client.vo.ProductImportRequest;
import com.jcloud.b2c.common.common.dictionary.OpenApiMethodEnum;
import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.common.common.web.security.ControllerContext;
import com.jcloud.b2c.item.client.vo.common.Pager;
import com.jcloud.b2c.item.client.vo.item.*;
import com.jcloud.b2c.platform.common.constant.ItemConstants;
import com.jcloud.b2c.platform.common.util.json.JsonUtils;
import com.jcloud.b2c.platform.domain.vo.ImportItemFail;
import com.jcloud.b2c.platform.domain.vo.ImportItemResponse;
import com.jcloud.b2c.platform.domain.vo.ImportItemVo;
import com.jcloud.b2c.platform.service.feign.*;
import com.jcloud.b2c.platform.service.item.ItemService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @description:
 * @author: wukun
 * @requireNo:
 * @createdate: 2017-02-15 13:58
 * @lastdate:
 */
@Controller
@RequestMapping(value="/item")
public class ItemController {
    private static final Logger log = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    private ItemService itemService;
    @Autowired
    private ImportItemClient importItemClient;
    @Autowired
    private ItemListClient itemListClient;
    @Autowired
    private OpenApiClient openApiClient;
    @Autowired
    private ItemBrandClient itemBrandClient;
    @Autowired
    private MallPrincipalItemClient mallPrincipalItemClient;
    @Autowired
    private EsItemClient esItemClient;

    @RequestMapping(value = "/to_import_item", method = {RequestMethod.GET, RequestMethod.POST})
    public String toImportItem(ModelMap modelMap) {
        return "item/importItem";
    }
    /**
     * 导入商品
     * @return
     */
    @RequestMapping(value = "/import_item", method = {RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> importItem(@RequestParam MultipartFile itemTemplate) {
        Map<String, Object> result = new HashMap<String, Object>();
        Long tenantId = ControllerContext.getTenantId();
        Long shopId = ControllerContext.getShopId();
        Long userId = ControllerContext.getUserId();
        //Long userId = 1L;
        result.put("success", true);

        if (itemTemplate == null || itemTemplate.isEmpty()) {
            log.info("上传文件为空");
            result.put("success", false);
            result.put("msg", "上传文件为空");
            return result;
        }

        long fileSize = itemTemplate.getSize();
        if (fileSize > 5 * 1024 * 1024) {
            log.info("上传文件大于5M");
            result.put("success", false);
            result.put("msg", "上传文件大于5M");
            return result;
        }

        try {
//            Map<String, String> resultMap = this.itemListService.importItems(itemTemplate, getPlatformId(), getShopId(), getShopSellerId());
//            result.put("successCount", resultMap.get("successCount"));
//            result.put("failCount", resultMap.get("failCount"));
//            result.put("resultFileUrl", resultMap.get("resultFileUrl"));
            ImportItemResponse response = itemService.transImportItem(itemTemplate,tenantId,shopId);
            Boolean transResult = response.getResult();
            if (transResult == false){
                result.put("success", false);
                result.put("msg",response.getResultMsg());
                return result;
            }
            List<ImportItemVo> importItemVos = response.getImportItemVos();
            List<ImportItemFail> importItemFails = response.getImportItemFails();
            int total = importItemFails.size() + importItemVos.size();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmssSSS");
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            String batchNumber = sdf.format(date);
            String importDate = sdf2.format(date);
            if (importItemVos != null && importItemVos.size() > 0){
                List<ImportItemVoForOpen> importItemVoForOpens = new ArrayList<ImportItemVoForOpen>();
                List<String> skuIds = new ArrayList<String>();
                for (ImportItemVo importItemVo:importItemVos){
                    if (importItemVo.getStatus() == 1){
                        ImportItemVoForOpen itemVoForOpen = new ImportItemVoForOpen();
                        itemVoForOpen.setSkuId(importItemVo.getSkuId());
                        itemVoForOpen.setcFirstId(importItemVo.getFirstCid());
                        itemVoForOpen.setcSecondId(importItemVo.getSecondCid());
                        if (importItemVo.getThirdCid() != null){
                            itemVoForOpen.setcThirdId(importItemVo.getThirdCid());
                        }
                        itemVoForOpen.setBatchNumber(batchNumber);
                        itemVoForOpen.setShopId(shopId);
                        itemVoForOpen.setTenantId(tenantId);
                        itemVoForOpen.setUserId(userId);
                        importItemVoForOpens.add(itemVoForOpen);
                        skuIds.add(String.valueOf(importItemVo.getSkuId()));
                    }
                }
                if (skuIds != null && skuIds.size() > 0){
                    CommonClientRequest clientRequest = new CommonClientRequest();
                    clientRequest.setMethod(OpenApiMethodEnum.PRODUCT_IMPORT.getCode());
                    ProductImportRequest productImportRequest = new ProductImportRequest();
                    productImportRequest.setTenantId(tenantId);
                    productImportRequest.setCallbackParam(batchNumber);
                    productImportRequest.setSkuList(skuIds);
                    Map<String, Object> queryParamMap = new HashMap<String, Object>();
                    queryParamMap.put("tenantId",tenantId);
                    queryParamMap.put("shuList",skuIds);
                    queryParamMap.put("callbackParam",batchNumber);
                    clientRequest.setRequestParam(productImportRequest);
                    String data = JsonUtils.toJSON(clientRequest);
                    String returnStr = openApiClient.api(data);
                    String openApiResult = JsonUtils.getString(returnStr, "isSuccess");
                    if (openApiResult.equals("true")){
                        BaseResponseVo<Boolean> responseVo = importItemClient.addImportItems(importItemVoForOpens);
                        if(responseVo.isSuccess()) {
                            Boolean isSuccess = responseVo.getData();
                            if (isSuccess == true){
                                result.put("msg", "导入成功");
                                result.put("importDate",importDate);
                                result.put("total",total);
                                result.put("succeCount",importItemVos.size());
                                result.put("faileCount",importItemFails.size());
                                result.put("importItemFails",importItemFails);
                            }else{
                                result.put("success", false);
                                result.put("msg","插入商品导入表失败");
                            }
                            // 接口调用失败的场合
                        } else {
                            result.put("success", false);
                            result.put("msg", responseVo.getMessage());
                        }
                    }else{
                        result.put("success", false);
                        result.put("msg","插入任务表失败");
                    }
                }else{
                    result.put("importDate",importDate);
                    result.put("total",total);
                    result.put("succeCount",importItemVos.size());
                    result.put("faileCount",importItemFails.size());
                    result.put("importItemFails",importItemFails);
                }


            }else{
                result.put("importDate",importDate);
                result.put("total",total);
                result.put("succeCount",importItemVos.size());
                result.put("faileCount",importItemFails.size());
                result.put("importItemFails",importItemFails);
            }
        } catch (Exception e) {
            log.error("导入商品异常" + e.getMessage(), e);
            result.put("success", false);
            result.put("msg", "系统异常, 导入失败");
            return result;
        }
        return result;
    }
    /**
     * 商品列表
     * @return
     */
    @RequestMapping(value = "/toItemList", method = {RequestMethod.GET, RequestMethod.POST})
    public String toItemList(ModelMap modelMap) {
        log.info("进入商品查询列表功能");
        log.info("商品查询列表功能页面获取完成");
        return "item/itemList";
    }
    /**
     * 商品列表条件搜索
     * @return
     */
    @RequestMapping(value = "/searchItemList", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String searchItemList(PlatformQuerySkuListCriteria criteria) {
        log.info("开始平台条件查询商品列表，criteria{}",criteria);
        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put(ItemConstants.SUCCESS_FLAG, false);
        try{
            Long tenantId = ControllerContext.getTenantId();
            Long shopId = ControllerContext.getShopId();
            if(null == tenantId){
                returnMap.put(ItemConstants.MESSAGE_FLAG, "平台id不能为空");
                return JsonUtils.toJSON(returnMap);
            }
            criteria.setTenantId(tenantId);
            if(null == shopId){
                returnMap.put(ItemConstants.MESSAGE_FLAG, "店铺id不能为空");
                return JsonUtils.toJSON(returnMap);
            }
            criteria.setShopId(shopId);
            criteria.setOrderColumn("created");
            criteria.setOrderType("desc");
            BaseResponseVo<PlatformQuerySkuListResponse> responseVo = itemListClient.platformQueryItemList(criteria);
            if(responseVo.isSuccess()) {
                PlatformQuerySkuListResponse response = responseVo.getData();
                if (response != null){
                    List<PlatformQuerySkuListVo> skuListVos = response.getPlatformQuerySkuListVos();
                    Pager pager = response.getPager();
                    returnMap.put("list", skuListVos);
                    returnMap.put("totalCount", pager.getTotalCount());
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
            log.error("【searchItemList】: " + "出现异常： ", e);
        }
        return JsonUtils.toJSON(returnMap);
    }
    /**
     * 搜索平台有效品牌列表
     * @return
     */
    @RequestMapping(value = "/searchBrandList", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String searchBrandList(){
        log.info("开始查询品牌列表");
        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put(ItemConstants.SUCCESS_FLAG, false);
        try{
            Long tenantId = ControllerContext.getTenantId();
            if(null == tenantId){
                returnMap.put(ItemConstants.MESSAGE_FLAG, "平台id不能为空");
                return JsonUtils.toJSON(returnMap);
            }
            BaseResponseVo<List<BrandVo>> baseResponseVo = itemBrandClient.getItemBrandList(tenantId);
            if(baseResponseVo.isSuccess()) {
                List<BrandVo> brandVos = baseResponseVo.getData();
                if (brandVos != null){
                    returnMap.put("brandVos", brandVos);
                    returnMap.put(ItemConstants.SUCCESS_FLAG, true);
                    returnMap.put(ItemConstants.MESSAGE_FLAG, "查询成功");
                }else{
                    returnMap.put(ItemConstants.SUCCESS_FLAG, false);
                    returnMap.put(ItemConstants.MESSAGE_FLAG,"查询失败");
                }
                // 接口调用失败的场合
            } else {
                returnMap.put(ItemConstants.SUCCESS_FLAG, false);
                returnMap.put(ItemConstants.MESSAGE_FLAG, baseResponseVo.getMessage());
            }


        }catch (Exception e){
            returnMap.put(ItemConstants.MESSAGE_FLAG, "查询失败");
            log.error("【searchBrandList】: " + "出现异常： ", e);
        }
        return JsonUtils.toJSON(returnMap);
    }

    /**
     * 搜索平台有效品牌列表
     * @return deleteItemLists
     */
    @RequestMapping(value = "/deleteItemLists", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String deleteItemLists(String itemList){
        log.info("开始删除商品");
        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put(ItemConstants.SUCCESS_FLAG, false);
        try{
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
            if(StringUtils.isEmpty(itemList)){
                returnMap.put(ItemConstants.MESSAGE_FLAG, "请选择商品后再执行删除操作");
                return JsonUtils.toJSON(returnMap);
            }
            JSONArray jsonArray = JSON.parseArray(itemList);
            List<Long> skuList = new ArrayList<Long>();
            List<Long> jdSkuList = new ArrayList<Long>();
            for (int i=0;i<jsonArray.size();i++){
                skuList.add(Long.parseLong(jsonArray.getJSONObject(i).getString("itemId")));
                jdSkuList.add(Long.parseLong(jsonArray.getJSONObject(i).getString("jdSkuId")));
            }
            log.info("开始调用 isBindItem 方法");
            BaseResponseVo<Map<Long,Boolean>> responseVo = mallPrincipalItemClient.isBindItems(tenantId,shopId,jdSkuList);
            log.info("调用 isBindItem 方法返回的结果 isSuccess:" + responseVo.isSuccess() + "data:" + responseVo.getData());
            String errorJdSkuId="";
            if(responseVo.isSuccess() && responseVo.getData()!= null){
                Map<Long,Boolean> map = responseVo.getData();
                for (int j=0;j<jdSkuList.size();j++){
                    if(map.get(jdSkuList.get(j))){
                        errorJdSkuId = errorJdSkuId + jdSkuList.get(j).toString()+ ",";
                    }
                }
                if(!"".equals(errorJdSkuId)){
                    returnMap.put(ItemConstants.SUCCESS_FLAG, false);
                    returnMap.put(ItemConstants.MESSAGE_FLAG, "以下商品已关联管理类目，无法删除！jdSkuId:"+errorJdSkuId);
                    return JsonUtils.toJSON(returnMap);
                }
            }else {
                returnMap.put(ItemConstants.SUCCESS_FLAG, false);
                returnMap.put(ItemConstants.MESSAGE_FLAG, responseVo.getMessage());
                return JsonUtils.toJSON(returnMap);
            }
            log.info("开始调用 deleteItems 方法");
            BaseResponseVo baseResponseVo = importItemClient.deleteItems(tenantId, shopId, skuList);
            log.info("调用 deleteItems 方法返回的结果 isSuccess:" + baseResponseVo.isSuccess() + "data:" + baseResponseVo.getData() + "message" + baseResponseVo.getMessage());
            if(baseResponseVo.isSuccess()) {
                    returnMap.put(ItemConstants.SUCCESS_FLAG, true);
                    returnMap.put(ItemConstants.MESSAGE_FLAG, "删除成功");
                // 接口调用失败的场合
            } else {
                returnMap.put(ItemConstants.SUCCESS_FLAG, false);
                if(baseResponseVo.getData()!=null){
                    returnMap.put(ItemConstants.MESSAGE_FLAG, "以下商品删除出错！skuId:"+baseResponseVo.getData());
                }else{
                    returnMap.put(ItemConstants.MESSAGE_FLAG, baseResponseVo.getMessage());
                }
            }
        }catch (Exception e){
            returnMap.put(ItemConstants.MESSAGE_FLAG, "删除失败");
            log.error("【deleteItemLists】: " + "出现异常： ", e);
        }
        return JsonUtils.toJSON(returnMap);
    }

    @RequestMapping(value = "/toBrandCusManager", method = {RequestMethod.GET, RequestMethod.POST})
    public String toBrandCusManager(ModelMap modelMap) {
        return "customer/brandCusStatusList";
    }

    @RequestMapping(value = "/selectRrandCusRelList", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String selectRrandCusRelList(QueryBrandRelCriteria criteria) {
        log.info("开始查询品牌客服关联列表，criteria{}",criteria);
        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put(ItemConstants.SUCCESS_FLAG, false);
        try{
            Long tenantId = ControllerContext.getTenantId();
            Long shopId = ControllerContext.getShopId();
            if(null == tenantId){
                returnMap.put(ItemConstants.MESSAGE_FLAG, "平台id不能为空");
                return JsonUtils.toJSON(returnMap);
            }
            criteria.setTenantId(tenantId);
            if(null == shopId){
                returnMap.put(ItemConstants.MESSAGE_FLAG, "店铺id不能为空");
                return JsonUtils.toJSON(returnMap);
            }
            criteria.setShopId(shopId);
            BaseResponseVo<QueryBrandRelResponse> responseVo = itemBrandClient.queryBrandRelList(criteria);
            if(responseVo.isSuccess()) {
                QueryBrandRelResponse response = responseVo.getData();
                if (response != null){
                    List<BrandRelVo> brandRelVos = response.getBrandRelVos();
                    Pager pager = response.getPager();
                    returnMap.put("list", brandRelVos);
                    returnMap.put("totalCount", pager.getTotalCount());
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
            log.error("【selectRrandCusRelList】: " + "出现异常： ", e);
        }
        return JsonUtils.toJSON(returnMap);
    }
    @RequestMapping(value = "/updateCusState", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String updateCusState(UpdateBrandRelState state) {
        log.info("开始启用停用客服开关，state{}",state);
        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put(ItemConstants.SUCCESS_FLAG, false);
        try{
            Long tenantId = ControllerContext.getTenantId();
            Long shopId = ControllerContext.getShopId();
            if(null == tenantId){
                returnMap.put(ItemConstants.MESSAGE_FLAG, "平台id不能为空");
                return JsonUtils.toJSON(returnMap);
            }
            state.setTenantId(tenantId);
            if(null == shopId){
                returnMap.put(ItemConstants.MESSAGE_FLAG, "店铺id不能为空");
                return JsonUtils.toJSON(returnMap);
            }
            state.setShopId(shopId);
            BaseResponseVo responseVo = itemBrandClient.updateCustomerRelState(state);
            if(responseVo.isSuccess()) {
                    returnMap.put(ItemConstants.SUCCESS_FLAG, true);
                    returnMap.put(ItemConstants.MESSAGE_FLAG, "更改客服状态成功");
                }else{
                    returnMap.put(ItemConstants.SUCCESS_FLAG, false);
                    returnMap.put(ItemConstants.MESSAGE_FLAG,"更改客服状态失败");
                    returnMap.put(ItemConstants.MESSAGE_FLAG, responseVo.getMessage());
                }
                // 接口调用失败的场合
        }catch (Exception e){
            returnMap.put(ItemConstants.MESSAGE_FLAG, "更改客服状态失败");
            log.error("【updateCusState】: " + "出现异常： ", e);
        }
        return JsonUtils.toJSON(returnMap);
    }

    @RequestMapping(value = "/deleteSkuIds", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public BaseResponseVo<Void> deleteSkuIds(String skuIds) {
        skuIds.trim();
        skuIds.replaceAll(" ","");
        String[] split = skuIds.split(",");
        ArrayList<Long> skuidsList = new ArrayList<Long>();
        for (String sku:split) {
            Long skuId = Long.valueOf(sku);
            skuidsList.add(skuId);
        }
        BaseResponseVo<Void> result = esItemClient.batchPutIntoEs(skuidsList, ControllerContext.getTenantId());
        return result;
    }

    @RequestMapping(value = "/unBindSku", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String unBindSku(String skuIds) {
        log.info("开始解除商品关联，skuIds{}",skuIds);
        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put(ItemConstants.SUCCESS_FLAG, false);
        try{
            Long tenantId = ControllerContext.getTenantId();
            if(null == tenantId){
                returnMap.put(ItemConstants.MESSAGE_FLAG, "平台id不能为空");
                return JsonUtils.toJSON(returnMap);
            }

            BaseResponseVo<Integer> responseVo = mallPrincipalItemClient.batchDelete(tenantId, skuIds);
            if(responseVo.isSuccess()) {
                returnMap.put(ItemConstants.SUCCESS_FLAG, true);
                returnMap.put(ItemConstants.MESSAGE_FLAG, "解除关联成功");
            }else{
                returnMap.put(ItemConstants.SUCCESS_FLAG, false);
                returnMap.put(ItemConstants.MESSAGE_FLAG,"解除关联失败");
                returnMap.put(ItemConstants.MESSAGE_FLAG, responseVo.getMessage());
            }
            // 接口调用失败的场合
        }catch (Exception e){
            returnMap.put(ItemConstants.MESSAGE_FLAG, "解除商品关联经营类目失败");
            log.error("【unBindSku】: " + "出现异常： ", e);
        }
        return JsonUtils.toJSON(returnMap);
    }
}
