package com.core.scpwms.server.service.warehouse;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.core.db.server.service.BaseManager;
import com.core.scpwms.server.model.warehouse.Bin;

/**
 * <p>
 * 库位设定接口。
 * </p>
 * @version 1.0
 * @author MBP : 吴<br/>
 *         修改履历 <br/>
 *         2013/02/18 : MBP 吴: 初版创建<br/>
 */
@Transactional(readOnly = false)
public interface BinSettingManager extends BaseManager {

    /**
     * <p>
     * 新建修改库位。
     * </p>
     * 
     * @param bin 库位实例
     */
    @Transactional
    void saveBin(Bin bin);
    
    /**
     * 删除库位
     * @param ids
     */
    @Transactional
    void deleteBin(List<Long> ids);


    /**
     * <p>
     * 生效选中数据。
     * </p>
     * 
     * @param ids 选中的ID
     */
    @Transactional
    void enableBin(List<Long> ids);


    /**
     * <p>
     * 失效选中数据。
     * </p>
     * 
     * @param ids 选中的ID
     */
    @Transactional
    void disableBin(List<Long> ids);

    
    /**
     * <p>
     * 锁定库位。
     * </p>
     * 
     * @param lockStatus 锁定状态
     * @param lockReason 锁定理由
     * @param ids 选中的ID
     */
    @Transactional
    void lockBin(String lockStatus,String lockReason,List<Long> ids);
    
    /**
     * <p>
     * 解锁库位。
     * </p>
     * 
     * @param ids 选中的ID
     */
    @Transactional
    void unlockBin(List<Long> ids);


    /**
     * <p>
     * 批量创建库位。
     * </p>
     * @param aisleFrom 起始道
     * @param aisleTo 截止道
     * @param stackFrom 起始间
     * @param stackTo 截止间
     * @param depthFrom 起始列
     * @param depthTo 截止列
     * @param levelFrom 起始层
     * @param levelTo 截止层
     * @param bin 库位实例
     * @param prefix 编码前缀
     * @param aisleCount 道编码位数
     * @param aisleString 道编码分隔符
     * @param stackCount 开间编码位数
     * @param stackString 开间编码分隔符
     * @param depthCount 列编码位数
     * @param depthString 列编码分隔符
     * @param levelCount 层编码位数
     */
    @Transactional
    void createBins(Integer aisleFrom,Integer aisleTo,Integer stackFrom,Integer stackTo,Integer depthFrom,Integer depthTo,Integer levelFrom,Integer levelTo,Bin bin,
    		String prefix, Integer aisleCount, String aisleString, Integer stackCount, String stackString, Integer depthCount, String depthString, Integer levelCount);

    @Transactional
    List<String[]> refBinInfo( String whCode );
    
    @Transactional
    Bin autoCreateBin( Long whId, String stType );
}
