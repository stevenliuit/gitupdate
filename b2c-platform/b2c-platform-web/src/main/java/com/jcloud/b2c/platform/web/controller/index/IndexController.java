/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: IndexController.java   project: b2c-jcloud
 * @creator: huangning1
 * @date: 2017/2/23
 */

package com.jcloud.b2c.platform.web.controller.index;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jcloud.b2c.mall.service.client.vo.MallFunctionTypeVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.jcloud.b2c.common.common.web.security.ControllerContext;
import com.jcloud.b2c.platform.util.RoleXmlParser;

/**
 *
 * @description: 主页controller
 * @author: huangning1
 * @requireNo: none
 * @createdate: 2017-02-23 20:05
 * @lastdate:
 */
@Controller
public class IndexController {
    private static final Logger log = LoggerFactory.getLogger(IndexController.class);

    @Value("${sso.logout.address}")
    private String logoutAddress;
    @Value("${sso.webapp.domain.name}")
    private String homeUrl;
    
    @Resource
    private RoleXmlParser roleXmlParser ;
    
    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
    public String index(ModelMap modelMap,HttpServletRequest request) {
        log.info("请求默认首页..");
        log.info("请求默认首页结束..");
        // 跳转到 当前用户所拥有的第一个列表
        String userErp = String.valueOf(request.getAttribute("userErp"));
        
        List<MallFunctionTypeVo> list =  roleXmlParser.getUserFunctions(userErp,false);
        if(list == null || list.size() == 0){
        	// 用户没有权限
        	return "forward:error";
        }
        // 获取用户权限的  第一个 url
        String url = list.get(0).getFunctions().get(0).getFuncUrl();
        return "forward:"+url;
    }

    /**
     * 用户退出系统
     * @return
     */
    @RequestMapping(value="logout",method = {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView logout(HttpServletRequest request,HttpServletResponse response) {
        log.info("请求退出登录页面..");
        /*String logoutUrl = (String)urlSet.get("logoutUrl");
        String homeUrl = (String)urlSet.get("homeModule");
        String toUrl = logoutUrl + "?ReturnUrl=" + homeUrl;
        try {
            response.sendRedirect(toUrl);
        } catch (IOException e) {
            log.error("退出系统异常:", e);
        }*/
        String userErp = String.valueOf(request.getAttribute("userErp"));
        Long tenantId = ControllerContext.getTenantId();
        // 清除权限信息
        roleXmlParser.clearCache(tenantId, userErp);
        
        
        String logoutUrl = logoutAddress;
        String toUrl = logoutUrl + "?ReturnUrl=" + homeUrl;
        try {
            response.sendRedirect(toUrl);
        } catch (IOException e) {
            log.error("退出系统异常:", e);
        }
        log.info("请求退出登录页面结束..");
        return null;
    }
}
