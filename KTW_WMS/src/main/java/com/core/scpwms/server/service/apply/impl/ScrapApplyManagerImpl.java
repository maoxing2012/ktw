/**
 * (C)2012 MBP Corporation. All rights reserved.
 * 系统名称 : MBP-WMS
 * 文件名   : ScrapApplyManagerImpl.java
 */

package com.core.scpwms.server.service.apply.impl;

import java.util.Date;
import java.util.List;

import com.core.business.exception.BusinessException;
import com.core.business.model.domain.CreateInfo;
import com.core.business.model.domain.UpdateInfo;
import com.core.business.web.security.UserHolder;
import com.core.db.server.service.pojo.DefaultBaseManager;
import com.core.scpwms.server.constant.ExceptionConstant;
import com.core.scpwms.server.enumerate.EnuApplyDetailStatus;
import com.core.scpwms.server.enumerate.EnuApplyStatus;
import com.core.scpwms.server.enumerate.EnuInvenHisType;
import com.core.scpwms.server.filter.WarehouseHolder;
import com.core.scpwms.server.model.apply.ScrapApply;
import com.core.scpwms.server.model.apply.ScrapApplyDetail;
import com.core.scpwms.server.model.common.OrderType;
import com.core.scpwms.server.model.inventory.Inventory;
import com.core.scpwms.server.model.user.Owner;
import com.core.scpwms.server.service.apply.ScrapApplyManager;
import com.core.scpwms.server.service.common.BCManager;
import com.core.scpwms.server.service.common.InventoryHelper;
import com.core.scpwms.server.service.common.InventoryHistoryHelper;
import com.core.scpwms.server.service.common.OrderStatusHelper;

/**
 * 破损申请单
 * @author mousachi
 *
 */
@SuppressWarnings("all")
public class ScrapApplyManagerImpl extends DefaultBaseManager implements ScrapApplyManager {
	
	private BCManager bcManager;
	private OrderStatusHelper orderStatusHelper;
	private InventoryHelper invHelper;
	private InventoryHistoryHelper historyHelper;
	
	public BCManager getBcManager() {
		return bcManager;
	}

	public void setBcManager(BCManager bcManager) {
		this.bcManager = bcManager;
	}

	public OrderStatusHelper getOrderStatusHelper() {
		return orderStatusHelper;
	}

	public void setOrderStatusHelper(OrderStatusHelper orderStatusHelper) {
		this.orderStatusHelper = orderStatusHelper;
	}
	
	public InventoryHelper getInvHelper() {
		return invHelper;
	}

	public void setInvHelper(InventoryHelper invHelper) {
		this.invHelper = invHelper;
	}
	
	public InventoryHistoryHelper getHistoryHelper() {
		return historyHelper;
	}

	public void setHistoryHelper(InventoryHistoryHelper historyHelper) {
		this.historyHelper = historyHelper;
	}

	@Override
	public void save(ScrapApply scrapApply) {
		if( scrapApply.isNew() ){
			scrapApply.setWh(WarehouseHolder.getWarehouse());
			OrderType ot = commonDao.load(OrderType.class, scrapApply.getOrderType().getId());
			scrapApply.setApplyNumber(bcManager.getOrderSequence(ot, scrapApply.getWh().getId()));
			Owner owner = commonDao.load(Owner.class, scrapApply.getOwner().getId());
			scrapApply.setPlant(owner.getPlant());
			scrapApply.setOwner(owner);
			scrapApply.setCreateInfo(new CreateInfo(UserHolder.getUser()));
			scrapApply.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
			commonDao.store(scrapApply);
			
			orderStatusHelper.changeStatus(scrapApply, EnuApplyStatus.DRAFT);
		}
		else{
			if( !EnuApplyStatus.DRAFT.equals(scrapApply.getStatus()) ){
				throw new BusinessException(ExceptionConstant.ERROR_STATUS);
			}
			
			scrapApply.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
			commonDao.store(scrapApply);
		}
	}

	@Override
	public void publish(List<Long> scrapApplyIds) {
		for( Long id : scrapApplyIds ){
			ScrapApply sp = commonDao.load(ScrapApply.class, id);
					
			if( !EnuApplyStatus.DRAFT.equals(sp.getStatus()) ){
				throw new BusinessException(ExceptionConstant.ERROR_STATUS);
			}
			
			if( sp.getDetails() == null || sp.getDetails().size() == 0 ){
				throw new BusinessException(ExceptionConstant.EMPTY_ORDER);
			}
			
			sp.setApplyDate(new Date());
			orderStatusHelper.changeStatus(sp, EnuApplyStatus.PUBLISH);
			
			
			for( ScrapApplyDetail detail : sp.getDetails() ){
				detail.setStatus(EnuApplyDetailStatus.PUBLISH);
			}
		}
	}

