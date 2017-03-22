package com.core.scpwms.server.service.common;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.core.db.server.service.BaseManager;
import com.core.scpwms.server.model.base.InfoMessage;

/**
 * 
 * <p>
 * 系统消息
 * </p>
 * 
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *         2014年10月15日<br/>
 */
@Transactional(readOnly = false)
public interface InfoMessageManager extends BaseManager {
	@Transactional
	public void save(InfoMessage info);

	@Transactional
	public void delete(List<Long> infoIds);

	@Transactional
	public void top(List<Long> infoIds);

	@Transactional
	public void unTop(List<Long> infoIds);
	
	public void addMessage( String title, String content, String type, Long whId );

}
