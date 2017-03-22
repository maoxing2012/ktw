/**
 * (C)2012 MBP Corporation. All rights reserved.
 * 系统名称 : MBP-WMS
 * 文件名   : CustomConstant.java
 */

package com.core.scpwms.client.custom.page.data;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * @description   所以自定义画面相关的常量定义在这里
 * @author         MBP:毛幸<br/>
 * @createDate    2013-3-1
 * @version        V1.0<br/>
 */
public class CustomConstant implements IsSerializable {

    // -------------------执行状态-------------------
    /** 异常,错误 */
    public static final String ERROR = "error";
    /** 成功 */
    public static final String SUCCESS = "success";
    /** 阻塞 */
    public static final String OBSTRUCT = "obstruct";

    // -------------------Service名-------------------
    /** 上架计划手工分配库位 */
    public static final String MANUAL_ALLOCATE_EDIT_MANAGER = "asnPutawayDocManager";
    
    /**质检上架库位分配　*/
    public static final String QCPUTAWAY_ALLOCATE_MANAGER = "qcputawayManager";
    
    

    // -------------------方法名-------------------
    /** 取得该ID对应的上架计划头部 */
    public static final String GET_ASN_PUTAWAY_DOC = "getAsnPutawayDoc";

    /** 取得该ID对应的上架计划明细部 */
    public static final String GET_ASN_PUTAWAY_DOC_DETAIL = "getAsnPutawayDocDetail";

    /** 追加WT */
    public static final String ADD_WT = "addWt";

    /** 重置WT明细 */
    public static final String GET_WT_LIST = "getWtList";

    /** 删除选中的WT明细 */
    public static final String REMOVE_SELECTED_WT_LIST = "removeSelectedWtList";
    
    /**qc明细  */
    public static final String QCPUTAWAY_LIST = "qcDetailList";
    
    public static final String ALLOCATE_BIN = "allocateBin";

    // -------------------Key值-------------------
    /** 上架计划ID */
    public static final String ASN_PUTAWAY_DOC_ID = "asnPutawayDocId";

    /** 上架计划号 */
    public static final String ASN_PUTAWAY_DOC_SEQUENCE = "asnPutawayDocSequence";
    
    /** 移位计划号 */
    public static final String MOVE_PLAN_SEQUENCE = "movePlanSequence";
    
    /** 预期数量 */
    public static final String MOVE_PLAN_PLAN_QTY = "movePlanPlanQty";
    
    /** 分配数量 */
    public static final String MOVE_PLAN_ALLOCATE_QTY = "movePlanAllocateQty";

    /** 移位数量 */
    public static final String MOVE_PLAN_EXECUTE_QTY = "movePlanExecuteQty";
    
    
    /** ASN号 */
    public static final String ASN_PUTAWAY_ASN_NUMBER = "asnPutawayAsnNumber";

    /** 预期数量 */
    public static final String ASN_PUTAWAY_PLAN_QTY = "asnPutawayPlanQty";

    /** 分配数量 */
    public static final String ASN_PUTAWAY_ALLOCATE_QTY = "asnPutawayAllocateQty";

    /** 移位数量 */
    public static final String ASN_PUTAWAY_EXECUTE_QTY = "asnPutawayExecuteQty";
    
    /** 移位计划明细 */
    public static final String MOVE_PLAN_DETAIL = "movePlanDetail";
    
    /** 移位计划明细ID */
    public static final String MOVE_PLAN_DETAIL_ID = "movePlanDetailId";

    /** 上架计划明细 */
    public static final String ASN_PUTAWAY_DOC_DETAIL = "asnPutawayDocDetail";

    /** 上架计划明细ID */
    public static final String ASN_PUTAWAY_DOC_DETAIL_ID = "asnPutawayDocDetailId";
    
    /** 移位计划:目标库位 */
    public static final String MOVE_PLAN_TARGET_BIN = "movePlanTargetBin";
    
    /** 移位计划:目标分配数量 */
    public static final String MOVE_PLAN_TARGET_BALLOCATE_QUANTITY = "movePlanTargetAllocateQuantity";

    /** 移位计划:目标托盘 */
    public static final String MOVE_PLAN_TARGET_PALLET = "movePlanTargetPallet";

    /** 移位计划:目标容器 */
    public static final String MOVE_PLAN_TARGET_CONTAINER = "movePlanTargetContainer";

    /** 目标库位 */
    public static final String ASN_PUTAWAY_TARGET_BIN = "asnPutawayTargetBin";

    /** 目标分配数量 */
    public static final String ASN_PUTAWAY_TARGET_BALLOCATE_QUANTITY = "asnPutawayTargetAllocateQuantity";

