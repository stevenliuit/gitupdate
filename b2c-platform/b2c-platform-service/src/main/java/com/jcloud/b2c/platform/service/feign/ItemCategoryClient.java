package com.jcloud.b2c.platform.service.feign;

import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.item.client.vo.category.CategoryResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

//@FeignClient(name="b2c-item-service",url="http://www.eureka2.com:18403")
@FeignClient("B2C-ITEM-SERVICE")
public interface ItemCategoryClient {
    @RequestMapping(value = "/itemCategory/queryCategoryInfo", method = POST)
    BaseResponseVo<List<CategoryResponse>> getCategoryList(@RequestParam("tenantId") Long tenantId, @RequestParam("parentCid") Long parentCid);

    @RequestMapping("itemCategory/addCategoryInfo")
    BaseResponseVo<Integer> insCategoryInfo(@RequestBody CategoryResponse record);

    @RequestMapping("itemCategory/modifyCategoryName")
    BaseResponseVo<Integer> modifyCategoryName(@RequestBody CategoryResponse record);

    @RequestMapping("itemCategory/delCategoryInfo")
    BaseResponseVo<Integer> delCategoryInfo(@RequestParam(value = "tenantId") Long tenantId, @RequestParam(value = "cid") Long cid);

    @RequestMapping("itemCategory/isExistsCategory")
    BaseResponseVo<Boolean> isExistsCategory(@RequestParam(value = "tenantId") Long tenantId,@RequestParam(value = "cid") Long cid,@RequestParam(value = "parentCid") Long parentCid);

    @RequestMapping("itemCategory/categorySort")
    BaseResponseVo<Boolean> categorySort(@RequestParam("tenantId") Long tenantId,@RequestParam("shopId") Long shopId ,@RequestParam("cid") Long cid,@RequestParam("flag") String flag);
}