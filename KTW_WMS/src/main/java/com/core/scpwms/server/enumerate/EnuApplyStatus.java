package com.core.scpwms.server.enumerate;

/**
 * 申请流程
 *  1、DRAFT --------- 草案状态
 * 2、APPROVING -- 审批中
 * 3、APPROVED  --- 审批完成
 * 4、EXECUTE ------ 作业中
 * 5、EXECUTED ---- 作业完成
 * 6、CLOSE ---------- 关闭
 * 7、CANCEL -------- 取消
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 */
public interface EnuApplyStatus {
    // 取消
    public static final Long CANCEL = 0L;

    // 新建
    public static final Long DRAFT = 100L;

    // 申请后
    public static final Long PUBLISH = 200L;

    // 审批完成
    public static final Long APPROVED = 300L;

    // 关闭
    public static final Long CLOSE = 999L;

}
