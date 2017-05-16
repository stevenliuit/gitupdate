package com.jcloud.b2c.mall.service.domain;

import java.util.Date;

/**
 * app头条实体
 */
public class MallTopnewsArticle {
    /**
     * 主键
     */
    private Long id;
    /**
     * 租户id
     */
    private Long tenantId;
    /**
     * 客户端类型 1、PC 2、H5 3、Android 4、IOS
     * com.jcloud.b2c.mall.service.client.enums.ClientTypeEnum
     */
    private Integer clientType;

    /**
     * 文章标题
     */
    private String title;
    /**
     * 文章id
     */
    private Long articleId;
    /**
     * 文章url
     */
    private String articleUrl;
    /**
     * 序号
     */
    private Integer sortNum;
    /**
     * -1、删除 1、上架 2、下架
     */
    private Integer state;
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

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public Integer getClientType() {
        return clientType;
    }

    public void setClientType(Integer clientType) {
        this.clientType = clientType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public String getArticleUrl() {
        return articleUrl;
    }

    public void setArticleUrl(String articleUrl) {
        this.articleUrl = articleUrl;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
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
}