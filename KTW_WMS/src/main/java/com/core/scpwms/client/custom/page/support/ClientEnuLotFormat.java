package com.core.scpwms.client.custom.page.support;

/**
 * 控制相对应的批次属性的输入格式
 * 字符、数字、日期、日期时间、选择框
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 *
 */
public interface ClientEnuLotFormat {
	
	/** 字符 */
	public static final String LOTFORMAT_CHAR = "CHAR";
	
	/** 数字 */
	public static final String LOTFORMAT_NUMERIC = "NUMERIC";
	
	/** 日期 */
	public static final String LOTFORMAT_DATE = "DATE";
	
	/** 日期时间 */
	public static final String LOTFORMAT_DATETIME = "DATETIME";	
	
	/** 选择框 */
	public static final String LOTFORMAT_LIST = "LIST";
	
	   /** 禁用 */
    public static String LOTFIELD_1 = "DISABLED";
    
    /** 必输 */
    public static String LOTFIELD_2 = "REQUIRED";
    
    /** 可选 */
    public static String LOTFIELD_3 = "OPTIONAL";
	
}
