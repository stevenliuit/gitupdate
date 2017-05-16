/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: MallAdService.java project: jcloud-b2c-mall-service
 * @creator: lidongxing
 * @date: 2017/2/15
 */

package com.jcloud.b2c.mall.service.service;

import com.jcloud.b2c.mall.service.domain.MallAd;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @description: 首页广告SERVICE
 * @author: lidongxing
 * @requireNo:
 * @createdate: 2017-02-15 17:10
 * @lastdate:
 */

public interface MallAdService {

    MallAd get(Long id, Long tenantId);

    boolean add(MallAd mallAd, String principalType, Long principalId, Integer principalSort);

    boolean update(MallAd mallAd, String principalType, Long principalId, Integer principalSort);

    boolean delete(MallAd mallAd, String principalType, Long principalId);

    List<MallAd> query(MallAd mallAd, String principalType, Long principalId);

    boolean add(MallAd mallAd);

    boolean update(MallAd mallAd);

    boolean delete(MallAd mallAd);

    List<MallAd> query(MallAd mallAd);
}
