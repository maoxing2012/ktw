/**
 * (C)2014 MBP Corporation. All rights reserved.
 * 项目名称 : 华耐供应链
 * 子项目名称: 仓库系统
 * 文件名: MobileCommonManager.java
 */

package com.core.scpwms.server.service.mobile;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.core.db.server.service.BaseManager;

/**
 * <p>移动应用的共同处理</p>
 *
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *  2015年3月4日<br/>
 */
@Transactional(readOnly = false)
public interface MobileCommonManager extends BaseManager {
	@Transactional
	public List<Long> getLaborId( Long userId );
	
	@Transactional
	public Long getLaborIdFirst( Long userId );
	
	/**
	 * 取得用户有权限的货主信息
	 * @param userId
	 * @return
	 */
	@Transactional
	public List<Map<String, Object>> getOwnerList( Long userId );
	
	@Transactional
	public Map<String, Object> getSkuIdInfos(Long whId, Long ownerId, String skuBarCode );
	
	@Transactional
	public Long getSkuId(Long whId, Long ownerId, String skuBarCode );
	
	@Transactional
	public Map<String, Object> getSkuInfos(Long whId, Long ownerId, Long skuId );
	
	@Transactional
	public Map<String, Object> getSkuInfos(Long whId, Long ownerId, String qrCode );
}
