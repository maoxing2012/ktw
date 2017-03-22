package com.core.scpwms.server.enumerate;

/**
 * 波次作业方式
 * 边拣边分、分拣库位分拣
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 *
 */
public interface EnuWaveWorkModel {

    // 按批次拣货(商品别拣选方式，播种式拣选方式)
    public static final String SUM_SKU_PICK = "SUM_SKU_PICK";

    // 按单拣货(订单别拣选方式，摘果式拣选方式)
    public static final String SUM_ORDER_PICK = "SUM_ORDER_PICK";

}
