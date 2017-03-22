/**
 * (C)2012 MBP Corporation. All rights reserved.
 * 系统名称 : MBP-WMS
 * 文件名   : ScrapApplyManager.java
 */

package com.core.scpwms.server.service.apply;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.core.db.server.service.BaseManager;
import com.core.scpwms.server.model.apply.ScrapApply;

/**
 * 
 * <p>
 * 破损申请单处理
 * </p>
 * 
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *         2014/07/22<br/>
 */
@Transactional(readOnly = false)
public interface ScrapApplyManager extends BaseManager {
	
	@Transactional
	public void save(ScrapApply scrapApply);

	@Transactional
	public void publish(List<Long> scrapApplyIds);

	@Transactional
	public void cancel(List<Long> scrapApplyIds);

	@Transactional
	public void execute(List<Long> scrapApplyIds) ;

	@Transactional
	public void close(List<Long> scrapApplyIds);
	
	@Transactional
	public void addDetail(Long scrapApplyId, List<Long> invIds);

	@Transactional
	public void deleteDetail(List<Long> scrapApplyDetailIds);

}
