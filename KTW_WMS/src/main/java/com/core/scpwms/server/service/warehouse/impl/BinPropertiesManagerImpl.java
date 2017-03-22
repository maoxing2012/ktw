/**
 * (C)2012 MBP Corporation. All rights reserved.
 * 系统名称 : MBP-WMS
 * 文件名   : BinPropertiesManagerImpl.java
 */

package com.core.scpwms.server.service.warehouse.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.core.business.exception.BusinessException;
import com.core.business.model.domain.CreateInfo;
import com.core.business.model.domain.UpdateInfo;
import com.core.business.web.security.UserHolder;
import com.core.db.server.service.pojo.DefaultBaseManager;
import com.core.scpwms.server.constant.ExceptionConstant;
import com.core.scpwms.server.filter.WarehouseHolder;
import com.core.scpwms.server.model.common.Sku;
import com.core.scpwms.server.model.warehouse.Bin;
import com.core.scpwms.server.model.warehouse.BinProperties;
import com.core.scpwms.server.service.common.BinHelper;
import com.core.scpwms.server.service.warehouse.BinPropertiesManager;

/**
 * @description   库位属性设定
 * @author         MBP:毛幸<br/>
 * @createDate    2013/02/18
 * @version        V1.0<br/>
 */
public class BinPropertiesManagerImpl extends DefaultBaseManager implements BinPropertiesManager {
	private BinHelper binHelper;
	
    public BinHelper getBinHelper() {
		return this.binHelper;
	}

	public void setBinHelper(BinHelper binHelper) {
		this.binHelper = binHelper;
	}

	/*
     * (non-Javadoc)
     * 
     * @see com.core.scpwms.server.service.warehouse.BinPropertiesManager#deleteBinProperties(java.util.List)
     */
    @Override
    public void deleteBinProperties(List<Long> binPropertiesIds) {
        for (Long id : binPropertiesIds) {
        	// 是否有库位用这个库位属性
        	String hql = "select bin.id from Bin bin where bin.properties.id = :binProId";
        	List<Long> binIds = commonDao.findByQuery(hql, "binProId", id);
        	
        	if( binIds != null && binIds.size() > 0 ){
        		throw new BusinessException(ExceptionConstant.CANNOT_DELETE_AS_HAS_HISTORY);
        	}
        	
            delete(load(BinProperties.class, id));
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.core.scpwms.server.service.warehouse.BinPropertiesManager#saveBinProperties(com.core.scpwms.server.model.warehouse.BinProperties)
     */
    @SuppressWarnings("unchecked")
    @Override
    public void saveBinProperties(BinProperties binProperties) {
        binProperties.setWh(WarehouseHolder.getWarehouse());
        if (binProperties.isNew()) {

            // 判断是否重复记录
            StringBuffer hql = new StringBuffer("from BinProperties bp where lower(bp.code) = :bpCode and bp.wh=:whId");

            List<BinProperties> bps = commonDao.findByQuery(hql.toString(), new String[] { "bpCode", "whId" },
                    new Object[] { binProperties.getCode().toLowerCase(), WarehouseHolder.getWarehouse() });

            if (!bps.isEmpty()) {
                throw new BusinessException(ExceptionConstant.DUPLICATE_CODE);
            }
            binProperties.setCreateInfo(new CreateInfo(UserHolder.getUser()));
            commonDao.store(binProperties);
        } else {
            binProperties.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
            commonDao.store(binProperties);

            // 找到关联的库位，从新刷一下库容的使用率
            String hql = "select bin.id FROM Bin bin WHERE bin.properties.id = :binProId ";
            List<Long> bins = commonDao.findByQuery(hql, "binProId", binProperties.getId());
            if (bins != null && bins.size() > 0) {
                binHelper.refreshBinInvInfo(bins);
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.core.scpwms.server.service.warehouse.BinPropertiesManager#enableBinProperties(java.util.List)
     */
    @Transactional
    public void enableBinProperties(List<Long> binProList) {
        for (Long id : binProList) {
            BinProperties bp = this.commonDao.load(BinProperties.class, id);
            bp.setDisabled(Boolean.FALSE);
            bp.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
            commonDao.store(bp);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.core.scpwms.server.service.warehouse.BinPropertiesManager#disableBinProperties(java.util.List)
     */
    @Transactional
    public void disableBinProperties(List<Long> binProList) {
        for (Long id : binProList) {
            BinProperties bp = this.commonDao.load(BinProperties.class, id);
            bp.setDisabled(Boolean.TRUE);
            bp.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
            commonDao.store(bp);
        }
    }
}
