/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: GlobalExceptionHandler.java project: jcloud-b2c-mall-web
 * @creator: wangxin17
 * @date: 2017/2/15
 */
package com.jcloud.b2c.platform.web.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @description: 全局异常处理
 * @author: wangxin17
 * @requireNo:
 * @createdate: 2017-02-15 20:56
 * @lastdate:
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    public static final String DEFAULT_ERROR_VIEW = "common/error";

    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        LOGGER.error("error path :" + req.getRequestURI(), e);
        ModelAndView mav = new ModelAndView();
        mav.setViewName(DEFAULT_ERROR_VIEW);
        return mav;
    }

}
