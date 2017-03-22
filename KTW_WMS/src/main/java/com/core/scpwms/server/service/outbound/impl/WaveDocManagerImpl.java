/**
 * (C)2014 MBP Corporation. All rights reserved.
 * 项目名称 : 华耐供应链
 * 子项目名称: 仓库系统
 * 文件名: WaveDocManagerImpl.java
 */

package com.core.scpwms.server.service.outbound.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.ComparatorChain;
import org.apache.commons.collections.comparators.ReverseComparator;

import com.core.business.exception.BusinessException;
import com.core.business.model.domain.CreateInfo;
import com.core.business.model.domain.UpdateInfo;
import com.core.business.web.security.UserHolder;
import com.core.db.server.service.pojo.DefaultBaseManager;
import com.core.scpview.client.utils.StringUtils;
import com.core.scpwms.server.constant.ExceptionConstant;
import com.core.scpwms.server.constant.NamedHqlConstant;
import com.core.scpwms.server.domain.OrderAnalyzeInfo;
import com.core.scpwms.server.domain.SkuAnalyzeInfo;
import com.core.scpwms.server.enumerate.EnuCoincidenceDegree;
import com.core.scpwms.server.enumerate.EnuOutboundDeliveryDetailStatus;
import com.core.scpwms.server.enumerate.EnuOutboundDeliveryStatus;
import com.core.scpwms.server.enumerate.EnuWarehouseOrderStatus;
import com.core.scpwms.server.enumerate.EnuWaveStatus;
import com.core.scpwms.server.enumerate.EnuWaveWorkModel;
import com.core.scpwms.server.filter.WarehouseHolder;
import com.core.scpwms.server.model.inbound.Asn;
import com.core.scpwms.server.model.outbound.OutboundDelivery;
import com.core.scpwms.server.model.outbound.OutboundDeliveryDetail;
import com.core.scpwms.server.model.outbound.WaveDoc;
import com.core.scpwms.server.model.outbound.WaveDocDetail;
import com.core.scpwms.server.model.rules.WaveRule;
import com.core.scpwms.server.model.task.WarehouseTask;
import com.core.scpwms.server.model.user.Carrier;
import com.core.scpwms.server.model.user.Owner;
import com.core.scpwms.server.model.warehouse.Bin;
import com.core.scpwms.server.model.warehouse.Warehouse;
import com.core.scpwms.server.service.common.BCManager;
import com.core.scpwms.server.service.common.BinHelper;
import com.core.scpwms.server.service.common.OrderStatusHelper;
import com.core.scpwms.server.service.outbound.WaveDocManager;
import com.core.scpwms.server.util.DateUtil;

/**
 * <p>
 * 波次管理
 * 
 * 
 * </p>
 * 
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *         2014/08/03<br/>
 */
public class WaveDocManagerImpl extends DefaultBaseManager implements WaveDocManager {

	private BCManager bcManager;
	private OrderStatusHelper orderStatusHelper;
	private BinHelper binHelper;

	public BCManager getBcManager() {
		return this.bcManager;
	}

	public void setBcManager(BCManager bcManager) {
		this.bcManager = bcManager;
	}

	public OrderStatusHelper getOrderStatusHelper() {
		return this.orderStatusHelper;
	}

	public void setOrderStatusHelper(OrderStatusHelper orderStatusHelper) {
		this.orderStatusHelper = orderStatusHelper;
	}

	public BinHelper getBinHelper() {
		return this.binHelper;
	}

