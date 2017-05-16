package com.jcloud.b2c.platform.web.controller.category;
import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.common.common.web.security.ControllerContext;
import com.jcloud.b2c.item.client.vo.category.CategoryResponse;
import com.jcloud.b2c.item.dictionary.ItemMessageDic;
import com.jcloud.b2c.platform.common.constant.ItemConstants;
import com.jcloud.b2c.platform.common.util.json.JsonUtils;
import com.jcloud.b2c.platform.domain.vo.Category;
import com.jcloud.b2c.platform.domain.vo.PlatCategoryVo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
@Controller
@RequestMapping(value="/platcategory")
public class PlatCategoryController {
    private static final Logger log = LoggerFactory.getLogger(PlatCategoryController.class);
    @Autowired
    private com.jcloud.b2c.platform.service.feign.ItemCategoryClient itemCategoryClient;
    /**
     * 获取类目管理页面
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/toCategoryManage", method = {RequestMethod.GET, RequestMethod.POST})
    public String toCategoryManage(ModelMap modelMap) {
        Long tenantId = ControllerContext.getTenantId();
        log.info("进入查询类目列表页面");
        //基本的越权校验
        return "category/categoryManage";
    }
    /**
     * 获取类目数据
     * @param
     * @return
     */
    @RequestMapping(value = "/searchCategoryData", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String toCategoryManageData() {
        log.info("开始查询系统类目列表");
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
            BaseResponseVo<List<CategoryResponse>> first = itemCategoryClient.getCategoryList(tenantId,-1l);
            if(first.getIsSuccess()){
                List<CategoryResponse> firstCategorys = first.getData();
                List<PlatCategoryVo> firstplatCategorys = new ArrayList<PlatCategoryVo>();
                if(firstCategorys != null && firstCategorys.size() > 0){
                    for (CategoryResponse cav : firstCategorys) {
                        List<Category> secondtcategory = new ArrayList<Category>();
                        PlatCategoryVo platCategoryVo = new PlatCategoryVo();
                        platCategoryVo.setCid(cav.getCid());
                        platCategoryVo.setCategoryName(cav.getCategoryName());
                        platCategoryVo.setHasLeaf(cav.getHasLeaf());
                        platCategoryVo.setLev(cav.getLev());
                        platCategoryVo.setParentCid(cav.getParentCid());
                        platCategoryVo.setSortNumber(cav.getSortNumber());
                        platCategoryVo.setStatus(cav.getStatus());
                        platCategoryVo.setTenantId(cav.getTenantId());
                        BaseResponseVo<List<CategoryResponse>> second = itemCategoryClient.getCategoryList(cav.getTenantId(), cav.getCid());
                        if(second.isSuccess()){
                            if(second.getData()!=null){
                                for (CategoryResponse cata : second.getData()) {
                                    Category category = new Category();
                                    category.setCid(cata.getCid());
                                    category.setCategoryName(cata.getCategoryName());
                                    category.setHasLeaf(cata.getHasLeaf());
                                    category.setLev(cata.getLev());
                                    category.setParentCid(cata.getParentCid());
                                    category.setSortNumber(cata.getSortNumber());
                                    category.setStatus(cata.getStatus());
                                    category.setTenantId(cata.getTenantId());
                                    secondtcategory.add(category);
                                }
                                platCategoryVo.setLtObjects(secondtcategory);
                                firstplatCategorys.add(platCategoryVo);
                            }else{
                                firstplatCategorys.add(platCategoryVo);
                            }
                        }
                    }
                    returnMap.put(ItemConstants.SUCCESS_FLAG, true);
                    returnMap.put("list", firstplatCategorys);
                }else{
                    returnMap.put(ItemConstants.SUCCESS_FLAG, true);
                }
            }
        }catch (Exception e){
            log.error("查询类目列表出错" + e.getMessage());
            returnMap.put(ItemConstants.MESSAGE_FLAG, "查询类目失败");
        }
        return JsonUtils.toJSON(returnMap);
    }

