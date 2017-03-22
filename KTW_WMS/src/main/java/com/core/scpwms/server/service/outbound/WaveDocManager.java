package com.core.scpwms.server.service.outbound;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.core.db.server.service.BaseManager;
import com.core.scpwms.server.model.outbound.WaveDoc;

/**
 * 
 * <p>波次管理</p>
 *
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *  2014/08/03<br/>
 */
@Transactional(readOnly = false)
public interface WaveDocManager extends BaseManager {

    @Transactional
    public void save(WaveDoc waveDoc);

    @Transactional
    public void addDetail(Long waveDocId, List<Long> obdIds);

    @Transactional
    public void deleteDetail(List<Long> obdIds);

    @Transactional
    public void publish(List<Long> waveDocIds);

    @Transactional
    public void cancel(List<Long> waveDocIds);

    @Transactional
    public void close(List<Long> waveDocIds);

    @Transactional
    public void autoCreateWave(Long ownerId, Date etd, Long orderType, Long carrierId, String desc);

}
