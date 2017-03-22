package com.core.scpwms.server.domain;

import java.util.Date;

import com.core.db.server.model.DomainModel;
import com.core.scpwms.server.model.common.PackageDetail;
import com.core.scpwms.server.model.common.ReasonCode;
import com.core.scpwms.server.model.inventory.Quant;
import com.core.scpwms.server.model.user.Owner;
import com.core.scpwms.server.model.warehouse.Bin;

/**
 * 
 * <p>确定一条库存的属性</p>
 *
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *  2014/07/17<br/>
 */
@SuppressWarnings("all")
public class InventoryInfo extends DomainModel {
    private Owner owner;

    private Quant quant;

    private Bin bin;

    private String containerSeq;
    
    private String palletSeq;

    private PackageDetail packageDetail;

    private Date inboundDate;

    private String trackSeq;

    private String invStatus;
    
    private ReasonCode reasonCode;

    public Owner getOwner() {
        return this.owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public Quant getQuant() {
        return this.quant;
    }

    public void setQuant(Quant quant) {
        this.quant = quant;
    }

    public Bin getBin() {
        return this.bin;
    }

    public void setBin(Bin bin) {
        this.bin = bin;
    }

    public String getContainerSeq() {
        return this.containerSeq;
    }

    public void setContainerSeq(String containerSeq) {
        this.containerSeq = containerSeq;
    }

    public PackageDetail getPackageDetail() {
        return this.packageDetail;
    }

    public void setPackageDetail(PackageDetail packageDetail) {
        this.packageDetail = packageDetail;
    }

    public Date getInboundDate() {
        return this.inboundDate;
    }

    public void setInboundDate(Date inboundDate) {
        this.inboundDate = inboundDate;
    }

    public String getTrackSeq() {
        return this.trackSeq;
    }

    public void setTrackSeq(String trackSeq) {
        this.trackSeq = trackSeq;
    }

    public String getInvStatus() {
        return this.invStatus;
    }

    public void setInvStatus(String invStatus) {
        this.invStatus = invStatus;
    }

	public ReasonCode getReasonCode() {
		return this.reasonCode;
	}

	public void setReasonCode(ReasonCode reasonCode) {
		this.reasonCode = reasonCode;
	}

	public String getPalletSeq() {
		return palletSeq;
	}

	public void setPalletSeq(String palletSeq) {
		this.palletSeq = palletSeq;
	}
	
}
