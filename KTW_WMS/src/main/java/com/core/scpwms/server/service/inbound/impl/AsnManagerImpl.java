package com.core.scpwms.server.service.inbound.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.hibernate.criterion.Order;

import com.core.business.exception.BusinessException;
import com.core.business.model.domain.CreateInfo;
import com.core.business.model.domain.UpdateInfo;
import com.core.business.utils.DoubleUtil;
import com.core.business.web.security.UserHolder;
import com.core.db.server.service.pojo.DefaultBaseManager;
import com.core.scpwms.server.constant.ExceptionConstant;
import com.core.scpwms.server.enumerate.EnuAsnDetailStatus;
import com.core.scpwms.server.enumerate.EnuAsnStatus;
import com.core.scpwms.server.enumerate.EnuInvProcessType;
import com.core.scpwms.server.enumerate.EnuInvStatus;
import com.core.scpwms.server.enumerate.EnuInvenHisType;
import com.core.scpwms.server.enumerate.EnuStockDiv;
import com.core.scpwms.server.filter.WarehouseHolder;
import com.core.scpwms.server.model.common.OrderType;
import com.core.scpwms.server.model.common.PackageDetail;
import com.core.scpwms.server.model.common.Sku;
import com.core.scpwms.server.model.inbound.Asn;
import com.core.scpwms.server.model.inbound.AsnDetail;
import com.core.scpwms.server.model.inbound.InboundHistory;
import com.core.scpwms.server.model.inventory.Inventory;
import com.core.scpwms.server.model.inventory.Quant;
import com.core.scpwms.server.model.lot.LotInputData;
import com.core.scpwms.server.model.warehouse.Bin;
import com.core.scpwms.server.model.warehouse.Labor;
import com.core.scpwms.server.model.warehouse.Warehouse;
import com.core.scpwms.server.service.common.BCManager;
import com.core.scpwms.server.service.common.InventoryHelper;
import com.core.scpwms.server.service.common.InventoryHistoryHelper;
import com.core.scpwms.server.service.common.OrderStatusHelper;
import com.core.scpwms.server.service.inbound.AsnManager;
import com.core.scpwms.server.service.inventory.QuantManager;
import com.core.scpwms.server.util.DateUtil;
import com.core.scpwms.server.util.LotValidate;
import com.core.scpwms.server.util.PrecisionUtils;

public class AsnManagerImpl extends DefaultBaseManager implements AsnManager {
	private BCManager bcManager;
	private OrderStatusHelper orderStatusHelper;
	private QuantManager quantManager;
	private InventoryHistoryHelper historyHelper;
	private InventoryHelper invHelper;
	
	public QuantManager getQuantManager() {
		return quantManager;
	}

	public void setQuantManager(QuantManager quantManager) {
		this.quantManager = quantManager;
	}

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

	public InventoryHistoryHelper getHistoryHelper() {
		return historyHelper;
	}

	public void setHistoryHelper(InventoryHistoryHelper historyHelper) {
		this.historyHelper = historyHelper;
	}

	public InventoryHelper getInvHelper() {
		return invHelper;
	}

	public void setInvHelper(InventoryHelper invHelper) {
		this.invHelper = invHelper;
	}

	@Override
	public void saveAsn(Asn asn) {
		if( asn.isNew() ){
			asn.setWh(WarehouseHolder.getWarehouse());
			OrderType ot = commonDao.load(OrderType.class, asn.getOrderType().getId());
			asn.setAsnNumber(bcManager.getOrderSequence(ot, asn.getWh().getId()));
			asn.setCreateInfo(new CreateInfo(UserHolder.getUser()));
			commonDao.store(asn);
			orderStatusHelper.changeStatus(asn, EnuAsnStatus.STATUS100);
		}
		else{
			if( !EnuAsnStatus.STATUS100.equals(asn.getStatus()) )
				throw new BusinessException(ExceptionConstant.ERROR_STATUS);
			asn.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
			commonDao.store(asn);
		}
		
	}