    /** 目标托盘 */
    public static final String ASN_PUTAWAY_TARGET_PALLET = "asnPutawayTargetPallet";

    /** 目标容器 */
    public static final String ASN_PUTAWAY_TARGET_CONTAINER = "asnPutawayTargetContainer";

    /** 整托分配 */
    public static final String ASN_PUTAWAY_WHOLE_PALLET = "asnPutawayWholePallet";

    /** WT明细 */
    public static final String ASN_PUTAWAY_WT_DETAIL = "asnPutawayWtDetail";

    /** 选中的WT明细 */
    public static final String ASN_PUTAWAY_WT_DETAIL_IDS = "asnPutawayWtDetailIds";

    /** 库位下拉单内容 */
    public static final String ASN_PUTAWAY_BIN_LIST = "asnPutawayBinList";

    // -------------------Message-----------------
    /**初始化**/
    public static final String MSG_INIT = "msgInitPage";

    /**追加WT**/
    public static final String MSG_ADD_WT = "msgAddWt";

    /**重置WT明细*/
    public static final String MSG_RESET_WT = "msgResetWt";

    /**删除选中的WT*/
    public static final String MSG_REMOVE_WT = "msgRemoveWt";
    
    public static final String QC_ALLOCATE_CONFIRM = "qcAllocateConfirm";
    
    public static final String QC_DROPDOWNLIST = "getDrpDownList";
    
    /** 移位计划ID */
    public static final String MOVE_PLAN_ID = "movePlanId";
    

    // -------------------Properties Key--------------
    /** 分配库位 */
    public static final String PRO_ALLOCATE_BIN = "allocate.bin";

    /** 目标库位 */
    public static final String PRO_TARGET_BIN = "target.bin";

    /** 分配数量 */
    public static final String PRO_ALLOCATE_QUANTITY = "allocate.quantity";

    /** 目标托盘 */
    public static final String PRO_TARGET_PALLET = "target.pallet";

    /** 目标容器 */
    public static final String PRO_TARGET_CONTAINER = "target.container";

    /** 整托分配 */
    public static final String PRO_WHOLE_PALLET = "whole.pallet";

    /** 确定分配 */
    public static final String PRO_ALLOCATE_OK = "allocate.ok";

    /** 序号 */
    public static final String PRO_TABLE_INDEX = "tableIndex";

    /** 货品代码 */
    public static final String PRO_TABLE_SKU_CODE = "tableSkuCode";

    /** 货品名称 */
    public static final String PRO_TABLE_SKU_NAME = "tableSkuName";

    /** 预分配数量(MU) */
    public static final String PRO_TABLE_PLAN_QUANTITY = "tablePlanQuantity";

    /** 预分配数量(包装单位) */
    public static final String PRO_TABLE_PLAN_QUANTITY_PACKAGE = "tablePlanQuantityPackage";

    /** 分配数量(MU) */
    public static final String PRO_TABLE_ALLOCATE_QUANTITY = "tableAllocateQuantity";

    /** 移动数量(MU) */
    public static final String PRO_TABLE_EXECUTE_QUANTITY = "tableExecuteQuantity";

    /** 库位 */
    public static final String PRO_TABLE_BIN = "tableBin";
    
    /** 目标库位 */
    public static final String PRO_TABLE_DESCBIN = "tableDescBin";

    /** 托盘号 */
    public static final String PRO_TABLE_PALLET = "tablePallet";

    /** 容器号 */
    public static final String PRO_TABLE_CONTAINER = "tableContainer";

    /** 序号 */
    public static final String PRO_TABLE_WT_INDEX = "tableWtIndex";

    /** 货品代码 */
    public static final String PRO_TABLE_WT_SKU_CODE = "tableWtSkuCode";

    /** 货品名称 */
    public static final String PRO_TABLE_WT_SKU_NAME = "tableWtSkuName";

    /** 预期数量(MU) */
    public static final String PRO_TABLE_WT_PLAN_QUANTITY = "tableWtPlanQuantity";

    /** 预期数量(包装单位) */
    public static final String PRO_TABLE_WT_PLAN_PACKAGE_QUANTITY = "tableWtPlanPackageQuantity";

    /** 上架数量(MU) */
    public static final String PRO_TABLE_WT_PUTAWAY_QUANTITY = "tableWtPutawayQuantity";

    /** 上架数量(包装单位) */
    public static final String PRO_TABLE_WT_PUTAWAY_PACKAGE_QUANTITY = "tableWtPutawayPackageQuantity";

    /** 目标库位 */
    public static final String PRO_TABLE_WT_DESC_BIN = "tableWtDescBin";

