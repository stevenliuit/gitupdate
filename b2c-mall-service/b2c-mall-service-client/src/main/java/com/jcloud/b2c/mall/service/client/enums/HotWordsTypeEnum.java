/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: HotWordsTypeEnum.java project: jcloud-b2c-mall-service
 * @creator: wangxin17
 * @date: 2017/2/18
 */
package com.jcloud.b2c.mall.service.client.enums;

/**
 * @description: 热词类型枚举
 * @author: wangxin17
 * @requireNo:
 * @createdate: 2017-02-18 14:37
 * @lastdate:
 */
public enum HotWordsTypeEnum {

    DEFAULT_SEARCH(2,"默认搜索框热词"),
    TOP(1,"顶部热词"),
    ;

    private int value;
    private String name;

    HotWordsTypeEnum(int value, String name) {
        this.value = value;
        this.name=name;
    }

    public static HotWordsTypeEnum getTypeByValue(Integer value) {
        if(value !=null) {
            for (HotWordsTypeEnum enumObj : HotWordsTypeEnum.values()) {
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
