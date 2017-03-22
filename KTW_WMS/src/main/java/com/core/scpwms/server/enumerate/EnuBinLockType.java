package com.core.scpwms.server.enumerate;

/**
 * 库位锁
 * 包含：
 * 入锁
 * 出锁
 * 出入锁
 * 盘点锁
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 *
 */
public interface EnuBinLockType {
	
	/** 入锁 */
	public static String LOCK_T1 = "LOCK_T1";
	
	/** 出锁 */
	public static String LOCK_T2 = "LOCK_T2";
	
	/** 出入锁 */
	public static String LOCK_T3 = "LOCK_T3";
	
	/** 盘点锁 */
	public static String LOCK_T4 = "LOCK_T4";
	
}
