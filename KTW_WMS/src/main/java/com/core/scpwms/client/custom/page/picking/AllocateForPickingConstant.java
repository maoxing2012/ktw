package com.core.scpwms.client.custom.page.picking;

public interface AllocateForPickingConstant {

	//异常,错误
	public static final String ERROR = "error";
	//异常,警告
	public static final String WARN = "warn";
	//成功
	public static final String SUCCESS = "success";
	//阻塞
	public static final String OBSTRUCT = "obstruct";
	
	//服务端Manager
	public static final String MANAGER = "allocatePickingManager";
	
	
	//params key
	//单据ID
	public static final String PARAM_DOC_ID = "PARAM_DOC_ID";
	public static final String PARAM_DOC_TYPE = "PARAM_DOC_TYPE";
	
	
	//单据明细ID
	public static final String PARAM_DOC_DETAIL_ID = "PARAM_DOC_DETAIL_ID";
	public static final String PARAM_DOC_INV_ID = "PARAM_DOC_INV_ID";
	public static final String PARAM_DOC_TASK_ID = "PARAM_DOC_TASK_ID";
	public static final String PARAM_ALLOCATE_QTY = "PARAM_ALLOCATE_QTY";
	
	
	//返回值
	public static final String RESULT_DOC = "RESULT_DOC";
	public static final String RESULT_DOC_DETAIL = "RESULT_DOC_DETAIL";
	public static final String RESULT_DOC_INV = "RESULT_DOC_INV";
	public static final String RESULT_DOC_TASKS = "RESULT_DOC_TASKS";
	
	
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
	
	
}
