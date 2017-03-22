package com.core.scpwms.server.enumerate;

/**
 * 
 * <p>原因码类型</p>
 *
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *  2014/08/06<br/>
 */
public interface EnuReasonCodeType {
    /** 盘点 */
    public static final String COUNT = "COUNT";

    /** 破损执行 */
    public static final String SCRAP = "SCRAP";

    /** 批次调整 */
    public static final String LOT_ADJUST = "LOT_ADJUST";

    /** 移位 */
    public static final String MOVE = "MOVE";

}
