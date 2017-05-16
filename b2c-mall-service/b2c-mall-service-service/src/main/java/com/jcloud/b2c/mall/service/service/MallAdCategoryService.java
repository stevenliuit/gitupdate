package com.jcloud.b2c.mall.service.service;

import com.jcloud.b2c.mall.service.domain.MallAdCategory;

import java.util.List;

/**
 * @description:首页类目广告设置
 * @author: YueZheng
 * @requireNo:
 * @createdate: 2017/2/22 15:56
 * @lasdate
 */
public interface MallAdCategoryService {

    List<MallAdCategory> query(Long categoryId, Long tenantId);

    boolean add(MallAdCategory mallAdCategory);

    boolean delete(MallAdCategory mallAdCategory);

    MallAdCategory get(Long id, Long tenantId);

    boolean update(MallAdCategory mallAdCategory, Integer beforeSort);
}
