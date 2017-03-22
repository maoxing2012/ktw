package com.core.scpwms.server.service.common.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.tags.Param;

import com.core.business.exception.BusinessException;
import com.core.business.model.domain.CreateInfo;
import com.core.business.model.security.BussinessPermission;
import com.core.business.utils.DoubleUtil;
import com.core.business.web.security.UserHolder;
import com.core.db.server.service.pojo.DefaultBaseManager;
import com.core.scpview.client.utils.StringUtils;
import com.core.scpwms.server.constant.ExceptionConstant;
import com.core.scpwms.server.constant.WmsConstant;
import com.core.scpwms.server.domain.BinInfo;
import com.core.scpwms.server.domain.InventoryInfo;
import com.core.scpwms.server.domain.PutawayRuleInfo;
import com.core.scpwms.server.enumerate.EnuInvStatus;
import com.core.scpwms.server.enumerate.EnuPARBePallet;
import com.core.scpwms.server.enumerate.EnuPARBinSelectPriority;
import com.core.scpwms.server.enumerate.EnuProcessDetailType;
import com.core.scpwms.server.enumerate.EnuProcessType;
import com.core.scpwms.server.enumerate.EnuPutawayDocStatus;
import com.core.scpwms.server.enumerate.EnuStoreRole;
import com.core.scpwms.server.enumerate.EnuWarehouseOrderStatus;
import com.core.scpwms.server.enumerate.EnuWtProcessType;
import com.core.scpwms.server.filter.WarehouseHolder;
import com.core.scpwms.server.model.common.OrderType;
import com.core.scpwms.server.model.common.PackageDetail;
import com.core.scpwms.server.model.common.Sku;
import com.core.scpwms.server.model.inventory.Inventory;
import com.core.scpwms.server.model.putaway.PutawayDoc;
import com.core.scpwms.server.model.putaway.PutawayDocDetail;
import com.core.scpwms.server.model.rules.PutAwayRule;
import com.core.scpwms.server.model.task.WarehouseOrder;
import com.core.scpwms.server.model.task.WarehouseTask;
import com.core.scpwms.server.model.warehouse.Bin;
import com.core.scpwms.server.service.common.AllocateHelper;
import com.core.scpwms.server.service.common.BCManager;
import com.core.scpwms.server.service.common.InventoryHelper;
import com.core.scpwms.server.service.common.OrderStatusHelper;
import com.core.scpwms.server.util.MessageUtil;
import com.core.scpwms.server.util.PrecisionUtils;
import com.sun.org.apache.xpath.internal.operations.Bool;

