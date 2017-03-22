package com.core.scpwms.server.enumerate;

/**
 * 
 * <p>作业人员费用统计单</p>
 *
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *  2014/08/29<br/>
 */
public interface EnuFeeReportStatus {
    // 新建
    public static final Long DRAFT = 100L;

    // 发行
    public static final Long PUBLISH = 200L;

    // 结算
    public static final Long CLOSE = 999L;

}
