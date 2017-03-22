/**
 * (C)2014 MBP Corporation. All rights reserved.
 * 项目名称 : 华耐供应链
 * 子项目名称: 仓库系统
 * 文件名: OwneChangeDocManagerImpl.java
 */

package com.core.scpwms.server.service.change.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.core.business.exception.BusinessException;
import com.core.business.model.domain.CreateInfo;
import com.core.business.model.domain.UpdateInfo;
import com.core.business.web.security.UserHolder;
import com.core.db.server.service.pojo.DefaultBaseManager;
import com.core.scpwms.server.constant.ExceptionConstant;
import com.core.scpwms.server.constant.WmsConstant;
import com.core.scpwms.server.domain.InventoryInfo;
import com.core.scpwms.server.enumerate.EnuInfoType;
import com.core.scpwms.server.enumerate.EnuInvenHisType;
import com.core.scpwms.server.enumerate.EnuOwnerChangeDocDetailStatus;
import com.core.scpwms.server.enumerate.EnuOwnerChangeDocStatus;
import com.core.scpwms.server.enumerate.EnuWarehouseOrderStatus;
import com.core.scpwms.server.filter.WarehouseHolder;
import com.core.scpwms.server.model.change.OwnerChangeDoc;
import com.core.scpwms.server.model.change.OwnerChangeDocDetail;
import com.core.scpwms.server.model.common.OrderType;
import com.core.scpwms.server.model.common.PackageDetail;
import com.core.scpwms.server.model.inventory.Inventory;
import com.core.scpwms.server.model.lot.LotInputData;
import com.core.scpwms.server.model.task.WarehouseTask;
import com.core.scpwms.server.model.user.Owner;
import com.core.scpwms.server.service.change.OwnerChangeDocManager;
import com.core.scpwms.server.service.common.AllocatePickingHelper;
import com.core.scpwms.server.service.common.BCManager;
import com.core.scpwms.server.service.common.CommonHelper;
import com.core.scpwms.server.service.common.InfoMessageManager;
import com.core.scpwms.server.service.common.InventoryHelper;
import com.core.scpwms.server.service.common.InventoryHistoryHelper;
import com.core.scpwms.server.service.common.OrderStatusHelper;
import com.core.scpwms.server.util.LocaleUtil;
import com.core.scpwms.server.util.PrecisionUtils;

/**
 * <p>
 * 货主转换管理
 * </p>
 * 
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *         2014/07/28<br/>
 */
