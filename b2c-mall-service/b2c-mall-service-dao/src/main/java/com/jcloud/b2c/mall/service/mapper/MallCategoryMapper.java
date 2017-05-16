/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: MallCategoryMapper.java project: jcloud-b2c-mall-service
 * @creator: lidongxing
 * @date: 2017/2/16
 */

package com.jcloud.b2c.mall.service.mapper;

import com.jcloud.b2c.mall.service.domain.MallCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @description: 类目
 * @author: lidongxing
 * @requireNo:
 * @createdate: 2017-02-16 14:31
 * @lastdate:
 */

@Mapper
public interface MallCategoryMapper {

    MallCategory getByPrimaryKey(MallCategory mallCategory);
    MallCategory get(MallCategory mallCategory);

    int insert(MallCategory mallCategory);

    int updateByPrimaryKeySelective(MallCategory mallCategory);

    int deleteByPrimaryKey(MallCategory mallCategory);

    List<MallCategory> querySelective(MallCategory mallCategory);

    List<MallCategory> queryByParentId(MallCategory mallCategory);

    int updateSortToMoveDown(@Param("mallCategory") MallCategory mallCategory, @Param("beforeSort") Integer beforeSort);

    int updateSortToMoveUp(@Param("mallCategory") MallCategory mallCategory, @Param("afterSort") Integer afterSort);
    int updateSort(@Param("mallCategory") MallCategory mallCategory);

    List<MallCategory> getBySortNum(MallCategory mallCategory);

    MallCategory getById(MallCategory current);

    MallCategory getPurposeCate(MallCategory minTimeCategory);

    MallCategory getMinTime(MallCategory mallCategory);

    MallCategory getLessSort(MallCategory mallCategory);

    MallCategory getDownSort(MallCategory mallCategory);

    MallCategory getMaxTime(MallCategory mallCategory);

    MallCategory getMaxSortCategory(MallCategory mallCategory);
}
