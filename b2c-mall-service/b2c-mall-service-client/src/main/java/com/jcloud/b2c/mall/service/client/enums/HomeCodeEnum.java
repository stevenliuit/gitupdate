package com.jcloud.b2c.mall.service.client.enums;

/**
 * @description:HomeCodeEnum
 * @author: YueZheng
 * @requireNo:
 * @createdate: 2017/2/27 9:53
 * @lasdate
 */
public enum HomeCodeEnum {

    NORMAL("-1","无频道首页");

    private String value;
    private String name;

    HomeCodeEnum(String value, String name) {
        this.value = value;
        this.name = name;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
