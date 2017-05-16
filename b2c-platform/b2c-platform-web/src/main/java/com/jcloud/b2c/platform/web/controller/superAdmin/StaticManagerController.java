package com.jcloud.b2c.platform.web.controller.superAdmin;

import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.common.common.web.security.ControllerContext;
import com.jcloud.b2c.platform.service.feign.CacheFeignClient;
import com.jcloud.b2c.platform.service.feign.MallIndexPageClient;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by shiyusen on 2017/3/29.
 */
@Controller
@RequestMapping("/super/static")
public class StaticManagerController {

    private static Logger logger= Logger.getLogger(StaticManagerController.class);

    @Resource
    private CacheFeignClient cacheFeignClient;

    @Autowired
    private MallIndexPageClient mallIndexPageClient;

    @RequestMapping(value = "/index",method = {RequestMethod.GET})
    public String index(Model model){
        model.addAttribute("tenantId",ControllerContext.getTenantId());
        return "/super/static";
    }

    @ResponseBody
    @RequestMapping(value = "/createIndexPage",method = {RequestMethod.GET,RequestMethod.POST})
    public BaseResponseVo<Void> staticIndexPage(@RequestParam Long tenantId){
        String error="";
        try {
            mallIndexPageClient.reloadTemplate();
            BaseResponseVo<Boolean> responseVo =  mallIndexPageClient.createIndexPage(tenantId);
            error=responseVo.getMessage();
            return new BaseResponseVo(responseVo.getIsSuccess(),"success");
        } catch (Exception e) {
            logger.error("静态化首页失败",e);
            error=e.getMessage();
        }
        return new BaseResponseVo(false,error);

    }
    @ResponseBody
    @RequestMapping(value = "/createMIndexPage",method = {RequestMethod.GET,RequestMethod.POST})
    public BaseResponseVo<String> createMIndexPage(@RequestParam Long tenantId){
        String error="";
        try {
            mallIndexPageClient.reloadTemplate();
            BaseResponseVo<String> responseVo =  mallIndexPageClient.createMIndexPage(tenantId);
            error=responseVo.getMessage();
            return new BaseResponseVo(responseVo.getIsSuccess(),"success");
        } catch (Exception e) {
            logger.error("静态化h5首页失败",e);
            error=e.getMessage();
        }
        return new BaseResponseVo(false,error);

    }
}
