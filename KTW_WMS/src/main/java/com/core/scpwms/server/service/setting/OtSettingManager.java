package com.core.scpwms.server.service.setting;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.core.db.server.service.BaseManager;
import com.core.scpwms.server.model.common.OrderType;

/**
 * 
 * <p>
 * 单据类型设定接口。
 * </p>
 * 
 * @version 1.0
 * @author MBP : 戴 <br/>
 *         修改履历 <br/>
 *         2013/02/22 : MBP 戴: 初版创建<br/>
 */
@Transactional(readOnly = false)
public interface OtSettingManager extends BaseManager {

    /**
     * 
     * <p>
     * 新建或者修改一个单据类型设定.
     * </p>
     * 
     * @param ot 单据类型设定信息
     */
    @Transactional
    public void saveOt(OrderType ot);

    /**
     * 删除单据类型
     * @param ids
     */
    @Transactional
    public void deleteOt(List<Long> ids);

    /**
     * 
     * <p>
     * 使单据类型设定生效
     * </p>
     * 
     * @param ids 选中的id列表
     */
    @Transactional
    public void enableOt(List<Long> ids);

    /**
     * 
     * <p>
     * 使单据类型设定失效
     * </p>
     * 
     * @param ids 选中的id列表
     */
    @Transactional
    public void disabledOt(List<Long> ids);

}
