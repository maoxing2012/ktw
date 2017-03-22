package com.core.scpwms.server.service.sku;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.core.db.server.service.BaseManager;
import com.core.scpwms.server.model.common.PackageInfo;
import com.core.scpwms.server.model.common.Sku;

/**
 * <p>
 * SKU管理接口。
 * </p>
 * @version 1.0
 * @author MBP : 陈 <br/>
 *         修改履历 <br/>
 *         2013/02/05 : MBP 陈: 初版创建<br/>
 */
@Transactional(readOnly = false)
public interface SkuManager extends BaseManager {

    /**
     * <p>
     * 新建修改数据。
     * </p>
     * 
     * @param sku SKU对象
     * @param lotInfoId
     * @param packInfoId
     */
    @Transactional
    void saveSku(Sku sku);

    /**
     * <p>
     * 生效选中数据。
     * </p>
     * 
     * @param ids 选中的ID
     */
    @Transactional
    void enableSku(List<Long> ids);

    /**
     * <p>
     * 失效选中数据。
     * </p>
     * 
     * @param ids 选中的ID
     */
    @Transactional
    void disableSku(List<Long> ids);

    /**
     * 
     * <p>删除</p>
     *
     * @param ids
     */
    @Transactional
    public void deleteSku(List<Long> ids);
    
    /**
     * 增加条码
     * @param skuId
     * @param barCode
     * @param packLevel
     */
    @Transactional
    public void addInterCode(Long skuId, String barCode, String packLevel);
    
    /**
     * 删除条码
     * @param skuId
     * @param barCode
     * @param packLevel
     */
    @Transactional
    public void deleteInterCode(List<Long> barCodes);
    
}
