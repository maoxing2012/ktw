package com.core.scpwms.server.model.inventory;

import java.util.HashSet;
import java.util.Set;

import com.core.db.server.model.VersionalEntity;
import com.core.scpwms.server.model.warehouse.Warehouse;

/**
 * 按照Quant汇总库存信息
 * 便于统计
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 *
 */
@SuppressWarnings("all")
public class QuantInventory extends VersionalEntity {

    /** 
     * 仓库 
     */
    private Warehouse wh;

    /** 
     * Quant信息 
     */
    private Quant quant;

    /** 
     * 库存集合
     */
    private Set<Inventory> invs = new HashSet<Inventory>();

    public Warehouse getWh() {
        return this.wh;
    }

    public void setWh(Warehouse wh) {
        this.wh = wh;
    }

    public Quant getQuant() {
        return quant;
    }

    public void setQuant(Quant quant) {
        this.quant = quant;
    }

    public Set<Inventory> getInvs() {
        return invs;
    }

    public void setInvs(Set<Inventory> invs) {
        this.invs = invs;
    }
}
