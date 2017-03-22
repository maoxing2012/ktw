/**
 * (C)2014 MBP Corporation. All rights reserved.
 * 项目名称 : 华耐供应链
 * 子项目名称: 仓库系统
 * 文件名: RfAsnManager.java
 */

package com.core.scpwms.server.service.mobile;

import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.core.db.server.service.BaseManager;

/**
 * <p>手持入库</p>
 *
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *  2015年3月4日<br/>
 */
@Transactional(readOnly = false)
public interface MobileAsnManager extends BaseManager {
	@Transactional
	Map<String, Object> getAsn( Long whId, Long ownerId, String asnNumber );
	
	@Transactional
	Map<String, Object> getAsnDetail( Long asnId, Long skuId );
	
	@Transactional
	Map<String, Object> executeReceive(Long whId, Long userId, Long asnDetailId, String expDate, Double qty );
	
	@Transactional
	Map<String, Object> getLabelInfo(Long whId, Long ownerId, Long skuId, String expDate, String asnNumber );
	
	@Transactional
	Map<String, Object> getInvInfo( Long invId );
	
	@Transactional
	public Long startMultiAsn( Long whId, Long ownerId, Long userId, Long... asnIds  );
	
	@Transactional
	public Map<String, Object> getAsnIdBySku4Multi( Long whId, Long ownerId, Long userId, Long processId, Long skuId );
}
