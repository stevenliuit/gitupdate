/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: MallCategoryService.java project: jcloud-b2c-mall-service
 * @creator: lidongxing
 * @date: 2017/2/16
 */

package com.jcloud.b2c.mall.service.service;

import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.mall.service.domain.MallCategory;

import java.util.List;

/**
 * @description: 类目
 * @author: lidongxing
 * @requireNo:
 * @createdate: 2017-02-16 14:47
 * @lastdate:
 */

public interface MallCategoryService {

    MallCategory get(Long id, Long tenantId);

    boolean add(MallCategory mallCategory);
    boolean isUniqueName(MallCategory mallCategory);
    BaseResponseVo<Void> moveCate(MallCategory mallCategory, String action);

    boolean update(MallCategory mallCategory, Integer beforeSort);

    boolean delete(MallCategory mallCategory);

    List<MallCategory> query(MallCategory mallCategory);

    List<MallCategory> queryByParentId(MallCategory mallCategory);

    MallCategory getByIdFromCache(Long tenantId, Long id);

    boolean verifySortNum(MallCategory mallCategory);

    BaseResponseVo<Void> updateMoveUp(MallCategory mallCategory,String action);
}
