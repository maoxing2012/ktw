/**
o * (C)2012 MBP Corporation. All rights reserved.
 * 系统名称 : MBP-WMS
 * 文件名   : WarehouseTaskManager.java
 */

package com.core.scpwms.server.service.task;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.core.db.server.service.BaseManager;

/**
 * 
 * <p>作业WT管理</p>
 *
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *  2014/07/18<br/>
 */
@Transactional(readOnly = false)
public interface WarehouseTaskSingleManager extends BaseManager {
	/**
	 * 個口決定
	 * @param waveDocId
	 */
    @Transactional
    public void createWarehouseTaskSingle( List<Long> waveDocId );
    
    /**
     * 個口取り消し
     * @param waveDocId
     */
    @Transactional
    public void cancelWavehouseTaskSingle( List<Long> waveDocId );
    
    /**
     * 批量拣货
     * @param wtsIds
     * @param laborId
     */
    @Transactional
    public void pickExecute( List<Long> wtsIds, Long laborId );
    
    /**
     * 批量复核
     * @param wtsIds
     * @param laborId
     */
    @Transactional
    public void checkExecute( List<Long> wtsIds, Long laborId );
    
    /**
     * ヤマトのCSV
     * @param wtsIds
     * @param laborId
     */
    @Transactional
    public Long createYamatoCsv(List<Long> ids );
    
    /**
     * ヤマトクールのCSV
     * @param wtsIds
     * @param laborId
     */
    @Transactional
    public Long createYamatoCoolCsv(List<Long> ids );
    
    /**
     * 福山のCSV
     * @param wtsIds
     * @param laborId
     */
    @Transactional
    public Long createFukuyamaCsv(List<Long> ids );
    
    /**
     * 取消检品
     * @param wtsIds
     */
    @Transactional
    public void cancelCheck(List<Long> wtsIds);
    
    /**
     * 取消拣货
     * @param wtsIds
     */
    @Transactional
    public void cancelPick(List<Long> wtsIds);
}
