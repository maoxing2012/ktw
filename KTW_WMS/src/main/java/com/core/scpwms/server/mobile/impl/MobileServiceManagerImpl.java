package com.core.scpwms.server.mobile.impl;

import java.util.List;
import java.util.Map;

import com.core.business.exception.BusinessException;
import com.core.business.model.security.User;
import com.core.db.util.LocalizedMessage;
import com.core.scpwms.server.mobile.MobileServiceManager;
import com.core.scpwms.server.mobile.bean.MobileConstant;
import com.core.scpwms.server.mobile.bean.MobileRequest;
import com.core.scpwms.server.mobile.bean.MobileResponse;
import com.core.scpwms.server.service.mobile.MobileAsnManager;
import com.core.scpwms.server.service.mobile.MobileCommonManager;
import com.core.scpwms.server.service.mobile.MobileCountManager;
import com.core.scpwms.server.service.mobile.MobileInventoryManager;
import com.core.scpwms.server.service.mobile.MobileWarehouseTaskManager;
import com.core.scpwms.server.service.security.SecurityManager;
import com.core.scpwms.server.service.warehouse.WarehouseManager;
import com.core.scpwms.server.util.JsonHelper;

public class MobileServiceManagerImpl implements MobileServiceManager {
	private static final String CANNOT_FIND_DATA = "該当するデータが見つかりません。";
	
	private SecurityManager securityManager;
	private WarehouseManager warehouseManager;
	private MobileAsnManager asnManager;
	private MobileInventoryManager invManager;
	private MobileCountManager countManager;
	private MobileWarehouseTaskManager wtManager;
	private MobileCommonManager commonManager;
	
	public SecurityManager getSecurityManager() {
		return this.securityManager;
	}

	public void setSecurityManager(SecurityManager securityManager) {
		this.securityManager = securityManager;
	}
	
	public WarehouseManager getWarehouseManager() {
		return this.warehouseManager;
	}

	public void setWarehouseManager(WarehouseManager warehouseManager) {
		this.warehouseManager = warehouseManager;
	}
	
	public MobileAsnManager getAsnManager() {
		return this.asnManager;
	}

	public void setAsnManager(MobileAsnManager asnManager) {
		this.asnManager = asnManager;
	}
	
	public MobileInventoryManager getInvManager() {
		return this.invManager;
	}

	public void setInvManager(MobileInventoryManager invManager) {
		this.invManager = invManager;
	}
	
	public MobileCountManager getCountManager() {
		return this.countManager;
	}

	public void setCountManager(MobileCountManager countManager) {
		this.countManager = countManager;
	}
	
	public MobileWarehouseTaskManager getWtManager() {
		return this.wtManager;
	}

	public void setWtManager(MobileWarehouseTaskManager wtManager) {
		this.wtManager = wtManager;
	}
	
	public MobileCommonManager getCommonManager() {
		return commonManager;
	}

	public void setCommonManager(MobileCommonManager commonManager) {
		this.commonManager = commonManager;
	}

	@Override
	public String helloMobile(String param) {
		return "hello," + param;
	}

	@Override
	public String login(String param) {
		MobileRequest request = (MobileRequest)JsonHelper.toJavaObject(new MobileRequest(), param);
		
		String userId = (String)request.getParameters().get("loginName");
		String password = (String)request.getParameters().get("password");
		
		MobileResponse result = new MobileResponse();
		
		try{
			Object[] userInfos = securityManager.login(userId, password);
			User user = (User)userInfos[0];
			result.getResults().put("userId", user.getId());
			result.getResults().put("loginName", user.getLoginName());
			result.getResults().put("userName", user.getName());
		}catch(BusinessException be){
			result.setSeverityMsgType("E");
			result.setSeverityMsg(be.getMessage());
		}catch (Exception e) {
			result.setSeverityMsgType("E");
			result.setSeverityMsg(e.getMessage());
		}finally{
			LocalizedMessage.nullSetLocalizedMess();
		}
		return  JsonHelper.toJsonString(result, new String[]{"results"}) ;
	}

	@Override
	public String getOwnerList(String param) {
		MobileRequest requestBean = (MobileRequest) JsonHelper.toJavaObject(new MobileRequest(), param);
		
		Long userId = requestBean.getUserId();
		
		MobileResponse result = new MobileResponse();
		try {
			List<Map<String, Object>> ownerInfos = commonManager.getOwnerList(userId);
			if( ownerInfos == null ){
				result.setSeverityMsgType(MobileConstant.ERROR);
				result.setSeverityMsg(CANNOT_FIND_DATA);
			}
			else{
				result.getResults().put("ownerList", ownerInfos);
			}
		} catch (BusinessException be) {
			result.setSeverityMsgType(MobileConstant.ERROR);
			result.setSeverityMsg(be.getLocalizedMessage());
		} catch (Exception e) {
			result.setSeverityMsgType(MobileConstant.ERROR);
			result.setSeverityMsg(e.getMessage());
		} finally{
			LocalizedMessage.nullSetLocalizedMess();
		}
		return JsonHelper.toJsonString(result, new String[]{"results", "results.ownerList"});		
	}

	@Override
	public String getSkuId(String param) {
		MobileRequest requestBean = (MobileRequest) JsonHelper.toJavaObject(new MobileRequest(), param);
		
		Long whId = requestBean.getWhId();
		Long ownerId = requestBean.getOwnerId();
		String skuBarCode = (String)requestBean.getParameters().get("skuBarCode");
		
		MobileResponse result = new MobileResponse();
		try {
			Map<String, Object> skuInfos = commonManager.getSkuIdInfos(whId, ownerId, skuBarCode);
			if( skuInfos == null ){
				result.setSeverityMsgType(MobileConstant.ERROR);
				result.setSeverityMsg(CANNOT_FIND_DATA);
			}
			else{
				result.getResults().putAll(skuInfos);
			}
		} catch (BusinessException be) {
			result.setSeverityMsgType(MobileConstant.ERROR);
			result.setSeverityMsg(be.getLocalizedMessage());
		} catch (Exception e) {
			result.setSeverityMsgType(MobileConstant.ERROR);
			result.setSeverityMsg(e.getMessage());
		} finally{
			LocalizedMessage.nullSetLocalizedMess();
		}
		return JsonHelper.toJsonString(result, new String[]{"results", "results.skuList"});	
	}

