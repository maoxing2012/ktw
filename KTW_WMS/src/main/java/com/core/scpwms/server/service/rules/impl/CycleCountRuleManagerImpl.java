package com.core.scpwms.server.service.rules.impl;

import java.util.List;

import com.core.business.exception.BusinessException;
import com.core.business.model.domain.CreateInfo;
import com.core.business.model.domain.UpdateInfo;
import com.core.business.web.security.UserHolder;
import com.core.db.server.service.pojo.DefaultBaseManager;
import com.core.scpwms.server.constant.ExceptionConstant;
import com.core.scpwms.server.filter.WarehouseHolder;
import com.core.scpwms.server.model.rules.CycleCountRule;
import com.core.scpwms.server.service.rules.CycleCountRuleManager;

/**
 * 
 * <p>循环盘点策略</p>
 *
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *  2014/08/13<br/>
 */
public class CycleCountRuleManagerImpl extends DefaultBaseManager implements CycleCountRuleManager {

    @Override
    public void delete(List<Long> ids) {
        for (Long id : ids) {
            CycleCountRule rule = commonDao.load(CycleCountRule.class, id);
            commonDao.delete(rule);
        }
    }

    @Override
    public void disable(List<Long> ids) {
        for (Long id : ids) {
            CycleCountRule rule = commonDao.load(CycleCountRule.class, id);
            rule.setDisabled(Boolean.TRUE);
            commonDao.store(rule);
        }
    }

    @Override
    public void enable(List<Long> ids) {
        for (Long id : ids) {
            CycleCountRule rule = commonDao.load(CycleCountRule.class, id);
            rule.setDisabled(Boolean.FALSE);
            commonDao.store(rule);
        }
    }

    @Override
    public void store(CycleCountRule rule) {
        if (rule.getWa() != null && rule.getWa().getId() == null) {
            rule.setWa(null);
        }
        if (rule.getSt() != null && rule.getSt().getId() == null) {
            rule.setSt(null);
        } else {
            rule.setWa(null);
        }
        if (rule.getBg() != null && rule.getBg().getId() == null) {
            rule.setBg(null);
        } else {
            rule.setSt(null);
            rule.setWa(null);
        }
        if (rule.getBin() != null && rule.getBin().getId() == null) {
            rule.setBin(null);
        } else {
            rule.setSt(null);
            rule.setWa(null);
            rule.setBg(null);
        }
        if (rule.getWa() == null && rule.getSt() == null && rule.getBg() == null && rule.getBin() == null) {
            throw new BusinessException(ExceptionConstant.BIN_BG_ST_WA_REQUIRED);
        }

        if (rule.isNew()) {
            rule.setWh(WarehouseHolder.getWarehouse());
            String checkHsql = "select rule.id from CycleCountRule rule where rule.wh.id=:whId and rule.priority=:priority";
            List<Long> rules = (List<Long>) this.commonDao.findByQuery(checkHsql, new String[] { "whId", "priority" },
                    new Object[] { rule.getWh().getId(), rule.getPriority() });
            if (!rules.isEmpty()) {
                throw new BusinessException(ExceptionConstant.DUPLICATE_CODE);
            }
            rule.setCreateInfo(new CreateInfo(UserHolder.getUser()));
        } else {
            rule.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
        }
        commonDao.store(rule);
    }

}
