package com.core.scpwms.server.enumerate;

/**
 * 
 * <p>OQC计划状态</p>
 *
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *  2015年12月8日<br/>
 */
public interface EnuOQCDetailStatus {
	/** 未质检 */
	public static Long STATUS200 = 200L;
	/** 已质检 */
	public static Long STATUS300 = 300L;
	/** 关闭 */
	public static Long STATUS999 = 999L;
}
