package com.core.scpwms.client.custom.page.support;

import java.util.LinkedHashMap;
import java.util.Map;

import com.core.scpview.client.engine.BaseDataAccess;
import com.core.scpview.client.engine.IMessagePage;
import com.core.scpview.client.utils.LocaleUtils;
import com.smartgwt.client.util.SC;

public class UploadFileDataAccess extends BaseDataAccess{
	
	private String managerName;
	
	protected LinkedHashMap<String, String> uploadRules;

	public UploadFileDataAccess(IMessagePage page,String managerName){
		super(page);
		this.managerName = managerName;
	}		
	
	public void getInitPageDate(Map params){
		remoteCall(UploadFileConstant.MSG_INIT, managerName, UploadFileConstant.INIT_PAGE_MST, params);
	}
	
	public void getInitPageDate4Asn(Map params){
		remoteCall(UploadFileConstant.MSG_INIT, managerName, UploadFileConstant.INIT_PAGE_ASN, params);
	}
	
	public void getInitPageDate4Obd(Map params){
		remoteCall(UploadFileConstant.MSG_INIT, managerName, UploadFileConstant.INIT_PAGE_OBD, params);
	}
	
	public void updateFileInfo(Map params){
		remoteCall(UploadFileConstant.MSG_UPDATE, managerName, UploadFileConstant.UPDATE_FILE_INFO_MST, params);
	}	
	
	@Override
	public void onFailure(String message, Map result) {
		SC.say(LocaleUtils.getTextWithoutException(result.get(UploadFileConstant.ERROR).toString()));
	}

	@Override
	public void onSuccess(String message, Map result) {
		if(UploadFileConstant.MSG_INIT.equalsIgnoreCase(message)){
			uploadRules = new LinkedHashMap<String, String>();
			uploadRules.putAll((Map)result.get("valueMap"));
		}
		sendMessage(message);
	}

	@Override
	public boolean onTimeOutFailure(String message) {
		return false;
	}

	public LinkedHashMap<String, String> getUploadRules() {
		return uploadRules;
	}

	public void setUploadRules(LinkedHashMap<String, String> uploadRules) {
		this.uploadRules = uploadRules;
	} 	
	
	
	
	

}
