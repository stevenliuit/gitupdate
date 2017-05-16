/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: MallAdChannel.java project: jcloud-b2c-mall-service
 * @creator: lidongxing
 * @date: 2017/2/15
 */

package com.jcloud.b2c.mall.service.mapper;

import com.jcloud.b2c.mall.service.domain.MallAdChannel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @description: 广告和频道关联
 * @author: lidongxing
 * @requireNo:
 * @createdate: 2017-02-15 14:55
 * @lastdate:
 */

@Mapper
public interface MallAdChannelMapper {
    MallAdChannel getByPrimaryKey(MallAdChannel mallAdChannel);

    int insert(MallAdChannel mallAdChannel);

    int updateByPrimaryKeySelective(MallAdChannel mallAdChannel);

    int updateUniqueSelective(MallAdChannel mallAdChannel);

    int deleteByPrimaryKey(MallAdChannel mallAdChannel);

    int deleteUnique(MallAdChannel mallAdChannel);

    List<MallAdChannel> querySelective(MallAdChannel mallAdChannel);

    List<MallAdChannel> queryByChannel(MallAdChannel mallAdChannel);

    int updateSortToMoveDown(@Param("mallAdChannel")MallAdChannel mallAdChannel, @Param("beforeSort") Integer beforeSort);

    int updateSortToMoveUp(@Param("mallAdChannel")MallAdChannel mallAdChannel, @Param("afterSort") Integer afterSort);

    MallAdChannel getAdChannelById(MallAdChannel mallAdChannel);

}
