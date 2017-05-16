/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: Test.java project: jcloud-b2c-mall-service
 * @creator: lidongxing
 * @date: 2017/2/13
 */

package com.jcloud.b2c.mall.service.mapper;

import com.jcloud.b2c.mall.service.domain.MallFloor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description: 楼层
 * @author: lidongxing
 * @requireNo:
 * @createdate: 2017-02-13 21:18
 * @lastdate:
 */

@Mapper
public interface MallFloorMapper {
    MallFloor getByPrimaryKey(MallFloor mallFloor);

    int insert(MallFloor mallFloor);

    int updateByPrimaryKeySelective(MallFloor mallFloor);

    int deleteByPrimaryKey(MallFloor mallFloor);

    List<MallFloor> querySelective(MallFloor mallFloor);
}
