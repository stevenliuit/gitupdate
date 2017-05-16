/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: BannerTypeEnum.java project: jcloud-b2c-mall-service
 * @creator: lidongxing
 * @date: 2017/2/15
 */

package com.jcloud.b2c.mall.service.client.enums;

/**
 * @description: BannerType
 * @author: lidongxing
 * @requireNo:
 * @createdate: 2017-02-15 10:46
 * @lastdate:
 */

public enum BannerTypeEnum {
    BANNER(1,"轮播图"),
    ADSENSE(2,"广告位"),
    CEILING_ADSENSE(3,"吸顶广告"),
    SHORTCUT(4,"快捷入口"),
    SPECIAL(5,"专题管理"),
    APPAD(6,"APP广告位管理"),
    HOTCLASS(7,"热门分类"),
    BRAND(8,"品牌管理"),
    FLASH(9,"APP闪图");


    private int value;
    private String name;

    BannerTypeEnum(int value, String name) {
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
