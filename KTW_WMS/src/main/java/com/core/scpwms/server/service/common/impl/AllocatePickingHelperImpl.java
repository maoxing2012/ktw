package com.core.scpwms.server.service.common.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.ComparatorChain;
import org.apache.commons.collections.comparators.ReverseComparator;

import com.core.business.exception.BusinessException;
import com.core.business.model.domain.CreateInfo;
import com.core.business.web.security.UserHolder;
import com.core.db.server.service.pojo.DefaultBaseManager;
import com.core.scpview.client.utils.StringUtils;
import com.core.scpwms.server.constant.ExceptionConstant;
import com.core.scpwms.server.constant.WmsConstant4Ktw;
import com.core.scpwms.server.domain.AllocateDocDetail;
import com.core.scpwms.server.domain.AllocateInventoryInfo;
import com.core.scpwms.server.enumerate.EnuInvStatus;
import com.core.scpwms.server.enumerate.EnuLotChangeDocDetailStatus;
import com.core.scpwms.server.enumerate.EnuLotChangeDocStatus;
import com.core.scpwms.server.enumerate.EnuLotFieldType;
import com.core.scpwms.server.enumerate.EnuOutboundDeliveryDetailStatus;
import com.core.scpwms.server.enumerate.EnuOutboundDeliveryStatus;
import com.core.scpwms.server.enumerate.EnuOwnerChangeDocDetailStatus;
import com.core.scpwms.server.enumerate.EnuOwnerChangeDocStatus;
import com.core.scpwms.server.enumerate.EnuPackageLevel;
import com.core.scpwms.server.enumerate.EnuStoreRole;
import com.core.scpwms.server.enumerate.EnuTurnOverConf;
import com.core.scpwms.server.enumerate.EnuWarehouseOrderStatus;
import com.core.scpwms.server.enumerate.EnuWaveStatus;
import com.core.scpwms.server.enumerate.EnuWtProcessType;
import com.core.scpwms.server.filter.WarehouseHolder;
import com.core.scpwms.server.model.change.LotChangeDoc;
import com.core.scpwms.server.model.change.LotChangeDocDetail;
import com.core.scpwms.server.model.change.OwnerChangeDoc;
import com.core.scpwms.server.model.change.OwnerChangeDocDetail;
import com.core.scpwms.server.model.common.PackageDetail;
import com.core.scpwms.server.model.common.Sku;
import com.core.scpwms.server.model.inventory.ContainerInv;
import com.core.scpwms.server.model.inventory.Inventory;
import com.core.scpwms.server.model.lot.LotInfo;
import com.core.scpwms.server.model.lot.LotInputData;
import com.core.scpwms.server.model.outbound.OutboundDelivery;
import com.core.scpwms.server.model.outbound.OutboundDeliveryDetail;
import com.core.scpwms.server.model.outbound.WaveDoc;
import com.core.scpwms.server.model.rules.PickUpRule;
import com.core.scpwms.server.model.task.WarehouseOrder;
import com.core.scpwms.server.model.task.WarehouseTask;
import com.core.scpwms.server.model.warehouse.Bin;
import com.core.scpwms.server.service.common.AllocateHelper;
import com.core.scpwms.server.service.common.AllocatePickingHelper;
import com.core.scpwms.server.service.common.BCManager;
import com.core.scpwms.server.service.common.BinHelper;
import com.core.scpwms.server.service.common.InventoryHelper;
import com.core.scpwms.server.service.common.OrderStatusHelper;
import com.core.scpwms.server.util.DateUtil;
import com.core.scpwms.server.util.MessageUtil;

@SuppressWarnings("all")
public class AllocatePickingHelperImpl extends DefaultBaseManager implements AllocatePickingHelper {

	/** 仓库ID 、多仓库的分配可以并行进行 */
	private Long whId;
	public BinHelper binHelper;
	public BCManager bcManager;
	public InventoryHelper invHelper;
	public OrderStatusHelper orderStatusHelper;
	public AllocateHelper allocateHelper;
	
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

	public BinHelper getBinHelper() {
		return binHelper;
	}

	public void setBinHelper(BinHelper binHelper) {
		this.binHelper = binHelper;
	}

	public OrderStatusHelper getOrderStatusHelper() {
		return this.orderStatusHelper;
	}

	public void setOrderStatusHelper(OrderStatusHelper orderStatusHelper) {
		this.orderStatusHelper = orderStatusHelper;
	}
	
	public AllocateHelper getAllocateHelper() {
		return this.allocateHelper;
	}

	public void setAllocateHelper(AllocateHelper allocateHelper) {
		this.allocateHelper = allocateHelper;
	}
	
	// 按箱分配
	private void allocateDetail4Ns(List<? extends AllocateDocDetail> details, Double maxQty, Double caseIn, Date minExpDate, Date maxExpDate, Date uniExpDate, List<String[]> result) {
		double totalQty = maxQty.doubleValue();
		
		// 查询当前库存
		List<Inventory> invs = this.getInventoriesWithSort(details.get(0), caseIn, minExpDate, maxExpDate, uniExpDate, new String[]{ EnuStoreRole.R1000, EnuStoreRole.R3000, EnuStoreRole.R4040 });

		// 未查到相关库存
		if (invs != null && !invs.isEmpty()) {
			for( AllocateDocDetail detail : details ){
				if( detail.getUnAllocateQty() > 0 ){
					totalQty -= allocateSplitAll(invs, detail, totalQty, details.size() == 1 ?  caseIn : 1D);
				}
				
				if( totalQty  <= 0 )
					break;
			}
		}
	}

	public double allocateDetail(AllocateDocDetail detail, List<String[]> result) {
		List<PickUpRule> rules = getPickUpRules(detail);
		
		double totalQty = 0.0;
		
		// 未找到策略
		if (rules == null || rules.isEmpty()) {
			// 查询当前库存
			List<Inventory> invs = this.getInventoriesWithSort(detail, new String[]{ EnuStoreRole.R1000, EnuStoreRole.R3000, EnuStoreRole.R4040 });

			// 未查到相关库存
			if (invs != null && !invs.isEmpty()) {
				// 拣货分配，累加已分配数量
				totalQty += allocateInventory(invs, detail , -1);
			}
		}
		
		// 按照规则的优先级，循环进行分配
		else{
			for (PickUpRule rule : rules) {
				// 查询当前库存
				List<Inventory> invs = this.getInventoriesWithSort(detail, rule);

				// 未查到相关库存
				if (invs == null || invs.isEmpty()) {
					continue;
				}

				// 拣货分配，累加已分配数量
				totalQty += allocateInventory(invs, detail , -1);
			}
		}

		if (detail.getUnAllocateQty() > 0) {
			result.add(new String[] {
					ExceptionConstant.HAS_DETAIL_NOT_ALL_ALLOCATE,
					"(" + detail.getSku().getCode() + ")" + detail.getSku().getName(),
					detail.getLotData() == null ? "" : detail.getLotData().toString(), 
					"在庫不足数：" + String.valueOf(detail.getUnAllocateQty()) });
		}

		return totalQty;
	}

