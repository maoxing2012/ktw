package com.core.scpwms.server.model.accessory;

import java.util.Date;

import com.core.business.utils.DoubleUtil;
import com.core.db.server.model.VersionalEntity;
import com.core.scpwms.client.custom.page.picking.ClientPickingInventory;
import com.core.scpwms.server.domain.InventoryInfo;
import com.core.scpwms.server.enumerate.EnuInvStatus;
import com.core.scpwms.server.model.common.PackageDetail;
import com.core.scpwms.server.model.user.Owner;
import com.core.scpwms.server.model.warehouse.Bin;
import com.core.scpwms.server.model.warehouse.Warehouse;
import com.core.scpwms.server.util.PrecisionUtils;

/**
 * 
 * <p>辅料库存信息</p>
 *
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *  2014/08/08<br/>
 */
@SuppressWarnings("all")
public class AccessoryInventory extends VersionalEntity {
    /** 仓库 */
    private Warehouse wh;

    /** 辅料 */
    private Accessory accessory;

    /** 数量 */
    private Double baseQty = 0D;

    /** 库位 */
    private Bin bin;

    /**  辅料库存状态 */
    private String status = EnuInvStatus.AVAILABLE;

    public Accessory getAccessory() {
        return this.accessory;
    }

    public void setAccessory(Accessory accessory) {
        this.accessory = accessory;
    }

    public Double getBaseQty() {
        return this.baseQty;
    }

    public void setBaseQty(Double baseQty) {
        this.baseQty = baseQty;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public void addQty(double qty) {
        if (qty > 0) {
            this.baseQty = DoubleUtil.add(this.baseQty, qty);
        }
    }

    public void removeQty(double qty) {
        if (qty > 0) {
            this.baseQty = DoubleUtil.sub(this.baseQty, qty);
        }
    }
}
