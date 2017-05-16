/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: MallAdCategoryMapper.java project: jcloud-b2c-mall-service
 * @creator: lidongxing
 * @date: 2017/2/16
 */

package com.jcloud.b2c.mall.service.mapper;

import com.jcloud.b2c.mall.service.domain.MallAdCategory;
import com.jcloud.b2c.mall.service.domain.MallAdChannel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.omg.CORBA.INTERNAL;

import java.util.List;

/**
 * @description: 广告和楼层关联
 * @author: lidongxing
 * @requireNo:
 * @createdate: 2017-02-16 14:32
 * @lastdate:
 */

@Mapper
public interface MallAdCategoryMapper {

    MallAdCategory getByPrimaryKey(MallAdCategory mallAdCategory);

    int insert(MallAdCategory mallAdCategory);

    int updateByPrimaryKeySelective(MallAdCategory mallAdCategory);

    int updateUniqueSelective(MallAdCategory mallAdCategory);

    int deleteByPrimaryKey(MallAdCategory mallAdCategory);

    int deleteUnique(MallAdCategory mallAdCategory);

    List<MallAdCategory> querySelective(MallAdCategory mallAdCategory);

    List<MallAdCategory> queryByCategory(@Param("categoryId") Long categoryId,
                                         @Param("tenantId") Long tenantId, @Param("state") Integer state);

    int updateSortToMoveDown(@Param("mallAdCategory") MallAdCategory mallAdCategory, @Param("beforeSort") Integer beforeSort);

    int updateSortToMoveUp(@Param("mallAdCategory") MallAdCategory mallAdCategory, @Param("afterSort") Integer afterSort);

    MallAdCategory queryAdCategoryById(MallAdCategory mallAdCategory);
}
