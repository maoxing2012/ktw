package com.core.scpwms.server.enumerate;

/**
 * 库存交易类型
 */
public interface EnuInvenHisType {

    /** 收货 */
    public static String RECEIVING = "100";

    /** 收货调整 */
    public static String RECEIVING_ADJUST = "110";

    /** 发货 */
    public static String SHIPPING = "200";

    /** 发货调整 */
    public static String SHIPPING_ADJUST = "210";

    /** 发货退回 */
    public static String SHIPPING_BACK = "220";

    /** 库存调整 */
    public static String INVENTORY_ADJUST = "300";

    /** 盈亏调整 */
    public static String COUNT_ADJUST = "310";

    /** 加工消耗  */
    public static String PROCESS_DOWN = "400";

    /** 加工收货 */
    public static String PROCESS_UP = "500";

    /** 货权转入 */
    public static String COMPANY_TRANS_IN = "600";

    /** 货权转出 */
    public static String COMPANY_TRANS_OUT = "700";

    /** 批次调整 */
    public static String MODIFY_LOT = "800";

    /** 库内移位 */
    public static String MOVE = "900";

    /** 包装转换 */
    public static String CONVERT_PACKAGEUNIT = "1000";

    /** 库存状态转换 */
    public static String CHANGE_STATUS = "1100";

}
