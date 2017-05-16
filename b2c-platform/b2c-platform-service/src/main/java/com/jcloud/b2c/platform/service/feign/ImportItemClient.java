package com.jcloud.b2c.platform.service.feign;

import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.item.client.vo.item.ImportItemVoForOpen;
import com.jcloud.b2c.item.client.vo.item.ItemImportTaskResult;
import com.jcloud.b2c.item.client.vo.item.ItemImportTaskVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @description: 导入商品模板服务
 * @author: wukun
 * @requireNo:
 * @createdate: 2017-02-17 14:09
 * @lastdate:
 */
//@FeignClient(name="B2C-ITEM-SERVICE", url="http://localhost:8666")
@FeignClient("B2C-ITEM-SERVICE")
public interface ImportItemClient {
    @RequestMapping(value = "/isExistsSkuForImport", method = RequestMethod.POST)
    BaseResponseVo<Boolean> isExistsSkuForImport(@RequestParam(value = "tenantId") Long tenantId,@RequestParam(value = "shopId") Long shopId,@RequestParam(value = "skuId") Long skuId);
    @RequestMapping(value = "/addImportItems", method = RequestMethod.POST)
    public BaseResponseVo<Boolean> addImportItems(List<ImportItemVoForOpen> importItemVoForOpens);

    //导入任务列表
    @RequestMapping("/findItemImportTask")
    BaseResponseVo<ItemImportTaskResult> findItemImportTask(@RequestBody ItemImportTaskVo itemImportTaskVo);

    @RequestMapping(value = "/deleteItems", method = RequestMethod.POST)
    BaseResponseVo deleteItems(@RequestParam(value = "tenantId") Long tenantId,@RequestParam(value = "shopId") Long shopId,@RequestBody List<Long> skuList);
}
