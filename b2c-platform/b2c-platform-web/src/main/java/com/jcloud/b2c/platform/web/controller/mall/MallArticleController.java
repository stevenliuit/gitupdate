package com.jcloud.b2c.platform.web.controller.mall;

import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.common.common.web.security.ControllerContext;
import com.jcloud.b2c.mall.service.client.enums.StateEnum;
import com.jcloud.b2c.mall.service.client.vo.MallArticleCategoryVo;
import com.jcloud.b2c.mall.service.client.vo.MallArticleVo;
import com.jcloud.b2c.platform.service.feign.MallArticleCategoryClient;
import com.jcloud.b2c.platform.service.feign.MallArticleClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * @description:文章管理controller
 * @author: YueZheng
 * @requireNo:
 * @createdate: 2017/3/2 20:43
 * @lasdate
 */
@Controller
@RequestMapping("/mall/mallArticle")
public class MallArticleController {

    @Autowired
    private MallArticleClient mallArticleClient;

    @Autowired
    private MallArticleCategoryClient mallArticleCategoryClient;

    /**
     * 跳转文章类目管理页面
     * @param modelMap
     * @param mallArticle
     * @return
     */
    @RequestMapping(value = "/toCategoryManage", method = {RequestMethod.GET, RequestMethod.POST})
    public String toCategoryManage (ModelMap modelMap, MallArticleVo mallArticle){
        Long tenantId = ControllerContext.getTenantId();
        mallArticle.setTenantId(tenantId);
        BaseResponseVo<List<MallArticleVo>> articles = mallArticleClient.query(mallArticle);
        MallArticleCategoryVo mallArticleCategoryVo = new MallArticleCategoryVo();
        mallArticleCategoryVo.setTenantId(tenantId);
        mallArticleCategoryVo.setParentId(0L);
        mallArticleCategoryVo.setState(StateEnum.ON_SHELF.getValue());
        BaseResponseVo<List<MallArticleCategoryVo>> categorys = mallArticleCategoryClient.query(mallArticleCategoryVo);

        modelMap.addAttribute("articles",articles.getData());
        modelMap.addAttribute("categorys",categorys.getData());
        modelMap.addAttribute("article",mallArticle);
        return "/articleManage/articleCategoryManage";
    }

    /**
     * 跳转文章管理页面
     * @param modelMap
     * @param mallArticle
     * @return
     */
    @RequestMapping(value = "/toArticleManage", method = {RequestMethod.GET, RequestMethod.POST})
    public String toArticleManage (ModelMap modelMap, MallArticleVo mallArticle){
        Long tenantId = ControllerContext.getTenantId();
        mallArticle.setTenantId(tenantId);
        BaseResponseVo<List<MallArticleVo>> articles = mallArticleClient.query(mallArticle);
        MallArticleCategoryVo mallArticleCategoryVo = new MallArticleCategoryVo();
        mallArticleCategoryVo.setTenantId(tenantId);
        mallArticleCategoryVo.setParentId(0L);
        mallArticleCategoryVo.setState(StateEnum.ON_SHELF.getValue());
        BaseResponseVo<List<MallArticleCategoryVo>> categorys = mallArticleCategoryClient.query(mallArticleCategoryVo);

        modelMap.addAttribute("articles",articles.getData());
        modelMap.addAttribute("categorys",categorys.getData());
        modelMap.addAttribute("article",mallArticle);
        return "/articleManage/articleManage";
    }

    /**
     * 编辑文章
     * @param modelMap
     * @param id
     * @return
     */
    @RequestMapping(value = "/toEdit", method = {RequestMethod.GET, RequestMethod.POST})
    public String toEdit(ModelMap modelMap, Long id){
        Long tenantId = ControllerContext.getTenantId();
        BaseResponseVo<MallArticleVo> result = mallArticleClient.get(id, tenantId);
        MallArticleCategoryVo mallArticleCategoryVo = new MallArticleCategoryVo();
        mallArticleCategoryVo.setTenantId(tenantId);
        mallArticleCategoryVo.setParentId(0L);
        mallArticleCategoryVo.setState(StateEnum.ON_SHELF.getValue());
        BaseResponseVo<List<MallArticleCategoryVo>> categorys = mallArticleCategoryClient.query(mallArticleCategoryVo);
        BaseResponseVo<MallArticleCategoryVo> childCategory = mallArticleCategoryClient.get(result.getData().getCategoryId(), tenantId);
        modelMap.addAttribute("article",result.getData());
        modelMap.addAttribute("categorys", categorys.getData());
        modelMap.addAttribute("childCategory", childCategory.getData());
        return "/articleManage/articleEdit";
    }

    /**
     * 保存文章编辑
     * @param mallArticle
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public boolean update(MallArticleVo mallArticle){
        Long tenantId = ControllerContext.getTenantId();
        mallArticle.setTenantId(tenantId);
        BaseResponseVo<Void> result = mallArticleClient.update(mallArticle);
        return result.isSuccess();
    }

    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public boolean add(MallArticleVo mallArticle){
        Long tenantId = ControllerContext.getTenantId();
        mallArticle.setTenantId(tenantId);
        BaseResponseVo<Void> result = mallArticleClient.update(mallArticle);
        return result.isSuccess();
    }

    /**
     * 发布下线操作
     * @param mallArticle
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateState", method = RequestMethod.POST)
    public boolean updateState(MallArticleVo mallArticle){
        Long tenantId = ControllerContext.getTenantId();
        mallArticle.setTenantId(tenantId);
        if (mallArticle.getState()==StateEnum.OFF_SHELF.getValue()){
            mallArticle.setNewstime(new Date());
        }
        BaseResponseVo<Void> result = mallArticleClient.update(mallArticle);
        return result.isSuccess();
    }

    /**
     * 跳转新增文章页面
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/toArticleInsert", method = {RequestMethod.POST,RequestMethod.GET})
    public String toArticleInsert(ModelMap modelMap){
        Long tenantId = ControllerContext.getTenantId();
        MallArticleCategoryVo mallArticleCategoryVo = new MallArticleCategoryVo();
        mallArticleCategoryVo.setTenantId(tenantId);
        mallArticleCategoryVo.setParentId(0L);
        mallArticleCategoryVo.setState(StateEnum.ON_SHELF.getValue());
        BaseResponseVo<List<MallArticleCategoryVo>> categorys = mallArticleCategoryClient.query(mallArticleCategoryVo);
        modelMap.addAttribute("categorys", categorys.getData());
        return "/articleManage/articleInsert";
    }
}
