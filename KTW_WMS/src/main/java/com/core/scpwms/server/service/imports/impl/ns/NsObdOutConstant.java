package com.core.scpwms.server.service.imports.impl.ns;

public interface NsObdOutConstant {
	
	// 出荷出庫実績データ項目数
	public static final int OBD_LENGTH = 20;
	// 0:出荷・出庫倉庫コード
	public static final int OBD_WH_CD = 0;
	// 1:出荷・出庫区分１：出荷２：出庫３：移動
	public static final int OBD_ORDER_DIV = 1;
	// 2:データ種別０：通常１：サンプル
	public static final int OBD_DATA_DIV = 2;
	// 3:出荷・出庫日
	public static final int OBD_SHIP_DATE = 3;
	// 4:伝票ＮＯ 売上伝票ＮＯ、出庫案内ＮＯ、サンプル出荷案内ＮＯ、サンプル出庫案内ＮＯ
	public static final int OBD_ORDER_NO = 4;
	// 5:伝票行ＮＯ
	public static final int OBD_LINE_NO = 5;
	// 6:伝票行ＮＯ枝番サンプルの場合は賞味期限指定はないので１固定
	public static final int OBD_SUB_LINE_NO = 6;
	// 7:専用伝票区分０：自社１：専用２：ＣＳ ３：ＥＯＳ
	public static final int OBD_BILL_DIV = 7;
	// 8:専用伝票ＮＯ
	public static final int OBD_BILL_NO = 8;
	// 9:運送便コード
	public static final int OBD_CARRIER_CD = 9;
	// 10:送り状ＮＯ
	public static final int OBD_DELIVERY_BILL_NO = 10;
	// 11:寄託者管理ＮＯ
	public static final int OBD_WH_ORDER_NO = 11;
	// 12:寄託者管理行ＮＯ
	public static final int OBD_WH_LINE_NO = 12;
	// 13:商品コードデータ種別＝１（サンプル）の場合、製品商品コードを設定
	public static final int OBD_SKU_CD = 13;
	// 14:在庫ステータス１:通常、２:限定品、３:保留品、４:不良品
	public static final int OBD_INV_STATUS = 14;
	// 15:サンプル商品区分０：製品１：小分※データ種別＝１（サンプル）の場合、必須
	public static final int OBD_SKU_TYPE_DIV = 15;
	// 16:サンプル小分商品コード
	public static final int OBD_SMP_SKU_CD = 16;
	// 17:出荷・出庫数（基本）
	public static final int OBD_QTY = 17;
	// 18:賞味期限
	public static final int OBD_EXP_DATE = 18;
	// 19:得意先コード（入庫倉庫コード） 出荷・出庫区分＝１（出荷）の場合、得意先コードを設定 出荷・出庫区分＝２（出庫）の場合、入庫倉庫コードを設定
	public static final int OBD_CUST_CD = 19;
}
