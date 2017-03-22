/**
 * (C)2012 MBP Corporation. All rights reserved.
 * 系统名称 : MBP-WMS
 * 文件名   : WarehouseTaskManager.java
 */

package com.core.scpwms.server.service.task;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.core.db.server.service.BaseManager;
import com.core.scpwms.server.model.task.WarehouseOrder;

/**
 * 
 * <p>
 * 作业单WO管理
 * </p>
 * 
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *         2014/07/18<br/>
 */
@SuppressWarnings("all")
@Transactional(readOnly = false)
public interface WarehouseOrderManager extends BaseManager {

	@Transactional
	public WarehouseOrder createPutawayWo(List<Long> ids);

	@Transactional
	public void addPutawayTask2Wo(List<Long> woId, List<Long> wtIds);

	@Transactional
	public void addPutawayTask2Wo(Long woId, List<Long> wtIds);

	@Transactional
	public WarehouseOrder createPickupWo(List<Long> ids);

	@Transactional
	public void addPickTask2Wo(List<Long> woId, List<Long> wtIds);

	@Transactional
	public void addPickTask2Wo(Long woId, List<Long> wtIds);

	@Transactional
	public void deleteWt(List<Long> wtIds);

	@Transactional
	public void save(WarehouseOrder wo);

	@Transactional
	public void savePickWo(WarehouseOrder wo);

	@Transactional
	public void publish(List<Long> woIds);

	@Transactional
	public void cancel(List<Long> woIds);

	@Transactional
	public void close(List<Long> woIds, String desc);

	/**
	 * 
	 * <p>
	 * 自动创建拣货单
	 * </p>
	 * 
	 * @param waveDocIds
	 * @param laborNumber
	 */
	@Transactional
	public int autoCreatePickWo(List<Long> waveDocIds, Integer laborNumber);

	/**
	 * 
	 * <p>
	 * 取得拣货单的状态
	 * </p>
	 * 
	 * @param whCode
	 * @param warehouseOrderSeq
	 * @param lastUpdateDate
	 * @return
	 */
	@Transactional
	public String getWarehouseOrderStatus(String whCode, String warehouseOrderSeq, Date lastUpdateDate);
	
	/**
	 * 
	 * <p>取得待作业的上架单数</p>
	 *
	 * @param whId
	 * @return
	 */
	@Transactional
	public Long getUnPutawayWoCount(Long whId);
	
	/**
	 * 
	 * <p>取得待作业的拣货单数</p>
	 *
	 * @param whId
	 * @return
	 */
	@Transactional
	public Long getUnPickupWoCount(Long whId);
	
	/**
	 * 
	 * <p>关闭打印</p>
	 *
	 * @param woIds
	 */
	@Transactional
	public void closePrint(List<Long> woIds);
	
	/**
	 * 
	 * <p>开启打印</p>
	 *
	 * @param woIds
	 */
	@Transactional
	public void openPrint(List<Long> woIds);
	
	/**
	 * 
	 * <p>记录整个拣货单最后的完成时间</p>
	 *
	 * @param woId
	 */
	@Transactional
	public void recordCompleteTime( Long woId );
	
    
    /**
     * 
     * <p>以OBD为单位，将没有加入拣货单的拣货任务合成一张拣货单，作业方式是手持PDA</p>
     *
     * @param obdIds
     */
    @Transactional
    public void createPDAWo( List<Long> obdIds );
    
    /**
     * 
     * <p>最多5张OBD，合并成一张拣货单，作业方式是拣货小车</p>
     *
     * @param obdIds
     */
    @Transactional
    public void createPickCarWo( List<Long> obdIds );
}
