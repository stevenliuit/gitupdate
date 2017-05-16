/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: StateEnum.java project: jcloud-b2c-mall-service
 * @creator: lidongxing
 * @date: 2017/2/14
 */

package com.jcloud.b2c.mall.service.client.enums;

/**
 * @description: 状态枚举
 * @author: lidongxing
 * @requireNo:
 * @createdate: 2017-02-14 10:48
 * @lastdate:
 */

public enum StateEnum {
    DELETED(-1,"删除"),
    ON_SHELF(1,"上架"),
    OFF_SHELF(2,"下架");

    private int value;
    private String name;

    StateEnum(int value, String name) {
        this.value = value;
        this.name=name;
    }

    public static StateEnum getStateByValue(Integer value) {
        if(value !=null) {
            for (StateEnum enumObj : StateEnum.values()) {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
