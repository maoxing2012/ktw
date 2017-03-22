package com.core.scpwms.server.service.common;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.core.db.server.service.BaseManager;
import com.core.scpwms.server.domain.AllocateDocDetail;
import com.core.scpwms.server.model.inventory.Inventory;
import com.core.scpwms.server.model.task.WarehouseTask;

/**
 * 库存分配与取消
 * 
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 * 
 */
@Transactional(readOnly = false)
public interface AllocatePickingHelper extends BaseManager {

	/**
	 * 发货单库存分配
	 * 
	 * @param ids
	 * @return
	 */
	@Transactional
	public List<String[]> allocateObdDoc(List<Long> ids);

	/**
	 * 发货单库存取消分配
	 * 
	 * @param ids
	 */
	@Transactional
	public void unAllocateObdDoc(List<Long> ids);

	/**
	 * 
	 * <p>
	 * 波次单库存分配
	 * </p>
	 * 
	 * @param ids
	 * @return
	 */
	@Transactional
	public List<String[]> allocateWaveDoc(List<Long> ids);

	/**
	 * 
	 * <p>库存验证</p>
	 *
	 * @param obdId
	 * @return
	 */
	@Transactional
	public List<String[]> checkInventory(Long obdId);

	/**
	 * 
	 * <p>
	 * 波次单库存取消分配
	 * </p>
	 * 
	 * @param ids
	 */
	@Transactional
	public void unAllocateWaveDoc(List<Long> ids);

	/**
	 * 加工单库存取消分配
	 * 
	 * @param ids
	 */
	@Transactional
	public void unAllocateProDoc(List<Long> ids);

	/**
	 * 补货单库存取消分配
	 * 
	 * @param ids
	 */
	@Transactional
	public void unAllocateInvApplyDoc(List<Long> ids);

	/**
	 * 
	 * <p>
	 * 货主转换单库存分配
	 * </p>
	 * 
	 * @param ids
	 * @param whId
	 * @return
	 */
	@Transactional
	public List<String[]> allocateOwnerDoc(List<Long> ids, Long whId);
	
	/**
	 * 
	 * <p>批次调整单库存分配</p>
	 *
	 * @param detailId
	 * @return
	 */
	@Transactional
	public List<String[]> allocateLotChangeDocDetail( Long detailId, Long whId );

	/**
	 * 
	 * <p>
	 * 货主转换单库存分配
	 * </p>
	 * 
	 * @param ids
	 * @param whId
	 * @return
	 */
	@Transactional
	public List<String[]> allocateOwnerDoc(List<Long> ids);

	/**
	 * 
	 * <p>
	 * 货主转换单库存取消分配
	 * </p>
	 * 
	 * @param ids
	 * 
	 */
	@Transactional
	public void unAllocateOwnerDoc(List<Long> ids);

	/**
	 * 
	 * <p>
	 * 对于尚未加入WO的拣货任务，可以取消
	 * </p>
	 * 
	 * @param taskId
	 */
	@Transactional
	public void cancelPickTask(Long taskId);

	/**
	 * 
	 * <p>
	 * 对于已经加入WO的拣货任务，可以强制关闭
	 * </p>
	 * 
	 * @param taskId
	 */
	@Transactional
	public void closePickTask(Long taskId);

	/**
	 * 
	 * <p>
	 * 库存分配修改单据状态
	 * </p>
	 * 
	 * @param detail
	 * @param allocateQty
	 */
	@Transactional
	public void allocateDetail(AllocateDocDetail detail, double allocateQty);

	/**
	 * 
	 * <p>
	 * 库存分配创建WT
	 * </p>
	 * 
	 * @param detail
	 * @param inventory
	 * @param quantity
	 */
	@Transactional
	public void createWarehouseTask(AllocateDocDetail detail, Inventory inventory, double quantity);
}
