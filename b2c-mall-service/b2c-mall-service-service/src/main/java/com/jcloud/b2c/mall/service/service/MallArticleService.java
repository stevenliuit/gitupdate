package com.jcloud.b2c.mall.service.service;

import com.jcloud.b2c.mall.service.domain.MallArticle;

import java.util.List;

/**
 * @description:文章service
 * @author: YueZheng
 * @requireNo:
 * @createdate: 2017/3/2 17:28
 * @lasdate
 */
public interface MallArticleService {

    MallArticle get(Long id, Long tenantId);

    boolean add(MallArticle mallArticle);

    boolean update(MallArticle mallArticle);

    boolean delete(MallArticle mallArticle);

    List<MallArticle> query(MallArticle mallArticle);
}
