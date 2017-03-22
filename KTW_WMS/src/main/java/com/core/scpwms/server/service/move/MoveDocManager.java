/**
 * (C)2012 MBP Corporation. All rights reserved.
 * 系统名称 : MBP-WMS
 * 文件名   : MoveDocManager.java
 */

package com.core.scpwms.server.service.move;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.core.db.server.service.BaseManager;
import com.core.scpwms.server.model.move.MoveDoc;
import com.core.scpwms.server.model.move.MoveDocDetail;

/**
 * 
 * <p>移位管理</p>
 *
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *  2014/07/22<br/>
 */
@SuppressWarnings("all")
@Transactional(readOnly = false)
public interface MoveDocManager extends BaseManager {

    @Transactional
    public void save(MoveDoc moveDoc);
    
    @Transactional
    public void newAuto(MoveDoc moveDoc);

    @Transactional
    public void addDetail(Long moveDocId, List<Long> invIds);

    @Transactional
    public void deleteDetail(List<Long> moveDocDetailIds);

    @Transactional
    public void editDetail(MoveDocDetail moveDocDetail, double movePackQty);

    @Transactional
    public void cancel(List<Long> moveDocIds);

    @Transactional
    public void close(List<Long> moveDocIds);
}
