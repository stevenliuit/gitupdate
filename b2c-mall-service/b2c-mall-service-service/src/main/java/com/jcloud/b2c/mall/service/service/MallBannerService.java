/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: MallBannerService.java project: jcloud-b2c-mall-service
 * @creator: lidongxing
 * @date: 2017/2/15
 */

package com.jcloud.b2c.mall.service.service;

import com.jcloud.b2c.mall.service.client.vo.MallBannerVo;
import com.jcloud.b2c.mall.service.domain.MallBanner;

import java.util.List;

/**
 * @description: 轮播图+广告位SERVICE 
 * @author: lidongxing
 * @requireNo:
 * @createdate: 2017-02-15 17:10
 * @lastdate:
 */

public interface MallBannerService {

    MallBanner get(Long id, Long tenantId);

    boolean add(MallBanner mallBanner);

    boolean update(MallBanner mallBanner);

    boolean delete(MallBanner mallBanner);

    List<MallBanner> query(MallBanner mallBanner);

    MallBannerVo queryWithSkuId(MallBanner mallBanner);

    MallBanner queryByFlash(MallBanner mallBanner);

    boolean checkByBeginTime(MallBanner mallBanner);
}