	/**
	 * 根据单据明细获取规则列表，按照优先级排序
	 * 
	 * @param detail
	 * @return
	 */
	public List<PickUpRule> getPickUpRules(AllocateDocDetail detail) {

		StringBuffer sb = new StringBuffer();

		List<String> params = new ArrayList<String>();
		List<Object> values = new ArrayList<Object>();

		Sku sku = detail.getSku();
		PackageDetail packageDetail = detail.getPackageDetail();

		sb.append("select rule from PickUpRule rule where rule.disabled=false and rule.wh.id =:whId ");
		// 仓库限定
		params.add("whId");
		values.add(detail.getWh().getId());

		// 目标库位限定(拣选至库位),在货主转换单中，可能出现拣选至库位为空的情况
		if (detail.getAllocateDoc().getDescBin() == null
				&& detail.getBin() == null) {
			sb.append(" AND rule.srcBin IS NULL  ");
		} else {
			sb.append(" AND (rule.srcBin IS NULL or (rule.srcBin.id=:srcBinId))  ");
			params.add("srcBinId");
			values.add(detail.getAllocateDoc().getDescBin() == null ? detail
					.getBin().getId() : detail.getAllocateDoc().getDescBin()
					.getId());
		}

		// 货主限定
		sb.append(" AND (rule.plant IS NULL or (rule.plant.id=:plantId))  ");
		params.add("plantId");
		values.add(detail.getAllocateDoc().getPlant().getId());

		// SKU限定
		sb.append(" AND (rule.sku IS NULL or (rule.sku.id=:skuId))  ");
		params.add("skuId");
		values.add(sku.getId());

		// 订单类型限定
		sb.append(" AND (rule.iot IS NULL or (rule.iot.id=:iotId))  ");
		params.add("iotId");
		values.add(detail.getAllocateDoc().getOrderType().getId());

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
		sb.append("      rule.useQtyLimit = false ");
		sb.append("      or ( (rule.qtyLowerLimit IS NULL or rule.qtyLowerLimit = 0.0 OR rule.qtyLowerLimit<=:planQty1 ) ");
		sb.append("  and  ");
		sb.append(" (rule.qtyLimit IS NULL or rule.qtyLimit = 0.0 OR rule.qtyLimit >=:planQty2 ) ");
		sb.append(" ) )  ");

		params.add("planQty1");
		params.add("planQty2");
		values.add(detail.getUnAllocateQty());
		values.add(detail.getUnAllocateQty());

		// 包装线限定

		// sb.append(" AND ( ");
		// sb.append(" rule.usePackLimit = false ");
		// sb.append(" or ( ) ");
		// sb.append(" ) ");
		//
		// params.add("planQty1");
		// params.add("planQty2");
		// values.add(detail.getUnAllocateQty());
		// values.add(detail.getUnAllocateQty());

		// sb.append(" AND ( ");
		// sb.append(" rule.usePackLimit = false ");
		// sb.append(" or ( ( (rule.packageLevel IS NULL or rule.packageLevel =
		// 'PK1000') and :pack1 <= :planQty1 ) ");
		// sb.append(" and ");
		// sb.append(" (rule.qtyLimit IS NULL or rule.qtyLimit = 0.0 OR
		// rule.qtyLimit >=:planQty2 ) ");
		// sb.append(" ) ) ");

		sb.append(" order by  rule.priority ");

		List<PickUpRule> rules = this.commonDao.findByQuery(sb.toString(),
				(String[]) params.toArray(new String[params.size()]),
				(Object[]) values.toArray(new Object[values.size()]));

		List<PickUpRule> result = new ArrayList<PickUpRule>();

		// 包装限定,排除掉不满足包装的
		if (rules != null && !rules.isEmpty()) {
			for (PickUpRule rule : rules) {
				if (rule.getUsePackLimit() == null || !rule.getUsePackLimit()) {
					// 未启用包装折算系数 限定
					result.add(rule);
				} else {
					// 启用包装折算系数限定
					if (!StringUtils.isEmpty(rule.getPackageLevel())) {
						if (!StringUtils.isEmpty(rule.getPackageLevelEnd())) {
							// 包装开始，结束都启用
							double startQty = 1;
							if (EnuPackageLevel.PK1000.equalsIgnoreCase(rule
									.getPackageLevel())) {
								// PK1000
								startQty = packageDetail.getPackageInfo()
										.getP1000().getCoefficient();
							} else if (EnuPackageLevel.PK2100
									.equalsIgnoreCase(rule.getPackageLevel())) {
								// PK2100
								startQty = packageDetail.getPackageInfo()
										.getP2100().getCoefficient();
							} else if (EnuPackageLevel.PK2000
									.equalsIgnoreCase(rule.getPackageLevel())) {
								// PK2000
								startQty = packageDetail.getPackageInfo()
										.getP2000().getCoefficient();
							} else if (EnuPackageLevel.PK3000
									.equalsIgnoreCase(rule.getPackageLevel())) {
								// PK3000
								startQty = packageDetail.getPackageInfo()
										.getP3000().getCoefficient();
							} else if (EnuPackageLevel.PK4000
									.equalsIgnoreCase(rule.getPackageLevel())) {
								// PK4000
								startQty = packageDetail.getPackageInfo()
										.getP4000().getCoefficient();
							}

							double endQty = 1D;
							if (EnuPackageLevel.PK1000.equalsIgnoreCase(rule
									.getPackageLevel())) {
								// PK1000
								endQty = packageDetail.getPackageInfo()
										.getP1000().getCoefficient();
							} else if (EnuPackageLevel.PK2100
									.equalsIgnoreCase(rule.getPackageLevel())) {
								// PK2100
								endQty = packageDetail.getPackageInfo()
										.getP2100().getCoefficient();
							} else if (EnuPackageLevel.PK2000
									.equalsIgnoreCase(rule.getPackageLevel())) {
								// PK2000
								endQty = packageDetail.getPackageInfo()
										.getP2000().getCoefficient();
							} else if (EnuPackageLevel.PK3000
									.equalsIgnoreCase(rule.getPackageLevel())) {
								// PK3000
								endQty = packageDetail.getPackageInfo()
										.getP3000().getCoefficient();
							} else if (EnuPackageLevel.PK4000
									.equalsIgnoreCase(rule.getPackageLevel())) {
								// PK4000
								endQty = packageDetail.getPackageInfo()
										.getP4000().getCoefficient();
							}
							if (detail.getUnAllocateQty() >= startQty
									&& detail.getUnAllocateQty() < endQty) {
								result.add(rule);
							} else {
								continue;
							}
						} else {
							// 包装开始启用 only
							double startQty = 1;
							if (EnuPackageLevel.PK1000.equalsIgnoreCase(rule
									.getPackageLevel())) {
								// PK1000
								startQty = packageDetail.getPackageInfo()
										.getP1000().getCoefficient();
							} else if (EnuPackageLevel.PK2100
									.equalsIgnoreCase(rule.getPackageLevel())) {
								// PK2100
								startQty = packageDetail.getPackageInfo()
										.getP2100().getCoefficient();
							} else if (EnuPackageLevel.PK2000
									.equalsIgnoreCase(rule.getPackageLevel())) {
								// PK2000
								startQty = packageDetail.getPackageInfo()
										.getP2000().getCoefficient();
							} else if (EnuPackageLevel.PK3000
									.equalsIgnoreCase(rule.getPackageLevel())) {
								// PK3000
								startQty = packageDetail.getPackageInfo()
										.getP3000().getCoefficient();
							} else if (EnuPackageLevel.PK4000
									.equalsIgnoreCase(rule.getPackageLevel())) {
								// PK4000
								startQty = packageDetail.getPackageInfo()
										.getP4000().getCoefficient();
							}
							if (detail.getUnAllocateQty() >= startQty) {
								result.add(rule);
							} else {
								continue;
							}
						}
					} else {
						// 包装未启用
						if (!StringUtils.isEmpty(rule.getPackageLevelEnd())) {
							// 结束启用
							double endQty = 1D;
							if (EnuPackageLevel.PK1000.equalsIgnoreCase(rule
									.getPackageLevel())) {
								// PK1000
								endQty = packageDetail.getPackageInfo()
										.getP1000().getCoefficient();
							} else if (EnuPackageLevel.PK2100
									.equalsIgnoreCase(rule.getPackageLevel())) {
								// PK2100
								endQty = packageDetail.getPackageInfo()
										.getP2100().getCoefficient();
							} else if (EnuPackageLevel.PK2000
									.equalsIgnoreCase(rule.getPackageLevel())) {
								// PK2000
								endQty = packageDetail.getPackageInfo()
										.getP2000().getCoefficient();
							} else if (EnuPackageLevel.PK3000
									.equalsIgnoreCase(rule.getPackageLevel())) {
								// PK3000
								endQty = packageDetail.getPackageInfo()
										.getP3000().getCoefficient();
							} else if (EnuPackageLevel.PK4000
									.equalsIgnoreCase(rule.getPackageLevel())) {
								// PK4000
								endQty = packageDetail.getPackageInfo()
										.getP4000().getCoefficient();
							}
							if (detail.getUnAllocateQty() < endQty) {
								result.add(rule);
							} else {
								continue;
							}
						} else {
							// 开始结束都未设定
							result.add(rule);
						}

					}
				}

			}
		}

		return result;
	}
	
	private double getInventoryQty(OutboundDelivery obd, Long skuId, String[] stRoles, String lotInfo){
		
		StringBuffer invHsql = new StringBuffer();

		List<String> params = new ArrayList<String>();
		List<Object> values = new ArrayList<Object>();

		// 构建中转数据结构、排序用
		invHsql.append(" select sum(inv.baseQty - inv.queuedQty) from Inventory inv ");
//		invHsql.append(" left join inv.owner ");
		invHsql.append(" where 1=1");
		invHsql.append(" and inv.wh.id=:whId"); // 仓库条件
		invHsql.append(" and inv.quantInv.quant.sku.id =:skuId "); // 商品条件
		invHsql.append(" and inv.status <> 'FREEZE' "); // 非冻结
		invHsql.append(" and inv.bin.storageType.role in ( :stRoles )"); // 非盘点库位'R1000','R2000','R3000','R4040'
		invHsql.append(" and (inv.bin.lockStatus is null or inv.bin.lockStatus = 'LOCK_T1') "); // 库位锁（没有出锁）
		invHsql.append(" and (inv.baseQty - inv.queuedQty) > 0 "); // 库存数量

		// 处理参数列表 仓库、SKU、折算系数、目标库位
		params.add("whId");
		params.add("skuId");
		params.add("stRoles");

		values.add(obd.getWh().getId());
		values.add(skuId);
		values.add(Arrays.asList(stRoles));

		if (obd.getOwner() != null) {
			invHsql.append(" and inv.owner.id=:ownerId ");// 货主（分布场所）
			params.add("ownerId");
			values.add(obd.getOwner().getId());
		}
		else{
			invHsql.append(" and inv.owner.id is null ");// 货主（分布场所）
		}

		// 库存状态(不良品出库单)
		if ("OB_UN".equals(obd.getOrderType().getCode())) {
			invHsql.append(" and inv.status <> 'AVAILABLE' ");//非良品
		}
		else {
			invHsql.append(" and inv.status = 'AVAILABLE' ");//良品
		}
		
		// 指定批次发货
		if( lotInfo != null && lotInfo.length() > 0 ){
			invHsql.append(" and inv.quantInv.quant.trackSeq = :poNumber ");//PO单号
			params.add("poNumber");
			values.add(lotInfo);
		}
		
		Double result = (Double)commonDao.findByQueryUniqueResult(invHsql.toString(), params.toArray(new String[params.size()]), values.toArray(new Object[values.size()]));
		
		return result == null ? 0d : result.doubleValue();
	}
	
	// 找一个能满足数量需求的最早的批次
	private Date getExpDate4CannotMix(AllocateDocDetail detail, Double sumQty, Date minExpDate, Date maxExpDate, String[] stRoles) {
		StringBuffer invHsql = new StringBuffer();

		List<String> params = new ArrayList<String>();
		List<Object> values = new ArrayList<Object>();
		
		List<String> sameExpList = new ArrayList<String>();
		
		// 构建中转数据结构、排序用
		invHsql.append(" select min(inv.quantInv.quant.lotData.property1) from Inventory inv ");
		invHsql.append(" where 1=1");
		invHsql.append(" and inv.wh.id=:whId"); // 仓库条件
		invHsql.append(" and inv.owner.id=:ownerId"); // 仓库条件
		invHsql.append(" and inv.quantInv.quant.sku.id =:skuId "); // 商品条件
		invHsql.append(" and inv.bin.storageType.role in ( :stRoles )"); // 非盘点库位'R1000','R2000','R3000','R4040'
		invHsql.append(" and (inv.bin.lockStatus is null or inv.bin.lockStatus = 'LOCK_T1') "); // 库位锁（没有出锁）
		invHsql.append(" and (inv.baseQty - inv.queuedQty) > 0 "); // 库存数量

		// 处理参数列表 仓库、SKU、折算系数、目标库位
		params.add("whId");
		params.add("ownerId");
		params.add("skuId");
		params.add("stRoles");

		values.add(detail.getWh().getId());
		values.add(detail.getOwner().getId());
		values.add(detail.getSku().getId());
		values.add(Arrays.asList(stRoles));
		
		// 指定了MinDate
		if( minExpDate != null ){
			invHsql.append(" and inv.quantInv.quant.lotData.property1 >= :minDate ");
			params.add("minDate");
			values.add(DateUtil.getStringDate(minExpDate, DateUtil.PURE_DATE_FORMAT));
		}
		
		// 指定了MaxDate
		if( maxExpDate != null ){
			invHsql.append(" and inv.quantInv.quant.lotData.property1 <= :maxDate ");
			params.add("maxDate");
			values.add(DateUtil.getStringDate(maxExpDate, DateUtil.PURE_DATE_FORMAT));
		}
		
		// 指定批次发货
		if( detail.getLotData() != null && detail.getLotData().getProperty1() != null && detail.getLotData().getProperty1().length() > 0 ){
			invHsql.append(" and inv.quantInv.quant.lotData.property1 = :property1 ");
			params.add("property1");
			values.add(detail.getLotData().getProperty1());
		}
		
		// 指定库存状态
		if( detail.getInvStatus() != null && detail.getInvStatus().length() > 0 ){
			invHsql.append(" and inv.status = :invStatus ");
			params.add("invStatus");
			values.add(detail.getInvStatus());
		}
		else{
			invHsql.append(" and inv.status = :invStatus ");
			params.add("invStatus");
			values.add(EnuInvStatus.AVAILABLE);
		}
		
		invHsql.append(" group by inv.quantInv.quant.id  ");
		invHsql.append(" having sum(inv.baseQty - inv.queuedQty) >= :sumQty  ");
		
		params.add("sumQty");
		values.add(sumQty);
		
		invHsql.append(" order by min(inv.quantInv.quant.lotData.property1)  ");
		
		List<String> expDateUnis = commonDao.findByQuery(invHsql.toString(), params.toArray(new String[params.size()]), values.toArray(new Object[values.size()]));
		if( expDateUnis != null && expDateUnis.size() > 0){
			return DateUtil.getDate(expDateUnis.get(0), DateUtil.PURE_DATE_FORMAT);
		}
		return null;
	}
	
