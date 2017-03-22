package com.core.scpwms.server.enumerate;

/**
 * 库存可用状态
 */
public interface EnuInvStatus {

    /**
     * 通常品（良品，可以正常发货）
     */
    public static final String AVAILABLE = "AVAILABLE";

    /**
     * 限定品（针对特定的客户，特定的订单可以发货）
     */
    public static final String QC = "QC";

    /**
     * 不良品（需要报废的商品）
     */
    public static final String SCRAP = "SCRAP";
    
    /**
     * 保留品（由于各种原因，暂时不能出库）
     */
    public static final String FREEZE = "FREEZE";
}
