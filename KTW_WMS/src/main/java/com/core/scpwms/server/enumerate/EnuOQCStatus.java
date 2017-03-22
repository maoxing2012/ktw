package com.core.scpwms.server.enumerate;

/**
 * 
 * <p>OQC计划状态</p>
 *
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *  2015年12月8日<br/>
 */
public interface EnuOQCStatus {
	/** 发行 */
	public static Long STATUS200 = 200L;
	/** 质检中 */
	public static Long STATUS300 = 300L;
	/** 质检完毕 */
	public static Long STATUS400 = 400L;
	/** 关闭 */
	public static Long STATUS999 = 999L;
}
