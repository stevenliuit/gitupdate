/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: AdTypeEnum.java project: jcloud-b2c-mall-service
 * @creator: lidongxing
 * @date: 2017/2/15
 */

package com.jcloud.b2c.mall.service.client.enums;

/**
 * @description: 广告类型
 * @author: lidongxing
 * @requireNo:
 * @createdate: 2017-02-15 10:55
 * @lastdate:
 */

public enum AdTypeEnum {
    NORMAL(1,"普通"),
    RECOMMEND(2,"推荐"),
    FULLRECOMMEND(3,"通栏推荐"),
    SPECIAL(4,"专题");

    private int value;
    private String name;

    AdTypeEnum(int value, String name) {
        this.value = value;
        this.name=name;
    }

    public static AdTypeEnum getAdTypeByValue(Integer value) {
        if(value !=null) {
            for (AdTypeEnum enumObj : AdTypeEnum.values()) {
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
