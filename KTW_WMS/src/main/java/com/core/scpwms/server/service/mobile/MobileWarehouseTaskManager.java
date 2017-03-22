/**
 * (C)2014 MBP Corporation. All rights reserved.
 * 项目名称 : 华耐供应链
 * 子项目名称: 仓库系统
 * 文件名: RfAsnManager.java
 */

package com.core.scpwms.server.service.mobile;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.core.db.server.service.BaseManager;

/**
 * 拣货/复合
 * @author mousachi
 *
 */
@Transactional(readOnly = false)
public interface MobileWarehouseTaskManager extends BaseManager {
	
	@Transactional
	public List<Map<String,Object>> getCaseInfo(Long whId, Long ownerId, String caseNumber);
	
	@Transactional
	public void checkedCase( Long whId, Long ownerId, String caseNumber, Long userId );
	
	@Transactional
	public Map<String, Object> getCsPickInfo( Long whId, Long ownerId, String caseNumberFrom, String caseNumberTo );
	
	@Transactional
	public Map<String, Object> getBatchPickInfo( Long whId, Long ownerId, Long wvId, Long skuId, String binCode, String tempDiv);
	
	@Transactional
	public void executeCsPick( Long whId, Long ownerId, Long userId, String caseNumbers );
	
	@Transactional
	public Map<String, Object>  executeBatchPick( Long whId, Long ownerId, Long userId, Long waveId, Long wtsId );
	
	@Transactional
	public Map<String, Object> getPsPickInfo( Long whId, Long ownerId, String caseNumber );
	
	@Transactional
	public Long startPsPick( Long whId, Long ownerId, Long userId, String... caseNumber  );
	
	@Transactional
	public Map<String, Object> getNextPickInfo( Long whId, Long ownerId, Long processId);
	
	@Transactional
	public boolean executePsPick( Long whId, Long ownerId, Long userId, Long processId, Long binId, Long skuId, String expDate );

	@Transactional
	public Map<String, Object> getWaveDocInfo( Long whId, Long ownerId, String waveDocNumber );
}
