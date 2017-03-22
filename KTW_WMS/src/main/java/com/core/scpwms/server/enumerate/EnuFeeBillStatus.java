package com.core.scpwms.server.enumerate;

/**
 * 
 * <p>计费单状态</p>
 *
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *  2014/08/18<br/>
 */
public interface EnuFeeBillStatus {
    // 取消
    public static final Long CANCEL = 0L;

    // 新建
    public static final Long DRAFT = 100L;

    // 发行
    public static final Long PUBLISH = 200L;

    // 已结算
    public static final Long CLOSE = 300L;

}