	@Override
	public String getSkuInfo(String param) {
		MobileRequest requestBean = (MobileRequest) JsonHelper.toJavaObject(new MobileRequest(), param);
		
		Long whId = requestBean.getWhId();
		Long ownerId = requestBean.getOwnerId();
		//Long skuId = Long.parseLong(requestBean.getParameters().get("skuId").toString());
		String qrCode = (String)requestBean.getParameters().get("qrCode");
		
		MobileResponse result = new MobileResponse();
		try {
			Map<String, Object> skuInfos = commonManager.getSkuInfos(whId, ownerId, qrCode);
			if( skuInfos == null ){
				result.setSeverityMsgType(MobileConstant.ERROR);
				result.setSeverityMsg(CANNOT_FIND_DATA);
			}
			else{
				result.getResults().putAll(skuInfos);
			}
		} catch (BusinessException be) {
			result.setSeverityMsgType(MobileConstant.ERROR);
			result.setSeverityMsg(be.getLocalizedMessage());
		} catch (Exception e) {
			result.setSeverityMsgType(MobileConstant.ERROR);
			result.setSeverityMsg(e.getMessage());
		} finally{
			LocalizedMessage.nullSetLocalizedMess();
		}
		return JsonHelper.toJsonString(result, new String[]{"results"});	
	}

	@Override
	public String searchInventory(String param) {
		MobileRequest requestBean = (MobileRequest) JsonHelper.toJavaObject(new MobileRequest(), param);
		
		Long whId = requestBean.getWhId();
		Long ownerId = requestBean.getOwnerId();
		// Long skuId = Long.parseLong(requestBean.getParameters().get("skuId").toString());
		String binCode = (String)requestBean.getParameters().get("binCode");
		String barCode = (String)requestBean.getParameters().get("barCode");
				
		MobileResponse result = new MobileResponse();
		try {
			List<Map<String, Object>> invInfos = invManager.getInventory(whId, ownerId, barCode, binCode);
			if( invInfos == null ){
				result.setSeverityMsgType(MobileConstant.ERROR);
				result.setSeverityMsg(CANNOT_FIND_DATA);
			}
			else{
				result.getResults().put("invList", invInfos);
			}
		} catch (BusinessException be) {
			result.setSeverityMsgType(MobileConstant.ERROR);
			result.setSeverityMsg(be.getLocalizedMessage());
		} catch (Exception e) {
			result.setSeverityMsgType(MobileConstant.ERROR);
			result.setSeverityMsg(e.getMessage());
		} finally{
			LocalizedMessage.nullSetLocalizedMess();
		}
		return JsonHelper.toJsonString(result, new String[]{"results", "results.invList"});
	}

	@Override
	public String searchAsn(String param) {
		MobileRequest requestBean = (MobileRequest) JsonHelper.toJavaObject(new MobileRequest(), param);
		
		Long whId = requestBean.getWhId();
		Long ownerId = requestBean.getOwnerId();
		String asnNumber = (String)requestBean.getParameters().get("asnNumber");
		
		MobileResponse result = new MobileResponse();
		try {
			Map<String, Object> asnInfos = asnManager.getAsn(whId, ownerId, asnNumber);
			if( asnInfos == null ){
				result.setSeverityMsgType(MobileConstant.ERROR);
				result.setSeverityMsg(CANNOT_FIND_DATA);
			}
			else{
				result.getResults().putAll(asnInfos);
			}
		} catch (BusinessException be) {
			result.setSeverityMsgType(MobileConstant.ERROR);
			result.setSeverityMsg(be.getLocalizedMessage());
		} catch (Exception e) {
			result.setSeverityMsgType(MobileConstant.ERROR);
			result.setSeverityMsg(e.getMessage());
		} finally{
			LocalizedMessage.nullSetLocalizedMess();
		}
		return JsonHelper.toJsonString(result, new String[]{"results"});	
	}

	@Override
	public String getAsnDetailBySku(String param) {
		MobileRequest requestBean = (MobileRequest) JsonHelper.toJavaObject(new MobileRequest(), param);
		
		Long asnId = Long.parseLong(requestBean.getParameters().get("asnId").toString());
		Long skuId = Long.parseLong(requestBean.getParameters().get("skuId").toString());
		
		MobileResponse result = new MobileResponse();
		try {
			Map<String, Object> asnInfos = asnManager.getAsnDetail(asnId, skuId);
			if( asnInfos == null ){
				result.setSeverityMsgType(MobileConstant.ERROR);
				result.setSeverityMsg(CANNOT_FIND_DATA);
			}
			else{
				result.getResults().putAll(asnInfos);
			}
		} catch (BusinessException be) {
			result.setSeverityMsgType(MobileConstant.ERROR);
			result.setSeverityMsg(be.getLocalizedMessage());
		} catch (Exception e) {
			result.setSeverityMsgType(MobileConstant.ERROR);
			result.setSeverityMsg(e.getMessage());
		} finally{
			LocalizedMessage.nullSetLocalizedMess();
		}
		return JsonHelper.toJsonString(result, new String[]{"results"});
	}