    //新增
    @RequestMapping(value = "/addNewCategory", method = { RequestMethod.GET, RequestMethod.POST }, produces = "text/html;charset=utf-8")
    @ResponseBody
    public String addNewCategory(HttpServletRequest httpRequest, String categoryData) {
        JSONObject jsonObject = new JSONObject();
        Long tenantId = ControllerContext.getTenantId();
        log.info("tenantId：[" + tenantId + "]");
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            JSONArray jsonArray = JSONArray.fromObject(categoryData);
            @SuppressWarnings("unchecked")
            List<CategoryResponse> categories = (List<CategoryResponse>) JSONArray.toCollection(jsonArray,CategoryResponse.class);
            for (CategoryResponse cav : categories) {
                if (cav.getCategoryName().length() > 20) {
                    map.put("success", false);
                    map.put("msg", "类目名称长度需不超过20个字符，请检查确认。");
                    log.info("类目名称长度超过了20个字符，名称如下：" + cav.getCategoryName());
                    jsonObject.put("result", map);
                    log.info(jsonObject.toString());
                    return jsonObject.toString();
                }
            }
            for (CategoryResponse cav : categories) {
                BaseResponseVo<List<CategoryResponse>> second = itemCategoryClient.getCategoryList(tenantId, cav.getParentCid());
                if(second.getData() != null && second.getData().size()>0){
                    int sortNumber = second.getData().size();
                    cav.setSortNumber(sortNumber+1);
                }else{
                    cav.setSortNumber(1);
                }
                cav.setTenantId(tenantId);
                cav.setCreated(new Date());
                cav.setModified(new Date());
                log.info(cav.getCategoryName());
                log.info(String.valueOf(cav.getParentCid()));
                log.info(String.valueOf(cav.getLev()));
                itemCategoryClient.insCategoryInfo(cav);
            }
            map.put("success", true);
        } catch (Exception e) {
            log.error("添加类目controller层调用异常", e);
            map.put("success", false);
            map.put("msg", ItemMessageDic.ITEM_CATEGORY_E6420);
        }
        jsonObject.put("result", map);
        log.info(jsonObject.toString());
        return jsonObject.toString();
    }
    //查询
    @RequestMapping(value = "/queryCategoryList", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String queryCategoryList(Long parentId){
        log.info("开始查询平台类目列表，parentId{}",parentId);
        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put(ItemConstants.SUCCESS_FLAG, false);
        try{
            Long tenantId = ControllerContext.getTenantId();
            if(null == tenantId){
                returnMap.put(ItemConstants.MESSAGE_FLAG, "平台id不能为空");
                return JsonUtils.toJSON(returnMap);
            }
            BaseResponseVo<List<CategoryResponse>> responseVo = itemCategoryClient.getCategoryList(tenantId, parentId);
            if(responseVo.isSuccess()) {
                List<CategoryResponse> categoryResponses = responseVo.getData();
                returnMap.put(ItemConstants.SUCCESS_FLAG,true);
                returnMap.put("categorys",categoryResponses);
            }else {
                returnMap.put(ItemConstants.SUCCESS_FLAG, false);
                returnMap.put(ItemConstants.MESSAGE_FLAG,"查询失败");
            }
        }catch (Exception e){
            returnMap.put(ItemConstants.MESSAGE_FLAG, "查询失败");
            log.error("【queryCategoryList】: " + "出现异常： ", e);
        }
        return JsonUtils.toJSON(returnMap);
    }

    //修改
    @RequestMapping(value = "/modifyCategoryName", method = { RequestMethod.GET, RequestMethod.POST }, produces = "text/html;charset=utf-8")
    @ResponseBody
    public String modifyCategoryName(HttpServletRequest httpRequest,Long cid, String categoryName) {
        JSONObject jsonObject = new JSONObject();
        Long tenantId = ControllerContext.getTenantId();
        log.info("tenantId：[" + tenantId + "]");
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setCategoryName(categoryName);
        categoryResponse.setCid(cid);
        categoryResponse.setTenantId(tenantId);
        categoryResponse.setModified(new Date());
        Map<String, Object> map = new HashMap<String, Object>();
        if (categoryName.length() > 20) {
            map.put("success", false);
            map.put("msg", "类目名称长度需不超过20个字符，请检查确认。");
            log.info("类目名称长度超过了20个字符，名称如下：" + categoryName);
            jsonObject.put("result", map);
            log.info(jsonObject.toString());
            return jsonObject.toString();
        }
        try {
            itemCategoryClient.modifyCategoryName(categoryResponse);
        } catch (Exception e) {
            log.error("修改类目controller层调用service时遇到异常，信息如下：", e);
            map.put("success", false);
            map.put("msg", "修改类目信息失败");
        }
        jsonObject.put("result", map);
        log.info(jsonObject.toString());
        return jsonObject.toString();
    }

    //删除
    @RequestMapping(value = "/delCategoryByCid", method = { RequestMethod.GET, RequestMethod.POST }, produces = "text/html;charset=utf-8")
    @ResponseBody
    public String delCategoryByCid(HttpServletRequest httpRequest, ModelMap result, Long cid, Byte lev) {
        JSONObject jsonObject = new JSONObject();
        Long tenantId = ControllerContext.getTenantId();
        log.info("tenantId：[" + tenantId + "]");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(ItemConstants.SUCCESS_FLAG, false);
        if (cid == null || lev == null) {
            map.put(ItemConstants.SUCCESS_FLAG, false);
            map.put(ItemConstants.MESSAGE_FLAG, "参数有误，稍后再试");
            jsonObject.put("result", map);
            log.info(jsonObject.toString());
            return jsonObject.toString();
        }
        try {
            BaseResponseVo<Integer> value = itemCategoryClient.delCategoryInfo(tenantId, cid);
            if(value.isSuccess()){
                map.put(ItemConstants.SUCCESS_FLAG, true);
                map.put(ItemConstants.MESSAGE_FLAG, "删除成功!");
            }else{
                map.put(ItemConstants.SUCCESS_FLAG, false);
                map.put(ItemConstants.MESSAGE_FLAG, value.getErrorCode());
            }
        } catch (Exception e) {
            log.error("删除单个类目controller调用service时出现异常。", e);
            map.put(ItemConstants.SUCCESS_FLAG, false);
            map.put(ItemConstants.MESSAGE_FLAG, ItemMessageDic.ITEM_CATEGORY_E6430);
        }
        jsonObject.put("result", map);
        log.info(jsonObject.toString());
        return jsonObject.toString();
    }

    //移动
    @RequestMapping(value = "/categorySort", method = { RequestMethod.GET, RequestMethod.POST }, produces = "text/html;charset=utf-8")
    @ResponseBody
    public String categorySort(HttpServletRequest httpRequest,Long cid, String flag) {
        JSONObject jsonObject = new JSONObject();
        Long tenantId = ControllerContext.getTenantId();
        log.info("cid：[" + cid + "], flag：[" + flag + "]");
        Map<String, Object> map = new HashMap<String, Object>();
        if(null == tenantId){
            map.put(ItemConstants.SUCCESS_FLAG, false);
            map.put(ItemConstants.MESSAGE_FLAG, "平台id不能为空");
            jsonObject.put("result", map);
            return jsonObject.toString();
        }
        Long shopId = ControllerContext.getShopId();
        if(null == shopId){
            map.put(ItemConstants.SUCCESS_FLAG, false);
            map.put(ItemConstants.MESSAGE_FLAG, "店铺id不能为空");
            jsonObject.put("result", map);
            return jsonObject.toString();
        }
        if (!flag.equalsIgnoreCase("top") && !flag.equalsIgnoreCase("up") && !flag.equalsIgnoreCase("down") && !flag.equalsIgnoreCase("bottom")) {
            map.put(ItemConstants.SUCCESS_FLAG, false);
            map.put(ItemConstants.MESSAGE_FLAG, "参数有误，稍后再试");
            jsonObject.put("result", map);
            log.info(jsonObject.toString());
            return jsonObject.toString();
        }

        try {
            BaseResponseVo<Boolean> result= itemCategoryClient.categorySort(tenantId,shopId,cid, flag);
            map.put(ItemConstants.SUCCESS_FLAG, result.getIsSuccess());
            map.put(ItemConstants.MESSAGE_FLAG, result.getMessage());
        } catch (Exception e) {
            log.error("排序controller调用service时出现异常。", e);
            map.put(ItemConstants.MESSAGE_FLAG, "类目移动时出现异常！");
        }
        jsonObject.put("result", map);

        log.info(jsonObject.toString());
        return jsonObject.toString();
    }
}