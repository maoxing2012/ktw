package com.core.scpwms.server.enumerate;

/**
 * －控制相对应的批次属性的输入情况，系统提供
 * 禁用、必输、可选，3 种选择
 * 新建时，默认为禁用
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 *
 */
public interface EnuLotFieldType {
	
	/** 禁用 */
	public static String LOTFIELD_1 = "DISABLED";
	
	/** 必输 */
	public static String LOTFIELD_2 = "REQUIRED";
	
	/** 可选 */
	public static String LOTFIELD_3 = "OPTIONAL";
}