	public void setBinHelper(BinHelper binHelper) {
		this.binHelper = binHelper;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.outbound.WaveDocManager#addDetail(java
	 * .lang.Long, java.util.List)
	 */
	@Override
	public void addDetail(Long waveDocId, List<Long> obdIds) {
		WaveDoc waveDoc = commonDao.load(WaveDoc.class, waveDocId);
		if (!EnuWaveStatus.STATUS100.equals(waveDoc.getStatus())) {
			throw new BusinessException(ExceptionConstant.ERROR_STATUS);
		}

		for (Long id : obdIds) {
			OutboundDelivery obd = commonDao.load(OutboundDelivery.class, id);

			if (!EnuOutboundDeliveryStatus.STATUS200.equals(obd.getStatus())) {
				throw new BusinessException(ExceptionConstant.ERROR_STATUS);
			}

			obd.setWaveDoc(waveDoc);
			orderStatusHelper.changeStatus(obd, EnuOutboundDeliveryStatus.STATUS210);// 加入波次
			commonDao.store(obd);

			for (OutboundDeliveryDetail obdDetail : obd.getDetails()) {
				obdDetail.setStatus(EnuOutboundDeliveryDetailStatus.STATUS210);// 加入波次
				commonDao.store(obdDetail);
				
				WaveDocDetail waveDetail = new WaveDocDetail();
				waveDetail.setDoc(waveDoc);
				waveDetail.setObd(obd);
				waveDetail.setObdDetail(obdDetail);
				commonDao.store(waveDetail);
			}
		}

		commonDao.store(waveDoc);
		updateTotalInfo(waveDoc.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.outbound.WaveDocManager#cancel(java.util
	 * .List)
	 */
	@Override
	public void cancel(List<Long> waveDocIds) {
		for (Long id : waveDocIds) {
			WaveDoc waveDoc = commonDao.load(WaveDoc.class, id);

			// 发行 -> 草案
			if (EnuWaveStatus.STATUS200.equals(waveDoc.getStatus())) {
				// 释放掉复核库位
				List<Long> obdIds = getObdByWaveId(waveDoc.getId());

				for (Long obdId : obdIds) {
					OutboundDelivery obd = commonDao.load(OutboundDelivery.class, obdId);
					obd.setDescBin(null);
					commonDao.store(obd);
				}

				orderStatusHelper.changeStatus(waveDoc, EnuWaveStatus.STATUS100);
				commonDao.store(waveDoc);
			}
			// 草案 -> 取消
			else if (EnuWaveStatus.STATUS100.equals(waveDoc.getStatus())) {
				// 删除明细
				String sql = " delete from wms_wave_doc_detail where wave_id = ? ";
				commonDao.executeSqlQuery(sql, new Object[]{id});
				
				// 释放掉OBD，OBD，OBD明细状态回退到发行。
				List<Long> obdIds = getObdByWaveId(waveDoc.getId());
				
				int i = 1;
				for( Long obdId : obdIds ){
					System.out.println("---" + i++);
					OutboundDelivery obd = commonDao.load(OutboundDelivery.class, obdId);
					// 出库单改为200
					obd.setWaveDoc(null);
					orderStatusHelper.changeStatus(obd, EnuOutboundDeliveryStatus.STATUS200);
					commonDao.store(obd);
					
					String sql2 = " update wms_outbound_delivery_detail set status = 200 where obd_id = ? ";
					commonDao.executeSqlQuery(sql2, new Object[]{obdId});
				}
				
				orderStatusHelper.changeStatus(waveDoc, EnuWaveStatus.STATUS000);
				commonDao.store(waveDoc);
			} else {
				throw new BusinessException(ExceptionConstant.ERROR_STATUS);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.outbound.WaveDocManager#close(java.util
	 * .List)
	 */
	@Override
	public void close(List<Long> waveDocIds) {
		for (Long id : waveDocIds) {
			WaveDoc waveDoc = commonDao.load(WaveDoc.class, id);

			// 如果该波次下的所有WT都关闭了（拣货完成），就可以关闭
			List<Long> obdIds = getObdByWaveId(id);
			for (Long obdId : obdIds) {
				OutboundDelivery obd = commonDao.load(OutboundDelivery.class, obdId);
				for (OutboundDeliveryDetail obdDetail : obd.getDetails()) {
					for (WarehouseTask wt : obdDetail.getTasks()) {
						if (!EnuWarehouseOrderStatus.CLOSE.equals(wt.getStatus())) {
							throw new BusinessException(
									ExceptionConstant.CANNOT_CLOSE_WV_AS_HAS_OPEN_WT,
									new String[] { wt.getDocSequence() });
						}
					}

				}
			}

			// 关闭波次计划
			orderStatusHelper.changeStatus(waveDoc, EnuWaveStatus.STATUS500);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.outbound.WaveDocManager#deleteDetail(java
	 * .util.List)
	 */
	@Override
	public void deleteDetail(List<Long> obdIds) {
		for (Long id : obdIds) {
			OutboundDelivery obd = commonDao.load(OutboundDelivery.class, id);
			WaveDoc waveDoc = obd.getWaveDoc();

			if (!EnuWaveStatus.STATUS100.equals(waveDoc.getStatus())) {
				throw new BusinessException(ExceptionConstant.ERROR_STATUS);
			}

			for (OutboundDeliveryDetail obdDetail : obd.getDetails()) {
				// 出库单明细的状态改为200
				obdDetail.setStatus(EnuOutboundDeliveryDetailStatus.STATUS200);
				commonDao.store(obdDetail);
				
				// 删除波次明细
				String hql = " select waveDetail.id from WaveDocDetail waveDetail"
						+ " where waveDetail.doc.id = :waveDocId "
						+ " and waveDetail.obdDetail.id = :obdDetailId ";
				List<Long> waveDetailIds = commonDao.findByQuery(hql, new String[]{"waveDocId", "obdDetailId"}, new Object[]{waveDoc.getId(), obdDetail.getId()});
				if( waveDetailIds != null && waveDetailIds.size() > 0 ){
					for( Long waveDetailId : waveDetailIds ){
						commonDao.delete(commonDao.load(WaveDocDetail.class, waveDetailId));
					}
				}
			}
			// 出库单改为200
			obd.setWaveDoc(null);
			orderStatusHelper.changeStatus(obd, EnuOutboundDeliveryStatus.STATUS200);
			commonDao.store(obd);
			commonDao.store(waveDoc);
			updateTotalInfo(waveDoc.getId());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.outbound.WaveDocManager#publish(java.util
	 * .List)
	 */
	@Override
	public void publish(List<Long> waveDocIds) {
		for (Long id : waveDocIds) {
			publish(id);
		}
	}
	
	private void publish( Long waveDocId ){
		WaveDoc waveDoc = commonDao.load(WaveDoc.class, waveDocId);

		if (!EnuWaveStatus.STATUS100.equals(waveDoc.getStatus())) {
			throw new BusinessException(ExceptionConstant.ERROR_STATUS);
		}

		// 给所有Wave下的Obd分配复核库位
		List<Long> obdIds = getObdByWaveId(waveDoc.getId());
		Bin checkBin = binHelper.getEmptyCheckBin(waveDoc.getWh().getId());
		for (Long obdId : obdIds) {
			OutboundDelivery obd = commonDao.load(OutboundDelivery.class, obdId);
			if( obd.getDescBin() == null ){
				obd.setDescBin(checkBin);
			}
		}
		
		String sql = " select string_agg( distinct carrier.code, ',' )"
				+ " from wms_outbound_delivery obd left join wms_carrier carrier on carrier.id = obd.carrier_id "
				+ " where obd.wave_doc_id = ? ";
		List<Object> carrierCodes = commonDao.findBySqlQuery(sql, new Object[]{waveDocId});
		waveDoc.setCarrierCodes("," + (String)carrierCodes.get(0) + ",");
		
		orderStatusHelper.changeStatus(waveDoc, EnuWaveStatus.STATUS200);
		commonDao.store(waveDoc);
	}

	public List<Long> getObdByWaveId(Long waveId) {
		// 给所有Wave下的Obd分配复核库位
		String hql = "select obd.id FROM OutboundDelivery obd  WHERE obd.waveDoc.id = :waveDocId ORDER BY obd.id";
		return commonDao.findByQuery(hql, "waveDocId", waveId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.outbound.WaveDocManager#save(com.core.
	 * scpwms.server.model.outbound.WaveDoc)
	 */
	@Override
	public void save(WaveDoc waveDoc) {
		if (waveDoc.isNew()) {
			Owner owner = commonDao.load(Owner.class, waveDoc.getOwner().getId());
			waveDoc.setPlant(owner.getPlant());
			waveDoc.setWh(owner.getPlant().getWh());
			waveDoc.setWaveNumber(bcManager.getWaveSeq(waveDoc.getWh().getCode()));
			waveDoc.setCreateInfo(new CreateInfo(UserHolder.getUser()));
			commonDao.store(waveDoc);
			
			orderStatusHelper.changeStatus(waveDoc, EnuWaveStatus.STATUS100);
		} else {
			// 只有草案可以修改
			if (!EnuWaveStatus.STATUS100.equals(waveDoc.getStatus())) {
				throw new BusinessException(ExceptionConstant.ERROR_STATUS);
			}

			waveDoc.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
			commonDao.store(waveDoc);
		}
	}

	/** 更新头部合计 */
	private void updateTotalInfo(Long waveId) {
		StringBuffer hql = new StringBuffer(" select count( distinct obdDetail.obd.id ), ");
		hql.append(" count( distinct obdDetail.sku.id ),");
		hql.append(" coalesce(sum( obdDetail.planQty ),0),");
		hql.append(" coalesce(sum( obdDetail.planQty * obdDetail.sku.grossWeight ),0),");
		hql.append(" coalesce(sum( obdDetail.planQty * obdDetail.sku.volume ),0),");
		hql.append(" coalesce(sum( obdDetail.planQty * obdDetail.sku.price ),0)");
		hql.append(" from OutboundDeliveryDetail obdDetail where obdDetail.obd.waveDoc.id = :waveDocId");
		
		Object[] totalInfos = (Object[]) commonDao.findByQueryUniqueResult(hql.toString(), new String[]{"waveDocId"}, new Object[]{waveId});
		if( totalInfos != null ){
			WaveDoc waveDoc = commonDao.load(WaveDoc.class, waveId);
			waveDoc.setOrderQty((Long)totalInfos[0]);
			waveDoc.setSkuCount((Long)totalInfos[1]);
			waveDoc.setPlanQty((Double)totalInfos[2]);
			waveDoc.setSumWeight((Double)totalInfos[3]);
			waveDoc.setSumVolumn((Double)totalInfos[4]);
			waveDoc.setSumPrice((Double)totalInfos[5]);
			commonDao.store(waveDoc);
		}
	}

	@Override
	public void autoCreateWave(Long ownerId, Date etd, Long orderType, Long carrierId, String desc) {
		List<String> paramNames = new ArrayList<String>();
		List<Object> params = new ArrayList<Object>();
		StringBuilder hql = new StringBuilder();
		
		// 查找满足条件的数据
		hql.append(" select obd.id from OutboundDelivery obd where obd.wh.id = :whId ");
		hql.append(" and obd.owner.id = :ownerId ");
		hql.append(" and obd.status = 200 ");
		hql.append(" and obd.orderType.id = :orderTypeId ");
		hql.append(" and to_char( obd.etd, 'YYYYMMDD' ) = :etd ") ;
		paramNames.add("whId");
		paramNames.add("ownerId");
		paramNames.add("orderTypeId");
		paramNames.add("etd");
		
		params.add(WarehouseHolder.getWarehouse().getId());
		params.add(ownerId);
		params.add(orderType);
		params.add(DateUtil.getStringDate(etd, DateUtil.PURE_DATE_FORMAT));
		
		if( carrierId != null && carrierId.longValue() > 0L ){
			hql.append(" and obd.carrier.id = :carrierId ") ;
			paramNames.add("carrierId");
			params.add(carrierId);
		}
		
		List<Long> obdIds = commonDao.findByQuery(hql.toString(), paramNames.toArray(new String[paramNames.size()]), params.toArray(new Object[params.size()]));
		
		if( obdIds == null || obdIds.size() == 0 ){
			throw new BusinessException(ExceptionConstant.CANNOT_FIND_DATA);
		}
		
		// 创建WaveDoc
		WaveDoc waveDoc = new WaveDoc();
		waveDoc.setEtd(etd);
		waveDoc.setDescription(desc);
		waveDoc.setWh(WarehouseHolder.getWarehouse());
		waveDoc.setOwner(commonDao.load(Owner.class, ownerId));
		waveDoc.setPlant(waveDoc.getOwner().getPlant());
		if( carrierId != null && carrierId.longValue() > 0L ){
			waveDoc.setCarrier(commonDao.load(Carrier.class, carrierId));
		}
		save(waveDoc);
		
		addDetail(waveDoc.getId(), obdIds);
		
		publish(waveDoc.getId());
	}

}
