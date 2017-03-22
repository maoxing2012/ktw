package com.core.scpwms.server.service.warehouse;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.core.business.model.security.User;
import com.core.db.server.service.BaseManager;
import com.core.scpwms.server.model.warehouse.Warehouse;
import com.core.scpwms.server.model.warehouse.WarehouseTmsPlatForm;

/**
 * 
 * <p>
 * 仓库管理接口定义。
 * </p>
 * 
 * @version 1.0
 * @author MBP : 温 <br/>
 *         修改履历 <br/>
 *         2013/01/13 : MBP 温: 初版创建<br/>
 */
@Transactional(readOnly = false)
public interface WarehouseManager extends BaseManager {

    /**
     * 根据登陆用户，返回第一个可使用仓库
     * @param loginUser
     * @return
     */
    @Transactional
    Warehouse getWarehouseByLoginUser(User loginUser);

    /**
     * 获取当前仓库
     * @param m
     * @return
     */
    @Transactional
    Map getCurrentWarehouse(Map m);

    /**
     * 获取当前用户可以访问的仓库
     * @param m
     * @return
     */
    @Transactional
    Map getWhListForClient(Map m);

    /**
     * 保存仓库
     * @param warehouse
     * @return
     */
    @Transactional
    void saveWh(Warehouse warehouse);

    /**
     * 生效指定的仓库
     * @param warehouse 选择待生效的仓库ID集合
     * @return
     */
    @Transactional
    void enableWh(List<Long> whIdList);

    /**
     * 失效指定的仓库
     * 系统只失效没有设置库位的仓库
     * @param warehouseIds 选择待失效的仓库ID集合
     */
    @Transactional
    void disableWh(List<Long> whIdList);

    /**
     * 删除仓库
     * @param ids
     */
    @Transactional
    void deleteWh(List<Long> ids);

    /**
     * 
     * <p>移除用户组</p>
     *
     * @param ids
     */
    @Transactional
    void deleteUserGroup(List<Long> ids);

    /**
     * 
     * <p>添加用户组</p>
     *
     * @param warehouseId
     * @param ids
     */
    @Transactional
    void addUserGroup(Long warehouseId, List<Long> ids);
    
    /**
     * 
     * <p>删除调度平台</p>
     *
     * @param whTmsIds
     */
    @Transactional
    void deleteTmsPlatform(List<Long> whTmsIds);
    
    /**
     * 
     * <p>添加调度平台</p>
     *
     * @param whId
     * @param platForm
     */
    @Transactional
    void saveTmsPlatform(Long whId, WarehouseTmsPlatForm platForm);

}