/**
 * 分配实现类
 * 
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 */
@SuppressWarnings("all")
public class AllocateHelperImpl extends DefaultBaseManager implements
		AllocateHelper {

	/** 仓库ID 、多仓库的分配可以并行进行 */
	private Long whId;

	public BCManager bcManager;

	public InventoryHelper invHelper;

	private OrderStatusHelper orderStatusHelper;

	public InventoryHelper getInvHelper() {
		return invHelper;
	}

	public void setInvHelper(InventoryHelper invHelper) {
		this.invHelper = invHelper;
	}

	public BCManager getBcManager() {
		return bcManager;
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
	
	/**
	 * For ASUS
	 */
	@Override
	public List<Long> allocateMoveWt(Long whId, String containerSeq) {
		this.whId = whId;
		
		// 仓库ID 、多仓库的分配可以并行进行
		synchronized (this.whId) {
			String hql = " from Inventory inv where inv.wh.id = :whId and inv.container.containerSeq = :containerSeq ";
			List<Inventory> invs = commonDao.findByQuery(hql, new String[]{"whId","containerSeq"}, new Object[]{whId, containerSeq});
			
			if( invs == null || invs.size() == 0 ){
				throw new BusinessException("关联的库存已经不存在。");
			}
			
			Boolean isMixLot = Boolean.FALSE;//批次不混
			Boolean isMixSku = Boolean.FALSE;//商品不混
			
			Long skuId = invs.get(0).getQuantInv().getQuant().getSku().getId();
			Long quantId = invs.get(0).getQuantInv().getQuant().getId();
			Bin srcBin = invs.get(0).getBin();
			String invStatus = invs.get(0).getStatus();
			
			for( Inventory inv : invs ){
				if( EnuInvStatus.FREEZE.equals(inv.getStatus()) ){
					throw new BusinessException("冻结的库存不能移位。");
				}
				
				if( inv.getQueuedQty() != null && inv.getQueuedQty().doubleValue() > 0 ){
					throw new BusinessException("有部分库存已经被占用，不能移位。");
				}
				
				if( !skuId.equals(inv.getQuantInv().getQuant().getSku().getId()) ){
					isMixLot = Boolean.TRUE;//批次混
					isMixSku = Boolean.TRUE;//商品混
					throw new BusinessException("混批次物料，不支持库位推荐。");
				}
				
				if( !quantId.equals(inv.getQuantInv().getQuant().getId()) ){
					isMixLot = Boolean.TRUE;//批次混
					throw new BusinessException("混批次物料，不支持库位推荐。");
				}
			}
			
			String wtType = null;
			if( srcBin != null && EnuStoreRole.R1000.equals(srcBin.getStorageType().getRole()) ){
				wtType = EnuWtProcessType.ASN_PUTAWAY;
			}
			
			// 目前的库位推荐只支持上架移位
			if( EnuWtProcessType.ASN_PUTAWAY.equals(wtType) ){
//				// 如果不是混商品的，良品，该商品在仓库中也没有库存，优先去拣货区。
//				// 找空的拣货库位，按照上架序（没有上架序，用库位编号从小到大）
//				// 找到了分配，没有找到去存货
//				if( isMixSku != null && !isMixSku.booleanValue() && EnuInvStatus.AVAILABLE.equals(invStatus) ){
//					String hqlInv = " select id from Inventory inv where inv.bin.storageType.role in ('R2000', 'R3000') and inv.wh.id = :whId and inv.quantInv.quant.sku.id = :skuId and inv.baseQty > 0";
//					List<Long> oldInvs = commonDao.findByQuery(hqlInv, new String[]{"whId", "skuId"}, new Object[]{whId, skuId});
//					
//					// 该商品在拣货区和存货区都没有库存
//					if( oldInvs == null || oldInvs.size() == 0 ){
//						// 拣货库位的最后存放不能有相同商品的
//						String lastStoreHql = " select bin.id from Bin bin where bin.storageType.role in ('R2000') and bin.wh.id = :whId and bin.binInvInfo.binInfo.lastSku = :skuId and (bin.binInvInfo.binInfo.currentQty + bin.binInvInfo.binInfo.queuedQty) > 0";
//						List<Long> lastStoreBinIds = commonDao.findByQuery(lastStoreHql, new String[]{"whId", "skuId"}, new Object[]{whId, skuId});
//						
//						// 说明也没有其他同商品的待上架任务
//						if( lastStoreBinIds == null || lastStoreBinIds.size() == 0 ){
//							
//							StringBuffer jHql = new StringBuffer("from Bin bin where 1=1");
//							jHql.append(" and bin.storageType.role in ('R2000') ");// 拣货区
//							jHql.append(" and bin.disabled = false ");// 生效
//							jHql.append(" and (bin.lockStatus is null or bin.lockStatus = 'LOCK_T2' )");// 没有锁或只有出库锁
//							jHql.append(" and bin.binInvInfo.binInfo.currentQty = 0 and bin.binInvInfo.binInfo.queuedQty = 0 ");// 当前库存数是0，待上架数是0
//							
//							// 如果是混放批次的，要求这个库位是能混放批次的限制
//							if( isMixLot != null && isMixLot.booleanValue() ){
//								jHql.append(" and bin.processProperties.lotMixed = true ");
//							}
//							jHql.append(" and bin.wh.id = :whId");// 仓库
//							
//							jHql.append(" order by bin.putawayIndex, bin.binCode");// 按照上架序（没有上架序，用库位编号从小到大）
//							
//							List<Bin> jBins = commonDao.findByQuery(jHql.toString(), "whId", whId);
//							if( jBins != null && jBins.size() > 0 ){
//								List<Long> newWtIds = allocate4Container(invs, jBins.get(0), containerSeq, wtType);
//								if( newWtIds != null && newWtIds.size() > 0 ){
//									return newWtIds;
//								}
//							}
//						}
//					}
//				}
//				
				// 不良品，优先去不良品库区找库位
				if( !EnuInvStatus.AVAILABLE.equals(invStatus) ){
					List<String> paramNames = new ArrayList<String>();
					List<Object> params = new ArrayList<Object>();
					
					StringBuffer jHql = new StringBuffer("from Bin bin where 1=1");
					jHql.append(" and bin.storageType.role in ('R4040') ");// 不良品区
					jHql.append(" and bin.disabled = false ");// 生效
					jHql.append(" and (bin.lockStatus is null or bin.lockStatus = 'LOCK_T2' )");// 没有锁或只有出库锁
					jHql.append(" and bin.properties.isPalletBin = true");//必须是托盘库位
					jHql.append(" and bin.properties.palletCount > ( bin.binInvInfo.binInfo.currentPallet + bin.binInvInfo.binInfo.queuedPallet ) ");// 可放托盘数 > 已有托盘数 + 待上架托盘数
					
					// 如果是混放商品的，要求这个库位是能混放商品
					if( isMixLot != null && isMixLot.booleanValue() ){
						jHql.append(" and bin.processProperties.skuMixed = true ");
					}
					// 如果是不混商品的，要求这个库位是能混放商品或者（不能混商品，并且最后存放商品为空或者和当前商品一致）
					else{
						jHql.append(" and (bin.processProperties.skuMixed = true "
								+ " or (bin.processProperties.skuMixed = false "
								+ "and (bin.binInvInfo.binInfo.lastSku is null or bin.binInvInfo.binInfo.lastSku = :skuId))) ");
						paramNames.add("skuId");
						params.add(skuId);
					}
					
					// 如果是混放批次的，要求这个库位是能混放批次
					if( isMixLot != null && isMixLot.booleanValue() ){
						jHql.append(" and bin.processProperties.lotMixed = true ");
					}
					// 如果是不混批次的，要求这个库位是能混放批次或者（不能混批次，并且最后存放批次为空或者和当前批次一致）
					else{
						jHql.append(" and (bin.processProperties.lotMixed = true "
								+ " or (bin.processProperties.lotMixed = false "
								+ "and ( bin.binInvInfo.binInfo.lastLotInfo is null or bin.binInvInfo.binInfo.lastLotInfo = :lotId ))) ");
						paramNames.add("lotId");
						params.add(quantId);
					}
					
					jHql.append(" and bin.wh.id = :whId");// 仓库
					paramNames.add("whId");
					params.add(whId);
					jHql.append(" order by bin.putawayIndex, bin.binCode");// 按照上架序（没有上架序，用库位编号从小到大）
					
					List<Bin> blpBin = commonDao.findByQuery(jHql.toString(), paramNames.toArray(new String[paramNames.size()]), params.toArray(new Object[params.size()]));
					if( blpBin != null && blpBin.size() > 0 ){
						List<Long> newWtIds = allocate4Container(invs, blpBin.get(0), containerSeq, wtType);
						if(newWtIds != null && newWtIds.size() > 0){
							return newWtIds;
						}
					}
				}
				
				// 以上找不到库位，都去存货区
				List<String> paramNames = new ArrayList<String>();
				List<Object> params = new ArrayList<Object>();
//				Bin oldStockBin = oldStockBin(skuId, whId);
				
				StringBuffer jHql = new StringBuffer("from Bin bin where 1=1");
				jHql.append(" and bin.storageType.role in ('R3000') ");// 存货区
				jHql.append(" and bin.disabled = false ");// 生效
				jHql.append(" and (bin.lockStatus is null or bin.lockStatus = 'LOCK_T2' )");// 没有锁或只有出库锁
				jHql.append(" and bin.properties.isPalletBin = true");//必须是托盘库位
				jHql.append(" and bin.properties.palletCount > ( bin.binInvInfo.binInfo.currentPallet + bin.binInvInfo.binInfo.queuedPallet ) ");// 可放托盘数 > 已有托盘数 + 待上架托盘数
				
				// 同商品
				jHql.append(" and (bin.binInvInfo.binInfo.lastSku is null or bin.binInvInfo.binInfo.lastSku = :skuId)" );
				paramNames.add("skuId");
				params.add(skuId);
				
				// 同批次
				jHql.append("and ( bin.binInvInfo.binInfo.lastLotInfo is null or bin.binInvInfo.binInfo.lastLotInfo = :lotId ) ");
				paramNames.add("lotId");
				params.add(quantId);
				
				jHql.append(" and bin.wh.id=:whId ");// 仓库
				paramNames.add("whId");
				params.add(whId);
				jHql.append(" order by bin.putawayIndex, bin.binCode");// 按照上架序（没有上架序，用库位编号从小到大）
				
				List<Bin> blpBin = commonDao.findByQuery(jHql.toString(), paramNames.toArray(new String[paramNames.size()]), params.toArray(new Object[params.size()]));
				if( blpBin != null && blpBin.size() > 0 ){
					List<Long> newWtIds = allocate4Container(invs, blpBin.get(0), containerSeq, wtType);
					if(newWtIds != null && newWtIds.size() > 0){
						return newWtIds;
					}
				}
			}
		}
		
		return null;
	}
	
	private Bin oldStockBin( Long skuId, Long whId ){
		// 找这个物料在拣货位上的库位
		StringBuffer hql1 = new StringBuffer( " from Bin bin where 1=1");
		hql1.append(" and bin.disabled = false ");// 生效
		hql1.append(" and (bin.lockStatus is null or bin.lockStatus = 'LOCK_T2' )");// 没有锁或只有出库锁
		hql1.append(" and bin.storageType.role in ('R2000')");//拣货区
		hql1.append(" and bin.binInvInfo.binInfo.lastSku = :skuId ");//最后存放
		hql1.append(" and (bin.binInvInfo.binInfo.currentQty + bin.binInvInfo.binInfo.queuedQty) > 0 ");//有库存或者有待上架库存
		hql1.append(" and bin.wh.id = :whId ");//仓库
		hql1.append(" order by bin.putawayIndex, bin.binCode");//按照上架序（没有上架序，用库位编号从小到大）
		
		List<Bin> bins = commonDao.findByQuery(hql1.toString(), new String[]{"skuId","whId"}, new Object[]{skuId, whId});
		if( bins != null && bins.size() > 0 ){
			return bins.get(0);
		}
		
		// 如果没有找在存货库区上的库位
		StringBuffer hql2 = new StringBuffer( " from Bin bin where 1=1");
		hql2.append(" and bin.disabled = false ");// 生效
		hql2.append(" and (bin.lockStatus is null or bin.lockStatus = 'LOCK_T2' )");// 没有锁或只有出库锁
		hql2.append(" and bin.storageType.role in ('R3000')");//存货库区
		hql1.append(" and (bin.binInvInfo.binInfo.currentQty + bin.binInvInfo.binInfo.queuedQty) > 0 ");//有库存或者有待上架库存
		hql2.append(" and bin.binInvInfo.binInfo.lastSku = :skuId ");//最后存放
		hql2.append(" and bin.wh.id = :whId ");//仓库
		hql2.append(" order by bin.putawayIndex, bin.binCode");//按照上架序（没有上架序，用库位编号从小到大）
		
		List<Bin> bins2 = commonDao.findByQuery(hql2.toString(), new String[]{"skuId","whId"}, new Object[]{skuId, whId});
		if( bins2 != null && bins2.size() > 0 ){
			return bins2.get(0);
		}
		return null;
	}
	
	public List<Long> allocate4Container( List<Inventory> invs, Bin bin, String containerSeq, String processType ) {
		List<Long> wtIds = new ArrayList<Long>(invs.size());
		
		for( Inventory inv : invs ){
			WarehouseTask task = new WarehouseTask(inv.getId());
			task.setWh(inv.getWh());
//			task.setTaskSequence(bcManager.getTaskSeq(task.getWh().getCode()));
			task.setInvInfo(inv.getInventoryInfo());
			task.setDescBin(bin);
			task.setDescContainerSeq(containerSeq);
			task.setProcessType(processType);
			task.addPlanQty(inv.getBaseQty());
			task.setCreateInfo(new CreateInfo(UserHolder.getUser()));
			this.commonDao.store(task);
			wtIds.add(task.getId());
			orderStatusHelper.changeStatus(task, EnuWarehouseOrderStatus.PUBLISH);
			// 占库存，刷新目标库位的最后存放
			invHelper.allocateInvByPutaway(inv, bin, task.getPlanQty());
			// 刷新库位的待上架数量
			bin.getBinInvInfo().addQueuedInvInfoNoPallet(task.getInvInfo(),task.getPlanQty());
			
			// 如果是补货上架的话，在目标库位上-分配数
			if( EnuWtProcessType.RP_PUTAWAY.equals(processType) ){
				InventoryInfo descInvInfo = new InventoryInfo();
				try{
					BeanUtils.copyProperties(descInvInfo, inv.getInventoryInfo());
				}catch( Exception e ){
					throw new BusinessException(ExceptionConstant.SYSTEM_EXCEPTION);
				}
				descInvInfo.setBin(bin);
				descInvInfo.setContainerSeq(containerSeq);
				Inventory descInv = invHelper.getInv(descInvInfo);
				if( descInv == null ){
					descInv = invHelper.createZeroInventory(descInvInfo);
				}
				invHelper.removeAllocateQty(descInv, inv.getBaseQty());
			}
		}
		
		if( wtIds.size() > 0 ){
			// 刷新库位的待上架托盘数
			bin.getBinInvInfo().addQueuedPallet(1D);
			this.commonDao.store(bin);
		}
		
		return wtIds.size() == 0 ? null : wtIds;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.common.AllocateHelper#allocatePutawayDoc
	 * (java.util.List)
	 */
	@Override
	public void allocatePutawayDoc(List<Long> ids) {

		whId = WarehouseHolder.getWarehouse().getId();

		// 仓库ID 、多仓库的分配可以并行进行
		synchronized (whId) {
			List<String[]> messages = new ArrayList<String[]>();

			// 单条操作, 便于消息提示
			Long id = ids.get(0);

			// 上架单据取得
			PutawayDoc doc = this.commonDao.load(PutawayDoc.class, id);

			// 只有发行和分配中状态可以自动分配
			if (!EnuPutawayDocStatus.PUBLISH.equals(doc.getStatus())
					&& !EnuPutawayDocStatus.ALLOCATING.equals(doc.getStatus())) {
				throw new BusinessException(ExceptionConstant.ERROR_STATUS);
			}

			String putAwayType = null; // 上架 类型

			OrderType invOdt = doc.getOrderType();
			if (invOdt != null && invOdt.getCode() != null) {
				// * 收货上架单
				if (invOdt.getCode().equals(WmsConstant.PUTAWAY_ASN)) {
					putAwayType = EnuWtProcessType.ASN_PUTAWAY;
				}
				// * 质检上架单
				else if (invOdt.getCode().equals(WmsConstant.PUTAWAY_QC)) {
					putAwayType = EnuWtProcessType.QC_PUTAWAY;
				}
				// * 移位上架单
				else if (invOdt.getCode().equals(WmsConstant.PUTAWAY_MOV)) {
					putAwayType = EnuWtProcessType.MV_PUTAWAY;
				}
				// * 加工成品上架单
				else if (invOdt.getCode().equals(WmsConstant.PUTAWAY_PRO)) {
					putAwayType = EnuWtProcessType.PRO_PUTAWAY;
				}
				// * 退拣上架单
				else if (invOdt.getCode().equals(WmsConstant.PUTAWAY_OBD)) {
					putAwayType = EnuWtProcessType.RV_PUTAWAY;
				}
				// * 包装上架单
				else if (invOdt.getCode().equals("")) {
					// TODO
				}

			}

			// 分配执行 -- 非托盘
			messages.addAll(doAllocateDocNotPallet(doc, putAwayType));

			// 分配执行 -- 托盘
			// doAllocateDocWithPallet(doc, putAwayType);

			if (messages.size() > 0) {
				updateStatusBar(
						ExceptionConstant.HAS_DETAIL_NOT_ALL_ALLOCATE_BIN_SEE_DETAIL,
						MessageUtil.formatDetailMes(messages), Boolean.TRUE);
			}

		}
	}

	/**
	 * 分配单据明细行 托盘 统计单据行上托盘数量, 并制作循环标准
	 * 
	 * @param doc
	 */
	public void doAllocateDocWithPallet(PutawayDoc doc, String putAwayType) {
		// 统计托盘号和DOCDetail的映射关系
		Set<String> pallets = new HashSet<String>();
		Map<String, List<Long>> palletWithDocDetail = new HashMap<String, List<Long>>();

		for (PutawayDocDetail detail : doc.getDetails()) {
			if (detail.hasNotAllocate()
					&& !StringUtils.isEmpty(detail.getInvInfo().getPalletSeq())) {
				pallets.add(detail.getInvInfo().getPalletSeq());
				if (palletWithDocDetail.get(detail.getInvInfo().getPalletSeq()) != null) {
					palletWithDocDetail.get(detail.getInvInfo().getPalletSeq())
							.add(detail.getId());
				} else {
					List<Long> ids = new ArrayList<Long>();
					ids.add(detail.getId());
					palletWithDocDetail.put(detail.getInvInfo().getPalletSeq(), ids);
				}
			} else {
				continue;
			}
		}

		// 如果有未分配的托盘、则直接托盘分配
		if (!pallets.isEmpty()) {
			for (String pallet : pallets) {

				if (palletMixed(palletWithDocDetail.get(pallet))) {
					// 混托盘处理, 当成托盘上架, 不考虑明细
					doAllocateDetailWithPallet(pallet,
							palletWithDocDetail.get(pallet), doc, true,
							putAwayType);
				} else {
					// 非混托盘、正常分配
					doAllocateDetailWithPallet(pallet,
							palletWithDocDetail.get(pallet), doc, false,
							putAwayType);
				}
			}
		}
	}

	/**
	 * 根据DOC ID, 验证托盘是否混托（SKU && LOT）
	 * 
	 * @param detailIds
	 * @return
	 */
	public boolean palletMixed(List<Long> detailIds) {

		if (detailIds.size() == 1) {
			return false;
		}

		Long quantId = null;
		for (Long id : detailIds) {
			PutawayDocDetail detail = this.commonDao.load(
					PutawayDocDetail.class, id);
			if (quantId == null) {
				quantId = detail.getInvInfo().getQuant().getId();
			}
			if (!quantId.equals(detail.getInvInfo().getQuant().getId())) {
				// Quant不同, 混托
				return true;
			}
		}

		return false;
	}

	/**
	 * 托盘处理（SKU || LOT）
	 * 
	 * @param pallet
	 * @param ids
	 * @param doc
	 */
	public void doAllocateDetailWithPallet(String pallet, List<Long> ids,
			PutawayDoc doc, boolean mixPallet, String putAwayType) {

		// 使用任意行明细进行规则取得
		PutawayDocDetail detail0 = this.commonDao.load(PutawayDocDetail.class,
				ids.get(0));
		// 获取可用规则
		List<PutAwayRule> rules = new ArrayList<PutAwayRule>();

		// 混批次, 任意取一条明细, 找策略
		if (mixPallet) {
			rules = getPutAwayRulesWithMixPallet(detail0);
		}
		// 不能混批次
		else {
			rules = getPutAwayRules(detail0, EnuPARBePallet.HASPALLET);
		}

		if (rules.size() < 1) {
			// 未找到规则
			return;
		}

		for (PutAwayRule rule : rules) {

			Map<String, Object> targetMap = getBinsWithSort(detail0, rule,
					EnuPARBePallet.HASPALLET, null);

			List<Bin> targetBins = (List<Bin>) targetMap.get("TargetBin");
			Map<Long, BinInfo> targetBinsInfos = (Map<Long, BinInfo>) targetMap
					.get("BinInfos");

			if (targetBins.isEmpty()) {
				continue;
			}

			// 直接针对行进行分配、以托盘为单位, 每托盘对应多行
			for (Long id : ids) {

				PutawayDocDetail detail = this.commonDao.load(
						PutawayDocDetail.class, id);

				Inventory inv = invHelper.getInv(detail.getInvInfo());

				WarehouseTask task = new WarehouseTask(inv.getId());
//				task.setTaskSequence(bcManager.getTaskSeq(WarehouseHolder
//						.getWarehouse().getCode()));
				task.setInvInfo(detail.getInvInfo());
				task.setWh(detail.getPutawayDoc().getWh());
				task.setRelatedBill1(detail.getPutawayDoc().getRelatedBill1());
				task.setDescBin(targetBins.get(0));
				task.setPutawayDocDetail(detail);
				// 只分配能分配的数量
				task.addPlanQty(detail.getUnAllocateQtyBase());
				task.setProcessType(putAwayType);
				task.setCreateInfo(new CreateInfo(UserHolder.getUser()));
				detail.getTasks().add(task);

				this.commonDao.store(task);
				orderStatusHelper.changeStatus(task,
						EnuWarehouseOrderStatus.PUBLISH);

				// 更新上架计划明细和单头上的分配数
				detail.allocate(task.getPlanQty());

				//
				// // 质检上架和加工成品上架, 不占分配数？？
				// if (inv != null) {
				// if
				// (!detail.getPutawayDoc().getOrderType().getCode().equals("PA_PRO")
				// &&
				// !detail.getPutawayDoc().getOrderType().getCode().equals("PA_QC"))
				// {
				// inv.allocate(task.getPlanQty());
				// this.commonDao.store(inv);
				// }
				// }

				if (id.equals(ids.get(ids.size() - 1))) {
					// 最后一个明细、刷新库位状态
					// TODO
					// targetBins.get(0).refleshLastStoreInfo(inv);
				}

				// 刷新当前缓存内的库位状态, 未来可能将该对象存储至数据库中
				// TODO
				// targetBinsInfos.get(targetBins.get(0).getId()).refleshBinInfoWithPallet();

			}
			// 只要有一条分配成功, 就中断, 因为是以托盘为单位进行分配的。
			break;

		}

	}

	/**
	 * 分配单据明细行 非托盘
	 * 
	 * @param doc
	 */
	public List<String[]> doAllocateDocNotPallet(PutawayDoc doc,
			String putAwayType) {
		List<String[]> messages = new ArrayList<String[]>();
		
		// 需要过滤掉已经在计划中的库位。
		Set<Long> withoutBinIds = new HashSet<Long>();
		for (PutawayDocDetail detail : doc.getDetails()) {
			withoutBinIds.add(detail.getInvInfo().getBin().getId());
		}

		// 循环对每行进行处理 -- 第一个循环为散件
		for (PutawayDocDetail detail : doc.getDetails()) {
			if (!detail.hasNotAllocate()) {
				// 分配完成的单据行跳过处理
				continue;
			}

			// 如果单据行没有指定原始库位, 也就是说当前货物位置未知, 此行跳过
			// 理论上不会发生这样的情况, 为了避免意外, 此处判断加上
			if (detail.getInvInfo().getBin() == null
					|| detail.getInvInfo().getBin().getId() == null) {
				continue;
			}

			// 收货码盘后, 如果托盘号存在, 则走托盘分配流程,下个循环处理
			// 此处托盘号认为是上架托盘号, 即收货码盘, 然后托盘上架
			if (!StringUtils.isEmpty(detail.getInvInfo().getPalletSeq())) {
				continue;
			}

			messages.addAll(doAllocateDetailNotPallet(detail, putAwayType, withoutBinIds));
		}

		commonDao.store(doc);

		return messages;

	}

	/**
	 * 散货分配
	 * 
	 * @param doc
	 * @param detail
	 */
	public List<String[]> doAllocateDetailNotPallet(PutawayDocDetail detail,
			String putAwayType, Set<Long> withoutBinIds) {
		List<String[]> messages = new ArrayList<String[]>();

		// 如果一个规则下面的所有库位的所有能上架的库存小于明细中产品上架的数量（没有装完）, 则使用下一个规则的库位装,
		// 直到装完或者没有可用库位为止
		double toBeMovedQty = detail.getUnAllocateQtyBase();

		// 获取可用规则
		List<PutAwayRule> rules = getPutAwayRules(detail,
				EnuPARBePallet.NOPALLET);

		if (rules.size() < 1) {
			// 找不到符合的策略
			messages.add(new String[] { ExceptionConstant.CANNOT_FIND_RULE,
					"putaway.rule", "sku.name",
					detail.getInvInfo().getQuant().getSku().getName() });
			return messages;
		}

		for (PutAwayRule rule : rules) {
			toBeMovedQty -= allocateByRuleNotPallet(detail, rule, putAwayType, withoutBinIds);
			if (toBeMovedQty <= 0) {
				break;
			}
		}

		return messages;

	}

	public double allocateByRuleNotPallet(PutawayDocDetail detail,
			PutAwayRule rule, String wtType, Set<Long> withoutBinIds) {

		// 满足条件的库位, 排序
		Map<String, Object> targetMap = getBinsWithSort(detail, rule,
				EnuPARBePallet.NOPALLET, withoutBinIds);

		List<Bin> targetBins = (List<Bin>) targetMap.get("TargetBin");

		// 累计分配数量
		double allocatedQty = 0;

		// 开始分配
		if (targetBins.isEmpty()) {
			return allocatedQty;
		}

		double toBeMovedQty = detail.getUnAllocateQtyBase(); // 基本单位数量

		// 原始库位(检查一下库存, 如果库存已经被动, 可用量小于计划数量, 报错)
		Inventory inv = invHelper.getInv(detail.getInvInfo());
		if (inv == null) {
			throw new BusinessException(ExceptionConstant.INVENTORY_NOT_FOUND);
		}

		if (PrecisionUtils.compareByBasePackage(inv.getAvaliableQuantity(),
				toBeMovedQty, detail.getInvInfo().getPackageDetail()) < 0) {
			throw new BusinessException(
					ExceptionConstant.CANNOT_ADJUST_AS_MORE_THAN_AVAILABLE_QTY);
		}

		Map<String, Double> allocateResult = doAllocateByRuleNotPallet(
				targetBins, detail, toBeMovedQty, allocatedQty, wtType, true);

		toBeMovedQty = (Double) allocateResult.get("toBeMovedQty");
		allocatedQty = (Double) allocateResult.get("allocatedQty");

		// 未分配的信息暂时保留, 后面做垂直任务分配用
		// 将未分配的信息, 分配到库位周边
		// 这里目前没有实现, 需要后期实现

		return allocatedQty;

	}

	/**
	 * 计算当前库位还能存放多少改行的包装数
	 * 
	 * @param targetBins
	 * @param targetBinsInfos
	 * @param detail
	 * @param toBeMovedQty
	 * @param allocatedQty
	 * @param wtType
	 * @return
	 */
	public synchronized Map<String, Double> doAllocateByRuleNotPallet(
			List<Bin> targetBins, PutawayDocDetail detail, double toBeMovedQty,
			double allocatedQty, String wtType, boolean isAuto) {

		Map<String, Double> allocateResult = new HashMap<String, Double>();

		// 循环对库位进行分配、此处未考虑上架次数, 比如尽量一个库位放满
		// 如果有需要、此处逻辑需要扩展
		// 设置上架方法追求最小上架次数
		// 20140508 添加库位已经存在的Task验证, 避免不许混放的空库位被分配多个SKU、LOT、SUP
		for (Bin bin : targetBins) {

			// 已经分配完毕, 跳出循环
			if (toBeMovedQty <= 0) {
				break;
			}

			// 计算这个库位剩余的库容
			double canPutAwayBaseQty = bin.getBinInvInfo().getCanPutAway(
					detail.getInvInfo().getQuant().getSku(),
					detail.getInvInfo().getPackageDetail(), isAuto);

			// 只能上架到有空位的非托盘库位
			if (canPutAwayBaseQty > 0) {

				// 占原库存的分配数, 刷新目标库位的最后存放信息
				Inventory inv = invHelper.getInv(detail.getInvInfo());
				if (inv == null) {
					throw new BusinessException(
							ExceptionConstant.INVENTORY_NOT_FOUND);
				}

				WarehouseTask task = new WarehouseTask(inv.getId());
				task.setWh(detail.getPutawayDoc().getWh());
//				task.setTaskSequence(bcManager.getTaskSeq(task.getWh()
//						.getCode()));
				task.setInvInfo(detail.getInvInfo());
				task.setRelatedBill1(detail.getPutawayDoc().getDocSequence());// 上架计划单号
				task.setRelatedBill2(detail.getPutawayDoc().getRelatedBill1());// 相关单号（ASN单号,
				// 质检单号,
				// 加工单号,
				// 移位单号等）
				task.setDescBin(bin);
				task.setDescContainerSeq(null);
				task.setDescPalletSeq(null);
				task.setPutawayDocDetail(detail);
				task.setProcessType(wtType);
				// 如果明细剩余可分配数量小于库位可分配数量, 则直接分配
				if (canPutAwayBaseQty >= toBeMovedQty) {
					task.addPlanQty(toBeMovedQty);
				}
				// 只分配能分配的数量
				else {
					task.addPlanQty(canPutAwayBaseQty);
				}

				task.setCreateInfo(new CreateInfo(UserHolder.getUser()));
				detail.getTasks().add(task);
				this.commonDao.store(task);
				orderStatusHelper.changeStatus(task,
						EnuWarehouseOrderStatus.PUBLISH);

				allocatedQty += task.getPlanQty();
				toBeMovedQty -= task.getPlanQty();

				// 更新上架单明细和单头上的分配数
				detail.allocate(task.getPlanQty());

				// 更新上架单的状态
				if (EnuPutawayDocStatus.PUBLISH.equals(detail.getPutawayDoc()
						.getStatus())
						&& detail.getPutawayDoc().getAllocateQty() > 0) {
					orderStatusHelper.changeStatus(detail.getPutawayDoc(),
							EnuPutawayDocStatus.ALLOCATING);
				}
				if (EnuPutawayDocStatus.ALLOCATING.equals(detail
						.getPutawayDoc().getStatus())
						&& detail.getPutawayDoc().getUnAllocateQty() <= 0) {
					orderStatusHelper.changeStatus(detail.getPutawayDoc(),
							EnuPutawayDocStatus.ALLOCATE);
				}

				if (PrecisionUtils.compareByBasePackage(inv
						.getAvaliableQuantity(), task.getPlanQty(), task
						.getInvInfo().getPackageDetail()) < 0) {
					throw new BusinessException(
							ExceptionConstant.CANNOT_ADJUST_AS_MORE_THAN_AVAILABLE_QTY);
				}
				invHelper.allocateInvByPutaway(inv, bin, task.getPlanQty());

				// 刷新库位的库容信息
				bin.getBinInvInfo().addQueuedInvInfoNoPallet(task.getInvInfo(),
						task.getPlanQty());
				this.commonDao.store(bin);
			}
		}

		allocateResult.put("allocatedQty", allocatedQty);
		allocateResult.put("toBeMovedQty", toBeMovedQty);

		return allocateResult;

	}

	/**
	 * 获取可能存放的库位信息并排序
	 * 
	 * @param detail
	 * @param rule
	 * @param pallet
	 * @return
	 */
	public Map<String, Object> getBinsWithSort(PutawayDocDetail detail,
			PutAwayRule rule, String pallet, Set<Long> withOutBinIds) {

		// 返回值
		Map<String, Object> targetBinInfo = new HashMap<String, Object>();
		List<Bin> resultBins = new ArrayList<Bin>();
		Map<Long, BinInfo> binInfos = new HashMap<Long, BinInfo>();

		if (rule.getDescBin() != null) {
			// 指定目标库位, 直接查询目标库位进行分配, 单库位, 无优先级判断
			Bin bin = rule.getDescBin();
			double eachCanPutAway = bin.getBinInvInfo().getCanPutAway(
					detail.getInvInfo().getQuant().getSku(),
					detail.getInvInfo().getPackageDetail(), true);
			if (eachCanPutAway > 0) {
				targetBinInfo.put("TargetBin", resultBins);
				targetBinInfo.put("BinInfos", binInfos);
			}
			// 单库位直接返回
			return targetBinInfo;
		}

		// 多库位查询
		Long skuId = detail.getInvInfo().getQuant().getSku().getId();
		Long lotId = detail.getInvInfo().getQuant().getId();

		// 边排序边进行库存存放验证, 直到能够摆满库位
		String binSe0 = rule.getOnlySameLot() != null
				&& rule.getOnlySameLot().booleanValue() ? "Y" : null;
		String binSel = rule.getBinSelPriority1();
		String binSe2 = rule.getBinSelPriority2();
		String binSe3 = rule.getBinSelPriority3();
		String binSe4 = rule.getBinSelPriority4();

		List<BigDecimal> orgBins0 = new ArrayList<BigDecimal>();
		List<BigDecimal> orgBins1 = new ArrayList<BigDecimal>();
		List<BigDecimal> orgBins2 = new ArrayList<BigDecimal>();
		List<BigDecimal> orgBins3 = new ArrayList<BigDecimal>();
		List<BigDecimal> orgBins4 = new ArrayList<BigDecimal>();
		List<BigDecimal> orgBins5 = new ArrayList<BigDecimal>();

		StringBuffer level1 = new StringBuffer();
		StringBuffer notLevel1 = new StringBuffer();
		// 判断优先级一, 系统采用逐步查询并分配的策略, 如果第一优先级分配不够, 则第二优先级查询的时候, 需要Remove掉所有的第一级别的库位

		double canAllocatedQty = 0d;
		boolean binEnough = false;

		// 只差找同批次库位
		if (binSe0 != null) {
			orgBins0 = getBinIds(binSe0, skuId, detail, rule, pallet, withOutBinIds);
			binSel = null;
			binSe2 = null;
			binSe3 = null;
			binSe4 = null;
			binEnough = true;// 无论有没有同批次的库位，都不再查找别的库位。

			// 第0次晒选（第0优先级）
			for (BigDecimal binId : orgBins0) {

				Bin bin = this.commonDao.load(Bin.class, binId.longValue());
				double eachCanPutAway = bin.getBinInvInfo().getCanPutAway(
						detail.getInvInfo().getQuant().getSku(),
						detail.getInvInfo().getPackageDetail(), true);

				if (eachCanPutAway > 0) {
					canAllocatedQty += eachCanPutAway;
					resultBins.add(bin);
					binInfos.put(bin.getId(), bin.getBinInvInfo().getBinInfo());

					if (canAllocatedQty >= detail.getUnAllocateQtyBase()) {
						break;
					}
				}
			}
		}

		if (!StringUtils.isEmpty(binSel)) {

			orgBins1 = getBinIds(binSel, skuId, detail, rule, pallet, withOutBinIds);

			// 第1次晒选（第1优先级）
			for (BigDecimal binId : orgBins1) {

				Bin bin = this.commonDao.load(Bin.class, binId.longValue());

				double eachCanPutAway = bin.getBinInvInfo().getCanPutAway(
						detail.getInvInfo().getQuant().getSku(),
						detail.getInvInfo().getPackageDetail(), true);
				if (eachCanPutAway > 0) {
					canAllocatedQty += eachCanPutAway;
					resultBins.add(bin);
					binInfos.put(bin.getId(), bin.getBinInvInfo().getBinInfo());

					if (canAllocatedQty >= detail.getUnAllocateQtyBase()) {
						binEnough = true;
						break;
					}
				}
			}
		}

		if (!binEnough) {
			// 第一顺位没有分配足
			if (!StringUtils.isEmpty(binSe2)) {

				orgBins2 = getBinIds(binSe2, skuId, detail, rule, pallet, withOutBinIds);

				orgBins2.removeAll(orgBins1);

				// 第2次晒选（第2优先级）
				for (BigDecimal binId : orgBins2) {

					Bin bin = this.commonDao.load(Bin.class, binId.longValue());
					double eachCanPutAway = bin.getBinInvInfo().getCanPutAway(
							detail.getInvInfo().getQuant().getSku(),
							detail.getInvInfo().getPackageDetail(), true);
					if (eachCanPutAway > 0) {
						canAllocatedQty += eachCanPutAway;
						resultBins.add(bin);
						binInfos.put(bin.getId(), bin.getBinInvInfo()
								.getBinInfo());

						if (canAllocatedQty >= detail.getUnAllocateQtyBase()) {
							binEnough = true;
							break;
						}
					}
				}
			}
		}

		if (!binEnough) {
			// 第1,2顺位没有分配足
			if (!StringUtils.isEmpty(binSe3)) {
				// 第3顺位有录入
				orgBins3 = getBinIds(binSe3, skuId, detail, rule, pallet, withOutBinIds);

				orgBins3.removeAll(orgBins1);
				orgBins3.removeAll(orgBins2);

				// 第3次晒选（第3优先级）
				for (BigDecimal binId : orgBins3) {

					Bin bin = this.commonDao.load(Bin.class, binId.longValue());
					double eachCanPutAway = bin.getBinInvInfo().getCanPutAway(
							detail.getInvInfo().getQuant().getSku(),
							detail.getInvInfo().getPackageDetail(), true);
					if (eachCanPutAway > 0) {
						canAllocatedQty += eachCanPutAway;
						resultBins.add(bin);
						binInfos.put(bin.getId(), bin.getBinInvInfo()
								.getBinInfo());

						if (canAllocatedQty >= detail.getUnAllocateQtyBase()) {
							binEnough = true;
							break;
						}

					}
				}
			}
		}

		if (!binEnough) {
			// 第1,2,3顺位没有分配足
			if (!StringUtils.isEmpty(binSe4)) {
				// 第4顺位有录入
				orgBins4 = getBinIds(binSe4, skuId, detail, rule, pallet, withOutBinIds);

				orgBins4.removeAll(orgBins1);
				orgBins4.removeAll(orgBins2);
				orgBins4.removeAll(orgBins3);

				// 第4次晒选（第4优先级）
				for (BigDecimal binId : orgBins4) {
					Bin bin = this.commonDao.load(Bin.class, binId.longValue());
					double eachCanPutAway = bin.getBinInvInfo().getCanPutAway(
							detail.getInvInfo().getQuant().getSku(),
							detail.getInvInfo().getPackageDetail(), true);
					if (eachCanPutAway > 0) {
						canAllocatedQty += eachCanPutAway;
						resultBins.add(bin);
						binInfos.put(bin.getId(), bin.getBinInvInfo()
								.getBinInfo());

						if (canAllocatedQty >= detail.getUnAllocateQtyBase()) {
							binEnough = true;
							break;
						}
					}
				}
			}
		}

		if (!binEnough) {
			// 第1,2,3,4顺位没有分配足
			// 所有满足条件的库位作为第五顺位
			orgBins5 = getBinIds("", skuId, detail, rule, pallet, withOutBinIds);
			orgBins5.removeAll(orgBins1);
			orgBins5.removeAll(orgBins2);
			orgBins5.removeAll(orgBins3);
			orgBins5.removeAll(orgBins4);

			// 第5次晒选（第5优先级）
			for (BigDecimal binId : orgBins5) {
				Bin bin = this.commonDao.load(Bin.class, binId.longValue());

				double eachCanPutAway = bin.getBinInvInfo().getCanPutAway(
						detail.getInvInfo().getQuant().getSku(),
						detail.getInvInfo().getPackageDetail(), true);
				if (eachCanPutAway > 0) {
					canAllocatedQty += eachCanPutAway;
					resultBins.add(bin);
					binInfos.put(bin.getId(), bin.getBinInvInfo().getBinInfo());

					if (canAllocatedQty >= detail.getUnAllocateQtyBase()) {
						binEnough = true;
						break;
					}
				}
			}
		}

		targetBinInfo.put("TargetBin", resultBins);
		targetBinInfo.put("BinInfos", binInfos);

		return targetBinInfo;

	}

	/**
	 * 获取当前商品对应的上架规则
	 * 
	 * @param detail
	 * @param bePallet
	 * @return
	 */
	public List<PutAwayRule> getPutAwayRules(PutawayDocDetail detail,
			String bePallet) {

		StringBuffer sb = new StringBuffer();
		List<String> params = new ArrayList<String>();
		List<Object> values = new ArrayList<Object>();

		Sku sku = detail.getInvInfo().getQuant().getSku();
		PackageDetail packageDetail = detail.getInvInfo().getPackageDetail();

		sb.append("from PutAwayRule rule where rule.disabled =false and rule.wh.id =:whId ");
		// 仓库限定
		params.add("whId");
		values.add(detail.getPutawayDoc().getWh().getId());

		// 原始库位限定
		sb.append(" AND (rule.srcBin IS NULL or (rule.srcBin.id=:srcBinId))  ");
		params.add("srcBinId");
		values.add(detail.getInvInfo().getBin().getId());

		// 货主限定
		sb.append(" AND (rule.plant IS NULL or (rule.plant.id=:plantId))  ");
		params.add("plantId");
		values.add(detail.getPutawayDoc().getPlant().getId());

		// SKU限定
		sb.append(" AND (rule.sku IS NULL or (rule.sku.id=:skuId))  ");
		params.add("skuId");
		values.add(sku.getId());

		// 订单类型限定
		sb.append(" AND (rule.ot IS NULL or (rule.ot.id=:iotId))  ");
		params.add("iotId");
		values.add(detail.getPutawayDoc().getOrderType().getId());

		// 库存状态
		sb.append(" AND (rule.invStatus IS NULL or (rule.invStatus=:invStatus))  ");
		params.add("invStatus");
		values.add(detail.getInvInfo().getInvStatus());

		// ABC分类
		if (detail.getInvInfo().getQuant().getSku().getAbcType() != null) {
			sb.append(" AND (rule.abcProperties IS NULL or (rule.abcProperties.id=:abcPropertiesId))  ");
			params.add("abcPropertiesId");
			values.add(detail.getInvInfo().getQuant().getSku().getAbcType()
					.getId());
		} else {
			sb.append(" AND (rule.abcProperties IS NULL )  ");
		}

		// 商品大类限定
		if (sku.getIt1000() != null) {
			sb.append(" AND (rule.bType IS NULL or (rule.bType.id=:bTypeId))  ");
			params.add("bTypeId");
			values.add(sku.getIt1000().getId());
		} else {
			sb.append(" AND (rule.bType IS NULL )  ");
		}

		// 商品中类限定
		if (sku.getIt2000() != null) {
			sb.append(" AND (rule.mType IS NULL or (rule.mType.id=:mTypeId))  ");
			params.add("mTypeId");
			values.add(sku.getIt2000().getId());
		} else {
			sb.append(" AND (rule.mType IS NULL )  ");
		}

		// 商品小类限定
		if (sku.getIt3000() != null) {
			sb.append(" AND (rule.lType IS NULL or (rule.lType.id=:lTypeId))  ");
			params.add("lTypeId");
			values.add(sku.getIt3000().getId());
		} else {
			sb.append(" AND (rule.lType IS NULL )  ");
		}

		// 品牌限定
		if (!StringUtils.isEmpty(sku.getBrandName())) {
			sb.append(" AND (rule.brandName IS NULL or rule.brandName =:brandName) ");
			params.add("brandName");
			values.add(sku.getBrandName());
		} else {
			sb.append(" AND (rule.brandName IS NULL ) ");
		}

		// 托盘限定
		sb.append(" AND (rule.ruleMain.hasPallet IS NULL or (rule.ruleMain.hasPallet=:hasPallet))  ");
		params.add("hasPallet");
		values.add(bePallet);

		// 包装组限定
		sb.append(" AND (rule.packageInfo IS NULL or (rule.packageInfo.id=:packageInfoId))  ");
		params.add("packageInfoId");
		values.add(packageDetail.getPackageInfo().getId());

		// 包装限定
		sb.append(" AND (rule.packageDetail IS NULL or (rule.packageDetail.id=:packageDetailId))  ");
		params.add("packageDetailId");
		values.add(packageDetail.getId());

		// 数量限定
		sb.append(" AND (  ");
		sb.append("      rule.ruleMain.useQtyLimit = false ");
		sb.append("      or ( (rule.ruleMain.qtyLowerLimit IS NULL or rule.ruleMain.qtyLowerLimit = 0.0 OR rule.ruleMain.qtyLowerLimit<=:planQty1 ) ");
		sb.append("  and  ");
		sb.append(" (rule.ruleMain.qtyLimit IS NULL or rule.ruleMain.qtyLimit = 0.0 OR rule.ruleMain.qtyLimit >=:planQty2 ) ");
		sb.append(" ) )  ");

		params.add("planQty1");
		params.add("planQty2");
		values.add(detail.getUnAllocateQtyBase());
		values.add(detail.getUnAllocateQtyBase());

		// 托盘数限定
		sb.append(" AND (  ");
		sb.append("      rule.ruleMain.usePalletLimit = false ");
		sb.append("      or ( (rule.ruleMain.palletQtyLowerLimit IS NULL or rule.ruleMain.palletQtyLowerLimit = 0.0 OR (:planQty3 is null) OR rule.ruleMain.palletQtyLowerLimit<=:planQty3 ) ");
		sb.append("  and  ");
		sb.append(" (rule.ruleMain.palletQtyLimit IS NULL or rule.ruleMain.palletQtyLimit = 0.0 OR (:planQty4 is null) OR rule.ruleMain.palletQtyLimit >=:planQty4 ) ");
		sb.append(" ) )  ");

		params.add("planQty3");
		params.add("planQty4");

		Double palletCount = 0.0D;

		if (detail.getInvInfo().getPackageDetail().getPackageInfo().getP3000() != null
				&& detail.getInvInfo().getPackageDetail().getPackageInfo()
						.getP3000().getCoefficient() != null
				&& detail.getInvInfo().getPackageDetail().getPackageInfo()
						.getP3000().getCoefficient().doubleValue() > 0.0D) {
			palletCount = DoubleUtil.div(detail.getUnAllocateQtyBase(), detail
					.getInvInfo().getPackageDetail().getPackageInfo()
					.getP3000().getCoefficient(), 2);
		}
		values.add(palletCount);
		values.add(palletCount);

		// 包装限定2
		sb.append(" AND (rule.packageLevel IS NULL or (rule.packageLevel=:packageLevel))  ");
		params.add("packageLevel");
		values.add(packageDetail.getPackageLevel());

		sb.append(" AND (rule.disabled = false) ");

		sb.append(" order by  rule.priority ");

		List<PutAwayRule> rules = this.commonDao.findByQuery(sb.toString(),
				(String[]) params.toArray(new String[params.size()]),
				(Object[]) values.toArray(new Object[values.size()]));

		// 批次限定 -- 目前没有实现 等待完善
		// TODO

		return rules;

	}

	/**
	 * 获取当前商品对应的上架规则
	 * 
	 * @param detail
	 * @param bePallet
	 * @return
	 */
	public List<PutAwayRule> getPutAwayRulesWithMixPallet(
			PutawayDocDetail detail) {

		StringBuffer sb = new StringBuffer();
		List<String> params = new ArrayList<String>();
		List<Object> values = new ArrayList<Object>();

		Sku sku = detail.getInvInfo().getQuant().getSku();
		PackageDetail packageDetail = detail.getInvInfo().getPackageDetail();

		sb.append("from PutAwayRule rule where rule.disabled =false and rule.wh.id =:whId ");
		// 仓库限定
		params.add("whId");
		values.add(detail.getPutawayDoc().getWh().getId());

		// 原始库位限定
		sb.append(" AND (rule.srcBin IS NULL or (rule.srcBin.id=:srcBinId))  ");
		params.add("srcBinId");
		values.add(detail.getInvInfo().getBin().getId());

		// 货主限定
		sb.append(" AND (rule.plant IS NULL or (rule.plant.id=:plantId))  ");
		params.add("plantId");
		values.add(detail.getPutawayDoc().getPlant().getId());

		// 订单类型限定
		sb.append(" AND (rule.ot IS NULL or (rule.ot.id=:iotId))  ");
		params.add("iotId");
		values.add(detail.getPutawayDoc().getOrderType().getId());

		// 品牌限定
		if (!StringUtils.isEmpty(detail.getInvInfo().getQuant().getSku()
				.getBrandName())) {
			sb.append(" AND ( rule.brandName IS NULL OR ( rule.brandName = :brandName ) ) ");
			params.add("brandName");
			values.add(detail.getInvInfo().getQuant().getSku().getBrandName());
		} else {
			sb.append(" AND rule.brandName IS NULL ");
		}

		// 托盘限定
		sb.append(" AND (rule.ruleMain.hasPallet=:hasPallet)  ");
		params.add("hasPallet");
		values.add(EnuPARBePallet.HASPALLET);

		// 库存状态
		sb.append(" AND (rule.invStatus IS NULL or (rule.invStatus=:invStatus))  ");
		params.add("invStatus");
		values.add(detail.getInvInfo().getInvStatus());

		sb.append(" order by  rule.priority ");

		List<PutAwayRule> rules = this.commonDao.findByQuery(sb.toString(),
				(String[]) params.toArray(new String[params.size()]),
				(Object[]) values.toArray(new Object[values.size()]));

		return rules;

	}
	
	private static String toString4Set( Set<Long> withoutBinIds ){
		if( withoutBinIds == null || withoutBinIds.size() == 0 )
			return "";
		
		StringBuffer sb = new StringBuffer();
		for( Long id : withoutBinIds ){
			sb.append(",").append(id.toString());
		}
		
		return sb.substring(1).toString();
	}
	
	public static void main(String[] args){
		Set<Long> setA = new HashSet<Long>();
		setA.add(1001L);
		setA.add(1002L);
		setA.add(1003L);
		setA.add(1004L);
		setA.add(1005L);
		setA.add(1006L);
		setA.add(1007L);
		
		System.out.println( toString4Set(setA) );
	}

	/**
	 * 
	 * @param binSel
	 * @param bin
	 * @return
	 */
	public List<BigDecimal> getBinIds(String binSel, Long skuId,
			PutawayDocDetail detail, PutAwayRule rule, String pallet, Set<Long> withoutBinIds) {
		// 此处不能注释，有问题请联络大群 强行叫Hibernate 在本事物中同步 start
		this.commonDao
				.findByQuery("select max(t.taskSequence) from WarehouseTask t");
		// 此处不能注释，有问题请联络大群 end

		List<BigDecimal> result = new ArrayList<BigDecimal>();

		StringBuffer executeSql = new StringBuffer();

		StringBuffer imansb = new StringBuffer();
		imansb.append(" select bbd.BIN_ID from WMS_BIN_BINGROUP bbd,WMS_BIN bin,WMS_BIN_PROPERTIES binproperties, WMS_BIN_INV_INFO binInv where 1=1 ");
		imansb.append(" and bbd.BIN_ID = bin.id ");
		imansb.append(" and bin.PROPERTIES = binproperties.id ");
		imansb.append(" and bin.BIN_INV_INFO_ID = binInv.id ");
		imansb.append(" and bbd.BIN_GROUP_ID =").append(
				rule.getDescBinGroup().getId()); // 库位组
		imansb.append(" and bin.DISABLED = 0 "); // 库位状态
		imansb.append(" and nvl(bin.LOCK_STATUS,'LOCK_T2') = 'LOCK_T2' "); // 无入锁
		imansb.append(" and bin.id <> ").append(
				detail.getInvInfo().getBin().getId()); // 不等于当前库位
		
		if( withoutBinIds != null && withoutBinIds.size() > 0 ){
			imansb.append(" and bin.id not in ( ").append(
					toString4Set(withoutBinIds)).append(") "); // 不等于不能不包含的库位
		}
		
		// 目标库位的库容<原库位的库容
		if( detail.getSmallThanSrc() != null 
				&& detail.getSmallThanSrc() 
				&& detail.getInvInfo().getBin().getProperties().getVolume() != null ){
			imansb.append(" and binproperties.volume <  ").append(
					detail.getInvInfo().getBin().getProperties().getVolume()); 
		}
		
		imansb.append(" and bin.WH_ID =").append(
				detail.getPutawayDoc().getWh().getId()); // 仓库

		// if (EnuPARBePallet.NOPALLET.equalsIgnoreCase(pallet)) {
		// imansb.append(" and NVL(binproperties.PALLET_COUNT,0) = 0 "); // 托盘限定
		// } else {
		// imansb.append(" and NVL(binproperties.PALLET_COUNT,0)> 0  "); // 托盘限定
		// }

		// 只查找同批次库位
		if (binSel != null && "Y".equals(binSel)) {
			executeSql.append("select binView.BIN_ID from ");
			executeSql.append("(");

			executeSql
					.append(" select bbd.BIN_ID as BIN_ID,bin.PUTAWAY_INDEX as PUTAWAY_INDEX, bin.code as BIN_CODE from WMS_BIN_BINGROUP bbd,WMS_BIN bin, WMS_BIN_PROPERTIES binproperties, WMS_BIN_INV_INFO binInv where 1=1 ");
			executeSql.append(" and bbd.BIN_ID = bin.id ");
			executeSql.append(" and bin.PROPERTIES = binproperties.id ");
			executeSql.append(" and bin.BIN_INV_INFO_ID = binInv.id ");
			executeSql.append(" and bbd.BIN_GROUP_ID =").append(
					rule.getDescBinGroup().getId()); // 库位组
			executeSql.append(" and bin.DISABLED = 0 "); // 库位状态
			executeSql
					.append(" and nvl(bin.LOCK_STATUS,'LOCK_T2') = 'LOCK_T2' "); // 无入锁
			executeSql.append(" and bin.id <> ").append(
					detail.getInvInfo().getBin().getId()); // 不等于当前库位
			
			if( withoutBinIds != null && withoutBinIds.size() > 0 ){
				executeSql.append(" and bin.id not in ( ").append(
						toString4Set(withoutBinIds)).append(") "); // 不等于不能不包含的库位
			}
			
			// 目标库位的库容<原库位的库容
			if( detail.getSmallThanSrc() != null 
					&& detail.getSmallThanSrc() 
					&& detail.getInvInfo().getBin().getProperties().getVolume() != null ){
				executeSql.append(" and binproperties.volume < ").append(
						detail.getInvInfo().getBin().getProperties().getVolume()); 
			}
			
			executeSql.append(" and bin.WH_ID =").append(
					detail.getPutawayDoc().getWh().getId()); // 仓库
			// if (EnuPARBePallet.NOPALLET.equalsIgnoreCase(pallet)) {
			// executeSql
			// .append(" and NVL(binproperties.PALLET_COUNT,0) = 0 "); // 托盘限定
			// } else {
			// executeSql
			// .append(" and NVL(binproperties.PALLET_COUNT,0)> 0  "); // 托盘限定
			// }
			executeSql.append(" and (  ");
			executeSql.append(" exists (");
			executeSql
					.append(" select 1 from WMS_INVENTORY inv,WMS_QUANT_INVENTORY qinv ");
			executeSql
					.append(" where inv.BIN_ID = bin.id  and inv.QUANT_INV_ID = qinv.id ");
			executeSql.append(" and qinv.quant_id = ")
					.append(detail.getInvInfo().getQuant().getId())
					.append(" )");
			executeSql.append(" ) ");

			executeSql
					.append(" and (( bin.SKU_MIXED = 0 and ( binInv.LAST_SKU =")
					.append(skuId).append(" or binInv.LAST_SKU is null ) ");
			executeSql.append("  ) or bin.SKU_MIXED=1 )");

			executeSql.append("  and ( bin.SUPPLIER_MIXED=1 )");

			executeSql.append(" union");

			executeSql
					.append(" select bbd.BIN_ID as BIN_ID,bin.PUTAWAY_INDEX as PUTAWAY_INDEX, bin.code as BIN_CODE from WMS_BIN_BINGROUP bbd,WMS_BIN bin,WMS_BIN_PROPERTIES binproperties, WMS_BIN_INV_INFO binInv where 1=1 ");
			executeSql.append(" and bbd.BIN_ID = bin.id ");
			executeSql.append(" and bin.PROPERTIES = binproperties.id ");
			executeSql.append(" and bin.BIN_INV_INFO_ID = binInv.id ");
			executeSql.append(" and bbd.BIN_GROUP_ID =").append(
					rule.getDescBinGroup().getId()); // 库位组
			executeSql.append(" and bin.DISABLED = 0 "); // 库位状态
			executeSql
					.append(" and nvl(bin.LOCK_STATUS,'LOCK_T2') = 'LOCK_T2' "); // 无入锁
			executeSql.append(" and bin.id <> ").append(
					detail.getInvInfo().getBin().getId()); // 不等于当前库位
			
			if( withoutBinIds != null && withoutBinIds.size() > 0 ){
				executeSql.append(" and bin.id not in ( ").append(
						toString4Set(withoutBinIds)).append(") "); // 不等于不能不包含的库位
			}
			
			// 目标库位的库容<原库位的库容
			if( detail.getSmallThanSrc() != null 
					&& detail.getSmallThanSrc() 
					&& detail.getInvInfo().getBin().getProperties().getVolume() != null ){
				executeSql.append(" and binproperties.volume < ").append(
						detail.getInvInfo().getBin().getProperties().getVolume()); 
			}
			
			executeSql.append(" and bin.WH_ID =").append(
					detail.getPutawayDoc().getWh().getId()); // 仓库
			// if (EnuPARBePallet.NOPALLET.equalsIgnoreCase(pallet)) {
			// executeSql
			// .append(" and NVL(binproperties.PALLET_COUNT,0) = 0 "); // 托盘限定
			// } else {
			// executeSql
			// .append(" and NVL(binproperties.PALLET_COUNT,0)> 0  "); // 托盘限定
			// }
			executeSql.append("and binInv.LAST_LOT = ")
					.append(detail.getInvInfo().getQuant().getId()).append(" ");

			executeSql
					.append(" and (( bin.SKU_MIXED = 0 and ( binInv.LAST_SKU =")
					.append(skuId).append(" or binInv.LAST_SKU is null ) ");
			executeSql.append("  ) or bin.SKU_MIXED=1 )");

			executeSql.append("  and ( bin.SUPPLIER_MIXED=1 )");

			executeSql.append(") binView ");

			executeSql
					.append("order by binView.PUTAWAY_INDEX, binView.BIN_CODE");
		}

		// 空库位优先
		else if (EnuPARBinSelectPriority.EMPTY.equalsIgnoreCase(binSel)) {
			executeSql.append(imansb.toString());
			executeSql.append(" and binInv.CURRENT_QTY <= 0 ");
			executeSql.append(" and binInv.QUEUED_QTY <= 0 ");
			executeSql.append(" order by bin.PUTAWAY_INDEX, bin.CODE ");
		}

		// 历史存货库位
		else if (EnuPARBinSelectPriority.HIS_PA.equalsIgnoreCase(binSel)) {

			executeSql.append(imansb.toString());
			executeSql.append(" and exists (  ");
			executeSql
					.append(" select 1 from WMS_WT_HISTORY historyPa,WMS_QUANT quant where historyPa.TO_BIN_ID = bin.id ");
			executeSql.append(" and historyPa.QUANT_ID = quant.id ");
			executeSql.append(" and quant.SKU = ").append(skuId).append(") ");// 说明有过存货履历

			executeSql
					.append(" and (( bin.SKU_MIXED = 0 and ( binInv.LAST_SKU =")
					.append(skuId).append(" or binInv.LAST_SKU is null ) ");// 不能混商品的库位,
																			// 最后存货商品必须一样

			executeSql.append("  ) or bin.SKU_MIXED=1 )");// 可以混商品

			executeSql
					.append(" and (( bin.LOT_MIXED = 0 and ( binInv.LAST_LOT =")
					.append(detail.getInvInfo().getQuant().getId())
					.append(" or binInv.LAST_LOT is null )");// 不能混批次的库位,
			// 最后存货批次必须一样

			executeSql.append("  ) or bin.LOT_MIXED = 1 )");// 可以混批次

			executeSql.append(" and ( bin.SUPPLIER_MIXED=1 )");// 可以混供应商
			executeSql.append(" order by bin.PUTAWAY_INDEX, bin.CODE ");

		}
		// 历史拣选库位
		else if (EnuPARBinSelectPriority.HIS_PK.equalsIgnoreCase(binSel)) {

			executeSql.append(imansb.toString());
			executeSql.append(" and exists (  ");
			executeSql
					.append(" select 1 from WMS_WT_HISTORY historyPa,WMS_QUANT quant where historyPa.BIN_ID = bin.id ");
			executeSql.append(" and historyPa.QUANT_ID = quant.id ");
			executeSql.append(" and quant.SKU = ").append(skuId).append(") ");// 说明有过拣选履历

			executeSql
					.append(" and (( bin.SKU_MIXED = 0 and ( binInv.LAST_SKU =")
					.append(skuId).append(" or binInv.LAST_SKU is null ) ");// 不能混商品的库位,
																			// 最后存货商品必须一样

			executeSql.append("  ) or bin.SKU_MIXED=1 )");// 可以混商品

			executeSql
					.append(" and (( bin.LOT_MIXED = 0 and ( binInv.LAST_LOT =")
					.append(detail.getInvInfo().getQuant().getId())
					.append(" or binInv.LAST_LOT is null )");// 不能混批次的库位,
			// 最后存货批次必须一样

			executeSql.append("  ) or bin.LOT_MIXED = 1 )");// 可以混批次

			executeSql.append(" and ( bin.SUPPLIER_MIXED=1 )");// 可以混供应商

			executeSql.append(" order by bin.PUTAWAY_INDEX, bin.CODE ");

		}
		// 库位内必须有相同SKU
		else if (EnuPARBinSelectPriority.SKU_IN_BIN.equalsIgnoreCase(binSel)) {

			executeSql.append("select binView.BIN_ID from ");
			executeSql.append("(");

			executeSql
					.append(" select bbd.BIN_ID as BIN_ID,bin.PUTAWAY_INDEX as PUTAWAY_INDEX, bin.code as BIN_CODE from WMS_BIN_BINGROUP bbd,WMS_BIN bin,WMS_BIN_PROPERTIES binproperties, WMS_BIN_INV_INFO binInv where 1=1 ");
			executeSql.append(" and bbd.BIN_ID = bin.id ");
			executeSql.append(" and bin.PROPERTIES = binproperties.id ");
			executeSql.append(" and bin.BIN_INV_INFO_ID = binInv.id ");
			executeSql.append(" and bbd.BIN_GROUP_ID =").append(
					rule.getDescBinGroup().getId()); // 库位组
			executeSql.append(" and bin.DISABLED = 0 "); // 库位状态
			executeSql
					.append(" and nvl(bin.LOCK_STATUS,'LOCK_T2') = 'LOCK_T2' "); // 无入锁
			executeSql.append(" and bin.id <> ").append(
					detail.getInvInfo().getBin().getId()); // 不等于当前库位
			
			if( withoutBinIds != null && withoutBinIds.size() > 0 ){
				executeSql.append(" and bin.id not in ( ").append(
						toString4Set(withoutBinIds)).append(") "); // 不等于不能不包含的库位
			}
			
			// 目标库位的库容<原库位的库容
			if( detail.getSmallThanSrc() != null 
					&& detail.getSmallThanSrc() 
					&& detail.getInvInfo().getBin().getProperties().getVolume() != null ){
				executeSql.append(" and binproperties.volume < ").append(
						detail.getInvInfo().getBin().getProperties().getVolume()); 
			}
			
			executeSql.append(" and bin.WH_ID =").append(
					detail.getPutawayDoc().getWh().getId()); // 仓库
			// if (EnuPARBePallet.NOPALLET.equalsIgnoreCase(pallet)) {
			// executeSql
			// .append(" and NVL(binproperties.PALLET_COUNT,0) = 0 "); // 托盘限定
			// } else {
			// executeSql
			// .append(" and NVL(binproperties.PALLET_COUNT,0)> 0  "); // 托盘限定
			// }
			executeSql.append(" and (  ");
			executeSql.append(" exists (");
			executeSql
					.append(" select 1 from WMS_INVENTORY inv,WMS_QUANT_INVENTORY qinv,WMS_QUANT quant1 ");
			executeSql
					.append(" where inv.BIN_ID = bin.id and inv.QUANT_INV_ID = qinv.id and quant1.id =qinv.quant_id ");
			executeSql.append(" and quant1.sku = ").append(skuId).append(" )");
			executeSql.append(" ) ");

			executeSql
					.append(" and (( bin.LOT_MIXED = 0 and ( binInv.LAST_LOT =")
					.append(detail.getInvInfo().getQuant().getId())
					.append(" or binInv.LAST_LOT is null )");// 不能混批次的库位,
			// 最后存货批次必须一样
			executeSql.append("  ) or bin.LOT_MIXED = 1 )");// 可以混批次

			executeSql.append("  and ( bin.SUPPLIER_MIXED=1 )");// 可以混供应商

			executeSql.append(" union");

			executeSql
					.append(" select bbd.BIN_ID as BIN_ID,bin.PUTAWAY_INDEX as PUTAWAY_INDEX, bin.code as BIN_CODE from WMS_BIN_BINGROUP bbd,WMS_BIN bin,WMS_BIN_PROPERTIES binproperties, WMS_BIN_INV_INFO binInv where 1=1 ");
			executeSql.append(" and bbd.BIN_ID = bin.id ");
			executeSql.append(" and bin.PROPERTIES = binproperties.id ");
			executeSql.append(" and bin.BIN_INV_INFO_ID = binInv.id ");
			executeSql.append(" and bbd.BIN_GROUP_ID =").append(
					rule.getDescBinGroup().getId()); // 库位组
			executeSql.append(" and bin.DISABLED = 0 "); // 库位状态
			executeSql
					.append(" and nvl(bin.LOCK_STATUS,'LOCK_T2') = 'LOCK_T2' "); // 无入锁
			executeSql.append(" and bin.id <> ").append(
					detail.getInvInfo().getBin().getId()); // 不等于当前库位
			
			if( withoutBinIds != null && withoutBinIds.size() > 0 ){
				executeSql.append(" and bin.id not in ( ").append(
						toString4Set(withoutBinIds)).append(") "); // 不等于不能不包含的库位
			}
			
			// 目标库位的库容<原库位的库容
			if( detail.getSmallThanSrc() != null 
					&& detail.getSmallThanSrc() 
					&& detail.getInvInfo().getBin().getProperties().getVolume() != null ){
				executeSql.append(" and binproperties.volume < ").append(
						detail.getInvInfo().getBin().getProperties().getVolume()); 
			}
			
			executeSql.append(" and bin.WH_ID =").append(
					detail.getPutawayDoc().getWh().getId()); // 仓库
			// if (EnuPARBePallet.NOPALLET.equalsIgnoreCase(pallet)) {
			// executeSql
			// .append(" and NVL(binproperties.PALLET_COUNT,0) = 0 "); // 托盘限定
			// } else {
			// executeSql
			// .append(" and NVL(binproperties.PALLET_COUNT,0)> 0  "); // 托盘限定
			// }
			executeSql.append(" and binInv.LAST_SKU =").append(skuId)
					.append("  ");// 待上架的最后一个商品

			executeSql
					.append(" and (( bin.LOT_MIXED = 0 and ( binInv.LAST_LOT =")
					.append(detail.getInvInfo().getQuant().getId())
					.append(" or binInv.LAST_LOT is null )");
			executeSql.append("  ) or bin.LOT_MIXED = 1 )");

			executeSql.append(" and ( bin.SUPPLIER_MIXED=1 )");

			executeSql.append(") binView ");

			executeSql
					.append("order by binView.PUTAWAY_INDEX, binView.BIN_CODE");

		}
		// 库位内必须有相同批次库存
		else if (EnuPARBinSelectPriority.LOT_IN_BIN.equalsIgnoreCase(binSel)) {

			executeSql.append("select binView.BIN_ID from ");
			executeSql.append("(");

			executeSql
					.append(" select bbd.BIN_ID as BIN_ID,bin.PUTAWAY_INDEX as PUTAWAY_INDEX, bin.code as BIN_CODE from WMS_BIN_BINGROUP bbd,WMS_BIN bin, WMS_BIN_PROPERTIES binproperties, WMS_BIN_INV_INFO binInv where 1=1 ");
			executeSql.append(" and bbd.BIN_ID = bin.id ");
			executeSql.append(" and bin.PROPERTIES = binproperties.id ");
			executeSql.append(" and bin.BIN_INV_INFO_ID = binInv.id ");
			executeSql.append(" and bbd.BIN_GROUP_ID =").append(
					rule.getDescBinGroup().getId()); // 库位组
			executeSql.append(" and bin.DISABLED = 0 "); // 库位状态
			executeSql
					.append(" and nvl(bin.LOCK_STATUS,'LOCK_T2') = 'LOCK_T2' "); // 无入锁
			executeSql.append(" and bin.id <> ").append(
					detail.getInvInfo().getBin().getId()); // 不等于当前库位
			
			if( withoutBinIds != null && withoutBinIds.size() > 0 ){
				executeSql.append(" and bin.id not in ( ").append(
						toString4Set(withoutBinIds)).append(") "); // 不等于不能不包含的库位
			}
			
			// 目标库位的库容<原库位的库容
			if( detail.getSmallThanSrc() != null 
					&& detail.getSmallThanSrc() 
					&& detail.getInvInfo().getBin().getProperties().getVolume() != null ){
				executeSql.append(" and binproperties.volume < ").append(
						detail.getInvInfo().getBin().getProperties().getVolume()); 
			}
			
			executeSql.append(" and bin.WH_ID =").append(
					detail.getPutawayDoc().getWh().getId()); // 仓库
			// if (EnuPARBePallet.NOPALLET.equalsIgnoreCase(pallet)) {
			// executeSql
			// .append(" and NVL(binproperties.PALLET_COUNT,0) = 0 "); // 托盘限定
			// } else {
			// executeSql
			// .append(" and NVL(binproperties.PALLET_COUNT,0)> 0  "); // 托盘限定
			// }
			executeSql.append(" and (  ");
			executeSql.append(" exists (");
			executeSql
					.append(" select 1 from WMS_INVENTORY inv,WMS_QUANT_INVENTORY qinv ");
			executeSql
					.append(" where inv.BIN_ID = bin.id  and inv.QUANT_INV_ID = qinv.id ");
			executeSql.append(" and qinv.quant_id = ")
					.append(detail.getInvInfo().getQuant().getId())
					.append(" )");
			executeSql.append(" ) ");

			executeSql
					.append(" and (( bin.SKU_MIXED = 0 and ( binInv.LAST_SKU =")
					.append(skuId).append(" or binInv.LAST_SKU is null ) ");
			executeSql.append("  ) or bin.SKU_MIXED=1 )");

			executeSql.append("  and ( bin.SUPPLIER_MIXED=1 )");

			executeSql.append(" union");

			executeSql
					.append(" select bbd.BIN_ID as BIN_ID,bin.PUTAWAY_INDEX as PUTAWAY_INDEX, bin.code as BIN_CODE from WMS_BIN_BINGROUP bbd,WMS_BIN bin,WMS_BIN_PROPERTIES binproperties, WMS_BIN_INV_INFO binInv where 1=1 ");
			executeSql.append(" and bbd.BIN_ID = bin.id ");
			executeSql.append(" and bin.PROPERTIES = binproperties.id ");
			executeSql.append(" and bin.BIN_INV_INFO_ID = binInv.id ");
			executeSql.append(" and bbd.BIN_GROUP_ID =").append(
					rule.getDescBinGroup().getId()); // 库位组
			executeSql.append(" and bin.DISABLED = 0 "); // 库位状态
			executeSql
					.append(" and nvl(bin.LOCK_STATUS,'LOCK_T2') = 'LOCK_T2' "); // 无入锁
			executeSql.append(" and bin.id <> ").append(
					detail.getInvInfo().getBin().getId()); // 不等于当前库位
			
			if( withoutBinIds != null && withoutBinIds.size() > 0 ){
				executeSql.append(" and bin.id not in ( ").append(
						toString4Set(withoutBinIds)).append(") "); // 不等于不能不包含的库位
			}
			
			// 目标库位的库容<原库位的库容
			if( detail.getSmallThanSrc() != null 
					&& detail.getSmallThanSrc() 
					&& detail.getInvInfo().getBin().getProperties().getVolume() != null ){
				executeSql.append(" and binproperties.volume < ").append(
						detail.getInvInfo().getBin().getProperties().getVolume()); 
			}
			
			executeSql.append(" and bin.WH_ID =").append(
					detail.getPutawayDoc().getWh().getId()); // 仓库
			// if (EnuPARBePallet.NOPALLET.equalsIgnoreCase(pallet)) {
			// executeSql
			// .append(" and NVL(binproperties.PALLET_COUNT,0) = 0 "); // 托盘限定
			// } else {
			// executeSql
			// .append(" and NVL(binproperties.PALLET_COUNT,0)> 0  "); // 托盘限定
			// }
			executeSql.append("and binInv.LAST_LOT = ")
					.append(detail.getInvInfo().getQuant().getId()).append(" ");

			executeSql
					.append(" and (( bin.SKU_MIXED = 0 and ( binInv.LAST_SKU =")
					.append(skuId).append(" or binInv.LAST_SKU is null ) ");
			executeSql.append("  ) or bin.SKU_MIXED=1 )");

			executeSql.append("  and ( bin.SUPPLIER_MIXED=1 )");

			executeSql.append(") binView ");

			executeSql
					.append("order by binView.PUTAWAY_INDEX, binView.BIN_CODE");

		}
		// 库位内必须有相同大类的库存
		else if (EnuPARBinSelectPriority.BT_IN_BIN.equalsIgnoreCase(binSel)) {

			executeSql.append(imansb.toString());

			if (detail.getInvInfo().getQuant().getSku().getIt1000() == null
					|| detail.getInvInfo().getQuant().getSku().getIt1000()
							.getId() == null) {
				executeSql.append(" and 1 < 0 ");// 商品没有设置大分类, 直接跳过
			} else {
				executeSql.append(" and exists (  ");
				executeSql
						.append(" select 1 from WMS_INVENTORY inv,WMS_QUANT_INVENTORY qinv,WMS_QUANT quant1,WMS_SKU sku ");
				executeSql
						.append(" where inv.BIN_ID = bin.id  and inv.QUANT_INV_ID = qinv.id and quant1.id =qinv.quant_id and quant1.sku = sku.id ");
				executeSql
						.append(" and sku.IT1000 = ")
						.append(detail.getInvInfo().getQuant().getSku()
								.getIt1000().getId()).append(" ) ");

				executeSql
						.append(" and (( bin.SKU_MIXED = 0 and ( binInv.LAST_SKU =")
						.append(skuId).append(" or binInv.LAST_SKU is null ) ");
				executeSql.append("  ) or bin.SKU_MIXED=1 )");

				executeSql
						.append(" and (( bin.LOT_MIXED = 0 and ( binInv.LAST_LOT =")
						.append(detail.getInvInfo().getQuant().getId())
						.append(" or binInv.LAST_LOT is null )");
				executeSql.append("  ) or bin.LOT_MIXED = 1 )");

				executeSql.append(" and ( bin.SUPPLIER_MIXED=1 )");
				executeSql.append(" order by bin.PUTAWAY_INDEX, bin.CODE ");
			}

		}
		// 库位内必须有相同中类的库存
		else if (EnuPARBinSelectPriority.MT_IN_BIN.equalsIgnoreCase(binSel)) {
			executeSql.append(imansb.toString());

			if (detail.getInvInfo().getQuant().getSku().getIt2000() == null
					|| detail.getInvInfo().getQuant().getSku().getIt2000()
							.getId() == null) {
				executeSql.append(" and 1 < 0 ");
			} else {
				executeSql.append(" and exists (  ");
				executeSql
						.append(" select 1 from WMS_INVENTORY inv,WMS_QUANT_INVENTORY qinv,WMS_QUANT quant1,WMS_SKU sku ");
				executeSql
						.append(" where inv.BIN_ID = bin.id  and inv.QUANT_INV_ID = qinv.id and quant1.id =qinv.quant_id and quant1.sku = sku.id ");
				executeSql
						.append(" and sku.IT2000 = ")
						.append(detail.getInvInfo().getQuant().getSku()
								.getIt2000().getId()).append(" ) ");
				executeSql
						.append(" and (( bin.SKU_MIXED = 0 and ( binInv.LAST_SKU =")
						.append(skuId).append(" or binInv.LAST_SKU is null ) ");
				executeSql.append("  ) or bin.SKU_MIXED=1 )");

				executeSql
						.append(" and (( bin.LOT_MIXED = 0 and ( binInv.LAST_LOT =")
						.append(detail.getInvInfo().getQuant().getId())
						.append(" or binInv.LAST_LOT is null )");
				executeSql.append("  ) or bin.LOT_MIXED = 1 )");

				executeSql.append(" and ( bin.SUPPLIER_MIXED=1 )");

				executeSql.append(" order by bin.PUTAWAY_INDEX, bin.CODE ");
			}

		}
		// 库位内必须有相同小类的库存
		else if (EnuPARBinSelectPriority.LT_IN_BIN.equalsIgnoreCase(binSel)) {
			executeSql.append(imansb.toString());
			if (detail.getInvInfo().getQuant().getSku().getIt3000() == null
					|| detail.getInvInfo().getQuant().getSku().getIt3000()
							.getId() == null) {
				executeSql.append(" and 1 < 0 ");
			} else {
				executeSql.append(" and exists (  ");
				executeSql
						.append(" select 1 from WMS_INVENTORY inv,WMS_QUANT_INVENTORY qinv,WMS_QUANT quant1,WMS_SKU sku ");
				executeSql
						.append(" where inv.BIN_ID = bin.id  and inv.QUANT_INV_ID = qinv.id and quant1.id =qinv.quant_id and quant1.sku = sku.id ");
				executeSql
						.append(" and sku.IT3000 = ")
						.append(detail.getInvInfo().getQuant().getSku()
								.getIt3000().getId()).append(" ) ");
				executeSql
						.append(" and (( bin.SKU_MIXED = 0 and ( binInv.LAST_SKU =")
						.append(skuId).append(" or binInv.LAST_SKU is null ) ");
				executeSql.append("  ) or bin.SKU_MIXED=1 )");

				executeSql
						.append(" and (( bin.LOT_MIXED = 0 and ( binInv.LAST_LOT =")
						.append(detail.getInvInfo().getQuant().getId())
						.append(" or binInv.LAST_LOT is null )");
				executeSql.append("  ) or bin.LOT_MIXED = 1 )");

				executeSql.append("  and ( bin.SUPPLIER_MIXED=1 )");

				executeSql.append(" order by bin.PUTAWAY_INDEX, bin.CODE ");

			}

		}
		// 同供应商优先
		else if (EnuPARBinSelectPriority.SUPPLIER_IN_BIN
				.equalsIgnoreCase(binSel)) {
			if (detail.getInvInfo().getQuant().getSku().getDefSupplier() == null
					|| detail.getInvInfo().getQuant().getSku().getDefSupplier()
							.getId() == null) {
				new ArrayList<BigDecimal>();
			}

			executeSql.append("select binView.BIN_ID from ");
			executeSql.append("(");

			executeSql
					.append(" select bbd.BIN_ID as BIN_ID,bin.PUTAWAY_INDEX as PUTAWAY_INDEX, bin.code as BIN_CODE from WMS_BIN_BINGROUP bbd,WMS_BIN bin,WMS_BIN_PROPERTIES binproperties, WMS_BIN_INV_INFO binInv where 1=1 ");
			executeSql.append(" and bbd.BIN_ID = bin.id ");
			executeSql.append(" and bin.PROPERTIES = binproperties.id ");
			executeSql.append(" and bin.BIN_INV_INFO_ID = binInv.id ");
			executeSql.append(" and bbd.BIN_GROUP_ID =").append(
					rule.getDescBinGroup().getId()); // 库位组
			executeSql.append(" and bin.DISABLED = 0 "); // 库位状态
			executeSql
					.append(" and nvl(bin.LOCK_STATUS,'LOCK_T2') = 'LOCK_T2' "); // 无入锁
			executeSql.append(" and bin.id <> ").append(
					detail.getInvInfo().getBin().getId()); // 不等于当前库位
			
			if( withoutBinIds != null && withoutBinIds.size() > 0 ){
				executeSql.append(" and bin.id not in ( ").append(
						toString4Set(withoutBinIds)).append(") "); // 不等于不能不包含的库位
			}
			
			// 目标库位的库容<原库位的库容
			if( detail.getSmallThanSrc() != null 
					&& detail.getSmallThanSrc() 
					&& detail.getInvInfo().getBin().getProperties().getVolume() != null ){
				executeSql.append(" and binproperties.volume < ").append(
						detail.getInvInfo().getBin().getProperties().getVolume()); 
			}
			
			executeSql.append(" and bin.WH_ID =").append(
					detail.getPutawayDoc().getWh().getId()); // 仓库
			// if (EnuPARBePallet.NOPALLET.equalsIgnoreCase(pallet)) {
			// executeSql
			// .append(" and NVL(binproperties.PALLET_COUNT,0) = 0 "); // 托盘限定
			// } else {
			// executeSql
			// .append(" and NVL(binproperties.PALLET_COUNT,0)> 0  "); // 托盘限定
			// }
			executeSql.append(" and (  ");
			executeSql.append(" exists (");
			executeSql
					.append(" select 1 from WMS_INVENTORY inv,WMS_QUANT_INVENTORY qinv,WMS_QUANT quant1,WMS_SKU sku ");
			executeSql
					.append(" where inv.BIN_ID = bin.id and inv.QUANT_INV_ID = qinv.id and quant1.id =qinv.quant_id and quant1.sku = sku.id ");

			executeSql
					.append(" and sku.DEF_SUPPLIER = ")
					.append(detail.getInvInfo().getQuant().getSku()
							.getDefSupplier().getId()).append(" )");
			executeSql.append(" ) ");

			executeSql
					.append(" and (( bin.SKU_MIXED = 0 and ( binInv.LAST_SKU =")
					.append(skuId).append(" or binInv.LAST_SKU is null ) ");
			executeSql.append("  ) or bin.SKU_MIXED=1 )");

			executeSql
					.append(" and (( bin.LOT_MIXED = 0 and ( binInv.LAST_LOT =")
					.append(detail.getInvInfo().getQuant().getId())
					.append(" or binInv.LAST_LOT is null )");
			executeSql.append("  ) or bin.LOT_MIXED = 1 )");

			executeSql.append(" union");
			executeSql
					.append(" select bbd.BIN_ID as BIN_ID,bin.PUTAWAY_INDEX as PUTAWAY_INDEX, bin.code as BIN_CODE from WMS_BIN_BINGROUP bbd,WMS_BIN bin,WMS_BIN_PROPERTIES binproperties, WMS_BIN_INV_INFO binInv where 1=1 ");
			executeSql.append(" and bbd.BIN_ID = bin.id ");
			executeSql.append(" and bin.PROPERTIES = binproperties.id ");
			executeSql.append(" and bin.BIN_INV_INFO_ID = binInv.id ");
			executeSql.append(" and bbd.BIN_GROUP_ID =").append(
					rule.getDescBinGroup().getId()); // 库位组
			executeSql.append(" and bin.DISABLED = 0 "); // 库位状态
			executeSql
					.append(" and nvl(bin.LOCK_STATUS,'LOCK_T2') = 'LOCK_T2' "); // 无入锁
			executeSql.append(" and bin.id <> ").append(
					detail.getInvInfo().getBin().getId()); // 不等于当前库位
			
			if( withoutBinIds != null && withoutBinIds.size() > 0 ){
				executeSql.append(" and bin.id not in ( ").append(
						toString4Set(withoutBinIds)).append(") "); // 不等于不能不包含的库位
			}
			
			// 目标库位的库容<原库位的库容
			if( detail.getSmallThanSrc() != null 
					&& detail.getSmallThanSrc() 
					&& detail.getInvInfo().getBin().getProperties().getVolume() != null ){
				executeSql.append(" and binproperties.volume < ").append(
						detail.getInvInfo().getBin().getProperties().getVolume()); 
			}
			
			executeSql.append(" and bin.WH_ID =").append(
					detail.getPutawayDoc().getWh().getId()); // 仓库
			// if (EnuPARBePallet.NOPALLET.equalsIgnoreCase(pallet)) {
			// executeSql
			// .append(" and NVL(binproperties.PALLET_COUNT,0) = 0 "); // 托盘限定
			// } else {
			// executeSql
			// .append(" and NVL(binproperties.PALLET_COUNT,0)> 0  "); // 托盘限定
			// }

			executeSql
					.append(" and bin.lastSupId = ")
					.append(detail.getInvInfo().getQuant().getSku()
							.getDefSupplier().getId()).append(" ");

			executeSql
					.append(" and (( bin.SKU_MIXED = 0 and ( binInv.LAST_SKU =")
					.append(skuId).append(" or binInv.LAST_SKU is null ) ");
			executeSql.append("  ) or bin.SKU_MIXED=1 )");
			executeSql
					.append(" and (( bin.LOT_MIXED = 0 and ( binInv.LAST_LOT =")
					.append(detail.getInvInfo().getQuant().getId())
					.append(" or binInv.LAST_LOT is null )");
			executeSql.append("  ) or bin.LOT_MIXED = 1 )");

			executeSql.append(") binView ");

			executeSql
					.append("order by binView.PUTAWAY_INDEX, binView.BIN_CODE");

		} else {
			executeSql.append(imansb.toString());

			executeSql
					.append(" and (( bin.SKU_MIXED = 0 and ( binInv.LAST_SKU =")
					.append(skuId).append(" or binInv.LAST_SKU is null ) ");
			executeSql.append("  ) or bin.SKU_MIXED=1 )");

			executeSql
					.append(" and (( bin.LOT_MIXED = 0 and ( binInv.LAST_LOT =")
					.append(detail.getInvInfo().getQuant().getId())
					.append(" or binInv.LAST_LOT is null )");
			executeSql.append("  ) or bin.LOT_MIXED = 1 )");

			executeSql.append(" and ( bin.SUPPLIER_MIXED=1 )");

			executeSql.append(" order by bin.PUTAWAY_INDEX, bin.CODE ");
		}

		return (List<BigDecimal>) this.commonDao.findBySqlQuery(executeSql
				.toString());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.core.scpwms.server.service.common.AllocateHelper#
	 * unAllocatePutawayDocDetail(java.util.List)
	 */
	@Override
	public void unAllocatePutawayDocDetail(List<Long> ids) {

		// 未完成任务查询
		StringBuffer sb = new StringBuffer();
		sb.append("select wt.id From WarehouseTask wt where wt.putawayDocDetail.id  in (:docDetailIds) and (wt.planQty - wt.executeQty) > 0 and wt.status <700 ");

		List<Long> wtIds = this.commonDao.findByQuery(sb.toString(),
				new String[] { "docDetailIds" }, new Object[] { ids });

		if (wtIds.isEmpty()) {
			return;
		}

		unAllocateByWtPutaway(wtIds);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.common.AllocateHelper#unAllocatePutawayDoc
	 * (java.util.List)
	 */
	@Override
	public void unAllocatePutawayDoc(List<Long> ids) {

		// 未完成任务查询
		StringBuffer sb = new StringBuffer();
		sb.append("select wt.id From WarehouseTask wt where wt.putawayDocDetail.putawayDoc.id in (:docIds) and (wt.planQty - coalesce(wt.executeQty,0)) > 0 and wt.status <700 ");

		List<Long> wtIds = this.commonDao.findByQuery(sb.toString(),
				new String[] { "docIds" }, new Object[] { ids });

		if (wtIds.isEmpty()) {
			return;
		}

		unAllocateByWtPutaway(wtIds);

	}

	public void unAllocateByWtPutaway(List<Long> wtIds) {
		for (Long id : wtIds) {
			unAllocateByWtPutaway(id);
		}
	}

	public void unAllocateByWtPutaway(Long wtIds) {
		WarehouseTask task = this.commonDao.load(WarehouseTask.class, wtIds);
		
		String containerSeq = task.getInvInfo().getContainerSeq();
		if( containerSeq != null && containerSeq.length() > 0 ){
			String hql = " from WarehouseTask wt where wt.wh.id = :whId and wt.invInfo.containerSeq = :containerSeq and wt.status in (200,500) ";
			List<WarehouseTask> wts = commonDao.findByQuery(hql, new String[]{"whId","containerSeq"}, new Object[]{task.getWh().getId(), containerSeq});
			if( wts != null && wts.size() > 0 ){
				for( WarehouseTask wt : wts ){
					if( EnuWtProcessType.RP_PUTAWAY.equals(wt.getProcessType()) ){
						cancelDescBinRpInv(wt);
					}
					cancelPutawayWt(wt);
				}
				// 取消一个待上架的托盘数
				task.getDescBin().getBinInvInfo().removeQueuedPallet(1D);
			}
		}
		else{
			if( EnuWtProcessType.RP_PUTAWAY.equals(task.getProcessType()) ){
				cancelDescBinRpInv(task);
			}
			cancelPutawayWt(task);
		}
	}
	
	private void cancelDescBinRpInv( WarehouseTask task ) {
		InventoryInfo descInvInfo = new InventoryInfo();
		try{
			BeanUtils.copyProperties(descInvInfo, task.getInvInfo());
		}catch( Exception e ){
			throw new BusinessException(ExceptionConstant.SYSTEM_EXCEPTION);
		}
		descInvInfo.setBin(task.getDescBin());
		descInvInfo.setContainerSeq(task.getDescContainerSeq());
		
		Inventory inv = invHelper.getInv(descInvInfo);
		if( inv != null ){
			invHelper.allocateInv(inv, task.getUnExecuteQty());
			if( inv.getQueuedQty() != null && inv.getQueuedQty() > 0D ){
				throw new BusinessException("已经有拣货任务，不能取消。");
			}
			invHelper.deleteInv(inv);
		}
	}
	
	private void cancelPutawayWt( WarehouseTask task ){
		double quantity = task.getUnExecuteQty();

		if (quantity > 0) {
			if( task.getPutawayDocDetail() != null ){
				// 修改单据的分配数量
				task.getPutawayDocDetail().unAllocate(quantity);
	
				// 分配完成->分配中
				if (EnuPutawayDocStatus.ALLOCATE.equals(task.getPutawayDocDetail()
						.getPutawayDoc().getStatus())
						&& task.getPutawayDocDetail().getPutawayDoc()
								.getAllocateQty() < task.getPutawayDocDetail()
								.getPutawayDoc().getPlanQty()) {
					orderStatusHelper.changeStatus(task.getPutawayDocDetail()
							.getPutawayDoc(), EnuPutawayDocStatus.ALLOCATING);
				}
				// 分配中->发行
				if (EnuPutawayDocStatus.ALLOCATING.equals(task
						.getPutawayDocDetail().getPutawayDoc().getStatus())
						&& task.getPutawayDocDetail().getPutawayDoc()
								.getAllocateQty() <= 0) {
					orderStatusHelper.changeStatus(task.getPutawayDocDetail()
							.getPutawayDoc(), EnuPutawayDocStatus.PUBLISH);
				}
			}

			// 修改Tack的分配数量
			task.removePlanQty(quantity);

			// 更新库容
			task.getDescBin().getBinInvInfo()
					.removeQueuedInvInfoNoPallet(task.getInvInfo(), quantity);

			if (task.getPlanQty() <= 0) {
				if( task.getPutawayDocDetail() != null ){
					task.getPutawayDocDetail().getTasks().remove(task);
				}
				this.commonDao.delete(task);
			} else {
				this.commonDao.store(task);
			}

			// 上架库位分配不需要释放库存的分配数, 上架计划取消或者强制关闭的时候会取消分配数
			// 释放库存
			Inventory inv = invHelper.getInv(task.getInvInfo());
			if (inv != null) {
				invHelper.unAllocateInvByPutaway(inv, task.getDescBin(),quantity);
				this.commonDao.store(inv);
			}
		}
	}
	

	@Override
	public void closePutaway(Long wtId) {
		WarehouseTask task = this.commonDao.load(WarehouseTask.class, wtId);

		double quantity = task.getUnExecuteQty();

		if (quantity > 0) {
			// 修改Task的分配数量
			task.removePlanQty(quantity);

			// 更新库容
			task.getDescBin().getBinInvInfo()
					.removeQueuedInvInfoNoPallet(task.getInvInfo(), quantity);

			// Task的状态改为完成
			orderStatusHelper.changeStatus(task, EnuWarehouseOrderStatus.CLOSE);

			// 释放库存分配数
			Inventory inv = invHelper.getInv(task.getInvInfo());
			if (inv != null) {
				invHelper.unAllocateInvByPutaway(inv, task.getDescBin(),
						quantity);
				this.commonDao.store(inv);
			}

			// 修改所属WO的状态。
			if (task.getWo() != null) {
				WarehouseOrder wo = task.getWo();

				// 发行或计划状态改为执行中
				if (EnuWarehouseOrderStatus.PLANED.equals(wo.getStatus())
						|| EnuWarehouseOrderStatus.PUBLISH.equals(wo
								.getStatus())) {
					orderStatusHelper.changeStatus(wo,
							EnuWarehouseOrderStatus.EXECUTE);
				}

				// 如果该WO下属WT都已经是执行完成状态，WO也改为执行完成
				boolean allClosed = true;
				for (WarehouseTask wt : wo.getTasks()) {
					if (!EnuWarehouseOrderStatus.CLOSE.equals(wt.getStatus())) {
						allClosed = false;
						break;
					}
				}
				if (allClosed) {
					orderStatusHelper.changeStatus(wo,
							EnuWarehouseOrderStatus.CLOSE);
				}
			}

			// 修改上架单，明细的分配数量
			task.getPutawayDocDetail().unAllocate(quantity);

			// 计划中，计划完成 ->执行中
			PutawayDoc doc = task.getPutawayDocDetail().getPutawayDoc();
			if (EnuPutawayDocStatus.PLANED.equals(doc.getStatus())
					|| EnuPutawayDocStatus.PLANING.equals(doc.getStatus())) {
				orderStatusHelper.changeStatus(doc,
						EnuPutawayDocStatus.EXECUTEING);
			}

			// 如果上架计划下属的WT都已经关闭，该上架计划也关闭
			boolean allClosed = true;
			for (PutawayDocDetail pd : doc.getDetails()) {
				for (WarehouseTask wt : pd.getTasks()) {
					if (!EnuWarehouseOrderStatus.CLOSE.equals(wt.getStatus())) {
						allClosed = false;
						break;
					}
				}
			}
			if (allClosed) {
				orderStatusHelper.changeStatus(doc, EnuPutawayDocStatus.CLOSE);
			}
		}
	}

	@Override
	public String allocatePutawayDoc(PutawayDocDetail detail, Bin bin,
			PackageDetail packDetail, Double qty) {

		// 上架单据取得
		PutawayDoc doc = detail.getPutawayDoc();

		// 只有发行和分配中状态可以自动分配
		if (!EnuPutawayDocStatus.PUBLISH.equals(doc.getStatus())
				&& !EnuPutawayDocStatus.ALLOCATING.equals(doc.getStatus())) {
			return ExceptionConstant.ERROR_STATUS;
		}

		String putAwayType = null; // 上架 类型
		OrderType invOdt = doc.getOrderType();

		if (invOdt != null && invOdt.getCode() != null) {
			// * 收货上架单
			if (invOdt.getCode().equals(WmsConstant.PUTAWAY_ASN)) {
				putAwayType = EnuWtProcessType.ASN_PUTAWAY;
			}
			// * 质检上架单
			else if (invOdt.getCode().equals(WmsConstant.PUTAWAY_QC)) {
				putAwayType = EnuWtProcessType.QC_PUTAWAY;
			}
			// * 移位上架单
			else if (invOdt.getCode().equals(WmsConstant.PUTAWAY_MOV)) {
				putAwayType = EnuWtProcessType.MV_PUTAWAY;
			}
			// * 加工成品上架单
			else if (invOdt.getCode().equals(WmsConstant.PUTAWAY_PRO)) {
				putAwayType = EnuWtProcessType.PRO_PUTAWAY;
			}
			// * 退拣上架单
			else if (invOdt.getCode().equals(WmsConstant.PUTAWAY_OBD)) {
				putAwayType = EnuWtProcessType.RV_PUTAWAY;
			}
			// * 包装上架单
			else if (invOdt.getCode().equals("")) {
				// TODO
			}
		}

		List<Bin> targetBins = new ArrayList<Bin>(1);
		targetBins.add(bin);

		// 累计分配数量
		double allocatedQty = 0;

		// 待分配数
		double toBeMovedQty = qty;

		// 原始库位(检查一下库存, 如果库存已经被动, 可用量小于计划数量, 报错)
		Inventory inv = invHelper.getInv(detail.getInvInfo());
		if (inv == null) {
			return ExceptionConstant.INVENTORY_NOT_FOUND;
		}

		if (PrecisionUtils.compareByBasePackage(inv.getAvaliableQuantity(),
				toBeMovedQty, detail.getInvInfo().getPackageDetail()) < 0) {
			return ExceptionConstant.CANNOT_ADJUST_AS_MORE_THAN_AVAILABLE_QTY;
		}

		doAllocateByRuleNotPallet(targetBins, detail, toBeMovedQty,
				allocatedQty, putAwayType, false);

		return null;
	}

	@Override
	public boolean checkBin4Inventory(Long invId) {
//		Inventory inv = commonDao.load(Inventory.class, invId);
////		PutawayRuleInfo ruleInfo = inv.getPutawayRuleInfo();
//
//		// 获取可用规则
////		List<PutAwayRule> rules = getPutAwayRules(ruleInfo, inv.getBin().getId());
//		
//		if( rules != null && rules.size() > 0 ){
////			Set<Long> binIdSet = new HashSet<Long>();
////			for( PutAwayRule rule : rules ){
////				if( rule.getDescBin() != null ){
////					binIdSet.add(rule.getDescBin().getId());
////				}
////				
////				if( rule.getDescBinGroup() != null ){
////					for( Bin bin : rule.getDescBinGroup().getBins() ){
////						binIdSet.add(bin.getId());
////					}
////				}
////				
////				if( binIdSet.contains(inv.getBin().getId()) ){
////					return true;
////				}
////			}
//			
//			return true;
//		}

		return false;
	}

	/**
	 * 
	 * <p>匹配满足条件的上架策略</p>
	 *
	 * @param ruleInfo
	 * @return
	 */
	private List<PutAwayRule> getPutAwayRules(PutawayRuleInfo ruleInfo, Long binId) {

		StringBuffer sb = new StringBuffer();
		List<String> params = new ArrayList<String>();
		List<Object> values = new ArrayList<Object>();

		sb.append("from PutAwayRule rule where rule.disabled =false and rule.wh.id =:whId ");
		
		// 仓库限定
		params.add("whId");
		values.add(ruleInfo.getWhId());
		
		// 目标库位或者目标库位组有包含这个BIN_ID
		sb.append(" AND (rule.descBin is null or rule.descBin.id = :binId)  ");
		sb.append(" AND (rule.descBinGroup is null or :binId in ( select bbg.bin.id from BinBinGroup bbg where bbg.binGroup.id = rule.descBinGroup.id ))  ");
		
		params.add("binId");
		values.add(binId);
		
		
		
		// 原始库位限定
		sb.append(" AND (rule.srcBin IS NULL or (rule.srcBin.id=:srcBinId))  ");
		params.add("srcBinId");
		values.add(ruleInfo.getSrcBinId());

		// 货主限定
		sb.append(" AND (rule.plant IS NULL or (rule.plant.id=:plantId))  ");
		params.add("plantId");
		values.add(ruleInfo.getPlantId());

		// SKU限定
		sb.append(" AND (rule.sku IS NULL or (rule.sku.id=:skuId))  ");
		params.add("skuId");
		values.add(ruleInfo.getSkuId());

		// 订单类型限定
		if (ruleInfo.getOrderTypeId() != null) {
			sb.append(" AND (rule.ot IS NULL or (rule.ot.id=:iotId))  ");
			params.add("iotId");
			values.add(ruleInfo.getOrderTypeId());
		}

		// 库存状态
		sb.append(" AND (rule.invStatus IS NULL or (rule.invStatus=:invStatus))  ");
		params.add("invStatus");
		values.add(ruleInfo.getInvStatus());

		// ABC分类
		if (ruleInfo.getAbcTypeId() != null) {
			sb.append(" AND (rule.abcProperties IS NULL or (rule.abcProperties.id=:abcPropertiesId))  ");
			params.add("abcPropertiesId");
			values.add(ruleInfo.getAbcTypeId());
		} else {
			sb.append(" AND (rule.abcProperties IS NULL )  ");
		}

		// 商品大类限定
		if (ruleInfo.getbTypeId() != null) {
			sb.append(" AND (rule.bType IS NULL or (rule.bType.id=:bTypeId))  ");
			params.add("bTypeId");
			values.add(ruleInfo.getbTypeId());
		} else {
			sb.append(" AND (rule.bType IS NULL )  ");
		}

		// 商品中类限定
		if (ruleInfo.getmTypeId() != null) {
			sb.append(" AND (rule.mType IS NULL or (rule.mType.id=:mTypeId))  ");
			params.add("mTypeId");
			values.add(ruleInfo.getmTypeId());
		} else {
			sb.append(" AND (rule.mType IS NULL )  ");
		}

		// 商品小类限定
		if (ruleInfo.getlTypeId() != null) {
			sb.append(" AND (rule.lType IS NULL or (rule.lType.id=:lTypeId))  ");
			params.add("lTypeId");
			values.add(ruleInfo.getlTypeId());
		} else {
			sb.append(" AND (rule.lType IS NULL )  ");
		}

		// 品牌限定
		if (!StringUtils.isEmpty(ruleInfo.getBrandName())) {
			sb.append(" AND (rule.brandName IS NULL or rule.brandName =:brandName) ");
			params.add("brandName");
			values.add(ruleInfo.getBrandName());
		} else {
			sb.append(" AND (rule.brandName IS NULL ) ");
		}

		// 托盘限定
		if( ruleInfo.getBePallet() != null && ruleInfo.getBePallet() ){
			sb.append(" AND (rule.ruleMain.hasPallet IS NULL or (rule.ruleMain.hasPallet=:hasPallet))  ");
			params.add("hasPallet");
			values.add(EnuPARBePallet.HASPALLET);
		}
		else{
			sb.append(" AND (rule.ruleMain.hasPallet IS NULL or (rule.ruleMain.hasPallet=:hasPallet))  ");
			params.add("hasPallet");
			values.add(EnuPARBePallet.NOPALLET);
		}

		// 包装组限定
		sb.append(" AND (rule.packageInfo IS NULL or (rule.packageInfo.id=:packageInfoId))  ");
		params.add("packageInfoId");
		values.add(ruleInfo.getPackInfoId());

		// 包装限定
		sb.append(" AND (rule.packageDetail IS NULL or (rule.packageDetail.id=:packageDetailId))  ");
		params.add("packageDetailId");
		values.add(ruleInfo.getPackDetailId());

		// 数量限定
		sb.append(" AND (  ");
		sb.append("      rule.ruleMain.useQtyLimit = false ");
		sb.append("      or ( (rule.ruleMain.qtyLowerLimit IS NULL or rule.ruleMain.qtyLowerLimit = 0.0 OR rule.ruleMain.qtyLowerLimit<=:planQty1 ) ");
		sb.append("  and  ");
		sb.append(" (rule.ruleMain.qtyLimit IS NULL or rule.ruleMain.qtyLimit = 0.0 OR rule.ruleMain.qtyLimit >=:planQty2 ) ");
		sb.append(" ) )  ");

		params.add("planQty1");
		params.add("planQty2");
		values.add(ruleInfo.getToPutawayBaseQty());
		values.add(ruleInfo.getToPutawayBaseQty());

		// 托盘数限定
		sb.append(" AND (  ");
		sb.append("      rule.ruleMain.usePalletLimit = false ");
		sb.append("      or ( (rule.ruleMain.palletQtyLowerLimit IS NULL or rule.ruleMain.palletQtyLowerLimit = 0.0 OR (:planQty3 is null) OR rule.ruleMain.palletQtyLowerLimit<=:planQty3 ) ");
		sb.append("  and  ");
		sb.append(" (rule.ruleMain.palletQtyLimit IS NULL or rule.ruleMain.palletQtyLimit = 0.0 OR (:planQty4 is null) OR rule.ruleMain.palletQtyLimit >=:planQty4 ) ");
		sb.append(" ) )  ");

		params.add("planQty3");
		params.add("planQty4");
		values.add(ruleInfo.getToPutawayPalletQty());
		values.add(ruleInfo.getToPutawayPalletQty());

		// 包装限定2
		sb.append(" AND (rule.packageLevel IS NULL or (rule.packageLevel=:packageLevel))  ");
		params.add("packageLevel");
		values.add(ruleInfo.getPackLevel());

		sb.append(" AND (rule.disabled = false) ");
		sb.append(" order by  rule.priority ");

		List<PutAwayRule> rules = this.commonDao.findByQuery(sb.toString(),
				(String[]) params.toArray(new String[params.size()]),
				(Object[]) values.toArray(new Object[values.size()]));

		// 批次限定 -- 目前没有实现 等待完善
		// TODO

		return rules;

	}
}
