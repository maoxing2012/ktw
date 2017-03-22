package com.core.scpwms.server.service.outbound.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.core.business.model.domain.CreateInfo;
import com.core.business.web.security.UserHolder;
import com.core.db.server.service.pojo.DefaultBaseManager;
import com.core.scpwms.client.custom.page.picking.AllocateForPickingConstant;
import com.core.scpwms.client.custom.page.picking.ClientObdDoc;
import com.core.scpwms.client.custom.page.picking.ClientObdDocDetail;
import com.core.scpwms.client.custom.page.picking.ClientPickingInventory;
import com.core.scpwms.client.custom.page.picking.ClientPickingTask;
import com.core.scpwms.server.constant.ExceptionConstant;
import com.core.scpwms.server.enumerate.EnuInvApplyDocStatus;
import com.core.scpwms.server.enumerate.EnuInvStatus;
import com.core.scpwms.server.enumerate.EnuOutboundDeliveryStatus;
import com.core.scpwms.server.enumerate.EnuProcessDocStatus;
import com.core.scpwms.server.enumerate.EnuWarehouseOrderStatus;
import com.core.scpwms.server.enumerate.EnuWaveStatus;
import com.core.scpwms.server.enumerate.EnuWtProcessType;
import com.core.scpwms.server.filter.WarehouseHolder;
import com.core.scpwms.server.model.inventory.Inventory;
import com.core.scpwms.server.model.outbound.OutboundDelivery;
import com.core.scpwms.server.model.outbound.OutboundDeliveryDetail;
import com.core.scpwms.server.model.outbound.WaveDoc;
import com.core.scpwms.server.model.outbound.WaveDocDetail;
import com.core.scpwms.server.model.task.WarehouseTask;
import com.core.scpwms.server.model.warehouse.Bin;
import com.core.scpwms.server.service.common.AllocateHelper;
import com.core.scpwms.server.service.common.AllocatePickingHelper;
import com.core.scpwms.server.service.common.BCManager;
import com.core.scpwms.server.service.common.BinHelper;
import com.core.scpwms.server.service.common.InventoryHelper;
import com.core.scpwms.server.service.common.OrderStatusHelper;
import com.core.scpwms.server.service.inventory.QuantManager;
import com.core.scpwms.server.service.outbound.AllocatePickingManager;
import com.core.scpwms.server.util.DateUtil;
import com.core.scpwms.server.util.PrecisionUtils;

/**
 * 
 * <p>
 * 手工分配库存画面的后台manager
 * </p>
 * 
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *         2014年10月11日<br/>
 */
