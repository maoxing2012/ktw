package com.core.scpwms.client.custom.page.allocate.rcp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.core.scpview.client.engine.BaseDataAccess;
import com.core.scpview.client.engine.IMessagePage;
import com.core.scpview.client.utils.LocaleUtils;
import com.core.scpwms.client.custom.page.allocate.AllocateForMovingConstant;
import com.core.scpwms.client.custom.page.allocate.EnumMsg.Message;
import com.core.scpwms.client.custom.page.support.ClientAsnUpdataDetail;
import com.core.scpwms.client.custom.page.support.ClientBinDetailInfo;
import com.core.scpwms.client.custom.page.support.ClientTaskDetail;
import com.smartgwt.client.util.SC;

public class AllocationDs extends BaseDataAccess {
	public List<ClientBinDetailInfo>  cbdInfo;
	public List<ClientAsnUpdataDetail>  clientUpdata;
	public List<ClientTaskDetail> taskInfo;
	public AllocationDs(IMessagePage page) {
		super(page);
	}
	
	public void getBinDetail(Map params) {
		params.put("initBinInf", "detail_info");
		remoteCall("BIN_DETAILINFO", Message.ALLOCATION_MANAGER_NAME, Message.ALLOCATION_INITDATA,params);

	}
	public void getUpdataDetail(Map params){
		remoteCall("Query_updata_Detail", Message.ALLOCATION_MANAGER_NAME, Message.ALLOCATION_UPDATA_DETAIL, params);
	}
	
	public void cofirmAllotation(Map params){
		params.put("allotationInfor", "values");
		remoteCall("confirmAlloInfo",Message.ALLOCATION_MANAGER_NAME,Message.ALLOCATION_CONFRIM,params);
	}
	
	public void initTask(Map params){
		params.put("initInfor", "initValues");
		remoteCall("initData",Message.ALLOCATION_MANAGER_NAME,Message.ALLOCATION_INITTASK,params);
	}
	public void cancelAllovation(Map params){
		params.put("cancelMessage", "cancellAllocation");
		remoteCall("cancel", Message.ALLOCATION_MANAGER_NAME, Message.ALLOCATION_CANCEL, params);
	}
	

	

	@Override
	public void onFailure(String message, Map result) {
		
		if(result != null && result.get(AllocateForMovingConstant.ERROR) != null){
			SC.say(LocaleUtils.getTextWithoutException(result.get(AllocateForMovingConstant.ERROR).toString()));
			if("docAllocatedQtyNotEnough".equalsIgnoreCase(result.get(AllocateForMovingConstant.ERROR).toString()) || "invAllocatedQtyNotEnough".equalsIgnoreCase(result.get(AllocateForMovingConstant.ERROR).toString())){
				clientUpdata = new ArrayList<ClientAsnUpdataDetail>();
				taskInfo = new ArrayList<ClientTaskDetail>(); 
				sendMessage(AllocateForMovingConstant.MSG_RESET);
			}
		}else{
			onTimeOutFailure("xxxxxxxx");
		}
		


	}

	@SuppressWarnings("unchecked")
	@Override
	public void onSuccess(String message, Map result) {
		if(message.equalsIgnoreCase("BIN_DETAILINFO")){
			if(result.get("initBinInf").equals("detail_info")){
				cbdInfo=(List<ClientBinDetailInfo>) result.get("clientBinInfo");
			}
		}
		if(message.equalsIgnoreCase("Query_updata_Detail")){
			 clientUpdata=(List<ClientAsnUpdataDetail>) result.get("updatainfo");
			 taskInfo= (List<ClientTaskDetail>) result.get(AllocateForMovingConstant.RESULT_DOC_TASKS);
			
		}
		if(message.equalsIgnoreCase("confirmAlloInfo")||message.equalsIgnoreCase("initData")||message.equalsIgnoreCase("cancel")){
			taskInfo= (List<ClientTaskDetail>) result.get("FromServer_taskDetail");
			clientUpdata=(List<ClientAsnUpdataDetail>) result.get("updatainfo");
			cbdInfo = (List<ClientBinDetailInfo>)result.get("clientBinInfo");
		}
		sendMessage(message);
	}

	
	public List<ClientBinDetailInfo> getCbdInfo() {
		return cbdInfo;
	}

	@Override
	public boolean onTimeOutFailure(String message) {
		return false;
	}
	

	public static <K, V> HashMap<K, V> newHashMap() {
		return new HashMap<K, V>();
	}

	public List<ClientAsnUpdataDetail> getClientUpdata() {
		return clientUpdata;
	}

	public List<ClientTaskDetail> getTaskInfo() {
		return taskInfo;
	}
	

}
