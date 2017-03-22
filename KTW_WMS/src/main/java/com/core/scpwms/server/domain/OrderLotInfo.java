package com.core.scpwms.server.domain;

import java.util.Date;

import com.core.db.server.model.DomainModel;
import com.core.scpwms.server.model.common.PackageDetail;
import com.core.scpwms.server.model.common.Sku;
import com.core.scpwms.server.model.inventory.Quant;
import com.core.scpwms.server.model.lot.LotInputData;
import com.core.scpwms.server.model.user.Owner;
import com.core.scpwms.server.model.warehouse.Bin;

/**
 * 
 * <p>下订单时确定需要明确的信息</p>
 *
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *  2014/07/17<br/>
 */
@SuppressWarnings("all")
public class OrderLotInfo extends DomainModel {
    private Owner owner;// 华耐

    private Sku sku;

    private PackageDetail packageDetail;

    private LotInputData lotData;

    private String extString;// 华耐

    public Owner getOwner() {
        return this.owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public Sku getSku() {
        return this.sku;
    }

    public void setSku(Sku sku) {
        this.sku = sku;
    }

    public PackageDetail getPackageDetail() {
        return this.packageDetail;
    }

    public void setPackageDetail(PackageDetail packageDetail) {
        this.packageDetail = packageDetail;
    }

    public LotInputData getLotData() {
        return this.lotData;
    }

    public void setLotData(LotInputData lotData) {
        this.lotData = lotData;
    }

    public String getExtString() {
        return this.extString;
    }

    public void setExtString(String extString) {
        this.extString = extString;
    }

}
