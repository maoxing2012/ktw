/**
 * (C)2012 MBP Corporation. All rights reserved.
 * 系统名称 : MBP-WMS
 * 文件名   : CarrierManager.java
 */

package com.core.scpwms.server.service.user;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.core.db.server.service.BaseManager;
import com.core.scpwms.server.model.user.Carrier;

/**
 * @description   承运商设定
 * @author         MBP:毛幸<br/>
 * @createDate    2013-2-5
 * @version        V1.0<br/>
 */
@Transactional(readOnly = false)
public interface CarrierManager extends BaseManager {

    /**
     * 
     * <p>保存承运商信息</p>
     *
     * @param carrier
     */
	@Transactional
    void save(Carrier carrier);

    /**
     * 
     * <p>删除承运商信息</p>
     *
     * @param ids
     */
	@Transactional
    void delete(List<Long> ids);

    /**
     * 
     * <p>生效承运商信息</p>
     *
     * @param ids
     */
	@Transactional
    void enable(List<Long> ids);

    /**
     * 
     * <p>失效承运商信息</p>
     *
     * @param ids
     */
	@Transactional
    void disable(List<Long> ids);
}
