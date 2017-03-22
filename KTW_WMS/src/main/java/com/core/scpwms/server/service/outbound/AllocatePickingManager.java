package com.core.scpwms.server.service.outbound;

import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.core.db.server.service.BaseManager;

/**
 * 客户化分配拣选库位-服务端
 */
@Transactional (readOnly=false)
public interface AllocatePickingManager extends BaseManager {
	
	@Transactional
	public Map getInitialDataByDocId(Map params);
	
	// 取得相关的库存
	@Transactional
	public Map getInvDataByDetailId(Map params);
	
	// 分配库存
	@Transactional
	public Map allocateInv(Map params);
	
	// 取消分配
	@Transactional
	public Map unAllocateInv(Map params);	
}
