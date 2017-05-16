package com.jcloud.b2c.mall.service.mapper;

import com.jcloud.b2c.mall.service.domain.MallArticle;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MallArticleMapper {
    int deleteByPrimaryKey(MallArticle record);

    int insert(MallArticle record);

    int insertSelective(MallArticle record);

    MallArticle selectByPrimaryKey(MallArticle record);

    int updateByPrimaryKeySelective(MallArticle record);

    int updateByPrimaryKey(MallArticle record);

    int deleteByPrimaryKeyOnState(MallArticle record);

    List<MallArticle> query(MallArticle mallArticle);
}