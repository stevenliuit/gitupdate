package com.jcloud.b2c.platform.service.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

//@FeignClient(name="b2c-working-center-local", url="http://localhost:8878")
@FeignClient("B2C-WORKING-CENTER")
public interface RunJobDetailClient {
    /**
     *重新执行导入错误的任务
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "runJobDetail")
    String runJobDetail(@RequestParam(value = "id") Long id);
}
