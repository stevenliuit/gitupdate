/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: MallAdFloor.java project: jcloud-b2c-mall-service
 * @creator: lidongxing
 * @date: 2017/2/15
 */

package com.jcloud.b2c.mall.service.client.vo;

import java.io.Serializable;

/**
 * @description: 广告和楼层关联
 * @author: lidongxing
 * @requireNo:
 * @createdate: 2017-02-15 11:02
 * @lastdate:
 */

public class MallAdFloorVo extends MallAdPrincipalVo implements Serializable {
    /**
     * 楼层ID
     */
    private Long floorId;

    private MallFloorVo mallFloor;

    public MallFloorVo getMallFloor() {
        return mallFloor;
    }

    public void setMallFloor(MallFloorVo mallFloor) {
        this.mallFloor = mallFloor;
    }

    public Long getFloorId() {
        return floorId;
    }

    public void setFloorId(Long floorId) {
        this.floorId = floorId;
    }

    @Override
    public String toString() {
        return "MallAdFloorVo{" +
                "floorId=" + floorId +
                ", mallFloor=" + mallFloor +
                '}';
    }
}
