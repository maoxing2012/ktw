package com.core.scpwms.server.enumerate;

/**
 * 
 * <p>申请单的明细状态</p>
 *
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *  2014/07/22<br/>
 */
public interface EnuApplyDetailStatus {
    // 取消
    public static final Long CANCEL = 0L;

    // 新建
    public static final Long DRAFT = 100L;

    // 申请后
    public static final Long PUBLISH = 200L;

    // 审批中
    public static final Long APPROVING = 210L;

    // 审批完成
    public static final Long APPROVED = 300L;

    // 关闭
    public static final Long CLOSE = 999L;

}
