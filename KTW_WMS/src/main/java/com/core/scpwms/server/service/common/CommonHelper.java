package com.core.scpwms.server.service.common;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.core.db.server.service.BaseManager;
import com.core.scpwms.server.domain.AllocatePutawayDetail;
import com.core.scpwms.server.domain.InventoryInfo;
import com.core.scpwms.server.model.common.OrderType;
import com.core.scpwms.server.model.common.Sku;
import com.core.scpwms.server.model.user.BizOrg;
import com.core.scpwms.server.model.user.Carrier;
import com.core.scpwms.server.model.user.Plant;
import com.core.scpwms.server.model.warehouse.Warehouse;

/**
 * 
 * <p>
 * 一些常用的共通方法定义在这里
 * </p>
 * 
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *         2014/07/14<br/>
 */
@Transactional(readOnly = false)
public interface CommonHelper extends BaseManager {

	/**
	 * <p>
	 * 取得订单类型
	 * </p>
	 * 
	 * @param plantId
	 * @param orderTypeCode
	 * @return
	 */
	@Transactional
	public OrderType getOrderType(String orderTypeCode);

	/**
	 * 
	 * <p>
	 * 上架单明细关联的订单明细（收货履历，加工履历，质检履历，移位明细等）
	 * </p>
	 * 
	 * @param invInfo
	 * @param putawayDocSequence
	 * @param type
	 * @return
	 */
	@Transactional
	public List<AllocatePutawayDetail> getAllocatePutawayDetail( InventoryInfo invInfo, String putawayDocSequence, String type);
	
	@Transactional
	public Warehouse getWhByCode( String whCode );
	
	@Transactional
	public Plant getPlantByCode( String plantCode );
	
	@Transactional
	public Sku getSkuByCode( String skuCode, Long plantId );
	
	@Transactional
	public Sku getSkuByCode( String skuCode, String plantCode );
	
	@Transactional
	public BizOrg getBizOrgByCode( String bizOrgCode );
	
	@Transactional
	public Carrier getCarrierByCode( String carrierCode );
}
