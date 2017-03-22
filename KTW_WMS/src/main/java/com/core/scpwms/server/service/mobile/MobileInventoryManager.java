/**
 * (C)2014 MBP Corporation. All rights reserved.
 * 项目名称 : 华耐供应链
 * 子项目名称: 仓库系统
 * 文件名: MobileInventoryManager.java
 */

package com.core.scpwms.server.service.mobile;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.core.db.server.service.BaseManager;

/**
 * <p>手持库存操作</p>
 *
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *  2015年3月5日<br/>
 */
@Transactional(readOnly = false)
public interface MobileInventoryManager extends BaseManager {
	
	@Transactional
	public List<Map<String,Object>> getInventory(Long whId, Long ownerId, String barcode, String binCode );
	
	@Transactional
	public Map<String, Object> getInvInfoByQr(Long whId, Long ownerId, String binCode, String qrCode);
	
	@Transactional
	public Map<String, Object> getInvInfoListByBinCode( Long whId, Long ownerId, String binCode );
	
	@Transactional
	public void executeInvMove( Long whId, Long userId, Long invId, String descBinCode, Double moveQty );
	
	@Transactional
	public void executeBinMove( Long whId, Long userId, Long ownerId, Long binId, String descBinCode );
}
