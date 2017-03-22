/**
o * (C)2012 MBP Corporation. All rights reserved.
 * 系统名称 : MBP-WMS
 * 文件名   : WarehouseTaskManager.java
 */

package com.core.scpwms.server.service.task;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.core.db.server.service.BaseManager;
import com.core.scpwms.server.model.task.WarehouseTask;

/**
 * 
 * <p>作业WT管理</p>
 *
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *  2014/07/18<br/>
 */
@Transactional(readOnly = false)
public interface WarehouseTaskManager extends BaseManager {
	/**
	 * 
	 * <p>新建上架作业任务</p>
	 *
	 * @param wt
	 * @param invId
	 * @param qty
	 */
	@Transactional
	public List<Long> newWt(WarehouseTask wt, Long invId, Double qty);

    /**
     * 
     * <p>批量执行</p>
     *
     * @param wtIds
     * @param checkLaborIds
     * @param laborIds
     * 
     * @throws Exception
     */
    @Transactional
    public void batchExecute(List<Long> wtIds, List<Long> laborIds);

    /**
     * 
     * <p>不修改上架执行</p>
     *
     * @param task
     * @param packageDetailId
     * @param checkLabor
     * @param labor
     * 
     * @throws Exception
     */
    @Transactional
    public void putawayTaskExecute(WarehouseTask task, List<Long> labor);

    /**
     * 
     * <p>拣货执行</p>
     *
     * @param task
     * @param pickPackQty
     * @param checkLabor
     * @param labor
     * 
     * @throws Exception
     */
    @Transactional
    public void pickTaskExecute(Long taskId, Double pickQty, Long labor);
    
    /**
     * 
     * <p>拣货执行</p>
     *
     * @param task
     * @param pickPackQty
     * @param checkLabor
     * @param labor
     * 
     * @throws Exception
     */
    @Transactional
    public void pickTaskExecute(Long taskId, Double pickQty, String descContainerSeq, Long labor);
    
    /**
     * 
     * <p>取消拣货</p>
     *
     * @param task
     * @param pickPackQty
     * @param checkLabor
     * @param labor
     * 
     * @throws Exception
     */
    @Transactional
    public void pickTaskCancel(Long taskId, Double pickQty, String descContainerSeq);
    
    /**
     * 
     * <p>未完成上架的WT，强制关闭（加入WO状态）</p>
     *
     * @param wtIds
     */
    @Transactional
    public void close(List<Long> wtIds);
    
    /**
     * 
     * <p>未完成上架的WT，强制关闭（加入WO状态）</p>
     *
     * @param wtIds
     * @param desc
     */
    @Transactional
    public void close(List<Long> wtIds, String desc);
    
    /**
     * 
     * <p>取消WT（未加入WO状态）</p>
     *
     * @param wtIds
     */
    @Transactional
    public void cancel(List<Long> wtIds);

    /**
     * 
     * <p>取消已经完成上架作业（暂不提供该方法）</p>
     *
     * @param wtHistoryIds
     */
    @Transactional
    public void cancelPutaway(List<Long> wtIds);

    /**
     * 
     * <p>取消已经完成拣货作业（暂不提供该方法）</p>
     *
     * @param wtHistoryIds
     */
    @Transactional
    public void cancelPick(List<Long> wtIds);
    
    /**
     * 
     * <p>取消已经完成拣货作业</p>
     *
     * @param whId
     * @param cancelPickQty
     * @param descBinId
     * @param desc
     */
    @Transactional
    public void cancelPick( Long whId, Double cancelPickQty, Long descBinId, String desc );
    
    /**
     * 
     * <p>占任务</p>
     *
     * @param wtId
     * @param laborId
     */
    @Transactional
    public void lockPickWt( Long wtId, Long laborId );
    
    /**
     * 
     * <p>取消占任务</p>
     *
     * @param wtIds
     */
    @Transactional
    public void unlockPickWt( List<Long> wtIds );
}
