/**
 * (C)2014 MBP Corporation. All rights reserved.
 * 项目名称 : 华耐供应链
 * 子项目名称: 仓库系统
 * 文件名: OutboundDeliveryManagerImpl.java
 */

package com.core.scpwms.server.service.outbound.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;

import com.core.business.exception.BusinessException;
import com.core.business.model.domain.CreateInfo;
import com.core.business.model.domain.UpdateInfo;
import com.core.business.web.security.UserHolder;
import com.core.db.server.service.pojo.DefaultBaseManager;
import com.core.scpwms.server.constant.ExceptionConstant;
import com.core.scpwms.server.domain.InventoryInfo;
import com.core.scpwms.server.enumerate.EnuAsnDetailStatus;
import com.core.scpwms.server.enumerate.EnuAsnReportStatus;
import com.core.scpwms.server.enumerate.EnuInvenHisType;
import com.core.scpwms.server.enumerate.EnuOutboundDeliveryDetailStatus;
import com.core.scpwms.server.enumerate.EnuOutboundDeliveryStatus;
import com.core.scpwms.server.enumerate.EnuWarehouseTaskSingleStatus;
import com.core.scpwms.server.enumerate.EnuWaveStatus;
import com.core.scpwms.server.model.common.OrderType;
import com.core.scpwms.server.model.common.Sku;
import com.core.scpwms.server.model.inventory.Inventory;
import com.core.scpwms.server.model.outbound.OutboundDelivery;
import com.core.scpwms.server.model.outbound.OutboundDeliveryDetail;
import com.core.scpwms.server.model.outbound.WaveDoc;
import com.core.scpwms.server.model.task.WarehouseTaskSingle;
import com.core.scpwms.server.model.user.Owner;
import com.core.scpwms.server.service.common.AllocatePickingHelper;
import com.core.scpwms.server.service.common.BCManager;
import com.core.scpwms.server.service.common.BinHelper;
import com.core.scpwms.server.service.common.InfoMessageManager;
import com.core.scpwms.server.service.common.InventoryHelper;
import com.core.scpwms.server.service.common.InventoryHistoryHelper;
import com.core.scpwms.server.service.common.OrderStatusHelper;
import com.core.scpwms.server.service.inventory.InvManager;
import com.core.scpwms.server.service.inventory.QuantManager;
import com.core.scpwms.server.service.outbound.OutboundDeliveryManager;

/**
 * <p>
 * 出库管理
 * </p>
 * 
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *         2014/08/02<br/>
 */
public class OutboundDeliveryManagerImpl extends DefaultBaseManager implements OutboundDeliveryManager {

	private BCManager bcManager;
	private OrderStatusHelper orderStatusHelper;
	private InventoryHistoryHelper historyHelper;
	private InventoryHelper inventoryHelper;
	private QuantManager quantManager;
	private AllocatePickingHelper allocatePickingHelper;
	private InfoMessageManager infoMessageManager;
	private BinHelper binHelper;
	private InvManager invManager;
	
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

	public InventoryHistoryHelper getHistoryHelper() {
		return this.historyHelper;
	}

	public void setHistoryHelper(InventoryHistoryHelper historyHelper) {
		this.historyHelper = historyHelper;
	}

	public InventoryHelper getInventoryHelper() {
		return this.inventoryHelper;
	}

	public void setInventoryHelper(InventoryHelper inventoryHelper) {
		this.inventoryHelper = inventoryHelper;
	}

	public QuantManager getQuantManager() {
		return this.quantManager;
	}

	public void setQuantManager(QuantManager quantManager) {
		this.quantManager = quantManager;
	}

	public AllocatePickingHelper getAllocatePickingHelper() {
		return this.allocatePickingHelper;
	}

	public void setAllocatePickingHelper(
			AllocatePickingHelper allocatePickingHelper) {
		this.allocatePickingHelper = allocatePickingHelper;
	}

	public InfoMessageManager getInfoMessageManager() {
		return this.infoMessageManager;
	}

