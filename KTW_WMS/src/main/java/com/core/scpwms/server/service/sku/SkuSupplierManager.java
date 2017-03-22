package com.core.scpwms.server.service.sku;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.core.db.server.service.BaseManager;

/**
 * @description   商品-供应商关联设定
 * @author s1203730
 * @createDate 2013/07/23
 * @version    V1.0
 *
 */
@Transactional (readOnly=true)
public interface SkuSupplierManager extends BaseManager {
	
    /**
     * 
     * <p>保存供应商信息</p>
     *
     * @param skuId
     * @param supplierIds
     */
	@Transactional
    void saveSuppliers(Long skuId, List<Long> supplierIds);

    /**
     * 
     * <p>删除商品-供应商关联信息</p>
     *
     * @param ids
     */
	@Transactional
    void deleteSuppliers(List<Long> ids);
	
	/**
	 * 
	 * <p>设置默认供应商</p>
	 *
	 * @param skuId
	 * @param ids
	 */
	@Transactional
	void defaultSupplier(List<Long> skuId,List<Long> ids);

}
