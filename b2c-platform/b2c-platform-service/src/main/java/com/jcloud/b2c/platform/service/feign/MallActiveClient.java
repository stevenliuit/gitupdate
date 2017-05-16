package com.jcloud.b2c.platform.service.feign;


import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.mall.service.client.vo.MallActiveVo;


import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by issuser on 2017/3/3.
 */
//@FeignClient(name="b2c-mall-service", url = "http://localhost:8083/")
@FeignClient(name="b2c-mall-service")
public interface MallActiveClient {

    @RequestMapping(value = "/mallActive/query", method = POST)
    BaseResponseVo<List<MallActiveVo>> queryActiveList(@RequestBody MallActiveVo mallActive);

    @RequestMapping(value = "/mallActive/add", method = POST)
    BaseResponseVo<Void> addActive(@RequestBody MallActiveVo mallActive);

    @RequestMapping(value = "/mallActive/get", method = GET)
    BaseResponseVo<MallActiveVo> getById(@RequestParam(value = "id") Long id, @RequestParam(value = "tenantId") Long tenantId);

    @RequestMapping(value = "/mallActive/update", method = POST)
    BaseResponseVo<Void> update(@RequestBody MallActiveVo mallActive);

    @RequestMapping(value = "/mallActive/delete", method = POST)
    BaseResponseVo<Void> delete(@RequestBody MallActiveVo mallActive);

    @RequestMapping(value = "/mallActive/getByCode", method = POST)
    BaseResponseVo<MallActiveVo> getByCode(@RequestBody MallActiveVo mallActiveVo);
}
