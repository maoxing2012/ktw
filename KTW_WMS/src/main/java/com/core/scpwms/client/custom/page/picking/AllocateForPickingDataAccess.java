package com.core.scpwms.client.custom.page.picking;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.core.scpview.client.engine.BaseDataAccess;
import com.core.scpview.client.engine.IMessagePage;
import com.core.scpview.client.utils.LocaleUtils;
import com.smartgwt.client.util.SC;

@SuppressWarnings("all")
public class AllocateForPickingDataAccess extends BaseDataAccess implements AllocateForPickingConstant{
	
	//单据头信息
	private ClientObdDoc doc;
	//单据明细信息
	private List<ClientObdDocDetail> details;
	//任务明细信息
	private List<ClientPickingTask> tasks;
	//任务明细信息
	private List<ClientPickingInventory> invs;
	
	private Long currentId = 0L;
	
	public AllocateForPickingDataAccess(IMessagePage page) {
		super(page);
	}

	public ClientObdDocDetail getDetailByKey(){
		if(details == null || details.isEmpty() || currentId == null){
			return null;
		}
		
		for(int i=0;i<details.size() ; i ++){
			ClientObdDocDetail detail = details.get(i);
			if(currentId.equals(detail.getDocDetailId())){
				return detail;
			}
		}
		
		return null;
	}

	@Override
	public void onFailure(String message, Map result) {
		
		SC.say(LocaleUtils.getTextWithoutException(result.get(ERROR).toString()));
		
		if("shippingdoc.cannout.found".equalsIgnoreCase(result.get(ERROR).toString())){
//			doc = null;
			details = new ArrayList<ClientObdDocDetail>();
			tasks = new ArrayList<ClientPickingTask>(); 
			sendMessage(MSG_RESET);
		}
	}

	@Override
	public void onSuccess(String message, Map result) {
		
		if(AllocateForPickingConstant.MSG_INIT.equalsIgnoreCase(message)){
			//页面查询
			doc = (ClientObdDoc)result.get(RESULT_DOC);
			details = (List<ClientObdDocDetail>)result.get(RESULT_DOC_DETAIL);
			tasks = (List<ClientPickingTask>)result.get(RESULT_DOC_TASKS);
			
		}else if(AllocateForPickingConstant.MSG_SEARCH_INV.equalsIgnoreCase(message)){
			//刷新库存
			invs = (List<ClientPickingInventory>)result.get(RESULT_DOC_INV);
			
		}else if(AllocateForPickingConstant.MSG_ALLOCATE.equalsIgnoreCase(message)){
			doc = (ClientObdDoc)result.get(RESULT_DOC);
			details = (List<ClientObdDocDetail>)result.get(RESULT_DOC_DETAIL);
			tasks = (List<ClientPickingTask>)result.get(RESULT_DOC_TASKS);
			invs = (List<ClientPickingInventory>)result.get(RESULT_DOC_INV);
			currentId = (Long)result.get(PARAM_DOC_DETAIL_ID);
		}else if(AllocateForPickingConstant.MSG_UNALLOCATE.equalsIgnoreCase(message)){
			doc = (ClientObdDoc)result.get(RESULT_DOC);
			details = (List<ClientObdDocDetail>)result.get(RESULT_DOC_DETAIL);
			tasks = (List<ClientPickingTask>)result.get(RESULT_DOC_TASKS);
		}
		
		
		sendMessage(message);
	}
	
	

	public void searchByDocCode(Map params){
		this.remoteCall(MSG_INIT, MANAGER, METHOD_SEARCH_DOC, params);
	}
	
	public void queryInvByDetailId(Map params){
		this.remoteCall(MSG_SEARCH_INV, MANAGER, METHOD_SEARCH_INV, params);
	}
	
	public void doAllocate(Map params){
		this.remoteCall(MSG_ALLOCATE, MANAGER, METHOD_ALLOCATE_INV, params);
	}	
	
	public void doCancelAllocate(Map params){
		this.remoteCall(MSG_UNALLOCATE, MANAGER, METHOD_UNALLOCATE_INV, params);
	}	
	
	

	@Override
	public boolean onTimeOutFailure(String message) {
		return false;
	}


	public ClientObdDoc getDoc() {
		return doc;
	}


	public void setDoc(ClientObdDoc doc) {
		this.doc = doc;
	}


	public List<ClientObdDocDetail> getDetails() {
		return details;
	}


	public void setDetails(List<ClientObdDocDetail> details) {
		this.details = details;
	}


	public List<ClientPickingTask> getTasks() {
		return tasks;
	}


	public void setTasks(List<ClientPickingTask> tasks) {
		this.tasks = tasks;
	}


	public List<ClientPickingInventory> getInvs() {
		return invs;
	}


	public void setInvs(List<ClientPickingInventory> invs) {
		this.invs = invs;
	}

	public Long getCurrentId() {
		return currentId;
	}

	public void setCurrentId(Long currentId) {
		this.currentId = currentId;
	}


}
