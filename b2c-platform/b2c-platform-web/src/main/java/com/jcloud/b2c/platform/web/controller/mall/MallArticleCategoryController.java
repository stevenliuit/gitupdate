package com.jcloud.b2c.platform.web.controller.mall;

import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.common.common.web.security.ControllerContext;
import com.jcloud.b2c.mall.service.client.enums.StateEnum;
import com.jcloud.b2c.mall.service.client.vo.MallArticleCategoryVo;
import com.jcloud.b2c.platform.service.feign.MallArticleCategoryClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @description:文章分类controller
 * @author: YueZheng
 * @requireNo:
 * @createdate: 2017/3/3 11:48
 * @lasdate
 */
@Controller
@RequestMapping("/mall/mallArticleCategory")
public class MallArticleCategoryController {

    @Autowired
    private MallArticleCategoryClient mallArticleCategoryClient;

    /**
     * 查询文章分类
     * @param mallArticleCategory
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryCategory", method = RequestMethod.POST)
    public List<MallArticleCategoryVo> queryCategory(MallArticleCategoryVo mallArticleCategory){
        Long tenantId = ControllerContext.getTenantId();
        mallArticleCategory.setTenantId(tenantId);
        mallArticleCategory.setState(StateEnum.ON_SHELF.getValue());
        BaseResponseVo<List<MallArticleCategoryVo>> result = mallArticleCategoryClient.query(mallArticleCategory);
        return result.getData();
    }

    /**
     * 增加
     * @param mallArticleCategoryVo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public boolean add(MallArticleCategoryVo mallArticleCategoryVo){
        Long tenantId = ControllerContext.getTenantId();
        mallArticleCategoryVo.setTenantId(tenantId);
        mallArticleCategoryVo.setState(StateEnum.ON_SHELF.getValue());
        BaseResponseVo<Void> result = mallArticleCategoryClient.add(mallArticleCategoryVo);
        return result.isSuccess();
    }
}
