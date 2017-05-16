package com.jcloud.b2c.mall.service.service;

import com.jcloud.b2c.mall.service.domain.MallPrincipalItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 主体与商品Service
 * Created by issuser on 2017/3/1.
 * liuhao
 */
public interface MallPrincipalItemService {

    MallPrincipalItem get(Long id, Long tenantId);

    List<MallPrincipalItem> query(MallPrincipalItem mallPrincipalItem);

    Integer queryCount(MallPrincipalItem mallPrincipalItem);

    boolean update(MallPrincipalItem mallPrincipalItem);

    boolean add(MallPrincipalItem mallPrincipalItem);

    boolean delete(MallPrincipalItem mallPrincipalItem);

    boolean addBatch(List<MallPrincipalItem> mallPrincipalItemList);

    Map<Long,Boolean> isBindItems(Long tenantId, Long shopId, List<Long> skuIds);

    int batchDelete(Long tenantId, List<Long> skuIds);

    List<Long> queryDistinctItemIds(Long tenantId, int pageIndex, int pageSize);

    Long countDistinctItemId(Long tenantId);
}
