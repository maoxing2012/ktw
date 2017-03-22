package com.core.scpwms.server.service.fee;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.core.db.server.service.BaseManager;
import com.core.scpwms.server.model.fee.RequestReportHead;

/**
 * 
 * <p>请求书处理</p>
 *
 * @version 1.0
 * @author MBP :毛　幸<br/>
 * 変更履歴 <br/>
 *  2013-6-11 : MBP 毛　幸: 新規作成<br/>
 */
@Transactional(readOnly = false)
public interface RequestReportManager extends BaseManager {

    /**
     * 
     * <p>新建头部</p>
     *
     * @param head
     */
    @Transactional
    public void editRequest(RequestReportHead head);

    /**
     * 
     * <p>删除头部</p>
     *
     * @param ids
     */
    @Transactional
    public void deleteRequest(List<Long> ids);

    /**
     * 
     * <p>生成报表数据</p>
     *
     * @param ids
     */
    @Deprecated
    @Transactional
    public void doProcess(List<Long> ids);
    
    /**
     * 
     * <p>生成报表数据</p>
     *
     * @param ids
     */
    @Transactional
    public void doProcess2(List<Long> ids);

    /**
     * 
     * <p>恢复到处理前</p>
     *
     * @param ids
     */
    @Transactional
    public void deleteRequestDetail(List<Long> ids);
}
