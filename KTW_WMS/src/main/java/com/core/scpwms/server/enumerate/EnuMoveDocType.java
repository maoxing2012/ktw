package com.core.scpwms.server.enumerate;

/**
 * 
 * <p>自动创建移位计划类型</p>
 *
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *  2014/08/08<br/>
 */
public interface EnuMoveDocType {
	/** 库位错误存放 */
	public static String ERROR_BIN = "ERROR_BIN";

	/** 相同批次合并 */
	public static String SAME_LOT = "SAME_LOT";
	
	/** 库位优化 */
	public static String SLOT_OPT = "SLOT_OPT";
}