	private List<Inventory> getInventoriesWithSort(AllocateDocDetail detail, Double minQty, Date minExpDate, Date maxExpDate, Date uniExpDate, String[] stRoles) {
		StringBuffer invHsql = new StringBuffer();

		List<String> params = new ArrayList<String>();
		List<Object> values = new ArrayList<Object>();
		
		List<String> sameExpList = new ArrayList<String>();
		
		// 构建中转数据结构、排序用
		invHsql.append(" from Inventory inv ");
		invHsql.append(" where 1=1");
		invHsql.append(" and inv.wh.id=:whId"); // 仓库条件
		invHsql.append(" and inv.owner.id=:ownerId"); // 仓库条件
		invHsql.append(" and inv.quantInv.quant.sku.id =:skuId "); // 商品条件
		invHsql.append(" and inv.bin.storageType.role in ( :stRoles )"); // 非盘点库位'R1000','R2000','R3000','R4040'
		invHsql.append(" and (inv.bin.lockStatus is null or inv.bin.lockStatus = 'LOCK_T1') "); // 库位锁（没有出锁）
		invHsql.append(" and (inv.baseQty - inv.queuedQty) > 0 "); // 库存数量

		// 处理参数列表 仓库、SKU、折算系数、目标库位
		params.add("whId");
		params.add("ownerId");
		params.add("skuId");
		params.add("stRoles");

		values.add(detail.getWh().getId());
		values.add(detail.getOwner().getId());
		values.add(detail.getSku().getId());
		values.add(Arrays.asList(stRoles));
		
		// 指定最少库存数
		if( minQty != null && minQty > 0 ){
			invHsql.append(" and (inv.baseQty - inv.queuedQty) >= :minQty ");
			params.add("minQty");
			values.add(minQty);
		}
		
		// 指定了MinDate
		if( minExpDate != null ){
			invHsql.append(" and inv.quantInv.quant.lotData.property1 >= :minDate ");
			params.add("minDate");
			values.add(DateUtil.getStringDate(minExpDate, DateUtil.PURE_DATE_FORMAT));
		}
		
		// 指定了MaxDate
		if( maxExpDate != null ){
			invHsql.append(" and inv.quantInv.quant.lotData.property1 <= :maxDate ");
			params.add("maxDate");
			values.add(DateUtil.getStringDate(maxExpDate, DateUtil.PURE_DATE_FORMAT));
		}
		
		// 指定批次发货
		if( uniExpDate != null ){
			invHsql.append(" and inv.quantInv.quant.lotData.property1 = :uniExpDate ");
			params.add("uniExpDate");
			values.add(DateUtil.getStringDate(uniExpDate, DateUtil.PURE_DATE_FORMAT));
		}
		
		// 指定库存状态
		if( detail.getInvStatus() != null && detail.getInvStatus().length() > 0 ){
			invHsql.append(" and inv.status = :invStatus ");
			params.add("invStatus");
			values.add(detail.getInvStatus());
		}
		// 没有指定默认用良品
		else{
			invHsql.append(" and inv.status = :invStatus ");
			params.add("invStatus");
			values.add(EnuInvStatus.AVAILABLE);
		}
		
		// 保值期先进先出,入库日期先进先出,功能区(不良品->一般库区->收货区),库位编号
		invHsql.append(" order by inv.quantInv.quant.dispLot, inv.inboundDate, inv.bin.storageType.role desc, inv.bin.binCode ");
		
		return  commonDao.findByQuery(invHsql.toString(), params.toArray(new String[params.size()]), values.toArray(new Object[values.size()]));
	}
	
	private List<Inventory> getSameExpInvs( List<Inventory> invs, Double allocateQty ){
		if( invs == null || invs.size() == 0 )
			return null;
		
		String expDate = "";
		List<Inventory> newInvs = new ArrayList<Inventory>();
		Double sumQty = 0D;
		
		for( Inventory inv : invs ){
			if( !expDate.equals(inv.getQuantInv().getQuant().getDispLot()) ){
				if( newInvs.size() > 0 && sumQty.longValue() >= allocateQty.longValue() ){
					return newInvs;
				}
				
				newInvs = new ArrayList<Inventory>();
				sumQty = 0D;
			}
			newInvs.add(inv);
			sumQty = inv.getAvailableQty();
		}
		
		if( newInvs.size() > 0 && sumQty.longValue() >= allocateQty.longValue() ){
			return newInvs;
		}
		return null;
	}
	
	private List<Inventory> getInventoriesWithSort(AllocateDocDetail detail, String[] stRoles) {
		return getInventoriesWithSort(detail, null, detail.getExpDateMin(), detail.getExpDateMax(), null, stRoles);
	}

