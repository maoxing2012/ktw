package com.core.scpwms.server.service.sku;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.core.db.server.service.BaseManager;
import com.core.scpwms.server.model.common.SkuType;

/**
 * 
 * <p>
 * Sku类型接口定义。
 * </p>
 * 
 * @version 1.0
 * @author MBP : 温 <br/>
 *         修改履历 <br/>
 *         2013/02/5 : MBP 温: 初版创建<br/>
 */
@Transactional(readOnly = false)
public interface SkuTypeManager extends BaseManager {
	
	/**
	 * 保存Sku类型
	 * @param skuType
	 * @return
	 */
	@Transactional
	void saveSkuType(SkuType skuType);
	
	/**
	 * 生效指定的Sku类型
	 * @param ids 选择待生效的Sku类型ID集合
	 * @return
	 */
	@Transactional
	void enableSkuType(List<Long> ids);
	
	/**
	 * 失效指定的Sku类型
	 * 系统只失效没有设置的SKU类型
	 * @param ids 选择待失效的SKU类型集合
	 */
	@Transactional
	void disableSkuType(List<Long> ids);
	/**
	 * 删除sku类型
	 * @param ids
	 */
	@Transactional
	void deleteSkuType(List<Long> ids);
	
}