    /** 目标托盘 */
    public static final String PRO_TABLE_WT_DESC_PALLET = "tableWtDescPallet";

    /** 目标容器 */
    public static final String PRO_TABLE_WT_DESC_CONTAINER = "tableWtDescContainer";

    /** 库位代码 */
    public static final String PRO_BIN_CODE = "binCode";

    /** 库位名称 */
    public static final String PRO_BIN_NAME = "binName";
    
    
    // detail信息
    public static final String DOC_ID="docId";
    
    /** 是否创建wo */
    public static final String CREAT_WO="creatWo";
    
    
    /** 所属单据 */
    public static final String DOC_OWNER="docowner";
    
    /** 货品代码*/
    public static final String DOC_SKU_CODE="docSkuCode";
    
	/**货品名称*/
    public static final String DOC_SKU_NAME="docSkuName";
    
    /** 批次号*/
    public static final String LOT_SEQUENCE="lotSequence";
    
    /**期待包装 */
    public static final String PLAN_PACKAGE="qcplanPackage";
    
	/**预期数量MU*/
    public static final String DOC_plan_QuntityMu="docPlanQuntityMu";
    
    /**分配数量MU*/
    public static final String DOC_ASSIGN_QTYMU="docAssignQtyMu";
    
    /**QC目标库位*/
    public static final String DOC_DESC_BIN="docDescBin";
    
    /**托盘编号*/
    public static final String DOC_PALLET_SEQUEN="docPalletSequen";
    
    /** 容器编号 */
    public static final String DOC_CONTAINER_SEQ="containerSeq";
    //WT信息
    
    /** 交易号*/
    public static final String TASKSEQUENCE	="docPalletSequen";
    
    /**货品代码 */
    public static final String TASK_SKU_CODE="taskSku_code";
    
    /**货品名称 */
    public static final String TASK_SKU_NAME="taskSkuName";
    
    /**批次号 */
    public static final String TASK_SKU_LOTSEQU="taskSkuLotSequ";
    
    /** 关联单号 */
    public static final String TRACKINGSEQUENCE="trackingSequence";
    
    /**作业类型 */
    public static final String PROCESSTYPE="processType";
    
    /**状态 */
    public static final String STATUS="status";
    
    /**源库位 */
    public static final String TASK_SRCBIN="taskSrcBin";
    
    /**目标库位 */
    public static final String TASK_DESCBIN="taskDescBin";
    
    /**预期数量mu */
    public static final String TASK_PLANQTY="taskPlanQty";
    
    /**实际数量mu */
    public static final String TASK_EXECUTEQTY="taskExecuteQty";
    
    /**预期数量 */
    public static final String TASK_PLANPACKQTY="taskPlanPackQty";
    
    /**实际数量 */
    public static final String TASK_EXECUTEPACKQTY="taskExecutePackQty";
    
    /**期待包装 */
    public static final String TASK_PLANPACKAGE="taskPlanPackage";
    
    //
    public static final String DROPDOWNLIST="dropdownListMessage";
    
    public static final String BINCODE="binCode";
    public static final String BINNAME="binName";
    
    
    //-----------------------------//
    /**货主 */
    public static final String PLANT_NAME="plantName";
    
    /** 上架单ID */
    public static final String PACK_PUTAWAY_DOC_ID = "packPutawayDocId";

    /** 上架单号 */
    public static final String PACK_PUTAWAY_DOC_SEQUENCE = "packPutawayDocSequence";

    /** 预期数量 */
    public static final String PACK_PUTAWAY_PLAN_QTY = "packPutawayPlanQty";

    /** 分配数量 */
    public static final String PACK_PUTAWAY_ALLOCATE_QTY = "packPutawayAllocateQty";

    /** 移位数量 */
    public static final String PACK_PUTAWAY_EXECUTE_QTY = "packPutawayExecuteQty";
    
    
    /** outbound allocate_bin key value **/
    
    /** 出库单ID */
    public static final String OUTBOUND_PUTAWAY_DOC_ID = "obdPutawayDocId";
    
    /** 出库单号 */
    public static final String OUTBOUND_PUTAWAY_DOC_SEQUENCE = "obdPutawayDocSequence";
    
    /** 订单数量 */
    public static final String OUTBOUND_PUTAWAY_PLAN_QTY = "obdPutawayPlanQty";
    
    /** 分配数量 */
    public static final String OUTBOUND_PUTAWAY_ALLOCATE_QTY = "obdPutawayAllocateQty";
    
    /** 发货数量 */
    public static final String OUTBOUND_PUTAWAY_SHIP_QTY = "obdPutawayShipQty";
    
    

}
