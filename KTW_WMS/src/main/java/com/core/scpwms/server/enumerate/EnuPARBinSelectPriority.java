package com.core.scpwms.server.enumerate;

/**
 * 上架策略中，库位选择限定
 * 1、空库位优先
 * 2、历史存货库位
 * 3、历史拣选库位
 * 4、库位内必须有相同SKU
 * 5、库位内必须有相同批次库存
 * 6、库位内必须有相同大类的库存
 * 7、库位内必须有相同中类的库存
 * 8、库位内必须有相同小类的库存
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 */
public interface EnuPARBinSelectPriority {
	
	/**  空库位优先 */
	public static String EMPTY = "EMPTY";
	
	/**  历史存货库位 */
	public static String HIS_PA = "HIS_PA";
	
	/**  历史拣选库位 */
	public static String HIS_PK = "HIS_PK";
	
	/**  库位内必须有相同SKU */
	public static String SKU_IN_BIN = "SKU_IN_BIN";
	
	/**  库位内必须有相同批次库存 */
	public static String LOT_IN_BIN = "LOT_IN_BIN";
	
	/**  库位内必须有相同大类的库存 */
	public static String BT_IN_BIN = "BT_IN_BIN";
	
	/**  库位内必须有相同中类的库存 */
	public static String MT_IN_BIN = "MT_IN_BIN";
	
	/**  库位内必须有相同小类的库存 */
	public static String LT_IN_BIN = "LT_IN_BIN";	
	
	/**  同供应商优先 */
	public static String SUPPLIER_IN_BIN = "SUPPLIER_IN_BIN";	
	
}
