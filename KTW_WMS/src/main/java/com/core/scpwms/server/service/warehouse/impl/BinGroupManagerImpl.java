package com.core.scpwms.server.service.warehouse.impl;

import java.util.List;

import com.core.business.exception.BusinessException;
import com.core.business.model.domain.CreateInfo;
import com.core.business.model.domain.UpdateInfo;
import com.core.business.web.security.UserHolder;
import com.core.db.server.service.pojo.DefaultBaseManager;
import com.core.scpwms.server.constant.ExceptionConstant;
import com.core.scpwms.server.filter.WarehouseHolder;
import com.core.scpwms.server.model.warehouse.Bin;
import com.core.scpwms.server.model.warehouse.BinBinGroup;
import com.core.scpwms.server.model.warehouse.BinGroup;
import com.core.scpwms.server.service.warehouse.BinGroupManager;

/**
 * 
 * <p>
 * 库位组设定实现
 * </p>
 * 
 * @version 1.0
 * @author MBP : dengxg </br>
 *         修改履历 </br>
 *         2013-2-19 : MBP dengxg: 初版创建</br>
 */
public class BinGroupManagerImpl extends DefaultBaseManager implements BinGroupManager {

    /*
     * (non-Javadoc)
     * 
     * @see com.core.scpwms.server.service.warehouse.BinGroupManager#deleteBinGroup(java.util.List)
     */
    @Override
    public void deleteBinGroup(List<Long> ids) {
        for (Long id : ids) {
            BinGroup binGroup = this.commonDao.load(BinGroup.class, id);
            
            // 策略内是否已经有用到了该库位组
            // 上架策略
            String hql = "select id from PutAwayRule where descBinGroup.id = :binGroupId";
            List<Long> parIds = commonDao.findByQuery(hql, "binGroupId", id);
            if( parIds != null && parIds.size() > 0 ){
            	throw new BusinessException(ExceptionConstant.CANNOT_DELETE_AS_HAS_HISTORY);
            }
            
            // 拣货策略
            String hql2 = "select id from PickUpRule where descBinGroup.id = :binGroupId";
            List<Long> purIds = commonDao.findByQuery(hql2, "binGroupId", id);
            if( purIds != null && purIds.size() > 0 ){
            	throw new BusinessException(ExceptionConstant.CANNOT_DELETE_AS_HAS_HISTORY);
            }
            
            // 循环盘点策略
            String hql3 = "select id from CycleCountRule where bg.id = :binGroupId";
            List<Long> ccrIds = commonDao.findByQuery(hql3, "binGroupId", id);
            if( ccrIds != null && ccrIds.size() > 0 ){
            	throw new BusinessException(ExceptionConstant.CANNOT_DELETE_AS_HAS_HISTORY);
            }
            
            // 删除库位组和库位的关系
            List<BinBinGroup> bbgs = commonDao.findByQuery("from BinBinGroup where binGroup.id = :binGroupId", "binGroupId", id);
            if( bbgs != null && bbgs.size() > 0 ){
            	for( BinBinGroup bbg : bbgs ){
            		commonDao.delete(bbg);
            	}
            }
            
            // 删除库位组
            commonDao.delete(binGroup);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.core.scpwms.server.service.warehouse.BinGroupManager#store(com.core.scpwms.server.model.warehouse.BinGroup)
     */
    @SuppressWarnings("unchecked")
    @Override
    public void store(BinGroup gb) {

        if (gb.isNew()) {
            // 判断是否重复记录
            StringBuffer hql = new StringBuffer("from BinGroup bg where lower(bg.code) = :bgCode and bg.wh=:whId");
            List<BinGroup> bgs = commonDao.findByQuery(hql.toString(), new String[] { "bgCode", "whId" }, new Object[] {
                    gb.getCode().toLowerCase(), WarehouseHolder.getWarehouse() });
            if (!bgs.isEmpty()) {
                throw new BusinessException(ExceptionConstant.DUPLICATE_CODE);
            }

            gb.setWh(WarehouseHolder.getWarehouse());
            gb.setCreateInfo(new CreateInfo(UserHolder.getUser()));
        } else {
            gb.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
        }
        this.commonDao.store(gb);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.core.scpwms.server.service.warehouse.BinGroupManager#addBin(java.lang.Long,
     *      java.util.List)
     */
    @Override
    public void addBin(Long bgId, List<Long> ids) {
        BinGroup binGroup = this.commonDao.load(BinGroup.class, bgId);
        for (Long id : ids) {
            Bin bin = this.commonDao.load(Bin.class, id);
            
            BinBinGroup bbg = new BinBinGroup();
            bbg.setBin(bin);
            bbg.setBinGroup(binGroup);
            commonDao.store(bbg);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.core.scpwms.server.service.warehouse.BinGroupManager#removeBin(java.lang.Long,
     *      java.util.List)
     */
    @Override
    public void removeBin(Long bgId, List<Long> ids) {
        BinGroup binGroup = this.commonDao.load(BinGroup.class, bgId);
        for (Long id : ids) {
            Bin bin = this.commonDao.load(Bin.class, id);
            binGroup.getBins().remove(bin);
            this.commonDao.store(binGroup);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.core.scpwms.server.service.warehouse.BinGroupManager#disableBinGroup(java.util.List)
     */
    @Override
    public void disableBinGroup(List<Long> ids) {
        for (Long id : ids) {
            BinGroup bg = commonDao.load(BinGroup.class, id);
            bg.setDisabled(Boolean.TRUE);
            bg.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
            commonDao.store(bg);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.core.scpwms.server.service.warehouse.BinGroupManager#enableBinGroup(java.util.List)
     */
    @Override
    public void enableBinGroup(List<Long> ids) {
        for (Long id : ids) {
            BinGroup bg = commonDao.load(BinGroup.class, id);
            bg.setDisabled(Boolean.FALSE);
            bg.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
            commonDao.store(bg);
        }
    }

}