	/**
	 * 按照拣选规则和单据行明细，取得Inventory列表并排序 排序需要考虑因素 1、周转策略(EnuTurnOverConf) 2、历史库位优先
	 * 3、库存数量 针对托盘库位（拆托分配：允许的话则分配托盘库存时托盘库存数量从小到大分配（托盘被过多的拆托）；如果不允许拆托，则数量从大到小排列（
	 * 避免错误的分配不足）） 4、库位序号（目前使用库位编号） 5、包装（大包装排在前面、减少拣选次数）
	 * 
	 * @param detail
	 * @param rule
	 * @return
	 */
	public List<Inventory> getInventoriesWithSort(AllocateDocDetail detail, PickUpRule rule) {

		StringBuffer invHsql = new StringBuffer();

		List<String> params = new ArrayList<String>();
		List<Object> values = new ArrayList<Object>();

		// 构建中转数据结构、排序用
		invHsql.append("select new com.core.scpwms.server.domain.AllocateInventoryInfo(inv,sku.properties.dayOfExpiry,bin) from Inventory inv ");
		invHsql.append(" inner join inv.bin bin ");
		invHsql.append(" inner join inv.currentPack currentPack ");
		invHsql.append(" inner join inv.quantInv quantInv ");
		invHsql.append(" inner join inv.quantInv.quant quant ");
		invHsql.append(" inner join inv.quantInv.quant.sku sku ");
		invHsql.append(" left join inv.owner ");
		
		// 处理库位和库位组
		if (rule.getDescBin() != null) {
		} else {
			invHsql.append(" inner join inv.bin.bins bg ");
		}
		invHsql.append(" where 1=1");
		invHsql.append(" and inv.wh.id=:whId"); // 仓库条件
		invHsql.append(" and sku.id =:skuId "); // 商品条件
		invHsql.append(" and inv.status <> 'FREEZE' "); // 非冻结
		invHsql.append(" and inv.status <> 'QC' "); // 非待质检
		invHsql.append(" and inv.status <> 'SCRAP' "); // 非破损
		invHsql.append(" and inv.bin.storageType.role <> 'R8020' "); // 非盘点库位
		invHsql.append(" and inv.bin.storageType.role <> 'R8040' "); // 非辅料
		invHsql.append(" and inv.bin.storageType.role <> 'R5000' "); // 非备货
		invHsql.append(" and inv.bin.storageType.role <> 'R4020' "); // 非质检
		invHsql.append(" and inv.bin.storageType.role <> 'R8010' "); // 非加工
		invHsql.append(" and (bin.lockStatus is null or bin.lockStatus = 'LOCK_T1') "); // 库位锁（没有出锁）
		invHsql.append(" and bin.disabled = false "); // 可用
		invHsql.append(" and (inv.baseQty - inv.queuedQty) > 0 "); // 库存数量
		invHsql.append(" and currentPack.coefficient >=:coefficient "); // 库存包装大于等于发货包装
		invHsql.append(" and bin.id <>:currentBinId "); // 不等于拣选至库位

		// 处理库位和库位组
		if (rule.getDescBin() != null) {
		} else {
			invHsql.append(" and bg.id=")
					.append(rule.getDescBinGroup().getId()).append(" ");
		}

		// 处理库位和库位组
		if (rule.getDescBin() != null) {
			invHsql.append(" and bin.id=").append(rule.getDescBin().getId())
					.append(" ");
		} else {
			invHsql.append(" and (bin in elements(bg.bins) ) ");
		}

		// 处理参数列表 仓库、SKU、折算系数、目标库位
		params.add("whId");
		params.add("skuId");
		params.add("coefficient");
		params.add("currentBinId");

		values.add(detail.getWh().getId());
		values.add(detail.getSku().getId());
		values.add(detail.getPackageDetail().getCoefficient());
		values.add(detail.getAllocateDoc().getDescBin() == null ? (detail
				.getBin() == null ? 0L : detail.getBin().getId()) : detail
				.getAllocateDoc().getDescBin().getId());// 在货主转换单中可能出现拣选至库位为空的情况，这里放个0

		if (detail.getOwner() != null) {
			invHsql.append(" and inv.owner.id=:ownerId ");// 货主（分布场所）
			params.add("ownerId");
			values.add(detail.getOwner().getId());
		}
		else{
			invHsql.append(" and inv.owner.id is null ");// 货主（分布场所）
		}

		// 库存数量过滤，只查询满足需求库存（单据明细需求数量）的库存出来
		if (rule.getEnoughInv() != null && rule.getEnoughInv()) {
			invHsql.append(" and (inv.baseQty - inv.queuedQty) >=:needQty "); // 可用库存数量
			// >=
			// 需求数量
			params.add("needQty");
			values.add(detail.getUnAllocateQty());
		}

		// 库存状态
		if (StringUtils.isEmpty(rule.getInvStatus())) {
		} else {
			invHsql.append(" and inv.status=:invStatus ");
			params.add("invStatus");
			values.add(rule.getInvStatus());
		}

		// 批次处理，如果单据明细录入了批次属性，那么默认按照批次属性进行发货
		LotInputData lotData = detail.getLotData();
		LotInfo lotRule = detail.getSku().getProperties().getLotInfo();

		if (lotData != null && lotRule != null) {
			// 批次(1)
			if (!StringUtils.isEmpty(lotData.getProperty1())) {
				if (!EnuLotFieldType.LOTFIELD_1.equalsIgnoreCase(lotRule
						.getLdi1().getInputType())) {
					invHsql.append(" and quant.lotData.property1 =:property1 "); // 批次属性
					params.add("property1");
					values.add(lotData.getProperty1());
				} else {
					invHsql.append(" and quant.lotData.property1 is null "); // 批次属性
				}
			} else {
				invHsql.append(" and quant.lotData.property1 is null "); // 批次属性
			}
			if (!StringUtils.isEmpty(lotData.getProperty2())) {
				if (!EnuLotFieldType.LOTFIELD_1.equalsIgnoreCase(lotRule
						.getLdi2().getInputType())) {
					invHsql.append(" and quant.lotData.property2 =:property2 "); // 批次属性
					params.add("property2");
					values.add(lotData.getProperty2());
				} else {
					invHsql.append(" and quant.lotData.property2 is null "); // 批次属性
				}
			} else {
				invHsql.append(" and quant.lotData.property2 is null "); // 批次属性
			}
			if (!StringUtils.isEmpty(lotData.getProperty3())) {
				if (!EnuLotFieldType.LOTFIELD_1.equalsIgnoreCase(lotRule
						.getLdi3().getInputType())) {
					invHsql.append(" and quant.lotData.property3 =:property3 "); // 批次属性
					params.add("property3");
					values.add(lotData.getProperty3());
				} else {
					invHsql.append(" and quant.lotData.property3 is null "); // 批次属性
				}
			} else {
				invHsql.append(" and quant.lotData.property3 is null "); // 批次属性
			}
			if (!StringUtils.isEmpty(lotData.getProperty4())) {
				if (!EnuLotFieldType.LOTFIELD_1.equalsIgnoreCase(lotRule
						.getLdi4().getInputType())) {
					invHsql.append(" and quant.lotData.property4 =:property4 "); // 批次属性
					params.add("property4");
					values.add(lotData.getProperty4());
				} else {
					invHsql.append(" and quant.lotData.property4 is null "); // 批次属性
				}
			} else {
				invHsql.append(" and quant.lotData.property4 is null "); // 批次属性
			}
			if (!StringUtils.isEmpty(lotData.getProperty5())) {
				if (!EnuLotFieldType.LOTFIELD_1.equalsIgnoreCase(lotRule
						.getLdi5().getInputType())) {
					invHsql.append(" and quant.lotData.property5 =:property5 "); // 批次属性
					params.add("property5");
					values.add(lotData.getProperty5());
				} else {
					invHsql.append(" and quant.lotData.property5 is null "); // 批次属性
				}
			} else {
				invHsql.append(" and quant.lotData.property5 is null "); // 批次属性
			}
			if (!StringUtils.isEmpty(lotData.getProperty6())) {
				if (!EnuLotFieldType.LOTFIELD_1.equalsIgnoreCase(lotRule
						.getLdi6().getInputType())) {
					invHsql.append(" and quant.lotData.property6 =:property6 "); // 批次属性
					params.add("property6");
					values.add(lotData.getProperty6());
				} else {
					invHsql.append(" and quant.lotData.property6 is null "); // 批次属性
				}
			} else {
				invHsql.append(" and quant.lotData.property6 is null "); // 批次属性
			}
			if (!StringUtils.isEmpty(lotData.getProperty7())) {
				if (!EnuLotFieldType.LOTFIELD_1.equalsIgnoreCase(lotRule
						.getLdi7().getInputType())) {
					invHsql.append(" and quant.lotData.property7 =:property7 "); // 批次属性
					params.add("property7");
					values.add(lotData.getProperty7());
				} else {
					invHsql.append(" and quant.lotData.property7 is null "); // 批次属性
				}
			} else {
				invHsql.append(" and quant.lotData.property7 is null "); // 批次属性
			}
			if (!StringUtils.isEmpty(lotData.getProperty8())) {
				if (!EnuLotFieldType.LOTFIELD_1.equalsIgnoreCase(lotRule
						.getLdi8().getInputType())) {
					invHsql.append(" and quant.lotData.property8 =:property8 "); // 批次属性
					params.add("property8");
					values.add(lotData.getProperty8());
				} else {
					invHsql.append(" and quant.lotData.property8 is null "); // 批次属性
				}
			} else {
				invHsql.append(" and quant.lotData.property8 is null "); // 批次属性
			}
			if (!StringUtils.isEmpty(lotData.getProperty9())) {
				if (!EnuLotFieldType.LOTFIELD_1.equalsIgnoreCase(lotRule
						.getLdi9().getInputType())) {
					invHsql.append(" and quant.lotData.property9 =:property9 "); // 批次属性
					params.add("property9");
					values.add(lotData.getProperty9());
				} else {
					invHsql.append(" and quant.lotData.property9 is null "); // 批次属性
				}
			} else {
				invHsql.append(" and quant.lotData.property9 is null "); // 批次属性
			}
			if (!StringUtils.isEmpty(lotData.getProperty10())) {
				if (!EnuLotFieldType.LOTFIELD_1.equalsIgnoreCase(lotRule
						.getLdi10().getInputType())) {
					invHsql.append(" and quant.lotData.property10 =:property10 "); // 批次属性
					params.add("property10");
					values.add(lotData.getProperty10());
				} else {
					invHsql.append(" and quant.lotData.property10 is null "); // 批次属性
				}
			} else {
				invHsql.append(" and quant.lotData.property10 is null "); // 批次属性
			}
		} else {
			invHsql.append(" and quant.lotData.property1 is null "); // 批次属性
			invHsql.append(" and quant.lotData.property2 is null "); // 批次属性
			invHsql.append(" and quant.lotData.property3 is null "); // 批次属性
			invHsql.append(" and quant.lotData.property4 is null "); // 批次属性
			invHsql.append(" and quant.lotData.property5 is null "); // 批次属性
			invHsql.append(" and quant.lotData.property6 is null "); // 批次属性
			invHsql.append(" and quant.lotData.property7 is null "); // 批次属性
			invHsql.append(" and quant.lotData.property8 is null "); // 批次属性
			invHsql.append(" and quant.lotData.property9 is null "); // 批次属性
			invHsql.append(" and quant.lotData.property10 is null "); // 批次属性
		}

		List<AllocateInventoryInfo> invInfos = (List<AllocateInventoryInfo>) this.commonDao
				.findByQuery(invHsql.toString(),
						(String[]) params.toArray(new String[params.size()]),
						(Object[]) values.toArray(new Object[values.size()]));

		if (invInfos == null || invInfos.isEmpty()) {
			return null;
		}

		// 按照周转规则进行排序

		Sku sku = detail.getSku();
		List<InventorySortCondition> sortConditions = new ArrayList<InventorySortCondition>();

		String toc = sku.getProperties().getToc();

		// 库存分配(清库位优先/拣选次数优先)，默认清库位优先
		String invAllocate = "";

		if (StringUtils.isEmpty(toc)) {
			// 如果商品没有指定周转策略，则使用SKU的货主的周转策略
			if (sku.getPlant().getWhingProperties() != null
					&& sku.getPlant().getWhingProperties().getToc() != null) {
				toc = sku.getPlant().getWhingProperties().getToc();
			}
		}

		// 库存分配(清库位优先/拣选次数优先)
		if (sku.getPlant().getWhingProperties() != null
				&& !StringUtils.isEmpty(sku.getPlant().getWhingProperties()
						.getInvAllocate())) {
			invAllocate = sku.getPlant().getWhingProperties().getInvAllocate();
		}

		if (!StringUtils.isEmpty(toc)) {
			if (EnuTurnOverConf.TOC001.equalsIgnoreCase(toc)) {
				sortConditions.add(new InventorySortCondition(1, "inboundDate",
						"ASC"));
			} else if (EnuTurnOverConf.TOC002.equalsIgnoreCase(toc)) {
				sortConditions.add(new InventorySortCondition(1, "inboundDate",
						"DESC"));
			} else if (EnuTurnOverConf.TOC003.equalsIgnoreCase(toc)) {
				sortConditions.add(new InventorySortCondition(1, "expiryDate",
						"ASC"));
			} else if (EnuTurnOverConf.TOC003.equalsIgnoreCase(toc)) {
				sortConditions.add(new InventorySortCondition(1, "expiryDate",
						"DESC"));
			}
		} else {
			// 如果没有配置，则默认按照TOC001进行处理（存货日期先进先出）
			sortConditions.add(new InventorySortCondition(1, "inboundDate",
					"ASC"));
		}

		// 此处托盘为整托托盘、非混托。如果出现混托，需要额外处理
		// 凤凰一力这里商品上没有这个属性描述，所以，直接取货主的。
		// Boolean isSplitPallet = sku.getProperties().getSplitPallet();
		// 商品拆托盘未指定，取货主对应设定。（其实这情况不存在，Since SkuEdit内，checbox）

		Boolean isSplitPallet = null;

		// 凤凰一力没有维护拆托盘的属性，所以，此处CMD掉
		// if (sku.getPlant().getOutBountProperties() != null &&
		// sku.getPlant().getOutBountProperties().getSplitPallet() != null) {
		// isSplitPallet =
		// sku.getPlant().getOutBountProperties().getSplitPallet();
		// }

		if (isSplitPallet != null) {
			// 拆分托盘优先级高于 拣选次数设定
			if (isSplitPallet) {
				// 如果货主允许拆托分配，则分配托盘库存时托盘库存数量从小到大分配（避免托盘被过多的拆托）；
				sortConditions.add(new InventorySortCondition(2,
						"avaliableQuantity", "ASC"));
			} else {
				// 如果不允许拆托，则数量从大到小排列
				sortConditions.add(new InventorySortCondition(2,
						"avaliableQuantity", "DESC"));
			}
		} else {
			if (!StringUtils.isEmpty(invAllocate)) {
				if (!"clearBin".equalsIgnoreCase(invAllocate)) {
					sortConditions.add(new InventorySortCondition(2,
							"avaliableQuantity", "DESC"));
				} else {
					sortConditions.add(new InventorySortCondition(2,
							"avaliableQuantity", "ASC"));
				}
			} else {
				// 默认不许拆托盘,清库位优先
				sortConditions.add(new InventorySortCondition(2,
						"avaliableQuantity", "ASC"));
			}
		}

		// 参考库位编号顺序排列,保证一个库位的库存在一起
		sortConditions.add(new InventorySortCondition(4, "binCode", "ASC"));
		// 将托盘库存和非托盘库存分开排序，
		// 排序结果是：'','1','2','3',...[非托盘在前，托盘在后]
		sortConditions.add(new InventorySortCondition(5, "palletNo", "ASC"));
		// 将大包装放前面
		sortConditions
				.add(new InventorySortCondition(6, "coefficient", "DESC"));

		// 排序处理
		ComparatorChain chain = new ComparatorChain();
		for (InventorySortCondition sortCondition : sortConditions) {
			if (sortCondition.getSortMethod().equals("DESC")) {
				chain.addComparator(new ReverseComparator(new BeanComparator(
						sortCondition.getSortKey())));
			} else {
				chain.addComparator(new BeanComparator(sortCondition
						.getSortKey()));
			}
		}
		Collections.sort(invInfos, chain);

		/**
		 * a、库位上有整托和非整托时，如果待拣数量大于整托，则先分配整托； b、库位上有非整托时，如果待拣数量不够整托，先分配非整托；
		 * c、库位上托盘分配后，如果分配数量仍不足，则查找非托盘散货（inventory中lp为''的库存记录），按包装大小顺序分配；
		 */
		// 将托盘库存排到非托盘库存前
		List<Inventory> tempInvs1 = new ArrayList<Inventory>();
		List<Inventory> tempInvs2 = new ArrayList<Inventory>();

		for (AllocateInventoryInfo invInfo : invInfos) {

			if ("1".equalsIgnoreCase(invInfo.getPalletNo())) {
				tempInvs1.add(invInfo.getInv());
			} else {
				tempInvs2.add(invInfo.getInv());
			}
		}
		tempInvs1.addAll(tempInvs2);

		return tempInvs1;
	}

	public double allocateInventory(List<Inventory> invs, AllocateDocDetail detail, int isFullPallet) {

		// 分配数量
		double allocateQty = 0.0;
		// 拆包装和托盘分配
		allocateQty = allocateSplitAll(invs, detail, isFullPallet);

		return allocateQty;
	}
	
	/**
	 * 拆分托盘、拆分包装
	 * 
	 * @return
	 */
	public double allocateSplitAll(List<Inventory> invs, AllocateDocDetail detail, double maxQty, double allocateUnitQty) {

		double totalPickQty = 0.0;
		// 对库存循环处理、如果允许多发、要把整托暂时跳过
		for (Inventory inventory : invs) {
			if ((new Double(inventory.getAvaliableQuantity())).intValue() < (new Double(allocateUnitQty)).intValue()) {
				continue;
			}
			
			double unallcateQuantity = 0.0;
			unallcateQuantity = detail.getUnAllocateQty();
			
			if( unallcateQuantity > maxQty )
				unallcateQuantity = maxQty;

			if (unallcateQuantity > 0) {
				// 可以分配的量，然后执行分配
				double allcateQuantity = 0d;
				
				if (unallcateQuantity >= inventory.getAvaliableQuantity()) {
					// 库存可用量小于需求量，分配库存量
					int caseQty = (new Double(inventory.getAvaliableQuantity())).intValue() / (new Double(allocateUnitQty)).intValue();
					allcateQuantity = caseQty * allocateUnitQty;
				} else {
					allcateQuantity = unallcateQuantity;
				}
				
				if (allcateQuantity > 0) {
					// 库存分配
					invHelper.allocateInv(inventory, allcateQuantity);
					// 单据分配
					allocateDetail(detail, allcateQuantity);
					// 创建WT
					createWarehouseTask(detail, inventory, allcateQuantity, -1);
					// 累计总量
					totalPickQty += allcateQuantity;
					// 这轮能分配的数
					maxQty -= allcateQuantity;
				}
			}
			
			if (detail.getUnAllocateQty() <= 0) 
				return totalPickQty;
			
			if( maxQty <= 0 )
				return totalPickQty;
		}
		
		return totalPickQty;
	}

