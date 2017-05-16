/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: StateEnum.java project: jcloud-b2c-mall-service
 * @creator: lidongxing
 * @date: 2017/2/14
 */

package com.jcloud.b2c.mall.service.client.enums;

/**
 * @description: 推荐枚举
 * @author: yuezheng
 * @requireNo:
 * @createdate: 2017-02-14 10:48
 * @lastdate:
 */

public enum RecommendTypeEnum {
    RECOMMEND(1,"普通推荐"),
    FULLRECOMMEND(2,"通栏推荐");

    private int value;
    private String name;

    RecommendTypeEnum(int value, String name) {
        this.value = value;
        this.name=name;
    }

    public static RecommendTypeEnum getStateByValue(Integer value) {
        if(value !=null) {
            for (RecommendTypeEnum enumObj : RecommendTypeEnum.values()) {
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
