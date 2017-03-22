/**
 * (C)2012 MBP Corporation. All rights reserved.
 * 系统名称 : MBP-WMS
 * 文件名   : MoveDocManager.java
 */

package com.core.scpwms.server.service.change;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.core.db.server.service.BaseManager;
import com.core.scpwms.server.model.change.OwnerChangeDoc;
import com.core.scpwms.server.model.change.OwnerChangeDocDetail;
import com.core.scpwms.server.model.move.MoveDoc;
import com.core.scpwms.server.model.move.MoveDocDetail;

/**
 * 
 * <p>货主转换管理</p>
 *
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *  2014/07/22<br/>
 */
@SuppressWarnings("all")
@Transactional(readOnly = false)
public interface OwnerChangeDocManager extends BaseManager {

    @Transactional
    public void save(OwnerChangeDoc doc);

    @Transactional
    public void saveDetail(Long docId, OwnerChangeDocDetail detail, String[] lotData);

    @Transactional
    public void deleteDetail(List<Long> detailIds);
    
    @Transactional
    public void publish(List<Long> docIds);

    @Transactional
    public void cancel(List<Long> docIds);

    @Transactional
    public void docExecute(List<Long> docIds);

    @Transactional
    public void batchExecute(List<Long> detailIds);

    @Transactional
    public void execute(OwnerChangeDocDetail detail, double changeQty);
    
    @Transactional
    public void allocateExecute(List<Long> docIds);

    @Transactional
    public void close(List<Long> docIds);
}
