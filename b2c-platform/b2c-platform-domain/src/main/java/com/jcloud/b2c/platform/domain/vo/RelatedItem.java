/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: RelatedItem.java project: jcloud-b2c-platform
 * @creator: lidongxing
 * @date: 2017/3/2
 */

package com.jcloud.b2c.platform.domain.vo;

/**
 * @description: RELATED ITEM 
 * @author: lidongxing
 * @requireNo:
 * @createdate: 2017-03-02 16:08
 * @lastdate:
 */

public class RelatedItem {
    private Long id;
    private Long itemId;
    private Long skuId;
    private String skuImg;
    private String skuName;
    private boolean choosed;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public String getSkuImg() {
        return skuImg;
    }

    public void setSkuImg(String skuImg) {
        this.skuImg = skuImg;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public boolean isChoosed() {
        return choosed;
    }

    public void setChoosed(boolean choosed) {
        this.choosed = choosed;
    }
}
