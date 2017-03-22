package com.core.scpwms.server.service.rules.impl;

import java.util.List;

import com.core.business.exception.BusinessException;
import com.core.business.model.domain.CreateInfo;
import com.core.business.model.domain.UpdateInfo;
import com.core.business.web.security.UserHolder;
import com.core.db.server.service.pojo.DefaultBaseManager;
import com.core.scpwms.server.constant.ExceptionConstant;
import com.core.scpwms.server.filter.WarehouseHolder;
import com.core.scpwms.server.model.rules.WaveRule;
import com.core.scpwms.server.service.rules.WaveRuleManager;

/**
 * 
 * <p>波次策略管理</p>
 *
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *  2014/08/09<br/>
 */
public class WaveRuleManagerImpl extends DefaultBaseManager implements WaveRuleManager {

    @Override
    public void delete(List<Long> ids) {
        if (!ids.isEmpty()) {
            for (Long id : ids) {
                delete(this.commonDao.load(WaveRule.class, id));
            }
        }

    }

    @Override
    public void disable(List<Long> ids) {
        if (!ids.isEmpty()) {
            for (Long id : ids) {
                WaveRule wave = this.commonDao.load(WaveRule.class, id);
                wave.setDisabled(Boolean.TRUE);
                wave.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
                commonDao.store(wave);
            }
        }

    }

    @Override
    public void enable(List<Long> ids) {
        if (!ids.isEmpty()) {
            for (Long id : ids) {
                WaveRule wave = this.commonDao.load(WaveRule.class, id);
                wave.setDisabled(Boolean.FALSE);
                wave.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
                commonDao.store(wave);
            }
        }

    }

    @SuppressWarnings("unchecked")
    @Override
    public void store(WaveRule wave) {
        if (wave.getOrderType() != null && wave.getOrderType().getId() == null) {
            wave.setOrderType(null);
        }

        if (wave.isNew()) {
            wave.setWh(WarehouseHolder.getWarehouse());
            // 判断是否有重复
            String hsql = "from WaveRule wr where wr.waveCode=:code and wr.wh=:wh ";
            List<WaveRule> waveList = this.commonDao.findByQuery(hsql, new String[] { "code", "wh" }, new Object[] {
                    wave.getWaveCode(), wave.getWh() });
            if (!waveList.isEmpty()) {
                throw new BusinessException(ExceptionConstant.DUPLICATE_CODE);
            }
            wave.setCreateInfo(new CreateInfo(UserHolder.getUser()));
        } else {
            wave.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
        }
        commonDao.store(wave);
    }
    
}
