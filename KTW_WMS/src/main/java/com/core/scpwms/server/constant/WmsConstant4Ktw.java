package com.core.scpwms.server.constant;

import com.core.scpwms.server.enumerate.EnuStoreRole;

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
public interface WmsConstant4Ktw {
	// 仕入入荷
	public static final String ASN01 = "ASN01";
	
	// 移動入荷
	public static final String ASN02 = "ASN02";
		
	// 返品入荷
	public static final String ASN03 = "ASN03";
	
	// そのた入荷
	public static final String ASN04 = "ASN04";
	
	// 標準出荷
	public static final String OBD01 = "OBD01";
	
	// 移動出荷
	public static final String OBD02 = "OBD02";
		
	// ベンダー返品出荷
	public static final String OBD03 = "OBD03";
	
	// そのた出荷
	public static final String OBD04 = "OBD04";
	
	//ロット番号,货主CD，商品CD，賞味期限（YYYYMMDD），在庫ステータスCD，入库日期（YYYYMMDD），入库单号
	public static final int QR_INX_LOT_SEQ = 0;
	public static final int QR_INX_OWNER_CD = 1;
	public static final int QR_INX_SKU_CD = 2;
	public static final int QR_INX_EXP_DATE = 3;
	public static final int QR_INX_INV_STATUS = 4;
	public static final int QR_INX_IBD_DATE = 5;
	public static final int QR_INX_ASN_NUM = 6;
	
	// 関西トラ 149
	public static final String CARRIER_KANTORA = "149";
	
	// 低温（奈良） 83						
	public static final String CARRIER_TENON = "83";
	
	// ヤマト 8						
	public static final String CARRIER_YAMATO = "8";
	
	// ヤマトクール 81						
	public static final String CARRIER_YAMATO_COOL = "81";
	
	// 福山 3						
	public static final String CARRIER_FUKUYAMA = "3";
	
	// 日本食研の荷主コード
	public static final String NS_OWNER_CD = "1000";
	
	// 可以拣货的功能区
	public static final String[] ST_ROLES = new String[]{ EnuStoreRole.R1000, EnuStoreRole.R3000, EnuStoreRole.R4040 };
	
}
