package com.jcloud.b2c.mall.service.mapper;

import com.jcloud.b2c.mall.service.domain.MallFunction;
import com.jcloud.b2c.mall.service.domain.MallOperator;
import com.jcloud.b2c.mall.service.domain.MallRole;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @Method: 用户mapper
 * @Author:zhangjian
 * @Date: 2017/5/15
 */
@Mapper
public interface MallOperatorMapper {

    MallOperator getByOperatorKey(MallOperator mallOperator);

    int updateByOperatorKey(MallOperator mallOperator);

    int deleteByOperatorOrleKey(MallOperator mallOperator);

    int addOrUpdateOperatorRole(Map<String,Object> map);

    int insertOperator(MallOperator mallOperator);

    List<MallOperator> querySelective(MallOperator mallOperator);

    List<MallFunction> queryFunction(Map<String,Object> map);
    
    List<MallRole> queryOperatorRole(MallOperator operatorId);

}
