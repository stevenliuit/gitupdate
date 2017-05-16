package com.jcloud.b2c.platform.domain.vo;

/**
 * @description: 导入商品实体类
 * @author: wukun
 * @requireNo:
 * @createdate: 2017-02-15 17:17
 * @lastdate:
 */
public class ImportItemVo {
    //商品序号
    private int num;

    //商品id
    private Long skuId;

    //一级类目id
    private Long firstCid;

    //一级类目名称
    private String firstCname;

    //二级类目id
    private Long secondCid;

    //二级类目名称
    private String secondCname;

    //三级类目id
    private Long thirdCid;

    //三级类目名称
    private String thirdCname;

    //状态：1为未添加过 2为已添加过
    private int status;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Long getFirstCid() {
        return firstCid;
    }

    public void setFirstCid(Long firstCid) {
        this.firstCid = firstCid;
    }

    public String getFirstCname() {
        return firstCname;
    }

    public void setFirstCname(String firstCname) {
        this.firstCname = firstCname;
    }

    public Long getSecondCid() {
        return secondCid;
    }

    public void setSecondCid(Long secondCid) {
        this.secondCid = secondCid;
    }

    public String getSecondCname() {
        return secondCname;
    }

    public void setSecondCname(String secondCname) {
        this.secondCname = secondCname;
    }

    public Long getThirdCid() {
        return thirdCid;
    }

    public void setThirdCid(Long thirdCid) {
        this.thirdCid = thirdCid;
    }

    public String getThirdCname() {
        return thirdCname;
    }

    public void setThirdCname(String thirdCname) {
        this.thirdCname = thirdCname;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ImportItemVo{" +
                "num=" + num +
                ", skuId=" + skuId +
                ", firstCid=" + firstCid +
                ", firstCname='" + firstCname + '\'' +
                ", secondCid=" + secondCid +
                ", secondCname='" + secondCname + '\'' +
                ", thirdCid=" + thirdCid +
                ", thirdCname='" + thirdCname + '\'' +
                '}';
    }
}
