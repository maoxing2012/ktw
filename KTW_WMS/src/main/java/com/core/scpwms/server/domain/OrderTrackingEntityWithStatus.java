/**
 * (C)2014 MBP Corporation. All rights reserved.
 * 项目名称 : 华耐供应链
 * 子项目名称: 仓库系统
 * 文件名: HistoryTrackingEntity.java
 */

package com.core.scpwms.server.domain;

import com.core.business.model.TrackingEntityWithVer;

/**
 * <p>订单的共同抽象类</p>
 *
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *  2014/07/07<br/>
 */
@SuppressWarnings("all")
public abstract class OrderTrackingEntityWithStatus extends TrackingEntityWithVer {

    /**
     * 订单状态
     */
    private Long status;
    
    public Long getStatus() {
        return this.status;
    }

    /**
     * 
     * <p>修改订单状态请使用 OrderStatusHelper.changeStatus方法</p>
     *
     * @param status
     */
    private void setStatus(Long status) {
        this.status = status;
    }

    /**
     * 
     * <p>修改订单状态请使用 OrderStatusHelper.changeStatus方法</p>
     *
     * @param status
     */
    public void changeStatus(Long status) {
        this.status = status;
    }

    public abstract String getStatusEnum();
}
