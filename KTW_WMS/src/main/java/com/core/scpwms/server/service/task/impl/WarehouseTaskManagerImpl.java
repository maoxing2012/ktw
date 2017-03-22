/**
 * (C)2012 MBP Corporation. All rights reserved.
 * 系统名称 : MBP-WMS
 * 文件名   : WarehouseTaskManagerImpl.java
 */

package com.core.scpwms.server.service.task.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;

import com.core.business.exception.BusinessException;
import com.core.business.model.domain.UpdateInfo;
import com.core.business.utils.DoubleUtil;
import com.core.business.web.security.UserHolder;
import com.core.db.server.service.pojo.DefaultBaseManager;
import com.core.scpwms.server.constant.ExceptionConstant;
import com.core.scpwms.server.domain.AllocatePutawayDetail;
import com.core.scpwms.server.domain.InventoryInfo;
import com.core.scpwms.server.enumerate.EnuInvProcessType;
import com.core.scpwms.server.enumerate.EnuInvenHisType;
import com.core.scpwms.server.enumerate.EnuOutboundDeliveryDetailStatus;
import com.core.scpwms.server.enumerate.EnuOutboundDeliveryStatus;
import com.core.scpwms.server.enumerate.EnuPutawayDocStatus;
import com.core.scpwms.server.enumerate.EnuWarehouseOrderStatus;
import com.core.scpwms.server.enumerate.EnuWaveStatus;
import com.core.scpwms.server.enumerate.EnuWtProcessType;
import com.core.scpwms.server.model.common.PackageDetail;
import com.core.scpwms.server.model.inventory.ContainerInv;
import com.core.scpwms.server.model.inventory.Inventory;
import com.core.scpwms.server.model.outbound.OutboundDelivery;
import com.core.scpwms.server.model.outbound.OutboundDeliveryDetail;
import com.core.scpwms.server.model.outbound.WaveDoc;
import com.core.scpwms.server.model.putaway.PutawayDoc;
import com.core.scpwms.server.model.putaway.PutawayDocDetail;
import com.core.scpwms.server.model.task.WarehouseOrder;
import com.core.scpwms.server.model.task.WarehouseTask;
import com.core.scpwms.server.model.task.WtHistory;
import com.core.scpwms.server.model.warehouse.Bin;
import com.core.scpwms.server.model.warehouse.Labor;
import com.core.scpwms.server.service.common.AllocateHelper;
import com.core.scpwms.server.service.common.AllocatePickingHelper;
import com.core.scpwms.server.service.common.BCManager;
import com.core.scpwms.server.service.common.BinHelper;
import com.core.scpwms.server.service.common.CommonHelper;
import com.core.scpwms.server.service.common.InventoryHelper;
import com.core.scpwms.server.service.common.InventoryHistoryHelper;
import com.core.scpwms.server.service.common.OrderStatusHelper;
import com.core.scpwms.server.service.inventory.InvManager;
import com.core.scpwms.server.service.task.WarehouseTaskManager;
import com.core.scpwms.server.service.warehouse.LaborManager;
import com.core.scpwms.server.util.PrecisionUtils;

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
@SuppressWarnings("all")
public class WarehouseTaskManagerImpl extends DefaultBaseManager implements
		WarehouseTaskManager {
	// 从画面更新
	public static final String EXECUTE_BY_PAGE = "EXECUTE_BY_PAGE";
	// 从DPS更新
	public static final String EXECUTE_BY_DPS = "EXECUTE_BY_DPS";
	// 从手持更新
	public static final String EXECUTE_BY_HT = "EXECUTE_BY_HT";

	private InventoryHelper invHelper;
	private InventoryHistoryHelper historyHelper;
	private OrderStatusHelper orderStatusHelper;
	private CommonHelper commonHelper;
	private AllocateHelper allocateHelper;
	private AllocatePickingHelper allocatePickingHelper;
	public BinHelper binHelper;
	public BCManager bcManager;
	public InvManager invManager;

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

	public AllocateHelper getAllocateHelper() {
		return this.allocateHelper;
	}

	public void setAllocateHelper(AllocateHelper allocateHelper) {
		this.allocateHelper = allocateHelper;
	}

	public AllocatePickingHelper getAllocatePickingHelper() {
		return this.allocatePickingHelper;
	}

	public void setAllocatePickingHelper(
			AllocatePickingHelper allocatePickingHelper) {
		this.allocatePickingHelper = allocatePickingHelper;
	}

	public BinHelper getBinHelper() {
		return this.binHelper;
	}

	public void setBinHelper(BinHelper binHelper) {
		this.binHelper = binHelper;
	}
	
	public BCManager getBcManager() {
		return this.bcManager;
	}

	public void setBcManager(BCManager bcManager) {
		this.bcManager = bcManager;
	}
	
	public InvManager getInvManager() {
		return this.invManager;
	}

	public void setInvManager(InvManager invManager) {
		this.invManager = invManager;
	}

	/*
	 * (non-Javadoc)
	 * @see com.core.scpwms.server.service.task.WarehouseTaskManager#newWt(com.core.scpwms.server.model.task.WarehouseTask, java.lang.Long, java.lang.Double)
	 */
	@Override
	public List<Long> newWt(WarehouseTask wt, Long invId, Double qty) {
		Inventory inv = commonDao.load(Inventory.class, invId);
		
		if( inv.getContainer() == null || inv.getContainer().getContainerSeq().length() == 0 ){
			throw new BusinessException("暂时不支持非托盘移位的库位推荐。");
		}
		
		List<Long> result = allocateHelper.allocateMoveWt(inv.getWh().getId(), inv.getContainer().getContainerSeq());
		
		if( result == null || result.size() == 0 ){
			throw new BusinessException("找不到推荐库位。");
		}
		
		return result;
	}
	
	private Long createWt( String type, Inventory inv, Double qty ) {
		if( PrecisionUtils.compareByBasePackage(inv.getAvailableQty(), qty, inv.getBasePackage()) < 0){
			throw new BusinessException(ExceptionConstant.CANNOT_MOVE_AS_MORE_THAN_AVAILABLE_QTY);
		}
		
		WarehouseTask wt = new WarehouseTask();
		wt.setWh(inv.getWh());
//		wt.setTaskSequence(bcManager.getTaskSeq(wt.getWh().getCode()));
		wt.setProcessType(type);
		InventoryInfo invInfo = new InventoryInfo();
		try{
			BeanUtils.copyProperties(invInfo, inv.getInventoryInfo());
		}catch( Exception e ){
			throw new BusinessException(ExceptionConstant.SYSTEM_EXCEPTION);
		}
		wt.setInvInfo(invInfo);
		wt.setInvId(inv.getId());
		wt.addPlanQty(qty);
		commonDao.store(wt);
		orderStatusHelper.changeStatus(wt, EnuWarehouseOrderStatus.DRAFT);//新建
		
		return wt.getId();
	}
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.task.WarehouseOrderManager#close(java.
	 * util.List)
	 */
	@Override
	public void close(List<Long> wtIds, String desc) {
		// 加入WO的WT，如果没有完成，可以强制关闭
		for (Long id : wtIds) {
			WarehouseTask wt = commonDao.load(WarehouseTask.class, id);
			if (desc != null) {
				wt.setDescription(wt.getDescription() == null ? desc : wt
						.getDescription() + "," + desc);
			}

			// 计划和执行中状态可以强制关闭
			if (!EnuWarehouseOrderStatus.PLANED.equals(wt.getStatus())
					&& !EnuWarehouseOrderStatus.EXECUTE.equals(wt.getStatus())) {
				throw new BusinessException(ExceptionConstant.ERROR_STATUS);
			}

			// 上架任务
			if (EnuWtProcessType.ASN_PUTAWAY.equals(wt.getProcessType())
					|| EnuWtProcessType.MV_PUTAWAY.equals(wt.getProcessType())
					|| EnuWtProcessType.PK_PUTAWAY.equals(wt.getProcessType())
					|| EnuWtProcessType.PRO_PUTAWAY.equals(wt.getProcessType())
					|| EnuWtProcessType.QC_PUTAWAY.equals(wt.getProcessType())
					|| EnuWtProcessType.RP_PUTAWAY.equals(wt.getProcessType())
					|| EnuWtProcessType.RV_PUTAWAY.equals(wt.getProcessType())) {
				// 强制关闭上架任务
				allocateHelper.closePutaway(wt.getId());
			}
			// 拣货任务
			else {
				allocatePickingHelper.cancelPickTask(wt.getId());
			}

		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.task.WarehouseOrderManager#close(java.
	 * util.List)
	 */
	@Override
	public void close(List<Long> wtIds) {
		close(wtIds, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.task.WarehouseTaskManager#cancel(java.
	 * util.List)
	 */
	@Override
	public void cancel(List<Long> wtIds) {
		// 取消WT，未加入WO状态可以直接取消。如果是已经加入WO的WT要取消，只能去关闭WT
		for (Long id : wtIds) {
			WarehouseTask wt = commonDao.load(WarehouseTask.class, id);
			
			if( wt == null ){
				continue;
			}
			
			// 新建
			if (EnuWarehouseOrderStatus.DRAFT.equals(wt.getStatus())) {
				commonDao.delete(wt);
			}
			
			// 发行
			else if (EnuWarehouseOrderStatus.PUBLISH.equals(wt.getStatus())) {
				// 上架任务
				if (EnuWtProcessType.ASN_PUTAWAY.equals(wt.getProcessType())
						|| EnuWtProcessType.MV_PUTAWAY.equals(wt.getProcessType())
						|| EnuWtProcessType.PK_PUTAWAY.equals(wt.getProcessType())
						|| EnuWtProcessType.PRO_PUTAWAY.equals(wt.getProcessType())
						|| EnuWtProcessType.QC_PUTAWAY.equals(wt.getProcessType())
						|| EnuWtProcessType.RV_PUTAWAY.equals(wt.getProcessType())) {
					// 取消上架任务
					allocateHelper.unAllocateByWtPutaway(wt.getId());
				}
				// 补货上架
				else if(EnuWtProcessType.RP_PUTAWAY.equals(wt.getProcessType())){
					// 取消上架任务
					allocateHelper.unAllocateByWtPutaway(wt.getId());
				}
				// 拣货任务
				else {
					// 取消拣货任务
					allocatePickingHelper.cancelPickTask(wt.getId());
				}
			}
			
			// 非法状态
			else{
				continue;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.task.WarehouseTaskManager#cancelPutaway
	 * (java.util.List)
	 */
	@Override
	public void cancelPutaway(List<Long> wtIds) {
		// TODO
	}

	@Override
	public void cancelPick(List<Long> wtIds) {
		// TODO
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.task.WarehouseTaskManager#batchExecute
	 * (java.util.List, java.lang.Long)
	 */
	@Override
	public void batchExecute(List<Long> wtIds, List<Long> laborIds) {
		for (Long id : wtIds) {
			WarehouseTask wt = commonDao.load(WarehouseTask.class, id);
			executeTask(EXECUTE_BY_PAGE, wt, wt.getInvInfo().getBin(),
					wt.getDescBin(), wt.getUnExecuteQty(), null, laborIds,
					Boolean.TRUE, null);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.task.WarehouseTaskManager#putawayTaskExecute
	 * (com.core.scpwms.server.model.task.WarehouseTask, java.lang.Double,
	 * java.util.List, java.util.List)
	 */
	@Override
	public void putawayTaskExecute(WarehouseTask task, List<Long> labor) {
		WarehouseTask oldTask = commonDao.load(WarehouseTask.class, task.getId());
		
		if( oldTask == null || oldTask.getId() == null ){
			throw new BusinessException("该任务已经取消。");
		}
		if( EnuWarehouseOrderStatus.CLOSE.equals(oldTask.getStatus()) ){
			throw new BusinessException("该任务已经执行完成。");
		}
		
		String containerSeq = oldTask.getInvInfo().getContainerSeq();
		String descContainerSeq = oldTask.getDescContainerSeq();
		
		Bin newToBin = oldTask.getDescBin();
		
		if( task.getDescBin() != null && task.getDescBin().getId() != null ){
			newToBin = commonDao.load(Bin.class, task.getDescBin().getId());// 画面指定的目标库位
		}
		
		if( containerSeq != null && containerSeq.length() > 0 ){
			String hql = " from WarehouseTask wt "
					+ " where 1=1 "
					+ " and wt.wh.id = :whId "
					+ " and wt.processType like '%PUTAWAY' " //上架
					+ " and wt.invInfo.containerSeq = :containerSeq "
					+ " and wt.status in (200,500,600) ";
			List<WarehouseTask> wts = commonDao.findByQuery(hql, new String[]{"whId","containerSeq"}, new Object[]{oldTask.getWh().getId(), containerSeq});
			if( wts != null && wts.size() > 0 ){
				for( WarehouseTask wt : wts ){
					Double movePackQty = wt.getUnExecutePackQty();// 画面输入的包装数
					Double moveQty = PrecisionUtils.getBaseQty(movePackQty, wt.getInvInfo().getPackageDetail());
					// 原库存减，分配数减
					executeTaskRemoveInvFromSrcBin(wt, wt.getInvInfo().getBin(), moveQty, null, labor, Boolean.TRUE, null);
				}
				
				for( WarehouseTask wt : wts ){
					Double movePackQty = wt.getUnExecutePackQty();// 画面输入的包装数
					Double moveQty = PrecisionUtils.getBaseQty(movePackQty, wt .getInvInfo().getPackageDetail());
					// 原目标库位的待上架数减，目标库位上库存增加，更新WT状态。
					executeTaskAddInv2DescBin(wt, wt.getInvInfo().getBin(), newToBin, moveQty, null, labor, Boolean.TRUE, null);
				}
				
				// 取消一个待上架的托盘数
				oldTask.getDescBin().getBinInvInfo().removeQueuedPallet(1D);
			}
			
			// 因为补货产生的tmp容器号，在补货完成以后，修改容器号。关联的WT里的容器号也要一并修改。
			if( descContainerSeq != null && descContainerSeq.length() > 0 && descContainerSeq.startsWith("tmp_") ){
				updateTmpContainerSeq(oldTask.getWh().getId(), containerSeq, descContainerSeq);
			}
		}
		else{
			Double movePackQty = oldTask.getUnExecutePackQty();
			if( task.getUnExecutePackInputQty() != null ){
				task.getUnExecutePackInputQty();// 画面输入的包装数
			}
			Double moveQty = PrecisionUtils.getBaseQty(movePackQty, oldTask .getInvInfo().getPackageDetail());

			executeTask(EXECUTE_BY_PAGE, oldTask, oldTask.getInvInfo().getBin(), newToBin, moveQty, null, labor, Boolean.TRUE, null);
		}
	}
	
	private void updateTmpContainerSeq( Long whId, String containerSeq, String tmpContainerSeq ){
		String oldContainerInvHql = "select inv.id from Inventory inv where inv.wh.id = :whId and inv.container.containerSeq = :containerSeq ";
		List<Long> oldContainerInvIds = commonDao.findByQuery(oldContainerInvHql, new String[]{"whId","containerSeq"}, new Object[]{whId,containerSeq});
		if( oldContainerInvIds == null || oldContainerInvIds.size() == 0 ){
			String newContainerInvHql = " from ContainerInv cInv where cInv.containerSeq = :containerSeq and cInv.bin.wh.id = :whId ";
			List<ContainerInv> cInvs = commonDao.findByQuery(newContainerInvHql, new String[]{"whId","containerSeq"}, new Object[]{whId, tmpContainerSeq});
			if( cInvs != null && cInvs.size() > 0 ){
				ContainerInv newContainerInv = cInvs.get(0);
				newContainerInv.setContainerSeq(containerSeq);
				commonDao.store(newContainerInv);
			}
			
			String newContainerWtHql = " from WarehouseTask wt where wt.wh.id = :whId and wt.invInfo.containerSeq = :containerSeq ";
			List<WarehouseTask> newContainerWts = commonDao.findByQuery(newContainerWtHql, new String[]{"whId","containerSeq"},new Object[]{whId,tmpContainerSeq});
			if( newContainerWts != null && newContainerWts.size() > 0 ){
				for( WarehouseTask wt : newContainerWts ){
					wt.getInvInfo().setContainerSeq(containerSeq);
					commonDao.store(wt);
				}
			}
		}
	}

	@Override
	public void pickTaskExecute(Long taskId, Double pickQty, Long labor) {
		WarehouseTask oldTask = commonDao.load(WarehouseTask.class, taskId);

		Bin newFromBin = oldTask.getInvInfo().getBin();// 拣货库位
		Double moveQty = PrecisionUtils.getBaseQty(pickQty, oldTask.getInvInfo().getPackageDetail());
		
		List<Long> labors = new ArrayList<Long>(1);
		labors.add(labor);
		executeTask(EXECUTE_BY_PAGE, oldTask, newFromBin, oldTask.getDescBin(), moveQty, null, labors, Boolean.TRUE, null);
	}

	@Override
	public void pickTaskExecute(Long taskId, Double pickQty, String descContainerSeq, Long labor) {
		WarehouseTask oldTask = commonDao.load(WarehouseTask.class, taskId);

		Bin newFromBin = oldTask.getInvInfo().getBin();// 拣货库位
		Double moveQty = PrecisionUtils.getBaseQty(pickQty, oldTask.getInvInfo().getPackageDetail());
		
		List<Long> labors = new ArrayList<Long>(1);
		labors.add(labor);
		oldTask.setDescContainerSeq(descContainerSeq);
		executeTask(EXECUTE_BY_PAGE, oldTask, newFromBin, oldTask.getDescBin(), moveQty, null, labors, Boolean.TRUE, null);
	}
	
	@Override
	public void pickTaskCancel(Long taskId, Double pickQty, String descContainerSeq) {
		//
	}
	
	//用原库位减库存
	private void executeTaskRemoveInvFromSrcBin( WarehouseTask wt, Bin fromBin, Double executeQty, 
			List<Long> checkLaborIds, List<Long> laborIds, Boolean freeStatus, Date operDate ) {
		if (executeQty <= 0D)
			return;
		
		// WT作业类型
		String type = wt.getProcessType();

		// 作业人员
		Labor labor = laborIds != null && laborIds.size() > 0 ? commonDao.load( Labor.class, laborIds.get(0)) : null;
		
		if (!EnuWarehouseOrderStatus.PUBLISH.equals(wt.getStatus())
				&& !EnuWarehouseOrderStatus.PLANED.equals(wt.getStatus())
				 && !EnuWarehouseOrderStatus.EXECUTE.equals(wt.getStatus())) {
			throw new BusinessException(ExceptionConstant.ERROR_STATUS);
		}
		
		// 2.原库位，减库存（不减分配数和库容）
		// 2.1.如果计划原库位和实际原库位没有区别
		if (fromBin.getId().equals(wt.getInvInfo().getBin().getId())) {
			Inventory oldSrcInv = invHelper.getInv(wt.getInvInfo());
			
			if( oldSrcInv.getBaseQty() < executeQty ){
				throw new BusinessException("补货中，不能拣货。待补货完成后，再进行拣货。");
			}
			
			// 原库存减
			removeSrcInventory(wt, oldSrcInv, executeQty, checkLaborIds, laborIds, freeStatus, operDate);
		} else {
			// TODO
		}
	}
	
	// 增加库存到目标库位上
	private void executeTaskAddInv2DescBin( WarehouseTask wt, Bin fromBin, Bin toBin, Double executeQty, List<Long> checkLaborIds, List<Long> laborIds, Boolean freeStatus, Date operDate ) {
		if (executeQty <= 0D)
			return;
		
		// WT作业类型
		String type = wt.getProcessType();
		
		// 2.1.如果计划原库位和实际原库位没有区别
		if (fromBin.getId().equals(wt.getInvInfo().getBin().getId())) {
			// 新库存加，登记WH，登记KPI
			addDescInventory(wt, toBin, executeQty, checkLaborIds, laborIds, freeStatus, operDate);
		}
		else{
			// TODO
		}
		
		// 修改WT，WO的执行数量
		wt.execute(executeQty);
		wt.setOperateTime(new Date());

		// 修改WT状态
		if ((EnuWarehouseOrderStatus.PUBLISH.equals(wt.getStatus()) 
				|| EnuWarehouseOrderStatus.PLANED.equals(wt.getStatus())) 
				&& wt.getExecuteQty() > 0) {
			orderStatusHelper.changeStatus(wt, EnuWarehouseOrderStatus.EXECUTE);

		}
		if (EnuWarehouseOrderStatus.EXECUTE.equals(wt.getStatus())
				&& wt.getUnExecuteQty() <= 0) {
			orderStatusHelper.changeStatus(wt, EnuWarehouseOrderStatus.CLOSE);
		}

		// 修改WO的状态
		if (wt.getWo() != null) {
			if (EnuWarehouseOrderStatus.PLANED.equals(wt.getWo().getStatus())
					&& wt.getWo().getExecuteQty() > 0) {
				orderStatusHelper.changeStatus(wt.getWo(), EnuWarehouseOrderStatus.EXECUTE);
			}
			boolean allClosed = true;
			Set<WarehouseTask> tasks = wt.getWo().getTasks();
			for (WarehouseTask task : tasks) {
				if (!EnuWarehouseOrderStatus.CLOSE.equals(task.getStatus())) {
					allClosed = false;
					break;
				}
			}
			if (allClosed) {
				orderStatusHelper.changeStatus(wt.getWo(), EnuWarehouseOrderStatus.CLOSE);
			}
		}

		// 原目标库位上的待上架数要减掉
		if (type.contains("PUTAWAY")) {
			// 减掉原目标库位上占的库容，如果库容为0,最后存放信息清空
			wt.getDescBin().getBinInvInfo().removeQueuedInvInfoNoPallet(wt.getInvInfo(), executeQty);

			// 如果上架的库位和分配的库位不一致
			if (!wt.getDescBin().getId().equals(toBin.getId())) {
				// 如果要需要的批次正好和库位上的最后拣选信息一致，要重置库位上的最后拣选信息
				if (wt.getInvInfo()
						.getQuant()
						.getId()
						.equals(wt.getDescBin().getBinInvInfo().getBinInfo()
								.getLastLotInfo())) {
					// 从WT，WH取得最后拣选的信息，重新设置
					InventoryInfo invInfo = binHelper.getLastPutawayInvInfo(wt.getDescBin());
					if (invInfo != null) {
						wt.getDescBin().getBinInvInfo()
								.refleshLastStoreInfo(invInfo);
					}
				}
			}
		}

		// ---------------上架------------------------------------
		if (type.contains("PUTAWAY")) {
			// 修改上架单，上级单明细的上架数量
			PutawayDocDetail putawayDetail = wt.getPutawayDocDetail();
			if( putawayDetail != null ){
				PutawayDoc putaway = putawayDetail.getPutawayDoc();
				putawayDetail.execute(executeQty);
	
				// 修改上架单状态
				if ((EnuPutawayDocStatus.PLANED.equals(putaway.getStatus()) || EnuPutawayDocStatus.PLANING
						.equals(putaway.getStatus()))
						&& putaway.getExecuteQty() > 0) {
					orderStatusHelper.changeStatus(putaway, EnuPutawayDocStatus.EXECUTEING);
				}
				if (EnuPutawayDocStatus.EXECUTEING.equals(putaway.getStatus())
						&& putaway.getUnAllocateQty() <= 0
						&& putaway.getUnPutawayQty() <= 0) {
					orderStatusHelper.changeStatus(putaway, EnuPutawayDocStatus.CLOSE);
				}
			

				// 修改相关订单，订单明细，订单履历的上架数量
				List<AllocatePutawayDetail> ihs = commonHelper
						.getAllocatePutawayDetail(putawayDetail.getInvInfo(),
								putaway.getDocSequence(), putaway.getOrderType()
										.getCode());
			
				if (ihs != null && ihs.size() > 0) {
					double toAddPutawayQty = executeQty;
	
					for (AllocatePutawayDetail ih : ihs) {
						if (toAddPutawayQty <= 0)
							break;
	
						// 收货履历中能上架的数量
						double canAddPutawayQty = DoubleUtil.sub(
								ih.getPlanPutawayQty(), ih.getPutawayQty());
	
						if (canAddPutawayQty > toAddPutawayQty) {
							canAddPutawayQty = toAddPutawayQty;
						}
	
						toAddPutawayQty -= canAddPutawayQty;
	
						// 修改收货履历，ASN明细，ASN上的上架数量
						ih.addPutawayQty(canAddPutawayQty);
					}
				}
			}
		}

		// --------------质检拣选----------------------------------------
		else if (EnuWtProcessType.QC_REQ.equals(type)) {
			// TODO
			// 华耐是原位质检，暂时没有该拣选类型
		}

		// --------------移位拣选----------------------------------------
		else if (EnuWtProcessType.MV_REQ.equals(type)) {
			// TODO
		}

		// --------------补货拣选----------------------------------------
		else if (EnuWtProcessType.RP_REQ.equals(type)) {
			// TODO
			// 华耐暂时没有该拣选类型
		}

		// --------------发货拣选----------------------------------------
		else if (EnuWtProcessType.PK_REQ.equals(type)) {
			OutboundDeliveryDetail detail = wt.getObdDetail();
			
			// 更新obdDetail, obd, wave的拣货数
			updateObdWvPickQty( detail.getId() );

			// 修改单明细状态
			if (EnuOutboundDeliveryDetailStatus.STATUS310.equals(detail
					.getStatus())
					|| EnuOutboundDeliveryDetailStatus.STATUS400.equals(detail
							.getStatus())) {
				if (detail.getPickUpQty() > 0) {
					detail.setStatus(EnuOutboundDeliveryDetailStatus.STATUS710);// 拣选中
				}
			}
			if (EnuOutboundDeliveryDetailStatus.STATUS710.equals(detail
					.getStatus()) && detail.getUnPickUpQty() <= 0) {
				detail.setStatus(EnuOutboundDeliveryDetailStatus.STATUS800);// 拣选完成
			}
			
			// 修改单头状态
			OutboundDelivery doc = commonDao.load(OutboundDelivery.class, detail.getObd().getId());
			if (EnuOutboundDeliveryStatus.STATUS310.equals(doc.getStatus())
					|| EnuOutboundDeliveryStatus.STATUS400.equals(doc.getStatus())) {
				if (doc.getPickUpQty() > 0) {
					doc.setPickDate(new Date());
					orderStatusHelper.changeStatus(doc, EnuOutboundDeliveryStatus.STATUS710);// 拣选中
				}
			}
			if (EnuOutboundDeliveryStatus.STATUS710.equals(doc.getStatus()) && doc.getUnPickupQty() <= 0) {
				orderStatusHelper.changeStatus(doc, EnuOutboundDeliveryStatus.STATUS800);// 拣选完成
			}
			
			// 修改WV的状态
			WaveDoc waveDoc = commonDao.load(WaveDoc.class, doc.getWaveDoc().getId());
			if (waveDoc != null) {
				// 部分分配
				if (EnuWaveStatus.STATUS210.equals(waveDoc.getStatus()) && waveDoc.getExecuteQty() > 0) {
					orderStatusHelper.changeStatus(waveDoc, EnuWaveStatus.STATUS300);
				}
				
				// 分配完成
				if (EnuWaveStatus.STATUS220.equals(waveDoc.getStatus()) && waveDoc.getExecuteQty() > 0) {
					orderStatusHelper.changeStatus(waveDoc, EnuWaveStatus.STATUS300);
				}
				
				// 作业中
				if (EnuWaveStatus.STATUS250.equals(waveDoc.getStatus()) && waveDoc.getExecuteQty() > 0) {
					orderStatusHelper.changeStatus(waveDoc, EnuWaveStatus.STATUS300);
				}

				// 作业完了
				if (EnuWaveStatus.STATUS300.equals(waveDoc.getStatus()) && waveDoc.getAllocatedUnExecuteQty() <= 0) {
					orderStatusHelper.changeStatus(waveDoc, EnuWaveStatus.STATUS500);
				}
			}
		}
	}
	
	private void updateObdWvPickQty( Long obdDetailId ){
		String hql0 = " select sum(wt.executeQty) from WarehouseTask wt where wt.obdDetail.id = :obdDetailId ";
		String hql = "select sum(obdDetail.pickUpQty) from OutboundDeliveryDetail obdDetail where obdDetail.obd.id = :obdId ";
		String hql2 = " select sum(obdDetail.pickUpQty) from OutboundDeliveryDetail obdDetail where obdDetail.obd.waveDoc.id = :wvId";
		
		Double obdDetailPickQty = (Double)commonDao.findByQueryUniqueResult(hql0, new String[]{"obdDetailId"}, new Object[]{obdDetailId});
		if(obdDetailPickQty == null)
			obdDetailPickQty = 0D;
		
		OutboundDeliveryDetail obdDetail = commonDao.load(OutboundDeliveryDetail.class, obdDetailId);
		obdDetail.setPickUpQty(obdDetailPickQty);
		
		Long obdId = obdDetail.getObd().getId();
		Long wvId = obdDetail.getObd().getWaveDoc().getId();
		
		Double pickQty = (Double)commonDao.findByQueryUniqueResult(hql, new String[]{"obdId"}, new Object[]{obdId});
		if(pickQty == null)
			pickQty = 0D;
		OutboundDelivery obd = commonDao.load(OutboundDelivery.class, obdId);
		obd.setPickUpQty(pickQty);
		commonDao.store(obd);
		
		Double wvPickQty = (Double)commonDao.findByQueryUniqueResult(hql2, new String[]{"wvId"}, new Object[]{wvId});
		if(wvPickQty == null)
			wvPickQty = 0D;
		
		WaveDoc wv = commonDao.load(WaveDoc.class, wvId);
		wv.setExecuteQty(wvPickQty);
		commonDao.store(wv);
	}

	private void executeTask(String updateType, WarehouseTask wt, Bin fromBin,  Bin toBin, Double executeQty, List<Long> checkLaborIds, List<Long> laborIds, Boolean freeStatus, Date operDate) {
		executeTaskRemoveInvFromSrcBin(wt, fromBin, executeQty, checkLaborIds, laborIds, freeStatus, operDate);
		executeTaskAddInv2DescBin(wt, fromBin, toBin, executeQty, checkLaborIds, laborIds, freeStatus, operDate);
	}
	
	private void removeSrcInventory(WarehouseTask wt, Inventory srcInv,
			Double moveQty, List<Long> checkLaborIds, List<Long> laborIds,
			Boolean freeStatus, Date operDate) {
		// 找不到库存
		if (srcInv == null) {
			throw new BusinessException(ExceptionConstant.INVENTORY_NOT_FOUND);
		}
		
		// 减库存，减分配数
		invHelper.removeInv(srcInv, moveQty);

		// 库存履历（-）
		historyHelper.saveInvHistory(srcInv, moveQty * (-1),
				wt.getDocOrderType(), null, EnuInvenHisType.MOVE,
				wt.getDocSequence(), wt.getId(), null, wt.getTaskSequence(),
				wt.getWo() == null ? null : wt.getWo().getOrderSequence());
	}
	
	private void addDescInventory(WarehouseTask wt, Bin toBin,
			Double moveQty, List<Long> checkLaborIds, List<Long> laborIds,
			Boolean freeStatus, Date operDate) {
		// 目标库存（+）
		InventoryInfo descInvInfo = new InventoryInfo();
		try{
			BeanUtils.copyProperties(descInvInfo, wt.getInvInfo());
		}catch( Exception e ){
			throw new BusinessException(ExceptionConstant.SYSTEM_EXCEPTION);
		}
		descInvInfo.setBin(toBin);
		descInvInfo.setPalletSeq(wt.getDescPalletSeq());
		descInvInfo.setContainerSeq(wt.getDescContainerSeq());

		// 如果上架的目标库位和原计划目标库位一直，不做库容校验，不一致情况下需要做库容校验。
		Inventory descInv = invHelper.addInvToBin(descInvInfo, moveQty, toBin
				.getId().equals(wt.getDescBin().getId()) ? false : true);// 增加库存，增加库位的库容
		
		// 如果是补货上架，还需要加分配数
		if( EnuWtProcessType.RP_PUTAWAY.equals(wt.getProcessType()) ){
			invHelper.allocateInv(descInv, moveQty);
		}

		// 库存履历（+）
		historyHelper.saveInvHistory(descInv, moveQty, wt.getDocOrderType(),
				null, EnuInvenHisType.MOVE, wt.getDocSequence(), wt.getId(),
				null, wt.getTaskSequence(), wt.getWo() == null ? null : wt
						.getWo().getOrderSequence());

		// 登记WH，作业履历
		WtHistory wh = historyHelper.saveWtHistory(wt,
				wt.getInvInfo(), descInv.getInventoryInfo(), null,
				moveQty, freeStatus, operDate);

		// 登记作业量
		String invProcessType = EnuInvProcessType.M5000;

		// 上架
		if (EnuWtProcessType.ASN_PUTAWAY.equals(wt.getProcessType())
				|| EnuWtProcessType.QC_PUTAWAY.equals(wt.getProcessType())
				|| EnuWtProcessType.PRO_PUTAWAY.equals(wt.getProcessType())
				|| EnuWtProcessType.RV_PUTAWAY.equals(wt.getProcessType())
				|| EnuWtProcessType.RP_PUTAWAY.equals(wt.getProcessType())
				|| EnuWtProcessType.PK_PUTAWAY.equals(wt.getProcessType())) {
			invProcessType = EnuInvProcessType.M5000;
		}
		// 拣货
		else if (EnuWtProcessType.QC_REQ.equals(wt.getProcessType())
				|| EnuWtProcessType.PRO_REQ.equals(wt.getProcessType())
				|| EnuWtProcessType.MV_REQ.equals(wt.getProcessType())
				|| EnuWtProcessType.RP_REQ.equals(wt.getProcessType())
				|| EnuWtProcessType.PK_REQ.equals(wt.getProcessType())) {
			invProcessType = EnuInvProcessType.M6000;
		}
		// 移位
		else if (EnuWtProcessType.MV_REQ.equals(wt.getProcessType())
				|| EnuWtProcessType.INV_MOVE.equals(wt.getProcessType())) {
			invProcessType = EnuInvProcessType.M1000;
		}

	}

	@Override
	public void cancelPick(Long whId, Double cancelPickQty, Long descBinId, String desc) {
		// 检查OBD是不是已经到了打包完成以后的状态，如果是报错
		WtHistory wh = commonDao.load(WtHistory.class, whId);
		
		OutboundDeliveryDetail obdDetail = wh.getWt().getObdDetail();
		
		if( obdDetail.getStatus().longValue() >= EnuOutboundDeliveryDetailStatus.STATUS850 ){
			throw new BusinessException(ExceptionConstant.ERROR_STATUS);
		}
		
		// 取消拣货数 <= 拣货数-已经打包数，已经打包的不能退拣
		if( cancelPickQty.doubleValue() > obdDetail.getPickedUnPackedQty() ){
			throw new BusinessException("退拣数多于未打包的数量。");
		}
		
		// 检查在集货库位上的库存够不够退拣，不够报错。
		// 用WH里的InvInfo找库存，如果找不到或者不够，用空托盘找，还是找不到或者不够，报错。
		InventoryInfo descInvInfo = new InventoryInfo();
		try{
			BeanUtils.copyProperties(descInvInfo, wh.getInvInfo());
		}catch( Exception e ){
			throw new BusinessException(ExceptionConstant.SYSTEM_EXCEPTION);
		}
		descInvInfo.setBin(wh.getDescBin());
		
		List<Inventory> invs = new ArrayList<Inventory>();
		Inventory inv = invHelper.getInv(descInvInfo);
		Double canCancelQty = 0D;
		if( inv == null ){
			descInvInfo.setContainerSeq(null);
			Inventory invNoContainer = invHelper.getInv(descInvInfo);
			if( invNoContainer != null ){
				invs.add(invNoContainer);
				canCancelQty = canCancelQty + invNoContainer.getAvaliableQuantity();
			}
		}
		else if( inv.getAvailableQty() < cancelPickQty ){
			canCancelQty = canCancelQty + inv.getAvaliableQuantity();
			invs.add(inv);
			descInvInfo.setContainerSeq(null);
			Inventory invNoContainer = invHelper.getInv(descInvInfo);
			if( invNoContainer != null ){
				invs.add(invNoContainer);
				canCancelQty = canCancelQty + invNoContainer.getAvaliableQuantity();
			}
		}
		
		if( invs.size() == 0 ){
			throw new BusinessException("找不到能拣货的库存。");
		}
		if( canCancelQty < cancelPickQty ){
			throw new BusinessException("退拣数多于能退拣的数量。");
		}
		
		// 移动库存，从集货库位->退拣库位
		Bin returnBin = commonDao.load(Bin.class,descBinId);
		
		Double totalMoveQty = cancelPickQty;
		for( Inventory moveInv : invs ){
			if( totalMoveQty <= 0 )
				break;
			
			Double thisMoveQty = totalMoveQty;
			if( moveInv.getAvailableQty() < totalMoveQty ){
				thisMoveQty = moveInv.getAvailableQty();
			}
			
			invManager.invMove(moveInv.getId(), returnBin.getId(), thisMoveQty, null, desc);
			totalMoveQty = totalMoveQty - thisMoveQty;
		}
		
		// 修改WH的执行数
		wh.removeExecuteQty(cancelPickQty);
		wh.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
		commonDao.store(wh);
		
		// 修改WT的计划数，执行数
		WarehouseTask wt = wh.getWt();
		wt.removePlanQty(cancelPickQty);
		wt.unExecute(cancelPickQty);
		wt.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
		commonDao.store(wt);
		
		WarehouseOrder wo = wt.getWo();
		wo.removePlanQty(cancelPickQty);
		wo.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
		commonDao.store(wo);
		
		// 修改OBD，OBDDetail的分配数，拣货数
		obdDetail.removePickUpQty(cancelPickQty);
		obdDetail.removeAllocateQty(cancelPickQty);
		
		// 修改OBD，OBDDetail的状态，如果分配数=封箱数，打包完成
		if( obdDetail.getAllocateUnPickUpQty() == 0 
				&& obdDetail.getPickedUnPackedQty() == 0 ){
			obdDetail.setStatus(EnuOutboundDeliveryDetailStatus.STATUS850);
		}
		commonDao.store(obdDetail);
		
		OutboundDelivery obd = obdDetail.getObd();
		if( obd.getAllocatedUnPickupQty() == 0 
				&& obd.getPickedUnPackedQty() == 0 ){
			orderStatusHelper.changeStatus(obd, EnuOutboundDeliveryDetailStatus.STATUS850);
		}
		commonDao.store(obd);
	}

	@Override
	public void lockPickWt(Long wtId, Long laborId) {
		WarehouseTask wt = commonDao.load(WarehouseTask.class, wtId);
		
		if( !EnuWarehouseOrderStatus.PLANED.equals(wt.getStatus()) 
				&& !EnuWarehouseOrderStatus.PUBLISH.equals(wt.getStatus()) ){
			throw new BusinessException(ExceptionConstant.ERROR_STATUS);
		}
		
		if( wt.getLabor() != null && !wt.getLabor().getId().equals(laborId)){
			throw new BusinessException("该拣货任务已经由" + wt.getLabor().getName() + "占有!");
		}
		
		if( wt.getLabor() == null ){
			Labor labor = commonDao.load(Labor.class, laborId);
			wt.setLabor(labor);
			commonDao.store(wt);
		}
		
		// 状态统一改为已分配
		if( EnuWarehouseOrderStatus.DRAFT.equals(wt.getStatus()) ){
			orderStatusHelper.changeStatus(wt, EnuWarehouseOrderStatus.PLANED);
		}
		
		// 如果对应的WT单，开始拣货时间为空，设置
		if( wt.getOperateStartTime() == null ){
			wt.setOperateStartTime(new Date());
		}
	}

	@Override
	public void unlockPickWt(List<Long> wtIds) {
		for( Long wtId : wtIds ){
			WarehouseTask wt = commonDao.load(WarehouseTask.class, wtId);
			
			if( !EnuWarehouseOrderStatus.PLANED.equals(wt.getStatus()) ){
				throw new BusinessException(ExceptionConstant.ERROR_STATUS);
			}
			
			wt.setLabor(null);
			wt.setOperateStartTime(null);
			commonDao.store(wt);
		}
	}
}
