package com.jcloud.b2c.platform.domain.vo;


/**
 * mq基本信息
 *
 * @author 360
 */
public class MqInfoBean {
    /**
     * IP
     */
    private String ip;
    /**
     * MQ 名称
     */
    private String name;
    /**
     * MQ状态
     */
    private int status;
    /**
     * MQ版本
     */
    private String version;
    /**
     * MQ 存储空间占用
     */
    private int storeUsed;
    /**
     * MQ 内存空间占用
     */
    private int memoryUsed;
    /**
     * MQ 临时空间占用
     */
    private int tempUsed;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getStoreUsed() {
        return storeUsed;
    }

    public void setStoreUsed(String storeUsed) {
        try {
            this.storeUsed = Integer.parseInt(storeUsed);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getMemoryUsed() {
        return memoryUsed;
    }

    public void setMemoryUsed(String memoryUsed) {
        try {
            this.memoryUsed = Integer.parseInt(memoryUsed);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public int getTempUsed() {
        return tempUsed;
    }

    public void setTempUsed(String tempUsed) {
        try {
            this.tempUsed = Integer.parseInt(tempUsed);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
