package com.core.scpwms.server.enumerate;

/**
 * 盘点单状态
 * 
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 * 
 */
public interface EnuCountStatus extends EnuCommonStatus {
	/** 盘点中 */
	public static Long STATUS300 = 300L;
	
	/** 差异调整 */
	public static Long STATUS400 = 400L;
}
