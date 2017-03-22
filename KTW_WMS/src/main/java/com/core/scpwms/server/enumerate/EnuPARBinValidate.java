package com.core.scpwms.server.enumerate;

/**
 * 上架策略中，库位的容量校验
 * 1、体积限定
 * 2、重量限定
 * 3、数量限定
 * 4、托盘限定
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 */
public interface EnuPARBinValidate {
	
	/**  体积限定 */
	public static String VOLUME = "VOLUME";
	
	/**  重量限定 */
	public static String WEIGHT = "WEIGHT";
	
	/**  数量限定 */
	public static String QUANTITY = "QUANTITY";	
	
	/**  托盘限定 */
	public static String PALLETQTY = "PALLETQTY";		
	
	/**  标准容量限定 */
	public static String MEASURE = "MEASURE";	
	
}
