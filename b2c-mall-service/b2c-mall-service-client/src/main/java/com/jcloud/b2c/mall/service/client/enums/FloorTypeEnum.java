/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: FloorTypeEnum.java project: jcloud-b2c-mall-service
 * @creator: lidongxing
 * @date: 2017/2/14
 */

package com.jcloud.b2c.mall.service.client.enums;

/**
 * @description: 楼层类型枚举
 * @author: lidongxing
 * @requireNo:
 * @createdate: 2017-02-14 10:27
 * @lastdate:
 */

public enum FloorTypeEnum {
    NORMAL(1,"normal","普通"),
    ACTIVE(2,"active","活动"),
    NEW_ITEM(3,"newItem","新品"),
    SPECIAL(4,"SPECIAL","专题");

    private int value;
    private String code;
    private String name;

    FloorTypeEnum(int value, String code, String name) {
        this.value = value;
        this.code = code;
        this.name=name;
    }

    public static FloorTypeEnum getFloorTypeByValue(Integer value) {
        if(value !=null) {
            for (FloorTypeEnum enumObj : FloorTypeEnum.values()) {
                if (enumObj.getValue() == value) {
                    return enumObj;
                }
            }
        }
        return null;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