	@Override
	public String receiveAsnDetail(String param) {
		MobileRequest requestBean = (MobileRequest) JsonHelper.toJavaObject(new MobileRequest(), param);
		
		Long asnDetailId = Long.parseLong(requestBean.getParameters().get("asnDetailId").toString());
		Double qty = Double.parseDouble(requestBean.getParameters().get("qty").toString());
		String expDate = (String)requestBean.getParameters().get("expDate");
		Long userId = requestBean.getUserId();
		Long whId = requestBean.getWhId();
		
		MobileResponse result = new MobileResponse();
		try {
			Map<String, Object> asnInfos = asnManager.executeReceive(whId, userId, asnDetailId, expDate, qty);
			if( asnInfos == null ){
				result.setSeverityMsgType(MobileConstant.ERROR);
				result.setSeverityMsg(CANNOT_FIND_DATA);
			}
			else{
				result.getResults().putAll(asnInfos);
			}
		} catch (BusinessException be) {
			result.setSeverityMsgType(MobileConstant.ERROR);
			result.setSeverityMsg(be.getLocalizedMessage());
		} catch (Exception e) {
			result.setSeverityMsgType(MobileConstant.ERROR);
			result.setSeverityMsg(e.getMessage());
		} finally{
			LocalizedMessage.nullSetLocalizedMess();
		}
		return JsonHelper.toJsonString(result, new String[]{"results", "results.label"});
	}

	@Override
	public String getInvDetailInfo(String param) {
		MobileRequest requestBean = (MobileRequest) JsonHelper.toJavaObject(new MobileRequest(), param);
		
		Long invId = Long.parseLong(requestBean.getParameters().get("invId").toString());
		
		MobileResponse result = new MobileResponse();
		try {
			Map<String, Object> invInfos = asnManager.getInvInfo(invId);
			if( invInfos == null ){
				result.setSeverityMsgType(MobileConstant.ERROR);
				result.setSeverityMsg(CANNOT_FIND_DATA);
			}
			else{
				result.getResults().putAll(invInfos);
			}
		} catch (BusinessException be) {
			result.setSeverityMsgType(MobileConstant.ERROR);
			result.setSeverityMsg(be.getLocalizedMessage());
		} catch (Exception e) {
			result.setSeverityMsgType(MobileConstant.ERROR);
			result.setSeverityMsg(e.getMessage());
		} finally{
			LocalizedMessage.nullSetLocalizedMess();
		}
		return JsonHelper.toJsonString(result, new String[]{"results", "results.label"});
	}

	@Override
	public String printAsnLabel(String param) {
		MobileRequest requestBean = (MobileRequest) JsonHelper.toJavaObject(new MobileRequest(), param);
		
		Long userId = requestBean.getUserId();
		Long whId = requestBean.getWhId();
		Long ownerId = requestBean.getOwnerId();
		Long skuId = Long.parseLong(requestBean.getParameters().get("skuId").toString());
		String asnNumber = (String)requestBean.getParameters().get("asnNumber");
		String expDate = (String)requestBean.getParameters().get("expDate");
		
		
		MobileResponse result = new MobileResponse();
		try {
			Map<String, Object> asnInfos = asnManager.getLabelInfo(whId, ownerId, skuId, expDate, asnNumber);
			if( asnInfos == null ){
				result.setSeverityMsgType(MobileConstant.ERROR);
				result.setSeverityMsg(CANNOT_FIND_DATA);
			}
			else{
				result.getResults().putAll(asnInfos);
			}
		} catch (BusinessException be) {
			result.setSeverityMsgType(MobileConstant.ERROR);
			result.setSeverityMsg(be.getLocalizedMessage());
		} catch (Exception e) {
			result.setSeverityMsgType(MobileConstant.ERROR);
			result.setSeverityMsg(e.getMessage());
		} finally{
			LocalizedMessage.nullSetLocalizedMess();
		}
		return JsonHelper.toJsonString(result, new String[]{"results", "results.label"});
	}

	@Override
	public String getInvInfoByQr(String param) {
		MobileRequest requestBean = (MobileRequest) JsonHelper.toJavaObject(new MobileRequest(), param);
		
		Long whId = requestBean.getWhId();
		Long ownerId = requestBean.getOwnerId();
		String binCode = (String)requestBean.getParameters().get("binCode");
		String qrCode = (String)requestBean.getParameters().get("qrCode");
		
		MobileResponse result = new MobileResponse();
		try {
			Map<String, Object> invInfos = invManager.getInvInfoByQr(whId, ownerId, binCode, qrCode);
			if( invInfos == null ){
				result.setSeverityMsgType(MobileConstant.ERROR);
				result.setSeverityMsg(CANNOT_FIND_DATA);
			}
			else{
				result.getResults().putAll(invInfos);
			}
		} catch (BusinessException be) {
			result.setSeverityMsgType(MobileConstant.ERROR);
			result.setSeverityMsg(be.getLocalizedMessage());
		} catch (Exception e) {
			result.setSeverityMsgType(MobileConstant.ERROR);
			result.setSeverityMsg(e.getMessage());
		} finally{
			LocalizedMessage.nullSetLocalizedMess();
		}
		return JsonHelper.toJsonString(result, new String[]{"results"});
	}

	@Override
	public String executeInvMove(String param) {
		MobileRequest requestBean = (MobileRequest) JsonHelper.toJavaObject(new MobileRequest(), param);
		
		Long whId = requestBean.getWhId();
		Long userId = requestBean.getUserId();
		
		Long invId = Long.parseLong(requestBean.getParameters().get("invId").toString());
		Double qty = Double.parseDouble(requestBean.getParameters().get("qty").toString());
		String descBinCode = (String)requestBean.getParameters().get("descBinCode");
		
		MobileResponse result = new MobileResponse();
		try {
			invManager.executeInvMove(whId, userId, invId, descBinCode, qty);
			result.setSeverityMsgType(MobileConstant.MSG_1);
			result.setSeverityMsg("棚移動処理が正常に成功しました。");
		} catch (BusinessException be) {
			result.setSeverityMsgType(MobileConstant.ERROR);
			result.setSeverityMsg(be.getLocalizedMessage());
		} catch (Exception e) {
			result.setSeverityMsgType(MobileConstant.ERROR);
			result.setSeverityMsg(e.getMessage());
		} finally{
			LocalizedMessage.nullSetLocalizedMess();
		}
		return JsonHelper.toJsonString(result, new String[]{"results"});
	}

