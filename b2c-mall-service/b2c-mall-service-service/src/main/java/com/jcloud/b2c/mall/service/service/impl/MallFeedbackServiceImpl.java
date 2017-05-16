package com.jcloud.b2c.mall.service.service.impl;

import com.jcloud.b2c.mall.service.client.enums.StateEnum;
import com.jcloud.b2c.mall.service.domain.MallFeedback;
import com.jcloud.b2c.mall.service.mapper.MallFeedbackMapper;
import com.jcloud.b2c.mall.service.service.MallFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 意见反馈SERVICEIMPL
 * Created by hongyifei on 2017/3/9.
 */
@Service("mallFeedbackService")
public class MallFeedbackServiceImpl implements MallFeedbackService {

    @Autowired
    private MallFeedbackMapper mallFeedbackMapper;


    @Override
    public boolean add(MallFeedback mallFeedback) {
        Date now = new Date();
        mallFeedback.setCreated(now);
        mallFeedback.setModified(now);
        return mallFeedbackMapper.insert(mallFeedback) == 1?true:false;
    }

    @Override
    public boolean update(MallFeedback mallFeedback) {
        Date now = new Date();
        mallFeedback.setModified(now);
        return mallFeedbackMapper.updateByPrimaryKey(mallFeedback) == 1?true:false;
    }

    @Override
    public boolean delete(MallFeedback mallFeedback) {
        Date now = new Date();
        mallFeedback.setState(StateEnum.DELETED.getValue());
        mallFeedback.setModified(now);
        return mallFeedbackMapper.deleteByPrimaryKey(mallFeedback) == 1?true:false;
    }

    @Override
    public List<MallFeedback> query(MallFeedback mallFeedback) {
        return mallFeedbackMapper.querySelective(mallFeedback);
    }

    @Override
    public Integer queryCount(MallFeedback mallFeedback) {
        return mallFeedbackMapper.queryCount(mallFeedback);
    }
}
