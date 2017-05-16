package com.jcloud.b2c.mall.service.service;

import com.jcloud.b2c.mall.service.domain.MallArticleCategory;

import java.util.List;

/**
 * @description:
 * @author: YueZheng
 * @requireNo:
 * @createdate: 2017/3/3 10:19
 * @lasdate
 */
public interface MallArticleCategoryService {

    MallArticleCategory get(Long id, Long tenantId);

    boolean add(MallArticleCategory mallArticleCategory);

    boolean update(MallArticleCategory mallArticleCategory);

    boolean delete(MallArticleCategory mallArticleCategory);

    List<MallArticleCategory> query(MallArticleCategory mallArticleCategory);
}