	@Override
	public void saveAsnDetail(Long asnId, AsnDetail asnDetail) {
		Asn asn = commonDao.load(Asn.class, asnId);
		if( !EnuAsnStatus.STATUS100.equals(asn.getStatus()) )
			throw new BusinessException(ExceptionConstant.ERROR_STATUS);
		
		if( asnDetail.isNew() ){
			asnDetail.setLineNo(getNextLineNo(asnId));
			asnDetail.setStatus(EnuAsnDetailStatus.STATUS100);
			asnDetail.setAsn(asn);
		}
		
		Sku sku = commonDao.load(Sku.class, asnDetail.getSku().getId());
		asnDetail.setStockDiv(sku.getStockDiv());
		
		commonDao.store(asnDetail);
		updateTotalInfo(asnId);
	}
	
	private Double getNextLineNo( Long asnId ){
		String hql = " select max(lineNo) from AsnDetail asnDetail where asnDetail.asn.id = :asnId ";
		Double maxLine = (Double) commonDao.findByQueryUniqueResult(hql, new String[]{"asnId"}, new Object[]{asnId});
		
		if( maxLine == null )
			return 1D;
		else 
			return maxLine + 1D;
	}
	
	public void updateTotalInfo( Long asnId ){
		StringBuffer hql = new StringBuffer("select count( distinct asnDetail.sku.id ), ");
		hql.append(" coalesce(sum( asnDetail.planQty * asnDetail.sku.grossWeight ),0),");
		hql.append(" coalesce(sum( asnDetail.planQty * asnDetail.sku.volume ),0),");
		hql.append(" coalesce(sum( asnDetail.planQty * asnDetail.sku.price ),0),");
		hql.append(" coalesce(sum( asnDetail.planQty ),0)");
		hql.append(" from AsnDetail asnDetail where asnDetail.asn.id = :asnId ");
		
		Object[] totalInfos = (Object[]) commonDao.findByQueryUniqueResult(hql.toString(), new String[]{"asnId"}, new Object[]{asnId});
		if( totalInfos != null ){
			Asn asn = commonDao.load(Asn.class, asnId);
			asn.setSkuCount((Long)totalInfos[0]);
			asn.setSumWeight((Double)totalInfos[1]);
			asn.setSumVolume((Double)totalInfos[2]);
			asn.setSumPrice((Double)totalInfos[3]);
			asn.setPlanQty((Double)totalInfos[4]);
			commonDao.store(asn);
		}
	}

	@Override
	public void deleteAsnDetail(List<Long> asnDetailIds) {
		Asn asn = null;
		
		for( Long asnDetailId : asnDetailIds ){
			AsnDetail asnDetail = commonDao.load(AsnDetail.class, asnDetailId);
			
			if( asn == null ){
				asn = asnDetail.getAsn();
				
				if( !EnuAsnStatus.STATUS100.equals(asn.getStatus()) )
					throw new BusinessException(ExceptionConstant.ERROR_STATUS);
			}
			
			commonDao.delete(asnDetail);
		}
		
		updateTotalInfo(asn.getId());
	}

	@Override
	public void publish(List<Long> asnIds, Long binId) {
		Bin bin = commonDao.load(Bin.class, binId);
		
		for( Long asnId : asnIds ){
			Asn asn = commonDao.load(Asn.class, asnId);
			if( !EnuAsnStatus.STATUS100.equals(asn.getStatus()) )
				throw new BusinessException(ExceptionConstant.ERROR_STATUS);
			
			asn.setPublishDate(new Date());
			asn.setBin(bin);
			asn.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
			orderStatusHelper.changeStatus(asn, EnuAsnStatus.STATUS200);
			commonDao.store(asn);
			
			// asnDetails
			String hql = "select asnDetail.id from AsnDetail asnDetail where asnDetail.asn.id = :asnId ";
			List<Long> asnDetailIds = commonDao.findByQuery(hql, "asnId", asnId);
			if( asnDetailIds == null || asnDetailIds.size() == 0 ){
				throw new BusinessException(ExceptionConstant.EMPTY_ORDER);
			}
			
			updateAsnDetailStatus(asnId, EnuAsnDetailStatus.STATUS200);
		}
	}
	