	@Override
	public String getInvInfoByBinCode(String param) {
		MobileRequest requestBean = (MobileRequest) JsonHelper.toJavaObject(new MobileRequest(), param);
		
		Long whId = requestBean.getWhId();
		Long ownerId = requestBean.getOwnerId();
		String binCode = (String)requestBean.getParameters().get("binCode");
		
		MobileResponse result = new MobileResponse();
		try {
			Map<String, Object> invInfo = invManager.getInvInfoListByBinCode(whId, ownerId, binCode);
			if( invInfo == null ){
				result.setSeverityMsgType(MobileConstant.ERROR);
				result.setSeverityMsg(CANNOT_FIND_DATA);
			}
			else{
				result.getResults().putAll(invInfo);
			}
		} catch (BusinessException be) {
			result.setSeverityMsgType(MobileConstant.ERROR);
			result.setSeverityMsg(be.getLocalizedMessage());
		} catch (Exception e) {
			result.setSeverityMsgType(MobileConstant.ERROR);
			result.setSeverityMsg(e.getMessage());
		} finally{
			LocalizedMessage.nullSetLocalizedMess();
		}
		return JsonHelper.toJsonString(result, new String[]{"results", "results.invInfo"});
	}

	@Override
	public String executeBinMove(String param) {
		MobileRequest requestBean = (MobileRequest) JsonHelper.toJavaObject(new MobileRequest(), param);
		
		Long whId = requestBean.getWhId();
		Long ownerId = requestBean.getOwnerId();
		Long userId = requestBean.getUserId();
		
		Long binId = Long.parseLong(requestBean.getParameters().get("binId").toString());
		String descBinCode = (String)requestBean.getParameters().get("descBinCode");
		
		MobileResponse result = new MobileResponse();
		try {
			invManager.executeBinMove(whId, userId, ownerId, binId, descBinCode);
			result.setSeverityMsgType(MobileConstant.MSG_1);
			result.setSeverityMsg("棚移動処理が正常に成功しました。");
		} catch (BusinessException be) {
			result.setSeverityMsgType(MobileConstant.ERROR);
			result.setSeverityMsg(be.getLocalizedMessage());
		} catch (Exception e) {
			result.setSeverityMsgType(MobileConstant.ERROR);
			result.setSeverityMsg(e.getMessage());
		} finally{
			LocalizedMessage.nullSetLocalizedMess();
		}
		return JsonHelper.toJsonString(result, new String[]{"results"});
	}

	@Override
	public String searchCountPlan(String param) {
		MobileRequest requestBean = (MobileRequest) JsonHelper.toJavaObject(new MobileRequest(), param);
		
		Long whId = requestBean.getWhId();
		Long ownerId = requestBean.getOwnerId();
		
		String countPlanNumber = (String)requestBean.getParameters().get("countPlanNumber");
		
		MobileResponse result = new MobileResponse();
		try {
			List<Map<String, Object>> counPlan = countManager.searchCountPlan(whId, ownerId, countPlanNumber);
			if( counPlan == null ){
				result.setSeverityMsgType(MobileConstant.ERROR);
				result.setSeverityMsg(CANNOT_FIND_DATA);
			}
			else{
				result.getResults().put("countPlan", counPlan);
			}
		} catch (BusinessException be) {
			result.setSeverityMsgType(MobileConstant.ERROR);
			result.setSeverityMsg(be.getLocalizedMessage());
		} catch (Exception e) {
			result.setSeverityMsgType(MobileConstant.ERROR);
			result.setSeverityMsg(e.getMessage());
		} finally{
			LocalizedMessage.nullSetLocalizedMess();
		}
		return JsonHelper.toJsonString(result, new String[]{"results", "results.countPlan"});
	}

	@Override
	public String getCountInfoByQr(String param) {
		MobileRequest requestBean = (MobileRequest) JsonHelper.toJavaObject(new MobileRequest(), param);
		
		Long countPlanId = Long.parseLong(requestBean.getParameters().get("countPlanId").toString());
		String binCode = (String)requestBean.getParameters().get("binCode");
		String qrCode = (String)requestBean.getParameters().get("qrCode");
		
		MobileResponse result = new MobileResponse();
		try {
			Map<String, Object> countRecord = countManager.getCountInfoByQr(countPlanId, binCode, qrCode);
			result.getResults().putAll(countRecord);
		} catch (BusinessException be) {
			result.setSeverityMsgType(MobileConstant.ERROR);
			result.setSeverityMsg(be.getLocalizedMessage());
		} catch (Exception e) {
			result.setSeverityMsgType(MobileConstant.ERROR);
			result.setSeverityMsg(e.getMessage());
		} finally{
			LocalizedMessage.nullSetLocalizedMess();
		}
		return JsonHelper.toJsonString(result, new String[]{"results", "results.invStatusList"});
	}

	@Override
	public String executeCount(String param) {
		MobileRequest requestBean = (MobileRequest) JsonHelper.toJavaObject(new MobileRequest(), param);
		
		Long whId = requestBean.getWhId();
		Long ownerId = requestBean.getOwnerId();
		Long userId = requestBean.getUserId();
		
		Long countPlanId = Long.parseLong(requestBean.getParameters().get("countPlanId").toString());
		Long binId = Long.parseLong(requestBean.getParameters().get("binId").toString());
		Double qty = Double.parseDouble(requestBean.getParameters().get("qty").toString());
		String qrCode = (String)requestBean.getParameters().get("qrCode");
		
		MobileResponse result = new MobileResponse();
		try {
			countManager.executeCount(whId, userId, ownerId, countPlanId, binId, qrCode, qty);
		} catch (BusinessException be) {
			result.setSeverityMsgType(MobileConstant.ERROR);
			result.setSeverityMsg(be.getLocalizedMessage());
		} catch (Exception e) {
			result.setSeverityMsgType(MobileConstant.ERROR);
			result.setSeverityMsg(e.getMessage());
		} finally{
			LocalizedMessage.nullSetLocalizedMess();
		}
		return JsonHelper.toJsonString(result, new String[]{"results"});
	}

