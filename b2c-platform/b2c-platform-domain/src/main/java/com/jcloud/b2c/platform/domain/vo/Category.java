package com.jcloud.b2c.platform.domain.vo;

import java.util.Date;


public class Category {
    private Long cid;

    private Long parentCid;

    private Integer status;

    private Integer lev;

    private Integer hasLeaf;

    private Integer sortNumber;

    private Date created;

    private Date modified;

    private Long tenantId;

    private String categoryName;

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public Long getParentCid() {
        return parentCid;
    }

    public void setParentCid(Long parentCid) {
        this.parentCid = parentCid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getLev() {
        return lev;
    }

    public void setLev(Integer lev) {
        this.lev = lev;
    }

    public Integer getHasLeaf() {
        return hasLeaf;
    }

    public void setHasLeaf(Integer hasLeaf) {
        this.hasLeaf = hasLeaf;
    }

    public Integer getSortNumber() {
        return sortNumber;
    }

    public void setSortNumber(Integer sortNumber) {
        this.sortNumber = sortNumber;
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

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return "Category{" +
                "cid=" + cid +
                ", parentCid=" + parentCid +
                ", status=" + status +
                ", lev=" + lev +
                ", hasLeaf=" + hasLeaf +
                ", sortNumber=" + sortNumber +
                ", created=" + created +
                ", modified=" + modified +
                ", tenantId=" + tenantId +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }
}
