package com.core.scpwms.server.enumerate;

/**
 * 上传文件类型
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 */
public interface EnuImportRuleType {
	// 导入
	public static final String MASTER = "MASTER";
	public static final String ASN = "ASN";
	public static final String OBD = "OBD";
	// 导出
	public static final String ASN_OUT = "ASN_OUT";
	public static final String OBD_OUT = "OBD_OUT";
	public static final String INV_OUT = "INV_OUT";
	public static final String CNT_OUT = "CNT_OUT";
}
