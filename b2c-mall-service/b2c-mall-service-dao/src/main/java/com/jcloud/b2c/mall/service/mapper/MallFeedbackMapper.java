package com.jcloud.b2c.mall.service.mapper;

import com.jcloud.b2c.mall.service.domain.MallFeedback;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 意见反馈
 * Created by hongyifei on 2017/3/9.
 */
@Mapper
public interface MallFeedbackMapper {

    int insert(MallFeedback mallFeedback);

    int updateByPrimaryKey(MallFeedback mallFeedback);

    int deleteByPrimaryKey(MallFeedback mallFeedback);

    List<MallFeedback> querySelective(MallFeedback mallFeedback);

    int queryCount(MallFeedback mallFeedback);
}
