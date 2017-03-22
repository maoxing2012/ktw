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
 * <p>手持盘点</p>
 *
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *  2015年3月4日<br/>
 */
@Transactional(readOnly = false)
public interface MobileCountManager extends BaseManager {
	@Transactional
	List<Map<String, Object>> searchCountPlan( Long whId, Long ownerId, String countPlanNumber );
	
	@Transactional
	Map<String, Object> getCountInfoByQr( Long countPlanId, String binCode, String qrCode );
	
	@Transactional
	void executeCount( Long whId, Long userId, Long ownerId, Long countPlanId, Long binId, String qrCode, Double qty );
	
	@Transactional
	void checkBin4Count(Long whId, Long userId, Long ownerId, Long countPlanId, String binCode );
}