	/**
	 * 拆分托盘、拆分包装
	 * 
	 * @return
	 */
	public double allocateSplitAll(List<Inventory> invs, AllocateDocDetail detail, int isFullPallet) {

		// AllocateDoc doc = detail.getAllocateDoc();
		double totalPickQty = 0.0;
		// 对库存循环处理、如果允许多发、要把整托暂时跳过
		for (Inventory inventory : invs) {
			if (inventory.getAvaliableQuantity() <= 0.0) {
				continue;
			}
			double unallcateQuantity = 0.0;
			unallcateQuantity = detail.getUnAllocateQty();

			if (unallcateQuantity > 0) {
				// 可以分配的量，然后执行分配
				double allcateQuantity = 0d;
				if (unallcateQuantity >= inventory.getAvaliableQuantity()) {
					// 库存可用量小于需求量，分配库存量
					allcateQuantity = inventory.getAvaliableQuantity();
				} else {
					allcateQuantity = unallcateQuantity;
				}
				if (allcateQuantity > 0) {
					// 库存分配
					invHelper.allocateInv(inventory, allcateQuantity);
					// 单据分配
					allocateDetail(detail, allcateQuantity);
					// 创建WT
					createWarehouseTask(detail, inventory, allcateQuantity, isFullPallet);
					// 累计总量
					totalPickQty += allcateQuantity;
				}
			}
			if (detail.getUnAllocateQty() <= 0) {
				return totalPickQty;
			}
		}
		return totalPickQty;
	}
	
	public void unallocateDetail( AllocateDocDetail detail, double allocateQty ){
		detail.unallocate(allocateQty);

		// 货主转换
		if (detail instanceof OwnerChangeDocDetail) {
			//TODO
		}
		// 出库单
		else if (detail instanceof OutboundDeliveryDetail) {
			OutboundDeliveryDetail obdDetail = (OutboundDeliveryDetail) detail;
			OutboundDelivery obd = obdDetail.getObd();
			WaveDoc waveDoc = obd.getWaveDoc();// 可能为空

			if ((EnuOutboundDeliveryDetailStatus.STATUS400.equals(obdDetail.getStatus())) && obdDetail.getUnAllocateQty() > 0) {
				obdDetail.setStatus(EnuOutboundDeliveryDetailStatus.STATUS310);
			}

			if (EnuOutboundDeliveryDetailStatus.STATUS310.equals(obdDetail.getStatus()) && obdDetail.getAllocateQty() <= 0) {
				obdDetail.setStatus(EnuOutboundDeliveryDetailStatus.STATUS210);
			}

			if ((EnuOutboundDeliveryStatus.STATUS400.equals(obd.getStatus())) && obd.getUnAllocateQty() > 0) {
				orderStatusHelper.changeStatus(obd, EnuOutboundDeliveryStatus.STATUS310);
			}

			if (EnuOutboundDeliveryStatus.STATUS310.equals(obd.getStatus()) && obd.getAllocateQty() <= 0) {
				orderStatusHelper.changeStatus(obd, EnuOutboundDeliveryStatus.STATUS210);
			}

		}
		// 批次调整
		else if (detail instanceof LotChangeDocDetail) {
			// TODO
		}
		// TODO
	}

	@Override
	public void allocateDetail(AllocateDocDetail detail, double allocateQty) {
		detail.allocate(null, allocateQty);

		// 货主转换
		if (detail instanceof OwnerChangeDocDetail) {
			OwnerChangeDocDetail ownerChangeDocDetail = (OwnerChangeDocDetail) detail;
			OwnerChangeDoc ownerChangeDoc = ownerChangeDocDetail.getOwnerChangeDoc();

			if (EnuOwnerChangeDocDetailStatus.PUBLISH.equals(ownerChangeDocDetail.getStatus())
					&& ownerChangeDocDetail.getAllocateQty() > 0) {
				ownerChangeDocDetail.setStatus(EnuOwnerChangeDocDetailStatus.ALLOCATING);
			}

			if (EnuOwnerChangeDocDetailStatus.ALLOCATING
					.equals(ownerChangeDocDetail.getStatus())
					&& ownerChangeDocDetail.getUnAllocateQty() <= 0) {
				ownerChangeDocDetail
						.setStatus(EnuOwnerChangeDocDetailStatus.ALLOCATED);
			}

			if (EnuOwnerChangeDocStatus.PUBLISH.equals(ownerChangeDoc
					.getStatus()) && ownerChangeDoc.getAllocateQty() > 0) {
				orderStatusHelper.changeStatus(ownerChangeDoc,
						EnuOwnerChangeDocStatus.ALLOCATING);
			}

			if (EnuOwnerChangeDocStatus.ALLOCATING.equals(ownerChangeDoc.getStatus()) && ownerChangeDoc.getUnAllocateQty() <= 0) {
				orderStatusHelper.changeStatus(ownerChangeDoc, EnuOwnerChangeDocStatus.ALLOCATED);
			}
		}
		// 出库单
		else if (detail instanceof OutboundDeliveryDetail) {
			OutboundDeliveryDetail obdDetail = (OutboundDeliveryDetail) detail;
			OutboundDelivery obd = obdDetail.getObd();
			WaveDoc waveDoc = obd.getWaveDoc();// 可能为空

			if ((EnuOutboundDeliveryDetailStatus.STATUS200.equals(obdDetail.getStatus()) 
					|| EnuOutboundDeliveryDetailStatus.STATUS210.equals(obdDetail.getStatus())) 
					&& obdDetail.getAllocateQty() > 0) {
				obdDetail.setStatus(EnuOutboundDeliveryDetailStatus.STATUS310);
			}

			if (EnuOutboundDeliveryDetailStatus.STATUS310.equals(obdDetail.getStatus()) && obdDetail.getUnAllocateQty() <= 0) {
				obdDetail.setStatus(EnuOutboundDeliveryDetailStatus.STATUS400);
			}

			if ((EnuOutboundDeliveryStatus.STATUS200.equals(obd.getStatus()) 
					|| EnuOutboundDeliveryStatus.STATUS210.equals(obd.getStatus())) 
					&& obd.getAllocateQty() > 0) {
				orderStatusHelper.changeStatus(obd, EnuOutboundDeliveryStatus.STATUS310);
			}

			if (EnuOutboundDeliveryStatus.STATUS310.equals(obd.getStatus()) && obd.getUnAllocateQty() <= 0) {
				orderStatusHelper.changeStatus(obd, EnuOutboundDeliveryStatus.STATUS400);
			}

			if (waveDoc != null) {
				waveDoc.addAllocatedQty(allocateQty);
			}

		}
		// 批次调整
		else if (detail instanceof LotChangeDocDetail) {
			LotChangeDocDetail lotChangeDocDetail = (LotChangeDocDetail) detail;
			LotChangeDoc lotChangeDoc = lotChangeDocDetail.getLotChangeDoc();

			if (EnuLotChangeDocDetailStatus.PUBLISH.equals(lotChangeDocDetail
					.getStatus()) && lotChangeDocDetail.getAllocateQty() > 0) {
				lotChangeDocDetail
						.setStatus(EnuLotChangeDocDetailStatus.ALLOCATING);
			}

			if (EnuLotChangeDocDetailStatus.ALLOCATING
					.equals(lotChangeDocDetail.getStatus())
					&& lotChangeDocDetail.getUnAllocateQty() <= 0) {
				lotChangeDocDetail
						.setStatus(EnuLotChangeDocDetailStatus.ALLOCATED);
			}

			if (EnuLotChangeDocStatus.PUBLISH.equals(lotChangeDoc.getStatus())
					&& lotChangeDoc.getAllocateQty() > 0) {
				orderStatusHelper.changeStatus(lotChangeDoc,
						EnuLotChangeDocStatus.ALLOCATING);
			}

			if (EnuOwnerChangeDocStatus.ALLOCATING.equals(lotChangeDoc
					.getStatus()) && lotChangeDoc.getUnAllocateQty() <= 0) {
				orderStatusHelper.changeStatus(lotChangeDoc,
						EnuLotChangeDocStatus.ALLOCATED);
			}
		}
		// TODO
	}

	@Override
	public void createWarehouseTask(AllocateDocDetail detail,Inventory inventory, double quantity) {
		WarehouseTask wt = new WarehouseTask(inventory.getId());

		wt.setWh(detail.getWh());
		wt.setDetail(detail);
//		wt.setTaskSequence(bcManager.getTaskSeq(wt.getWh().getCode()));// Task号
		wt.setInvInfo(inventory.getInventoryInfo());
		wt.addPlanQty(quantity);
		wt.setDescBin(detail.getBin());// 目标库位
		wt.setProcessType(detail.getTaskProcessType());
		wt.setCreateInfo(new CreateInfo(UserHolder.getUser()));

		detail.getTasks().add(wt);
		this.commonDao.store(wt);

		orderStatusHelper.changeStatus(wt, EnuWarehouseOrderStatus.PUBLISH);
	}
	
	private void createWarehouseTask(AllocateDocDetail detail,Inventory inventory, double quantity, int isFullPallet) {
		WarehouseTask wt = new WarehouseTask(inventory.getId());

		wt.setWh(detail.getWh());
		wt.setDetail(detail);
//		wt.setTaskSequence(bcManager.getTaskSeq(wt.getWh().getCode()));// Task号
		wt.setInvInfo(inventory.getInventoryInfo());
		wt.addPlanQty(quantity);
		wt.setDescBin(detail.getBin());// 目标库位
		wt.setProcessType(detail.getTaskProcessType());
		wt.setCreateInfo(new CreateInfo(UserHolder.getUser()));
		wt.setIsFullPallet(isFullPallet);
		this.commonDao.store(wt);

		orderStatusHelper.changeStatus(wt, EnuWarehouseOrderStatus.PUBLISH);
	}

	@Override
	public void unAllocateProDoc(List<Long> ids) {
		return ;
	}

	@Override
	public void unAllocateObdDoc(List<Long> ids) {
		// TODO
	}

	@Override
	public List<String[]> allocateObdDoc(List<Long> ids) {
		// TODO
		return null;
	}

	@Override
	public void unAllocateInvApplyDoc(List<Long> ids) {
		// TODO
	}

	@Override
	public List<String[]> allocateOwnerDoc(List<Long> ids, Long whId) {
		List<String[]> result = new ArrayList<String[]>();
		this.whId = whId;

		// 仓库ID 、多仓库的分配可以并行进行
		synchronized (this.whId) {
			// 单条操作，便于消息提示
			Long id = ids.get(0);

			// 分配单据获得
			OwnerChangeDoc doc = this.commonDao.load(OwnerChangeDoc.class, id);

			if (doc.getUnAllocateQty() > 0) {
				for (OwnerChangeDocDetail detail : doc.getDetails()) {
					allocateDetail(detail, result);
				}
			}
		}
		return result;
	}

