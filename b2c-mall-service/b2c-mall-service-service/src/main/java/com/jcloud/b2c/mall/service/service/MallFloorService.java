/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: MallFloorService.java project: jcloud-b2c-mall-service
 * @creator: lidongxing
 * @date: 2017/2/14
 */

package com.jcloud.b2c.mall.service.service;

import com.jcloud.b2c.mall.service.domain.MallFloor;

import java.util.List;

/**
 * @description: 楼层SERVICE
 * @author: lidongxing
 * @requireNo:
 * @createdate: 2017-02-14 16:32
 * @lastdate:
 */

public interface MallFloorService {

    MallFloor get(Long id, Long tenantId);

    boolean add(MallFloor mallFloor);

    boolean update(MallFloor mallFloor);

    boolean delete(MallFloor mallFloor);

    List<MallFloor> query(MallFloor mallFloor);
}
