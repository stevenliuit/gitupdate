package com.jcloud.b2c.mall.service.client.enums;

/**
 * @description:
 * @author: YueZheng
 * @requireNo:
 * @createdate: 2017/3/4 11:08
 * @lasdate
 */
public enum ArticleStateEnum {
    DELETE(-1,"删除"),
    UNPUBLISHED(1,"未发布"),
    PUBLISH(2,"发布"),
    OFFLINE(3,"下架");

    private int value;
    private String name;

    ArticleStateEnum(int value, String name) {
        this.value = value;
        this.name=name;
    }

    public static BannerTypeEnum getBannerTypeByValue(Integer value) {
        if(value !=null) {
            for (BannerTypeEnum enumObj : BannerTypeEnum.values()) {
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
