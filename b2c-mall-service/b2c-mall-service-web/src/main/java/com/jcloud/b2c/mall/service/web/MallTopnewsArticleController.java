package com.jcloud.b2c.mall.service.web;

import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.mall.service.domain.MallTopnewsArticle;
import com.jcloud.b2c.mall.service.service.MallTopnewsArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description:
 * @author: YueZheng
 * @requireNo:
 * @createdate: 2017/3/6 10:20
 * @lasdate
 */
@RestController
@RequestMapping("/mallTopnewsArticle")
public class MallTopnewsArticleController {

    @Autowired
    private MallTopnewsArticleService mallTopnewsArticleService;

    @RequestMapping(value = "/get" ,method = RequestMethod.GET)
    public BaseResponseVo<MallTopnewsArticle> getById(@RequestParam Long id, @RequestParam Long tenantId){
        BaseResponseVo<MallTopnewsArticle> responseVo = new BaseResponseVo<MallTopnewsArticle>();
        MallTopnewsArticle mallBanner = mallTopnewsArticleService.get(id, tenantId);
        responseVo.setIsSuccess(true);
        responseVo.setData(mallBanner);
        return responseVo;
    }

    @RequestMapping(value = "/query" ,method = RequestMethod.POST)
    public BaseResponseVo<List<MallTopnewsArticle>> query(@RequestBody MallTopnewsArticle mallBanner){
        BaseResponseVo<List<MallTopnewsArticle>> responseVo = new BaseResponseVo<List<MallTopnewsArticle>>();
        List<MallTopnewsArticle> list = mallTopnewsArticleService.query(mallBanner);
        responseVo.setIsSuccess(true);
        responseVo.setData(list);
        return responseVo;
    }

    @RequestMapping(value = "/update" ,method = RequestMethod.POST)
    public BaseResponseVo<Void> update(@RequestBody MallTopnewsArticle mallBanner){
        BaseResponseVo<Void> responseVo = new BaseResponseVo<Void>();
        boolean success = mallTopnewsArticleService.update(mallBanner);
        responseVo.setIsSuccess(success);
        return responseVo;
    }

    @RequestMapping(value = "/add" ,method = RequestMethod.POST)
    public BaseResponseVo<Void> add(@RequestBody MallTopnewsArticle mallBanner){
        BaseResponseVo<Void> responseVo = new BaseResponseVo<Void>();
        boolean success = mallTopnewsArticleService.add(mallBanner);
        responseVo.setIsSuccess(success);
        return responseVo;
    }

    @RequestMapping(value = "/delete" ,method = RequestMethod.POST)
    public BaseResponseVo<Void> delete(@RequestBody MallTopnewsArticle mallBanner){
        BaseResponseVo<Void> responseVo = new BaseResponseVo<Void>();
        boolean success = mallTopnewsArticleService.delete(mallBanner);
        responseVo.setIsSuccess(success);
        return responseVo;
    }
}
