package com.core.scpwms.client.custom.page.support;

public interface UploadFileConstant {
	/**执行状态 **/
	//异常,错误
	public static final String ERROR = "error";
	//异常,警告
	public static final String WARN = "warn";
	//成功
	public static final String SUCCESS = "success";
	//阻塞
	public static final String OBSTRUCT = "obstruct";
	
	public static final String MSG_INIT = "msgInitPage";	
	public static final String MSG_UPDATE = "msgUpdateFileInfo";	
	
	public static final String INIT_PAGE_MST = "initClientData4Mst";
	public static final String INIT_PAGE_ASN = "initClientData4Asn";
	public static final String INIT_PAGE_OBD = "initClientData4Obd";
	
	public static final String UPDATE_FILE_INFO_MST = "updateMasterFileInfo";
	
	public static final String MANAGER = "importFileRuleManager4Wms";
	
	public static final String ORG_NAME = "ORG_NAME";
	public static final String TARGET_NAME = "TARGET_NAME";
	public static final String RULE_SELECT = "RULE_SELECT";
}

