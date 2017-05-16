/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: EsItemVo.java project: jcloud-b2c-mall-service
 * @creator: wangxin17
 * @date: 2017/3/2
 */
package com.jcloud.b2c.mall.service.client.vo;

import com.jcloud.b2c.common.common.util.JacksonUtil;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description:
 * @author: wangxin17
 * @requireNo:
 * @createdate: 2017-03-02 10:00
 * @lastdate:
 */
public class EsItemVo {
    /**
     * 基础类目1级
     */
    private Long baseCat1Id;
    private String baseCat1Name;

    /**
     * 基础类目2级
     */
    private Long baseCat2Id;
    private String baseCat2Name;

    /**
     * 基础类目3级
     */
    private Long baseCat3Id;
    private String baseCat3Name;

    /**
     * 运营类目1级
     */
    private Long cat1Id;
    private String cat1Name;

    /**
     * 运营类目2级
     */
    private Long cat2Id;
    private String cat2Name;

    /**
     * 运营类目3级
     */
    private Long cat3Id;
    private String cat3Name;

    /**
     * app运营类目1级
     */
    private Long appCat1Id;
    private String appCat1Name;

    /**
     * app运营类目2级
     */
    private Long appCat2Id;
    private String appCat2Name;

    /**
     * app运营类目字符串
     * eg:appCat1Id:appCat1Name;appCat2Id:appCat2Name;
     */
    private String appCatString;

    /**
     * 频道类目
     * eg:cat1Id:cat1Name;cat2Id:cat2Name;
     */
    private String channelCategories;

    /**
     *运营类目字符串
     * eg:category1Id:category1Name;category2Id:category2Name;category3Id:category3Name;
     */
    private String categoryString;

    /**
     * 商品id
     */
    private Long itemId;
    /**
     * 商品名称
     */
    private String itemName;
    /**
     * 商品图片
     */
    private String itemImgSrc;

    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * 品牌ID
     */
    private Long  brandId;

    /**
     *商品状态
     */
    private Integer state;

    /**
     * 销售价格
     */
    private BigDecimal  price;

    /**
     * 市场价
     */
    private BigDecimal marketPrice;

    private Long itemCreated;
    private Long itemModified;

    private Date created;
    private Date modified;

    /**
     * 转换为json
     * @return
     */
    public String toJsonString() {
        return JacksonUtil.convert(this);
    }

    public Long getBaseCat1Id() {
        return baseCat1Id;
    }

    public void setBaseCat1Id(Long baseCat1Id) {
        this.baseCat1Id = baseCat1Id;
    }

    public String getBaseCat1Name() {
        return baseCat1Name;
    }

    public void setBaseCat1Name(String baseCat1Name) {
        this.baseCat1Name = baseCat1Name;
    }

    public Long getBaseCat2Id() {
        return baseCat2Id;
    }

    public void setBaseCat2Id(Long baseCat2Id) {
        this.baseCat2Id = baseCat2Id;
    }

    public String getBaseCat2Name() {
        return baseCat2Name;
    }

    public void setBaseCat2Name(String baseCat2Name) {
        this.baseCat2Name = baseCat2Name;
    }

    public Long getBaseCat3Id() {
        return baseCat3Id;
    }

    public void setBaseCat3Id(Long baseCat3Id) {
        this.baseCat3Id = baseCat3Id;
    }

    public String getBaseCat3Name() {
        return baseCat3Name;
    }

    public void setBaseCat3Name(String baseCat3Name) {
        this.baseCat3Name = baseCat3Name;
    }

    public Long getCat1Id() {
        return cat1Id;
    }

    public void setCat1Id(Long cat1Id) {
        this.cat1Id = cat1Id;
    }

    public String getCat1Name() {
        return cat1Name;
    }

    public void setCat1Name(String cat1Name) {
        this.cat1Name = cat1Name;
    }

    public Long getCat2Id() {
        return cat2Id;
    }

    public void setCat2Id(Long cat2Id) {
        this.cat2Id = cat2Id;
    }

    public String getCat2Name() {
        return cat2Name;
    }

    public void setCat2Name(String cat2Name) {
        this.cat2Name = cat2Name;
    }

    public Long getCat3Id() {
        return cat3Id;
    }

    public void setCat3Id(Long cat3Id) {
        this.cat3Id = cat3Id;
    }

    public String getCat3Name() {
        return cat3Name;
    }

    public void setCat3Name(String cat3Name) {
        this.cat3Name = cat3Name;
    }

    public Long getAppCat1Id() {
        return appCat1Id;
    }

    public void setAppCat1Id(Long appCat1Id) {
        this.appCat1Id = appCat1Id;
    }

    public String getAppCat1Name() {
        return appCat1Name;
    }

    public void setAppCat1Name(String appCat1Name) {
        this.appCat1Name = appCat1Name;
    }

    public Long getAppCat2Id() {
        return appCat2Id;
    }

    public void setAppCat2Id(Long appCat2Id) {
        this.appCat2Id = appCat2Id;
    }

    public String getAppCat2Name() {
        return appCat2Name;
    }

    public void setAppCat2Name(String appCat2Name) {
        this.appCat2Name = appCat2Name;
    }

    public String getAppCatString() {
        return appCatString;
    }

    public void setAppCatString(String appCatString) {
        this.appCatString = appCatString;
    }

    public String getChannelCategories() {
        return channelCategories;
    }

    public void setChannelCategories(String channelCategories) {
        this.channelCategories = channelCategories;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemImgSrc() {
        return itemImgSrc;
    }

    public void setItemImgSrc(String itemImgSrc) {
        this.itemImgSrc = itemImgSrc;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getCategoryString() {
        return categoryString;
    }

    public void setCategoryString(String categoryString) {
        this.categoryString = categoryString;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    public Long getItemCreated() {
        return itemCreated;
    }

    public void setItemCreated(Long itemCreated) {
        this.itemCreated = itemCreated;
    }

    public Long getItemModified() {
        return itemModified;
    }

    public void setItemModified(Long itemModified) {
        this.itemModified = itemModified;
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
        return "EsItemVo{" +
                "baseCat1Id=" + baseCat1Id +
                ", baseCat1Name='" + baseCat1Name + '\'' +
                ", baseCat2Id=" + baseCat2Id +
                ", baseCat2Name='" + baseCat2Name + '\'' +
                ", baseCat3Id=" + baseCat3Id +
                ", baseCat3Name='" + baseCat3Name + '\'' +
                ", cat1Id=" + cat1Id +
                ", cat1Name='" + cat1Name + '\'' +
                ", cat2Id=" + cat2Id +
                ", cat2Name='" + cat2Name + '\'' +
                ", cat3Id=" + cat3Id +
                ", cat3Name='" + cat3Name + '\'' +
                ", appCat1Id=" + appCat1Id +
                ", appCat1Name='" + appCat1Name + '\'' +
                ", appCat2Id=" + appCat2Id +
                ", appCat2Name='" + appCat2Name + '\'' +
                ", appCatString='" + appCatString + '\'' +
                ", channelCategories='" + channelCategories + '\'' +
                ", categoryString='" + categoryString + '\'' +
                ", itemId=" + itemId +
                ", itemName='" + itemName + '\'' +
                ", itemImgSrc='" + itemImgSrc + '\'' +
                ", brandName='" + brandName + '\'' +
                ", brandId=" + brandId +
                ", state=" + state +
                ", price=" + price +
                ", marketPrice=" + marketPrice +
                ", itemCreated=" + itemCreated +
                ", itemModified=" + itemModified +
                ", created=" + created +
                ", modified=" + modified +
                '}';
    }
}
