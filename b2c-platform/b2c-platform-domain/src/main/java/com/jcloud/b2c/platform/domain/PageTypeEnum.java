/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: PageTypeEnum.java project: jcloud-b2c-platform
 * @creator: lidongxing
 * @date: 2017/2/28
 */

package com.jcloud.b2c.platform.domain;

/**
 * @description: 页面类型
 * @author: lidongxing
 * @requireNo:
 * @createdate: 2017-02-28 17:55
 * @lastdate:
 */

public enum PageTypeEnum {
    INDEX(1,"index"),
    CHANNEL(2,"channel");

    private int value;
    private String name;

    PageTypeEnum(int value, String name) {
        this.value = value;
        this.name=name;
    }

    public static PageTypeEnum getPageTypeByValue(Integer value) {
        if(value !=null) {
            for (PageTypeEnum enumObj : PageTypeEnum.values()) {
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
