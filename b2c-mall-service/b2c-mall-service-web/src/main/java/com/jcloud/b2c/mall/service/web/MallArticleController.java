package com.jcloud.b2c.mall.service.web;

import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.mall.service.domain.MallArticle;
import com.jcloud.b2c.mall.service.service.MallArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description:文章controller
 * @author: YueZheng
 * @requireNo:
 * @createdate: 2017/3/2 20:13
 * @lasdate
 */
@RestController
@RequestMapping("/mallArticle")
public class MallArticleController {

    @Autowired
    private MallArticleService mallArticleService;

    @RequestMapping(value = "/get" ,method = RequestMethod.GET)
    public BaseResponseVo<MallArticle> getById(@RequestParam Long id, @RequestParam Long tenantId){
        BaseResponseVo<MallArticle> responseVo = new BaseResponseVo<MallArticle>();
        MallArticle mallBanner = mallArticleService.get(id, tenantId);
        responseVo.setIsSuccess(true);
        responseVo.setData(mallBanner);
        return responseVo;
    }

    @RequestMapping(value = "/query" ,method = RequestMethod.POST)
    public BaseResponseVo<List<MallArticle>> query(@RequestBody MallArticle mallBanner){
        BaseResponseVo<List<MallArticle>> responseVo = new BaseResponseVo<List<MallArticle>>();
        List<MallArticle> list = mallArticleService.query(mallBanner);
        responseVo.setIsSuccess(true);
        responseVo.setData(list);
        return responseVo;
    }

    @RequestMapping(value = "/update" ,method = RequestMethod.POST)
    public BaseResponseVo<Void> update(@RequestBody MallArticle mallBanner){
        BaseResponseVo<Void> responseVo = new BaseResponseVo<Void>();
        boolean success = mallArticleService.update(mallBanner);
        responseVo.setIsSuccess(success);
        return responseVo;
    }

    @RequestMapping(value = "/add" ,method = RequestMethod.POST)
    public BaseResponseVo<Void> add(@RequestBody MallArticle mallBanner){
        BaseResponseVo<Void> responseVo = new BaseResponseVo<Void>();
        boolean success = mallArticleService.add(mallBanner);
        responseVo.setIsSuccess(success);
        return responseVo;
    }

    @RequestMapping(value = "/delete" ,method = RequestMethod.POST)
    public BaseResponseVo<Void> delete(@RequestBody MallArticle mallBanner){
        BaseResponseVo<Void> responseVo = new BaseResponseVo<Void>();
        boolean success = mallArticleService.delete(mallBanner);
        responseVo.setIsSuccess(success);
        return responseVo;
    }
}
