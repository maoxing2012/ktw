package com.core.scpwms.server.model.rules;

import com.core.business.model.TrackingEntity;
import com.core.business.model.TrackingEntityWithVer;
import com.core.scpwms.server.model.warehouse.Bin;
import com.core.scpwms.server.model.warehouse.BinGroup;
import com.core.scpwms.server.model.warehouse.StorageType;
import com.core.scpwms.server.model.warehouse.Warehouse;
import com.core.scpwms.server.model.warehouse.WhArea;

/**
 * 
 * <p>循环盘点策略</p>
 *
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *  2014/08/13<br/>
 */
@SuppressWarnings("all")
public class CycleCountRule extends TrackingEntity {

    /** 仓库 */
    private Warehouse wh;

    /**优先级*/
    private Integer priority;

    /** 描述 */
    private String description;

    /** 盘点周期(天) */
    private Long period;

    /** 库区 */
    private WhArea wa;

    /** 功能区 */
    private StorageType st;

    /** 库位组 */
    private BinGroup bg;

    /** 库位 */
    private Bin bin;

    /** 失效 */
    private Boolean disabled = Boolean.FALSE;

    public Integer getPriority() {
        return this.priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getPeriod() {
        return this.period;
    }

    public void setPeriod(Long period) {
        this.period = period;
    }

    public WhArea getWa() {
        return this.wa;
    }

    public void setWa(WhArea wa) {
        this.wa = wa;
    }

    public StorageType getSt() {
        return this.st;
    }

    public void setSt(StorageType st) {
        this.st = st;
    }

    public BinGroup getBg() {
        return this.bg;
    }

    public void setBg(BinGroup bg) {
        this.bg = bg;
    }

    public Bin getBin() {
        return this.bin;
    }

    public void setBin(Bin bin) {
        this.bin = bin;
    }

    public Warehouse getWh() {
        return this.wh;
    }

    public void setWh(Warehouse wh) {
        this.wh = wh;
    }

    public Boolean getDisabled() {
        return this.disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

}
