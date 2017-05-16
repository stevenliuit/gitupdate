/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: MallCategoryService.java project: jcloud-b2c-platform
 * @creator: lidongxing
 * @date: 2017/2/25
 */

package com.jcloud.b2c.platform.service.mall;

import com.jcloud.b2c.mall.service.client.enums.ClientTypeEnum;
import com.jcloud.b2c.mall.service.client.vo.MallCategoryVo;
import com.jcloud.b2c.platform.domain.PageTypeEnum;

import java.util.Map;

/**
 * @description: MALL CATEGORY SERVICE 
 * @author: lidongxing
 * @requireNo:
 * @createdate: 2017-02-25 14:10
 * @lastdate:
 */

public interface MallCategoryService {
    Map<String, MallCategoryVo> getCategoryMap(MallCategoryVo mallCategoryVo);
    MallCategoryVo getCategory(Long id,Long tenantId);
    Integer getCategoryMaxLevel(ClientTypeEnum ct, PageTypeEnum pageType);
}
