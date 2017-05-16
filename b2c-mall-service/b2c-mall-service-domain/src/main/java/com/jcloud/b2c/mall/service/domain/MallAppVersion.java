package com.jcloud.b2c.mall.service.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * app版本
 * Created by hongyifei on 2017/3/21.
 */
public class MallAppVersion implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 平台（1:IOS 2:Android）
     */
    private Integer platform;

    /**
     * 版本号
     */
    private Integer version;

    /**
     * 状态（0:不需要更新 1:普通更新 2强制更新）
     */
    private Integer status;

    /**
     * 更新信息
     */
    private String msg;

    /**
     * 下载链接
     */
    private String url;

    /**
     * 创建时间
     */
    private Date created;

    /**
     * 修改时间
     */
    private Date modified;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPlatform() {
        return platform;
    }

    public void setPlatform(Integer platform) {
        this.platform = platform;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    @Override
    public String toString() {
        return "MallAppVersion{" +
                "id=" + id +
                ", platform=" + platform +
                ", version=" + version +
                ", status=" + status +
                ", msg='" + msg + '\'' +
                ", url='" + url + '\'' +
                ", created=" + created +
                ", modified=" + modified +
                '}';
    }
}
