package com.jcloud.b2c.mall.service.web;

import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.mall.service.domain.MallArticleCategory;
import com.jcloud.b2c.mall.service.service.MallArticleCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description:
 * @author: YueZheng
 * @requireNo:
 * @createdate: 2017/3/3 10:35
 * @lasdate
 */
@RestController
@RequestMapping("/mallArticleCategory")
public class MallArticleCategoryController {
    
    @Autowired
    private MallArticleCategoryService mallArticleCategoryService;

    @RequestMapping(value = "/get" ,method = RequestMethod.GET)
    public BaseResponseVo<MallArticleCategory> getById(@RequestParam Long id, @RequestParam Long tenantId){
        BaseResponseVo<MallArticleCategory> responseVo = new BaseResponseVo<MallArticleCategory>();
        MallArticleCategory mallArticleCategory = mallArticleCategoryService.get(id, tenantId);
        responseVo.setIsSuccess(true);
        responseVo.setData(mallArticleCategory);
        return responseVo;
    }

    @RequestMapping(value = "/query" ,method = RequestMethod.POST)
    public BaseResponseVo<List<MallArticleCategory>> query(@RequestBody MallArticleCategory mallArticleCategory){
        BaseResponseVo<List<MallArticleCategory>> responseVo = new BaseResponseVo<List<MallArticleCategory>>();
        List<MallArticleCategory> list = mallArticleCategoryService.query(mallArticleCategory);
        responseVo.setIsSuccess(true);
        responseVo.setData(list);
        return responseVo;
    }

    @RequestMapping(value = "/update" ,method = RequestMethod.POST)
    public BaseResponseVo<Void> update(@RequestBody MallArticleCategory mallArticleCategory){
        BaseResponseVo<Void> responseVo = new BaseResponseVo<Void>();
        boolean success = mallArticleCategoryService.update(mallArticleCategory);
        responseVo.setIsSuccess(success);
        return responseVo;
    }

    @RequestMapping(value = "/add" ,method = RequestMethod.POST)
    public BaseResponseVo<Void> add(@RequestBody MallArticleCategory mallArticleCategory){
        BaseResponseVo<Void> responseVo = new BaseResponseVo<Void>();
        boolean success = mallArticleCategoryService.add(mallArticleCategory);
        responseVo.setIsSuccess(success);
        return responseVo;
    }

    @RequestMapping(value = "/delete" ,method = RequestMethod.POST)
    public BaseResponseVo<Void> delete(@RequestBody MallArticleCategory mallArticleCategory){
        BaseResponseVo<Void> responseVo = new BaseResponseVo<Void>();
        boolean success = mallArticleCategoryService.delete(mallArticleCategory);
        responseVo.setIsSuccess(success);
        return responseVo;
    }
}
