/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: MallAdFloorMapper.java project: jcloud-b2c-mall-service
 * @creator: lidongxing
 * @date: 2017/2/15
 */

package com.jcloud.b2c.mall.service.mapper;

import com.jcloud.b2c.mall.service.domain.MallAdFloor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @description: 广告和楼层关联
 * @author: lidongxing
 * @requireNo:
 * @createdate: 2017-02-15 14:56
 * @lastdate:
 */

@Mapper
public interface MallAdFloorMapper {
    MallAdFloor getByPrimaryKey(MallAdFloor mallAdFloor);

    int insert(MallAdFloor mallAdFloor);

    int updateByPrimaryKeySelective(MallAdFloor mallAdFloor);

    int updateUniqueSelective(MallAdFloor mallAdFloor);

    int deleteByPrimaryKey(MallAdFloor mallAdFloor);

    int deleteUnique(MallAdFloor mallAdFloor);

    List<MallAdFloor> querySelective(MallAdFloor mallAdFloor);

    List<MallAdFloor> queryByFloorId(@Param("floorId") Long floorId, @Param("adType") Integer adType,
                                     @Param("tenantId") Long tenantId);

    int updateSortToMoveDown(@Param("mallAdFloor") MallAdFloor mallAdFloor, @Param("beforeSort") Integer beforeSort);

    int updateSortToMoveUp(@Param("mallAdFloor") MallAdFloor mallAdFloor, @Param("afterSort") Integer afterSort);

    MallAdFloor getAdFloorById(@Param("id") Long id, @Param("tenantId") Long tenantId);

    List<MallAdFloor> queryBySpecial(MallAdFloor mallAdFloor);
}
