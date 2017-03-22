package com.core.scpwms.server.enumerate;

/**
 * 批拣方式
 * 集单(默认)
 * 集货
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 *
 */
public interface EnuBatchPickConf {
	
	/** 集单 */
	public static String PICK_BY_ORDER = "PBORDER";
	
	/** 集货 */
	public static String PICK_BY_SKU = "PBSKU";
	
	/** 拆托盘 */
	public static String PICK_PALLET = "PPALLET";
	
	/** 拆包装 */
	public static String PICK_PACKAGE = "PPACKAGE";
	
}
