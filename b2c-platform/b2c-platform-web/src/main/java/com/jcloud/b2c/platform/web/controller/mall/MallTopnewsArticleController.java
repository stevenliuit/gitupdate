package com.jcloud.b2c.platform.web.controller.mall;

import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.common.common.web.security.ControllerContext;
import com.jcloud.b2c.mall.service.client.vo.MallTopnewsArticleVo;
import com.jcloud.b2c.platform.service.feign.MallTopnewsArticleClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @description:app头条管理
 * @author: YueZheng
 * @requireNo:
 * @createdate: 2017/3/6 11:04
 * @lasdate
 */
@Controller
@RequestMapping("/mall/mallTopnewsArticle")
public class MallTopnewsArticleController {

    @Autowired
    private MallTopnewsArticleClient mallTopnewsArticleClient;

    /**
     * 获取头条
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getTopnews" ,method = {RequestMethod.GET, RequestMethod.POST})
    public MallTopnewsArticleVo getTopnews(@RequestParam Long id){
        Long tenantId = ControllerContext.getTenantId();
        BaseResponseVo<MallTopnewsArticleVo> result = mallTopnewsArticleClient.get(id, tenantId);
        return result.getData();
    }

    /**
     * 删除头条
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteTopnews" ,method = {RequestMethod.POST})
    public boolean deleteTopnews(@RequestParam Long id){
        Long tenantId = ControllerContext.getTenantId();
        MallTopnewsArticleVo mallTopnewsArticleVo = new MallTopnewsArticleVo();
        mallTopnewsArticleVo.setTenantId(tenantId);
        mallTopnewsArticleVo.setId(id);
        BaseResponseVo<Void> result = mallTopnewsArticleClient.delete(mallTopnewsArticleVo);
        return result.isSuccess();
    }

    /**
     * 更新头条
     * @param mallTopnewsArticleVo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateTopnews" ,method = {RequestMethod.POST})
    public boolean updateTopnews(MallTopnewsArticleVo mallTopnewsArticleVo){
        Long tenantId = ControllerContext.getTenantId();
        mallTopnewsArticleVo.setTenantId(tenantId);
        Long articleId = mallTopnewsArticleVo.getArticleId();
        mallTopnewsArticleVo.setArticleId(articleId==null?(long)0:articleId);
        String articleUrl = mallTopnewsArticleVo.getArticleUrl();
        mallTopnewsArticleVo.setArticleUrl(articleUrl==null?"":articleUrl);
        BaseResponseVo<Void> result = mallTopnewsArticleClient.update(mallTopnewsArticleVo);
        return result.isSuccess();
    }

    /**
     * 增加头条
     * @param mallTopnewsArticleVo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addTopnews" ,method = {RequestMethod.POST})
    public boolean addTopnews(MallTopnewsArticleVo mallTopnewsArticleVo){
        Long tenantId = ControllerContext.getTenantId();
        mallTopnewsArticleVo.setTenantId(tenantId);
        Long articleId = mallTopnewsArticleVo.getArticleId();
        mallTopnewsArticleVo.setArticleId(articleId==null?(long)0:articleId);
        String articleUrl = mallTopnewsArticleVo.getArticleUrl();
        mallTopnewsArticleVo.setArticleUrl(articleUrl==null?"":articleUrl);
        BaseResponseVo<Void> result = mallTopnewsArticleClient.add(mallTopnewsArticleVo);
        return result.isSuccess();
    }
}
