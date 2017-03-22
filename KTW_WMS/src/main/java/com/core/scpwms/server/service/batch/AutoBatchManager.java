package com.core.scpwms.server.service.batch;

import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.core.db.server.service.BaseManager;

/**
 * 库存信息导出
 * @author mousachi
 *
 */
@Transactional(readOnly = false)
public interface AutoBatchManager extends BaseManager {
	/**
	 * 日本食研的Throught商品自动检品
	 * @param ownerCode
	 */
	@Transactional
	public void checkThroughSku(String ownerCode);
}