	@Override
	public String getCaseInfo(String param) {
		MobileRequest requestBean = (MobileRequest) JsonHelper.toJavaObject(new MobileRequest(), param);
		
		Long whId = requestBean.getWhId();
		Long ownerId = requestBean.getOwnerId();
		String caseNumber = (String)requestBean.getParameters().get("caseNumber");
		if( caseNumber != null ){
			caseNumber = caseNumber.replaceAll("a", "");
		}
		
		MobileResponse result = new MobileResponse();
		try {
			List<Map<String, Object>> skuInfos = wtManager.getCaseInfo(whId, ownerId, caseNumber);
			result.getResults().put("skuInfo", skuInfos);
		} catch (BusinessException be) {
			result.setSeverityMsgType(MobileConstant.ERROR);
			result.setSeverityMsg(be.getLocalizedMessage());
		} catch (Exception e) {
			result.setSeverityMsgType(MobileConstant.ERROR);
			result.setSeverityMsg(e.getMessage());
		} finally{
			LocalizedMessage.nullSetLocalizedMess();
		}
		return JsonHelper.toJsonString(result, new String[]{"results", "results.skuInfo"});
	}

	@Override
	public String checkedCase(String param) {
		MobileRequest requestBean = (MobileRequest) JsonHelper.toJavaObject(new MobileRequest(), param);
		
		Long whId = requestBean.getWhId();
		Long ownerId = requestBean.getOwnerId();
		Long userId = requestBean.getUserId();
		String caseNumber = (String)requestBean.getParameters().get("caseNumber");
		if( caseNumber != null ){
			caseNumber = caseNumber.replaceAll("a", "");
		}
		
		MobileResponse result = new MobileResponse();
		try {
			wtManager.checkedCase(whId, ownerId, caseNumber, userId);
		} catch (BusinessException be) {
			result.setSeverityMsgType(MobileConstant.ERROR);
			result.setSeverityMsg(be.getLocalizedMessage());
		} catch (Exception e) {
			result.setSeverityMsgType(MobileConstant.ERROR);
			result.setSeverityMsg(e.getMessage());
		} finally{
			LocalizedMessage.nullSetLocalizedMess();
		}
		return JsonHelper.toJsonString(result, new String[]{"results"});
	}

	@Override
	public String getCsPickInfo(String param) {
		MobileRequest requestBean = (MobileRequest) JsonHelper.toJavaObject(new MobileRequest(), param);
		
		Long whId = requestBean.getWhId();
		Long ownerId = requestBean.getOwnerId();
		String caseNumberFrom = (String)requestBean.getParameters().get("caseNumberFrom");
		String caseNumberTo = (String)requestBean.getParameters().get("caseNumberTo");
		
		if( caseNumberFrom != null ){
			caseNumberFrom = caseNumberFrom.replaceAll("a", "");
		}
		
		if( caseNumberTo != null ){
			caseNumberTo = caseNumberTo.replaceAll("a", "");
		}
		
		MobileResponse result = new MobileResponse();
		try {
			Map<String, Object> pickInfo = wtManager.getCsPickInfo(whId, ownerId, caseNumberFrom, caseNumberTo);
			if( pickInfo == null || pickInfo.size() == 0 ){
				result.setSeverityMsgType(MobileConstant.ERROR);
				result.setSeverityMsg(CANNOT_FIND_DATA);
			}
			else{
				result.getResults().putAll(pickInfo);
			}
		} catch (BusinessException be) {
			result.setSeverityMsgType(MobileConstant.ERROR);
			result.setSeverityMsg(be.getLocalizedMessage());
		} catch (Exception e) {
			result.setSeverityMsgType(MobileConstant.ERROR);
			result.setSeverityMsg(e.getMessage());
		} finally{
			LocalizedMessage.nullSetLocalizedMess();
		}
		return JsonHelper.toJsonString(result, new String[]{"results"});
	}

	@Override
	public String executeCsPick(String param) {
		MobileRequest requestBean = (MobileRequest) JsonHelper.toJavaObject(new MobileRequest(), param);
		
		Long whId = requestBean.getWhId();
		Long ownerId = requestBean.getOwnerId();
		Long userId = requestBean.getUserId();
		String caseNumbers = (String)requestBean.getParameters().get("caseNumbers");
		
		if( caseNumbers != null ){
			caseNumbers = caseNumbers.replaceAll("a", "");
		}
		
		MobileResponse result = new MobileResponse();
		try {
			wtManager.executeCsPick(whId, ownerId, userId, caseNumbers);
			result.setSeverityMsgType(MobileConstant.MSG_1);
			result.setSeverityMsg("ピッキング処理が正常に成功しました。");
		} catch (BusinessException be) {
			result.setSeverityMsgType(MobileConstant.ERROR);
			result.setSeverityMsg(be.getLocalizedMessage());
		} catch (Exception e) {
			result.setSeverityMsgType(MobileConstant.ERROR);
			result.setSeverityMsg(e.getMessage());
		} finally{
			LocalizedMessage.nullSetLocalizedMess();
		}
		return JsonHelper.toJsonString(result, new String[]{"results"});
	}

