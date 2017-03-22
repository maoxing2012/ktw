package com.core.scpwms.server.service.setting;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.core.db.server.service.BaseManager;
import com.core.scpwms.server.model.common.SequenceProperties;

/**
 * 
 * <p>
 * 单据编号设定接口。
 * </p>
 * 
 * @version 1.0
 * @author MBP : 吴 <br/>
 *         修改履历 <br/>
 *         2013/02/21 : MBP 吴: 初版创建<br/>
 */
@Transactional(readOnly = false)
public interface SeqSettingManager extends BaseManager {

    /**
    * 
    * <p>
    * 新建或者修改单据编号.
    * </p>
    * 
    * @param seq 单据编号信息
    */
    @Transactional
    public void saveSeq(SequenceProperties seq);

    /**
     * 
     * <p>删除单据编号信息</p>
     *
     * @param ids 选中的id列表
     */
    @Transactional
    public void deleteSeq(List<Long> ids);

    /**
     * 
     * <p>生效</p>
     *
     * @param ids
     */
    @Transactional
    public void enableSeq(List<Long> ids);

    /**
     * 
     * <p>失效</p>
     *
     * @param ids
     */
    @Transactional
    public void disableSeq(List<Long> ids);

}
