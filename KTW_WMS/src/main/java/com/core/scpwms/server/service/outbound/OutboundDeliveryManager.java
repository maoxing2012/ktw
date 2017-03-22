/**
 * (C)2012 MBP Corporation. All rights reserved.
 * 系统名称 : MBP-WMS
 * 文件名   : ProcessDocManager.java
 */

package com.core.scpwms.server.service.outbound;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.core.db.server.service.BaseManager;
import com.core.scpwms.server.domain.InvEditData;
import com.core.scpwms.server.model.outbound.OutboundDelivery;
import com.core.scpwms.server.model.outbound.OutboundDeliveryDetail;

/**
 * 
 * <p>
 * 出库管理
 * </p>
 * 
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *         2014/07/22<br/>
 */
@SuppressWarnings("all")
@Transactional(readOnly = false)
public interface OutboundDeliveryManager extends BaseManager {

	@Transactional
	public void save(OutboundDelivery outboundDelivery);

	@Transactional
	public void saveDetail(Long obdId, OutboundDeliveryDetail obdDetail);

	@Transactional
	public void deleteDetail(List<Long> detailIds);

	@Transactional
	public void publish(List<Long> outboundDeliveryIds);

	@Transactional
	public void cancel(List<Long> outboundDeliveryIds);
	
	@Transactional
	public void delete(List<Long> outboundDeliveryIds);

	@Transactional
	public void close(List<Long> outboundDeliveryIds);

	@Transactional
	public void shipConfirmObd(List<Long> obdIds);
	
	@Transactional
	public void updateTotalInfo(Long obdId);
	
	@Transactional
	public void checkConfirm4Through(List<Long> obdDetailIds);
}
