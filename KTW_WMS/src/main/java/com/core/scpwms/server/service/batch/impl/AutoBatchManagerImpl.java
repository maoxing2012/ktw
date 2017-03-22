package com.core.scpwms.server.service.batch.impl;

import java.util.List;

import com.core.db.server.service.pojo.DefaultBaseManager;
import com.core.scpwms.server.service.batch.AutoBatchManager;
import com.core.scpwms.server.service.outbound.OutboundDeliveryManager;

public class AutoBatchManagerImpl extends DefaultBaseManager implements AutoBatchManager {
	
	private OutboundDeliveryManager outboundDeliveryManager;
	
	@Override
	public void checkThroughSku(String ownerCode) {
		// 查找状态Through品的拣货任务，并且关联的WT的状态已经是作业中
		String hqlString = "select odd.id from OutboundDeliveryDetail odd where 1=1 "
				+ " and odd.obd.owner.code = :ownerCode "
				+ " and odd.sku.stockDiv = 2 "
				+ " and odd.status = 400 "
				+ " and odd.obd.waveDoc.status = 300";
		
		List<Long> obdDetailIds = commonDao.findByQuery(hqlString, new String[]{"ownerCode"}, new Object[]{ownerCode});
		
		if( obdDetailIds != null && obdDetailIds.size() > 0 ){
			outboundDeliveryManager.checkConfirm4Through(obdDetailIds);
		}
		
	}

	public OutboundDeliveryManager getOutboundDeliveryManager() {
		return outboundDeliveryManager;
	}

	public void setOutboundDeliveryManager(
			OutboundDeliveryManager outboundDeliveryManager) {
		this.outboundDeliveryManager = outboundDeliveryManager;
	}
	
}
