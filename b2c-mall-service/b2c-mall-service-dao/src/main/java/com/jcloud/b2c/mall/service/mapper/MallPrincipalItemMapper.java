package com.jcloud.b2c.mall.service.mapper;

import com.jcloud.b2c.mall.service.domain.MallPrincipalItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 主体与商品Mapper
 * Created by issuser on 2017/3/1.
 * liuhao
 */
@Mapper
public interface MallPrincipalItemMapper {

    MallPrincipalItem getByPrimaryKey(MallPrincipalItem mallPrincipalItem);

    List<MallPrincipalItem> querySelective(MallPrincipalItem mallPrincipalItem);

    int queryCountSelective(MallPrincipalItem mallPrincipalItem);

    int updateByPrimaryKeySelective(MallPrincipalItem mallPrincipalItem);

    int insert(MallPrincipalItem mallPrincipalItem);

    int deleteByPrimaryKey(MallPrincipalItem mallPrincipalItem);

    int insertBatch(List<MallPrincipalItem>  mallPrincipalItemList);

    List<MallPrincipalItem> queryBySukIds(@Param("tenantId") Long tenantId, @Param("shopId") Long shopId,
                                          @Param("state") Integer state, @Param("skuIds") List<Long> skuIds);

    int batchDelete(@Param("tenantId") Long tenantId, @Param("skuIds") List<Long> skuIds);

    List<Long> queryDistinctItemIds(@Param("tenantId") Long tenantId, @Param("pageIndex") int pageIndex,  @Param("pageSize") int pageSize);

    Long countDistinctItemId(@Param("tenantId") Long tenantId);
}
