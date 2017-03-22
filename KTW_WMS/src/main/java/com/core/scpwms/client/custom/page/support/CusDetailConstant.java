package com.core.scpwms.client.custom.page.support;

import com.google.gwt.user.client.rpc.IsSerializable;

/** 
 * 画面所需常量类
 * @author zhaodaqun
 * */
public class CusDetailConstant implements IsSerializable{
	
	//异常,错误
	public static final String ERROR = "error";
	//成功
	public static final String SUCCESS = "success";
	//阻塞
	public static final String OBSTRUCT = "obstruct";
	
	/**服务端信息 **/
	public static final String ASN_MANAGER = "asnManager";
	public static final String MOVE_MANAGER = "moveManager";
	public static final String TASK_MANAGER = "warehouseTaskManager";
	public static final String QUANT_MANAGER = "quantManager";
	public static final String RECEIVING_ORDER_MANAGER = "receivingOrderManager";
	public static final String DELIVERY_ORDER_MANAGER = "deliveryOrderManager";
	
	
	
	public static final String IBD_MANAGER = "inboundManager";
	
	
	public static final String METHOD_GET_SKUINFO = "getCustomSkuInfo";
	public static final String METHOD_GET_LOTPKGINFO = "getlotPkgInfo";
	
	
	public static final String METHOD_GET_INITDATA = "getInitData";
	public static final String METHOD_GET_INITREVIEVING = "getRecievingData";
	public static final String METHOD_CREATE_ASNDETAIL = "createAsnDetail";
	public static final String METHOD_UPDATE_ASNDETAIL = "updateAsnDetail";
	public static final String METHOD_GET_QUANTINFO = "getQuantInfo";
	public static final String METHOD_GET_QUANT = "getQuantInfoData";
	
	public static final String METHOD_RECIEVING = "recievingByAsnDetail";
	public static final String METHOD_QUANTEDIT = "quantEdit";
	
	public static final String METHOD_INITDATA_IBDDETAIL = "getInitData";
	public static final String METHOD_CREAT_IBDDETAIL = "creatIbdDetail";
	public static final String METHOD_UPDATE_IBDDETAIL = "updateIbdDetail";
	public static final String METHOD_CREATE_DIRECTRECEIVEDETAIL = "receiveDirect";
	public static final String METHOD_GET_MOVEPLANDETAIL = "getMovePlanDetail";
	public static final String METHOD_ADD_WT = "addWt";
	
	public static final String METHOD_CREATE_RECEIVING_ORDER_DETAIL = "createReceivingOrderDetail";
	public static final String METHOD_UPDATE_RECEIVING_ORDER_DETAIL = "updateReceivingOrderDetail";
	
	public static final String METHOD_CREATE_DELIVERY_ORDER_DETAIL = "createDeliveryOrderDetail";
	public static final String METHOD_UPDATE_DELIVERY_ORDER_DETAIL = "updateDeliveryOrderDetail";
	
	
	/**服务端信息 **/
	public static final String PROCESS_MANAGER = "processDocManager";	
	public static final String METHOD_CREATE_PRODETAIL = "addProcessDocDetail";
	public static final String METHOD_UPDATE_PRODETAIL = "editProcessDocDetail";
	
	
	
	/**服务端信息 **/
	public static final String OBD_MANAGER = "outboundDeliveryManager";	
	public static final String METHOD_CREATE_OBDDETAIL = "createObdDetail";
	public static final String METHOD_UPDATE_OBDDETAIL = "updateObdDetail";
	public static final String METHOD_GET_OBDDATA = "getInitData";
	public static final String METHOD_GET_SHIPPING = "getShippingData";
	public static final String METHOD_SHIPPING = "shippedByDetail";
	
	//message
	public static final String INIT_PAGE = "INIT_PAGE";
	public static final String INIT_LOT_UI = "INIT_LOT_UI";
	public static final String CREATE_DETAIL = "CREATE_DETAIL";
	public static final String UPDATE_DETAIL = "UPDATE_DETAIL";
	
	
	public static final String ACTION_RECIEVING = "ACTION_RECIEVING";
	public static final String ACTION_SHIPPING = "ACTION_SHIPPING";
	public static final String ACTION_ALLOCATE = "ACTION_ALLOCATE";
	public static final String ACTION_CANCEL = "ACTION_CANCEL";
	public static final String ACTION_INITDATA = "initData";
	
	
	public static final String ACTION_QUANTEDIT = "ACTION_QUANTEDIT";
	
	
	
	
}
