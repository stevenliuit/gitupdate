/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: MallRelatedAdController.java project: jcloud-b2c-platform
 * @creator: lidongxing
 * @date: 2017/3/1
 */

package com.jcloud.b2c.platform.web.controller.mall;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.common.common.vo.PageInfo;
import com.jcloud.b2c.common.common.web.security.ControllerContext;
import com.jcloud.b2c.item.client.vo.common.Pager;
import com.jcloud.b2c.item.client.vo.item.PlatformQuerySkuListCriteria;
import com.jcloud.b2c.item.client.vo.item.PlatformQuerySkuListResponse;
import com.jcloud.b2c.item.client.vo.item.PlatformQuerySkuListVo;
import com.jcloud.b2c.item.client.vo.item.SkuPicVo;
import com.jcloud.b2c.mall.service.client.enums.AdPrincipalTypeEnum;
import com.jcloud.b2c.mall.service.client.enums.ClientTypeEnum;
import com.jcloud.b2c.mall.service.client.enums.YesNoEnum;
import com.jcloud.b2c.mall.service.client.vo.MallPrincipalItemVo;
import com.jcloud.b2c.platform.common.constant.ItemConstants;
import com.jcloud.b2c.platform.domain.vo.RelatedItem;
import com.jcloud.b2c.platform.service.feign.EsItemClient;
import com.jcloud.b2c.platform.service.feign.ItemListClient;
import com.jcloud.b2c.platform.service.feign.MallPrincipalItemClient;
import com.jcloud.b2c.platform.service.mall.RelatedItemService;
import com.jd.common.web.LoginContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 关联商品
 * @author: lidongxing
 * @requireNo:
 * @createdate: 2017-03-01 11:35
 * @lastdate:
 */

@Controller
@RequestMapping("/mall/relatedItem")
public class MallRelatedItemController {
    private static final Logger LOGGER = LoggerFactory.getLogger("com.jcloud.b2c.platform.web.controller.mall.MallRelatedItemController.info");
    //private static final Logger LOGGER = LoggerFactory.getLogger(MallRelatedItemController.class);

    @Autowired
    private RelatedItemService relatedItemService;

    @Autowired
    MallPrincipalItemClient mallPrincipalItemClient;

    @Autowired
    private ItemListClient itemListClient;

    @Autowired
    private EsItemClient esItemClient;

    private static Integer PAGE_SIZE = 10;
    @RequestMapping(value = "/main/{clientType}/{principalType}" ,method = RequestMethod.GET)
    public ModelAndView list(HttpServletRequest request, @PathVariable(value = "clientType") String clientType,
                             @PathVariable(value = "principalType") String principalType, @RequestParam Long principalId,@RequestParam String categoryName){
        if(!this.baseValidate(clientType,principalType)){
            return null;
        }

        ClientTypeEnum ct = ClientTypeEnum.getClientTypeByName(clientType);
        AdPrincipalTypeEnum pt = AdPrincipalTypeEnum.getAdPrincipalByCode(principalType);

        Integer isIndex = relatedItemService.isIndex(pt, principalId, ControllerContext.getTenantId());
        if(isIndex == null){
            return null;
        }
        Integer[] relatedLimit = relatedItemService.getRelatedLimit(ct, pt, isIndex);

        ModelAndView modelAndView = new ModelAndView("mall/relatedItem/main");
        modelAndView.addObject("categoryName",categoryName);
        modelAndView.addObject("clientTypeValue", ct.getValue());
        modelAndView.addObject("principalTypeValue", pt.getValue());
        modelAndView.addObject("clientType", clientType);
        modelAndView.addObject("principalType", principalType);
        modelAndView.addObject("principalId", principalId);
        modelAndView.addObject("isIndex", isIndex);
        modelAndView.addObject("relatedLimit", relatedLimit);
        return modelAndView;
    }

