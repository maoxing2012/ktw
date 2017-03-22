package com.core.scpwms.server.enumerate;

/**
 * 温度带
 * @author mousachi
 *
 */
public interface EnuTemperatureDiv {
	
	/** 未定 */
	public static final Long UNDEF = 0L;
	
	/** 常温 */
	public static final Long CW = 1L;
	
	/** 冷蔵 */
	public static final Long LC = 2L;
	
	/** 冷凍 */
	public static final Long LD = 3L;
	
	/** 低温 */
	public static final Long DW = 4L;
			
	/** ドライ */
	public static final Long DRY = 5L;
	
	/** チルド */
	public static final Long CHILLED = 7L;
}
