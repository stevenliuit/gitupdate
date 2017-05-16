package com.jcloud.b2c.mall.service.web;

import com.jcloud.b2c.common.common.util.JacksonUtil;
import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.common.common.vo.PageInfo;
import com.jcloud.b2c.mall.service.client.enums.StateEnum;
import com.jcloud.b2c.mall.service.domain.MallPrincipalItem;
import com.jcloud.b2c.mall.service.service.MallPrincipalItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 主体与商品Controller
 * Created by issuser on 2017/3/1.
 * liuhao
 */
@RestController
@RequestMapping("/mallPrincipalItem")
public class MallPrincipalItemController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MallPrincipalItemController.class);

    @Autowired
    private MallPrincipalItemService mallPrincipalItemService;

    @RequestMapping(value = "/get" ,method = RequestMethod.GET)
    public BaseResponseVo<MallPrincipalItem> getById(@RequestParam Long id, @RequestParam Long tenantId){
        BaseResponseVo<MallPrincipalItem> responseVo = new BaseResponseVo<MallPrincipalItem>();
        MallPrincipalItem MallPrincipalItem = mallPrincipalItemService.get(id, tenantId);
        responseVo.setIsSuccess(true);
        responseVo.setData(MallPrincipalItem);
        return responseVo;
    }
    @RequestMapping(value = "/query" ,method = RequestMethod.POST)
    public BaseResponseVo<List<MallPrincipalItem>> query(@RequestBody MallPrincipalItem MallPrincipalItem){
        BaseResponseVo<List<MallPrincipalItem>> responseVo = new BaseResponseVo<List<MallPrincipalItem>>();
        List<MallPrincipalItem> list = mallPrincipalItemService.query(MallPrincipalItem);
        responseVo.setIsSuccess(true);
        responseVo.setData(list);
        return responseVo;
    }
    @RequestMapping(value = "/queryPage" ,method = RequestMethod.POST)
    public BaseResponseVo<List<MallPrincipalItem>> queryPage(@RequestBody MallPrincipalItem mallPrincipalItem){
        BaseResponseVo<List<MallPrincipalItem>> responseVo = new BaseResponseVo<List<MallPrincipalItem>>();
        if(mallPrincipalItem.getPageSize()==null || mallPrincipalItem.getPageSize() == 0){
            return responseVo;
        }
        int pageIndex = mallPrincipalItem.getPageIndex();
        List<MallPrincipalItem> list = mallPrincipalItemService.query(mallPrincipalItem);
        Integer count = mallPrincipalItemService.queryCount(mallPrincipalItem);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setCurrentPage(pageIndex);
        pageInfo.setPageSize(mallPrincipalItem.getPageSize());
        pageInfo.setTotalRecord(count);
        pageInfo.setTotalPage((count + mallPrincipalItem.getPageSize() - 1) / mallPrincipalItem.getPageSize());
        responseVo.setPageInfo(pageInfo);
        responseVo.setIsSuccess(true);
        responseVo.setData(list);
        return responseVo;
    }
    @RequestMapping(value = "/update" ,method = RequestMethod.POST)
    public BaseResponseVo<Void> update(@RequestBody MallPrincipalItem MallPrincipalItem){
        BaseResponseVo<Void> responseVo = new BaseResponseVo<Void>();
        boolean success = mallPrincipalItemService.update(MallPrincipalItem);
        responseVo.setIsSuccess(success);
        return responseVo;
    }
    @RequestMapping(value = "/add" ,method = RequestMethod.POST)
    public BaseResponseVo<Long> add(@RequestBody MallPrincipalItem MallPrincipalItem){
        BaseResponseVo<Long> responseVo = new BaseResponseVo<Long>();
        boolean success = mallPrincipalItemService.add(MallPrincipalItem);
        responseVo.setData(MallPrincipalItem.getId());
        responseVo.setIsSuccess(success);
        return responseVo;
    }
    @RequestMapping(value = "/delete" ,method = RequestMethod.POST)
    public BaseResponseVo<Void> delete(@RequestBody MallPrincipalItem MallPrincipalItem){
        BaseResponseVo<Void> responseVo = new BaseResponseVo<Void>();
        boolean success = mallPrincipalItemService.delete(MallPrincipalItem);
        responseVo.setIsSuccess(success);
        return responseVo;
    }

    @RequestMapping(value = "/addBatch" ,method = RequestMethod.POST)
    public BaseResponseVo<Void> addBatch(@RequestBody List<MallPrincipalItem> MallPrincipalItemList){
        BaseResponseVo<Void> responseVo = new BaseResponseVo<Void>();
        boolean success = mallPrincipalItemService.addBatch(MallPrincipalItemList);
        responseVo.setIsSuccess(success);
        return responseVo;
    }

    @RequestMapping(value = "/isBindItem" ,method = RequestMethod.GET)
    public BaseResponseVo<Boolean> isBindItem(@RequestParam Long skuId, @RequestParam Long tenantId) {
        MallPrincipalItem mallPrincipalItem = new MallPrincipalItem();
        mallPrincipalItem.setItemId(skuId);
        mallPrincipalItem.setState(StateEnum.ON_SHELF.getValue());
        mallPrincipalItem.setTenantId(tenantId);
        int count = mallPrincipalItemService.queryCount(mallPrincipalItem);
        LOGGER.info("MallPrincipalItemController.isBindItem, tenantId="+tenantId+", skuId="+skuId+", count="+count);

        BaseResponseVo<Boolean> responseVo = new BaseResponseVo<Boolean>();
        responseVo.setIsSuccess(true);
        responseVo.setData(count > 0);
        return responseVo;
    }

    @RequestMapping(value = "/isBindItems", method = RequestMethod.POST)
    public BaseResponseVo<Map<Long,Boolean>> isBindItems(@RequestParam Long tenantId, @RequestParam Long shopId, @RequestBody List<Long> skuIds){
        BaseResponseVo<Map<Long,Boolean>> responseVo = new BaseResponseVo<Map<Long,Boolean>>();
        LOGGER.info("MallPrincipalItemController.isBindItems tenantId："+tenantId+", shopId:"+shopId+", skuIds:"+JacksonUtil.convert(skuIds));
        //对象验证
        if(tenantId == null || shopId == null || skuIds == null || skuIds.size() <= 0){
            responseVo.setData(null);
            responseVo.setErrorCode("");
            responseVo.setErrStrSet(null);
            responseVo.setIsSuccess(false);
            responseVo.setMessage("请求参数错误!");
        }

        Map<Long,Boolean> map = mallPrincipalItemService.isBindItems(tenantId, shopId, skuIds);
        responseVo.setIsSuccess(true);
        responseVo.setData(map);
        return responseVo;
    }

    @RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
    public BaseResponseVo<Integer> batchDelete(@RequestParam Long tenantId, @RequestParam String skuIds) {
        LOGGER.info("delete sku, tenantId={}, skuIds={}", tenantId, skuIds);

        String[] skus = skuIds.split(",");
        List<Long> skuIdList = new ArrayList();
        for (String id : skus) {
            skuIdList.add(Long.parseLong(id));
        }

        int count = this.mallPrincipalItemService.batchDelete(tenantId, skuIdList);
        BaseResponseVo<Integer> responseVo = new BaseResponseVo();
        responseVo.setIsSuccess(true);
        responseVo.setData(count);
        return responseVo;
    }

    @RequestMapping(value = "/queryDistinctItemIds", method = RequestMethod.GET)
    public BaseResponseVo<List<Long>> queryDistinctItemIds(Long tenantId, int pageIndex, int pageSize) {
        BaseResponseVo<List<Long>> responseVo = new BaseResponseVo();
        List<Long> itemIds = mallPrincipalItemService.queryDistinctItemIds(tenantId, pageIndex, pageSize);
        responseVo.setIsSuccess(true);
        responseVo.setData(itemIds);
        return responseVo;
    }

    @RequestMapping(value = "/countDistinctItemId", method = RequestMethod.GET)
    public BaseResponseVo<Long> countDistinctItemId(Long tenantId) {
        BaseResponseVo<Long> responseVo = new BaseResponseVo();
        long count = mallPrincipalItemService.countDistinctItemId(tenantId);
        responseVo.setIsSuccess(true);
        responseVo.setData(count);
        return responseVo;
    }
}
