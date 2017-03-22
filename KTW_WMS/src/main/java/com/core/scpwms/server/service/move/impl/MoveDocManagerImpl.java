package com.core.scpwms.server.service.move.impl;

import java.util.ArrayList;
import java.util.List;

import com.core.business.exception.BusinessException;
import com.core.business.model.domain.CreateInfo;
import com.core.business.model.domain.UpdateInfo;
import com.core.business.utils.DoubleUtil;
import com.core.business.web.security.UserHolder;
import com.core.db.server.service.pojo.DefaultBaseManager;
import com.core.scpwms.server.constant.ExceptionConstant;
import com.core.scpwms.server.constant.WmsConstant;
import com.core.scpwms.server.enumerate.EnuInvStatus;
import com.core.scpwms.server.enumerate.EnuMoveDocStatus;
import com.core.scpwms.server.enumerate.EnuMoveDocType;
import com.core.scpwms.server.filter.WarehouseHolder;
import com.core.scpwms.server.model.common.OrderType;
import com.core.scpwms.server.model.inventory.Inventory;
import com.core.scpwms.server.model.inventory.QuantInventory;
import com.core.scpwms.server.model.move.MoveDoc;
import com.core.scpwms.server.model.move.MoveDocDetail;
import com.core.scpwms.server.model.warehouse.Bin;
import com.core.scpwms.server.service.common.AllocateHelper;
import com.core.scpwms.server.service.common.BCManager;
import com.core.scpwms.server.service.common.BinHelper;
import com.core.scpwms.server.service.common.CommonHelper;
import com.core.scpwms.server.service.common.InventoryHelper;
import com.core.scpwms.server.service.common.OrderStatusHelper;
import com.core.scpwms.server.service.move.MoveDocManager;
import com.core.scpwms.server.util.LocaleUtil;
import com.core.scpwms.server.util.PrecisionUtils;