	@Override
	public void cancel(List<Long> scrapApplyIds) {
		for( Long id : scrapApplyIds ){
			ScrapApply sp = commonDao.load(ScrapApply.class, id);
					
			if( EnuApplyStatus.PUBLISH.equals(sp.getStatus()) ){
				sp.setApplyDate(null);
				orderStatusHelper.changeStatus(sp, EnuApplyStatus.DRAFT);
				
				for( ScrapApplyDetail detail : sp.getDetails() ){
					detail.setStatus(EnuApplyDetailStatus.DRAFT);
				}
			}
			else if( EnuApplyStatus.DRAFT.equals(sp.getStatus()) ){
				orderStatusHelper.changeStatus(sp, EnuApplyStatus.CANCEL);
				
				for( ScrapApplyDetail detail : sp.getDetails() ){
					Inventory inv = invHelper.getInv(detail.getInvInfo());
					if( inv != null ){
						inv.unAllocate(detail.getQty());
					}
					
					detail.setStatus(EnuApplyDetailStatus.CANCEL);
				}
			}
		}
	}

	@Override
	public void execute(List<Long> scrapApplyIds) {
		for( Long id : scrapApplyIds ){
			ScrapApply sa = commonDao.load(ScrapApply.class, id);
			
			for( ScrapApplyDetail detail : sa.getDetails() ){
				Inventory inv = invHelper.getInv(detail.getInvInfo());
				if( inv != null ){
					invHelper.removeInv(inv, detail.getQty());
					historyHelper.saveInvHistory(inv, detail.getQty()*(-1), sa.getOrderType(), null,
							EnuInvenHisType.INVENTORY_ADJUST, sa.getApplyNumber(), null, sa.getDescription(), null, null);
				}
				
				detail.setStatus(EnuApplyDetailStatus.APPROVED);
			}
			sa.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
			orderStatusHelper.changeStatus(sa, EnuApplyStatus.APPROVED);
			commonDao.store(sa);
		}
	}

	@Override
	public void close(List<Long> scrapApplyIds) {
		for( Long id : scrapApplyIds ){
			ScrapApply sp = commonDao.load(ScrapApply.class, id);
					
			if( !EnuApplyStatus.APPROVED.equals(sp.getStatus()) ){
				throw new BusinessException(ExceptionConstant.ERROR_STATUS);
			}
			
			orderStatusHelper.changeStatus(sp, EnuApplyStatus.CLOSE);
			for( ScrapApplyDetail detail : sp.getDetails() ){
				detail.setStatus(EnuApplyDetailStatus.CLOSE);
			}
		}
	}

	@Override
	public void addDetail(Long scrapApplyId, List<Long> invIds) {
		ScrapApply sp = commonDao.load(ScrapApply.class, scrapApplyId);
		
		if( !EnuApplyStatus.DRAFT.equals(sp.getStatus()) ){
			throw new BusinessException(ExceptionConstant.ERROR_STATUS);
		}
		
		for( Long invId : invIds ){
			Inventory inv = commonDao.load(Inventory.class, invId);
			if( inv.getAvailableQty() <= 0D )
				continue;
			
			ScrapApplyDetail detail = new ScrapApplyDetail();
			detail.setScrapApply(sp);
			detail.setStatus(EnuApplyDetailStatus.DRAFT);
			detail.setQty(inv.getAvailableQty());
			detail.setInvInfo(inv.getInventoryInfo());
			commonDao.store(detail);
			
			inv.allocate(detail.getQty());
		}
		
		// 合计头部
		updateSumInfo(scrapApplyId);
	}

	@Override
	public void deleteDetail(List<Long> scrapApplyDetailIds) {
		ScrapApply sp = null;
		
		for( Long detailId : scrapApplyDetailIds ){
			ScrapApplyDetail detail = commonDao.load(ScrapApplyDetail.class, detailId);
			
			if( sp == null ){
				sp = detail.getScrapApply();
				if( !EnuApplyStatus.DRAFT.equals(sp.getStatus()) ){
					throw new BusinessException(ExceptionConstant.ERROR_STATUS);
				}
			}
			
			Inventory inv = invHelper.getInv(detail.getInvInfo());
			if( inv != null ){
				inv.unAllocate(detail.getQty());
			}
			
			commonDao.delete(detail);
		}
		
		// 合计头部
		updateSumInfo(sp.getId());
	}
	
	private void updateSumInfo( Long scrapApplyId ){
		ScrapApply sp = commonDao.load(ScrapApply.class, scrapApplyId);
		
		String hql = " select count(distinct detail.invInfo.quant.sku.id) from ScrapApplyDetail detail "
				+ " where detail.scrapApply.id = :scrapApplyId ";
		
		String hql2 =  " select sum(detail.qty) from ScrapApplyDetail detail "
				+ " where detail.scrapApply.id = :scrapApplyId ";
		
		Long skuCount = (Long)commonDao.findByQueryUniqueResult(hql, new String[]{"scrapApplyId"}, new Object[]{scrapApplyId});
		Double sumQty = (Double)commonDao.findByQueryUniqueResult(hql2, new String[]{"scrapApplyId"}, new Object[]{scrapApplyId});
		
		sp.setSkuCount(skuCount == null ? 0L : skuCount.longValue());
		sp.setQty(sumQty == null ? 0D : sumQty);
		commonDao.store(sp);
	}  

}
