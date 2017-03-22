package com.core.scpwms.server.model.warehouse;

import com.core.db.server.model.Entity;
import com.core.scpwms.server.model.user.Carrier;

/**
 * 
 * <p>承运商和集货口发货月台的绑定关系</p>
 *
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *  2016年1月5日<br/>
 */
@SuppressWarnings("all")
public class CarrierBinGroup extends Entity {

    /** 所属仓库 */
    private Warehouse wh;

    /** 库位组 */
    private BinGroup bg;
    
    /** 承运商 */
    private Carrier carrier;

	public Warehouse getWh() {
		return this.wh;
	}

	public void setWh(Warehouse wh) {
		this.wh = wh;
	}

	public BinGroup getBg() {
		return this.bg;
	}

	public void setBg(BinGroup bg) {
		this.bg = bg;
	}

	public Carrier getCarrier() {
		return this.carrier;
	}

	public void setCarrier(Carrier carrier) {
		this.carrier = carrier;
	}
    
}
