package com.core.scpwms.server.service.rules.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ognl.Ognl;
import ognl.OgnlException;

import org.apache.commons.lang.StringUtils;

import com.core.business.exception.BusinessException;
import com.core.business.model.domain.CreateInfo;
import com.core.business.model.domain.UpdateInfo;
import com.core.business.web.security.UserHolder;
import com.core.db.server.service.pojo.DefaultBaseManager;
import com.core.scpwms.server.filter.WarehouseHolder;
import com.core.scpwms.server.model.common.AbcProperties;
import com.core.scpwms.server.model.common.OrderType;
import com.core.scpwms.server.model.common.PackageDetail;
import com.core.scpwms.server.model.common.PackageInfo;
import com.core.scpwms.server.model.common.Sku;
import com.core.scpwms.server.model.common.SkuType;
import com.core.scpwms.server.model.rules.PutAwayRule;
import com.core.scpwms.server.model.warehouse.Bin;
import com.core.scpwms.server.model.warehouse.BinGroup;
import com.core.scpwms.server.service.rules.PutAwayRuleManager;

@SuppressWarnings("all")
public class PutAwayRuleManagerImpl extends DefaultBaseManager implements PutAwayRuleManager {

    @Override
    public void activePutAwayRuleInfo(List<Long> ids) {
        for (Long id : ids) {
            PutAwayRule rule = this.commonDao.load(PutAwayRule.class, id);
            rule.setDisabled(false);
            commonDao.store(rule);
        }
    }

    /**
     * 验证并保存上架规则
     */
    @Override
    public void savePutAwayRuleInfo(PutAwayRule rule) {
        // 验证规则录入正确
        validatePutAwayRuleInfo(rule);
        if (rule.isNew()) {
            rule.setCreateInfo(new CreateInfo(UserHolder.getUser()));
            rule.setWh(WarehouseHolder.getWarehouse());
        } else {
            rule.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
        }

        // 更新捆绑的订单
        // TODO

        this.commonDao.store(rule);
    }

    /**
     * 验证规则录入正确
     * @param rule
     */

