/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: MallBannerMapper.java project: jcloud-b2c-mall-service
 * @creator: lidongxing
 * @date: 2017/2/15
 */

package com.jcloud.b2c.mall.service.mapper;

import com.jcloud.b2c.mall.service.domain.MallBanner;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description: 轮播图+广告位 
 * @author: lidongxing
 * @requireNo:
 * @createdate: 2017-02-15 14:45
 * @lastdate:
 */

@Mapper
public interface MallBannerMapper {
    MallBanner getByPrimaryKey(MallBanner mallBanner);

    int insert(MallBanner mallBanner);

    int updateByPrimaryKeySelective(MallBanner mallBanner);

    int deleteByPrimaryKey(MallBanner mallBanner);

    List<MallBanner> querySelective(MallBanner mallBanner);

    void updateSortToMoveDown(@Param("mallBanner")MallBanner mallBanner);

    MallBanner queryFlashByNow(MallBanner mallBanner);

    int checkByBeginTime(MallBanner mallBanner);
}
