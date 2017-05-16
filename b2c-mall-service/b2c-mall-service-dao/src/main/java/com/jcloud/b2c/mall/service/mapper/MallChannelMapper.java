/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: MallChannelMapper.java project: jcloud-b2c-mall-service
 * @creator: lidongxing
 * @date: 2017/2/15
 */

package com.jcloud.b2c.mall.service.mapper;

import com.jcloud.b2c.mall.service.domain.MallChannel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description: 频道
 * @author: lidongxing
 * @requireNo:
 * @createdate: 2017-02-15 14:42
 * @lastdate:
 */

@Mapper
public interface MallChannelMapper {
    MallChannel getByPrimaryKey(MallChannel mallChannel);

    int insert(MallChannel mallChannel);

    int updateByPrimaryKeySelective(MallChannel mallChannel);

    int deleteByPrimaryKey(MallChannel mallChannel);

    List<MallChannel> querySelective(MallChannel mallChannel);
}