	@Override
	public String getPsPickInfo(String param) {
		MobileRequest requestBean = (MobileRequest) JsonHelper.toJavaObject(new MobileRequest(), param);
		
		Long whId = requestBean.getWhId();
		Long ownerId = requestBean.getOwnerId();
		String caseNumber = (String)requestBean.getParameters().get("caseNumber");
		if( caseNumber != null ){
			caseNumber = caseNumber.replaceAll("a", "");
		}
		
		MobileResponse result = new MobileResponse();
		try {
			Map<String, Object> pickInfo = wtManager.getPsPickInfo(whId, ownerId, caseNumber);
			if( pickInfo == null || pickInfo.size() == 0 ){
				result.setSeverityMsgType(MobileConstant.ERROR);
				result.setSeverityMsg(CANNOT_FIND_DATA);
			}
			else{
				result.getResults().putAll(pickInfo);
			}
		} catch (BusinessException be) {
			result.setSeverityMsgType(MobileConstant.ERROR);
			result.setSeverityMsg(be.getLocalizedMessage());
		} catch (Exception e) {
			result.setSeverityMsgType(MobileConstant.ERROR);
			result.setSeverityMsg(e.getMessage());
		} finally{
			LocalizedMessage.nullSetLocalizedMess();
		}
		return JsonHelper.toJsonString(result, new String[]{"results"});
	}

	@Override
	public String startPsPick(String param) {
		MobileRequest requestBean = (MobileRequest) JsonHelper.toJavaObject(new MobileRequest(), param);
		
		Long whId = requestBean.getWhId();
		Long ownerId = requestBean.getOwnerId();
		Long userId = requestBean.getUserId();
		String caseNumberA = (String)requestBean.getParameters().get("caseNumberA");
		String caseNumberB = (String)requestBean.getParameters().get("caseNumberB");
		String caseNumberC = (String)requestBean.getParameters().get("caseNumberC");
		String caseNumberD = (String)requestBean.getParameters().get("caseNumberD");
		String caseNumberE = (String)requestBean.getParameters().get("caseNumberE");
		String caseNumberF = (String)requestBean.getParameters().get("caseNumberF");
		String caseNumberG = (String)requestBean.getParameters().get("caseNumberG");
		String caseNumberH = (String)requestBean.getParameters().get("caseNumberH");
		String caseNumberI = (String)requestBean.getParameters().get("caseNumberI");
		
		if( caseNumberA != null ){
			caseNumberA = caseNumberA.replaceAll("a", "");
		}
		if( caseNumberB != null ){
			caseNumberB = caseNumberB.replaceAll("a", "");
		}
		if( caseNumberC != null ){
			caseNumberC = caseNumberC.replaceAll("a", "");
		}
		
		if( caseNumberD != null ){
			caseNumberD = caseNumberD.replaceAll("a", "");
		}
		if( caseNumberE != null ){
			caseNumberE = caseNumberE.replaceAll("a", "");
		}
		if( caseNumberF != null ){
			caseNumberF = caseNumberF.replaceAll("a", "");
		}
		
		if( caseNumberG != null ){
			caseNumberG = caseNumberG.replaceAll("a", "");
		}
		if( caseNumberH != null ){
			caseNumberH = caseNumberH.replaceAll("a", "");
		}
		if( caseNumberI != null ){
			caseNumberI = caseNumberI.replaceAll("a", "");
		}
		
		MobileResponse result = new MobileResponse();
		try {
			Long processId = wtManager.startPsPick(whId, ownerId, userId, 
					caseNumberA, caseNumberB, caseNumberC, caseNumberD,
					caseNumberE, caseNumberF, caseNumberG, caseNumberH, caseNumberI);
			result.getResults().put("processId", processId);
		} catch (BusinessException be) {
			result.setSeverityMsgType(MobileConstant.ERROR);
			result.setSeverityMsg(be.getLocalizedMessage());
		} catch (Exception e) {
			result.setSeverityMsgType(MobileConstant.ERROR);
			result.setSeverityMsg(e.getMessage());
		} finally{
			LocalizedMessage.nullSetLocalizedMess();
		}
		return JsonHelper.toJsonString(result, new String[]{"results"});
	}

	@Override
	public String getNextPickInfo(String param) {
		MobileRequest requestBean = (MobileRequest) JsonHelper.toJavaObject(new MobileRequest(), param);
		
		Long whId = requestBean.getWhId();
		Long ownerId = requestBean.getOwnerId();
		Long processId = Long.parseLong(requestBean.getParameters().get("processId").toString());
		
		MobileResponse result = new MobileResponse();
		try {
			Map<String, Object> pickInfo = wtManager.getNextPickInfo(whId, ownerId, processId);
			if( pickInfo == null || pickInfo.size() == 0 ){
				result.setSeverityMsgType(MobileConstant.ERROR);
				result.setSeverityMsg(CANNOT_FIND_DATA);
			}
			else{
				result.getResults().putAll(pickInfo);
			}
		} catch (BusinessException be) {
			result.setSeverityMsgType(MobileConstant.ERROR);
			result.setSeverityMsg(be.getLocalizedMessage());
		} catch (Exception e) {
			result.setSeverityMsgType(MobileConstant.ERROR);
			result.setSeverityMsg(e.getMessage());
		} finally{
			LocalizedMessage.nullSetLocalizedMess();
		}
		return JsonHelper.toJsonString(result, new String[]{"results"});
	}

	@Override
	public String executePsPick(String param) {
		MobileRequest requestBean = (MobileRequest) JsonHelper.toJavaObject(new MobileRequest(), param);
		
		Long whId = requestBean.getWhId();
		Long ownerId = requestBean.getOwnerId();
		Long userId = requestBean.getUserId();
		Long processId = Long.parseLong(requestBean.getParameters().get("processId").toString());
		Long binId = Long.parseLong(requestBean.getParameters().get("binId").toString());
		Long skuId = Long.parseLong(requestBean.getParameters().get("skuId").toString());
		String expDate = (String)requestBean.getParameters().get("expDate");
		
		MobileResponse result = new MobileResponse();
		try {
			boolean isLast = wtManager.executePsPick(whId, ownerId, userId, processId, binId, skuId, expDate);
			if( isLast ){
				result.setSeverityMsgType(MobileConstant.MSG_1);
				result.setSeverityMsg("ピッキング処理が正常に成功しました。");
			}
		} catch (BusinessException be) {
			result.setSeverityMsgType(MobileConstant.ERROR);
			result.setSeverityMsg(be.getLocalizedMessage());
		} catch (Exception e) {
			result.setSeverityMsgType(MobileConstant.ERROR);
			result.setSeverityMsg(e.getMessage());
		} finally{
			LocalizedMessage.nullSetLocalizedMess();
		}
		return JsonHelper.toJsonString(result, new String[]{"results"});
	}