    public void validatePutAwayRuleInfo(PutAwayRule rule) {
        // 验证当前仓库 + 货主 + 优先级是否重复
        String checkHsql = "";
        List<Long> existIds = new ArrayList<Long>();
        if (rule.isNew()) {
            checkHsql = "select rule.id from PutAwayRule rule where rule.wh.id=:whId and rule.plant.id=:plantId and rule.priority=:priority";
            existIds = (List<Long>) this.commonDao.findByQuery(checkHsql,
                    new String[] { "whId", "plantId", "priority" }, new Object[] {
                            WarehouseHolder.getWarehouse().getId(), rule.getPlant().getId(), rule.getPriority() });
        } else {
            checkHsql = "select rule.id from PutAwayRule rule where rule.wh.id=:whId and rule.plant.id=:plantId and rule.priority=:priority and rule.id <> :existId";
            existIds = (List<Long>) this.commonDao.findByQuery(checkHsql, new String[] { "whId", "plantId", "priority",
                    "existId" }, new Object[] { WarehouseHolder.getWarehouse().getId(), rule.getPlant().getId(),
                    rule.getPriority(), rule.getId() });
        }
        if (existIds != null && !existIds.isEmpty()) {
            throw new BusinessException("putAwayRule.error.priority.isExist");
        }
        // 目标库位组和目标库位至少输入一项
        boolean descBinGroupEmpty = false;
        boolean descBinEmpty = false;
        if (rule.getDescBin() == null || rule.getDescBin().getId() == null) {
            descBinEmpty = true;
        }
        if (rule.getDescBinGroup() == null || rule.getDescBinGroup().getId() == null) {
            descBinGroupEmpty = true;
        }
        if (descBinGroupEmpty && descBinEmpty) {
            throw new BusinessException("putAwayRule.error.des.isnull");
        }
        // 库位限定至少录入一项，并不重复
        Set<String> binSels = new HashSet<String>();
        List<String> listSels = new ArrayList<String>();
        if (!StringUtils.isEmpty(rule.getBinSelPriority1())) {
            binSels.add(rule.getBinSelPriority1());
            listSels.add(rule.getBinSelPriority1());
        }
        if (!StringUtils.isEmpty(rule.getBinSelPriority2())) {
            binSels.add(rule.getBinSelPriority2());
            listSels.add(rule.getBinSelPriority2());
        }
        if (!StringUtils.isEmpty(rule.getBinSelPriority3())) {
            binSels.add(rule.getBinSelPriority3());
            listSels.add(rule.getBinSelPriority3());
        }
        if (!StringUtils.isEmpty(rule.getBinSelPriority4())) {
            binSels.add(rule.getBinSelPriority4());
            listSels.add(rule.getBinSelPriority4());
        }
        // 没有录入库位选择顺序
        if ((rule.getOnlySameLot() == null || !rule.getOnlySameLot().booleanValue()) && binSels.isEmpty()) {
            throw new BusinessException("putAwayRule.error.BinSelPriority.isnull");
        }
        // 重复录入库位选择顺序
        if (binSels.size() != listSels.size()) {
            throw new BusinessException("putAwayRule.error.BinSelPriority.duplicate");
        }
        // 库位验证至少录入一项，并不重复
        Set<String> binVals = new HashSet<String>();
        List<String> listBinVals = new ArrayList<String>();
        if (!StringUtils.isEmpty(rule.getBinValidate1())) {
            binVals.add(rule.getBinValidate1());
            listBinVals.add(rule.getBinValidate1());
        }
        if (!StringUtils.isEmpty(rule.getBinValidate2())) {
            binVals.add(rule.getBinValidate2());
            listBinVals.add(rule.getBinValidate2());
        }
        if (!StringUtils.isEmpty(rule.getBinValidate3())) {
            binVals.add(rule.getBinValidate3());
            listBinVals.add(rule.getBinValidate3());
        }
        if (!StringUtils.isEmpty(rule.getBinValidate4())) {
            binVals.add(rule.getBinValidate4());
            listBinVals.add(rule.getBinValidate4());
        }
        // 没有录入库位选择顺序
        if ( binVals.isEmpty()) {
            throw new BusinessException("putAwayRule.error.BinValidate.isnull");
        }
        // 重复录入库位选择顺序
        if (listBinVals.size() != binVals.size()) {
            throw new BusinessException("putAwayRule.error.BinValidate.duplicate");
        }
        // 如果启用数量过滤，则必须录入数量开始 和结束
        if (rule.getRuleMain().getUseQtyLimit()) {
            if (rule.getRuleMain().getQtyLimit() == null || rule.getRuleMain().getQtyLowerLimit() == null) {
                throw new BusinessException("putAwayRule.error.useQty.inputRequired");
            }
        }
        // 如果启用LOT过滤，则做LOT验证
        List<String> opts = Arrays.asList("=", ">=", ">", "<", "<=", "<>");
        if (rule.getUseLotLimit() && rule.getRuleLot() != null) {
            // 验证比较符正确,不验证值正确,
            for (int i = 1; i < 11; i++) {
                try {
                    Object startOpt = Ognl.getValue("opt_start" + i, rule.getRuleLot());
                    Object endOpt = Ognl.getValue("opt_end" + i, rule.getRuleLot());
                    if ((startOpt != null && !opts.contains((String) startOpt))
                            || (endOpt != null && !opts.contains((String) endOpt))) {
                        throw new BusinessException("putAwayRule.error.lot.optError", new String[] { "" + i });
                    }
                } catch (OgnlException ex) {
                    throw new BusinessException("putAwayRule.error.lot.optError", new String[] { "" + i });
                }
            }
        }
        // 外键安全性设定
        if (rule.getDescBin() != null && rule.getDescBin().getId() != null) {
            rule.setDescBin(this.commonDao.load(Bin.class, rule.getDescBin().getId()));
        } else {
            rule.setDescBin(null);
        }
        if (rule.getDescBinGroup() != null && rule.getDescBinGroup().getId() != null) {
            rule.setDescBinGroup(this.commonDao.load(BinGroup.class, rule.getDescBinGroup().getId()));
        } else {
            rule.setDescBinGroup(null);
        }
        if (rule.getSrcBin() != null && rule.getSrcBin().getId() != null) {
            rule.setSrcBin(this.commonDao.load(Bin.class, rule.getSrcBin().getId()));
        } else {
            rule.setSrcBin(null);
        }
        if (rule.getSku() != null && rule.getSku().getId() != null) {
            rule.setSku(this.commonDao.load(Sku.class, rule.getSku().getId()));
        } else {
            rule.setSku(null);
        }
        if (rule.getPackageInfo() != null && rule.getPackageInfo().getId() != null) {
            rule.setPackageInfo(this.commonDao.load(PackageInfo.class, rule.getPackageInfo().getId()));
        } else {
            rule.setPackageInfo(null);
        }
        if (rule.getPackageDetail() != null && rule.getPackageDetail().getId() != null) {
            rule.setPackageDetail(this.commonDao.load(PackageDetail.class, rule.getPackageDetail().getId()));
        } else {
            rule.setPackageDetail(null);
        }
        if (rule.getbType() != null && rule.getbType().getId() != null) {
            rule.setbType(this.commonDao.load(SkuType.class, rule.getbType().getId()));
        } else {
            rule.setbType(null);
        }
        if (rule.getmType() != null && rule.getmType().getId() != null) {
            rule.setmType(this.commonDao.load(SkuType.class, rule.getmType().getId()));
        } else {
            rule.setmType(null);
        }
        if (rule.getlType() != null && rule.getlType().getId() != null) {
            rule.setlType(this.commonDao.load(SkuType.class, rule.getlType().getId()));
        } else {
            rule.setlType(null);
        }
        if (rule.getAbcProperties() != null && rule.getAbcProperties().getId() != null) {
            rule.setAbcProperties(this.commonDao.load(AbcProperties.class, rule.getAbcProperties().getId()));
        } else {
            rule.setAbcProperties(null);
        }
        if (rule.getOt() != null && rule.getOt().getId() != null) {
            rule.setOt(this.commonDao.load(OrderType.class, rule.getOt().getId()));
        } else {
            rule.setOt(null);
        }
    }

    @Override
    public void unActivePutAwayRuleInfo(List<Long> ids) {
        for (Long id : ids) {
            PutAwayRule rule = this.commonDao.load(PutAwayRule.class, id);
            rule.setDisabled(true);
            commonDao.store(rule);
        }
    }

    @Override
    public void deleteRules(List<Long> ids) {
        for (Long id : ids) {
            PutAwayRule rule = this.commonDao.load(PutAwayRule.class, id);
            this.commonDao.delete(rule);
        }
    }

}