	@Override
	public List<String[]> allocateOwnerDoc(List<Long> ids) {
		List<String[]> result = new ArrayList<String[]>();
		this.whId = WarehouseHolder.getWarehouse().getId();

		// 仓库ID 、多仓库的分配可以并行进行
		synchronized (this.whId) {
			// 单条操作，便于消息提示
			Long id = ids.get(0);

			// 分配单据获得
			OwnerChangeDoc doc = this.commonDao.load(OwnerChangeDoc.class, id);

			if (doc.getUnAllocateQty() > 0) {
				for (OwnerChangeDocDetail detail : doc.getDetails()) {
					allocateDetail(detail, result);
				}
			}
		}

		if (result.size() > 0) {
			updateStatusBar(
					ExceptionConstant.HAS_DETAIL_NOT_ALL_ALLOCATE_SEE_DETAIL,
					MessageUtil.formatDetailMes(result), Boolean.TRUE);
		}
		return result;
	}

	@Override
	public List<String[]> allocateLotChangeDocDetail(Long detailId, Long whId) {
		List<String[]> result = new ArrayList<String[]>();
		this.whId = whId;

		// 仓库ID 、多仓库的分配可以并行进行
		synchronized (this.whId) {
			// 分配单据获得
			LotChangeDocDetail detail = this.commonDao.load(
					LotChangeDocDetail.class, detailId);

			if (detail.getUnAllocateQty() > 0) {
				allocateDetail(detail, result);
			}
		}
		return result;
	}

	@Override
	public void unAllocateOwnerDoc(List<Long> ids) {
		// 未完成任务查询
		for (Long id : ids) {
			OwnerChangeDoc doc = commonDao.load(OwnerChangeDoc.class, id);

			for (OwnerChangeDocDetail detail : doc.getDetails()) {
				List<WarehouseTask> toDeleteTasks = new ArrayList<WarehouseTask>();

				for (WarehouseTask task : detail.getTasks()) {
					if (EnuWarehouseOrderStatus.CLOSE.equals(task.getStatus())) {
						continue;
					}

					// Task中还未处理的数量
					double unExecuteQty = task.getUnExecuteQty();

					if (unExecuteQty > 0) {
						Inventory inv = invHelper.getInv(task.getInvInfo());
						if (inv == null) {
							throw new BusinessException(
									ExceptionConstant.INVENTORY_NOT_FOUND);
						}
						// 库存取消分配
						invHelper.unAllocateInv(inv, unExecuteQty);

						// 修改Task的计划数量
						task.removePlanQty(unExecuteQty);

						// 计划数为0的Task删除
						if (task.getPlanQty() <= 0) {
							toDeleteTasks.add(task);
						}
						// 执行中状态的Task，因为剩余的未拣选的数量被取消了，状态改为执行完成
						else if (EnuWarehouseOrderStatus.EXECUTE.equals(task
								.getStatus())) {
							orderStatusHelper.changeStatus(task,
									EnuWarehouseOrderStatus.CLOSE);
						}

						// 修改加工明细和单头的分配数
						detail.removeAllocateQty(unExecuteQty);
					}
				}

				if (toDeleteTasks.size() > 0) {
					detail.getTasks().removeAll(toDeleteTasks);
					commonDao.deleteAll(toDeleteTasks);
				}

				// 修改加工明细的状态,如果分配数为0，状态退回到发行，如果分配数不为0，说明已经有部分task被执行了，状态保持不变
				if (!EnuOwnerChangeDocDetailStatus.PUBLISH.equals(detail
						.getStatus())
						&& detail.getAllocateQty().doubleValue() <= 0) {
					detail.setStatus(EnuOwnerChangeDocDetailStatus.PUBLISH);
				}
			}

			// 修改加工单头的状态,如果分配数为0，状态退回到发行，如果分配数不为0，说明已经有部分task被执行了，状态保持不变
			if (!EnuOwnerChangeDocStatus.PUBLISH.equals(doc.getStatus())
					&& doc.getAllocateQty().doubleValue() <= 0) {
				orderStatusHelper.changeStatus(doc,
						EnuOwnerChangeDocStatus.PUBLISH);
			}

			this.commonDao.store(doc);
		}

	}

	/**
	 * 
	 * <p>
	 * 用于排序的查询条件内部类
	 * </p>
	 */
	class InventorySortCondition {
		private int sortIndex;
		private String sortKey;
		private String sortMethod;

		public InventorySortCondition(int index, String key, String method) {
			super();
			this.sortIndex = index;
			this.sortKey = key;
			this.sortMethod = method;
		}

		public int getSortIndex() {
			return sortIndex;
		}

		public void setSortIndex(int sortIndex) {
			this.sortIndex = sortIndex;
		}

		public String getSortKey() {
			return sortKey;
		}

		public void setSortKey(String sortKey) {
			this.sortKey = sortKey;
		}

		public String getSortMethod() {
			return sortMethod;
		}

		public void setSortMethod(String sortMethod) {
			this.sortMethod = sortMethod;
		}
	}
	
	public List<String[]> checkInventory( Long obdId ){
		List<String[]> result = new ArrayList<String[]>();
		
		// 出库单按SKUID合计数量
		StringBuilder sql = new StringBuilder();
		sql.append(" select ");
		sql.append(" obdDetail.SKU_ID,");
		sql.append(" sum( obdDetail.PLAN_QTY - obdDetail.ALLOCATE_QTY ) as qty");
		sql.append(" ,obdDetail.PROPERTY1");
		sql.append(" from");
		sql.append(" WMS_OUTBOUND_DELIVERY_DETAIL obdDetail");
		sql.append(" left join");
		sql.append(" WMS_OUTBOUND_DELIVERY obd");
		sql.append(" on");
		sql.append(" obdDetail.OBD_ID = obd.ID");
		sql.append(" where");
		sql.append(" obd.ID = ?");
		sql.append(" and");
		sql.append(" obdDetail.PLAN_QTY > obdDetail.ALLOCATE_QTY");
		sql.append(" group by");
		sql.append(" obdDetail.SKU_ID");
		sql.append(" ,obdDetail.PROPERTY1");
		
		List<Object> skuQtys = commonDao.findBySqlQuery(sql.toString(), new Object[]{obdId});
		OutboundDelivery obd = commonDao.load(OutboundDelivery.class, obdId);
		// 
		if( skuQtys != null && skuQtys.size() > 0 ){
			for( Object skuQty : skuQtys ){
				Object[] row = (Object[])skuQty;
				Long skuId = ((BigDecimal)row[0]).longValue();
				Double qty = ((BigDecimal)row[1]).doubleValue();
				String lotInfo = (String)row[2];
				
				// 拣货区，存货区，不良品区
				String[] stRoles = new String[]{ EnuStoreRole.R3000, EnuStoreRole.R4040 };
				double invQty = getInventoryQty(obd, skuId, stRoles, lotInfo);
				
				if( qty != null && qty > invQty ){
					Sku sku = commonDao.load(Sku.class, skuId);
					result.add(new String[]{ obd.getRelatedBill1(), sku.getCode(),
							sku.getName(), obd.getOwner().getName(),
							obd.getOrderType().getCode().equals("OB_UN") ? "不良品" : "良品",
							String.valueOf(qty.longValue()), String.valueOf(new Double(invQty).longValue())});
				}
			}
		}
		return result;
	}
	
	private List<Object[]> getSkuCaseQtyInfos( Long obdId ){
		String hql = "select obdDetail.allocateQty from OutboundDeliveryDetail obdDetail where obdDetail.obd.id = :obdId";
		List<Double> allocateQty = commonDao.findByQuery(hql, "obdId", obdId);
		
		StringBuilder sql = new StringBuilder("select");
		sql.append(" t.SKU_ID");
		sql.append(" ,sum(t.QTY)");
		sql.append(" ,wm_concat(OBD_DETAIL_IDS)");
		sql.append(" ,t.LOT_INFO");
		sql.append(" from(select");
		sql.append(" obdDetail.SKU_ID,");
		sql.append(" obdDetail.RELATED_BILL1,");
		sql.append(" case when p2000.COEFFICIENT is null or p2000.COEFFICIENT <= 0 then 0 ");
		sql.append(" else floor(sum( obdDetail.PLAN_QTY - obdDetail.ALLOCATE_QTY)/p2000.COEFFICIENT) * p2000.COEFFICIENT end as QTY,");
		sql.append(" wm_concat(obdDetail.ID) AS OBD_DETAIL_IDS,");
		sql.append(" obdDetail.PROPERTY1 AS LOT_INFO");
		sql.append(" from WMS_OUTBOUND_DELIVERY_DETAIL obdDetail");
		sql.append(" left join WMS_OUTBOUND_DELIVERY obd on obdDetail.OBD_ID = obd.ID");
		sql.append(" left join WMS_SKU sku on obdDetail.SKU_ID = sku.ID");
		sql.append(" left join WMS_PACKAGE_INFO packInfo on sku.PACK_INFO_ID = packInfo.ID");
		sql.append(" left join WMS_PACKAGE_DETAIL p2000 on packInfo.ID = p2000.PACKAGE_ID and p2000.PACKAGE_LEVEL = 'PK2000'");
		sql.append(" where obd.id = ?");
		sql.append(" and obdDetail.PLAN_QTY > obdDetail.ALLOCATE_QTY");
		sql.append(" group by obdDetail.SKU_ID,p2000.COEFFICIENT,obdDetail.RELATED_BILL1,obdDetail.PROPERTY1)t");
		sql.append(" where t.QTY > 0 group by t.SKU_ID,t.LOT_INFO");
		
		List<Object> result = commonDao.findBySqlQuery(sql.toString(), new Object[]{obdId});
		if( result != null && result.size() > 0 ){
			List<Object[]> mapResult = new ArrayList<Object[]>(result.size());
			for(int i = 0; i < result.size() ; i++ ){
				Object[] row = (Object[])result.get(i);
				Long skuId = ((BigDecimal)row[0]).longValue();
				Double totalQty = ((BigDecimal)row[1]).doubleValue();
				String[] obdDetailIds = ((String)row[2]).split(",");
				String lotInfo = (String)row[3];
				
				mapResult.add(new Object[]{skuId,totalQty,obdDetailIds,lotInfo});
			}
			return mapResult;
		}
		return null;
	}
	
