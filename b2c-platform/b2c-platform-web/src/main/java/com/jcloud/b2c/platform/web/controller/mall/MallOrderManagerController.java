package com.jcloud.b2c.platform.web.controller.mall;

import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.common.common.web.security.ControllerContext;
import com.jcloud.b2c.order.client.dictionary.OrderStateEnum;
import com.jcloud.b2c.order.client.vo.order.ListOrderResult;
import com.jcloud.b2c.platform.domain.vo.ListOrderParamVo;
import com.jcloud.b2c.platform.service.feign.OrderClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shiyusen on 2017/4/13.
 */
@Controller
@RequestMapping("/mall/order")
public class MallOrderManagerController {

    private static Map<Integer,String> orders=new HashMap<Integer,String>();
    static{
        OrderStateEnum[] ods = OrderStateEnum.values();
        for(int index=0;index<ods.length;index++){
            OrderStateEnum order = ods[index];
            orders.put(order.getCode(),order.getName());
        }
    }
    @Autowired
    private OrderClient orderClient;

    @RequestMapping(value = "/index")
    public String index(Model model){
        model.addAttribute("orders",orders);
        return "order/orderList";
    }

    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public BaseResponseVo<ListOrderResult> list(ListOrderParamVo listOrderParam){
        listOrderParam.getUserRequest().setTenantId(ControllerContext.getTenantId());
        BaseResponseVo<ListOrderResult> result = orderClient.findOrderList4Manager(listOrderParam);
        return result;
    }
}
