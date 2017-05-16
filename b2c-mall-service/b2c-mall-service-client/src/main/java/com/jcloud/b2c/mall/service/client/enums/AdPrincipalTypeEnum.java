/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: AdPrincipalEnum.java project: jcloud-b2c-mall-service
 * @creator: lidongxing
 * @date: 2017/2/16
 */

package com.jcloud.b2c.mall.service.client.enums;

/**
 * @description:广告主体
 * @author: lidongxing
 * @requireNo:
 * @createdate: 2017-02-16 17:40
 * @lastdate:
 */

public enum AdPrincipalTypeEnum {
    CATEGORY(1,"category"),
    CHANNEL(2,"channel"),
    FLOOR(3,"floor"),
    SUBJECT(4,"subject");

    private int value;
    private String code;

    AdPrincipalTypeEnum(int value, String code) {
        this.value = value;
        this.code=code;
    }

    public static AdPrincipalTypeEnum getAdPrincipalByValue(Integer value) {
        if(value !=null) {
            for (AdPrincipalTypeEnum enumObj : AdPrincipalTypeEnum.values()) {
                if (enumObj.getValue() == value) {
                    return enumObj;
                }
            }
        }
        return null;
    }

    public static AdPrincipalTypeEnum getAdPrincipalByCode(String code) {
        for (AdPrincipalTypeEnum enumObj : AdPrincipalTypeEnum.values()) {
            if (enumObj.getCode().equals(code)) {
                return enumObj;
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
}