	private void updateAsnDetailStatus( Long asnId , Long status ){
		String sql = " update WMS_ASN_DETAIL set STATUS = ? WHERE ASN_ID = ? ";
		commonDao.executeSqlQuery(sql, new Object[]{status, asnId});
	}
	
	@Override
	public void cancel(List<Long> asnIds) {
		for( Long asnId : asnIds ){
			Asn asn = commonDao.load(Asn.class, asnId);
			asn.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
			if( EnuAsnStatus.STATUS100.equals(asn.getStatus()) ){
				orderStatusHelper.changeStatus(asn, EnuAsnStatus.STATUS000);
				updateAsnDetailStatus(asnId, EnuAsnDetailStatus.STATUS000);
			}
			else if( EnuAsnStatus.STATUS200.equals(asn.getStatus()) ){
				asn.setBin(null);
				asn.setPublishDate(null);
				orderStatusHelper.changeStatus(asn, EnuAsnStatus.STATUS100);
				updateAsnDetailStatus(asnId, EnuAsnDetailStatus.STATUS100);
			}
			else{
				throw new BusinessException(ExceptionConstant.ERROR_STATUS);
			}
			
			commonDao.store(asn);
		}
	}

	@Override
	public void delete(List<Long> asnIds) {
		for( Long asnId : asnIds ){
			Asn asn = commonDao.load(Asn.class, asnId);
			if( !EnuAsnStatus.STATUS000.equals(asn.getStatus()) )
				throw new BusinessException(ExceptionConstant.ERROR_STATUS);
			commonDao.delete(asn);
		}
	}

	@Override
	public void close(List<Long> asnIds) {
		for( Long asnId : asnIds ){
			Asn asn = commonDao.load(Asn.class, asnId);
			asn.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
			orderStatusHelper.changeStatus(asn, EnuAsnStatus.STATUS400);
			
			for( AsnDetail asnDetail : asn.getDetails() ){
				asnDetail.setStatus(EnuAsnDetailStatus.STATUS400);
				commonDao.store(asnDetail);
			}
		}
	}
	
	@Override
	public void forceCloseAsnDetail(List<Long> asnDetailIds, String desc) {
		Set<Long> asnIds = new HashSet<Long>();
		for( Long asnDetailId : asnDetailIds ){
			AsnDetail asnDetail = commonDao.load(AsnDetail.class, asnDetailId);
			
			if( !EnuAsnDetailStatus.STATUS200.equals(asnDetail.getStatus()) && 
				!EnuAsnDetailStatus.STATUS300.equals(asnDetail.getStatus()) ){
				throw new BusinessException(ExceptionConstant.ERROR_STATUS);
			}
			
			asnDetail.setStatus(EnuAsnDetailStatus.STATUS400);
			asnDetail.setExtDescription(desc);
			commonDao.store(asnDetail);
			
			asnIds.add(asnDetail.getAsn().getId());
		}
		
		// 订单状态修改
		for( Long asnId : asnIds ){
			String hql1 = "select detail.id from AsnDetail detail where detail.status < 400 and detail.asn.id = :asnId ";
			List<Long> unClosedDetailIds = commonDao.findByQuery(hql1, "asnId", asnId);
			
			Asn asn = commonDao.load(Asn.class, asnId);
			asn.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
			if( unClosedDetailIds == null || unClosedDetailIds.size() == 0 ){
				orderStatusHelper.changeStatus(asn, EnuAsnStatus.STATUS400);// 入库完成
			}
			else{
				orderStatusHelper.changeStatus(asn, EnuAsnStatus.STATUS400);// 入库完成
			}
		}
	}

	@Override
	public void closeAsnDetail(List<Long> asnDetailIds, String desc) {
		for( Long asnDetailId : asnDetailIds ){
			AsnDetail asnDetail = commonDao.load(AsnDetail.class, asnDetailId);
			if( asnDetail.getUnClosedQty() > 0 ){
				asnDetail.addClosedQty(asnDetail.getUnClosedQty());
			}
		}
	}