	private List<Object[]> getSkuSoQtyInfos( Long obdId ){
		StringBuilder sql = new StringBuilder("select");
		sql.append(" obdDetail.SKU_ID,");
		sql.append(" obdDetail.RELATED_BILL1,");
		sql.append(" p2000.COEFFICIENT,");
		sql.append(" sum( obdDetail.PLAN_QTY - obdDetail.ALLOCATE_QTY) AS TOTOAL_QTY,");
		sql.append(" case when p2000.COEFFICIENT is null or p2000.COEFFICIENT <= 0 then 0 ");
		sql.append(" else floor(sum( obdDetail.PLAN_QTY - obdDetail.ALLOCATE_QTY)/p2000.COEFFICIENT) end as CASE_QTY,");
		sql.append(" case when p2000.COEFFICIENT is null or p2000.COEFFICIENT <= 0 then sum( obdDetail.PLAN_QTY - obdDetail.ALLOCATE_QTY)");
		sql.append(" else sum( obdDetail.PLAN_QTY - obdDetail.ALLOCATE_QTY) - p2000.COEFFICIENT * (floor(sum( obdDetail.PLAN_QTY - obdDetail.ALLOCATE_QTY)/p2000.COEFFICIENT)) end as PS_QTY,");
		sql.append(" wm_concat(obdDetail.ID) AS OBD_DETAIL_IDS,");
		sql.append(" obdDetail.PROPERTY1 AS LOT_INFO");
		sql.append(" from WMS_OUTBOUND_DELIVERY_DETAIL obdDetail");
		sql.append(" left join WMS_OUTBOUND_DELIVERY obd on obdDetail.OBD_ID = obd.ID");
		sql.append(" left join WMS_SKU sku on obdDetail.SKU_ID = sku.ID");
		sql.append(" left join WMS_PACKAGE_INFO packInfo on sku.PACK_INFO_ID = packInfo.ID");
		sql.append(" left join WMS_PACKAGE_DETAIL p2000 on packInfo.ID = p2000.PACKAGE_ID and p2000.PACKAGE_LEVEL = 'PK2000'");
		sql.append(" where obd.id = ?");
		sql.append(" and obdDetail.PLAN_QTY > obdDetail.ALLOCATE_QTY");
		sql.append(" group by");
		sql.append(" obdDetail.SKU_ID,");
		sql.append(" p2000.COEFFICIENT,");
		sql.append(" obdDetail.PROPERTY1,");
		sql.append(" obdDetail.RELATED_BILL1");
		sql.append(" order by SKU_ID, sum( obdDetail.PLAN_QTY - obdDetail.ALLOCATE_QTY)");
		System.out.println(sql.toString());
		List<Object> result = commonDao.findBySqlQuery(sql.toString(), new Object[]{obdId});
		if( result != null && result.size() > 0 ){
			List<Object[]> mapResult = new ArrayList<Object[]>(result.size());
			for(int i = 0; i < result.size() ; i++ ){
				Object[] row = (Object[])result.get(i);
				Long skuId = ((BigDecimal)row[0]).longValue();
				String soNumber = row[1] == null ? "" : (String)row[1];
				Double totalQty = ((BigDecimal)row[3]).doubleValue();
				Double caseQty = ((BigDecimal)row[4]).doubleValue();
				Double psQty = ((BigDecimal)row[5]).doubleValue();
				String[] obdDetailIds = ((String)row[6]).split(",");
				String lotInfo = (String)row[7];
				
				mapResult.add(new Object[]{skuId,soNumber,totalQty,caseQty,psQty,obdDetailIds,lotInfo});
			}
			return mapResult;
		}
		return null;
	}
	
	
	private List<String[]> inventoryCheck(Long waveDocId){
		
		return null;
	}
	
	

	@Override
	public List<String[]> allocateWaveDoc(List<Long> ids) {
		List<String[]> result = new ArrayList<String[]>();
		whId = WarehouseHolder.getWarehouse().getId();

		// 仓库ID 、多仓库的分配可以并行进行
		synchronized (whId) {
			// 单条操作，便于消息提示
			Long id = ids.get(0);

			// 分配单据获得
			WaveDoc doc = this.commonDao.load(WaveDoc.class, id);
			
			if( !EnuWaveStatus.STATUS200.equals(doc.getStatus()) && !EnuWaveStatus.STATUS210.equals(doc.getStatus())){
				throw new BusinessException(ExceptionConstant.ERROR_STATUS);
			}

			if (doc.getUnAllocateQty() > 0) {
				//非库存型
				String hql = " select detail.obdDetail.id "
						+ " from WaveDocDetail detail "
						+ " where detail.doc.id = :waveDocId "
						+ " and coalesce(detail.obdDetail.sku.stockDiv,1) in (2,3) " // 非库存型
						+ " and detail.obdDetail.status < 400 "
						+ " order by detail.obdDetail.obd.id, "
						+ " detail.obdDetail.sku.id,"
						+ " detail.obdDetail.lineNo, "
						+ " detail.obdDetail.subLineNo ";
				List<Long> throughDetailIds = commonDao.findByQuery(hql, "waveDocId", id);
				
				if( throughDetailIds != null && throughDetailIds.size() > 0 ){
					allocate4Through(throughDetailIds);
				}
				
				// 库存型
				String hql2 = " select detail.obdDetail.id "
						+ " from WaveDocDetail detail "
						+ " where detail.doc.id = :waveDocId "
						+ " and coalesce(detail.obdDetail.sku.stockDiv,1) not in (2,3) " // 在庫タイプ
						+ " and detail.obdDetail.status < 400 "
						+ " order by detail.obdDetail.obd.id, "
						+ " detail.obdDetail.extString2,"
						+ " detail.obdDetail.lineNo, "
						+ " detail.obdDetail.subLineNo, "
						+ " detail.obdDetail.sku.id";
				List<Long> obdDetailIds = commonDao.findByQuery(hql2, "waveDocId", id);
				
				// 日本食研
				if( WmsConstant4Ktw.NS_OWNER_CD.equals(doc.getOwner().getCode()) ){
					allocateObdDetails4Ns(obdDetailIds, result);
				}
				// 其他
				else{
					for (Long obdDetailId : obdDetailIds) {
						allocateDetail(commonDao.load(OutboundDeliveryDetail.class, obdDetailId), result);
					}
				}

			}
			
			// WaveDoc状态调整
			if( EnuWaveStatus.STATUS200.equals(doc.getStatus()) && doc.getAllocatedQty() > 0 ){
				orderStatusHelper.changeStatus(doc, EnuWaveStatus.STATUS210);
			}
			
			if( EnuWaveStatus.STATUS210.equals(doc.getStatus()) && doc.getUnAllocateQty() <= 0 ){
				orderStatusHelper.changeStatus(doc, EnuWaveStatus.STATUS220);
			}
		}

		if (result.size() > 0) {
			updateStatusBar( ExceptionConstant.HAS_DETAIL_NOT_ALL_ALLOCATE_SEE_DETAIL, MessageUtil.formatDetailMes(result), Boolean.TRUE);
		}

		return result;
	}
	
	private void allocate4Through( List<Long> obdDetailIds ){
		for( Long obdDetailId : obdDetailIds ){
			OutboundDeliveryDetail obdDetail = commonDao.load(OutboundDeliveryDetail.class, obdDetailId);
			allocateDetail(obdDetail, obdDetail.getUnAllocateQty());
		}
	}
	
	// 食研的库存分配
	private void allocateObdDetails4Ns( List<Long> obdDetailIds, List<String[]> result ){
		// 按Line合计出库数，如果正好是1Case或者1BL，库存分配时要保证分配到一个保质期
		List<OutboundDeliveryDetail> thisObdDetails = new ArrayList<OutboundDeliveryDetail>();
		Long obdId = 0L;
		Long skuId = 0L;
		Double lineNo = 0D;
		String orderNo = "";
		
		// 第一轮分配，分配整箱和单行
		if( obdDetailIds != null && obdDetailIds.size() > 0 ){
			for (Long obdDetailId : obdDetailIds) {
				OutboundDeliveryDetail obdDetail = commonDao.load(OutboundDeliveryDetail.class, obdDetailId);
				
				if( !obdId.equals(obdDetail.getObd().getId()) 
						|| lineNo.doubleValue() != obdDetail.getLineNo().doubleValue()  
						|| !skuId.equals(obdDetail.getSku().getId())
						|| !orderNo.equals(obdDetail.getExtString2() == null ? "" : obdDetail.getExtString2())){
					if( thisObdDetails != null && thisObdDetails.size() > 0 ){
						allocate4NS(thisObdDetails, result);
					}
					
					obdId = obdDetail.getObd().getId();
					skuId = obdDetail.getSku().getId();
					lineNo = obdDetail.getLineNo();
					orderNo = obdDetail.getExtString2() == null ? "" : obdDetail.getExtString2();
					
					thisObdDetails = new ArrayList<OutboundDeliveryDetail>();
				}
				
				thisObdDetails.add(obdDetail);
			}
		}
		
		if( thisObdDetails != null && thisObdDetails.size() > 0 ){
			allocate4NS(thisObdDetails, result);
		}
		
	}
	
	private void allocate4NS( List<OutboundDeliveryDetail> thisObdDetails, List<String[]> result ){
		// 如果是要求唯一保质期，这里先找一个能满足条件的批次，设置到expDateUni
		if( thisObdDetails.get(0).getCanMixExp() != null && !thisObdDetails.get(0).getCanMixExp().booleanValue() ){
			Date expDateUni = getExpDate4CannotMix(thisObdDetails.get(0), 
					thisObdDetails.get(0).getExtDouble6() == null ? thisObdDetails.get(0).getUnAllocateQty() : thisObdDetails.get(0).getExtDouble6(), 
					thisObdDetails.get(0).getExpDateMin(), thisObdDetails.get(0).getExpDateMax(), WmsConstant4Ktw.ST_ROLES);
			
			// 找不到这样的单一库存，直接报错
			if( expDateUni == null ){
				for( OutboundDeliveryDetail detail : thisObdDetails ){
					if (detail.getUnAllocateQty() > 0) {
						result.add(new String[] {
								ExceptionConstant.HAS_DETAIL_NOT_ALL_ALLOCATE,
								"(" + detail.getSku().getCode() + ")" + detail.getSku().getName(),
								detail.getLotData() == null ? "" : detail.getLotData().toString(), 
								"在庫不足数：" + String.valueOf(detail.getUnAllocateQty()) });
					}
				}
				return;
			}
			else{
				for( OutboundDeliveryDetail detail : thisObdDetails ){
					detail.setExpDateUni(expDateUni);
				}
			}
		}
		
		// 合计出库数，如果正好是一个CS，或者一个BL，要保证分配到一个LOT
		Sku sku = thisObdDetails.get(0).getSku();
		Double csIn = sku.getProperties().getPackageInfo().getP2000().getCoefficient();
		if( csIn == null )
			csIn = 0D;
		Double blIn = sku.getProperties().getPackageInfo().getP2100().getCoefficient();
		if( blIn == null )
			blIn = 0D;
		
		// 如果单行的数量超过1CS，直接分配掉
		for( OutboundDeliveryDetail obdDetail : thisObdDetails ){
			Double unAllocateQty = obdDetail.getUnAllocateQty();
			int caseQty = csIn > 0D ? unAllocateQty.intValue()/csIn.intValue() : 0;
			if( caseQty > 0 ){
				List<OutboundDeliveryDetail> oneObdDetails = new ArrayList<OutboundDeliveryDetail>(1);
				oneObdDetails.add(obdDetail);
				// 按CASE单位分配
				allocateDetail4Ns(oneObdDetails, new Double( csIn * caseQty ), new Double( csIn ), obdDetail.getExpDateMin(), obdDetail.getExpDateMax(), obdDetail.getExpDateUni(), result);
			}
		}
		
		// 单行如果是批次指定的，按指定的批次分配，不做CASE合并
		for( OutboundDeliveryDetail obdDetail : thisObdDetails ){
			if( obdDetail.getExpDateUni() != null && obdDetail.getUnAllocateQty() > 0D ){
				List<OutboundDeliveryDetail> oneObdDetails = new ArrayList<OutboundDeliveryDetail>(1);
				oneObdDetails.add(obdDetail);
				allocateDetail4Ns(oneObdDetails, obdDetail.getUnAllocateQty(), 1D, obdDetail.getExpDateMin(), obdDetail.getExpDateMax(), obdDetail.getExpDateUni(), result);
			}
		}
			
		// 剩余部分
		String hql2 = " select sum(obdDetail.planQty - obdDetail.allocateQty), max(obdDetail.expDateMin) "
				    + " from OutboundDeliveryDetail obdDetail where obdDetail in (:obdDetailIds) "
				    + " and (obdDetail.planQty - obdDetail.allocateQty) > 0 "
				    + " and obdDetail.expDateUni is null ";
		Object[] planInfos = (Object[])commonDao.findByQueryUniqueResult(hql2, new String[]{"obdDetailIds"}, new Object[]{thisObdDetails});
		// 总数
		Double planQty = 0D;
		Date expDateMin = null;
		
		if( planInfos != null && planInfos[0] != null ){
			planQty = (Double)planInfos[0];
			expDateMin = (Date)planInfos[1];
		}
		
		int caseQty = csIn > 0D ? planQty.intValue()/csIn.intValue() : 0;
		int blQty = blIn > 0D ? (planQty.intValue() - caseQty * csIn.intValue())/blIn.intValue() : 0;
		int psQty = planQty.intValue() - caseQty * csIn.intValue() - blQty * blIn.intValue();
		
		// 整CS
		if( caseQty > 0 ){
			for( int i = 0 ; i < caseQty ; i++){
				allocateDetail4Ns(thisObdDetails, new Double( csIn ), new Double( csIn ), expDateMin, null, null, result);
			}
		}
		
		// 整BL
		if( blQty > 0 ){
			for( int i = 0 ; i < blQty ; i++){
				allocateDetail4Ns(thisObdDetails, new Double( blIn ), new Double( blIn ), expDateMin, null, null, result);
			}
		}
		
		// 如果还有没有分配完的，这里再分配一次
		for( OutboundDeliveryDetail obdDetail : thisObdDetails ){
			if( obdDetail.getUnAllocateQty() > 0D ){
				List<OutboundDeliveryDetail> oneObdDetails = new ArrayList<OutboundDeliveryDetail>(1);
				oneObdDetails.add(obdDetail);
				allocateDetail4Ns(oneObdDetails, obdDetail.getUnAllocateQty(), 1D, obdDetail.getExpDateMin(), obdDetail.getExpDateMax(), obdDetail.getExpDateUni(), result);
			}
		}						
		
		for( OutboundDeliveryDetail detail : thisObdDetails ){
			if (detail.getUnAllocateQty() > 0) {
				result.add(new String[] {
						ExceptionConstant.HAS_DETAIL_NOT_ALL_ALLOCATE,
						"(" + detail.getSku().getCode() + ")" + detail.getSku().getName(),
						detail.getLotData() == null ? "" : detail.getLotData().toString(), 
						"在庫不足数：" + String.valueOf(detail.getUnAllocateQty()) });
			}
		}
	}

