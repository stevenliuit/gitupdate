/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: MallAdFloor.java project: jcloud-b2c-mall-service
 * @creator: lidongxing
 * @date: 2017/2/15
 */

package com.jcloud.b2c.mall.service.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @description: 广告和楼层关联
 * @author: lidongxing
 * @requireNo:
 * @createdate: 2017-02-15 11:02
 * @lastdate:
 */

public class MallAdFloor extends MallAdPrincipal implements Serializable {
    /**
     * 楼层ID
     */
    private Long floorId;

    private MallFloor mallFloor;

    public Long getFloorId() {
        return floorId;
    }

    public void setFloorId(Long floorId) {
        this.floorId = floorId;
    }

    public MallFloor getMallFloor() {
        return mallFloor;
    }

    public void setMallFloor(MallFloor mallFloor) {
        this.mallFloor = mallFloor;
    }

    @Override
    public String toString() {
        return "MallAdFloor{" +
                "floorId=" + floorId +
                ", mallFloor=" + mallFloor +
                '}';
    }
}
