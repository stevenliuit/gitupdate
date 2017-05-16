package com.jcloud.b2c.mall.service.service;

import com.jcloud.b2c.mall.service.domain.MallAdRecommend;

import java.util.List;

/**
 * @description:
 * @author: YueZheng
 * @requireNo:
 * @createdate: 2017/3/11 17:03
 * @lasdate
 */
public interface MallAdRecommendService {

    MallAdRecommend get(Long id, Long tenantId);

    boolean add(MallAdRecommend mallAdRecommend);

    boolean update(MallAdRecommend mallAdRecommend);

    boolean delete(MallAdRecommend mallAdRecommend);

    List<MallAdRecommend> queryWithMallAd(MallAdRecommend mallAdRecommend);
}
