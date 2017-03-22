package com.core.scpwms.server.enumerate;

/**
 * 
 * <p>作业量的结算状态</p>
 *
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *  2014/08/08<br/>
 */
public interface EnuAccountStatus {

    /**
     * 未结算
     */
    public static final String OPEN = "OPEN";

    /**
     * 已结算
     */
    public static final String CLOSED = "CLOSED";

}