@SuppressWarnings("all")
public class MoveDocManagerImpl extends DefaultBaseManager implements
		MoveDocManager {

	private BCManager bcManager;
	private OrderStatusHelper orderStatusHelper;
	private InventoryHelper invHelper;
	private CommonHelper commonHelper;
	private AllocateHelper allocateHelper;
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

	public InventoryHelper getInvHelper() {
		return this.invHelper;
	}

	public void setInvHelper(InventoryHelper invHelper) {
		this.invHelper = invHelper;
	}

	public CommonHelper getCommonHelper() {
		return this.commonHelper;
	}

	public void setCommonHelper(CommonHelper commonHelper) {
		this.commonHelper = commonHelper;
	}

	public AllocateHelper getAllocateHelper() {
		return this.allocateHelper;
	}

	public void setAllocateHelper(AllocateHelper allocateHelper) {
		this.allocateHelper = allocateHelper;
	}

	public BinHelper getBinHelper() {
		return this.binHelper;
	}

	public void setBinHelper(BinHelper binHelper) {
		this.binHelper = binHelper;
	}

	@Override
	public void addDetail(Long moveDocId, List<Long> invIds) {
		MoveDoc moveDoc = commonDao.load(MoveDoc.class, moveDocId);

		if (!EnuMoveDocStatus.DRAFT.equals(moveDoc.getStatus())) {
			throw new BusinessException(ExceptionConstant.ERROR_STATUS);
		}

		for (Long invId : invIds) {
			Inventory inv = commonDao.load(Inventory.class, invId);

			// 冻结，直接跳过
			if (EnuInvStatus.FREEZE.equals(inv.getStatus()))
				continue;

			// 有出锁
			if (inv.getBin().isOutLock()) {
				continue;
			}

			// 只取可用量
			if (inv.getAvaliableQuantity() > 0) {
				// 取可用数
				double qcQty = inv.getAvaliableQuantity();

				MoveDocDetail detail = new MoveDocDetail();
				detail.setDoc(moveDoc);
				detail.setInvInfo(inv.getInventoryInfo());
				detail.addPlanQty(qcQty);
				detail.setInventoryId(inv.getId());
				commonDao.store(detail);
			}
		}

		commonDao.store(moveDoc);

	}

	@Override
	public void cancel(List<Long> moveDocIds) {
		for (Long id : moveDocIds) {
			MoveDoc moveDoc = commonDao.load(MoveDoc.class, id);

			// 只有草案状态可以取消
			if (EnuMoveDocStatus.DRAFT.equals(moveDoc.getStatus())) {
				orderStatusHelper
						.changeStatus(moveDoc, EnuMoveDocStatus.CANCEL);
				moveDoc.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
				commonDao.store(moveDoc);
			} else {
				throw new BusinessException(ExceptionConstant.ERROR_STATUS);
			}
		}
	}

	@Override
	public void close(List<Long> moveDocIds) {
		// 不需要
		// 关联的上架单执行完成，移位单自然关闭
		// 关联的上架单强制关闭，移位单也强制关闭
	}

	@Override
	public void deleteDetail(List<Long> moveDocDetailIds) {
		for (Long id : moveDocDetailIds) {
			MoveDocDetail detail = commonDao.load(MoveDocDetail.class, id);
			MoveDoc doc = detail.getDoc();

			// 只有草案状态可以删除
			if (!EnuMoveDocStatus.DRAFT.equals(doc.getStatus())) {
				throw new BusinessException(ExceptionConstant.ERROR_STATUS);
			}

			detail.getDoc().getDetails().remove(detail);
			detail.removePlanQty(detail.getPlanQty());
			commonDao.delete(detail);
		}
	}

	@Override
	public void editDetail(MoveDocDetail moveDocDetail, double movePackQty) {

		// 如果更新数为0，直接删除该明细
		if (movePackQty <= 0) {
			List<Long> moveDocDetailIds = new ArrayList<Long>(1);
			moveDocDetailIds.add(moveDocDetail.getId());
			deleteDetail(moveDocDetailIds);
			return;
		}

		MoveDocDetail oldDetail = commonDao.load(MoveDocDetail.class,
				moveDocDetail.getId());
		MoveDoc doc = oldDetail.getDoc();

		if (!EnuMoveDocStatus.DRAFT.equals(doc.getStatus())) {
			throw new BusinessException(ExceptionConstant.ERROR_STATUS);
		}

		Inventory inv = invHelper.getInv(oldDetail.getInvInfo());

		// 找不到库存
		if (inv == null) {
			throw new BusinessException(ExceptionConstant.INVENTORY_NOT_FOUND);
		}

		double moveQty = PrecisionUtils.getBaseQty(movePackQty, oldDetail
				.getInvInfo().getPackageDetail());

		// 超过库存可用量
		if (DoubleUtil.compareByPrecision(inv.getAvaliableQuantity(), moveQty,
				oldDetail.getInvInfo().getQuant().getSku().getProperties()
						.getPackageInfo().getP1000().getPrecision().intValue()) < 0) {
			throw new BusinessException(
					ExceptionConstant.CANNOT_ADJUST_AS_MORE_THAN_AVAILABLE_QTY);
		}

		oldDetail.removePlanQty(oldDetail.getPlanQty());
		oldDetail.addPlanQty(moveQty);
		commonDao.store(oldDetail);

	}

	@Override
	public void save(MoveDoc moveDoc) {
		if (moveDoc.getSt() != null && moveDoc.getSt().getId() == null) {
			moveDoc.setSt(null);
		}

		if (moveDoc.getBg() != null && moveDoc.getBg().getId() == null) {
			moveDoc.setBg(null);
		}

		if (moveDoc.isNew()) {
			moveDoc.setWh(WarehouseHolder.getWarehouse());

			OrderType ot = commonHelper.getOrderType(WmsConstant.INV_MOV);
			if (ot == null) {
				throw new BusinessException(
						ExceptionConstant.CANNOT_FIND_ORDER_TYPE,
						new String[] { LocaleUtil.getText("move.doc") });
			}

			moveDoc.setOrderType(ot);
			moveDoc.setDocSequence(bcManager.getOrderSequence(
					moveDoc.getOrderType(), moveDoc.getWh().getId()));
			moveDoc.setCreateInfo(new CreateInfo(UserHolder.getUser()));
			commonDao.store(moveDoc);

			orderStatusHelper.changeStatus(moveDoc, EnuMoveDocStatus.DRAFT);

		} else {
			// 只有草案可以修改
			if (!EnuMoveDocStatus.DRAFT.equals(moveDoc.getStatus())) {
				throw new BusinessException(ExceptionConstant.ERROR_STATUS);
			}

			moveDoc.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
			commonDao.store(moveDoc);
		}

	}

	@Override
	public void newAuto(MoveDoc moveDoc) {
		save(moveDoc);

		List<Long> invList = null;
		// 库位错误存放
		if (EnuMoveDocType.ERROR_BIN.equals(moveDoc.getMoveType())) {
			invList = getErrorBin(moveDoc);
		}
		// 相同批次合并
		else if (EnuMoveDocType.SAME_LOT.equals(moveDoc.getMoveType())) {
			invList = getSameLot(moveDoc);
		}
		// 库位优化
		else if (EnuMoveDocType.SLOT_OPT.equals(moveDoc.getMoveType())) {
			// 如果没有指定库容下限，报错。
			if (moveDoc.getMinBinInv() == null || moveDoc.getMinBinInv() <= 0D) {
				throw new BusinessException(
						ExceptionConstant.MINBININV_REQUIRED);
			}

			// 没有指定库位组，报错。
			if (moveDoc.getBg() == null) {
				throw new BusinessException(ExceptionConstant.BG_REQUIRED);
			}

			invList = getSlopOpt(moveDoc);
		}

		if (invList != null && invList.size() > 0) {
			addDetail(moveDoc.getId(), invList);
		} else {
			throw new BusinessException(
					ExceptionConstant.CANNOT_FIND_BIN_TO_OPT);
		}
	}

	private List<Long> getSlopOpt(MoveDoc moveDoc) {
		List<Long> result = new ArrayList<Long>();

		List<String> paramNames = new ArrayList<String>();
		List<Object> params = new ArrayList<Object>();

		// 同批次放置在不同的库位上的库存
		StringBuffer sb = new StringBuffer(
				" select inv.id from Inventory inv ");

		// 仓库
		sb.append(" where inv.wh.id = :whId ");
		paramNames.add("whId");
		params.add(moveDoc.getWh().getId());

		// 有锁的库位不参与优化
		sb.append(" and bin.lockStatus is null ");

		// 有分配数的库位不参与优化
		sb.append(" and bin.id not in ( select distinct inv2.bin.id from Inventory inv2 where inv2.wh.id =:whId and inv2.queuedQty > 0 ) ");

		// 功能区
		if (moveDoc.getSt() != null) {
			sb.append(" and bin.storageType.id = :stId ");
			paramNames.add("stId");
			params.add(moveDoc.getSt().getId());
		}
		// 库位组
		if (moveDoc.getBg() != null) {
			sb.append(" and inv.bin.id in ( select bbg.bin.id from BinBinGroup bbg where bbg.binGroup.id = :bgId ) ");
			paramNames.add("bgId");
			params.add(moveDoc.getBg().getId());
		}
		
		// 库容低于指定值
		if( moveDoc.getMinBinInv() != null ){
			sb.append(" and inv.bin.binInvInfo.binInfo.minFullRate < :minBinInv ");
			paramNames.add("minBinInv");
			params.add(moveDoc.getMinBinInv());
		}

		return commonDao.findByQuery(sb.toString(),
				paramNames.toArray(new String[paramNames.size()]),
				params.toArray(new Object[params.size()]));
	}

	private List<Long> getSameLot(MoveDoc moveDoc) {
		List<Long> result = new ArrayList<Long>();

		List<String> paramNames = new ArrayList<String>();
		List<Object> params = new ArrayList<Object>();

		// 同批次放置在不同的库位上的库存
		StringBuffer sb = new StringBuffer(
				" select inv.quantInv.id from Inventory inv ");

		// 仓库
		sb.append(" where inv.wh.id = :whId ");
		paramNames.add("whId");
		params.add(moveDoc.getWh().getId());

		// 有锁的库位不参与优化
		sb.append(" and bin.lockStatus is null ");

		// 有分配数的库位不参与优化
		sb.append(" and bin.id not in ( select distinct inv2.bin.id from Inventory inv2 where inv2.wh.id =:whId and inv2.queuedQty > 0 ) ");

		// 功能区
		if (moveDoc.getSt() != null) {
			sb.append(" and bin.storageType.id = :stId ");
			paramNames.add("stId");
			params.add(moveDoc.getSt().getId());
		}
		// 库位组
		if (moveDoc.getBg() != null) {
			sb.append(" and inv.bin.id in ( select bbg.bin.id from BinBinGroup bbg where bbg.binGroup.id = :bgId ) ");
			paramNames.add("bgId");
			params.add(moveDoc.getBg().getId());
		}

		sb.append(" group by inv.quantInv.id ");
		sb.append(" having count( distinct inv.bin.id ) > 1 ");

		List<Long> quantInvIds = commonDao.findByQuery(sb.toString(),
				paramNames.toArray(new String[paramNames.size()]),
				params.toArray(new Object[params.size()]));

		if (quantInvIds != null && quantInvIds.size() > 0) {
			for (Long quantInvId : quantInvIds) {
				QuantInventory quantInv = commonDao.load(QuantInventory.class,
						quantInvId);
				String hql = "select inv.bin.id, sum( inv.baseQty ), 0 from Inventory inv where inv.quantInv.id = :quantId group by inv.bin.id order by sum( inv.baseQty ) desc";
				List<Object[]> binInfos = commonDao.findByQuery(hql, "quantId",
						quantInvId);

				Double totalQty = 0D;
				int binCount = binInfos.size();// 分散的库位数
				// 库位ID，当前存放数量，剩余可容纳的数量
				for (Object[] binInfo : binInfos) {
					Long binId = (Long) binInfo[0];
					Bin bin = commonDao.load(Bin.class, binId);
					Double canPutawayQty = bin.getBinInvInfo().getCanPutAway(
							quantInv.getQuant().getSku(),
							quantInv.getQuant().getSku().getProperties()
									.getPackageInfo().getP1000(), Boolean.TRUE);
					binInfo[2] = canPutawayQty;
					totalQty = totalQty + (Double) binInfo[1];
				}

				// 1个库位能够容下
				Long descBinId = null;
				for (Object[] binInfo : binInfos) {
					if (DoubleUtil.compareByPrecision(totalQty
							- (Double) binInfo[1], (Double) binInfo[2], 2) <= 0) {
						descBinId = (Long) binInfo[0];
						break;
					}
				}

				if (descBinId != null) {
					String hql2 = "select inv.id from Inventory inv where inv.quantInv.id = :quantId and inv.bin.id != :descBinId";
					List<Long> invIds = commonDao.findByQuery(hql2,
							new String[] { "quantId", "descBinId" },
							new Object[] { quantInvId, descBinId });
					if (invIds != null && invIds.size() > 0) {
						result.addAll(invIds);
					}
				}
			}
		}

		return result;
	}

	private List<Long> getErrorBin(MoveDoc moveDoc) {
		List<Long> result = new ArrayList<Long>();

		List<String> paramNames = new ArrayList<String>();
		List<Object> params = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer(" select inv.id from Inventory inv ");

		// 仓库
		sb.append(" where inv.wh.id = :whId ");
		paramNames.add("whId");
		params.add(moveDoc.getWh().getId());

		// 有锁的库位不参与优化
		sb.append(" and bin.lockStatus is null ");

		// 有分配数的库位不参与优化
		sb.append(" and bin.id not in ( select distinct inv2.bin.id from Inventory inv2 where inv2.wh.id =:whId and inv2.queuedQty > 0 ) ");

		// 功能区
		if (moveDoc.getSt() != null) {
			sb.append(" and bin.storageType.id = :stId ");
			paramNames.add("stId");
			params.add(moveDoc.getSt().getId());
		}
		// 库位组
		if (moveDoc.getBg() != null) {
			sb.append(" and inv.bin.id in ( select bbg.bin.id from BinBinGroup bbg where bbg.binGroup.id = :bgId ) ");
			paramNames.add("bgId");
			params.add(moveDoc.getBg().getId());
		}

		List<Long> invList = commonDao.findByQuery(sb.toString(),
				paramNames.toArray(new String[paramNames.size()]),
				params.toArray(new Object[params.size()]));
		int i = 1;
		if (invList != null && invList.size() > 0) {
			for (Long id : invList) {
				if (!allocateHelper.checkBin4Inventory(id)) {
					result.add(id);
				}
			}
		}
		return result;
	}

}