	@Override
	public InboundHistory receive(Long asnDetailId, Date expDate, String invStatus, Double qty, Long laborId) {
		return recievingOneLine(asnDetailId, invStatus, qty, expDate, null, null, laborId);
	}

	@Override
	public void batchReceive(List<Long> asnDetailIds, Long laborId) {
		for( Long asnDetailId : asnDetailIds ){
			AsnDetail asnDetail = commonDao.load(AsnDetail.class, asnDetailId);
			
			// 库存型
			if( EnuStockDiv.STOCK.equals(asnDetail.getStockDiv()) ){
				if( asnDetail.getExpDate() == null ){
					throw new BusinessException( asnDetail.getSku().getName() + "、賞味期限が必須です。" );
				}
				//　默认为良品
				String invStatus = asnDetail.getInvStatus();
				if( StringUtils.isEmpty(invStatus) )
					invStatus = EnuInvStatus.AVAILABLE;
				
				recievingOneLine(asnDetailId, invStatus, asnDetail.getUnexecuteQty(), asnDetail.getExpDate(), null, null, laborId);
			}
			// 非库存型
			else{
				recievingOneLine(asnDetailId, EnuInvStatus.AVAILABLE, asnDetail.getUnexecuteQty(), null, null, null, laborId);
			}
		}
	}

	@Override
	public InboundHistory receive4Through(Long asnDetailId, Double qty, Long laborId) {
		return recievingOneLine(asnDetailId, EnuInvStatus.AVAILABLE, qty, null, null, null, laborId);
	}

	@Override
	public void cancelReceive(List<Long> ibdHisIds, String desc) {
		for( Long ibdHistoryId : ibdHisIds ){
			InboundHistory ibdHis = commonDao.load(InboundHistory.class, ibdHistoryId);
			AsnDetail detail = ibdHis.getDetail();
			Asn asn = ibdHis.getAsn();
			
			// 库存型
			if( EnuStockDiv.STOCK.equals(ibdHis.getDetail().getStockDiv()) ){
				// 删除库存
				Inventory inv = invHelper.getInv(ibdHis.getInvInfo());
				if( inv == null ){
					throw new BusinessException("入荷検品区に該当する在庫が見つかりません。");
				}
				else if( DoubleUtil.compareByPrecision(ibdHis.getExecuteQty(), inv.getAvailableQty(), 0) > 0 ){
					throw new BusinessException("入荷検品区に取消可能の在庫数が入荷実績数より少ないです。");
				}
				invHelper.removeInvWithoutAllocateQty(inv, ibdHis.getExecuteQty());
				
				// 登记库存履历，收货调整
				historyHelper.saveInvHistory(inv, ibdHis.getExecuteQty()*(-1), ibdHis.getAsn().getOrderType(), null, EnuInvenHisType.RECEIVING_ADJUST, 
						ibdHis.getAsn().getAsnNumber(), null, desc, ibdHis.getAsn().getRelatedBill1(), null);
			}
			
			// 删除入库履历
			commonDao.delete(ibdHis);
			
			// 调整AsnDetai的收货数,调整Asn的收货数
			detail.removeExecuteQty(ibdHis.getExecuteQty());
			
			if( detail.getPlanQty() <= 0 && detail.getExecuteQty() <= 0 ){
				
				asn.getDetails().remove(detail);
				commonDao.delete(detail);
				
				// 查找用LineNo的Detail，调整状态
				String hql = " from AsnDetail detail where detail.asn.id = :asnId and detail.lineNo = :lineNo ";
				List<AsnDetail> details = commonDao.findByQuery(hql, new String[]{"asnId", "lineNo"}, new Object[]{asn.getId(), detail.getLineNo()});
				
				if( details != null && details.size() > 0 ){
					for( AsnDetail otherDetail : details ){
						// 更新AsnDetail的状态
						if (EnuAsnDetailStatus.STATUS400.equals(otherDetail.getStatus()) && otherDetail.getUnexecuteQty() > 0) {
							otherDetail.setStatus(EnuAsnDetailStatus.STATUS300);
						}
						if (EnuAsnDetailStatus.STATUS300.equals(otherDetail.getStatus()) && otherDetail.getExecuteQty() <= 0) {
							otherDetail.setStatus(EnuAsnDetailStatus.STATUS200);
							otherDetail.setActInvStatus(null);
							otherDetail.setActExpDate(null);
						}
						
						commonDao.store(otherDetail);
					}
				}
			}
			else{
				// 更新AsnDetail的状态
				if (EnuAsnDetailStatus.STATUS400.equals(detail.getStatus()) && detail.getUnexecuteQty() > 0) {
					detail.setStatus(EnuAsnDetailStatus.STATUS300);
				}
				if (EnuAsnDetailStatus.STATUS300.equals(detail.getStatus()) && detail.getExecuteQty() <= 0) {
					detail.setStatus(EnuAsnDetailStatus.STATUS200);
					detail.setActInvStatus(null);
					detail.setActExpDate(null);
				}
				
				commonDao.store(detail);
			}
			
			// 更新Asn的状态
			if (EnuAsnStatus.STATUS400.equals(asn.getStatus()) && asn.getUnExecuteQty() > 0) {
				orderStatusHelper.changeStatus(asn, EnuAsnStatus.STATUS300);
			}
			if (EnuAsnStatus.STATUS300.equals(asn.getStatus()) && asn.getExecuteQty() <= 0) {
				orderStatusHelper.changeStatus(asn, EnuAsnStatus.STATUS200);
			}
			commonDao.store(asn);
		}
	}
	
