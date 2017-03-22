package com.core.scpwms.server.constant;

/**
 * 
 * <p>
 * WMS常量
 * </p>
 * 
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *         2014/07/21<br/>
 */
public interface WmsConstant {

	/** 当前仓库 */
	public static final String SESSION_WAREHOUSE = "SESSION_WAREHOUSE"; // key=SESSION_WAREHOUSE

	/** 当前仓库 ID */
	public static final String SESSION_WAREHOUSE_ID = "SESSION_WAREHOUSE_ID"; // key=SESSION_WAREHOUSE_ID
	
	/** 是否要做JDE的可用量验证 */
	public static final Boolean JDE_AVAILABLE_QTY_CHECK = Boolean.TRUE;
	
	/** 收货上架单 */
	public static final String PUTAWAY_ASN = "PA_AN";

	/** 加工成品上架单 */
	public static final String PUTAWAY_PRO = "PA_PR";

	/** 移位上架单 */
	public static final String PUTAWAY_MOV = "PA_MV";

	/** 质检上级单 */
	public static final String PUTAWAY_QC = "PA_QC";

	/** 退拣上架单 */
	public static final String PUTAWAY_OBD = "PA_OB";

	/** 报废申请单 */
	public static final String INV_SCP = "IV_SC";

	/** 破损返库单 */
	public static final String PO_SCP = "PO_SC";

	/** 质检单 */
	public static final String INV_QC = "IV_QC";

	/** 移位单 */
	public static final String INV_MOV = "IV_MV";

	/** 货主转换单 */
	public static final String INV_OCH = "IV_OC";
	
	/** 批次调整单 */
	public static final String INV_LCH = "IV_LC";

	/** 盘点单 */
	public static final String CNT_DOC = "CT_DC";

	/** 加工入库单 */
	public static final String IB_PR = "IB_PR";

	/** 退货入库单 */
	public static final String IB_BK = "IB_BK";

	/** 调拨入库单 */
	public static final String IB_MV = "IB_MV";
	
	/** 非库存出库单 */
	public static final String OB_TH = "OB_TH";
	
	/** OQC单 */
	public static final String OQC = "OQC";

	/** 大分类：瓷砖 */
	public static final String LTYPE_1 = "0";

	/** 大分类：卫浴 */
	public static final String LTYPE_2 = "2";

	/** 大分类：礼品 */
	public static final String LTYPE_A = "A";

	/** 大分类：其他 */
	public static final String LTYPE_3 = "99";

	/** 瓷砖批次属性 */
	public static final String LTYPE_1_LOT = "01";

	/** 卫浴批次属性 */
	public static final String LTYPE_2_LOT = "02";

	/** 其他批次属性 */
	public static final String LTYPE_3_LOT = "99";
	
	/** DPS的WebService */
	public static final String DPS_SERVICE = "DPS_SERVICE";

}