	@Override
	public String searchWaveDoc(String param) {
		MobileRequest requestBean = (MobileRequest) JsonHelper.toJavaObject(new MobileRequest(), param);
		
		Long whId = requestBean.getWhId();
		Long ownerId = requestBean.getOwnerId();
		String waveNumber = (String)requestBean.getParameters().get("waveNumber");
		
		MobileResponse result = new MobileResponse();
		try {
			Map<String, Object> waveInfos = wtManager.getWaveDocInfo(whId, ownerId, waveNumber);
			if( waveInfos == null ){
				result.setSeverityMsgType(MobileConstant.ERROR);
				result.setSeverityMsg(CANNOT_FIND_DATA);
			}
			else{
				result.getResults().putAll(waveInfos);
			}
		} catch (BusinessException be) {
			result.setSeverityMsgType(MobileConstant.ERROR);
			result.setSeverityMsg(be.getLocalizedMessage());
		} catch (Exception e) {
			result.setSeverityMsgType(MobileConstant.ERROR);
			result.setSeverityMsg(e.getMessage());
		} finally{
			LocalizedMessage.nullSetLocalizedMess();
		}
		return JsonHelper.toJsonString(result, new String[]{"results"});
	}

	@Override
	public String getPickInfo4Batch(String param) {
		MobileRequest requestBean = (MobileRequest) JsonHelper.toJavaObject(new MobileRequest(), param);
		
		Long whId = requestBean.getWhId();
		Long ownerId = requestBean.getOwnerId();
		Long waveId = Long.parseLong(requestBean.getParameters().get("waveId").toString());
		Long skuId = Long.parseLong(requestBean.getParameters().get("skuId").toString());
		String tempDiv = (String)requestBean.getParameters().get("tempDiv");
		String binCode = (String)requestBean.getParameters().get("binCode");
		
		MobileResponse result = new MobileResponse();
		try {
			Map<String, Object> pickInfos = wtManager.getBatchPickInfo(whId, ownerId, waveId, skuId, binCode, tempDiv);
			if( pickInfos == null ){
				result.setSeverityMsgType(MobileConstant.ERROR);
				result.setSeverityMsg(CANNOT_FIND_DATA);
			}
			else{
				result.getResults().putAll(pickInfos);
			}
		} catch (BusinessException be) {
			result.setSeverityMsgType(MobileConstant.ERROR);
			result.setSeverityMsg(be.getLocalizedMessage());
		} catch (Exception e) {
			result.setSeverityMsgType(MobileConstant.ERROR);
			result.setSeverityMsg(e.getMessage());
		} finally{
			LocalizedMessage.nullSetLocalizedMess();
		}
		return JsonHelper.toJsonString(result, new String[]{"results"});
	}

	@Override
	public String executeBatchPick(String param) {
		MobileRequest requestBean = (MobileRequest) JsonHelper.toJavaObject(new MobileRequest(), param);
		
		Long whId = requestBean.getWhId();
		Long ownerId = requestBean.getOwnerId();
		Long userId = requestBean.getUserId();
		Long waveId = Long.parseLong(requestBean.getParameters().get("waveId").toString());
		Long wtsId = Long.parseLong(requestBean.getParameters().get("wtId").toString());
		
		MobileResponse result = new MobileResponse();
		try {
			Map<String, Object> waveInfos = wtManager.executeBatchPick(whId, ownerId, userId, waveId, wtsId);
			if( waveInfos == null ){
				result.setSeverityMsgType(MobileConstant.MSG_1);
				result.setSeverityMsg("このピッキングリスト中のタスクは全部完了しました。");
			}
			else{
				result.getResults().putAll(waveInfos);
			}
		} catch (BusinessException be) {
			result.setSeverityMsgType(MobileConstant.ERROR);
			result.setSeverityMsg(be.getLocalizedMessage());
		} catch (Exception e) {
			result.setSeverityMsgType(MobileConstant.ERROR);
			result.setSeverityMsg(e.getMessage());
		} finally{
			LocalizedMessage.nullSetLocalizedMess();
		}
		return JsonHelper.toJsonString(result, new String[]{"results"});
	}

	@Override
	public String getAsnInfo4Multi(String param) {
		MobileRequest requestBean = (MobileRequest) JsonHelper.toJavaObject(new MobileRequest(), param);
		
		Long whId = requestBean.getWhId();
		Long ownerId = requestBean.getOwnerId();
		String asnNumber = (String)requestBean.getParameters().get("asnNumber");
		
		MobileResponse result = new MobileResponse();
		try {
			Map<String, Object> asnInfos = asnManager.getAsn(whId, ownerId, asnNumber);
			if( asnInfos == null ){
				result.setSeverityMsgType(MobileConstant.ERROR);
				result.setSeverityMsg(CANNOT_FIND_DATA);
			}
			else{
				result.getResults().putAll(asnInfos);
			}
		} catch (BusinessException be) {
			result.setSeverityMsgType(MobileConstant.ERROR);
			result.setSeverityMsg(be.getLocalizedMessage());
		} catch (Exception e) {
			result.setSeverityMsgType(MobileConstant.ERROR);
			result.setSeverityMsg(e.getMessage());
		} finally{
			LocalizedMessage.nullSetLocalizedMess();
		}
		return JsonHelper.toJsonString(result, new String[]{"results"});
	}

