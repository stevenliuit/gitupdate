/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: MallAdCategory.java project: jcloud-b2c-mall-service
 * @creator: lidongxing
 * @date: 2017/2/16
 */

package com.jcloud.b2c.mall.service.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @description: 广告和类目关联
 * @author: lidongxing
 * @requireNo:
 * @createdate: 2017-02-16 11:39
 * @lastdate:
 */

public class MallAdCategory extends MallAdPrincipal implements Serializable {
    /**
     * 类目ID
     */
    private Long categoryId;

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "MallAdCategory{" +
                "categoryId=" + categoryId +
                "} " + super.toString();
    }
}
