package com.core.scpwms.server.service.imports.impl.ns;

public interface NsObdConstant {
	public static final int COLUMN_NUM = 137;
	
	// 1:倉庫識別子
	public static final int WH_DIV = 0;
	// 2:出荷・出庫区分
	public static final int ORDER_DIV = 1;
	// 5:伝票NO
	public static final int ORDER_SEQ = 4;
	// 6:納品先コード
	public static final int SHOP_CD = 5;
	// 7:納品先名
	public static final int SHOP_NM = 6;
	// 8:店名
	public static final int SHOP_NM2 = 7;
	// 10:受取人
	public static final int SHOP_LINKMAN = 9;
	// 11:住所１
	public static final int SHOP_ADD1 = 10;
	// 12:住所２
	public static final int SHOP_ADD2 = 11;
	// 13:郵便
	public static final int SHOP_POST = 12;
	// 14:電話
	public static final int SHOP_TEL = 13;
	// 20:運送便コード
	public static final int CARRIER_CD = 19;
	// 21:運送便名
	public static final int CARRIER_NM = 20;
	// 23:受注No
	public static final int JYUTYU_NO = 22;
	// 26:出荷指定日
	public static final int SHIP_DATE = 25;
	// 27:納品指定日
	public static final int DELIVERY_DATE = 26;
	// 33:温度帯区分
	public static final int TEMP_DIV = 32;
	// 34:専用伝票区分
	public static final int DELIVERY_BILL_DIV = 33;
	// 35:伝票発行区分
	public static final int DELIVERY_BILL_DIV2 = 34;
	// 40:伝票情報
	public static final int DELIVERY_INFO = 39;
	// 44:得意先コード
	public static final int CUST_CD = 44;
	// 68:担当営業所コード
	public static final int SALES_PART = 67;
	// 80:行NO
	public static final int LINE_NO = 79;
	// 81:行No枝番
	public static final int SUB_LINE_NO = 80;
	// 83:商品コード
	public static final int SKU_CD = 82;
	// 85:商品名
	public static final int SKU_NM = 84;
	// 87:特別品ＮＯ
	public static final int SPEC_NO = 86;
	// 88:在庫ステータス
	public static final int INV_STATUS = 87;
	// 89:入力数
	public static final int INPUT_QTY = 88;
	// 90:出荷数（基本）
	public static final int BASE_QTY = 89;
	// 93:入力単位
	public static final int INPUT_UNIT = 92;
	// 94:基本単位
	public static final int BASE_UNIT_NM = 93;
	// 95:包装単位
	public static final int BL_UNIT_NM = 94;
	// 96:ケース単位
	public static final int CS_UNIT_NM = 95;
	// 97:基本チェック数
	public static final int BASE_CHECK_QTY = 96;
	// 98:基本単位あたり重量
	public static final int WEIGHT = 97;
	// 99:包装入り数
	public static final int BL_IN = 98;
	// 100:ケース入り数
	public static final int CS_IN = 99;
	// 101:包装形態名
	public static final int SPECS = 100;
	// 103:単価
	public static final int INPUT_PRICE = 102;
	// 107:税率
	public static final int TAX_RATE = 106;
	// 109:税区分
	public static final int TAX_DIV = 108;
	// 123:ＪＡＮコード
	public static final int JAN_CODE = 122;
	// 124:賞味期限逆転不可、２：不可
	public static final int CAN_REVERSAL = 123;
	// 125:賞味期限混載、２：不可
	public static final int CAN_MIX = 124;
	// 126:賞味期限（予定）
	public static final int EXP_DATE = 125;
	// 127:賞味期限（指定）
	public static final int EXP_DATE2 = 126;
	// 128:賞味期限（前回）
	public static final int EXP_DATE3 = 127;
	// 129:出荷数(トータル)
	public static final int SUM_INPUT_QTY = 128;
	// 134:出荷数(基本トータル)
	public static final int SUM_BASE_QTY = 133;

}
