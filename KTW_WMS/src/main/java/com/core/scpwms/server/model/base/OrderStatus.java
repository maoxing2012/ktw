/**
 * (C)2014 MBP Corporation. All rights reserved.
 * 项目名称 : 华耐供应链
 * 子项目名称: 仓库系统
 * 文件名: OrderStatus.java
 */

package com.core.scpwms.server.model.base;

import com.core.business.model.TrackingEntity;

/**
 * <p>请添加注释</p>
 *
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *  2014/07/14<br/>
 */
@SuppressWarnings("all")
public class OrderStatus extends TrackingEntity {

    /** 状态对应的Enumerate */
    private String statusEnum;

    /** 变更前状态 */
    private String statusFrom;

    /** 变更后状态 */
    private String statusTo;

    private Long orderId;

    private String orderType;

    public String getStatusEnum() {
        return this.statusEnum;
    }

    public void setStatusEnum(String statusEnum) {
        this.statusEnum = statusEnum;
    }

    public String getStatusFrom() {
        return this.statusFrom;
    }

    public void setStatusFrom(String statusFrom) {
        this.statusFrom = statusFrom;
    }

    public String getStatusTo() {
        return this.statusTo;
    }

    public void setStatusTo(String statusTo) {
        this.statusTo = statusTo;
    }

    public Long getOrderId() {
        return this.orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOrderType() {
        return this.orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

}
