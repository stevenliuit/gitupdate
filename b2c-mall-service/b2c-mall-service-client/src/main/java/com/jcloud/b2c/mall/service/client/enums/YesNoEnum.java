/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: YesNoEnum.java project: jcloud-b2c-mall-service
 * @creator: lidongxing
 * @date: 2017/2/16
 */

package com.jcloud.b2c.mall.service.client.enums;

/**
 * @description: 是否
 * @author: lidongxing
 * @requireNo:
 * @createdate: 2017-02-16 14:24
 * @lastdate:
 */

public enum YesNoEnum {
    NO(0,"否"),
    YES(1,"是");

    private int value;
    private String name;

    YesNoEnum(int value, String name) {
        this.value = value;
        this.name=name;
    }

    public static YesNoEnum getYesNoByValue(Integer value) {
        if(value !=null) {
            for (YesNoEnum enumObj : YesNoEnum.values()) {
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