	private InboundHistory recievingOneLine(Long asnDetailId, String invStatus, double receiveQty, Date expDate, Bin bin, String container, Long laborId) {
		AsnDetail oldDetail = commonDao.load(AsnDetail.class, asnDetailId);
		
		// 收货数不能超过同行号的待收货数
		String hql = " select coalesce(sum(asnDetail.planQty-asnDetail.executeQty)) "
				+ "from AsnDetail asnDetail where asnDetail.asn.id = :asnId and asnDetail.sku.id = :skuId and asnDetail.lineNo = :lineNo ";
		Double unexeQty = (Double)commonDao.findByQueryUniqueResult(hql, new String[]{"asnId", "skuId", "lineNo"}, 
				new Object[]{ oldDetail.getAsn().getId(), oldDetail.getSku().getId(), oldDetail.getLineNo()});
		if( DoubleUtil.compareByPrecision(receiveQty, unexeQty, 0) > 0){
			throw new BusinessException(ExceptionConstant.OVER_PLANQTY);
		}
		
		AsnDetail detail = null;
		// 如果AsnDetail的预定保质期有而其和输入的一致，就用这个AsnDetail
		if( oldDetail.getExpDate() != null && DateUtils.isSameDay(oldDetail.getExpDate(), expDate) ){
			detail = oldDetail;
			detail.setActExpDate(expDate);
		}
		// 如果AsnDetail的预定保质期有，但是和输入的不一致
		else if( oldDetail.getExpDate() != null && !DateUtils.isSameDay(oldDetail.getExpDate(), expDate) ){
			detail = getNewAsnDetail(oldDetail, expDate);
		}
		// 如果AsnDetail的预定保质期没有，实际保值期也没有
		else if( oldDetail.getExpDate() == null && oldDetail.getActExpDate() == null ){
			detail = oldDetail;
			detail.setActExpDate(expDate);
		}
		// 如果AsnDetail的预定保质期没有，实际保值期有，和输入的一直
		else if( oldDetail.getExpDate() == null && DateUtils.isSameDay(oldDetail.getActExpDate(), expDate) ){
			detail = oldDetail;
		}
		// 如果AsnDetail的预定保质期没有，实际保值期有，和输入的不一直
		else if( oldDetail.getExpDate() == null && !DateUtils.isSameDay(oldDetail.getActExpDate(), expDate) ){
			detail = getNewAsnDetail(oldDetail, expDate);
		}
		
		Asn asn = detail.getAsn();

		// 状态验证（发行,收货中）
		if (!EnuAsnDetailStatus.STATUS200.equals(detail.getStatus())
				&& !EnuAsnDetailStatus.STATUS300.equals(detail.getStatus())) {
			throw new BusinessException(ExceptionConstant.ERROR_STATUS);
		}

		// 收货库位
		Bin descBin = bin == null ? asn.getBin() : bin;
		asn.setBin(descBin);
		
		// 库存状态
		if( invStatus == null )
			invStatus = detail.getInvStatus();//预定库存状态
		if( invStatus == null )
			invStatus = EnuInvStatus.AVAILABLE;//默认良品
		detail.setActInvStatus(invStatus);
		
		// 容器号
		String descContainer = container;

		// 剩余为收货数量
		receiveQty = PrecisionUtils.getBaseQty(receiveQty, detail.getPlanPackage());

		// 收货包装（用户在画面上选择的包装）
		PackageDetail pkg = detail.getPlanPackage();

		// Quant信息取得
		LotInputData lotDate = new LotInputData();
		lotDate.setProperty1(DateUtil.getStringDate(expDate, DateUtil.PURE_DATE_FORMAT));
		Quant quant = quantManager.getQuantInfo(detail.getSku().getId(), lotDate);

		// 更新AsnDetail,Asn的收货数量
		detail.addExecuteQty(receiveQty);
		
		// 更新AsnDetail的状态
		if (EnuAsnDetailStatus.STATUS200.equals(detail.getStatus()) && detail.getExecuteQty() > 0) {
			detail.setStatus(EnuAsnDetailStatus.STATUS300);
		}
		if (EnuAsnDetailStatus.STATUS300.equals(detail.getStatus()) && detail.getPlanQty() > 0 && detail.getUnexecuteQty() <= 0) {
			detail.setStatus(EnuAsnDetailStatus.STATUS400);
		}
		
		// 按LineNO合计，如果待收货数已经为0了，相关的AsnDetail状态都设置成收货完成。
		String hql2 = " select coalesce(sum(detail.planQty-detail.executeQty)) from AsnDetail detail where detail.asn.id = :asnId and detail.lineNo = :lineNo ";
		Double unexeQty4Line = (Double)commonDao.findByQueryUniqueResult(hql2, new String[]{"asnId", "lineNo"},  new Object[]{asn.getId(), detail.getLineNo()});
		if( unexeQty4Line <= 0D ){
			String hql3 = " from AsnDetail detail where detail.asn.id = :asnId and detail.lineNo = :lineNo and detail.status < 400 ";
			List<AsnDetail> un400DetailList = commonDao.findByQuery(hql3, new String[]{"asnId", "lineNo"},  new Object[]{asn.getId(), detail.getLineNo()});
			if( un400DetailList != null && un400DetailList.size() > 0 ){
				for( AsnDetail un400Detail : un400DetailList ){
					un400Detail.setStatus(EnuAsnDetailStatus.STATUS400);
					commonDao.store(un400Detail);
				}
			}
		}
		
		// 更新Asn的状态
		if (EnuAsnStatus.STATUS200.equals(asn.getStatus()) && asn.getExecuteQty() > 0) {
			orderStatusHelper.changeStatus(asn, EnuAsnStatus.STATUS300);
			asn.setRevieveStart(new Date());
		}
		if (EnuAsnStatus.STATUS300.equals(asn.getStatus()) && asn.getUnExecuteQty() <= 0) {
			orderStatusHelper.changeStatus(asn, EnuAsnStatus.STATUS400);
			asn.setFinishStart(new Date());
		}
		
		asn.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));

		// 创建收货履历
		InboundHistory inboundHistory = historyHelper.saveInboundHistory(detail, descBin, descContainer, detail.getPlanPackage(), quant, receiveQty, invStatus, laborId);

		// 非库存的行类型，不需要加库存(行类型以S或者C打头是库存商品)
		if (EnuStockDiv.STOCK.equals(detail.getStockDiv())) {
			// 创建收货库存
			Inventory inv = invHelper.addInvToBin(inboundHistory.getInvInfo(), receiveQty, false);
			// 创建库存履历
			historyHelper.saveInvHistory(inv, receiveQty, asn.getOrderType(), commonDao.load(Labor.class, laborId), 
					EnuInvenHisType.RECEIVING, asn.getAsnNumber(), null, null, asn.getRelatedBill1(), null );
		}

		return inboundHistory;
	}
	
	private AsnDetail getNewAsnDetail( AsnDetail oldAsnDetail, Date expDate ){
		// 找同行号，同实际保质期的AsnDetail
		String hql = " select asnDetail.id from AsnDetail asnDetail where 1=1 "
				+ " and asnDetail.asn.id = :asnId "
				+ " and asnDetail.sku.id = :skuId "
				+ " and asnDetail.lineNo = :lineNo "
				+ " and asnDetail.status >= 200 "
				+ " and asnDetail.status <= 300 "
				+ " and (asnDetail.actExpDate = :expDate or asnDetail.expDate = :expDate)"
				+ " order by asnDetail.subLineNo";
		List<Long> asnDetailIds = commonDao.findByQuery(hql, 
				new String[]{"asnId", "skuId", "lineNo", "expDate"}, 
				new Object[]{oldAsnDetail.getAsn().getId(), oldAsnDetail.getSku().getId(), oldAsnDetail.getLineNo(), expDate});
		
		if( asnDetailIds != null && asnDetailIds.size() > 0 ){
			return commonDao.load(AsnDetail.class, asnDetailIds.get(0));
		}
		
		AsnDetail asnDetail =new AsnDetail();
		asnDetail.setAsn(oldAsnDetail.getAsn());
		asnDetail.setStatus(EnuAsnDetailStatus.STATUS300);
		asnDetail.setLineNo(oldAsnDetail.getLineNo());
		asnDetail.setSubLineNo(getNextSubLineNo(oldAsnDetail.getAsn().getId(), oldAsnDetail.getLineNo()));//
		asnDetail.setSku(oldAsnDetail.getSku());
		asnDetail.setStockDiv(oldAsnDetail.getStockDiv());
		asnDetail.setPlanQty(0D);
		asnDetail.setExecuteQty(0D);
		asnDetail.setInvStatus(oldAsnDetail.getInvStatus());
		asnDetail.setExpDate(null);
		asnDetail.setActInvStatus(oldAsnDetail.getInvStatus());
		asnDetail.setActExpDate(expDate);
		asnDetail.setPlanPackage(oldAsnDetail.getPlanPackage());
		asnDetail.setExtString1(oldAsnDetail.getExtString1());
		asnDetail.setExtString2(oldAsnDetail.getExtString2());
		asnDetail.setExtString3(oldAsnDetail.getExtString3());
		asnDetail.setDescription(oldAsnDetail.getDescription());
		asnDetail.setExtDescription(oldAsnDetail.getExtDescription());
		oldAsnDetail.getAsn().getDetails().add(asnDetail);
		return asnDetail;
	}
	
	private int getNextSubLineNo( Long asnId, Double lineNo ){
		String hql = " select max(detail.subLineNo) + 1 from AsnDetail detail where detail.asn.id = :asnId and detail.lineNo = :lineNo ";
		return (Integer)commonDao.findByQueryUniqueResult(hql, 
				new String[]{"asnId", "lineNo"}, 
				new Object[]{asnId, lineNo});
	}

	@Override
	public double canReceiveQty(Long asnDetailId) {
		AsnDetail oldDetail = commonDao.load(AsnDetail.class, asnDetailId);
		
		// 收货数不能超过同行号的待收货数
		String hql = " select coalesce(sum(asnDetail.planQty-asnDetail.executeQty)) "
				+ "from AsnDetail asnDetail where asnDetail.asn.id = :asnId and asnDetail.sku.id = :skuId and asnDetail.lineNo = :lineNo ";
		Double unexeQty = (Double)commonDao.findByQueryUniqueResult(hql, new String[]{"asnId", "skuId", "lineNo"}, 
				new Object[]{ oldDetail.getAsn().getId(), oldDetail.getSku().getId(), oldDetail.getLineNo()});
		return unexeQty == null ? 0D : unexeQty.doubleValue();
	}
}
