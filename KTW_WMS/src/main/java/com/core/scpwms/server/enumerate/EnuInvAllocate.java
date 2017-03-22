package com.core.scpwms.server.enumerate;

/**
 * 
 * <p>库存分配</p>
 *
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *  2014/06/26<br/>
 */
public interface EnuInvAllocate {
    /** 清库位优先 */
    public static final String CLEAR_BIN = "clearBin";
    
    /** 拣货次数最少优先 */
    public static final String PICK_TIME = "pickTime";
}
