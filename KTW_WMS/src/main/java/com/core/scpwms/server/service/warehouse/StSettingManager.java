package com.core.scpwms.server.service.warehouse;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.core.db.server.service.BaseManager;
import com.core.scpwms.server.model.warehouse.StorageType;

/**
 * 
 * <p>
 * 功能区设定接口。
 * </p>
 * 
 * @version 1.0
 * @author MBP : 陈 <br/>
 *         修改履历 <br/>
 *         2013/01/31 : MBP 陈: 初版创建<br/>
 */
@Transactional(readOnly = false)
public interface StSettingManager extends BaseManager {

    /**
     * 
     * <p>
     * 新建或者修改一个功能区.
     * </p>
     * 
     * @param storageType 功能区信息
     */
	@Transactional
    public void saveST(StorageType storageType);


    /**
     * 
     * <p>
     * 使功能区生效
     * </p>
     * 
     * @param ids 选中的id列表
     */
	@Transactional
    public void enableST(List<Long> ids);


    /**
     * 
     * <p>
     * 使功能区失效
     * </p>
     * 
     * @param ids 选中的id列表
     */
	@Transactional
    public void disabledST(List<Long> ids);
    
    /**
     * 删除功能区
     * @param ids
     */
	@Transactional
    public void deleteST(List<Long> ids);
}
