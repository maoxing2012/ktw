package com.core.scpwms.server.enumerate;

/**
 * 发运单对应的包裹的物流状态
 * 装箱中
 * 装箱完成 
 * 已装运 
 * 在途
 * 签收
 * 运抵
 * 回单 
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 */
public interface EnuPakcageDocStatus {
	/** 装箱中 */
	public static Long STATUS100 = 100L;
	
	/** 装箱完成 */
	public static Long STATUS200 = 200L;
	
	/** 待装车 */
	public static Long STATUS210 = 210L;
	
	/** 已装车 */
	public static Long STATUS250 = 250L;
	
	/** 在途 */
	public static Long STATUS300 = 300L;
	
//	/** 部分签收 */
//	public static Long STATUS310 = 310L;
//	
//	/** 运抵签收 */
//	public static Long STATUS400 = 400L;
//	
//	/** 回单 */
//	public static Long STATUS500 = 500L;
}
