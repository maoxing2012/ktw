package com.core.scpwms.server.service.count.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;

import com.core.business.exception.BusinessException;
import com.core.business.model.domain.CreateInfo;
import com.core.business.model.domain.UpdateInfo;
import com.core.business.web.security.UserHolder;
import com.core.db.server.service.pojo.DefaultBaseManager;
import com.core.scpwms.server.constant.ExceptionConstant;
import com.core.scpwms.server.constant.NamedHqlConstant;
import com.core.scpwms.server.constant.WmsConstant;
import com.core.scpwms.server.domain.InvEditData;
import com.core.scpwms.server.domain.InventoryInfo;
import com.core.scpwms.server.enumerate.EnuCountDetailStatus;
import com.core.scpwms.server.enumerate.EnuCountMethod;
import com.core.scpwms.server.enumerate.EnuCountStatus;
import com.core.scpwms.server.enumerate.EnuInvenHisType;
import com.core.scpwms.server.enumerate.EnuStoreRole;
import com.core.scpwms.server.filter.WarehouseHolder;
import com.core.scpwms.server.model.common.OrderType;
import com.core.scpwms.server.model.common.PackageDetail;
import com.core.scpwms.server.model.common.Sku;
import com.core.scpwms.server.model.count.CountDetail;
import com.core.scpwms.server.model.count.CountPlan;
import com.core.scpwms.server.model.count.CountRecord;
import com.core.scpwms.server.model.inventory.Inventory;
import com.core.scpwms.server.model.inventory.Quant;
import com.core.scpwms.server.model.lot.LotInputData;
import com.core.scpwms.server.model.rules.CycleCountRule;
import com.core.scpwms.server.model.user.Owner;
import com.core.scpwms.server.model.warehouse.Bin;
import com.core.scpwms.server.model.warehouse.Labor;
import com.core.scpwms.server.service.common.BCManager;
import com.core.scpwms.server.service.common.BinHelper;
import com.core.scpwms.server.service.common.CommonHelper;
import com.core.scpwms.server.service.common.InventoryHelper;
import com.core.scpwms.server.service.common.InventoryHistoryHelper;
import com.core.scpwms.server.service.common.OrderStatusHelper;
import com.core.scpwms.server.service.count.CountPlanManager;
import com.core.scpwms.server.service.inventory.InvManager;
import com.core.scpwms.server.service.inventory.QuantManager;
import com.core.scpwms.server.util.DateUtil;
import com.core.scpwms.server.util.LocaleUtil;
import com.core.scpwms.server.util.PrecisionUtils;

/**
 * 复盘，不论原来的盘点是什么类型，都是生成从库位盘的盘点单。有差异的库位都会加入。
 * 
 */
public class CountPlanManagerImpl extends DefaultBaseManager implements CountPlanManager {
	
	private static Set<String> stRoles;
	private BCManager bcManager;
	private InventoryHelper inventoryHelper;
	private InventoryHistoryHelper historyHelper;
	private QuantManager quantManager;
	private OrderStatusHelper orderStatusHelper;
	private BinHelper binHelper;
	private CommonHelper commonHelper;
	private InvManager invManager;

	static{
		stRoles = new HashSet<String>();
		stRoles.add(EnuStoreRole.R1000);
		stRoles.add(EnuStoreRole.R3000);
		stRoles.add(EnuStoreRole.R4040);
	}
	public BCManager getBcManager() {
		return this.bcManager;
	}