	@Override
	public void unAllocateWaveDoc(List<Long> ids) {
		// 非库存的取消分配
		String hql = " select obdDetail.id from OutboundDeliveryDetail obdDetail "
				+ " where obdDetail.obd.waveDoc.id in (:waveDocIds) "
				+ " and obdDetail.sku.stockDiv in (2,3) and (obdDetail.allocateQty - obdDetail.pickUpQty > 0 )";
		
		List<Long> throughDetailIds = commonDao.findByQuery(hql, "waveDocIds", ids);
		if( throughDetailIds != null && throughDetailIds.size() > 0 ){
			for( Long obdDetailId : throughDetailIds ){
				OutboundDeliveryDetail obdDetail = commonDao.load(OutboundDeliveryDetail.class, obdDetailId);
				unallocateDetail(obdDetail, obdDetail.getUnPickUpQty());
			}
		}
		
		// 未完成任务查询
		StringBuffer sb = new StringBuffer();
		sb.append("select wt.id From WarehouseTask wt where wt.obdDetail.obd.waveDoc.id in (:waveIds) "
				+ "and (wt.planQty - coalesce(wt.executeQty,0)) > 0 and wt.status <700 ");

		List<Long> wtIds = this.commonDao.findByQuery(sb.toString(), new String[] { "waveIds" }, new Object[] { ids });

		if (wtIds != null && wtIds.size() > 0) {
			for (Long taskId : wtIds) {
				cancelPickTask(taskId);
			}
		}
		
		// 调整WaveDoc的状态
		for( Long wvId : ids ){
			WaveDoc waveDoc = commonDao.load(WaveDoc.class, wvId);
			if( EnuWaveStatus.STATUS220.equals(waveDoc.getStatus()) && waveDoc.getUnAllocateQty() > 0 ){
				orderStatusHelper.changeStatus(waveDoc, EnuWaveStatus.STATUS210);
			}
			
			if( EnuWaveStatus.STATUS210.equals(waveDoc.getStatus()) && waveDoc.getAllocatedQty() <= 0 ){
				orderStatusHelper.changeStatus(waveDoc, EnuWaveStatus.STATUS200);
			}
		}
	}

	@Override
	public void cancelPickTask(Long taskId) {
		WarehouseTask task = commonDao.load(WarehouseTask.class, taskId);

		// 未关闭的作业任务都可以取消或者关闭
		if (EnuWarehouseOrderStatus.CLOSE.equals(task.getStatus())) {
			throw new BusinessException(ExceptionConstant.ERROR_STATUS);
		}

		// Task中还未处理的数量
		double unExecuteQty = task.getUnExecuteQty();

		if (unExecuteQty > 0) {
			Inventory inv = invHelper.getInv(task.getInvInfo());
			if (inv == null) {
				throw new BusinessException(
						ExceptionConstant.INVENTORY_NOT_FOUND);
			}
			// 库存取消分配
			invHelper.unAllocateInv(inv, unExecuteQty);

			// 修改Task的计划数量
			task.removePlanQty(unExecuteQty);

			// 如果已经加入了WO，修改WO的计划数量，修改WO的状态
			if (task.getWo() != null) {
				WarehouseOrder wo = task.getWo();
				wo.removePlanQty(unExecuteQty);

				// 草案
				if (EnuWarehouseOrderStatus.DRAFT.equals(wo.getStatus())) {
					// NOTHING
				}
				// 发行
				else if (EnuWarehouseOrderStatus.PUBLISH.equals(wo.getStatus())) {
					// NOTHING
				}
				// 分配
				else if (EnuWarehouseOrderStatus.PLANED.equals(wo.getStatus())) {
					// NOTHING
				}
				// 执行中
				else if (EnuWarehouseOrderStatus.EXECUTE.equals(wo.getStatus())) {
					// 待执行数为0时，该WO状态改为执行完成
					if (wo.getUnExecuteQty() <= 0) {
						orderStatusHelper.changeStatus(wo, EnuWarehouseOrderStatus.CLOSE);
					}
				}
			}

			// 修改加工明细和单头的分配数
			if (task.getObdDetail() != null) {
				task.getObdDetail().removeAllocateQty(unExecuteQty);

				// -----修改Obd明细状态
				// 原来是分配完成状态，改分配中
				if (EnuOutboundDeliveryDetailStatus.STATUS400.equals(task.getObdDetail().getStatus()) && task.getObdDetail().getUnAllocateQty() > 0) {
					task.getObdDetail().setStatus(EnuOutboundDeliveryDetailStatus.STATUS310);
				}

				// 原来是分配中，改发行或加入波次（根据WaveDoc）
				if (EnuOutboundDeliveryDetailStatus.STATUS310.equals(task.getObdDetail().getStatus()) && task.getObdDetail().getAllocateQty() <= 0) {
					// 无波次的回退到发行状态
					if (task.getObdDetail().getObd().getWaveDoc() == null) {
						task.getObdDetail().setStatus(EnuOutboundDeliveryDetailStatus.STATUS200);
					}
					// 有波次的回退加入波次状态
					else {
						task.getObdDetail().setStatus( EnuOutboundDeliveryDetailStatus.STATUS210);
					}
				}

				// -----修改Obd状态
				// 分配完成 -> 分配中
				if (EnuOutboundDeliveryStatus.STATUS400.equals(task.getObdDetail().getObd().getStatus()) && task.getObdDetail().getObd().getUnAllocateQty() > 0) {
					orderStatusHelper.changeStatus( task.getObdDetail().getObd(), EnuOutboundDeliveryStatus.STATUS310);
				}

				// 分配中&分配数=0 -> 加入波次/发行
				if (EnuOutboundDeliveryStatus.STATUS310.equals(task.getObdDetail().getObd().getStatus()) && task.getObdDetail().getObd().getAllocateQty() <= 0) {
					// 无波次的回退到发行状态
					if (task.getObdDetail().getObd().getWaveDoc() == null) {
						orderStatusHelper.changeStatus(task.getObdDetail().getObd(), EnuOutboundDeliveryStatus.STATUS200);
					}
					// 有波次的回退加入波次状态
					else {
						orderStatusHelper.changeStatus(task.getObdDetail().getObd(), EnuOutboundDeliveryStatus.STATUS210);
					}
				}
			} else if (task.getOwnerChangeDocDetail() != null) {
				// TODO
			}

			// 计划数为0的Task删除
			if (task.getPlanQty() <= 0) {
				// 如果已经加入了WO，要从WO中删除
				if (task.getWo() != null) {
					task.getWo().getTasks().remove(task);
				}

				if (task.getObdDetail() != null) {
					task.getObdDetail().getTasks().remove(task);
				} else if (task.getOwnerChangeDocDetail() != null) {
					// TODO
				} 

				commonDao.delete(task);
			}
			// 执行中状态的Task，因为剩余的未拣选的数量被取消了，状态改为执行完成
			else if (EnuWarehouseOrderStatus.EXECUTE.equals(task.getStatus())) {
				orderStatusHelper.changeStatus(task,
						EnuWarehouseOrderStatus.CLOSE);
			}
		}
	}

	@Override
	public void closePickTask(Long taskId) {
		WarehouseTask task = commonDao.load(WarehouseTask.class, taskId);

		// 已加入WO且尚未执行完成的WT可以强制关闭
		if (!EnuWarehouseOrderStatus.PLANED.equals(task.getStatus())
				&& !EnuWarehouseOrderStatus.EXECUTE.equals(task.getStatus())) {
			throw new BusinessException(ExceptionConstant.ERROR_STATUS);
		}

		// Task中还未处理的数量
		double unExecuteQty = task.getUnExecuteQty();

		if (unExecuteQty > 0) {
			Inventory inv = invHelper.getInv(task.getInvInfo());
			if (inv == null) {
				throw new BusinessException(
						ExceptionConstant.INVENTORY_NOT_FOUND);
			}
			// 库存取消分配
			invHelper.unAllocateInv(inv, unExecuteQty);

			// 修改Task的计划数量
			task.removePlanQty(unExecuteQty);

			// 修改加工明细和单头的分配数
			if (task.getObdDetail() != null) {
				task.getObdDetail().removeAllocateQty(unExecuteQty);
			} else if (task.getOwnerChangeDocDetail() != null) {
				// TODO
			} 

			// Task的状态改为关闭
			orderStatusHelper.changeStatus(task, EnuWarehouseOrderStatus.CLOSE);
		}
	}


}
