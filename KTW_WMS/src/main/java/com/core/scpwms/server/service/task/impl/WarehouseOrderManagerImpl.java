/**
 * (C)2012 MBP Corporation. All rights reserved.
 * 系统名称 : MBP-WMS
 * 文件名   : WarehouseTaskManagerImpl.java
 */

package com.core.scpwms.server.service.task.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.core.business.exception.BusinessException;
import com.core.business.model.domain.CreateInfo;
import com.core.business.model.domain.UpdateInfo;
import com.core.business.web.security.UserHolder;
import com.core.db.server.service.pojo.DefaultBaseManager;
import com.core.scpwms.server.constant.ExceptionConstant;
import com.core.scpwms.server.constant.WmsConstant;
import com.core.scpwms.server.enumerate.EnuOutboundDeliveryStatus;
import com.core.scpwms.server.enumerate.EnuPickMethod;
import com.core.scpwms.server.enumerate.EnuPutawayDocStatus;
import com.core.scpwms.server.enumerate.EnuStoreRole;
import com.core.scpwms.server.enumerate.EnuWOType;
import com.core.scpwms.server.enumerate.EnuWarehouseOrderStatus;
import com.core.scpwms.server.enumerate.EnuWaveStatus;
import com.core.scpwms.server.enumerate.EnuWaveWorkModel;
import com.core.scpwms.server.filter.WarehouseHolder;
import com.core.scpwms.server.model.outbound.OutboundDelivery;
import com.core.scpwms.server.model.outbound.WaveDoc;
import com.core.scpwms.server.model.putaway.PutawayDoc;
import com.core.scpwms.server.model.putaway.PutawayDocDetail;
import com.core.scpwms.server.model.task.WarehouseOrder;
import com.core.scpwms.server.model.task.WarehouseTask;
import com.core.scpwms.server.model.warehouse.StorageType;
import com.core.scpwms.server.service.common.AllocatePickingHelper;
import com.core.scpwms.server.service.common.BCManager;
import com.core.scpwms.server.service.common.CommonHelper;
import com.core.scpwms.server.service.common.InventoryHelper;
import com.core.scpwms.server.service.common.InventoryHistoryHelper;
import com.core.scpwms.server.service.common.OrderStatusHelper;
import com.core.scpwms.server.service.outbound.WaveDocManager;
import com.core.scpwms.server.service.task.WarehouseOrderManager;

/**
 * 
 * <p>
 * 作业管理实现类
 * </p>
 * 
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *         2014/07/18<br/>
 */
