/**
 * (C)2012 MBP Corporation. All rights reserved.
 * 系统名称 : MBP-WMS
 * 文件名   : InboundDeliveryManager.java
 */

package com.core.scpwms.server.service.edi;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.core.db.server.service.BaseManager;
import com.core.scpwms.server.model.edi.SoapInfo;

/**
 * 
 * <p>接口管理</p>
 *
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *  2014/08/18<br/>
 */
@Transactional(readOnly = false)
public interface EdiManager extends BaseManager {

    @Transactional
    public Long save(SoapInfo wsRequest);
    
    @Transactional
    public void delete(List<Long> ids);
    
    /**
     * 
     * <p>是否是合法的EDI请求</p>
     *
     * @param whCd
     * @param userName
     * @param password
     * @return
     */
    @Transactional
    public String isValidEdiRequest( String whCd, String userName, String password );
}