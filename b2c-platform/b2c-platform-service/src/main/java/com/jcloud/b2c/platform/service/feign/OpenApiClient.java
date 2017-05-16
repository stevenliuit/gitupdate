package com.jcloud.b2c.platform.service.feign;

import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @description: 调度中心Client
 * @author: wukun
 * @requireNo:
 * @createdate: 2017-02-21 14:36
 * @lastdate:
 */
//@FeignClient(name = "b2c-common-service",url = "http://localhost:8878")
@FeignClient(name="B2C-COMMON-SERVICE")
public interface OpenApiClient {

    @RequestMapping(method = RequestMethod.POST,value = "/api")
    public String api(@RequestBody String data);
}


