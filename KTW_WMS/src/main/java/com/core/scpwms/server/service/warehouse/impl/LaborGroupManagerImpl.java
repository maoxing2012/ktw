package com.core.scpwms.server.service.warehouse.impl;

import java.util.List;

import com.core.business.exception.BusinessException;
import com.core.business.model.domain.CreateInfo;
import com.core.business.model.domain.UpdateInfo;
import com.core.business.web.security.UserHolder;
import com.core.db.server.service.pojo.DefaultBaseManager;
import com.core.scpwms.server.constant.ExceptionConstant;
import com.core.scpwms.server.filter.WarehouseHolder;
import com.core.scpwms.server.model.warehouse.LaborGroup;
import com.core.scpwms.server.model.warehouse.LaborGroupLabor;
import com.core.scpwms.server.service.warehouse.LaborGroupManager;

/**
 * 
 * @description   作业组实现类
 * @author         MBP:xiaoyan<br/>
 * @createDate    2014-1-2
 * @version        V1.0<br/>
 */
public class LaborGroupManagerImpl extends DefaultBaseManager implements LaborGroupManager {

    @Override
    public void delete(List<Long> ids) {
        if (!ids.isEmpty()) {
            for (Long id : ids) {
            	LaborGroup lg = commonDao.load(LaborGroup.class, id);
            	
            	//删除和Labor的绑定关系
            	if( lg.getLabors() != null && lg.getLabors().size() > 0 ){
            		for( LaborGroupLabor lgl : lg.getLabors() ){
            			commonDao.delete(lgl);
            		}
            	}
            	
            	// 删除LaborGroup
                this.delete(lg);
            }
        }
    }

    @Override
    public void disable(List<Long> ids) {
        for (Long id : ids) {
            LaborGroup lg = this.commonDao.load(LaborGroup.class, id);
            lg.setDisabled(Boolean.TRUE);
            this.commonDao.store(lg);
        }

    }

    @Override
    public void enable(List<Long> ids) {
        for (Long id : ids) {
            LaborGroup lg = this.commonDao.load(LaborGroup.class, id);
            lg.setDisabled(Boolean.FALSE);
            this.commonDao.store(lg);
        }

    }

    @SuppressWarnings("all")
    @Override
    public void store(LaborGroup laborGroup) {
        if (laborGroup.isNew()) {
            String hsql = "from LaborGroup lg where lg.code=:code and lg.wh=:whId ";
            List<LaborGroup> lgList = this.commonDao.findByQuery(hsql, new String[] { "code", "whId" }, new Object[] {
                    laborGroup.getCode(), WarehouseHolder.getWarehouse() });

            if (!lgList.isEmpty()) {
                throw new BusinessException(ExceptionConstant.DUPLICATE_CODE);
            }
            laborGroup.setWh(WarehouseHolder.getWarehouse());
            laborGroup.setCreateInfo(new CreateInfo(UserHolder.getUser()));
        } else {
            laborGroup.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
        }
        this.commonDao.store(laborGroup);

    }

}
