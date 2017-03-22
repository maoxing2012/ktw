package com.core.scpwms.server.enumerate;

/**
 * 
 * <p>移位计划单据状态</p>
 *
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *  2014/07/27<br/>
 */
public interface EnuMoveDocStatus {

    /** 草案 */
    public static Long DRAFT = 100L;
    /** 已生成上架计划 */
    public static Long PUBLISH = 200L;
//    /** 分配中 */
//    public static Long ALLOCATING = 300L;
//    /** 分配 */
//    public static Long ALLOCATED = 400L;
//    /** 移位中 */
//    public static Long MOVING = 500L;
//    /** 关闭 */
    public static Long CLOSED = 900L;
//    /** 取消 */
    public static Long CANCEL = 0L;

}
