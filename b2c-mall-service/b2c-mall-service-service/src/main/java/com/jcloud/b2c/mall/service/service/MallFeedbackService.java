package com.jcloud.b2c.mall.service.service;

import com.jcloud.b2c.mall.service.domain.MallFeedback;

import java.util.List;

/**
 * 意见反馈Service
 * Created by hongyifei on 2017/3/9.
 */
public interface MallFeedbackService {

    boolean add(MallFeedback mallFeedback);

    boolean update(MallFeedback mallFeedback);

    boolean delete(MallFeedback mallFeedback);

    List<MallFeedback> query(MallFeedback mallFeedback);

    Integer queryCount(MallFeedback mallFeedback);
}
