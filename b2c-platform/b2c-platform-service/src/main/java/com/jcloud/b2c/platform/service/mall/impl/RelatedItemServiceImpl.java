/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: RelatedItemServiceImpl.java project: jcloud-b2c-platform
 * @creator: lidongxing
 * @date: 2017/3/2
 */

package com.jcloud.b2c.platform.service.mall.impl;

import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.item.client.vo.item.ItemBasicInfoVo;
import com.jcloud.b2c.item.client.vo.item.ItemDetailVo;
import com.jcloud.b2c.item.client.vo.item.SkuPicVo;
import com.jcloud.b2c.mall.service.client.enums.AdPrincipalTypeEnum;
import com.jcloud.b2c.mall.service.client.enums.ClientTypeEnum;
import com.jcloud.b2c.mall.service.client.enums.YesNoEnum;
import com.jcloud.b2c.mall.service.client.vo.*;
import com.jcloud.b2c.platform.domain.vo.RelatedItem;
import com.jcloud.b2c.platform.service.feign.*;
import com.jcloud.b2c.platform.service.mall.RelatedItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: RELATED ITEM SERVICE IMPL 
 * @author: lidongxing
 * @requireNo:
 * @createdate: 2017-03-02 14:26
 * @lastdate:
 */

@Service("relatedItemService")
public class RelatedItemServiceImpl implements RelatedItemService{

    @Autowired
    MallCategoryClient mallCategoryClient;

    @Autowired
    MallFloorClient mallFloorClient;

    @Autowired
    MallChannelClient mallChannelClient;

    @Autowired
    MallBannerClient mallBannerClient;

    @Autowired
    MallPrincipalItemClient mallPrincipalItemClient;

    @Autowired
    ItemClient itemClient;

    public Integer[] getRelatedLimit(ClientTypeEnum ct, AdPrincipalTypeEnum pt, Integer isIndex){
        Map<String, Integer[]> map = new HashMap<String, Integer[]>();
        map.put(ClientTypeEnum.PC.getName()+ AdPrincipalTypeEnum.FLOOR.getCode()+ YesNoEnum.NO.getValue(), new Integer[]{4,14});
        map.put(ClientTypeEnum.PC.getName()+ AdPrincipalTypeEnum.FLOOR.getCode()+ YesNoEnum.YES.getValue(), new Integer[]{4,14});
        map.put(ClientTypeEnum.H5.getName()+ AdPrincipalTypeEnum.FLOOR.getCode()+ YesNoEnum.NO.getValue(), new Integer[]{2,12});
        map.put(ClientTypeEnum.H5.getName()+ AdPrincipalTypeEnum.FLOOR.getCode()+ YesNoEnum.YES.getValue(), new Integer[]{2,12});
        map.put(ClientTypeEnum.ANDROID.getName()+ AdPrincipalTypeEnum.SUBJECT.getCode()+ YesNoEnum.NO.getValue(), new Integer[]{2,12});
        map.put(ClientTypeEnum.ANDROID.getName()+ AdPrincipalTypeEnum.SUBJECT.getCode()+ YesNoEnum.YES.getValue(), new Integer[]{2,12});
        //map.put(ClientTypeEnum.PC.getName()+ AdPrincipalTypeEnum.CATEGORY.getCode()+ YesNoEnum.YES.getValue(), new Integer[]{4,14});

        Integer[] relatedLimit = map.get(ct.getName() + pt.getCode()+isIndex);
        //return relatedLimit==null?new Integer[]{0,0}:relatedLimit;
        return relatedLimit;
    }

    public Integer isIndex( AdPrincipalTypeEnum pt, Long id, Long tenantId){
        Long channelId = null;
        if(pt == AdPrincipalTypeEnum.CATEGORY){
            MallCategoryVo categoryVo =  mallCategoryClient.get(id, tenantId).getData();
            if(categoryVo!=null){
                channelId = categoryVo.getChannelId();
            }else{
                return null;
            }
        }
        if(pt == AdPrincipalTypeEnum.FLOOR){
            MallFloorVo mallFloorVo =  mallFloorClient.get(id, tenantId).getData();
            if(mallFloorVo!=null){
                channelId = mallFloorVo.getChannelId();
            }else{
                return null;
            }
        }
        if(pt == AdPrincipalTypeEnum.SUBJECT){
            MallBannerVo mallBannerVo =  mallBannerClient.get(id, tenantId).getData();
            if(mallBannerVo!=null){
                channelId = mallBannerVo.getChannelId();
            }else{
                return null;
            }
        }
        if(pt == AdPrincipalTypeEnum.CHANNEL){
            return YesNoEnum.YES.getValue();
        }
        return (channelId==null||channelId==0L)?YesNoEnum.YES.getValue():YesNoEnum.NO.getValue();
    }

    public List<RelatedItem> queryPrincipalItem(Map<String, Object> data, MallPrincipalItemVo mallPrincipalItem, Long shopId){
        List<RelatedItem> list = new ArrayList<RelatedItem>();
        BaseResponseVo<List<MallPrincipalItemVo>> responseVo = mallPrincipalItemClient.queryPage(mallPrincipalItem);
        List<MallPrincipalItemVo> principalItemList = responseVo.getData();
        data.put("pageInfo", responseVo.getPageInfo());
        if(principalItemList==null || principalItemList.size()==0){
            return list;
        }
        for(MallPrincipalItemVo pt : principalItemList){
            ItemBasicInfoVo itemBasicInfoVo = itemClient.getItemBasicInfo(pt.getTenantId(), shopId, pt.getItemId()).getData();
            RelatedItem relatedItem = new RelatedItem();
            relatedItem.setId(pt.getId());
            if(itemBasicInfoVo!=null){
                relatedItem.setSkuId(itemBasicInfoVo.getSkuId());
                relatedItem.setSkuName(itemBasicInfoVo.getSkuName());
                relatedItem.setSkuImg(itemBasicInfoVo.getImgUrl());
            }
            list.add(relatedItem);
        }
        return list;
    }

    public List<RelatedItem> queryItem(){

        return null;
    }

    public Boolean deletePrincipalItem(MallPrincipalItemVo mallPrincipalItem){
        return mallPrincipalItemClient.delete(mallPrincipalItem).isSuccess();
    }
}
