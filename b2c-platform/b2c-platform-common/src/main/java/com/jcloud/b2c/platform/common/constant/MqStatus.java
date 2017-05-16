package com.jcloud.b2c.platform.common.constant;

public enum MqStatus {
    ABNORMAL(0, "停止服务"), NORMAL(1, "运行正常");
    private final int value;
    private final String text;

    private MqStatus(int value, String text) {
        this.value = value;
        this.text = text;
    }

    public int getValue() {
        return this.value;
    }

    public String getText() {
        return this.text;
    }
}
