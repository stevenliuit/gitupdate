package com.jcloud.b2c.platform.common;

import com.jcloud.b2c.common.common.util.HttpUtils;
import com.jcloud.b2c.common.common.util.IpUtils;
import com.jcloud.b2c.common.common.web.security.ControllerContext;
import com.jcloud.b2c.platform.common.sso.UserInfo;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: wukun
 * @requireNo:
 * @createdate: 2017-02-15 10:45
 * @lastdate:
 */

public class PlatformInterceptor extends HandlerInterceptorAdapter {
    @Value("${sso.login.address}")
    private String loginAddress;
    @Value("${sso.verify.ticket.address}")
    private String verifyTicketAddress;
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 公共变量basePath
        buildBasePath(request, response);
        String sso_service_ticket = "";
        String requestIp = IpUtils.getIpAddress(request);
        String returnUri = request.getRequestURL().toString();
        String queryString = request.getQueryString();
        if (queryString != null){
            returnUri += queryString;
        }
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0){
            for (Cookie cookie:cookies){
                if(cookie.getName().equals("sso.jd.com")){
                    sso_service_ticket = cookie.getValue();
                }
            }
        }
        if (sso_service_ticket == null || sso_service_ticket.equals("")){
            response.sendRedirect(loginAddress + "?ReturnUrl=" + returnUri);
            return true;
        }
        try{
            Map<String, String> pars = new HashMap<String, String>();
            pars.put("ticket", sso_service_ticket);
            pars.put("url", returnUri);
            pars.put("ip", requestIp);
            String resultStr = HttpUtils.postMethod(verifyTicketAddress, pars);
            JSONObject json = JSONObject.fromObject(resultStr);
            boolean flag= json.getBoolean("REQ_FLAG");
            if (flag == true){
                UserInfo userInfo = null;
                JSONObject object = json.getJSONObject("REQ_DATA");
                userInfo = (UserInfo)JSONObject.toBean(object,UserInfo.class);
                Long userId = userInfo.getUserId();
                String userErp = userInfo.getUsername();
                String userName = userInfo.getFullname();
                request.setAttribute("userName", userName);
                request.setAttribute("userErp", userErp);
                ControllerContext.setUserId(userId);
                ControllerContext.setUserName(userName);
            }else {
                response.sendRedirect(loginAddress + "?ReturnUrl=" + returnUri);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
    private void buildBasePath(HttpServletRequest request, HttpServletResponse response) {
        String basePath = "//" + request.getServerName();
        if (request.getServerPort() != 80) {
            basePath += ":" + request.getServerPort();
        }
        basePath += request.getContextPath();
        request.setAttribute("basePath", basePath);
    }
}

