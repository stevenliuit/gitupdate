package com.jcloud.b2c.platform.service.feign;

import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.item.client.vo.item.PlatformQuerySkuListCriteria;
import com.jcloud.b2c.item.client.vo.item.PlatformQuerySkuListResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @description: 商品列表服务
 * @author: wukun
 * @requireNo:
 * @createdate: 2017-02-20 16:57
 * @lastdate:
 */
//@FeignClient(name="B2C-ITEM-SERVICE", url="http://www.eureka2.com:18403")
@FeignClient("B2C-ITEM-SERVICE")
public interface ItemListClient {
    @RequestMapping(value = "/platformQueryItemList", method = RequestMethod.POST)
    public BaseResponseVo<PlatformQuerySkuListResponse> platformQueryItemList(PlatformQuerySkuListCriteria criteria);
}