	@Override
	public String startMultiAsn(String param) {
		MobileRequest requestBean = (MobileRequest) JsonHelper.toJavaObject(new MobileRequest(), param);
		
		Long whId = requestBean.getWhId();
		Long ownerId = requestBean.getOwnerId();
		Long userId = requestBean.getUserId();
	
		Long asnId0 = requestBean.getParameters().get("asnId0") == null || requestBean.getParameters().get("asnId0").toString().length() == 0 ? 
				null : Long.parseLong(requestBean.getParameters().get("asnId0").toString());
		Long asnId1 = requestBean.getParameters().get("asnId1") == null || requestBean.getParameters().get("asnId1").toString().length() == 0 ? 
				null : Long.parseLong(requestBean.getParameters().get("asnId1").toString());
		Long asnId2 = requestBean.getParameters().get("asnId2") == null || requestBean.getParameters().get("asnId2").toString().length() == 0 ? 
				null : Long.parseLong(requestBean.getParameters().get("asnId2").toString());
		Long asnId3 = requestBean.getParameters().get("asnId3") == null || requestBean.getParameters().get("asnId3").toString().length() == 0 ? 
				null : Long.parseLong(requestBean.getParameters().get("asnId3").toString());
		Long asnId4 = requestBean.getParameters().get("asnId4") == null || requestBean.getParameters().get("asnId4").toString().length() == 0 ? 
				null : Long.parseLong(requestBean.getParameters().get("asnId4").toString());
		Long asnId5 = requestBean.getParameters().get("asnId5") == null || requestBean.getParameters().get("asnId5").toString().length() == 0 ? 
				null : Long.parseLong(requestBean.getParameters().get("asnId5").toString());
		Long asnId6 = requestBean.getParameters().get("asnId6") == null || requestBean.getParameters().get("asnId6").toString().length() == 0 ? 
				null : Long.parseLong(requestBean.getParameters().get("asnId6").toString());
		Long asnId7 = requestBean.getParameters().get("asnId7") == null || requestBean.getParameters().get("asnId7").toString().length() == 0 ? 
				null : Long.parseLong(requestBean.getParameters().get("asnId7").toString());
		Long asnId8 = requestBean.getParameters().get("asnId8") == null || requestBean.getParameters().get("asnId8").toString().length() == 0 ? 
				null : Long.parseLong(requestBean.getParameters().get("asnId8").toString());
		Long asnId9 = requestBean.getParameters().get("asnId9") == null || requestBean.getParameters().get("asnId9").toString().length() == 0 ? 
				null : Long.parseLong(requestBean.getParameters().get("asnId9").toString());
		MobileResponse result = new MobileResponse();
		try {
			Long processId = asnManager.startMultiAsn(whId, ownerId, userId, 
							asnId0, asnId1, asnId2, asnId3, asnId4
							, asnId5, asnId6, asnId7, asnId8, asnId9);
			result.getResults().put("processId", processId);
		} catch (BusinessException be) {
			result.setSeverityMsgType(MobileConstant.ERROR);
			result.setSeverityMsg(be.getLocalizedMessage());
		} catch (Exception e) {
			result.setSeverityMsgType(MobileConstant.ERROR);
			result.setSeverityMsg(e.getMessage());
		} finally{
			LocalizedMessage.nullSetLocalizedMess();
		}
		return JsonHelper.toJsonString(result, new String[]{"results"});
	}

	@Override
	public String getAsnIdBySku4Multi(String param) {
		MobileRequest requestBean = (MobileRequest) JsonHelper.toJavaObject(new MobileRequest(), param);
		
		Long whId = requestBean.getWhId();
		Long ownerId = requestBean.getOwnerId();
		Long userId = requestBean.getUserId();
		Long processId = Long.parseLong(requestBean.getParameters().get("processId").toString());
		Long skuId = Long.parseLong(requestBean.getParameters().get("skuId").toString());
		
		MobileResponse result = new MobileResponse();
		try {
			Map<String, Object> asnInfos = asnManager.getAsnIdBySku4Multi(whId, ownerId, userId, processId, skuId);
			if( asnInfos == null ){
				result.setSeverityMsgType(MobileConstant.ERROR);
				result.setSeverityMsg(CANNOT_FIND_DATA);
			}
			else{
				result.getResults().putAll(asnInfos);
			}
		} catch (BusinessException be) {
			result.setSeverityMsgType(MobileConstant.ERROR);
			result.setSeverityMsg(be.getLocalizedMessage());
		} catch (Exception e) {
			result.setSeverityMsgType(MobileConstant.ERROR);
			result.setSeverityMsg(e.getMessage());
		} finally{
			LocalizedMessage.nullSetLocalizedMess();
		}
		return JsonHelper.toJsonString(result, new String[]{"results", "results.asnList"});
	}

	@Override
	public String checkBin4Count(String param) {
		MobileRequest requestBean = (MobileRequest) JsonHelper.toJavaObject(new MobileRequest(), param);
		
		Long whId = requestBean.getWhId();
		Long ownerId = requestBean.getOwnerId();
		Long userId = requestBean.getUserId();
		Long countPlanId = Long.parseLong(requestBean.getParameters().get("countPlanId").toString());
		String binCode = (String)requestBean.getParameters().get("binCode");
		
		MobileResponse result = new MobileResponse();
		try {
			countManager.checkBin4Count(whId, userId, ownerId, countPlanId, binCode);
		} catch (BusinessException be) {
			result.setSeverityMsgType(MobileConstant.ERROR);
			result.setSeverityMsg(be.getLocalizedMessage());
		} catch (Exception e) {
			result.setSeverityMsgType(MobileConstant.ERROR);
			result.setSeverityMsg(e.getMessage());
		} finally{
			LocalizedMessage.nullSetLocalizedMess();
		}
		return JsonHelper.toJsonString(result, new String[]{"results"});
	}

}
