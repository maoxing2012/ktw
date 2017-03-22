package com.core.scpwms.server.enumerate;

/**
 * 控制相对应的批次属性的输入格式
 * 字符、数字、日期、日期时间、选择框
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 *
 */
public interface EnuLotFormat {
	
	/** 字符 */
	public static String LOTFORMAT_CHAR = "CHAR";
	
	/** 数字 */
	public static String LOTFORMAT_NUMERIC = "NUMERIC";
	
	/** 日期 */
	public static String LOTFORMAT_DATE = "DATE";
	
	/** 日期时间 */
	public static String LOTFORMAT_DATETIME = "DATETIME";	
	
	/** 选择框 */
	public static String LOTFORMAT_LIST = "LIST";
	
	/** 查询输入框 */
	public static String LOTFORMAT_REMOTE = "REMOTE";
	
}
