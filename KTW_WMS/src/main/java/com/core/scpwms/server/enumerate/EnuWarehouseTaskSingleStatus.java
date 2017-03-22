package com.core.scpwms.server.enumerate;

/**
 * 个数明细
 * @author mousachi
 *
 */
public interface EnuWarehouseTaskSingleStatus {
	// 未拣货
	public static Long STATUS000 = 0L;
    // 拣货完成
	public static Long STATUS100 = 100L;
	// 复合完成
    public static Long STATUS200 = 200L;
    // 发运
    public static Long STATUS999 = 999L;

}