    @RequestMapping(value = "/query/{clientType}/{principalType}" ,method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> query(HttpServletRequest request, @PathVariable(value = "clientType") String clientType,
                                     @PathVariable(value = "principalType") String principalType,
                                     @RequestParam(value = "principalId", required = true) Long principalId,
                                     @RequestParam(value = "pageIndex", required = false) Integer pageIndex,
                                     @RequestParam(value = "pageSize", required = false) Integer pageSize){
        Map<String, Object> data = new HashMap<String, Object>();
        if(!this.baseValidate(clientType,principalType)){
            data.put("success", "0");
            return data;
        }

        ClientTypeEnum ct = ClientTypeEnum.getClientTypeByName(clientType);
        AdPrincipalTypeEnum pt = AdPrincipalTypeEnum.getAdPrincipalByCode(principalType);

        //分页查询，关联数据；查询商品信息
        pageSize = PAGE_SIZE;
        MallPrincipalItemVo mallPrincipalItem = new MallPrincipalItemVo();
        mallPrincipalItem.setTenantId(ControllerContext.getTenantId());
        mallPrincipalItem.setPrincipalType(pt.getValue());
        mallPrincipalItem.setState(YesNoEnum.YES.getValue());
        mallPrincipalItem.setClientType(ct.getValue());
        mallPrincipalItem.setPrincipalId(principalId);
        mallPrincipalItem.setPageIndex(pageIndex==null?0:(pageIndex-1));
        mallPrincipalItem.setPageSize(pageSize);
        List<RelatedItem> itemList = relatedItemService.queryPrincipalItem(data,mallPrincipalItem,ControllerContext.getShopId());
        data.put("itemList", itemList);
        data.put("success", "1");
        return data;
    }

    @RequestMapping(value = "/del/{clientType}/{principalType}" ,method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> del(HttpServletRequest request, @PathVariable(value = "clientType") String clientType,
                                   @PathVariable(value = "principalType") String principalType, @RequestParam Long principalId,
                                   @RequestParam(value = "id") Long id) {
        String userErp = String.valueOf(request.getAttribute("userErp"));
        LOGGER.info("MallRelatedItemController.del begin, userErp:{}, principalType:{}, id:{}", userErp, principalType, id);
        Map<String, Object> data = new HashMap<String, Object>();
        if(!this.baseValidate(clientType,principalType)){
            data.put("success", "0");
            return data;
        }
        Long tenantId = ControllerContext.getTenantId();
        MallPrincipalItemVo pItem = mallPrincipalItemClient.get(id, tenantId).getData();
        if(pItem==null){
            data.put("success", "0");
            return data;
        }
        MallPrincipalItemVo mallPrincipalItem = new MallPrincipalItemVo();
        mallPrincipalItem.setTenantId(ControllerContext.getTenantId());
        mallPrincipalItem.setId(id);
        Boolean suc = relatedItemService.deletePrincipalItem(mallPrincipalItem);
        if(suc){
            List<Long> itemIds = new ArrayList<Long>();
            itemIds.add(pItem.getItemId());
            esItemClient.putIntoEsTask(itemIds, ControllerContext.getTenantId());
            data.put("success", 1);
            LOGGER.info("MallRelatedItemController.del success, userErp:{}, principalType:{}, id:{}", userErp, principalType, id);
        }else{
            data.put("success", 0);
        }
        return data;
    }

    @RequestMapping(value = "/saveBatch/{clientType}/{principalType}" ,method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> saveBatch(HttpServletRequest request, @PathVariable(value = "clientType") String clientType,
                                         @PathVariable(value = "principalType") String principalType, @RequestParam Long principalId,
                                         @RequestParam(value = "items") String items) {
        String userErp = String.valueOf(request.getAttribute("userErp"));
        LOGGER.info("MallRelatedItemController.saveBatch begin, userErp:{}, principalType:{}, principalId:{}, items:{}", userErp, principalType, principalId, items);
        Map<String, Object> data = new HashMap<String, Object>();
        if(!this.baseValidate(clientType,principalType)){
            data.put("success", "0");
            return data;
        }
        Long tenantId = ControllerContext.getTenantId();
        List<Long> itemIds = new ArrayList<Long>();
        Map<Long, Long> delItemIds = new HashMap<Long, Long>();
        Map<Long, Long> newItemIds = new HashMap<Long, Long>();
        ClientTypeEnum ct = ClientTypeEnum.getClientTypeByName(clientType);
        AdPrincipalTypeEnum pt = AdPrincipalTypeEnum.getAdPrincipalByCode(principalType);
        List<RelatedItem> choosedItemList = JSON.parseObject(items, new TypeReference<List<RelatedItem>>() {});
        List<MallPrincipalItemVo> newList = new ArrayList<MallPrincipalItemVo>();
        for(RelatedItem relatedItem : choosedItemList){
            //解除关联
            if(!relatedItem.isChoosed() && relatedItem.getId()!=null){
                MallPrincipalItemVo mallPrincipalItem = new MallPrincipalItemVo();
                mallPrincipalItem.setTenantId(ControllerContext.getTenantId());
                mallPrincipalItem.setId(relatedItem.getId());
                relatedItemService.deletePrincipalItem(mallPrincipalItem);
                itemIds.add(relatedItem.getSkuId());
                delItemIds.put(relatedItem.getSkuId(), relatedItem.getId());
            }else if(relatedItem.isChoosed()){//新增关联
                MallPrincipalItemVo mallPrincipalItem = new MallPrincipalItemVo();
                mallPrincipalItem.setTenantId(ControllerContext.getTenantId());
                mallPrincipalItem.setPrincipalId(principalId);
                mallPrincipalItem.setPrincipalType(pt.getValue());
                mallPrincipalItem.setClientType(ct.getValue());
                mallPrincipalItem.setSortNum(1);
                mallPrincipalItem.setItemId(relatedItem.getSkuId());
                //if(relatedItemService.queryPrincipalItem())
                newList.add(mallPrincipalItem);
                Long id = mallPrincipalItemClient.add(mallPrincipalItem).getData();
                itemIds.add(relatedItem.getSkuId());
                newItemIds.put(relatedItem.getSkuId(), id);
            }
        }
        //mallPrincipalItemClient.addBatch(newList);
        LOGGER.info("MallRelatedItemController.saveBatch delItemIds, userErp:{}, principalType:{}, principalId:{}, delItemIds:{}", userErp, principalType, principalId, delItemIds);
        LOGGER.info("MallRelatedItemController.saveBatch newItemIds, userErp:{}, principalType:{}, principalId:{}, newItemIds:{}", userErp, principalType, principalId, newItemIds);
        if(itemIds.size()>0) {
            esItemClient.putIntoEsTask(itemIds, tenantId);
        }
        data.put("newItemIds", newItemIds);
        data.put("success", 1);
        return data;
    }

    @RequestMapping(value = "/queryItem/{clientType}/{principalType}" ,method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryItem(HttpServletRequest request, @PathVariable(value = "clientType") String clientType,
                             @PathVariable(value = "principalType") String principalType, @RequestParam Long principalId,
                                     @RequestParam Integer pageIndex, @RequestParam Integer pageSize, PlatformQuerySkuListCriteria criteria){
        Map<String, Object> data = new HashMap<String, Object>();
        if(!this.baseValidate(clientType,principalType)){
            data.put("success", "0");
            return data;
        }

        ClientTypeEnum ct = ClientTypeEnum.getClientTypeByName(clientType);
        AdPrincipalTypeEnum pt = AdPrincipalTypeEnum.getAdPrincipalByCode(principalType);

        pageSize = PAGE_SIZE;
        //分页查询商品
        criteria.setTenantId(ControllerContext.getTenantId());
        criteria.setShopId(ControllerContext.getShopId());
        criteria.setPage(pageIndex);
        criteria.setSize(pageSize);
        BaseResponseVo<PlatformQuerySkuListResponse> responseVo = itemListClient.platformQueryItemList(criteria);
        List<PlatformQuerySkuListVo> skuListVos = null;
        if(responseVo.isSuccess()) {
            PlatformQuerySkuListResponse response = responseVo.getData();
            if (response != null && response.getPlatformQuerySkuListVos().size()>0){
                skuListVos = response.getPlatformQuerySkuListVos();
            }
            Pager pager = response.getPager();
            PageInfo pageInfo = new PageInfo();
            pageInfo.setCurrentPage(pageIndex);
            pageInfo.setPageSize(pageSize);
            pageInfo.setTotalPage(pager.getTotalPages());
            pageInfo.setTotalRecord(pager.getTotalCount());

            data.put("pageInfo", pageInfo);
            data.put("success", "1");
        }else{
            data.put("success", "0");
        }
        //判断商品是否已选择
        if("1".equals(data.get("success")) && skuListVos!=null && skuListVos.size()>0){
            List<RelatedItem> itemList = new ArrayList<RelatedItem>();
            for(PlatformQuerySkuListVo item : skuListVos){
                MallPrincipalItemVo mallPrincipalItem = new MallPrincipalItemVo();
                mallPrincipalItem.setPrincipalId(principalId);
                mallPrincipalItem.setClientType(ct.getValue());
                mallPrincipalItem.setPrincipalType(pt.getValue());
                mallPrincipalItem.setState(YesNoEnum.YES.getValue());
                mallPrincipalItem.setTenantId(ControllerContext.getTenantId());
                mallPrincipalItem.setItemId(item.getJdSkuId());
                List<MallPrincipalItemVo> pItemList = mallPrincipalItemClient.query(mallPrincipalItem).getData();
                RelatedItem relatedItem = new RelatedItem();
                if(pItemList!=null && pItemList.size()>0){
                    relatedItem.setId(pItemList.get(0).getId());
                    relatedItem.setChoosed(true);
                }else{
                    relatedItem.setChoosed(false);
                }
                relatedItem.setSkuId(item.getJdSkuId());
                relatedItem.setSkuName(item.getSkuName());
                relatedItem.setSkuImg(item.getMainPictureUrl());

                itemList.add(relatedItem);
            }
            data.put("itemList", itemList);
        }

        return data;
    }

    private boolean baseValidate(String clientType, String principalType){
        if(clientType == null || clientType.length()==0 || principalType == null || principalType.length()==0){
            return false;
        }
        ClientTypeEnum ct = ClientTypeEnum.getClientTypeByName(clientType);
        AdPrincipalTypeEnum at = AdPrincipalTypeEnum.getAdPrincipalByCode(principalType);
        if(ct == null || at == null){
            return false;
        }
        return true;
    }
}
