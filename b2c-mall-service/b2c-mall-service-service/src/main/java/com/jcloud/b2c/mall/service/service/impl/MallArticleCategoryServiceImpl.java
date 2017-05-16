package com.jcloud.b2c.mall.service.service.impl;

import com.jcloud.b2c.mall.service.domain.MallArticleCategory;
import com.jcloud.b2c.mall.service.mapper.MallArticleCategoryMapper;
import com.jcloud.b2c.mall.service.service.MallArticleCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: YueZheng
 * @requireNo:
 * @createdate: 2017/3/3 10:20
 * @lasdate
 */
@Service("mallArticleCategoryService")
public class MallArticleCategoryServiceImpl implements MallArticleCategoryService {

    @Autowired
    private MallArticleCategoryMapper mallArticleCategoryMapper;

    @Override
    public MallArticleCategory get(Long id, Long tenantId) {
        MallArticleCategory mallArticleCategory = new MallArticleCategory();
        mallArticleCategory.setTenantId(tenantId);
        mallArticleCategory.setId(id);
        return mallArticleCategoryMapper.selectByPrimaryKey(mallArticleCategory);
    }

    @Override
    public boolean add(MallArticleCategory mallArticleCategory) {
        Date now = new Date();
        mallArticleCategory.setCreated(now);
        mallArticleCategory.setModified(now);
        return mallArticleCategoryMapper.insertSelective(mallArticleCategory)==1;
    }

    @Override
    public boolean update(MallArticleCategory mallArticleCategory) {
        Date now = new Date();
        mallArticleCategory.setModified(now);
        return mallArticleCategoryMapper.updateByPrimaryKeySelective(mallArticleCategory)==1;
    }

    @Override
    public boolean delete(MallArticleCategory mallArticleCategory) {
        return mallArticleCategoryMapper.deleteByPrimaryKey(mallArticleCategory)==1;
    }

    @Override
    public List<MallArticleCategory> query(MallArticleCategory mallArticleCategory) {
        return mallArticleCategoryMapper.querySelective(mallArticleCategory);
    }
}
