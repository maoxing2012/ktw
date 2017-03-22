package com.core.scpwms.server.enumerate;

/**
 * 
 * <p>
 * 作业量是否要计费用
 * </p>
 * 
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *         2014/08/08<br/>
 */
public interface EnuKpiFreeStatus {

	/**
	 * 不计费用
	 */
	public static final String FREE = "FREE";

	/**
	 * 正常计费
	 */
	public static final String UNFREE = "UNFREE";

}
