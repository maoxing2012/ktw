package com.core.scpwms.server.enumerate;

//* == 入库部分
//* 1、	采购入库单    ----- CODE "IN_PO"
//* 2、	调拨入库单   ----- CODE "IN_SWO"
//* 3、	生产入库单   ----- CODE "IN_PRO"
//* 4、	退货入库单   ----- CODE "IN_BACK"
//* 
//* == 库内作业部分（作业时需指定作业中心、移位作业）
//* 5、	质检作业单     ----- CODE "INV_QC"
//* 6、	包装作业单     ----- CODE "INV_PKG"
//* 7、	加工作业单      ----- CODE "INV_PRO"
//* 8、	作业单                ----- CODE ""
//* 9、	盘点计划单      ----- CODE ""
//* 10、	补货作业单      ----- CODE ""
//* 
//* == 库内作业完成后移位上架作业（移位作业）
//* 		收货上架单     ----- CODE "PA_ASN"
//* 11、	质检上架单     ----- CODE "PA_QC"
//* 12、	包装上架单     ----- CODE "PA_PKG"
//* 13、	加工上架单     ----- CODE "PA_PRO"
//* 14、	作业上架单
//* 
//* == 发货单
//* 15、	销售发货单    ---- CODE "OT_"
//* 16、	调拨发货单

public interface EnuInventoryOrderType {

	public static final String PA_ASN = "PA_ASN";
	
	public static final String PA_QC = "PA_QC";
	
	public static final String PA_MOV="PA_MOV";
	
}
