package com.core.scpwms.server.enumerate;

/**
 * 配载状态
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 *
 */
public interface EnuCalStatus {
	
	/** 未结算 */
	public static Long STATUS100 = 100L;
	/** 已结算 */
	public static Long STATUS200 = 200L;
	/** 结算失败 */
	public static Long STATUS300 = 300L;
	/** 已审核 */
	public static Long STATUS400 = 400L;	
	/** 已关帐 */
	public static Long STATUS500 = 500L;	
	
	
}
