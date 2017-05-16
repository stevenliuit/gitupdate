/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: MallAdMapper.java project: jcloud-b2c-mall-service
 * @creator: lidongxing
 * @date: 2017/2/15
 */

package com.jcloud.b2c.mall.service.mapper;

import com.jcloud.b2c.mall.service.domain.MallAd;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description: 首页广告
 * @author: lidongxing
 * @requireNo:
 * @createdate: 2017-02-15 14:48
 * @lastdate:
 */

@Mapper
public interface MallAdMapper {
    MallAd getByPrimaryKey(MallAd mallAd);

    int insert(MallAd mallAd);

    int updateByPrimaryKeySelective(MallAd mallAd);

    int deleteByPrimaryKey(MallAd mallAd);

    List<MallAd> querySelective(MallAd mallAd);

    List<MallAd> queryByFloor(@Param("mallAd") MallAd mallAd, @Param("principalId") Long principalId);

    List<MallAd> queryByChannel(@Param("mallAd") MallAd mallAd, @Param("principalId") Long principalId);

    List<MallAd> queryByCategory(@Param("mallAd") MallAd mallAd, @Param("principalId") Long principalId);
}
