package com.core.scpwms.server.enumerate;

/**
 * 客户发货数量限定
 * 1、严格数量发货
 * 2、允许多发
 * 3、允许部分发
 * 4、无限定
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 *
 */
public interface EnuShipQtyConf {
	
	/** 严格数量发货 */
	public static String SHIP_QTY01 = "SHIP_QTY01";

	/** 允许多发 */
	public static String SHIP_QTY02 = "SHIP_QTY02";
	
	/** 允许部分发 */
	public static String SHIP_QTY03 = "SHIP_QTY03";
	
	/** 无限定 */
	public static String SHIP_QTY04 = "SHIP_QTY04";
}
