package com.core.scpwms.server.service.setting;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.core.db.server.service.BaseManager;
import com.core.scpwms.server.model.lot.LotEnumerate;
import com.core.scpwms.server.model.lot.LotEnumerateDetail;

/**
 * 
 * <p>
 * 批次枚举维护接口。
 * </p>
 * 
 * @version 1.0
 * @author MBP : 吴 <br/>
 *         修改履历 <br/>
 *         2013/02/27 : MBP 吴: 初版创建<br/>
 */
@Transactional(readOnly = false)
public interface LotEnumerateManager extends BaseManager {

    /**
    * 
    * <p>
    * 新建或者修改批次.
    * </p>
    * 
    * @param lotEnumerate 批次信息
    */
    @Transactional
    public void saveLotEnumerate(LotEnumerate lotEnumerate);

    /**
     * 
     * <p>删除批次信息</p>
     *
     * @param ids 选中的id列表
     */
    @Transactional
    public void deleteLotEnumerate(List<Long> ids);

    /**
     * 
     * <p>保存明细</p>
     *
     * @param id
     * @param detail
     */
    @Transactional
    void saveDetail(Long id, LotEnumerateDetail detail);

    /**
     * 
     * <p>删除明细</p>
     *
     * @param ids
     */
    @Transactional
    void removeDetail(List<Long> ids);

    /**
     * 
     * <p>生效</p>
     *
     * @param ids
     */
    @Transactional
    void enableLotEnumerate(List<Long> ids);

    /**
     * 
     * <p>失效</p>
     *
     * @param ids
     */
    @Transactional
    void disableLLotEnumerate(List<Long> ids);

}
