/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: MallCategoryController.java project: jcloud-b2c-mall-service
 * @creator: lidongxing
 * @date: 2017/2/16
 */

package com.jcloud.b2c.mall.service.web;

import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.mall.service.client.enums.StateEnum;
import com.jcloud.b2c.mall.service.domain.MallCategory;
import com.jcloud.b2c.mall.service.service.MallCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @description: 类目CONTROLLER
 * @author: lidongxing
 * @requireNo:
 * @createdate: 2017-02-16 14:51
 * @lastdate:
 */

@RestController
@RequestMapping("/mallCategory")
public class MallCategoryController {

    @Autowired
    private MallCategoryService mallCategoryService;

    @RequestMapping(value = "/get" ,method = RequestMethod.GET)
    public BaseResponseVo<MallCategory> getById(@RequestParam Long id, @RequestParam Long tenantId){
        BaseResponseVo<MallCategory> responseVo = new BaseResponseVo<MallCategory>();
        MallCategory mallCategory = mallCategoryService.get(id, tenantId);
        responseVo.setIsSuccess(true);
        responseVo.setData(mallCategory);
        return responseVo;
    }

    @RequestMapping(value = "/query" ,method = RequestMethod.POST)
    public BaseResponseVo<List<MallCategory>> query(@RequestBody MallCategory mallCategory){
        BaseResponseVo<List<MallCategory>> responseVo = new BaseResponseVo<List<MallCategory>>();
        List<MallCategory> list = mallCategoryService.query(mallCategory);
        responseVo.setIsSuccess(true);
        responseVo.setData(list);
        return responseVo;
    }

    @RequestMapping(value = "/update" ,method = RequestMethod.POST)
    public BaseResponseVo<Void> update(@RequestBody MallCategory mallCategory, @RequestParam Integer beforeSort){
        BaseResponseVo<Void> responseVo = new BaseResponseVo<Void>();
        boolean success = mallCategoryService.update(mallCategory, beforeSort);
        responseVo.setIsSuccess(success);
        return responseVo;
    }

    @RequestMapping(value = "/add" ,method = RequestMethod.POST)
    public BaseResponseVo<Void> add(@RequestBody MallCategory mallCategory){
        BaseResponseVo<Void> responseVo = new BaseResponseVo<Void>();
        boolean success = mallCategoryService.add(mallCategory);
        responseVo.setIsSuccess(success);
        return responseVo;
    }

    @RequestMapping(value = "/delete" ,method = RequestMethod.POST)
    public BaseResponseVo<Void> delete(@RequestBody MallCategory mallCategory){
        BaseResponseVo<Void> responseVo = new BaseResponseVo<Void>();
        boolean success = mallCategoryService.delete(mallCategory);
        responseVo.setIsSuccess(success);
        return responseVo;
    }

    @RequestMapping(value = "/getCategoryFromCache" ,method = RequestMethod.GET)
    public BaseResponseVo<MallCategory> getCategoryFromCache(@RequestParam Long id, @RequestParam Long tenantId){
        BaseResponseVo<MallCategory> responseVo = new BaseResponseVo();
        MallCategory category = mallCategoryService.getByIdFromCache(tenantId, id);
        responseVo.setData(category);
        responseVo.setIsSuccess(true);
        return responseVo;
    }
    @RequestMapping(value = "/isUniqueName" , method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Boolean isUniqueName(HttpServletRequest request,@RequestBody MallCategory mallCategoryVo) {
        mallCategoryVo.setState(StateEnum.ON_SHELF.getValue());
        Boolean isUnique = mallCategoryService.isUniqueName(mallCategoryVo);
        return isUnique;
    }

    @RequestMapping(value = "/verifySortNum" , method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Boolean verifySortNum(HttpServletRequest request,@RequestBody MallCategory mallCategory) {
        mallCategory.setState(StateEnum.ON_SHELF.getValue());
        Boolean isUnique = mallCategoryService.verifySortNum(mallCategory);
        return isUnique;
    }

    @RequestMapping(value = "/move" ,method = {RequestMethod.POST})
    @ResponseBody
    public BaseResponseVo<Void> move_cate(@RequestBody MallCategory mallCategoryVo,@RequestParam String action){
        BaseResponseVo<Void> baseResponseVo=mallCategoryService.moveCate(mallCategoryVo, action);
        return baseResponseVo;
    }

    @RequestMapping(value = "/updateMoveUp" ,method = {RequestMethod.POST})
    @ResponseBody
    public BaseResponseVo<Void> updateMoveUp(@RequestBody MallCategory mallCategory,@RequestParam String action){
        BaseResponseVo<Void> baseResponseVo=mallCategoryService.updateMoveUp(mallCategory,action);
        return baseResponseVo;
    }
}
