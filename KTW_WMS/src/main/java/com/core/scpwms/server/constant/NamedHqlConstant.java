/**
 * (C)2014 MBP Corporation. All rights reserved.
 * 项目名称 : 华耐供应链
 * 子项目名称: 仓库系统
 * 文件名: NamedHqlConstant.java
 */

package com.core.scpwms.server.constant;

/**
 * <p>声明HQL名</p>
 *
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *  2014/07/10<br/>
 */
public interface NamedHqlConstant {

    /** 删除作业量 */
    public static final String DELETE_LABOR_KPI = "deleteLaborKpi";

    /** 删除计费报表明细 */
    public static final String DELETE_FEE_REPORT_DETAIL = "deleteFeeReportDetail";

    /** 作业人员计费单头部的费用合计 */
    public static final String GET_SUM_FEE = "getSumFee";

    // ------------------------Inbound--------------------------------
    /** 合计入库单头部的预订数(EA) */
    public static final String UPDATE_INBOUND_DELIVERY_PLAN_QTY = "updateInboundDeliveryPlanQty";

    /** 合计入库单头部的品项数 */
    public static final String UPDATE_INBOUND_DELIVERY_SKU_COUNT = "updateInboundDeliverySkuCount";

    /** 合计入库单头部的加入ASN数(EA) */
    public static final String UPDATE_INBOUND_DELIVERY_ASN_QTY = "updateInboundDeliveryAsnQty";

    /** 根据入库单号和仓库ID取得入库单 */
    public static final String GET_INBOUND_DELIVERY_BY_IBDNUMBER = "getInboundDeliveryByIbdNumber";

    /** 根据入库单号,仓库ID和行号取得入库单明细 */
    public static final String GET_INBOUND_DELIVERY_DETAIL_BY_IBDNUMBER_LINENO = "getInboundDeliveryDetailByIbdNumberLineNo";

    // ------------------------Asn--------------------------------
    /** 合计ASN头部的预订数(EA) */
    public static final String UPDATE_ASN_PLAN_QTY = "updateAsnPlanQty";

    /** 合计ASN头部的品项数 */
    public static final String UPDATE_ASN_SKU_COUNT = "updateAsnSkuCount";
    
    /** 合计ASN头部的订单数 */
    public static final String UPDATE_ASN_ORDER_COUNT = "updateAsnOrderCount";

    /** 合计ASN头部的总重量 */
    public static final String UPDATE_ASN_SUM_WEIGHT = "updateAsnSumWeight";

    /** 合计ASN头部的总体积 */
    public static final String UPDATE_ASN_SUM_VOLUME = "updateAsnSumVolume";

    /** 合计ASN头部的总标准度量 */
    public static final String UPDATE_ASN_SUM_METRIC = "updateAsnSumMetric";

    /** 合计ASN头部的总价值 */
    public static final String UPDATE_ASN_SUM_PRICE = "updateAsnSumPrice";

    // ------------------------Obd--------------------------------
    /** 合计OBD头部的品项数 */
    public static final String UPDATE_OBD_SKU_COUNT = "updateObdSkuCount";

    /** 合计OBD头部的总重量 */
    public static final String UPDATE_OBD_SUM_WEIGHT = "updateObdSumWeight";

    /** 合计OBD头部的总体积 */
    public static final String UPDATE_OBD_SUM_VOLUME = "updateObdSumVolume";

    /** 合计OBD头部的总标准度量 */
    public static final String UPDATE_OBD_SUM_METRIC = "updateObdSumMetric";

    /** 合计OBD头部的总价值 */
    public static final String UPDATE_OBD_SUM_PRICE = "updateObdSumPrice";

    // ------------------------Wave--------------------------------
    // /** 合计WAVE头部的订单数 */
    // public static final String UPDATE_WAVE_ORDER_COUNT =
    // "updateWaveOrderCount";
    //
    // /** 合计WAVE头部的品项数 */
    // public static final String UPDATE_WAVE_SKU_COUNT = "updateWaveSkuCount";
    //
    // /** 合计WAVE头部的总重量 */
    // public static final String UPDATE_WAVE_SUM_WEIGHT =
    // "updateWaveSumWeight";
    //
    // /** 合计WAVE头部的总体积 */
    // public static final String UPDATE_WAVE_SUM_VOLUME =
    // "updateWaveSumVolume";
    //
    // /** 合计WAVE头部的总标准度量 */
    // public static final String UPDATE_WAVE_SUM_METRIC =
    // "updateWaveSumMetric";
    //
    // /** 合计WAVE头部的总价值 */
    // public static final String UPDATE_WAVE_SUM_PRICE = "updateWaveSumPrice";

    // ------------------------Stowage--------------------------------
    /** 合计配载单头部的预订数(EA) */
    public static final String UPDATE_STOWAGE_ORDER_COUNT = "updateStowageOrderCount";

    /** 合计配载单头部的品项数 */
    public static final String UPDATE_STOWAGE_SKU_COUNT = "updateStowageSkuCount";

    /** 合计配载单头部的总重量 */
    public static final String UPDATE_STOWAGE_SUM_WEIGHT = "updateStowageSumWeight";

    /** 合计配载单头部的总体积 */
    public static final String UPDATE_STOWAGE_SUM_VOLUME = "updateStowageSumVolume";

    /** 合计配载单头部的总标准度量 */
    public static final String UPDATE_STOWAGE_SUM_METRIC = "updateStowageSumMetric";

    /** 合计配载单头部的总价值 */
    public static final String UPDATE_STOWAGE_SUM_PRICE = "updateStowageSumPrice";

    // ------------------------Count--------------------------------
    /** 合计盘点单头部的品项数 */
    public static final String UPDATE_COUNT_SKU_COUNT = "updateCountSkuCount";

    /** 合计盘点单头部的批次数 */
    public static final String UPDATE_COUNT_QUANT_COUNT = "updateCountQuantCount";

    /** 合计盘点单头部的总重量 */
    public static final String UPDATE_COUNT_SUM_WEIGHT = "updateCountSumWeight";

    /** 合计盘点单头部的总体积 */
    public static final String UPDATE_COUNT_SUM_VOLUME = "updateCountSumVolume";

    /** 合计盘点单头部的总价值 */
    public static final String UPDATE_COUNT_SUM_PRICE = "updateCountSumPrice";

    /** 合计盘点单头部的总标准度量 */
    public static final String UPDATE_COUNT_SUM_METRIC = "updateCountSumMetric";

    /** 合计盘点单头部的盘点数量 */
    public static final String UPDATE_COUNT_QTY = "updateCountQty";

    /** 合计盘点单头部的盘盈数量 */
    public static final String UPDATE_PLUS_DELTA_QTY = "updatePlusDeltaQty";

    /** 合计盘点单头部的盘亏数量 */
    public static final String UPDATE_MINUS_DELTA_QTY = "updateMinusDeltaQty";

    // ------------------------Wo--------------------------------
    // /** 合计WO头部的品项数 */
    // public static final String UPDATE_WO_SKU_COUNT = "updateWoSkuCount";
    //
    // /** 合计WO头部的总重量 */
    // public static final String UPDATE_WO_SUM_WEIGHT = "updateWoSumWeight";
    //
    // /** 合计WO头部的总体积 */
    // public static final String UPDATE_WO_SUM_VOLUME = "updateWoSumVolume";
    //
    // /** 合计WO头部的总标准度量 */
    // public static final String UPDATE_WO_SUM_METRIC = "updateWoSumMetric";
    //
    // /** 合计WO头部的总价值 */
    // public static final String UPDATE_WO_SUM_PRICE = "updateWoSumPrice";
}
