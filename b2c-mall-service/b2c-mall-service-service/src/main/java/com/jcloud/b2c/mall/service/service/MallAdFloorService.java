package com.jcloud.b2c.mall.service.service;

import com.jcloud.b2c.mall.service.domain.MallAdFloor;

import java.util.List;

/**
 * @description:
 * @author: YueZheng
 * @requireNo:
 * @createdate: 2017/2/21 10:54
 * @lasdate
 */
public interface MallAdFloorService {

    List<MallAdFloor> query(Long floorId, Integer adType, Long tenantId);

    boolean add(MallAdFloor mallAdFloor);

    boolean delete(MallAdFloor mallAdFloor);

    MallAdFloor get(Long id, Long tenantId);

    boolean update(MallAdFloor mallAdFloor, Integer beforeSort);

    List<MallAdFloor> queryBySpecial(MallAdFloor mallAdFloor);

    boolean addBySpecial(MallAdFloor mallAdFloor);

    boolean updateBySpecial(MallAdFloor mallAdFloor);

    boolean deleteBySpecial(MallAdFloor mallAdFloor);
}
