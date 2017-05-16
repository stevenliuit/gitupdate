package com.jcloud.b2c.mall.service.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by shiyusen on 2017/4/6.
 */
@RestController
public class IndexController {

    @RequestMapping("/index")
    public String index(){
        return "mall-service is ok";
    }
}