	public void setBcManager(BCManager bcManager) {
		this.bcManager = bcManager;
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

	public InventoryHistoryHelper getHistoryHelper() {
		return this.historyHelper;
	}

	public void setHistoryHelper(InventoryHistoryHelper historyHelper) {
		this.historyHelper = historyHelper;
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

	public CommonHelper getCommonHelper() {
		return this.commonHelper;
	}

	public void setCommonHelper(CommonHelper commonHelper) {
		this.commonHelper = commonHelper;
	}

	public InvManager getInvManager() {
		return this.invManager;
	}

	public void setInvManager(InvManager invManager) {
		this.invManager = invManager;
	}

	@Override
	public void save(CountPlan cp) {
		if (cp.isNew()) {
			cp.setWarehouse(WarehouseHolder.getWarehouse());
			Owner owner = commonDao.load(Owner.class, cp.getOwner().getId());
			cp.setPlant(owner.getPlant());
			OrderType ot = commonHelper.getOrderType(WmsConstant.CNT_DOC);
			if (ot == null) {
				throw new BusinessException( ExceptionConstant.CANNOT_FIND_ORDER_TYPE, new String[] { LocaleUtil.getText("CNT_DOC") });
			}
			cp.setOrderType(ot);
			if( cp.getCountMethod() == null ){
				cp.setCountMethod(EnuCountMethod.FROM_BIN);
			}
			cp.setPlanDate(new Date());
			cp.setCreateInfo(new CreateInfo(UserHolder.getUser()));
			this.commonDao.store(cp);
			orderStatusHelper.changeStatus(cp, EnuCountStatus.STATUS100);
		} else {
			cp.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
			this.commonDao.store(cp);
		}
	}

	@Override
	public void deleteCount(List<Long> ids) {
		for (Long cpId : ids) {
			CountPlan cp = this.commonDao.load(CountPlan.class, cpId);
			orderStatusHelper.changeStatus(cp, EnuCountStatus.STATUS000);
			this.commonDao.store(cp);
		}
	}

	@SuppressWarnings("all")
	@Override
	public void publish(List<Long> ids) {
		for (Long cpId : ids) {
			CountPlan countPlan = this.commonDao.load(CountPlan.class, cpId);
			countPlan.setCode(bcManager.getOrderSequence(countPlan.getOrderType(), countPlan.getWarehouse().getId()));

			// 找一个盘点库位，如果找不到，报错
			Bin bin = binHelper.getEmptyCountBin(countPlan.getWarehouse().getId());
			if (bin == null) {
				throw new BusinessException(ExceptionConstant.COUNT_BIN_NOT_FIND);
			}
			countPlan.setBin(bin);
			
			// 找库存
			if( EnuCountMethod.DYNAMIC.equals(countPlan.getCountMethod()) ){
				String sql = "select inv.id from wms_inventory inv "
						+ " inner join wms_quant_inventory qi on inv.quant_inv_id = qi.id"
						+ " inner join wms_quant q on qi.quant_id = q.id"
						+ " inner join wms_count_detail cd on inv.bin_id = cd.bin_id and q.sku = cd.sku_id"
						+ " where cd.count_plan_id = ? and inv.wh_id = ? and inv.owner_id = ?";
				List<Object> invs = this.commonDao.findBySqlQuery(sql, new Object[]{countPlan.getId(), countPlan.getWarehouse().getId(), countPlan.getOwner().getId()});

				// 创建盘点记录
				for (Object invObj : invs) {
					Inventory inv = commonDao.load(Inventory.class, ((BigInteger)invObj).longValue());
					CountRecord ctRecord = new CountRecord();
					ctRecord.setCountPlan(countPlan);
					ctRecord.setStatus(EnuCountDetailStatus.STATUS100);// 未盘点
					ctRecord.setInvInfo(inv.getInventoryInfo());
					ctRecord.addPlanQty(inv.getBaseQty().doubleValue());// 理论库存数
					ctRecord.setCountQuantity(0D);// 盘点数
					countPlan.getRecords().add(ctRecord);
					commonDao.store(ctRecord);
				}
			}
			else{
				String hql = "select inv.id from Inventory inv where 1=1 "
						+ " and inv.wh.id=:whId "
						+ " and inv.owner.id = :ownerId "
						+ " and inv.bin.lockStatus is null "
						+ " and inv.bin.id in ( select distinct cr.bin.id from CountDetail cr where cr.countPlan.id = :cpId ) "
						+ " order by inv.bin.binCode, inv.quantInv.quant.sku.code, inv.quantInv.quant.dispLot";
				List<Long> invs = this.commonDao .findByQuery(hql,
								new String[] { "whId", "ownerId", "cpId" },
								new Object[] { countPlan.getWarehouse().getId(), countPlan.getOwner().getId(), countPlan.getId() });

				// 创建盘点记录
				for (Long invId : invs) {
					Inventory inv = commonDao.load(Inventory.class, invId);
					CountRecord ctRecord = new CountRecord();
					ctRecord.setCountPlan(countPlan);
					ctRecord.setStatus(EnuCountDetailStatus.STATUS100);// 未盘点
					ctRecord.setInvInfo(inv.getInventoryInfo());
					ctRecord.addPlanQty(inv.getBaseQty().doubleValue());// 理论库存数
					ctRecord.setCountQuantity(0D);// 盘点数
					countPlan.getRecords().add(ctRecord);
					commonDao.store(ctRecord);
				}
			}
			
			// 更新盘点单状态
			orderStatusHelper.changeStatus(countPlan, EnuCountStatus.STATUS200);// 发行
			commonDao.store(countPlan);

			// 更新盘点单头部的合计信息
			updateTotalInfo(countPlan.getId());
		}
	}

	@Override
	public void close(List<Long> ids) {
		for (Long countPlanId : ids) {
			CountPlan countPlan = commonDao.load(CountPlan.class, countPlanId);

			// 释放盘点锁
			releaseBinLock(countPlan);

			// 释放盘点库位
			Bin bin = countPlan.getBin();
			
			// 如果该库位上没有库存，把库位的备注释放。
			String hql = "select inv.id from Inventory inv where inv.bin.id = :binId ";
			List<Long> invIds = commonDao.findByQuery(hql, "binId", bin.getId());
			if( invIds == null || invIds.size() == 0 ){
				bin.setDescription(null);
				commonDao.store(bin);
			}

			// 修改盘点明细状态
			for (CountRecord cr : countPlan.getRecords()) {
				cr.setStatus(EnuCountDetailStatus.STATUS999);
				commonDao.store(cr);
			}

			// 修改盘点单状态
			orderStatusHelper.changeStatus(countPlan, EnuCountStatus.STATUS999);
			commonDao.store(countPlan);

		}
	}

	@Override
	public void cancel(List<Long> ids) {
		for (Long countPlanId : ids) {

			CountPlan countPlan = commonDao.load(CountPlan.class, countPlanId);

			// 发行 -> 草案
			if (EnuCountStatus.STATUS200.equals(countPlan.getStatus())) {
				countPlan.setCode(null);
				// 释放盘点锁
				releaseBinLock(countPlan);

				// 释放盘点库位
				Bin bin = countPlan.getBin();
				countPlan.setBin(null);
				bin.setDescription(null);
				commonDao.store(bin);

				// 删除所有CountRecord
				for (CountRecord cr : countPlan.getRecords()) {
					cr.removePlanQty(cr.getInvBaseQty());
					commonDao.delete(cr);
				}
				countPlan.getRecords().removeAll(countPlan.getRecords());

				// 更新盘点单头部状态
				orderStatusHelper.changeStatus(countPlan, EnuCountStatus.STATUS100);
				commonDao.store(countPlan);

				// 更新盘点单头部合计信息
				updateTotalInfo(countPlan.getId());
			}

			// 草案 -> 取消
			else if (EnuCountStatus.STATUS100.equals(countPlan.getStatus())) {
				orderStatusHelper.changeStatus(countPlan, EnuCountStatus.STATUS000);
				commonDao.store(countPlan);
			}
			else {
				throw new BusinessException(ExceptionConstant.ERROR_STATUS);
			}
		}
	}

	private void releaseBinLock(CountPlan countPlan) {
		// 如果有库位锁，解锁
		if (countPlan.getBinLock() != null
				&& countPlan.getBinLock().booleanValue()) {
			// 盘点方式
			String countMethod = countPlan.getCountMethod();

			Set<CountDetail> countDetail = countPlan.getDetails();
			Set<CountRecord> ctRecords = countPlan.getRecords();

			// 从库位盘/动碰盘/循环盘
			if (EnuCountMethod.FROM_BIN.equals(countMethod)
					|| EnuCountMethod.DYNAMIC.equals(countPlan.getCountMethod())
					|| EnuCountMethod.CYCLE.equals(countPlan.getCountMethod())) {
				if (countDetail != null && countDetail.size() > 0) {
					for (CountDetail d : countDetail) {
						Bin bin = d.getBin();

						if (bin != null) {
							bin.setLockStatus(null);
							commonDao.store(bin);
						}
					}
				}
			}

			// 从商品盘
			else if (EnuCountMethod.FROM_SKU.equals(countMethod)) {
				if (ctRecords != null && ctRecords.size() > 0) {
					for (CountRecord countRecord : ctRecords) {
						Bin bin = countRecord.getInvInfo().getBin();

						if (bin != null) {
							bin.setLockStatus(null);
							commonDao.store(bin);
						}
					}
				}
			}
		}
	}

	@Override
	public void countConfirm(List<Long> ids, Map params) {
		for (Long ctRecordId : ids) {
			CountRecord ctRecord = load(CountRecord.class, ctRecordId);

			// 只有未盘点和盘点中的CR可以盘点确认。
			if (!EnuCountDetailStatus.STATUS100.equals(ctRecord.getStatus())
					&& !EnuCountDetailStatus.STATUS200.equals(ctRecord.getStatus())
					&& !EnuCountDetailStatus.STATUS210.equals(ctRecord.getStatus())) {
				continue;
			}

			List<Object> value = (List<Object>) params.get(ctRecord.getId());

			// 盘点数量
			double countQuantity = PrecisionUtils.formatByBasePackage( new Double((value.get(0).toString())), ctRecord.getInvInfo().getPackageDetail());

			ctRecord.setCountQuantity(countQuantity);
			ctRecord.setOccurTime(new Date());
			ctRecord.setOperator(UserHolder.getUser().getName());
			ctRecord.setStatus(ctRecord.getDeltaQuantity() != null && ctRecord.getDeltaQuantity() == 0D ? 
					EnuCountDetailStatus.STATUS200 : EnuCountDetailStatus.STATUS210);// 盘点中
			commonDao.store(ctRecord);

			// 更新库位的最后盘点日期
			Bin bin = ctRecord.getInvInfo().getBin();
			bin.getBinInvInfo().getBinInfo().setLastCountDate(new Date());
			commonDao.store(bin);

			// 更新盘点单头状态
			CountPlan countPlan = ctRecord.getCountPlan();

			// 如果盘点单头的状态还是发行，改为盘点中状态，如果已经是差异调整状态，则不改状态
			if (EnuCountStatus.STATUS200.equals(countPlan.getStatus())) {
				orderStatusHelper.changeStatus(countPlan, EnuCountStatus.STATUS300);
				commonDao.store(countPlan);
			}

			// 更新盘点单头的合计数量
			updateCountTotalInfo(countPlan.getId());
		}
	}
	
	@Override
	public void countConfirm(Long countRecordId, Double countPackQty, Long packId, Long laborId){
		CountRecord ctRecord = load(CountRecord.class, countRecordId);

		// 只有未盘点和盘点中的CR可以盘点确认。
		if (!EnuCountDetailStatus.STATUS100.equals(ctRecord.getStatus())
				&& !EnuCountDetailStatus.STATUS200.equals(ctRecord.getStatus())
				&& !EnuCountDetailStatus.STATUS210.equals(ctRecord.getStatus())) {
			throw new BusinessException(ExceptionConstant.ERROR_STATUS);
		}

		PackageDetail pd = packId == null ? ctRecord.getInvInfo().getPackageDetail() : commonDao.load(PackageDetail.class, packId );
		
		// 盘点数量
		double countQuantity = PrecisionUtils.getBaseQty(countPackQty, pd);
		
		ctRecord.setCountQuantity(countQuantity);
		ctRecord.setOccurTime(new Date());
		Labor labor = null;
		if( laborId != null ){
			labor = commonDao.load(Labor.class, laborId);
		}
		ctRecord.setLabor(labor);
		ctRecord.setOperator(UserHolder.getUser().getName());
		
		ctRecord.setStatus(ctRecord.getDeltaQuantity() != null && ctRecord.getDeltaQuantity() == 0D ? 
				EnuCountDetailStatus.STATUS200 : EnuCountDetailStatus.STATUS210);// 盘点中
		commonDao.store(ctRecord);

		// 更新库位的最后盘点日期
		Bin bin = ctRecord.getInvInfo().getBin();
		bin.getBinInvInfo().getBinInfo().setLastCountDate(new Date());
		commonDao.store(bin);

		// 更新盘点单头状态
		CountPlan countPlan = ctRecord.getCountPlan();

		// 如果盘点单头的状态还是发行，改为盘点中状态，如果已经是差异调整状态，则不改状态
		if (EnuCountStatus.STATUS200.equals(countPlan.getStatus())) {
			orderStatusHelper.changeStatus(countPlan, EnuCountStatus.STATUS300);
			commonDao.store(countPlan);
		}

		// 更新盘点单头的合计数量
		updateCountTotalInfo(countPlan.getId());
	}

	@Override
	public void saveSkuDetail(List<Long> ids, Long countPlanId) {
		CountPlan cp = load(CountPlan.class, countPlanId);
		if (ids == null) {
			return;
		}
		for (Long idd : ids) {
			CountDetail detail = new CountDetail();
			Sku sku = load(Sku.class, idd);
			detail.setCountPlan(cp);
			detail.setSku(sku);
			commonDao.store(detail);
		}
	}

	@Override
	public void saveBinDetail(List<Long> ids, Long countPlanId) {
		CountPlan cp = load(CountPlan.class, countPlanId);
		if (ids == null) {
			return;
		}
		for (Long id : ids) {
			CountDetail detail = new CountDetail();
			Bin bin = load(Bin.class, id);
			detail.setCountPlan(cp);
			detail.setBin(bin);
			commonDao.store(detail);
		}
	}

	@Override
	public void deleteCountDetail(List<Long> ids) {
		for (Long id : ids) {
			CountDetail cd = load(CountDetail.class, id);
			cd.getCountPlan().getDetails().remove(cd);
			commonDao.delete(cd);
		}
	}
	
	@Override
	public void saveRegister(Long cpId, Long sku, Long bin, Double qty, Date expDate, String invStatus) {
		saveRegister(cpId, sku, bin, qty, expDate, invStatus, null, null, null);
	}

	@Override
	public void saveRegister(Long countPlanId, Long skuId, Long binId, Double qty, 
			Date expDate, String invStatus, String trackSeq, Date inboundDate, Long laborId){
		// 盘点登记：只有发行，盘点中，差异调整状态可以盘点登记，取消和关闭状态的盘点单不能盘点登记。
		CountPlan countPlan = commonDao.load(CountPlan.class, countPlanId);

		if (!EnuCountStatus.STATUS200.equals(countPlan.getStatus())
				&& !EnuCountStatus.STATUS300.equals(countPlan.getStatus())
				&& !EnuCountStatus.STATUS400.equals(countPlan.getStatus())) {
			throw new BusinessException(ExceptionConstant.ERROR_STATUS);
		}

		// 从商品盘，不能做盘点登记
		if (EnuCountMethod.FROM_SKU.equals(countPlan.getCountMethod())) {
			return;
		}
		
		// 只有存货区，不良品区的库存可以盘点
		Bin bin = commonDao.load(Bin.class, binId);
		if( !stRoles.contains(bin.getStorageType().getRole()) ){
			throw new BusinessException(bin.getBinCode() + "、棚卸不可。");
		}
		
		// LOT处理
		LotInputData inputLot = new LotInputData();
		inputLot.setProperty1(DateUtil.getStringDate(expDate, DateUtil.PURE_DATE_FORMAT));

		// Quant信息取得
		Quant quant = quantManager.getQuantInfo(skuId, inputLot);
		Sku sku = commonDao.load(Sku.class, skuId);
		List<String> paramsList = new ArrayList<String>();
		List<Object> objList = new ArrayList<Object>();
		
		StringBuffer sb = new StringBuffer("select cr.id FROM CountRecord cr WHERE cr.status < 300 AND cr.countPlan.id = :countPlantId ");
		paramsList.add("countPlantId");
		objList.add(countPlan.getId());
		
		// 批次
		sb.append(" AND cr.invInfo.quant.id = :quantId ");
		paramsList.add("quantId");
		objList.add(quant.getId());
		// 库位
		sb.append(" AND cr.invInfo.bin.id = :binId ");
		paramsList.add("binId");
		objList.add(binId);
		// 包装
		sb.append(" AND cr.invInfo.packageDetail.id = :packId ");
		paramsList.add("packId");
		objList.add(sku.getProperties().getPackageInfo().getP1000().getId());
		// 库存状态
		sb.append(" AND cr.invInfo.invStatus=:invStatus ");
		paramsList.add("invStatus");
		objList.add(invStatus);
		// 货主
		sb.append(" AND cr.invInfo.owner.id =:ownerId ");
		paramsList.add("ownerId");
		objList.add(sku.getOwner().getId());
		
		// 入库单号
		if( trackSeq != null ){
			sb.append(" AND cr.invInfo.trackSeq =:trackSeq ");
			paramsList.add("trackSeq");
			objList.add(trackSeq);
		}
		else{
			sb.append(" AND cr.invInfo.trackSeq is null ");
		}
		
		// 入库日期
		if( inboundDate != null ){
			sb.append(" AND cr.invInfo.inboundDate =:inboundDate ");
			paramsList.add("inboundDate");
			objList.add(inboundDate);
		}
		
		sb.append(" ORDER BY cr.invInfo.inboundDate DESC");// 入库日期从大到小

		List<Long> oldCountRecords = this.commonDao.findByQuery(sb.toString(),
				paramsList.toArray(new String[paramsList.size()]),
				objList.toArray(new Object[objList.size()]));

		// 没有相同的CR，新建
		CountRecord record = null;
		double deltaQty = PrecisionUtils.formatByBasePackage(qty, sku.getProperties().getPackageInfo().getP1000());// 盘点数量
		if (oldCountRecords == null || oldCountRecords.size() == 0) {
			record = new CountRecord();
			record.setCountPlan(countPlan);
			
			InventoryInfo invInfo = new InventoryInfo();
			invInfo.setOwner(sku.getOwner());
			invInfo.setQuant(quant);
			invInfo.setBin(bin);
			invInfo.setContainerSeq(null);
			invInfo.setPalletSeq(null);
			invInfo.setPackageDetail(sku.getProperties().getPackageInfo().getP1000());
			invInfo.setInboundDate(inboundDate == null ? new Date() : inboundDate);
			invInfo.setTrackSeq( trackSeq == null ? countPlan.getCode() : trackSeq );
			invInfo.setInvStatus(invStatus);
			
			record.setInvInfo(invInfo);
		}
		// 如果已经有一个相同的CR
		else {
			record = commonDao.load(CountRecord.class, oldCountRecords.get(0));
		}
		
		if( laborId != null ){
			Labor labor = commonDao.load(Labor.class, laborId);
			record.setLabor(labor);
		}
		record.setCountQuantity(record.getCountQuantity() + deltaQty);
		record.setOperator(UserHolder.getUser().getName());
		record.setOccurTime(new Date());
		record.setStatus(record.getDeltaQuantity() != null && record.getDeltaQuantity() == 0D ? EnuCountDetailStatus.STATUS200 : EnuCountDetailStatus.STATUS210);
		countPlan.getRecords().add(record);
		this.commonDao.store(record);

		// 更新库位的最后盘点日期
		Bin countBin = record.getInvInfo().getBin();
		countBin.getBinInvInfo().getBinInfo().setLastCountDate(new Date());
		commonDao.store(countBin);

		// 更新盘点单头状态
		// 如果盘点单头的状态还是发行，改为盘点中状态，如果已经是差异调整状态，则不改状态
		if (EnuCountStatus.STATUS200.equals(countPlan.getStatus())) {
			orderStatusHelper.changeStatus(countPlan, EnuCountStatus.STATUS300);
			commonDao.store(countPlan);
		}

		// 更新盘点单头的合计数量
		updateCountTotalInfo(countPlan.getId());
	}

	@Override
	public void adjust(List<Long> countRecordIds, String desc) throws Exception {
		List<InvEditData> result = new ArrayList<InvEditData>();

		for (Long id : countRecordIds) {
			CountRecord countRecord = commonDao.load(CountRecord.class, id);
			CountPlan countPlan = countRecord.getCountPlan();

			if (countRecord.getDeltaQuantity() != null && countRecord.getDeltaQuantity().doubleValue() != 0) {
				double deltaQty = PrecisionUtils.formatByBasePackage( countRecord.getDeltaQuantity(), countRecord.getInvInfo().getPackageDetail());

				// 元库位修改库存
				Inventory oldInv = inventoryHelper.getInv(countRecord.getInvInfo());
				
				// 盘亏
				if (deltaQty < 0) {
					if (oldInv == null) {
						throw new BusinessException(ExceptionConstant.INVENTORY_NOT_FOUND);
					}

					// 盘亏数 < 库存可用量
					if (oldInv.getAvailableQty().doubleValue() < deltaQty * (-1)) {
						throw new BusinessException(ExceptionConstant.CANNOT_ADJUST_AS_MORE_THAN_AVAILABLE_QTY);
					}
				}

				// 盘点库位上的库存
				InventoryInfo invInfo = new InventoryInfo();
				BeanUtils.copyProperties(invInfo, countRecord.getInvInfo());
				invInfo.setBin(countPlan.getBin());
				invInfo.setContainerSeq(countRecord.getCountPlan().getCode());

				// 修改虚拟库位库存
				Inventory inventory = inventoryHelper.addInvToBin4Count(invInfo, deltaQty * (-1), false);

				// 创建虚拟库存履历
				historyHelper.saveInvHistory(inventory, deltaQty * (-1),
						countPlan.getOrderType(), null,
						EnuInvenHisType.INVENTORY_ADJUST, countPlan.getCode(),
						id, desc, null, null);// 库存调整

				// 盘盈
				if (deltaQty > 0) {
					oldInv = inventoryHelper.addInvToBin4Count(countRecord.getInvInfo(), deltaQty, true);
				}
				// 盘亏
				else {
					inventoryHelper.removeInvWithoutAllocateQty4Count(oldInv, deltaQty * (-1));
				}

				if (oldInv != null) {
					// 创建虚拟库存履历
					historyHelper.saveInvHistory(oldInv, deltaQty,
							countPlan.getOrderType(), null,
							EnuInvenHisType.INVENTORY_ADJUST,
							countPlan.getCode(), id, desc, null, null);

					result.add(new InvEditData(oldInv.getInventoryInfo(),
							oldInv.getInventoryInfo(), deltaQty, UserHolder
									.getUser().getName(), new Date(), countPlan
									.getCode(), countPlan.getCode()));
				}
			}

			countRecord.setDescription(desc);
			countRecord.setStatus(EnuCountDetailStatus.STATUS300);// 差异确认，盘点完成
			this.commonDao.store(countRecord);

			orderStatusHelper.changeStatus(countPlan, EnuCountStatus.STATUS400);// 差异调整
		}

	}

	@Override
	public void geneRecordBill(List<Long> ids) {
		// 复盘：原盘点单关闭，释放盘点锁。
		// 针对有差异的库位，生产新的盘点单，复盘单就是按库位盘。上盘点锁。

		// -----关闭原来的盘点单，释放盘点锁----
		close(ids);

		for (Long id : ids) {
			CountPlan oldCountPlan = this.commonDao.load(CountPlan.class, id);
			Set<CountRecord> rds = oldCountPlan.getRecords();

			Map<Long, Bin> toCountBin = new HashMap<Long, Bin>();
			// 添加有差异的
			for (CountRecord rd : rds) {
				// 只有有差异的CR要加进复盘单
				if (rd.getDeltaQuantity() != null
						&& rd.getDeltaQuantity().doubleValue() != 0) {
					toCountBin.put(rd.getInvInfo().getBin().getId(), rd.getInvInfo().getBin());
				}
			}

			// 无盘点差异，直接关单。
			if (toCountBin.size() == 0) {
				continue;
			}

			// 复盘后新的盘点计划
			CountPlan newCountPlan = new CountPlan();
			newCountPlan.setWarehouse(oldCountPlan.getWarehouse());
			newCountPlan.setOrderType(oldCountPlan.getOrderType());
			newCountPlan.setPlant(oldCountPlan.getPlant());
			newCountPlan.setOwner(oldCountPlan.getOwner());
			newCountPlan.setPlanDate(new Date());
			newCountPlan.setCode(bcManager.getOrderSequence(newCountPlan.getOrderType(), newCountPlan.getWarehouse().getId()));
			newCountPlan.setCountMethod(EnuCountMethod.FROM_BIN);// 复盘单都是从库位盘
			newCountPlan.setParrentPlan(oldCountPlan);
			newCountPlan.setBlindCount(Boolean.FALSE);
			newCountPlan.setBinLock(oldCountPlan.getBinLock());
			this.commonDao.store(newCountPlan);
			orderStatusHelper.changeStatus(newCountPlan,
					EnuCountStatus.STATUS100);

			for (Long binId : toCountBin.keySet()) {
				CountDetail detail = new CountDetail();
				Bin bin = load(Bin.class, binId);
				detail.setCountPlan(newCountPlan);
				detail.setBin(bin);
				newCountPlan.getDetails().add(detail);
				commonDao.store(detail);
			}

			// 发行新的盘点单
			List<Long> newCpList = new ArrayList<Long>(1);
			newCpList.add(newCountPlan.getId());
			publish(newCpList);
		}
	}

	private void updateTotalInfo(Long cpId) {
		String hql = "select count( distinct cr.invInfo.quant.sku.id )"
				+ ",count( distinct cr.invInfo.quant.id )"
				+ ",coalesce(sum(cr.invBaseQty * cr.invInfo.quant.sku.grossWeight),0)"
				+ ",coalesce(sum(cr.invBaseQty * cr.invInfo.quant.sku.volume),0) "
				+ "from CountRecord cr where cr.countPlan.id=:cpId";
		
		Object[] totalInfos = (Object[])commonDao.findByQueryUniqueResult(hql, new String[]{"cpId"}, new Object[]{cpId});
		if( totalInfos != null ){
			CountPlan cp = commonDao.load(CountPlan.class, cpId);
			cp.setSkuCount((Long)totalInfos[0]);
			cp.setQuantCount((Long)totalInfos[1]);
			cp.setSumWeight((Double)totalInfos[2]);
			cp.setSumVolume((Double)totalInfos[3]);
			commonDao.store(cp);
		}
	}

	private void updateCountTotalInfo(Long cpId) {
		// 盘点数
		commonDao.executeByNamed(NamedHqlConstant.UPDATE_COUNT_QTY, new String[] { "cpId" }, new Object[] { cpId });
		// 盘亏
		commonDao.executeByNamed(NamedHqlConstant.UPDATE_MINUS_DELTA_QTY, new String[] { "cpId" }, new Object[] { cpId });
		// 盘盈
		commonDao.executeByNamed(NamedHqlConstant.UPDATE_PLUS_DELTA_QTY, new String[] { "cpId" }, new Object[] { cpId });
	}

	@Override
	public List<InvEditData> inventoryConfirm(List<Long> countRecordIds, String desc){
		List<InvEditData> result = new ArrayList<InvEditData>(countRecordIds.size());

		for (Long id : countRecordIds) {
			Inventory virInv = this.commonDao.load(Inventory.class, id);

			String hql = "FROM CountPlan cp WHERE cp.code=:cpCode AND cp.warehouse.id = :whId";
			CountPlan cp = (CountPlan) commonDao.findByQueryUniqueResult(hql,
					new String[] { "cpCode", "whId" }, 
					new Object[] { virInv.getContainerSeq(), virInv.getWh().getId() });

			cp.setDescription(desc);
			commonDao.store(cp);

			historyHelper.saveInvHistory(virInv, virInv.getBaseQty() * (-1),
					cp.getOrderType(), null, EnuInvenHisType.COUNT_ADJUST,
					virInv.getContainerSeq(), null, desc, null, null);

			result.add(new InvEditData(virInv.getInventoryInfo(), virInv
					.getInventoryInfo(), virInv.getBaseQty() * (-1), UserHolder
					.getUser().getName(), new Date(), desc, cp.getCode()));

			inventoryHelper.removeInvWithoutAllocateQty4Count(virInv, virInv.getBaseQty());
		}

		return result;
	}

	private List<Long> getCycleCountBins(Long whId) {

		String hql = "select rule.id from CycleCountRule rule where rule.wh.id = :whId order by rule.priority";

		List<Long> ruleIds = commonDao.findByQuery(hql, "whId", whId);
		if (ruleIds == null || ruleIds.size() == 0) {
			throw new BusinessException(ExceptionConstant.CANNOT_FIND_RULE);
		}
		Map<Long, Long> binIdMap = new HashMap<Long, Long>();

		Date now = new Date();
		for (Long ruleId : ruleIds) {
			CycleCountRule rule = commonDao.load(CycleCountRule.class, ruleId);

			List<Bin> bins = new ArrayList<Bin>();
			if (rule.getBin() != null) {
				bins.add(rule.getBin());
			} else if (rule.getBg() != null) {
				bins.addAll(rule.getBg().getBins());
			} else if (rule.getSt() != null) {
				String stHql = "FROM Bin bin WHERE bin.storageType.id = :stId";
				bins.addAll(commonDao.findByQuery(stHql, "stId", rule.getSt()
						.getId()));
			} else if (rule.getWa() != null) {
				String waHql = "FROM Bin bin WHERE bin.storageType.wa.id = :waId";
				bins.addAll(commonDao.findByQuery(waHql, "waId", rule.getWa()
						.getId()));
			}

			for (Bin bin : bins) {
				// 最后盘点日为空，直接进入这次的盘点计划
				if (bin.getBinInvInfo().getBinInfo().getLastCountDate() == null
						&& !binIdMap.containsKey(bin.getId())) {
					binIdMap.put(bin.getId(), bin.getId());
				}
				// 最后盘点日到现在的日数>=盘点周期，进入这次的盘点计划
				else if (bin.getBinInvInfo().getBinInfo().getLastCountDate() != null
						&& getDays(now, bin.getBinInvInfo().getBinInfo()
								.getLastCountDate()) >= rule.getPeriod()
						&& !binIdMap.containsKey(bin.getId())) {
					binIdMap.put(bin.getId(), bin.getId());
				}
			}
		}

		if (binIdMap.isEmpty()) {
			return null;
		}

		List<Long> binIds = new ArrayList<Long>();
		binIds.addAll(binIdMap.keySet());
		return binIds;
	}

	private static int getDays(Date now, Date lastCount) {
		return DateUtil.getDays(lastCount, now);
	}

	// 生成a个，范围在0到b-1之间的随机数,不重复
	private static int[] getRandomArray(int a, int b) {
		int[] result = new int[a];
		int count = 0;

		while (count < a) {
			Random rdm = new Random();
			int intRd = Math.abs(rdm.nextInt()) % b;
			int flag = 0;
			for (int i = 0; i < count; i++) {
				if (result[i] == intRd) {
					flag = 1;
					break;
				}
			}
			if (flag == 0) {
				result[count] = intRd;
				count++;
			}
		}
		return result;
	}

	private List<CountDetail> getSampleRatio(Set<CountDetail> countDetails, Double sampleRatio) {
		CountDetail[] oldCountDetails = countDetails.toArray(new CountDetail[countDetails.size()]);

		List<CountDetail> result = null;
		if (sampleRatio == null || sampleRatio.doubleValue() >= 100D
				|| sampleRatio.doubleValue() <= 0D) {
			result = Arrays.asList(oldCountDetails);
			return result;
		}

		int b = countDetails.size();
		int a = (int) (b * sampleRatio.intValue()) / 100;

		int[] inxs = getRandomArray(a, b);

		result = new ArrayList<CountDetail>(a);
		for (int inx : inxs) {
			result.add(oldCountDetails[inx]);
		}

		return result;
	}

	@Override
	public void closeCountRecord(List<Long> ids) {
		for (Long id : ids) {
			CountRecord cr = commonDao.get(CountRecord.class, id);

			if (!EnuCountDetailStatus.STATUS200.equals(cr.getStatus())) {
				throw new BusinessException(ExceptionConstant.ERROR_STATUS);
			}

			cr.setStatus(EnuCountDetailStatus.STATUS300);
			commonDao.store(cr);
		}
	}

	@Override
	public void newSkuBin(CountPlan cp) {
		save(cp);
	}

	@Override
	public void newCircle(CountPlan cp) {
		cp.setCountMethod(EnuCountMethod.CYCLE);
		save(cp);
	}

	@Override
	public void newDynamic(CountPlan cp) {
		cp.setDynamicCountFrom(new Date());
		cp.setDynamicCountTo(new Date());
		cp.setCountMethod(EnuCountMethod.DYNAMIC);
		save(cp);
		
		// 通过库存履历找到期间内有库存变化的库位
		List<Object> binIds = null;
		List<String> paramNames = new ArrayList<String>();
		List<Object> params = new ArrayList<Object>();
		
		StringBuffer sb = new StringBuffer("select distinct 1, his.invInfo.bin.id, his.invInfo.quant.sku.id  from InventoryHistory his ");
		sb.append(" where his.wh.id = :whId ");
		sb.append(" and to_char(his.operateTime,'YYYY-MM-DD') >= :dateFrom ");
		sb.append(" and to_char(his.operateTime,'YYYY-MM-DD') <= :dateTo ");
		sb.append(" and his.hisType in ('900') and his.orderSeq is not null ");// 发货的库存移动
		sb.append(" and his.invInfo.bin.storageType.role in (:stRoles) ");// 只有拣货区,存货区，不良品区参与动碰
		
		paramNames.add("whId");
		paramNames.add("dateFrom");
		paramNames.add("dateTo");
		paramNames.add("stRoles");
		params.add(cp.getWarehouse().getId());
		params.add(DateUtil.getStringDate(cp.getDynamicCountFrom(), DateUtil.HYPHEN_DATE_FORMAT));
		params.add(DateUtil.getStringDate(cp.getDynamicCountTo(), DateUtil.HYPHEN_DATE_FORMAT));
		params.add(stRoles);
		
		if( cp.getSt() != null ){
			sb.append(" and his.invInfo.bin.storageType.id = :stId ");
			paramNames.add("stId");
			params.add(cp.getSt().getId());
		}
		
		if( cp.getOwner() != null ){
			sb.append(" and his.invInfo.owner.id = :ownerId ");
			paramNames.add("ownerId");
			params.add(cp.getOwner().getId());
		}
		
		binIds = commonDao.findByQuery(sb.toString(), paramNames.toArray(new String[paramNames.size()]), params.toArray(new Object[params.size()]));
		if( binIds == null || binIds.size() == 0 )
			throw new BusinessException("荷動きがあったロケーションが見つかりません。");
		
		// 建CountDetail
		for (Object binInfos : binIds) {
			Object[] binInfoObjs = (Object[])binInfos;
			Long binId = (Long)binInfoObjs[1];
			Long skuId = (Long)binInfoObjs[2];
			
			Bin countBin = commonDao.load(Bin.class, binId);
			Sku countSku = commonDao.load(Sku.class, skuId);
			CountDetail countDetail = new CountDetail();
			countDetail.setCountPlan(cp);
			countDetail.setBin(countBin);
			countDetail.setSku(countSku);
			cp.getDetails().add(countDetail);
			commonDao.store(countDetail);
		}
	}
	
	@Override
	public void newAll(CountPlan cp) {
		cp.setCountMethod(EnuCountMethod.ALL);
		save(cp);
		
		// 通过库存履历找到期间内有库存变化的库位
		List<Long> binIds = null;
		List<String> paramNames = new ArrayList<String>();
		List<Object> params = new ArrayList<Object>();
		
		StringBuffer sb = new StringBuffer("select distinct inv.bin.id from Inventory inv ");
		sb.append(" where inv.wh.id = :whId ");
		sb.append(" and inv.bin.storageType.role in (:stRoles) ");
		
		paramNames.add("whId");
		paramNames.add("stRoles");
		params.add(cp.getWarehouse().getId());
		params.add(stRoles);
		
		if( cp.getSt() != null ){
			sb.append(" and inv.bin.storageType.id = :stId ");
			paramNames.add("stId");
			params.add(cp.getSt().getId());
		}
		
		if( cp.getOwner() != null ){
			sb.append(" and inv.owner.id = :ownerId ");
			paramNames.add("ownerId");
			params.add(cp.getOwner().getId());
		}
		
		binIds = commonDao.findByQuery(sb.toString(), paramNames.toArray(new String[paramNames.size()]), params.toArray(new Object[params.size()]));
		if( binIds == null || binIds.size() == 0 )
			throw new BusinessException("在庫があるロケーションが見つかりません。");
		
		// 建CountDetail
		for (Long binId : binIds) {
			Bin countBin = commonDao.load(Bin.class, binId);
			CountDetail countDetail = new CountDetail();
			countDetail.setCountPlan(cp);
			countDetail.setBin(countBin);
			cp.getDetails().add(countDetail);
			commonDao.store(countDetail);
		}
	}

	@Override
	public void addAllBin(CountPlan cp) {
		if( cp.isNew() ){
			newSkuBin(cp);
		}
		
		CountPlan countPlan = commonDao.load(CountPlan.class, cp.getId());

		if (!EnuCountStatus.STATUS100.equals(countPlan.getStatus())) {
			throw new BusinessException(ExceptionConstant.ERROR_STATUS);
		}

		StringBuffer sb = new StringBuffer("select bin.id from Bin bin where bin.wh.id = :whId");// 同仓库
		sb.append(" and bin.lockStatus is null ");// 无锁
		sb.append(" and bin.storageType.role in (:stRoles) ");// 存货区，不良品区
		sb.append(" and exists ( select 1 from Inventory inv where inv.bin.id = bin.id and inv.owner.id = :ownerId ) ");// 只选择有库存的库位

		List<Long> bins = commonDao.findByQuery(sb.toString(), 
				new String[]{"whId", "stRoles", "ownerId"}, 
				new Object[]{countPlan.getWarehouse().getId(), stRoles, countPlan.getOwner().getId()});
		if (bins == null || bins.size() == 0) {
			throw new BusinessException(ExceptionConstant.CANNOT_FIND_BIN_FOR_COUNT);
		}

		saveBinDetail(bins, cp.getId());
	}

	@Override
	public Long getUnCountPlanCount(Long whId) {
		StringBuffer hql = new StringBuffer(
				" select count(id) from CountPlan cp where 1=1 ");
		hql.append(" and cp.warehouse.id = :whId");
		hql.append(" and cp.status in (200,300) ");

		return (Long) commonDao.findByQueryUniqueResult(hql.toString(),
				new String[] { "whId" }, new Object[] { whId });
	}

}
