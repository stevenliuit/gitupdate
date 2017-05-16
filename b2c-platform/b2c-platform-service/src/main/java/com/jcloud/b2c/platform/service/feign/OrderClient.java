package com.jcloud.b2c.platform.service.feign;

import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.order.client.vo.order.ListOrderResult;
import com.jcloud.b2c.platform.domain.vo.ListOrderParamVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @description:
 * @author: YueZheng
 * @requireNo:
 * @createdate: 2017/3/30 15:36
 * @lasdate
 */
//@FeignClient(name = "b2c-order-service",url = "http://localhost:18344/")
@FeignClient(name = "b2c-order-service")
public interface OrderClient {


    @RequestMapping(value = "/order/findOrderList4Manager",method = RequestMethod.GET)
    public BaseResponseVo<ListOrderResult> findOrderList4Manager(@RequestBody ListOrderParamVo listOrderParam);
}
