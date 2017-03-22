package com.core.scpwms.server.enumerate;

/**
 * 
 * <p>库内作业类型，是EnuInvProcessType的一个分支的细化</p>
 *
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *  2014/07/27<br/>
 */
public interface EnuWtProcessType {

    /**
     * 收货上架
     * 需要WO
     * 对应的作业类型 EnuInvProcessType.M5000 (上架作业)
     */
    public static final String ASN_PUTAWAY = "ASN_PUTAWAY";

    /**
     * 质检上架（成品上架）
     * 需要WO
     * 对应的作业类型 EnuInvProcessType.M5000 (上架作业)
     */
    public static final String QC_PUTAWAY = "QC_PUTAWAY";

    /**
     * 加工上架（成品上架）
     * 需要WO
     * 对应的作业类型 EnuInvProcessType.M5000 (上架作业)
     */
    public static final String PRO_PUTAWAY = "PRO_PUTAWAY";

    /**
     * 退拣上架
     * 需要WO
     * 对应的作业类型 EnuInvProcessType.M5000 (上架作业)
     */
    public static final String RV_PUTAWAY = "RV_PUTAWAY";

    /**
     * 移位上架
     * 需要WO
     * 对应的作业类型 EnuInvProcessType.M5000 (上架作业)
     */
    public static final String MV_PUTAWAY = "MV_PUTAWAY";

    /**
     * 补货上架
     * 需要WO
     * 对应的作业类型 EnuInvProcessType.M5000 (上架作业)
     */
    public static final String RP_PUTAWAY = "RP_PUTAWAY";

    /**
     * 拣选上架
     * 需要WO
     * 对应的作业类型 EnuInvProcessType.M5000 (上架作业)
     */
    public static final String PK_PUTAWAY = "PK_PUTAWAY";

    /**
     * 质检下架
     * 不需要WO
     * 对应的作业类型 EnuInvProcessType.M6000 (拣货作业)
     */
    public static final String QC_REQ = "QC_REQ";

    /**
     * 加工下架
     * 不需要WO
     * 对应的作业类型 EnuInvProcessType.M6000 (拣货作业)
     */
    public static final String PRO_REQ = "PRO_REQ";

    /**
     * 移位下架
     * 不需要WO
     * 对应的作业类型 EnuInvProcessType.M6000 (拣货作业)
     */
    public static final String MV_REQ = "MV_REQ";

    /**
     * 补货下架
     * 不需要WO
     * 对应的作业类型 EnuInvProcessType.M6000 (拣货作业)
     */
    public static final String RP_REQ = "RP_REQ";

    /**
     * 拣选下架
     * 需要WO
     * 对应的作业类型 EnuInvProcessType.M6000 (拣货作业)
     */
    public static final String PK_REQ = "PK_REQ";

    /**
     * 库内移位(普通移位，直接移位)
     * 不需要WO
     * 对应的作业类型 EnuInvProcessType.M1000 (移位倒垛)
     */
    public static final String INV_MOVE = "INV_MOVE";

    /**
     * 其他(例如货主转换等)
     * 不需要WO
     * 对应的作业类型 无
     */
    public static final String OTHER = "OTHER";
}
