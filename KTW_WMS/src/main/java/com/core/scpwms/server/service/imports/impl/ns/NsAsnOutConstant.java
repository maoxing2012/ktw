package com.core.scpwms.server.service.imports.impl.ns;

public interface NsAsnOutConstant {
	
	public static final int ASN_LENGTH = 12;
	// 伝票No
	public static final int ASN_ORDER_NO = 0;
	// 行No
	public static final int ASN_LINE_NO = 1;
	// 枝番
	public static final int ASN_SUB_LINE_NO = 2;
	// 入庫日
	public static final int ASN_ETA = 3;
	// 入庫倉庫コード
	public static final int ASN_WH_CD = 4;
	// 入荷区分
	public static final int ASN_ORDER_DIV = 5;
	// 運送便コード
	public static final int ASN_TRANSPORT_BILL_NO = 6;
	// 商品コード
	public static final int ASN_SKU_CD = 7;
	//　在庫ステータス
	public static final int ASN_INV_STATUS = 8;
	// 賞味期限
	public static final int ASN_EXP_DATE = 9;
	// 単位区分
	public static final int ASN_UNIT_DIV = 10;
	// 入庫数
	public static final int ASN_BASE_QTY = 11;
}
