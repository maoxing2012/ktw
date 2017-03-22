package com.core.scpwms.server.service.setting;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.core.db.server.service.BaseManager;
import com.core.scpwms.server.model.common.AbcProperties;

/**
 * 
 * <p>
 * ABC分类设定接口。
 * </p>
 * 
 * @version 1.0
 * @author MBP : 戴 <br/>
 *         修改履历 <br/>
 *         2013/02/21 : MBP 戴: 初版创建<br/>
 */
@Transactional(readOnly = false)
public interface AbcTypeManager extends BaseManager {

    /**
     * 
     * <p>
     * 新建或者修改一个ABC分类设定.
     * </p>
     * 
     * @param abc ABC分类设定
     */
    @Transactional
    public void saveAbc(AbcProperties abc);

    /**
     * 
     * <p>
     * 删除一个或多个ABC分类设定.
     * </p>
     * 
     * @param ids ABC分类设定id集合
     */
    @Transactional
    public void deleteAbc(List<Long> ids);
}
