package com.core.scpwms.server.service.common;

import java.util.Date;

import org.springframework.transaction.annotation.Transactional;

import com.core.business.model.security.User;
import com.core.db.server.service.BaseManager;
import com.core.scpwms.server.domain.OrderTrackingEntityWithStatus;

/**
 * 
 * <p>记录订单状态变化时间点的共同类</p>
 *
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *  2014/07/14<br/>
 */
@Transactional(readOnly = false)
public interface OrderStatusHelper extends BaseManager {
    /**
     * 
     * <p>状态变化</p>
     *
     * @param order
     * @param newStatus
     */
    @Transactional
    public void changeStatus(OrderTrackingEntityWithStatus order, Long newStatus);
    
    /**
     * 
     * <p>状态变化</p>
     *
     * @param order
     * @param newStatus
     * @param user
     */
    @Transactional
    public void changeStatus(OrderTrackingEntityWithStatus order, Long newStatus, User user);
    
    /**
     * 
     * <p>状态变化</p>
     *
     * @param order
     * @param newStatus
     */
    @Transactional
    public void changeStatus4Edi(OrderTrackingEntityWithStatus order, Long newStatus);
    
    /**
     * 
     * <p>状态变化</p>
     *
     * @param order
     * @param newStatus
     */
    @Transactional
    public void changeStatus4Other(OrderTrackingEntityWithStatus order, Long newStatus, String operatorName);
    
    /**
     * 
     * <p>取得最后一次状态变化的时间</p>
     *
     * @param orderType
     * @param enumType
     * @param statusFrom
     * @param statusTo
     * @return
     */
    @Transactional
    public Date getLastDate4StatusChange(Long orderId, String orderType, String enumType, Long statusFrom, Long statusTo );
}
