package com.jcloud.b2c.mall.service.service;

import com.jcloud.b2c.mall.service.client.vo.EsItemVo;

import java.util.List;

/**
 * liuhao
 * Created by issuser on 2017/3/2.
 *
 */
public interface EsItemService {

    void putIntoEs(Long itemId, Long tenantId);

    /**
     * 放入es异步任务
     * @param tenantId
     * @param itemId
     */
    void putIntoEsTask(Long tenantId, List<Long> itemId);

    /**
     *
     */
    void putJsonMapping(Long tenantId);

    void deleteIndex(Long tenantId);

    @Deprecated
    void putIntoEs(Long tenantId, EsItemVo esItemVo, int type);
}
