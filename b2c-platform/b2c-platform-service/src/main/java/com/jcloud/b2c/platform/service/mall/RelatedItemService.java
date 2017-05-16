/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: RelatedItemService.java project: jcloud-b2c-platform
 * @creator: lidongxing
 * @date: 2017/3/2
 */

package com.jcloud.b2c.platform.service.mall;

import com.jcloud.b2c.mall.service.client.enums.AdPrincipalTypeEnum;
import com.jcloud.b2c.mall.service.client.enums.ClientTypeEnum;
import com.jcloud.b2c.mall.service.client.vo.MallPrincipalItemVo;
import com.jcloud.b2c.platform.domain.vo.RelatedItem;

import java.util.List;
import java.util.Map;

/**
 * @description: RELATED ITEM SERVICE 
 * @author: lidongxing
 * @requireNo:
 * @createdate: 2017-03-02 14:25
 * @lastdate:
 */

public interface RelatedItemService {
    /**
     * 关联商品的上下限, 为NULL无限制
     * @param ct
     * @param pt
     * @param isIndex : 1,0
     * @return
     */
    Integer[] getRelatedLimit(ClientTypeEnum ct, AdPrincipalTypeEnum pt, Integer isIndex);

    /**
     * 判断是否首页, 为NULL无此主体(楼层，类目）
     * @param pt
     * @param principalId
     * @param tenantId
     * @return
     */
    Integer isIndex(AdPrincipalTypeEnum pt, Long principalId, Long tenantId);

    /**
     * 首页分页列表
     * @param data
     * @param mallPrincipalItem
     * @param shopId
     * @return
     */
    List<RelatedItem> queryPrincipalItem(Map<String, Object> data, MallPrincipalItemVo mallPrincipalItem, Long shopId);

    /**
     * 选择商品类别
     * @return
     */
    List<RelatedItem> queryItem();

    /**
     * 删除
     * @return
     */
    Boolean deletePrincipalItem(MallPrincipalItemVo mallPrincipalItem);
}
