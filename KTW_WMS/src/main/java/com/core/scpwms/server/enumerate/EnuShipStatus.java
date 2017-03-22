package com.core.scpwms.server.enumerate;

/**
 * 配载状态
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 *
 */
public interface EnuShipStatus {
	
	/** 未配载 */
	public static Long STATUS100 = 100L;
	
	/** 已配载 */
	public static Long STATUS200 = 200L;
	
	/** 部分装运 */
	public static Long STATUS230 = 230L;
	
	/** 已装运 */
	public static Long STATUS250 = 250L;	
	
	/** 在途 */
	public static Long STATUS300 = 300L;
	
	/** 部分签收 */
	public static Long STATUS310 = 310L;
	
	/** 运抵签收 */
	public static Long STATUS400 = 400L;
	
	/** 回单 */
	public static Long STATUS500 = 500L;	
}
