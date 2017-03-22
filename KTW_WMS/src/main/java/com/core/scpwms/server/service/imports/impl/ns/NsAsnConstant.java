package com.core.scpwms.server.service.imports.impl.ns;

public interface NsAsnConstant {
	public static final int COLUMN_NUM = 48;
	// 0:倉庫識別子
	public static final int WH_DIV = 0;
	// 1:処理日
	public static final int EXE_DATE = 1;
	// 2:伝票ＮＯ
	public static final int ORDER_NO = 2;
	// 3:行ＮＯ
	public static final int LINE_NO = 3;
	// 4:枝番
	public static final int SUB_LINE_NO = 4;
	// 5:出庫日
	public static final int SHIP_DATE = 5;
	// 6:入庫予定日
	public static final int ETA = 6;
	// 7:入庫予定時間
	public static final int ETA_TIME = 7;
	// 8:入庫区分
	public static final int ASN_DIV = 8;
	// 9:移動区分
	public static final int MOVE_DIV = 9;
	// 10:出庫倉庫コード
	public static final int SHIP_WH_CD = 10;
	// 11:出庫倉庫名
	public static final int SHIP_WH_NM = 11;
	// 12:運送便コード
	public static final int TRANS_BIN_CD = 12;
	// 13:運送便名
	public static final int TRANS_BIN_NM = 13;
	// 14:送り状No
	public static final int DELIVERY_NO = 14;
	// 15:商品コード
	public static final int SKU_CD = 15;
	// 16:ロットNo
	public static final int LOT_NO = 16;
	// 17:商品名
	public static final int SKU_NM = 17;
	// 18:特別品ＮＯ
	public static final int SPEC_SKU_CD = 18;
	// 19:在庫ステータス
	public static final int STATUS_DIV = 19;
	// 20:出荷数（基本）
	public static final int PLAN_QTY = 20;
	// 21:包装数
	public static final int BL_QTY = 21;
	// 22:ケース数
	public static final int CS_QTY = 22;
	// 23:予備
	public static final int YOBI_1 = 23;
	// 24:賞味期限逆転フラグ
	public static final int EXP_FLG = 24;
	// 25:入荷・入庫荷受期限外フラグ
	public static final int RECV_FLG = 25;
	// 26:仕入荷受期限外フラグ
	public static final int VENDOR_RECV_FLG = 26;
	// 27:基本単位
	public static final int PS_UNIT = 27;
	// 28:包装単位
	public static final int BL_UNIT = 28;
	// 29:ケース単位
	public static final int CS_UNIT = 29;
	// 30:基本単位当たり重量
	public static final int WEIGHT = 30;
	// 31:包装入数
	public static final int BL_IN = 31;
	// 32:ケース入数
	public static final int CS_IN = 32;
	// 33:包装形態名
	public static final int SPECS = 33;
	// 34:基本チェック数
	public static final int BASE_CHECK_QTY = 34;
	// 35:ＪＡＮＣＤ
	public static final int JAN = 35;
	// 36:パレットケース数
	public static final int PT_QTY = 36;
	// 37:基本単位ＣＤ
	public static final int PS_UNIT_CD = 37;
	// 38:包装単位ＣＤ
	public static final int BL_UNIT_CD = 38;
	// 39:ケース単位ＣＤ
	public static final int CS_UNIT_CD = 39;
	// 40:配送温度区分
	public static final int TEMP_DIV = 40;
	// 41:同梱可否フラグ
	public static final int PACK_FLG = 41;
	// 42:生産ロット
	public static final int MANU_LOT = 42;
	// 43:賞味期限日
	public static final int EXP_DATE = 43;
	// 44:出荷許容残日数
	public static final int SHIP_EXP_DAYS = 44;
	// 45:包装ＩＴＦコード
	public static final int BL_ITF = 45;
	// 46:ケースＩＴＦコード
	public static final int CS_ITF = 46;
	// 47:予備
	public static final int YOBI_2 = 47;
}
