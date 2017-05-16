package com.jcloud.b2c.mall.service.service;

import com.jcloud.b2c.mall.service.domain.MallActive;

import java.util.List;

/**
 * liuhao
 * Created by issuser on 2017/3/3.
 * 活动页Service
 */
public interface MallActiveService {

    MallActive get(Long id, Long tenantId);

    List<MallActive> query(MallActive mallActive);

    boolean add(MallActive mallActive);

    boolean update(MallActive mallActive);

    boolean delete(MallActive mallActive);
}
