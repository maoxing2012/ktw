package com.core.scpwms.server.enumerate;

/**
 * 盘点记录状态
 * 100 新建后是为盘点状态
 * 200 盘点确认，盘点登记后都是盘点中状态，可反复盘点确认。
 * 300 差异确认后，盘点完成。不能再盘点确认。
 * 999 复盘或者盘点单关闭以后是关闭状态
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 *
 */
public interface EnuCountDetailStatus {
	
	/** 未盘点 */
	public static Long STATUS100 = 100L;
	
	/** 盘点中(无差异) */
	public static Long STATUS200 = 200L;
	
	/** 盘点中(有差异) */
	public static Long STATUS210 = 210L;
	
	/** 盘点完成 */
	public static Long STATUS300 = 300L;
	
	/** 关闭 */
	public static Long STATUS999 = 999L;
	
}
