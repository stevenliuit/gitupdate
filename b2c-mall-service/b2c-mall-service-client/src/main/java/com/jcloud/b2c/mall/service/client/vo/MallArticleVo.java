package com.jcloud.b2c.mall.service.client.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 文章主体
 */
public class MallArticleVo {
    /**
     * 主键
     */
    private Long id;
    /**
     * 租户ID
     */
    private Long tenantId;
    /**
     * 客户端类型 1、PC 2、H5 3、Android 4、IOS
     * com.jcloud.b2c.mall.service.client.enums.ClientTypeEnum
     */
    private Byte clientType;
    /**
     * 类目ID
     */
    private Long categoryId;
    /**
     * 文章标题
     */
    private String title;
    /**
     * 文章内容
     */
    private String content;
    /**
     * 发布时间
     */
    private Date newstime;
    /**
     * 排序
     */
    private Integer sortNum;
    /**
     * -1、删除 1、未发布 2、发布
     */
    private Integer state;
    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date created;
    /**
     * 修改时间
     */
    private Date modified;

    /**
     * 查询用 后置日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date afterDate;

    /**
     * 查询用 一级类目ID
     */
    private Long parentId;
    /**
     * 查询用 一级类目名称
     */
    private String categoryParent;
    /**
     * 查询用 二级类目名称
     */
    private String categoryName;

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

    public Byte getClientType() {
        return clientType;
    }

    public void setClientType(Byte clientType) {
        this.clientType = clientType;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getNewstime() {
        return newstime;
    }

    public void setNewstime(Date newstime) {
        this.newstime = newstime;
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

    public Date getAfterDate() {
        return afterDate;
    }

    public void setAfterDate(Date afterDate) {
        this.afterDate = afterDate;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getCategoryParent() {
        return categoryParent;
    }

    public void setCategoryParent(String categoryParent) {
        this.categoryParent = categoryParent;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return "MallArticleVo{" +
                "id=" + id +
                ", tenantId=" + tenantId +
                ", clientType=" + clientType +
                ", categoryId=" + categoryId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", newstime=" + newstime +
                ", sortNum=" + sortNum +
                ", state=" + state +
                ", created=" + created +
                ", modified=" + modified +
                ", afterDate=" + afterDate +
                ", parentId=" + parentId +
                ", categoryParent='" + categoryParent + '\'' +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }
}