public class AllocatePickingManagerImpl extends DefaultBaseManager implements
		AllocatePickingManager {

	private BCManager bcManager;
	private QuantManager quantManager;
	private InventoryHelper inventoryHelper;
	private BinHelper binHelper;
	private OrderStatusHelper orderStatusHelper;
	private AllocatePickingHelper allocatePickingHelper;

	public BinHelper getBinHelper() {
		return binHelper;
	}

	public void setBinHelper(BinHelper binHelper) {
		this.binHelper = binHelper;
	}

	public BCManager getBcManager() {
		return bcManager;
	}

	public void setBcManager(BCManager bcManager) {
		this.bcManager = bcManager;
	}

	public QuantManager getQuantManager() {
		return quantManager;
	}

	public void setQuantManager(QuantManager quantManager) {
		this.quantManager = quantManager;
	}

	public InventoryHelper getInventoryHelper() {
		return this.inventoryHelper;
	}

	public void setInventoryHelper(InventoryHelper inventoryHelper) {
		this.inventoryHelper = inventoryHelper;
	}

	public OrderStatusHelper getOrderStatusHelper() {
		return this.orderStatusHelper;
	}

	public void setOrderStatusHelper(OrderStatusHelper orderStatusHelper) {
		this.orderStatusHelper = orderStatusHelper;
	}

	public AllocatePickingHelper getAllocatePickingHelper() {
		return this.allocatePickingHelper;
	}

	public void setAllocatePickingHelper(
			AllocatePickingHelper allocatePickingHelper) {
		this.allocatePickingHelper = allocatePickingHelper;
	}

	/**
	 * 根据单据ID查询单据明细和分配任务明细
	 */
	@Override
	public synchronized Map getInitialDataByDocId(Map params) {
		Map result = new HashMap();
		try {
			Long docId = (Long) params.get(AllocateForPickingConstant.PARAM_DOC_ID);
			Long bizType = (Long) params.get(AllocateForPickingConstant.PARAM_DOC_TYPE);

			Map docInfo = getDocInfoByDocId(docId, bizType);

			if (docInfo == null || docInfo.get(AllocateForPickingConstant.RESULT_DOC) == null) {
				result.put(AllocateForPickingConstant.ERROR, "shippingdoc.cannout.found");
				return result;
			}

			result.putAll(docInfo);
			result.putAll(getDocDetailInfoByDocId(docId, bizType));
			result.putAll(getTaskInfoByDocId(docId, bizType));

		} catch (Exception ex) {
			ex.printStackTrace();
			result.put(AllocateForPickingConstant.ERROR, "System.error");
		}
		return result;
	}

	/**
	 * 获取单据信息
	 * 
	 * @param docId
	 * @return
	 */
	public synchronized Map getDocInfoByDocId(Long docId, Long bizType) {

		Map result = new HashMap();
		StringBuffer sb = new StringBuffer();
		ClientObdDoc cliendDoc = new ClientObdDoc();

		if (bizType == 0L) {
			// 波次单
			OutboundDelivery doc = commonDao.load(OutboundDelivery.class, docId);
			cliendDoc.setDocId(doc.getId());
			cliendDoc.setCode(doc.getObdNumber());
			cliendDoc.setOrderQuantity(doc.getPlanQty());
			cliendDoc.setPickedQuantity(doc.getExecuteQty());
		} else {
			// TODO
		}

		result.put(AllocateForPickingConstant.RESULT_DOC, cliendDoc);

		return result;
	}

	/**
	 * 获取单据明细信息
	 * 
	 * @param docId
	 * @return
	 */
	public synchronized Map getDocDetailInfoByDocId(Long docId, Long bizType) {
		Map result = new HashMap();

		StringBuffer sb = new StringBuffer();

		if (bizType == 0L) {
			// 波次单
			String hql = "From OutboundDeliveryDetail detail where detail.obd.id = :obdId";
			List<OutboundDeliveryDetail> obdDetails = commonDao.findByQuery(hql, "obdId", docId);

			if (obdDetails != null && obdDetails.size() > 0) {
				List<ClientObdDocDetail> clientDetails = new ArrayList<ClientObdDocDetail>(obdDetails.size());
				for (OutboundDeliveryDetail obdDetail : obdDetails) {
					ClientObdDocDetail clientDetail = new ClientObdDocDetail();
					clientDetail.setDocDetailId(obdDetail.getId());
					clientDetail.setCustBillNo(null);
					clientDetail.setLineNo(null);
					Long status = obdDetail.getStatus();
					
					clientDetail.setStatus(status);
					clientDetail.setCustBillNo(obdDetail.getObd().getRelatedBill1());
					String lineNo = String.valueOf(obdDetail.getLineNo().intValue()) + "." + String.valueOf(obdDetail.getSubLineNo().intValue());
					clientDetail.setLineNo(Double.parseDouble(lineNo));
					clientDetail.setLotData(obdDetail.getExpDate() == null ? "" : DateUtil.getStringDate(obdDetail.getExpDate(), DateUtil.PURE_DATE_FORMAT));
					clientDetail.setOwnerCode(obdDetail.getOwner().getCode());
					clientDetail.setOwnerName(obdDetail.getOwner().getName());
					clientDetail.setSkuCode(obdDetail.getSku().getCode());
					clientDetail.setSkuName(obdDetail.getSku().getName());
					clientDetail.setPackageName(obdDetail.getPackageDetail().getName());
					clientDetail.setPackQty(obdDetail.getPlanQty());
					clientDetail.setPlanQty(obdDetail.getPlanQty());
					clientDetail.setAllocateQty(obdDetail.getAllocateQty());
					clientDetail.setPickUpQty(obdDetail.getPickUpQty());
					clientDetail.setExecuteQty(obdDetail.getExecuteQty());
					clientDetail.setTrackSeq(null);
					clientDetail.setBinCode(null);
					clientDetail.setObdNum(null);

					clientDetails.add(clientDetail);
				}

				result.put(AllocateForPickingConstant.RESULT_DOC_DETAIL, clientDetails);
				return result;

			}

		} else if (bizType == 1L) {
			// TODO

		} else if (bizType == 2L) {
			// TODO
		}

		return result;

	}

	/**
	 * 获取单据明细对应库存信息
	 * 
	 * @param docId
	 * @return
	 */
	public synchronized Map getInvInfoByDocDetailId(Long detailId, Long bizType) {
		Map result = new HashMap();

		List<String> paramKeyList = new ArrayList<String>();
		List<Object> paramValueList = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();

		if (bizType == 0L) {
			OutboundDeliveryDetail detail = this.commonDao.load(OutboundDeliveryDetail.class, detailId);
			// 收货，拣货，存货，不良品,退拣
			sb.append("From Inventory inv where inv.bin.storageType.role in ('R2000','R3000','R4040','R8000','R1000') ");
			sb.append(" and (inv.baseQty - inv.queuedQty) > 0");// 有可用量
			sb.append(" and (inv.bin.lockStatus = 'LOCK_T1' or inv.bin.lockStatus is null or inv.bin.lockStatus = '') ");// 没有锁
			
			// 仓库
			sb.append(" and inv.wh.id=:whId ");
			paramKeyList.add("whId");
			paramValueList.add(detail.getWh().getId());
			
			// 商品
			sb.append(" and inv.quantInv.quant.sku.id=:skuId");
			paramKeyList.add("skuId");
			paramValueList.add(detail.getSku().getId());
			
			//货主
			sb.append(" and inv.owner.id=:ownerId");
			paramKeyList.add("ownerId");
			paramValueList.add(detail.getOwner().getId());
			
			// 库存状态
			if( !StringUtils.isEmpty(detail.getInvStatus()) ){
				sb.append(" and inv.status=:invStatus");
				paramKeyList.add("invStatus");
				paramValueList.add(detail.getInvStatus());
			}
			// 默认良品
			else{
				sb.append(" and inv.status=:invStatus");
				paramKeyList.add("invStatus");
				paramValueList.add(EnuInvStatus.AVAILABLE);
			}
			
			sb.append(" order by inv.bin.binCode ");

			List<Inventory> invs = (List<Inventory>) this.commonDao.findByQuery(sb.toString(), 
					paramKeyList.toArray(new String[paramKeyList.size()]), 
					paramValueList.toArray(new Object[paramValueList.size()]));

			List<ClientPickingInventory> clientInvs = new ArrayList<ClientPickingInventory>();
			for (Inventory inv : invs) {
				ClientPickingInventory clientObject = new ClientPickingInventory();
				clientObject.setInvId(inv.getId());
				clientObject.setBinCode(inv.getBin().getBinCode());
				clientObject.setOwnerCode(inv.getOwner().getCode());
				clientObject.setOwnerName(inv.getOwner().getName());
				clientObject.setSkuCode(inv.getQuantInv().getQuant().getSku().getCode());
				clientObject.setSkuName(inv.getQuantInv().getQuant().getSku().getName());
				clientObject.setLotSeq(inv.getQuantInv().getQuant().getLotSequence());
				clientObject.setLotData(inv.getQuantInv().getQuant().getDispLot());
				clientObject.setTrackSeq(inv.getTrackSeq());
				clientObject.setStatus(inv.getStatus());
				clientObject.setInboundDate(null);
				clientObject.setCurrentPack(inv.getBasePackage().getName());
				clientObject.setPackQty(inv.getBaseQty());
				clientObject.setBaseQty(inv.getBaseQty());
				clientObject.setQueuedQty(inv.getQueuedQty());
				clientObject.setPalletNo(null);
				clientObject.setContainerNo(inv.getContainerSeq());
				clientObject.setInboundDate(inv.getInboundDate());

				clientInvs.add(clientObject);
			}
			result.put(AllocateForPickingConstant.RESULT_DOC_INV, clientInvs);

		} else if (bizType == 1L) {
			// TODO
		} else if (bizType == 2L) {
			// TODO
		}

		return result;
	}

	/**
	 * 获取单据已经分配的任务信息
	 * 
	 * @param docId
	 * @return
	 */
	public synchronized Map getTaskInfoByDocId(Long docId, Long bizType) {
		Map result = new HashMap();
		if (bizType == 0L) {
			StringBuffer sb = new StringBuffer();
			sb.append("From WarehouseTask wt where wt.obdDetail.obd.id = :obdId");
			List<WarehouseTask> tasks = (List<WarehouseTask>) this.commonDao.findByQuery(sb.toString(), "obdId", docId);

			List<ClientPickingTask> clientTasks = new ArrayList<ClientPickingTask>();
			for (WarehouseTask task : tasks) {
				ClientPickingTask clientTask = new ClientPickingTask();
				
				clientTask.setTaskIds(task.getId());
				clientTask.setDocSequence(task.getTaskSequence());
				clientTask.setStatus(task.getStatus());
				clientTask.setOwnerCode(task.getInvInfo().getOwner().getCode());
				clientTask.setOwnerName(task.getInvInfo().getOwner().getName());
				clientTask.setSkuCode(task.getInvInfo().getQuant().getSku().getCode());
				clientTask.setSkuName(task.getInvInfo().getQuant().getSku().getName());
				clientTask.setLotSeq(task.getInvInfo().getQuant().getLotSequence());
				clientTask.setLot(task.getInvInfo().getQuant().getDispLot());
				clientTask.setTrackSeq(task.getInvInfo().getTrackSeq());
				clientTask.setPkgName(task.getInvInfo().getPackageDetail().getName());
				clientTask.setPackQty(task.getPlanPackQty());
				clientTask.setPlanQty(task.getPlanQty());
				clientTask.setSrcBin(task.getInvInfo().getBin().getBinCode());
				clientTask.setSrcPalletSeq(null);
				clientTask.setSrcContainerSeq(null);
				clientTask.setDescBin(task.getDescBin().getBinCode());
				clientTask.setDescPalletSeq(task.getDescPalletSeq());
				clientTask.setDescContainerSeq(task.getDescContainerSeq());
				clientTask.setInboundDate(null);
				clientTask.setInvStatus(task.getInvInfo().getInvStatus());

				clientTasks.add(clientTask);
			}

			result.put(AllocateForPickingConstant.RESULT_DOC_TASKS, clientTasks);
		} else if (bizType == 1L) {
			// TODO
		} else if (bizType == 2L) {
			// TODO
		}

		return result;
	}

	/**
	 * 客户端调用，返回库存信息
	 */
	@Override
	public synchronized Map getInvDataByDetailId(Map params) {
		Map result = new HashMap();
		try {
			Long docDetailId = (Long) params.get(AllocateForPickingConstant.PARAM_DOC_DETAIL_ID);
			Long bizType = (Long) params.get(AllocateForPickingConstant.PARAM_DOC_TYPE);
			Map invInfo = getInvInfoByDocDetailId(docDetailId, bizType);

			if (invInfo == null || invInfo.get(AllocateForPickingConstant.RESULT_DOC_INV) == null) {
				result.put(AllocateForPickingConstant.ERROR, ExceptionConstant.INVENTORY_NOT_FOUND);
				return result;
			}

			result.putAll(invInfo);

		} catch (Exception ex) {
			ex.printStackTrace();
			result.put(AllocateForPickingConstant.ERROR, "System.error");
		}
		return result;
	}

	/**
	 * 分配库存至Task
	 */
	@Override
	public synchronized Map allocateInv(Map params) {
		Map result = new HashMap();

		Long docDetailId = (Long) params.get(AllocateForPickingConstant.PARAM_DOC_DETAIL_ID);
		Long bizType = (Long) params .get(AllocateForPickingConstant.PARAM_DOC_TYPE);
		Long invId = (Long) params.get(AllocateForPickingConstant.PARAM_DOC_INV_ID);
		Long docID = (Long) params.get(AllocateForPickingConstant.PARAM_DOC_ID);
		Double packQty = Double.parseDouble((String) params.get(AllocateForPickingConstant.PARAM_ALLOCATE_QTY));

		if (bizType == 0L) {
			// 获得单据Detail
			OutboundDeliveryDetail detail = this.commonDao.load(OutboundDeliveryDetail.class, docDetailId);
			Inventory inv = this.commonDao.load(Inventory.class, invId);
			Double allocateQty = PrecisionUtils.getBaseQty(packQty,inv.getBasePackage());
			
			// 分配数>待分配数
			if( allocateQty > detail.getUnAllocateQty() ){
				result.put( AllocateForPickingConstant.ERROR, ExceptionConstant.CANNOT_ALLOCATE_AS_MORE_THAN_TOALLOCATE_QTY);
				return result;
			}
			
			// 该库存不存在
			if( inv == null ){
				result.put( AllocateForPickingConstant.ERROR, ExceptionConstant.INVENTORY_NOT_FOUND);
				return result;
			}
			
			// 分配数>库存可用量
			if (PrecisionUtils.compareByBasePackage(inv.getAvailableQty(),
					allocateQty, inv.getBasePackage()) < 0) {
				result.put( AllocateForPickingConstant.ERROR, ExceptionConstant.CANNOT_ALLOCATE_AS_MORE_THAN_AVAILABLE_QTY);
				return result;
			}

			// 库存分配
			inventoryHelper.allocateInv(inv, allocateQty);
			// 单据分配
			allocatePickingHelper.allocateDetail(detail, allocateQty);
			// 创建WT
			allocatePickingHelper.createWarehouseTask(detail, inv, allocateQty);

			result.putAll(getDocInfoByDocId(docID, bizType));
			result.putAll(getDocDetailInfoByDocId(docID, bizType));
			result.putAll(getTaskInfoByDocId(docID, bizType));
			result.putAll(getInvInfoByDocDetailId(docDetailId, bizType));
			result.put(AllocateForPickingConstant.PARAM_DOC_DETAIL_ID, docDetailId);

		} else if (bizType == 1L) {
			// TODO
		} else if (bizType == 2L) {
			// TODO
		}

		return result;
	}

	public synchronized void executeAllocate(OutboundDeliveryDetail detail,
			Inventory inv, Double allocateQty, Long bizType, Long docID) {

	}

	@Override
	public synchronized Map unAllocateInv(Map params) {
		Map result = new HashMap();

		Long taskId = (Long) params.get(AllocateForPickingConstant.PARAM_DOC_TASK_ID);
		Long docId = (Long) params.get(AllocateForPickingConstant.PARAM_DOC_ID);
		Long bizType = (Long) params.get(AllocateForPickingConstant.PARAM_DOC_TYPE);

		// 只有尚未加入WO的WT可以取消
		WarehouseTask wt = commonDao.load(WarehouseTask.class, taskId);
		if (!EnuWarehouseOrderStatus.DRAFT.equals(wt.getStatus()) && !EnuWarehouseOrderStatus.PUBLISH.equals(wt.getStatus())) {
			result.put( AllocateForPickingConstant.ERROR, ExceptionConstant.CANNOT_CANCEL_TASK_AS_PLANED);
			return result;
		}

		if (bizType == 0L) {
			allocatePickingHelper.cancelPickTask(taskId);

			result.put(AllocateForPickingConstant.PARAM_DOC_TASK_ID, taskId);
			result.putAll(getDocInfoByDocId(docId, bizType));
			result.putAll(getDocDetailInfoByDocId(docId, bizType));
			result.putAll(getTaskInfoByDocId(docId, bizType));

		} else if (bizType == 1L) {
			// TODO
		} else if (bizType == 2L) {
			// TODO

		}

		return result;
	}

}
