package com.jcloud.b2c.mall.service.client.enums;

/**
 * @description:
 * @author: YueZheng
 * @requireNo:
 * @createdate: 2017/2/27 10:11
 * @lasdate
 */
public enum ChannelFieldEnum {

    DEFAULT(Long.valueOf(0),"为PC首页");

    private Long value;
    private String name;

    ChannelFieldEnum(Long value, String name) {
        this.value = value;
        this.name = name;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