	public void setInfoMessageManager(InfoMessageManager infoMessageManager) {
		this.infoMessageManager = infoMessageManager;
	}
	
	public BinHelper getBinHelper() {
		return this.binHelper;
	}

	public void setBinHelper(BinHelper binHelper) {
		this.binHelper = binHelper;
	}
	
	public InvManager getInvManager() {
		return this.invManager;
	}

	public void setInvManager(InvManager invManager) {
		this.invManager = invManager;
	}
	
	@Override
	public void delete(List<Long> obdIds) {
		for (Long id : obdIds) {
			OutboundDelivery doc = commonDao.load(OutboundDelivery.class, id);
			// 取消
			if (EnuOutboundDeliveryStatus.STATUS000.equals(doc.getStatus())) {
				commonDao.delete(doc);

			} else {
				throw new BusinessException(ExceptionConstant.ERROR_STATUS);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.outbound.OutboundDeliveryManager#cancel
	 * (java.util.List)
	 */
	@Override
	public void cancel(List<Long> obdIds) {
		for (Long id : obdIds) {
			OutboundDelivery doc = commonDao.load(OutboundDelivery.class, id);
			// 发行->草案
			if (EnuOutboundDeliveryStatus.STATUS200.equals(doc.getStatus())) {
				orderStatusHelper.changeStatus(doc,
						EnuOutboundDeliveryStatus.STATUS100);
				
				// 取消备货库位
				doc.setDescBin(null);
				doc.setPublishDate(null);
				
				for (OutboundDeliveryDetail detail : doc.getDetails()) {
					detail.setStatus(EnuOutboundDeliveryDetailStatus.STATUS100);
					commonDao.store(detail);
				}
			}
			// 草案->取消
			else if (EnuOutboundDeliveryStatus.STATUS100
					.equals(doc.getStatus())) {
				orderStatusHelper.changeStatus(doc,
						EnuOutboundDeliveryStatus.STATUS000);
				
				for (OutboundDeliveryDetail detail : doc.getDetails()) {
					detail.setStatus(EnuOutboundDeliveryDetailStatus.STATUS000);
					commonDao.store(detail);
				}

			} else {
				throw new BusinessException(ExceptionConstant.ERROR_STATUS);
			}
			doc.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
			commonDao.store(doc);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.outbound.OutboundDeliveryManager#close
	 * (java.util.List)
	 */
	@Override
	public void close(List<Long> obdIds) {
		for (Long id : obdIds) {
			OutboundDelivery obd = commonDao.load(OutboundDelivery.class, id);
			// 发运完成可以关闭
			if (!EnuOutboundDeliveryStatus.STATUS910.equals(obd.getStatus()) && !EnuOutboundDeliveryStatus.STATUS900.equals(obd.getStatus())) {
				throw new BusinessException(ExceptionConstant.ERROR_STATUS);
			}
//			orderStatusHelper.changeStatus(obd, EnuOutboundDeliveryStatus.STATUS999);
			obd.setReportStatus(EnuAsnReportStatus.STATUS999);
			
			for( OutboundDeliveryDetail obdDetail : obd.getDetails() ){
//				obdDetail.setStatus(EnuOutboundDeliveryDetailStatus.STATUS999);
				obdDetail.setIsReported(Boolean.TRUE);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.outbound.OutboundDeliveryManager#deleteDetail
	 * (java.util.List)
	 */
	@Override
	public void deleteDetail(List<Long> detailIds) {
		for (Long id : detailIds) {
			OutboundDeliveryDetail detail = commonDao.load(OutboundDeliveryDetail.class, id);

			// 只有草案状态可以删除
			if (!EnuOutboundDeliveryDetailStatus.STATUS100.equals(detail.getStatus())) {
				throw new BusinessException(ExceptionConstant.ERROR_STATUS);
			}

			detail.getObd().getDetails().remove(detail);
			commonDao.delete(detail);

			updateTotalInfo(detail.getObd().getId());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.outbound.OutboundDeliveryManager#publish
	 * (java.util.List)
	 */
	@Override
	public void publish(List<Long> obdIds) {
		for (Long id : obdIds) {
			OutboundDelivery obd = commonDao.load(OutboundDelivery.class, id);

			// 只有草案状态可以发行
			if (!EnuOutboundDeliveryStatus.STATUS100.equals(obd.getStatus())) {
				throw new BusinessException(ExceptionConstant.ERROR_STATUS);
			}

			if (obd.getDetails() == null || obd.getDetails().size() == 0) {
				throw new BusinessException(ExceptionConstant.EMPTY_ORDER);
			}
			
			// 分配备货库位
			// binHelper.getCheckBin(obd);

			for (OutboundDeliveryDetail detail : obd.getDetails()) {
				detail.setStatus(EnuOutboundDeliveryDetailStatus.STATUS200);
				commonDao.store(detail);
			}

			orderStatusHelper.changeStatus(obd, EnuOutboundDeliveryStatus.STATUS200);
			
			obd.setPublishDate(new Date());
			commonDao.store(obd);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.outbound.OutboundDeliveryManager#save(
	 * com.core.scpwms.server.model.process.ProcessDoc)
	 */
	@Override
	public void save(OutboundDelivery obd) {
		if (obd.isNew()) {
			Owner owner = commonDao.load(Owner.class, obd.getOwner().getId());
			obd.setPlant(owner.getPlant());
			obd.setWh(owner.getPlant().getWh());
			OrderType orderType = load(OrderType.class, obd.getOrderType().getId());
			obd.setObdNumber(bcManager.getOrderSequence(orderType, obd.getWh().getId()));
			obd.setCreateInfo(new CreateInfo(UserHolder.getUser()));
			obd.setTransactionDate(new Date());
			commonDao.store(obd);
			orderStatusHelper.changeStatus(obd, EnuOutboundDeliveryStatus.STATUS100);
		} else {
			// 只有草案可以修改
			if (!EnuOutboundDeliveryStatus.STATUS100.equals(obd.getStatus())) {
				throw new BusinessException(ExceptionConstant.ERROR_STATUS);
			}

			obd.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
			commonDao.store(obd);
		}
	}

	@Override
	public void saveDetail(Long obdId, OutboundDeliveryDetail obdDetail) {
		OutboundDelivery obd = commonDao.load(OutboundDelivery.class, obdId);
		if( !EnuOutboundDeliveryStatus.STATUS100.equals(obd.getStatus()) )
			throw new BusinessException(ExceptionConstant.ERROR_STATUS);
		
		if( obdDetail.isNew() ){
			obdDetail.setLineNo(queryMaxLineNo(obdId));
			obdDetail.setStatus(EnuAsnDetailStatus.STATUS100);
			obdDetail.setObd(obd);
		}
		
		Sku sku = commonDao.load(Sku.class, obdDetail.getSku().getId());
		obdDetail.setStockDiv(sku.getStockDiv());
		
		commonDao.store(obdDetail);
		updateTotalInfo(obdId);
		
	}
	
	private Double queryMaxLineNo(Long obdId) {
		String hql = " select max(lineNo) from OutboundDeliveryDetail obdDetail where obdDetail.obd.id = :obdId ";
		Double maxLine = (Double) commonDao.findByQueryUniqueResult(hql, new String[]{"obdId"}, new Object[]{obdId});
		
		if( maxLine == null )
			return 1D;
		else 
			return maxLine + 1D;
	}
	
	/** 更新头部合计 */
	public void updateTotalInfo(Long obdId) {
		StringBuffer hql = new StringBuffer("select count( distinct obdDetail.sku.id ), ");
		hql.append(" coalesce(sum( obdDetail.planQty * obdDetail.sku.grossWeight ),0),");
		hql.append(" coalesce(sum( obdDetail.planQty * obdDetail.sku.volume ),0),");
		hql.append(" coalesce(sum( obdDetail.planQty * obdDetail.sku.price ),0),");
		hql.append(" coalesce(sum( obdDetail.planQty ),0)");
		hql.append(" from OutboundDeliveryDetail obdDetail where obdDetail.obd.id = :obdId ");
		
		Object[] totalInfos = (Object[]) commonDao.findByQueryUniqueResult(hql.toString(), new String[]{"obdId"}, new Object[]{obdId});
		if( totalInfos != null ){
			OutboundDelivery obd = commonDao.load(OutboundDelivery.class, obdId);
			obd.setSkuCount((Long)totalInfos[0]);
			obd.setSumWeight((Double)totalInfos[1]);
			obd.setSumVolume((Double)totalInfos[2]);
			obd.setSumPrice((Double)totalInfos[3]);
			obd.setPlanQty((Double)totalInfos[4]);
			commonDao.store(obd);
		}
	}

	@Override
	public void shipConfirmObd(List<Long> obdIds) {
		for( Long obdId : obdIds ){
			// 复合完成才能发运确认
			OutboundDelivery obd = commonDao.load(OutboundDelivery.class, obdId);
			if( !EnuOutboundDeliveryStatus.STATUS850.equals(obd.getStatus()) ){
				throw new BusinessException(ExceptionConstant.ERROR_STATUS);
			}
			
			// スルー品出荷
			String hql0 = " select obdDetail.id from OutboundDeliveryDetail obdDetail where obdDetail.sku.stockDiv <> 1 and obdDetail.obd.id = :obdId";
			List<Long> throughObdDetailIds = commonDao.findByQuery(hql0, "obdId", obdId);
			if( throughObdDetailIds != null && throughObdDetailIds.size() > 0 ){
				for( Long throughObdDetailId : throughObdDetailIds ){
					OutboundDeliveryDetail obdDetail = commonDao.load(OutboundDeliveryDetail.class, throughObdDetailId);
					if( obdDetail.getPackedUnExecuteQty() > 0 ){
						obdDetail.addExecuteQty(obdDetail.getPackedUnExecuteQty());
					}
				}
			}
			
			String hql = " select wts.id from WarehouseTaskSingle wts where wts.wt.obdDetail.obd.id = :obdId and wts.status = 200 order by wts.wt.obdDetail.id";
			List<Long> wtsIds = commonDao.findByQuery(hql, "obdId", obdId);
			
			if( wtsIds != null && wtsIds.size() > 0 ){
				for( Long wtsId : wtsIds ){
					WarehouseTaskSingle wts = commonDao.load(WarehouseTaskSingle.class, wtsId);
					wts.setStatus(EnuWarehouseTaskSingleStatus.STATUS999);
					
					InventoryInfo invInfo = new InventoryInfo();
					try{
						BeanUtils.copyProperties(invInfo, wts.getWt().getInvInfo());
						invInfo.setBin(obd.getDescBin());
						invInfo.setContainerSeq(wts.getRelatedBill2());// 箱号
					}catch(Exception e){
						throw new BusinessException(ExceptionConstant.SYSTEM_EXCEPTION);
					}
					// 扣库存
					Inventory inv = inventoryHelper.getInv(invInfo);
					if( inv == null ){
						throw new BusinessException(ExceptionConstant.INVENTORY_NOT_FOUND);
					}
					
					inventoryHelper.removeInvWithoutAllocateQty(inv, wts.getCheckQty());
					
					// 记录库存履历
					historyHelper.saveInvHistory(inv, wts.getCheckQty() * (-1), 
							obd.getOrderType(), null, EnuInvenHisType.SHIPPING, 
							obd.getObdNumber(), null, null, obd.getRelatedBill1(), wts.getRelatedBill2());
					
					// 调整出库单的出库数
					wts.getWt().getObdDetail().addExecuteQty(wts.getCheckQty());
					
					// 记录出库履历
					historyHelper.saveShipHistory(wts.getWt().getObdDetail(), invInfo, wts.getCheckQty(), null, null);
				}
			}
			
			// 订单明细状态
			for( OutboundDeliveryDetail obdDetail : obd.getDetails() ){
				if( EnuOutboundDeliveryDetailStatus.STATUS900 > obdDetail.getStatus() && obdDetail.getUnExecuteQty() <= 0 ){
					obdDetail.setStatus(EnuOutboundDeliveryDetailStatus.STATUS900);// 发运完成
				}
				else if(EnuOutboundDeliveryDetailStatus.STATUS900 > obdDetail.getStatus() && obdDetail.getUnExecuteQty() > 0){
					obdDetail.setStatus(EnuOutboundDeliveryDetailStatus.STATUS910);// 发运完成有欠品
				}
			}
			
			// 订单状态
			if( EnuOutboundDeliveryStatus.STATUS900 > obd.getStatus() && obd.getUnExecuteQty() <= 0 ){
				orderStatusHelper.changeStatus(obd, EnuOutboundDeliveryStatus.STATUS900);// 发运完成
			}
			else if(EnuOutboundDeliveryStatus.STATUS900 > obd.getStatus() && obd.getUnExecuteQty() > 0){
				orderStatusHelper.changeStatus(obd, EnuOutboundDeliveryStatus.STATUS910);// 发运完成有欠品
			}
			
			obd.setAtd(new Date());//实际发运日期
			obd.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
		}
	}

	@Override
	public void checkConfirm4Through(List<Long> obdDetailIds) {
		
		Set<OutboundDelivery> obdSet = new HashSet<OutboundDelivery>();
		Set<WaveDoc> waveSet = new HashSet<WaveDoc>();
		for( Long obdDetailId : obdDetailIds ){
			OutboundDeliveryDetail obdDetail = commonDao.load(OutboundDeliveryDetail.class, obdDetailId);
			if( !EnuOutboundDeliveryDetailStatus.STATUS400.equals(obdDetail.getStatus()) ){
				throw new BusinessException(ExceptionConstant.ERROR_STATUS);
			}
			
			obdDetail.addPickUpQty(obdDetail.getAllocateQty());
			obdDetail.addPackedQty(obdDetail.getAllocateQty());
			obdDetail.setStatus(EnuOutboundDeliveryDetailStatus.STATUS850);
			commonDao.store(obdDetail);
			
			obdSet.add(obdDetail.getObd());
			waveSet.add(obdDetail.getObd().getWaveDoc());
		}
		
		for( OutboundDelivery obd : obdSet ){
			if( EnuOutboundDeliveryStatus.STATUS840.longValue() > obd.getStatus().longValue() && obd.getPackedQty() > 0 ){
				orderStatusHelper.changeStatus(obd, EnuOutboundDeliveryStatus.STATUS840);
			}
			
			if( EnuOutboundDeliveryStatus.STATUS840.longValue() == obd.getStatus().longValue() && obd.getUnPickupQty() <= 0 ){
				orderStatusHelper.changeStatus(obd, EnuOutboundDeliveryStatus.STATUS850);
			}
		}
		
		for( WaveDoc wv : waveSet ){
			String hql2 = " select sum(obdDetail.pickUpQty) from OutboundDeliveryDetail obdDetail where obdDetail.obd.waveDoc.id = :wvId";
			Double wvPickQty = (Double)commonDao.findByQueryUniqueResult(hql2, new String[]{"wvId"}, new Object[]{wv.getId()});
			if(wvPickQty == null)
				wvPickQty = 0D;
			wv.setExecuteQty(wvPickQty);
			commonDao.store(wv);
			
			if( EnuWaveStatus.STATUS300.longValue() > wv.getStatus().longValue() && wv.getExecuteQty() > 0 ){
				orderStatusHelper.changeStatus(wv, EnuWaveStatus.STATUS300);
			}
			
			if( EnuWaveStatus.STATUS300.longValue() == wv.getStatus().longValue() && wv.getUnExecuteQty() <= 0 ){
				orderStatusHelper.changeStatus(wv, EnuWaveStatus.STATUS500);
			}
		}
	}
	
}