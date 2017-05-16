package com.jcloud.b2c.platform.service.feign;

import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.item.client.vo.item.BrandVo;
import com.jcloud.b2c.item.client.vo.item.QueryBrandRelCriteria;
import com.jcloud.b2c.item.client.vo.item.QueryBrandRelResponse;
import com.jcloud.b2c.item.client.vo.item.UpdateBrandRelState;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @description: 品牌服务Client
 * @author: wukun
 * @requireNo:
 * @createdate: 2017-02-24 14:32
 * @lastdate:
 */
//@FeignClient(name="B2C-ITEM-SERVICE", url="http://www.eureka2.com:18403")
@FeignClient("B2C-ITEM-SERVICE")
public interface ItemBrandClient {
    /**
     * 查询平台有效品牌列表
     *
     * @param tenantId
     * @return
     */
    @RequestMapping("/getItemBrandList")
    public BaseResponseVo<List<BrandVo>> getItemBrandList(@RequestParam(value = "tenantId") Long tenantId);
    /**
     * 查询品牌客服关联信息
     *
     * @param criteria
     * @return
     */
    @RequestMapping(value = "/queryBrandRelList", method = RequestMethod.POST)
    public BaseResponseVo<QueryBrandRelResponse> queryBrandRelList(@RequestBody QueryBrandRelCriteria criteria);
    /**
     * 开启停用客服
     *
     * @param state
     * @return
     */
    @RequestMapping(value = "/updateCustomerRelState", method = RequestMethod.POST)
    public BaseResponseVo updateCustomerRelState(@RequestBody UpdateBrandRelState state);
}