public class WarehouseOrderManagerImpl extends DefaultBaseManager implements
		WarehouseOrderManager {

	private BCManager bcManager;
	private InventoryHelper invHelper;
	private InventoryHistoryHelper historyHelper;
	private OrderStatusHelper orderStatusHelper;
	private CommonHelper commonHelper;
	private WaveDocManager waveDocManager;
	private AllocatePickingHelper allocatePickingHelper;

	public BCManager getBcManager() {
		return this.bcManager;
	}

	public void setBcManager(BCManager bcManager) {
		this.bcManager = bcManager;
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

	public OrderStatusHelper getOrderStatusHelper() {
		return this.orderStatusHelper;
	}

	public void setOrderStatusHelper(OrderStatusHelper orderStatusHelper) {
		this.orderStatusHelper = orderStatusHelper;
	}

	public CommonHelper getCommonHelper() {
		return this.commonHelper;
	}

	public void setCommonHelper(CommonHelper commonHelper) {
		this.commonHelper = commonHelper;
	}

	public WaveDocManager getWaveDocManager() {
		return this.waveDocManager;
	}

	public void setWaveDocManager(WaveDocManager waveDocManager) {
		this.waveDocManager = waveDocManager;
	}

	public AllocatePickingHelper getAllocatePickingHelper() {
		return this.allocatePickingHelper;
	}

	public void setAllocatePickingHelper(
			AllocatePickingHelper allocatePickingHelper) {
		this.allocatePickingHelper = allocatePickingHelper;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.task.WarehouseTaskManager#createPutawayWo
	 * (java.util.List)
	 */
	@Override
	public WarehouseOrder createPutawayWo(List<Long> ids) {
		return createWo(ids, EnuWOType.PUTAWAY, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.task.WarehouseTaskManager#createPickupWo
	 * (java.util.List)
	 */
	@Override
	public WarehouseOrder createPickupWo(List<Long> ids) {
		return createWo(ids, EnuWOType.PICKUP, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.task.WarehouseTaskManager#addPickUpTask2Wo
	 * (java.util.List, java.util.List)
	 */
	@Override
	public void addPickTask2Wo(List<Long> woId, List<Long> wtIds) {
		WarehouseOrder wo = commonDao.load(WarehouseOrder.class, woId.get(0));

		for (Long wtId : wtIds) {
			WarehouseTask wt = commonDao.load(WarehouseTask.class, wtId);

			// 加入WO
			addWtToWo(wt, wo, EnuWOType.PICKUP);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.task.WarehouseTaskManager#addPickUpTask2Wo
	 * (java.lang.Long, java.util.List)
	 */
	@Override
	public void addPickTask2Wo(Long woId, List<Long> wtIds) {
		WarehouseOrder wo = commonDao.load(WarehouseOrder.class, woId);

		for (Long wtId : wtIds) {
			WarehouseTask wt = commonDao.load(WarehouseTask.class, wtId);

			// 加入WO
			addWtToWo(wt, wo, EnuWOType.PICKUP);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.task.WarehouseTaskManager#addPutawayTask2Wo
	 * (java.util.List, java.util.List)
	 */
	@Override
	public void addPutawayTask2Wo(List<Long> woId, List<Long> wtIds) {
		WarehouseOrder wo = commonDao.load(WarehouseOrder.class, woId.get(0));

		for (Long wtId : wtIds) {
			WarehouseTask wt = commonDao.load(WarehouseTask.class, wtId);

			// 加入WO
			addWtToWo(wt, wo, EnuWOType.PUTAWAY);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.task.WarehouseTaskManager#addPutawayTask2Wo
	 * (java.lang.Long, java.util.List)
	 */
	@Override
	public void addPutawayTask2Wo(Long woId, List<Long> wtIds) {
		WarehouseOrder wo = commonDao.load(WarehouseOrder.class, woId);

		for (Long wtId : wtIds) {
			WarehouseTask wt = commonDao.load(WarehouseTask.class, wtId);

			// 加入WO
			addWtToWo(wt, wo, EnuWOType.PUTAWAY);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.task.WarehouseOrderManager#deleteWt(java
	 * .lang.Long, java.util.List)
	 */
	@Override
	public void deleteWt(List<Long> wtIds) {
		List<WarehouseTask> tasks = new ArrayList<WarehouseTask>(wtIds.size());
		for (Long wtId : wtIds) {
			WarehouseTask wt = commonDao.load(WarehouseTask.class, wtId);
			tasks.add(wt);
		}
		removeWtFromWo(tasks);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.task.WarehouseOrderManager#save(com.core
	 * .scpwms.server.model.task.WarehouseOrder)
	 */
	@Override
	public void save(WarehouseOrder wo) {
		saveWo(wo, EnuWOType.PUTAWAY);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.task.WarehouseOrderManager#savePickWo(
	 * com.core.scpwms.server.model.task.WarehouseOrder)
	 */
	@Override
	public void savePickWo(WarehouseOrder wo) {
		saveWo(wo, EnuWOType.PICKUP);
	}

	private void saveWo(WarehouseOrder wo, String type) {
		if (wo.getLabor() != null && wo.getLabor().getId() == null) {
			wo.setLabor(null);
		}

		if (wo.isNew()) {
			wo.setWh(WarehouseHolder.getWarehouse());
			wo.setOrderSequence(bcManager.getWarehouseOrderSeq(WarehouseHolder
					.getWarehouse().getCode()));
			wo.setWarehouseOrderType(type);
			wo.setCreateInfo(new CreateInfo(UserHolder.getUser()));

			commonDao.store(wo);
			orderStatusHelper.changeStatus(wo, EnuWarehouseOrderStatus.DRAFT);
		} else {
			if (!EnuWarehouseOrderStatus.DRAFT.equals(wo.getStatus())) {
				throw new BusinessException(ExceptionConstant.ERROR_STATUS);
			}
			wo.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
			commonDao.store(wo);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.task.WarehouseOrderManager#publish(java
	 * .util.List, java.util.Date, java.util.Date, java.lang.Long)
	 */
	@Override
	public void publish(List<Long> woIds) {
		for (Long id : woIds) {
			WarehouseOrder wo = commonDao.load(WarehouseOrder.class, id);

			// 只有草案状态可以发行
			if (!EnuWarehouseOrderStatus.DRAFT.equals(wo.getStatus())) {
				throw new BusinessException(ExceptionConstant.ERROR_STATUS);
			}

			// 计划数量 <= 0
			if (wo.getTasks() == null || wo.getTasks().size() == 0) {
				throw new BusinessException(
						ExceptionConstant.CANNOT_PUBLISH_AS_NO_DETAIL);
			}
			// 计算是否有尾箱
			isHalfFullCase(wo);
			orderStatusHelper.changeStatus(wo, EnuWarehouseOrderStatus.PUBLISH);
			orderStatusHelper.changeStatus(wo, EnuWarehouseOrderStatus.PLANED);
			commonDao.store(wo);
		}
	}
	
	private void isHalfFullCase( WarehouseOrder wo ){
		// 该WO中是否有尾箱
		StringBuilder sql = new StringBuilder();
		sql.append(" select T.WO_ID,T.SO_NUMBER,T.SKU_ID,T.FROM_BIN_ID,T.CONTAINER_SEQ,T.CS_PACK_COEFFICIENT");
		sql.append(" ,sum(QTY) - (floor(sum(QTY)/CS_PACK_COEFFICIENT))*CS_PACK_COEFFICIENT AS PC_QTY");
		sql.append(" from( select");
		sql.append(" wo.ID AS WO_ID");
		sql.append(" ,obdDetail.RELATED_BILL1 AS SO_NUMBER");
		sql.append(" ,sku.ID AS SKU_ID");
		sql.append(" ,from_bin.ID AS FROM_BIN_ID");
		sql.append(" ,case when st.ROLE = 'R2000' then '' else wt.CONTAINER_SEQ end AS CONTAINER_SEQ");
		sql.append(" ,p2000.COEFFICIENT AS CS_PACK_COEFFICIENT");
		sql.append(" ,wt.PLAN_QTY AS QTY");
		sql.append(" from WMS_WAREHOUSE_TASK　wt");
		sql.append(" left join WMS_OUTBOUND_DELIVERY_DETAIL obdDetail on wt.OBD_DETAIL_ID = obdDetail.ID");
		sql.append(" left join WMS_OUTBOUND_DELIVERY obd on obdDetail.OBD_ID = obd.ID");
		sql.append(" left join WMS_WAREHOUSE_ORDER wo on wt.WO_ID = wo.ID");
		sql.append(" left join WMS_STORAGE_TYPE st on wo.STORAGE_TYPE_ID = st.ID");
		sql.append(" left join WMS_QUANT quant on wt.QUANT_ID = quant.ID");
		sql.append(" left join wms_sku sku on quant.SKU = sku.ID");
		sql.append(" left join WMS_BIN from_bin on wt.BIN_ID = from_bin.ID");
		sql.append(" left join WMS_PACKAGE_INFO pi on sku.PACK_INFO_ID = pi.ID");
		sql.append(" left join WMS_PACKAGE_DETAIL p2000 on p2000.ID = pi.P2000");
		sql.append(" where wo.ID = ? )T");
		sql.append(" group by T.WO_ID,T.SO_NUMBER,T.SKU_ID,T.FROM_BIN_ID,T.CONTAINER_SEQ,T.CS_PACK_COEFFICIENT");
		sql.append(" having sum(QTY) - (floor(sum(QTY)/CS_PACK_COEFFICIENT))*CS_PACK_COEFFICIENT > 0");
		
		List<Object> result = commonDao.findBySqlQuery(sql.toString(), new Object[]{wo.getId()});
		
		if( result != null && result.size() > 0 ){
			wo.setHasHalfFullCase(Boolean.TRUE);
		}
		else{
			wo.setHasHalfFullCase(Boolean.FALSE);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.task.WarehouseOrderManager#cancel(java
	 * .util.List)
	 */
	@Override
	public void cancel(List<Long> woIds) {
		for (Long id : woIds) {
			WarehouseOrder wo = commonDao.load(WarehouseOrder.class, id);

			if (EnuWarehouseOrderStatus.PLANED.equals(wo.getStatus())) {
				orderStatusHelper.changeStatus(wo,
						EnuWarehouseOrderStatus.DRAFT);
				commonDao.store(wo);
			}

			else if (EnuWarehouseOrderStatus.PUBLISH.equals(wo.getStatus())) {
				orderStatusHelper.changeStatus(wo,
						EnuWarehouseOrderStatus.DRAFT);
				commonDao.store(wo);

			}
			// 草案状态，WO的计划数量》0，先取消加入的WT
			else if (EnuWarehouseOrderStatus.DRAFT.equals(wo.getStatus())
					&& wo.getPlanQty() > 0) {
				List<WarehouseTask> tasks = new ArrayList<WarehouseTask>(wo
						.getTasks().size());

				if (wo.getTasks() != null && wo.getTasks().size() > 0) {
					tasks.addAll(wo.getTasks());
					removeWtFromWo(tasks);
				} else {
					commonDao.delete(wo);
				}
			}
			// 草案状态，WO的计划《=0，直接删除
			else if (EnuWarehouseOrderStatus.DRAFT.equals(wo.getStatus())
					&& wo.getPlanQty() <= 0) {
				commonDao.delete(wo);
			}

		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.task.WarehouseOrderManager#close(java.
	 * util.List, java.lang.String)
	 */
	@Override
	public void close(List<Long> woIds, String desc) {
		for (Long id : woIds) {
			WarehouseOrder wo = commonDao.load(WarehouseOrder.class, id);

			// 下属的WT如果有尚未执行完成的，不能关闭该WO
			if (wo.getTasks() != null) {
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
				} else {
					throw new BusinessException(
							ExceptionConstant.CANNOT_CLOSE_WO_AS_HAS_WT_TODO);
				}
			}

		}
	}

	private WarehouseOrder createWo(List<Long> ids, String type, StorageType st) {
		WarehouseOrder wo = null;

		for (Long wtId : ids) {
			WarehouseTask wt = commonDao.load(WarehouseTask.class, wtId);

			if (wo == null) {
				wo = new WarehouseOrder();
				wo.setWh(wt.getWh());
				wo.setOrderSequence(bcManager.getWarehouseOrderSeq(wo.getWh()
						.getCode()));
				wo.setWarehouseOrderType(type);
				wo.setSt(st);
				wo.setCreateInfo(new CreateInfo(UserHolder.getUser()));
				commonDao.store(wo);
				orderStatusHelper.changeStatus(wo,
						EnuWarehouseOrderStatus.DRAFT);
			}

			addWtToWo(wt, wo, type);
		}

		if (wo != null) {
			updateTotalInfo(wo.getId());
		}
		return wo;
	}
	
	private WarehouseOrder createWo(List<Long> ids, String type, StorageType st, String pickMethod) {
		WarehouseOrder wo = null;

		for (Long wtId : ids) {
			WarehouseTask wt = commonDao.load(WarehouseTask.class, wtId);

			if (wo == null) {
				wo = new WarehouseOrder();
				wo.setWh(wt.getWh());
				wo.setOrderSequence(bcManager.getWarehouseOrderSeq(wo.getWh().getCode()));
				wo.setWarehouseOrderType(type);
				wo.setSt(st);
				wo.setPickMethod(pickMethod);
				wo.setCreateInfo(new CreateInfo(UserHolder.getUser()));
				commonDao.store(wo);
				orderStatusHelper.changeStatus(wo, EnuWarehouseOrderStatus.DRAFT);
			}

			addWtToWo(wt, wo, type);
		}

		if (wo != null) {
			updateTotalInfo(wo.getId());
		}
		return wo;
	}

	private void removeWtFromWo(List<WarehouseTask> tasks) {
		for (WarehouseTask wt : tasks) {
			WarehouseOrder wo = wt.getWo();

			// 只有计划状态的WT可以删除
			if (!EnuWarehouseOrderStatus.PLANED.equals(wt.getStatus())) {
				throw new BusinessException(ExceptionConstant.ERROR_STATUS);
			}

			// WT状态修改为发行
			wt.setWo(null);
			orderStatusHelper.changeStatus(wt, EnuWarehouseOrderStatus.PUBLISH);

			// 修改WO头部的计划数量
			wo.getTasks().remove(wt);
			wo.removePlanQty(wt.getPlanQty());
			commonDao.store(wo);

			// 上架
			if (EnuWOType.PUTAWAY.equals(wo.getWarehouseOrderType())) {
				// 修改WT对应的上架单状态
				PutawayDoc putawayDoc = wt.getPutawayDocDetail()
						.getPutawayDoc();

				// 上架单状态现为分配完成，改为分配中
				if (EnuPutawayDocStatus.PLANED.equals(putawayDoc.getStatus())) {
					orderStatusHelper.changeStatus(putawayDoc,
							EnuPutawayDocStatus.PLANING);
				}
				// 分配中状态的上架单，下属WT都没有加入WO，改为分配完成或者分配中
				if (EnuPutawayDocStatus.PLANING.equals(putawayDoc.getStatus())) {
					boolean allPublish = true;

					stopLoop: for (PutawayDocDetail detail : putawayDoc
							.getDetails()) {
						for (WarehouseTask task : detail.getTasks()) {
							if (!EnuWarehouseOrderStatus.PUBLISH.equals(task
									.getStatus())) {
								allPublish = false;
								break stopLoop;
							}
						}
					}

					if (allPublish) {
						// 退回到 分配中
						if (putawayDoc.getUnAllocateQty() > 0) {
							orderStatusHelper.changeStatus(putawayDoc,
									EnuPutawayDocStatus.ALLOCATING);
						}
						// 退回到分配完成
						else {
							orderStatusHelper.changeStatus(putawayDoc,
									EnuPutawayDocStatus.ALLOCATE);
						}
					}
				}
			}
			// 拣货
			else if (EnuWOType.PICKUP.equals(wo.getWarehouseOrderType())) {
				// Nothing
			}

			else if (EnuWOType.INVAPPLY.equals(wo.getWarehouseOrderType())) {
				// TODO
			}
		}
	}

	private void addWtToWo(WarehouseTask wt, WarehouseOrder wo, String type) {
		// 只有草案状态的WO可以添加
		if (!EnuWarehouseOrderStatus.DRAFT.equals(wo.getStatus())) {
			throw new BusinessException(ExceptionConstant.ERROR_STATUS);
		}

		// 只有发行状态的WT可以添加至WO
		if (!EnuWarehouseOrderStatus.PUBLISH.equals(wt.getStatus())) {
			throw new BusinessException(ExceptionConstant.ERROR_STATUS);
		}

		// WT状态修改为已计划
		wt.setWo(wo);
		orderStatusHelper.changeStatus(wt, EnuWarehouseOrderStatus.PLANED);
		commonDao.store(wt);

		// 修改WO头部的计划数量
		wo.getTasks().add(wt);
		wo.addPlanQty(wt.getPlanQty());
		commonDao.store(wo);
		updateTotalInfo(wo.getId());

		// 上架
		if (EnuWOType.PUTAWAY.equals(type)) {

			// 修改WT对应的上架单状态
			PutawayDoc putawayDoc = wt.getPutawayDocDetail().getPutawayDoc();

			// 上架单状态现为分配或者分配中，改为计划中
			if (EnuPutawayDocStatus.ALLOCATE.equals(putawayDoc.getStatus())
					|| EnuPutawayDocStatus.ALLOCATING.equals(putawayDoc
							.getStatus())) {
				orderStatusHelper.changeStatus(putawayDoc,
						EnuPutawayDocStatus.PLANING);
			}
			// 计划中状态的上架单，下属WT都加入了WO，改为计划完成
			if (EnuPutawayDocStatus.PLANING.equals(putawayDoc.getStatus())) {
				boolean allPlaned = true;

				stopLoop: for (PutawayDocDetail detail : putawayDoc
						.getDetails()) {
					for (WarehouseTask task : detail.getTasks()) {
						if (!EnuWarehouseOrderStatus.PLANED.equals(task
								.getStatus())) {
							allPlaned = false;
							break stopLoop;
						}
					}
				}

				if (allPlaned) {
					orderStatusHelper.changeStatus(putawayDoc,
							EnuPutawayDocStatus.PLANED);
				}
			}
		}
		// 拣货
		else if (EnuWOType.PICKUP.equals(type)) {
			// Nothing
		}

		else if (EnuWOType.INVAPPLY.equals(type)) {
			// TODO
		}
	}
	
	private String getInventoryInfos(List<String[]> info){
		 StringBuffer sbTitle = new StringBuffer();
         sbTitle.append("<table><tr>"
         		+ "<th>物料编号</th>"
         		+ "<th>预订数</th>"
         		+ "<th>库存可用量</th>"
         		+ "</tr>");
         sbTitle.append("<br />");

         // 1.单号 2.商品id 3.需求数 4.总需求数 5.库存数 6.批次信息
         for (String[] error : info) {
             if (error.length >= 6) {
            	 sbTitle.append("<tr>");
            	 //sbTitle.append("<td>" + error[0] + "</td>" );
            	 sbTitle.append("<td>" + error[1] + "</td>" );
            	 //sbTitle.append("<td>" + error[2] + "</td>");
            	 //sbTitle.append("<td>" + error[3] + "</td>");
            	 //sbTitle.append("<td>" + error[4] + "</td>");
            	 sbTitle.append("<td>" + error[5] + "</td>");
            	 sbTitle.append("<td>" + error[6] + "</td>");
            	 sbTitle.append("</tr>");
            	 sbTitle.append("<br />");
             }
         }
         sbTitle.append("</table>");
         
         return sbTitle.toString();
	}

	@Override
	public int autoCreatePickWo(List<Long> waveDocIds, Integer laborNumber) {
		int woNum = 0;
		
		for (Long id : waveDocIds) {
			WaveDoc waveDoc = commonDao.load(WaveDoc.class, id);
			// 按单拣货
			if (EnuWaveWorkModel.SUM_ORDER_PICK.equals(waveDoc.getWorkModel())) {
				String hql = " select obd.id from OutboundDelivery obd where obd.waveDoc.id = :waveDocId ";
				List<Long> obdIds = commonDao.findByQuery(hql, "waveDocId", id);
				
				for (Long obdId : obdIds) {
					woNum += createWoOneObd4Asus2(obdId);
				}
			}
			// 按批次拣货
			else {
				throw new BusinessException("暂不支持按批次拣货。");
				//createWoByLot(waveDoc, laborNumber);
			}
		}
		
		updateStatusBar(ExceptionConstant.AUTO_CREATE_WO, new String[] { String.valueOf(woNum) }, Boolean.TRUE);
		return woNum;
	}

	private void createWoByLot(WaveDoc waveDoc, Integer laborNumber) {
		// 所有作业按照拣货顺序排，拣货顺序为空或一样的情况下，用库位编码
		String hql = "from WarehouseTask wt where wt.obdDetail.obd.waveDoc.id = :waveDocId and wt.status = 200 order by wt.invInfo.bin.sortIndex, wt.invInfo.bin.binCode";
		List<WarehouseTask> tasks = commonDao.findByQuery(hql, "waveDocId",
				waveDoc.getId());

		// 该波次下的所有WT都加入了WO
		if (tasks == null || tasks.size() == 0) {
			throw new BusinessException(ExceptionConstant.WV_ALL_WT_ADD_TO_WO);
		}

		// 按照库位合计作业，按库位数平均分。
		List<Long> binList = (List<Long>) getBinMap(tasks)[0];
		Map<Long, List<WarehouseTask>> binValuesMap = (Map<Long, List<WarehouseTask>>) getBinMap(tasks)[1];

		int[] arvQtys = getAvgWorkArray(binValuesMap.size(), laborNumber);
		WarehouseOrder wo = null;
		int binIndex = 0;

		for (int i = 0; i < arvQtys.length; i++) {
			for (int j = 0; j < arvQtys[i]; j++) {
				Long thisBinId = binList.get(binIndex);
				List<WarehouseTask> wts = binValuesMap.get(thisBinId);
				if (j == 0) {
					wo = newWo(wts.get(0));
				}
				for (WarehouseTask wt : wts) {
					addWtToWo(wt, wo, EnuWOType.PICKUP);
				}
				binIndex++;
			}
		}

		updateStatusBar(ExceptionConstant.AUTO_CREATE_WO,
				new String[] { String.valueOf(arvQtys.length) }, Boolean.TRUE);
	}

	private Object[] getBinMap(List<WarehouseTask> tasks) {
		Object[] result = new Object[2];

		List<Long> binList = new ArrayList<Long>();
		Map<Long, List<WarehouseTask>> binMap = new LinkedHashMap<Long, List<WarehouseTask>>();

		for (WarehouseTask task : tasks) {
			Long binId = task.getInvInfo().getBin().getId();

			if (binMap.containsKey(binId)) {
				// Double[] vals = result.get(binId);
				//
				// // totalWeight
				// vals[0] += task.getInvInfo().getQuant().getSku()
				// .getGrossWeight() == null ? 0D : task.getInvInfo()
				// .getQuant().getSku().getGrossWeight();
				// // totalVolume
				// vals[1] += task.getInvInfo().getQuant().getSku().getVolume()
				// == null ? 0D
				// : task.getInvInfo().getQuant().getSku().getVolume();
				// // totalMetric
				// vals[2] += task.getInvInfo().getQuant().getSku().getMetric()
				// == null ? 0D
				// : task.getInvInfo().getQuant().getSku().getMetric();
				// // totalPrice
				// vals[3] += task.getInvInfo().getQuant().getSku().getPrice()
				// == null ? 0D
				// : task.getInvInfo().getQuant().getSku().getPrice();
				// // totalQty
				// vals[4] += task.getPlanQty();
				List<WarehouseTask> binTasks = binMap.get(binId);
				binTasks.add(task);
				binMap.put(binId, binTasks);
			} else {
				// Double[] vals = new Double[5];
				//
				// // totalWeight
				// vals[0] = task.getInvInfo().getQuant().getSku()
				// .getGrossWeight() == null ? 0D : task.getInvInfo()
				// .getQuant().getSku().getGrossWeight();
				// // totalVolume
				// vals[1] = task.getInvInfo().getQuant().getSku().getVolume()
				// == null ? 0D
				// : task.getInvInfo().getQuant().getSku().getVolume();
				// // totalMetric
				// vals[2] = task.getInvInfo().getQuant().getSku().getMetric()
				// == null ? 0D
				// : task.getInvInfo().getQuant().getSku().getMetric();
				// // totalPrice
				// vals[3] = task.getInvInfo().getQuant().getSku().getPrice() ==
				// null ? 0D
				// : task.getInvInfo().getQuant().getSku().getPrice();
				// // totalQty
				// vals[4] = task.getPlanQty();
				List<WarehouseTask> binTasks = new ArrayList<WarehouseTask>();
				binTasks.add(task);
				binMap.put(binId, binTasks);
				binList.add(binId);
			}
		}

		result[0] = binList;
		result[1] = binMap;
		return result;
	}

	private WarehouseOrder newWo(WarehouseTask task) {
		WarehouseOrder wo = new WarehouseOrder();
		wo.setWh(task.getWh());
		wo.setOrderSequence(bcManager
				.getWarehouseOrderSeq(wo.getWh().getCode()));
		wo.setWarehouseOrderType(EnuWOType.PICKUP);
		wo.setCreateInfo(new CreateInfo(UserHolder.getUser()));
		commonDao.store(wo);
		orderStatusHelper.changeStatus(wo, EnuWarehouseOrderStatus.DRAFT);

		return wo;
	}
	
	// 重庆仓
	private int createWoOneObd4Asus2(Long obdId){
		// 整托的按照库位所在的功能区分割成出库单
		
		// OBD下的WT分属于几个ST
		String hql = "from WarehouseTask wt where 1=1"
				+ " and wt.obdDetail.obd.id = :obdId "
				+ " and wt.status in (100,200) "
				+ " and wt.isFullPallet in (2,3) "//整托，或者混SO整托
				+ " order by "
				+ " wt.invInfo.bin.storageType.role, "
				+ " wt.invInfo.bin.storageType.id, "
				+ " wt.invInfo.bin.id, "
				+ " wt.invInfo.containerSeq, "
				+ " wt.invInfo.quant.sku.id";
		List<WarehouseTask> wts = commonDao.findByQuery(hql, "obdId", obdId);
		List<Long> woIds = new ArrayList<Long>();
		
		List<Long> wtIds = null;
		
		Long stId = null;
		for( WarehouseTask wt : wts ){
			Long thisStId = wt.getInvInfo().getBin().getStorageType().getId();
			
			// 同一个功能区
			if( stId != null && stId.equals(thisStId)){
				wtIds.add(wt.getId());
			}
			else{
				// 生成WO
				if( wtIds != null && wtIds.size() > 0 ){
					StorageType st = commonDao.load(StorageType.class, stId);
					WarehouseOrder wo = createWo(wtIds, EnuWOType.PICKUP, st, EnuPickMethod.PICK_BY_PDA);//整托都是PDA拣货
					woIds.add(wo.getId());
				}
				
				wtIds = new ArrayList<Long>();
				wtIds.add(wt.getId());
				
				stId = thisStId;
			}
		}
		
		// 最后一个WO
		if( wtIds != null && wtIds.size() > 0 ){
			StorageType st = commonDao.load(StorageType.class, stId);
			WarehouseOrder wo = createWo(wtIds, EnuWOType.PICKUP, st, EnuPickMethod.PICK_BY_PDA);//整托都是PDA拣货
			woIds.add(wo.getId());
		}
		
		if( woIds != null && woIds.size() > 0 ){
			publish(woIds);
		}
		return woIds.size();
		
	}
	
	private int createWoOneObd4Asus(Long obdId){
		// OBD下的WT分属于几个ST
		String hql = "from WarehouseTask wt where 1=1"
				+ " and wt.obdDetail.obd.id = :obdId "
				+ " and wt.status in (100,200) "
				+ " order by wt.invInfo.bin.storageType.role, "
				+ " wt.invInfo.bin.id, "
				+ " wt.invInfo.containerSeq, "
				+ " wt.invInfo.quant.sku.id";
		List<WarehouseTask> wts = commonDao.findByQuery(hql, "obdId", obdId);
		List<Long> woIds = new ArrayList<Long>();
		
		List<Long> wtIds = null;
		
		Long binId = 0L;
		Long skuId = 0L;
		String containerSeq = "";
		StorageType st = null;
		for( WarehouseTask wt : wts ){
			Long thisBinId = wt.getInvInfo().getBin().getId();
			Long thisSkuId = wt.getInvInfo().getQuant().getSku().getId();
			String thisContainerSeq = wt.getInvInfo().getContainerSeq();
			String thisStoreRole = wt.getInvInfo().getBin().getStorageType().getRole();
			
			if( EnuStoreRole.R4040.equals(thisStoreRole) ){
				thisContainerSeq = "";
			}
			
			if( binId.equals(thisBinId) && skuId.equals(thisSkuId) && containerSeq.equals(thisContainerSeq) ){
				wtIds.add(wt.getId());
			}
			else{
				if( wtIds != null && wtIds.size() > 0 ){
					WarehouseOrder wo = createWo(wtIds, EnuWOType.PICKUP, st);
					woIds.add(wo.getId());
				}
				
				wtIds = new ArrayList<Long>();
				wtIds.add(wt.getId());
				
				binId = thisBinId;
				skuId = thisSkuId;
				containerSeq = thisContainerSeq;
				st = wt.getInvInfo().getBin().getStorageType();
			}
		}
		
		if( wtIds != null && wtIds.size() > 0 ){
			WarehouseOrder wo = createWo(wtIds, EnuWOType.PICKUP, st);
			woIds.add(wo.getId());
		}
		
		if( woIds != null && woIds.size() > 0 ){
			publish(woIds);
		}
		return woIds.size();
	}

//	private void createWoOneObd(Long obdId) {
//		OutboundDelivery obd = commonDao.load(OutboundDelivery.class, obdId);
//		WarehouseOrder wo = null;
//		List<Long> wtIds = new ArrayList<Long>();
//
//		for (OutboundDeliveryDetail obdDetail : obd.getDetails()) {
//			if (!EnuOutboundDeliveryDetailStatus.STATUS400.equals(obdDetail
//					.getStatus())) {
//				throw new BusinessException(
//						ExceptionConstant.INVENTORY_NOT_ENOUGH,
//						new String[] { obdDetail.getSku().getCode() + " "
//								+ obdDetail.getSku().getName() }, true);
//			}
//
//			// 创建WO
//			for (WarehouseTask task : obdDetail.getTasks()) {
//				wtIds.add(task.getId());
//			}
//		}
//		wo = createPickupWo(wtIds);
//		orderStatusHelper.changeStatus(wo, EnuWarehouseOrderStatus.PUBLISH);
//		orderStatusHelper.changeStatus(wo, EnuWarehouseOrderStatus.PLANED);
//	}

	/**
	 * <p>
	 * 更新头部合计
	 * </p>
	 * 
	 * @param waveId
	 */
	private void updateTotalInfo(Long woId) {
		WarehouseOrder wo = commonDao.load(WarehouseOrder.class, woId);

		Long skuCount = (Long) commonDao
				.findByQueryUniqueResult(
						"select count( distinct wt.invInfo.quant.sku.id ) from WarehouseTask wt where wt.wo.id=:woId",
						new String[] { "woId" }, new Object[] { woId });

		Double sumWeight = (Double) commonDao
				.findByQueryUniqueResult(
						"select sum(wt.planQty * coalesce( wt.invInfo.quant.sku.grossWeight,0)) from WarehouseTask wt where wt.wo.id=:woId",
						new String[] { "woId" }, new Object[] { woId });

		Double sumVolume = (Double) commonDao
				.findByQueryUniqueResult(
						"select sum(wt.planQty * coalesce(wt.invInfo.quant.sku.volume,0)) from WarehouseTask wt where wt.wo.id=:woId",
						new String[] { "woId" }, new Object[] { woId });
		
		List<String> tripNumberList = commonDao.findByQuery(
				"select distinct wt.obdDetail.obd.relatedBill1 from WarehouseTask wt where wt.wo.id=:woId",
						new String[] { "woId" }, new Object[] { woId });

		wo.setSkuCount(skuCount == null ? 0D : skuCount.doubleValue());
		wo.setSumWeight(sumWeight == null ? 0D : sumWeight);
		wo.setSumVolume(sumVolume == null ? 0D : sumVolume);
		
		StringBuilder tripNumber = new StringBuilder();
		if( tripNumberList != null && tripNumberList.size() > 0 ){
			for( String trip : tripNumberList ){
				if( tripNumber.length() > 0 ){
					tripNumber.append(",");
				}
				tripNumber.append(trip);
			}
		}
//		wo.setRelatedBill1(tripNumber.toString());

		// // 品项数
		// commonDao.executeByNamed(NamedHqlConstant.UPDATE_WO_SKU_COUNT, new
		// String[] { "woId" }, new Object[] { woId });
		//
		// // 总重
		// commonDao.executeByNamed(NamedHqlConstant.UPDATE_WO_SUM_WEIGHT, new
		// String[] { "woId" }, new Object[] { woId });
		//
		// // 总体积
		// commonDao.executeByNamed(NamedHqlConstant.UPDATE_WO_SUM_VOLUME, new
		// String[] { "woId" }, new Object[] { woId });
		//
		// // 总标准度量
		// commonDao.executeByNamed(NamedHqlConstant.UPDATE_WO_SUM_METRIC, new
		// String[] { "woId" }, new Object[] { woId });
		//
		// // 总金额
		// commonDao.executeByNamed(NamedHqlConstant.UPDATE_WO_SUM_PRICE, new
		// String[] { "woId" }, new Object[] { woId });
	}

	private static int[] getAvgWorkArray(int bins, int laborNums) {
		int[] result = null;
		if (bins < laborNums) {
			result = new int[bins];
		} else {
			result = new int[laborNums];
		}

		for (int i = 0; i < result.length; i++) {
			result[i] = 0;
		}

		for (int i = 0; i < bins; i++) {
			result[i % laborNums] += 1;
		}

		return result;
	}

	@Override
	public String getWarehouseOrderStatus(String whCode,
			String warehouseOrderSeq, Date lastUpdateDate) {
		// 反馈信息(String)：
		// “0:正常”;
		// “1:有更新”;
		// “9:已取消”，
		// “-1:异常”

		String result = "-1:异常";
		String hql = "from WarehouseOrder wo where wo.wh.code = :whCode and wo.orderSequence = :woSeq and wo.status >= 200";
		List<WarehouseOrder> woBeans = commonDao
				.findByQuery(hql, new String[] { "whCode", "woSeq" },
						new Object[] { whCode, warehouseOrderSeq });

		if (woBeans == null || woBeans.size() == 0) {
			result = "9:已取消";
		} else {
			WarehouseOrder wo = woBeans.get(0);
			if (wo.getUpdateInfo().getUpdateTime().getTime() == lastUpdateDate
					.getTime()) {
				result = "0:正常";
			} else {
				result = "1:有更新";
			}

			// WO如果还是已分配状态，改为拣货中状态。
			if (EnuWarehouseOrderStatus.PLANED.equals(wo.getStatus())) {
				orderStatusHelper.changeStatus4Other(wo,
						EnuWarehouseOrderStatus.EXECUTE,
						WmsConstant.DPS_SERVICE);
			}

			for (WarehouseTask wt : wo.getTasks()) {
				// 必须是电子标签库位上的Task
				if (wt.getInvInfo().getBin().getProcessProperties().getIsDps() != null
						&& wt.getInvInfo().getBin().getProcessProperties()
								.getIsDps().booleanValue()) {
					// WT如果还是发行状态，改为已分配状态。
					if (EnuWarehouseOrderStatus.PLANED.equals(wt.getStatus())) {
						orderStatusHelper.changeStatus4Other(wt,
								EnuWarehouseOrderStatus.EXECUTE,
								WmsConstant.DPS_SERVICE);
					}
				}
			}
		}

		return result;
	}

	@Override
	public Long getUnPutawayWoCount(Long whId) {
		StringBuffer hql = new StringBuffer(
				" select count(wo.id) from WarehouseOrder wo where 1=1 ");
		hql.append(" and wo.wh.id = :whId ");
		hql.append(" and wo.status in (200,500,600)");
		hql.append(" and wo.warehouseOrderType = 'PUTAWAY'");
		return (Long) commonDao.findByQueryUniqueResult(hql.toString(),
				new String[] { "whId" }, new Object[] { whId });
	}

	@Override
	public Long getUnPickupWoCount(Long whId) {
		StringBuffer hql = new StringBuffer(
				" select count(wo.id) from WarehouseOrder wo where 1=1 ");
		hql.append(" and wo.wh.id = :whId ");
		hql.append(" and wo.status in (200,500,600)");
		hql.append(" and wo.warehouseOrderType = 'PICKUP'");
		return (Long) commonDao.findByQueryUniqueResult(hql.toString(),
				new String[] { "whId" }, new Object[] { whId });
	}

	@Override
	public void closePrint(List<Long> woIds) {
		for( Long woId : woIds ){
			WarehouseOrder wo = commonDao.load(WarehouseOrder.class, woId);
			wo.setIsPrinted(Boolean.TRUE);
			commonDao.store(wo);
		}
	}

	@Override
	public void openPrint(List<Long> woIds) {
		for( Long woId : woIds ){
			WarehouseOrder wo = commonDao.load(WarehouseOrder.class, woId);
			wo.setIsPrinted(Boolean.FALSE);
			commonDao.store(wo);
		}
	}

	@Override
	public void recordCompleteTime(Long woId) {
		WarehouseOrder wo = commonDao.load(WarehouseOrder.class, woId);
		
		if( !EnuWarehouseOrderStatus.CLOSE.equals(wo.getStatus()) ){
			throw new BusinessException("该拣货单尚未全部作业完成！");
		}
		
		wo.setExecuteEndDate(new Date());
	}

	@Override
	public void createPDAWo(List<Long> obdIds) {
		List<Long> woIds = new ArrayList<Long>();
		
		for( Long obdId : obdIds ){
			String hql = "select wt.id from WarehouseTask wt where wt.obdDetail.obd.id = :obdId and wt.wo.id is null and wt.status = 200 ";
			List<Long> wtIds = commonDao.findByQuery(hql, "obdId", obdId);
			if( wtIds != null && wtIds.size() > 0 ){
				// 生成WO
				WarehouseTask wt = commonDao.load(WarehouseTask.class, wtIds.get(0));
				WarehouseOrder wo = createWo(wtIds, EnuWOType.PICKUP, wt.getInvInfo().getBin().getStorageType(), EnuPickMethod.PICK_BY_PDA_PS);
				woIds.add(wo.getId());
			}
		}
		
		//　发行WO
		if( woIds.size() > 0 ){
			publish(woIds);
		}
	}

	@Override
	public void createPickCarWo(List<Long> obdIds) {
		if( obdIds.size() > 4 ){
			throw new BusinessException("拣货小车一次最多能拣4个单子。");
		}
		
		String hql = "select wt.id from WarehouseTask wt where wt.obdDetail.obd.id in (:obdIds) and wt.wo.id is null and wt.status = 200 ";
		List<Long> wtIds = commonDao.findByQuery(hql, "obdIds", obdIds);
		if( wtIds != null && wtIds.size() > 0 ){
			// 生成WO
			WarehouseTask wt = commonDao.load(WarehouseTask.class, wtIds.get(0));
			WarehouseOrder wo = createWo(wtIds, EnuWOType.PICKUP, wt.getInvInfo().getBin().getStorageType(), EnuPickMethod.PICK_BY_CAR);
			
			// 发行WO
			List<Long> woIds = new ArrayList<Long>(1);
			woIds.add(wo.getId());
			publish(woIds);
		}
	}
}