@SuppressWarnings("all")
public class OwnerChangeDocManagerImpl extends DefaultBaseManager implements
		OwnerChangeDocManager {

	private BCManager bcManager;
	private OrderStatusHelper orderStatusHelper;
	private InventoryHelper invHelper;
	private CommonHelper commonHelper;
	private InventoryHistoryHelper historyHelper;
	private AllocatePickingHelper allocatePickingHelper;
	private InfoMessageManager infoMessageManager;

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

	public InventoryHistoryHelper getHistoryHelper() {
		return this.historyHelper;
	}

	public void setHistoryHelper(InventoryHistoryHelper historyHelper) {
		this.historyHelper = historyHelper;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.change.OwneChangeDocManager#batchExecute
	 * (java.util.List)
	 */
	@Override
	public void batchExecute(List<Long> detailIds) {
		// TODO Auto-generated method stub
		// 暂不提供该功能
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.change.OwneChangeDocManager#cancel(java
	 * .util.List)
	 */
	@Override
	public void cancel(List<Long> docIds) {
		for (Long id : docIds) {
			OwnerChangeDoc doc = commonDao.load(OwnerChangeDoc.class, id);

			// 发行->草案
			if (EnuOwnerChangeDocStatus.PUBLISH.equals(doc.getStatus())) {
				orderStatusHelper.changeStatus(doc,
						EnuOwnerChangeDocStatus.DRAFT);

				for (OwnerChangeDocDetail detail : doc.getDetails()) {
					detail.setStatus(EnuOwnerChangeDocDetailStatus.DRAFT);
					commonDao.store(detail);
				}
			}

			// 草案->取消
			else if (EnuOwnerChangeDocStatus.DRAFT.equals(doc.getStatus())) {
				orderStatusHelper.changeStatus(doc,
						EnuOwnerChangeDocStatus.CANCEL);

				for (OwnerChangeDocDetail detail : doc.getDetails()) {
					detail.setStatus(EnuOwnerChangeDocDetailStatus.CANCEL);
					commonDao.store(detail);
				}
			}

			else {
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
	 * com.core.scpwms.server.service.change.OwneChangeDocManager#close(java
	 * .util.List)
	 */
	@Override
	public void close(List<Long> docIds) {
		for (Long id : docIds) {
			OwnerChangeDoc doc = commonDao.load(OwnerChangeDoc.class, id);

			// 执行中并且所有分配数都已经被执行，或者执行完成状态，可以关闭
			if (EnuOwnerChangeDocStatus.PROCESSED.equals(doc.getStatus())
					|| (EnuOwnerChangeDocStatus.PROCESS.equals(doc.getStatus()) && doc
							.getAllocatedUnExecuteQty() <= 0)) {

				orderStatusHelper.changeStatus(doc,
						EnuOwnerChangeDocStatus.CLOSE);
				doc.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
				commonDao.store(doc);

				for (OwnerChangeDocDetail detail : doc.getDetails()) {
					detail.setStatus(EnuOwnerChangeDocDetailStatus.CLOSE);
					commonDao.store(detail);
				}
			} else {
				throw new BusinessException(ExceptionConstant.ERROR_STATUS);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.change.OwneChangeDocManager#deleteDetail
	 * (java.util.List)
	 */
	@Override
	public void deleteDetail(List<Long> detailIds) {
		for (Long id : detailIds) {

			OwnerChangeDocDetail detail = commonDao.load(
					OwnerChangeDocDetail.class, id);

			// 只有草案状态可以删除
			if (!EnuOwnerChangeDocDetailStatus.DRAFT.equals(detail.getStatus())) {
				throw new BusinessException(ExceptionConstant.ERROR_STATUS);
			}

			detail.getOwnerChangeDoc().getDetails().remove(detail);
			detail.removePlanQty(detail.getPlanQty());
			commonDao.delete(detail);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.change.OwneChangeDocManager#docExecute
	 * (java.util.List)
	 */
	@Override
	public void docExecute(List<Long> docIds) {
		for (Long id : docIds) {
			OwnerChangeDoc doc = commonDao.load(OwnerChangeDoc.class, id);

			// 分配中，分配完成状态可以执行
			if (!EnuOwnerChangeDocStatus.ALLOCATING.equals(doc.getStatus())
					&& !EnuOwnerChangeDocStatus.ALLOCATED.equals(doc
							.getStatus())) {
				throw new BusinessException(ExceptionConstant.ERROR_STATUS);
			}

			for (OwnerChangeDocDetail detail : doc.getDetails()) {
				for (WarehouseTask task : detail.getTasks()) {
					Owner descOwner = doc.getDescOwner();

					// 原库存(-)
					Inventory srcInv = invHelper.getInv(task.getInvInfo());
					if (srcInv == null) {
						throw new BusinessException(
								ExceptionConstant.INVENTORY_NOT_FOUND);
					}

					invHelper.removeInv(srcInv, task.getPlanQty());

					// 库存履历(-)
					historyHelper.saveInvHistory(srcInv, task.getPlanQty()
							* (-1), doc.getOrderType(), null,
							EnuInvenHisType.COMPANY_TRANS_OUT,
							doc.getDocSequence(), detail.getId(), null,
							doc.getRelatedBill1(),null);

					// 目标库存(+)
					InventoryInfo newInvInfo = new InventoryInfo();
					try{
						BeanUtils.copyProperties(newInvInfo, task.getInvInfo());
					}catch( Exception e ){
						throw new BusinessException(ExceptionConstant.SYSTEM_EXCEPTION);
					}
					newInvInfo.setOwner(doc.getDescOwner());

					Inventory descInv = invHelper.addInvToBin(newInvInfo,
							task.getPlanQty(), false);

					// 库存履历(+)
					historyHelper.saveInvHistory(descInv, task.getPlanQty(),
							doc.getOrderType(), null,
							EnuInvenHisType.COMPANY_TRANS_IN,
							doc.getDocSequence(), detail.getId(), null,
							doc.getRelatedBill1(),null);

					// 修改WT的实际数和状态
					task.execute(task.getPlanQty());
					orderStatusHelper.changeStatus(task,
							EnuWarehouseOrderStatus.EXECUTE);

					// 修改Detail，Doc的执行数量
					detail.addExecuteQty(task.getPlanQty());
				}

				// 修改Detail状态
				if (EnuOwnerChangeDocDetailStatus.ALLOCATED.equals(detail
						.getStatus())
						|| EnuOwnerChangeDocDetailStatus.ALLOCATING
								.equals(detail.getStatus())) {
					if (detail.getExecuteQty() > 0) {
						detail.setStatus(EnuOwnerChangeDocDetailStatus.PROCESS);
					}
				}

				if (EnuOwnerChangeDocDetailStatus.PROCESS.equals(detail
						.getStatus()) && detail.getUnExecuteQty() <= 0) {
					detail.setStatus(EnuOwnerChangeDocDetailStatus.PROCESSED);
				}
			}

			// 修改Doc状态
			if (EnuOwnerChangeDocStatus.ALLOCATED.equals(doc.getStatus())
					|| EnuOwnerChangeDocStatus.ALLOCATING.equals(doc
							.getStatus())) {
				if (doc.getExecuteQty() > 0) {
					orderStatusHelper.changeStatus(doc,
							EnuOwnerChangeDocStatus.PROCESS);
				}
			}

			if (EnuOwnerChangeDocStatus.PROCESS.equals(doc.getStatus())
					&& doc.getUnExecuteQty() <= 0) {
				orderStatusHelper.changeStatus(doc,
						EnuOwnerChangeDocStatus.PROCESSED);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.change.OwneChangeDocManager#execute(com
	 * .core.scpwms.server.model.change.OwnerChangeDocDetail, double)
	 */
	@Override
	public void execute(OwnerChangeDocDetail detail, double changeQty) {
		// TODO Auto-generated method stub
		// 暂不提供该功能
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.change.OwneChangeDocManager#save(com.core
	 * .scpwms.server.model.change.OwnerChangeDoc)
	 */
	@Override
	public void save(OwnerChangeDoc doc) {
		if (doc.getSrcOwner().getId().equals(doc.getDescOwner().getId())) {
			throw new BusinessException(ExceptionConstant.SAME_SRC_DESC_OWNER);
		}

		if (doc.isNew()) {
			doc.setWh(WarehouseHolder.getWarehouse());

			OrderType ot = commonHelper.getOrderType(WmsConstant.INV_OCH);
			if (ot == null) {
				throw new BusinessException(
						ExceptionConstant.CANNOT_FIND_ORDER_TYPE,
						new String[] { LocaleUtil.getText("owner.change.doc") });
			}

			doc.setOrderType(ot);
			doc.setDocSequence(bcManager.getOrderSequence(doc.getOrderType(),
					doc.getWh().getId()));
			doc.setTransactionDate(new Date());
			doc.setCreateInfo(new CreateInfo(UserHolder.getUser()));
			commonDao.store(doc);

			orderStatusHelper.changeStatus(doc, EnuOwnerChangeDocStatus.DRAFT);
		} else {
			// 只有草案可以修改
			if (!EnuOwnerChangeDocStatus.DRAFT.equals(doc.getStatus())) {
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
	 * com.core.scpwms.server.service.change.OwneChangeDocManager#saveDetail
	 * (java.lang.Long,
	 * com.core.scpwms.server.model.change.OwnerChangeDocDetail)
	 */
	@Override
	public void saveDetail(Long docId, OwnerChangeDocDetail detail,
			String[] lotData) {
		OwnerChangeDoc doc = commonDao.load(OwnerChangeDoc.class, docId);

		// 只有草案状态可以修改
		if (!EnuOwnerChangeDocStatus.DRAFT.equals(doc.getStatus())) {
			throw new BusinessException(ExceptionConstant.ERROR_STATUS);
		}

		// 用画面输入的包装数量，刷基本包装数量
		PackageDetail packDetail = commonDao.load(PackageDetail.class, detail
				.getPackageDetail().getId());
		double changeQty = PrecisionUtils.getBaseQty(detail.getPlanPackQty(),
				packDetail);
		detail.setPackageDetail(packDetail);
		detail.setLotData(new LotInputData(lotData));

		if (detail.isNew()) {
			detail.setOwnerChangeDoc(doc);
			detail.setStatus(EnuOwnerChangeDocDetailStatus.DRAFT);
			detail.addPlanQty(changeQty);
			doc.getDetails().add(detail);
			commonDao.store(detail);
		} else {
			OwnerChangeDocDetail oldDetail = commonDao.load(
					OwnerChangeDocDetail.class, detail.getId());
			oldDetail.setPackageDetail(packDetail);
			oldDetail.removePlanQty(detail.getPlanQty());
			oldDetail.addPlanQty(changeQty);
			commonDao.store(oldDetail);
		}
		commonDao.store(doc);
	}

	@Override
	public void publish(List<Long> docIds) {
		for (Long id : docIds) {
			OwnerChangeDoc doc = commonDao.load(OwnerChangeDoc.class, id);

			// 只有草案状态可以发行
			if (!EnuOwnerChangeDocStatus.DRAFT.equals(doc.getStatus())) {
				throw new BusinessException(ExceptionConstant.ERROR_STATUS);
			}

			if (doc.getDetails() == null || doc.getDetails().size() == 0) {
				throw new BusinessException(ExceptionConstant.EMPTY_ORDER);
			}

			for (OwnerChangeDocDetail detail : doc.getDetails()) {
				detail.setStatus(EnuOwnerChangeDocDetailStatus.PUBLISH);
				commonDao.store(detail);
			}

			orderStatusHelper
					.changeStatus(doc, EnuOwnerChangeDocStatus.PUBLISH);
			doc.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
			commonDao.store(doc);
		}
	}

	@Override
	public void allocateExecute(List<Long> docIds){
		for (Long id : docIds) {
			OwnerChangeDoc doc = commonDao.load(OwnerChangeDoc.class, id);

			// 只有发行状态可以
			if (!EnuOwnerChangeDocStatus.PUBLISH.equals(doc.getStatus())) {
				throw new BusinessException(ExceptionConstant.ERROR_STATUS);
			}

			// 分配库存
			List<Long> ids = new ArrayList<Long>(1);
			ids.add(id);
			allocatePickingHelper.allocateOwnerDoc(ids, doc.getWh().getId());

			// 只有全部分配完成，才能做转换执行
			doc = commonDao.load(OwnerChangeDoc.class, id);
			for (OwnerChangeDocDetail detail : doc.getDetails()) {
				if (!EnuOwnerChangeDocDetailStatus.ALLOCATED.equals(detail
						.getStatus())) {
					if (UserHolder.getUser() == null) {
						// 由EDI调用
						infoMessageManager.addMessage("货主转换异常",
								"货主转换单(" + doc.getDocSequence()
										+ "),未能自动执行成功。请确认库存后手工执行。",
								EnuInfoType.WARN, doc.getWh().getId());
					}

					// Error:库存不足
					throw new BusinessException(
							ExceptionConstant.INVENTORY_NOT_ENOUGH,
							new String[] { detail.getSku().getName() }, true);
				}
			}

			// 转换执行
			docExecute(ids);
		}
	}
}
