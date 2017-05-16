/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: ListOrderParam.java project: b2c-order-service
 * @creator: wangzixing
 * @date: 2017/2/11
 */

package com.jcloud.b2c.platform.domain.vo;

import com.jcloud.b2c.order.client.vo.UserRequest;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单列表参数
 * @description:
 * @author: wangzixing
 * @requireNo:
 * @createdate: 2017-02-11 14:35
 * @lastdate:
 */
public class ListOrderParamVo implements Serializable {

    public ListOrderParamVo() {

    }

    /**
     * 订单号
     */
    private String orderId;

    /**
     * 根据父订单id排除已拆单的主订单
     */
    private String parentOrderId;

    /**
     * 查询起始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    /**
     * 查询结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    /**
     * 订单状态 1：待付款 5：商品出库=待收货 7：已取消 8:已完成 null或其他值：全部
     */
    private Integer orderState;

    /**
     * 页号
     */
    private int pageNum = 1;

    /**
     * 页大小
     */
    private int pageSize = 20;

    /**
     * 订单是否已取消
     */
    private Integer yn;

    /**
     * 用户请求
     */
    private UserRequest userRequest=new UserRequest();

    public UserRequest getUserRequest() {
        return userRequest;
    }

    public void setUserRequest(UserRequest userRequest) {
        this.userRequest = userRequest;
    }

    public String getParentOrderId() {
        return parentOrderId;
    }

    public void setParentOrderId(String parentOrderId) {
        this.parentOrderId = parentOrderId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getOrderState() {
        return orderState;
    }

    public void setOrderState(Integer orderState) {
        this.orderState = orderState;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getYn() {
        return yn;
    }

    public void setYn(Integer yn) {
        this.yn = yn;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        if("".equals(orderId)){
            orderId=null;
        }
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "ListOrderParam{" +
                "orderId='" + orderId + '\'' +
                ", parentOrderId='" + parentOrderId + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", orderState=" + orderState +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", yn=" + yn +
                ", userRequest=" + userRequest +
                '}';
    }
}
