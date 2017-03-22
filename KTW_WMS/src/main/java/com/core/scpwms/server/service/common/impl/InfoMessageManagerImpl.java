package com.core.scpwms.server.service.common.impl;

import java.util.List;

import com.core.business.model.domain.CreateInfo;
import com.core.business.model.domain.UpdateInfo;
import com.core.business.web.security.UserHolder;
import com.core.db.server.service.pojo.DefaultBaseManager;
import com.core.scpwms.server.model.base.InfoMessage;
import com.core.scpwms.server.model.warehouse.Warehouse;
import com.core.scpwms.server.service.common.InfoMessageManager;

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
public class InfoMessageManagerImpl extends DefaultBaseManager implements
		InfoMessageManager {

	@Override
	public void save(InfoMessage info) {
		if( info.getWh() !=null && info.getWh().getId() == null ){
			info.setWh(null);
		}
		
		if (info.isNew()) {
			info.setCreateInfo(new CreateInfo(UserHolder.getUser()));
		}
		info.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
		commonDao.store(info);
	}

	@Override
	public void delete(List<Long> infoIds) {
		for (Long id : infoIds) {
			InfoMessage info = commonDao.load(InfoMessage.class, id);
			commonDao.delete(info);
		}
	}

	@Override
	public void top(List<Long> infoIds) {
		for (Long id : infoIds) {
			InfoMessage info = commonDao.load(InfoMessage.class, id);
			info.setIsTop(Boolean.TRUE);
			commonDao.store(info);
		}
	}

	@Override
	public void unTop(List<Long> infoIds) {
		for (Long id : infoIds) {
			InfoMessage info = commonDao.load(InfoMessage.class, id);
			info.setIsTop(Boolean.FALSE);
			commonDao.store(info);
		}
	}

	@Override
	public void addMessage(String title, String content, String type, Long whId) {
		InfoMessage info = new InfoMessage();
		info.setInfoType(type);
		info.setTitle(title);
		info.setContent(content);
		info.setWh( whId == null ? null : commonDao.load(Warehouse.class, whId) );
		info.setCreateInfo(new CreateInfo(UserHolder.getUser()));
		commonDao.store(info);
	}
}
