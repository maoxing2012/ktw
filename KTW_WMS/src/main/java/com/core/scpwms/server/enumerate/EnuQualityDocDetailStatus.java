package com.core.scpwms.server.enumerate;

/**
 * 
 * <p>质检单明细状态</p>
 *
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *  2014/07/22<br/>
 */
public interface EnuQualityDocDetailStatus {
    // 取消
    public static final Long CANCEL = 0L;

    // 草案
    public static final Long DRAFT = 100L;

    // 发行
    public static final Long PUBLISH = 200L;

    // 质检中
    public static final Long APPROVING = 210L;

    // 质检完成
    public static final Long APPROVED = 300L;

    // 关闭
    public static final Long CLOSE = 999L;

}
