package com.jcloud.b2c.mall.service.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * liuhao
 * Created by issuser on 2017/3/3.
 * 活动页
 */
public class MallActive implements Serializable{

    /**
     * 主键
     */
    private Long id;

    /**
     * 租户ID
     */
    private Long tenantId;

    /**
     *编码
     */
    private String code;

    /**
     *文本内容地址
     */
    private String contentUrl;

    /**
     *1、PC 2、H5 3、Android 4、IOS
     */
    private Integer clientType;

    /**
     * 状态
     *1、可用 -1不可用
     */
    private Integer state;

    /**
    *带头部或不带头部
     * 0：不带头部，1：带头部
    */
    private Integer isHead;

    /**
     *创建时间
     */
    private Date created;

    /**
     *修改时间
     */
    private Date modified;


    /**
     * 业务字段
     * 获取到的HTML代码
     *
     */
    private String htmlStr;

    public String getHtmlStr() {
        return htmlStr;
    }

    public void setHtmlStr(String htmlStr) {
        this.htmlStr = htmlStr;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    public Integer getClientType() {
        return clientType;
    }

    public void setClientType(Integer clientType) {
        this.clientType = clientType;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
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

    public Integer getIsHead() {
        return isHead;
    }

    public void setIsHead(Integer isHead) {
        this.isHead = isHead;
    }

    @Override
    public String toString() {
        return "MallActive{" +
                "id=" + id +
                ", tenantId=" + tenantId +
                ", code='" + code + '\'' +
                ", contentUrl='" + contentUrl + '\'' +
                ", clientType=" + clientType +
                ", state=" + state +
                ", isHead=" + isHead +
                ", created=" + created +
                ", modified=" + modified +
                ", htmlStr='" + htmlStr + '\'' +
                '}';
    }
}
