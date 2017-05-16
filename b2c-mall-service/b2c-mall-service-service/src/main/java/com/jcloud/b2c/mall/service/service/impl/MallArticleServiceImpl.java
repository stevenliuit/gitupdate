package com.jcloud.b2c.mall.service.service.impl;

import com.jcloud.b2c.mall.service.client.enums.StateEnum;
import com.jcloud.b2c.mall.service.domain.MallArticle;
import com.jcloud.b2c.mall.service.mapper.MallArticleMapper;
import com.jcloud.b2c.mall.service.service.MallArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: YueZheng
 * @requireNo:
 * @createdate: 2017/3/2 17:37
 * @lasdate
 */
@Service("mallArticleService")
public class MallArticleServiceImpl implements MallArticleService {

    @Autowired
    private MallArticleMapper mallArticleMapper;

    @Override
    public MallArticle get(Long id, Long tenantId) {
        MallArticle mallArticle = new MallArticle();
        mallArticle.setId(id);
        mallArticle.setTenantId(tenantId);
        return mallArticleMapper.selectByPrimaryKey(mallArticle);
    }

    @Override
    public boolean add(MallArticle mallArticle) {
        Date now = new Date();
        mallArticle.setCreated(now);
        mallArticle.setModified(now);
        return mallArticleMapper.insertSelective(mallArticle)==1;
    }

    @Override
    public boolean update(MallArticle mallArticle) {
        Date now = new Date();
        mallArticle.setModified(now);
        return mallArticleMapper.updateByPrimaryKeySelective(mallArticle)==1;
    }

    @Override
    public boolean delete(MallArticle mallArticle) {
        Date now = new Date();
        mallArticle.setModified(now);
        mallArticle.setState(StateEnum.DELETED.getValue());
        return mallArticleMapper.deleteByPrimaryKeyOnState(mallArticle)==1;
    }

    @Override
    public List<MallArticle> query(MallArticle mallArticle) {
        return mallArticleMapper.query(mallArticle);
    }
}
