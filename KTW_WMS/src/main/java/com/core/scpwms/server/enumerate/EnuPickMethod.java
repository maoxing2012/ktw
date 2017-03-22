package com.core.scpwms.server.enumerate;

/**
 * 
 * <p>拣货的作业方式</p>
 *
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *  2015年12月3日<br/>
 */
public interface EnuPickMethod {
	// PDA拣货(整)
	public static String PICK_BY_PDA = "PICK_BY_PDA";
	
	// PDA拣货(散)
	public static String PICK_BY_PDA_PS = "PICK_BY_PDA_PS";
	
	// 拣货小车
	public static String PICK_BY_CAR = "PICK_BY_CAR";

	// 叉车平板
	public static String PICK_BY_FORK_PAD = "PICK_BY_FORK_PAD";
	
	// DPS
	public static String PICK_BY_DPS = "PICK_BY_FORK_PAD";
}
