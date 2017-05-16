package com.jcloud.b2c.mall.service.service;

import com.jcloud.b2c.mall.service.domain.MallHotWords;

import java.util.List;

/**
 * @description: 热词SERVICE
 * @author: YueZheng
 * @requireNo:
 * @createdate: 2017/2/17 16:59
 * @lasdate
 */
public interface MallHotWordsService {

    MallHotWords get(Long id, Long tenantId);

    boolean add(MallHotWords mallHotWords);

    boolean update(MallHotWords mallHotWords);

    boolean delete(MallHotWords mallHotWords);

    List<MallHotWords> query(MallHotWords mallHotWords);

    boolean updateSort(Long id, Long changeId, Long tenantId);

    Integer selectMaxSort(MallHotWords mallHotWords);

    boolean updateToStick(MallHotWords mallHotWords);

    boolean updateToFinally(MallHotWords mallHotWords);

    boolean updateMoveUp(MallHotWords mallHotWords);

    boolean updateMoveDown(MallHotWords mallHotWords);

}
