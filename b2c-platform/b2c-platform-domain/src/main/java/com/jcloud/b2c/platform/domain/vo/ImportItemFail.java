package com.jcloud.b2c.platform.domain.vo;


/**
 * @description: 导入商品失败记录
 * @author: wukun
 * @requireNo:
 * @createdate: 2017-02-16 17:25
 * @lastdate:
 */
public class ImportItemFail {
    private String  rowNum;

    private String skuId;

    private String resultMsg;

    public String getRowNum() {
        return rowNum;
    }

    public void setRowNum(String rowNum) {
        this.rowNum = rowNum;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    @Override
    public String toString() {
        return "ImportItemFail{" +
                "rowNum=" + rowNum +
                ", resultMsg='" + resultMsg + '\'' +
                '}';
    }
}
