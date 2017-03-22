package com.core.scpwms.server.mobile.bean;

public interface MobileConstant {
	// M：消息Message，“处理成功。”
	public static final String MSG = "M";
		
	// M1：重要消息Message，需要弹出框确认。“密码已经更新！”
	public static final String MSG_1 = "M1";
	
	// E：报错Error，“超过库存可用量，无法执行！”
	public static final String ERROR = "E";
	
	// W：警告消息Warning，“收货完成，有待质检商品，请注意及时处理！”
	public static final String WARN = "W";
	
	// C:确认信息Confirm，“本批次的生产日期早于库存的生产日期，确定要收货？”
	public static final String CONFIRM = "C";
	
	// 收货
	public static final String MENU_ASN = "menuAsn";
	
	// 上架
	public static final String MENU_PUTAWAY = "menuPutaway";
	
	// 拣货/分拣
	public static final String MENU_PICKUP = "menuPickup";
	
	// 拣货
	public static final String MENU_PICKING = "menuPicking";
	
	// 分拣
	public static final String MENU_SORTING = "menuSorting";
	
	// 装箱
	public static final String MENU_PACKAGE = "menuPackage";
	
	// 发运
	public static final String MENU_SHIPMENT = "menuShipment";
	
	// 盘点
	public static final String MENU_COUNT = "menuCount";
	
	// 其他
	public static final String MENU_OTHER = "menuOther";
	
	// 装箱2
	public static final String MENU_OBD_PACK = "menuObdPack";
	
	// 封箱
	public static final String MENU_CLOSE_PACK = "menuClosePack";
	
	// 按商品查询库存
	public static final String MENU_SKU_INV_LIST = "menuSkuInvList";
	
	// 按库位查询库存
	public static final String MENU_BIN_INV_LIST = "menuBinInvList";
	
	// 收货履历
	public static final String MENU_IBD_HIS_LIST = "menuIbdHisList";
	
	// 发货履历
	public static final String MENU_OBD_HIS_LIST = "menuObdHisList";
	
	// 库存履历
	public static final String MENU_INV_HIS_LIST = "menuInvHisList";
	
	// 点灯
	public static final String DPS_DIR_LIGHT_ON = "PP5060000m1";
	
	// 灭灯
	public static final String DPS_DIR_LIGHT_OFF = "D";
	
	public static final String DPS_DIR_ROAD_LIGHT_ON_OFF = "T";
	
	// 白色
	public static final String DPS_DIR_WRITE = "$32$22$21m4$45";
	
	// 红色
	public static final String DPS_DIR_RED = "$32$11$21m4$45";
	
	// 绿色
	public static final String DPS_DIR_GREEN = "$31$21$21m4$45";
	
	// 箱包装数的长度，正式环境是3位
	public static final int DPS_CASE_LENGTH = 3;
	
	public static final int DPS_MAX_CASE_NUM = 999;
	
	// 当箱数>999时，显示###
	public static final String DPS_CASE_ERROR = "###";
	
	// 散件数的长度
	public static final int DPS_PS_LENGTH = 3;
	
	public static final int DPS_MAX_PS_NUM = 999;
	
	// 当散件数>999时，显示###
	public static final String DPS_PS_ERROR = "###";
	
	// 电子标签的地址长度
	public static final int DPS_ADDRESS_LENGTH = 4;
	
	// PP命令头部长度
	public static final int DPS_PP_PREFIX_LENGTH = DPS_DIR_LIGHT_ON.length() + DPS_DIR_WRITE.length();
	
	// PP命令地址部长度
	public static final int DPS_PP_ADD_LENGTH = (DPS_CASE_LENGTH + DPS_PS_LENGTH + DPS_ADDRESS_LENGTH) * 80; 
	
	// D命令头部长度
	public static final int DPS_D_PREFIX_LENGTH = DPS_DIR_LIGHT_OFF.length();
	
	// D命令地址部长度
	public static final int DPS_D_ADD_LENGTH = DPS_ADDRESS_LENGTH * 200;
}
