package com.core.scpwms.server.service.warehouse;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.core.db.server.service.BaseManager;
import com.core.scpwms.server.model.warehouse.BinGroup;

/**
 * 
 * 
 * <p>
 * 库位组管理接口
 * </p>
 * 
 * @version 1.0
 * @author MBP : dengxg </br>
 *         修改履历 </br>
 *         2013-2-18 : MBP dengxg: 初版创建</br>
 */
@Transactional(readOnly = false)
public interface BinGroupManager extends BaseManager {

    /**
     * 保存库位组属性
     * @param gb 库位组实例
     */
    @Transactional
    void store(BinGroup gb);

    /**
     * 删除库位组
     * @param ids 库位组Id
     */
    @Transactional
    void deleteBinGroup(List<Long> ids);

    /**
     * 添加库位
     * @param bgId 库位组id
     * @param ids 库位id
     */
    @Transactional
    void addBin(Long bgId, List<Long> ids);

    /**
     * 移除库位
     * @param bgId 库位组id
     * @param ids 库位id
     */
    @Transactional
    void removeBin(Long bgId, List<Long> ids);

    /**
     * 库位生效
     * @param ids 库位组id
     */
    @Transactional
    void enableBinGroup(List<Long> ids);

    /**
     * 
     * <p>库位失效</p>
     *
     * @param ids
     */
    @Transactional
    void disableBinGroup(List<Long> ids);

}
