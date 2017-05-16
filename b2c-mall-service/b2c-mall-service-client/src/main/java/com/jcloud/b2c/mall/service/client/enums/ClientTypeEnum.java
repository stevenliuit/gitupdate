/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: ClientTypeEnum.java project: jcloud-b2c-mall-service
 * @creator: lidongxing
 * @date: 2017/2/15
 */

package com.jcloud.b2c.mall.service.client.enums;

/**
 * @description: 客户端类型
 * @author: lidongxing
 * @requireNo:
 * @createdate: 2017-02-15 11:29
 * @lastdate:
 */

public enum ClientTypeEnum {
    PC(1,"PC"),
    H5(2,"H5"),
    ANDROID(3,"Android"),
    IOS(4,"IOS");

    private int value;
    private String name;

    ClientTypeEnum(int value, String name) {
        this.value = value;
        this.name=name;
    }

    public static ClientTypeEnum getClientTypeByValue(Integer value) {
        if(value !=null) {
            for (ClientTypeEnum enumObj : ClientTypeEnum.values()) {
                if (enumObj.getValue() == value) {
                    return enumObj;
                }
            }
        }
        return null;
    }
    public static ClientTypeEnum getClientTypeByName(String name) {
        if(name !=null) {
            for (ClientTypeEnum enumObj : ClientTypeEnum.values()) {
                if (enumObj.getName().equalsIgnoreCase(name)) {
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
