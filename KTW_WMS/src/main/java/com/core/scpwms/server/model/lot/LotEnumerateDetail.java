package com.core.scpwms.server.model.lot;

import com.core.db.server.model.Entity;

/**
 * 选择框明细
 * @see LotEnumerate
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 *
 */
@SuppressWarnings("all")
public class LotEnumerateDetail extends Entity {

    /**
     * 所属选择框
     * @see LotEnumerate
     */
    private LotEnumerate lotEnumerate;

    /**
     * 代码
     * 此代码会存储至数据库
     */
    private String code;

    /**
     * 下拉框内显示 
     * */
    private String name;

    /** 排序 */
    private Integer lineNo;

    public LotEnumerate getLotEnumerate() {
        return this.lotEnumerate;
    }

    public void setLotEnumerate(LotEnumerate lotEnumerate) {
        this.lotEnumerate = lotEnumerate;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLineNo() {
        return this.lineNo;
    }

    public void setLineNo(Integer lineNo) {
        this.lineNo = lineNo;
    }

}
