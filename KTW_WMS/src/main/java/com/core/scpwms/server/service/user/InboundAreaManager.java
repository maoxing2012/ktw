/**
 * (C)2012 MBP Corporation. All rights reserved.
 * 系统名称 : MBP-WMS
 * 文件名   : CarrierManager.java
 */

package com.core.scpwms.server.service.user;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.core.db.server.service.BaseManager;
import com.core.scpwms.server.model.user.InboundArea;

/**
 * @description   收货区域维护
 * @author         MBP:Henry Lin<br/>
 * @createDate    2014-1-3
 * @version        V1.0<br/>
 */
@Transactional(readOnly = false)
public interface InboundAreaManager extends BaseManager {

	@Transactional
	void saveInboundArea(InboundArea  inboundArea); 
	@Transactional
	void enableInboundArea(List<Long> ids);
	@Transactional
	void disabledInboundArea(List<Long> ids);
	@Transactional
	void deleteInboundArea(List<Long> ids);  
}
