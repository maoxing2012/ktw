package com.core.scpwms.client.custom.page.allocate;

public interface AllocateForMovingConstant {

	//异常,错误
	public static final String ERROR = "error";
	//异常,警告
	public static final String WARN = "warn";
	//成功
	public static final String SUCCESS = "success";
	//阻塞
	public static final String OBSTRUCT = "obstruct";
	
	
	
	//params key
	//单据ID
	public static final String PARAM_DOC_ID = "PARAM_DOC_ID";
	//单据明细ID
	public static final String PARAM_DOC_DETAIL_ID = "PARAM_DOC_DETAIL_ID";
	public static final String PARAM_DOC_INV_ID = "PARAM_DOC_INV_ID";
	public static final String PARAM_DOC_TASK_ID = "PARAM_DOC_TASK_ID";
	public static final String PARAM_ALLOCATE_QTY = "PARAM_ALLOCATE_QTY";
	public static final String PARAM_BIN ="initBinInf";
	public static final String PARAM_DETAIL ="detail_info";
	public static final String PARAM_ALLOCATE ="allotationInfor";
	public static final String PARAM_VALUE ="values";
	
	
	
	
	//返回值
	public static final String RESULT_DOC_DETAIL = "MOVEPLAN_DETAIL";
	
	public static final String RESULT_DOC_INV = "RESULT_DOC_INV";
	public static final String RESULT_DOC_TASKS = "FromServer_taskDetail";
	
	public static final String ACTION_RECIEVING = "ACTION_RECIEVING";
	
	
	//MessageKey
	public static final String MSG_INIT = "msgInitPage";
	public static final String MSG_SEARCH_INV = "msgSearchInv";
	public static final String MSG_ALLOCATE = "msgAllocateInv";
	public static final String MSG_UNALLOCATE = "msgUnAllocateInv";
	public static final String MSG_RESET = "msgResetPage";
	
	
	//服务端方法
	public static final String METHOD_SEARCH_DOC = "getInitialDataByDocId";
	public static final String METHOD_SEARCH_INV = "getInvDataByDetailId";
	public static final String METHOD_ALLOCATE_INV = "allocateInv";
	public static final String METHOD_UNALLOCATE_INV = "unAllocateInv";
	
	
	/**服务端信息 **/
	public static final String ASN_MANAGER = "asnManager";
	public static final String MOVE_MANAGER = "moveManager";
	public static final String TASK_MANAGER = "taskManager";
	
	public static final String QUANT_MANAGER = "quantManager";
	public static final String DETAIL_MANAGER = "BIN_DETAILINFO";
	
	
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
	
	
	
	
	
	/**服务端信息 **/
	public static final String METHOD_GET_OBDDATA = "getInitData";
	public static final String METHOD_GET_SHIPPING = "getShippingData";
	public static final String METHOD_SHIPPING = "shippedByDetail";
	
	//message
	public static final String INIT_PAGE = "INIT_PAGE";
	public static final String INIT_LOT_UI = "INIT_LOT_UI";
	public static final String CREATE_DETAIL = "CREATE_DETAIL";
	public static final String UPDATE_DETAIL = "UPDATE_DETAIL";
	
	
	public static final String ACTION_SHIPPING = "ACTION_SHIPPING";
	public static final String ACTION_ALLOCATE = "ACTION_ALLOCATE";
	public static final String ACTION_CANCEL = "ACTION_CANCEL";
	public static final String ACTION_INITDATA = "initData";
	
	
	public static final String ACTION_QUANTEDIT = "ACTION_QUANTEDIT";
	
	/**状态 **/
	public static final String STATUS_DRAFT="草案";
	public static final String STATUS_PUBLISH="发行";
	public static final String STATUS_PUBLISHED="已发布";
	public static final String STATUS_ALLOCATE="分配";
	public static final String STATUS_ALLOCATING="分配中";
	public static final String STATUS_PLAN="已计划";
	public static final String STATUS_EXECUTING="移位中";
	public static final String STATUS_WT_EXECUTING="执行中";
	public static final String STATUS_WT_OVER="执行完了";
	public static final String STATUS_OVER="移位完成";
	public static final String STATUS_CLOSE="关闭";
	public static final String STATUS_CANCEL="取消";
	
	
}
