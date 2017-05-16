/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: MallChannelService.java project: jcloud-b2c-mall-service
 * @creator: lidongxing
 * @date: 2017/2/15
 */

package com.jcloud.b2c.mall.service.service;

import com.jcloud.b2c.mall.service.domain.MallChannel;
import com.jcloud.b2c.mall.service.domain.MallFloor;

import java.util.List;

/**
 * @description: 频道SERVICE
 * @author: lidongxing
 * @requireNo:
 * @createdate: 2017-02-15 17:09
 * @lastdate:
 */

public interface MallChannelService {

    MallChannel get(Long id, Long tenantId);

    boolean add(MallChannel mallChannel);

    boolean update(MallChannel mallChannel);

    boolean delete(MallChannel mallChannel);

    List<MallChannel> query(MallChannel mallChannel);
}
