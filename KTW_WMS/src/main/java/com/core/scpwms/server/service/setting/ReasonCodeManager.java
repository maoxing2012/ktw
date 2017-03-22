package com.core.scpwms.server.service.setting;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.core.db.server.service.BaseManager;
import com.core.scpwms.server.model.common.ReasonCode;

/**
 * 
 * @description 原因码设定
 * @author      曹国良@mbp
 * @createDate  2014-8-7
 * @version     V1.0
 *
 */
@Transactional(readOnly = false)
public interface ReasonCodeManager extends BaseManager {

	/**
     * 
     * <p>保存原因码信息</p>
     *
     * @param reasonCode
     */
    @Transactional
    void saveReasonCode(ReasonCode reasonCode);
    
    /**
     * 
     * <p>删除原因码信息</p>
     *
     * @param ids
     */
    @Transactional
    void deleteReasonCode(List<Long> ids);
    
    /**
     * 
     * <p>生效原因码信息</p>
     *
     * @param ids
     */
    @Transactional
    void enableReasonCode(List<Long> ids);
    
    /**
     * 
     * <p>失效原因码信息</p>
     *
     * @param ids
     */
    @Transactional
    void disableReasonCode(List<Long> ids);
    
    /**
     * 
     * <p>将原因码拼成长串</p>
     *
     * @return
     */
    @Transactional
    String getReasonCodeStr();
    
    /**
     * 
     * <p>将原因码拼成长串</p>
     *
     * @param type
     * @return
     */
    @Transactional
    String getReasonCodeStr( String type );
}
