package com.core.scpwms.server.service.sku;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.core.db.server.service.BaseManager;
import com.core.scpwms.server.model.common.PackageDetail;
import com.core.scpwms.server.model.common.PackageInfo;

/**
 * 
 * <p>
 * 包装管理接口。
 * </p>
 * 
 * @version 1.0
 * @author MBP : 陈 <br/>
 *         修改履历 <br/>
 *         2013/02/06 : MBP 陈: 初版创建<br/>
 */
@Transactional(readOnly = false)
public interface PackageManager extends BaseManager {

    /**
     * 
     * <p>保存包装组</p>
     * 
     * @param packageInfo
     */
    @Transactional
    public void savePackageInfo(PackageInfo packageInfo);

    /**
     * 
     * <p>生效</p>
     * 
     * @param ids
     */
    @Transactional
    public void enablePackage(List<Long> ids);

    /**
     * 
     * <p>失效</p>
     * 
     * @param ids
     */
    @Transactional
    public void disabledPackage(List<Long> ids);

    /**
     * 
     * <p>删除包装组</p>
     *
     * @param ids
     */
    @Transactional
    public void deletePackge(List<Long> ids);
    
    /**
     * 
     * <p>根据包装组ID，取得所有可用的包装信息，从小到大排序</p>
     *
     * @param packInfoId
     * @return
     */
    @Transactional
    public List<PackageDetail> getPackageDetails( Long packInfoId );
}
