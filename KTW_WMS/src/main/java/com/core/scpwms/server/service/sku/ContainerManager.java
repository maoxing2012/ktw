/**
 * (C)2012 MBP Corporation. All rights reserved.
 * 系统名称 : MBP-WMS
 * 文件名   : ContainerManager.java
 */

package com.core.scpwms.server.service.sku;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.core.db.server.service.BaseManager;
import com.core.scpwms.server.model.common.Container;

/**
 * @description   包材管理
 * @author         MBP:xiaoyan<br/>
 * @createDate    2013-2-21
 * @version        V1.0<br/>
 */
public interface ContainerManager extends BaseManager {
	
	/**
	 * 
	 * <p>保存包材信息</p>
	 *
	 * @param cantainer
	 */
	@Transactional
	void saveContainer(Container cantainer);
	
	/**
	 * 
	 * <p>删除包材信息</p>
	 *
	 * @param ids
	 */
	@Transactional
	void deleteContainer(List<Long> ids);
	
	/**
	 * 
	 * <p>生效包材信息</p>
	 *
	 * @param ids
	 */
	@Transactional
	void enableContainer(List<Long> ids);
	
	/**
	 * 
	 * <p>失效包材信息</p>
	 *
	 * @param ids
	 */
	@Transactional
	void disableContainer(List<Long> ids);
	

}
