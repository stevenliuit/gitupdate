package com.jcloud.b2c.platform.service.feign;

import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @description:
 * @author: YueZheng
 * @requireNo:
 * @createdate: 2017/3/30 15:36
 * @lasdate
 */
//@FeignClient(name = "b2c-mall-service",url = "http://localhost:8083/")
@FeignClient(name = "b2c-mall-service")
public interface CdnDirClient {

    @RequestMapping(value = "/tenant/cdnPurgeDir" ,method = RequestMethod.GET)
    BaseResponseVo<String> cdnPurgeDir(@RequestParam("urls") String urls);

    @RequestMapping(value = "/tenant/cdnPurgeAllDir", method = RequestMethod.GET)
    public BaseResponseVo<Void> cdnPurgeAllDir();
}
