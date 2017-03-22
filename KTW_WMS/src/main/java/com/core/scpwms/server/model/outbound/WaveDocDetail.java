package com.core.scpwms.server.model.outbound;

import java.util.HashSet;
import java.util.Set;

import com.core.business.utils.DoubleUtil;
import com.core.db.server.model.Entity;
import com.core.scpwms.server.domain.AllocateDoc;
import com.core.scpwms.server.domain.AllocateDocDetail;
import com.core.scpwms.server.enumerate.EnuWtProcessType;
import com.core.scpwms.server.model.common.OrderType;
import com.core.scpwms.server.model.common.PackageDetail;
import com.core.scpwms.server.model.common.Sku;
import com.core.scpwms.server.model.inventory.Inventory;
import com.core.scpwms.server.model.lot.LotInputData;
import com.core.scpwms.server.model.task.WarehouseTask;
import com.core.scpwms.server.model.user.Owner;
import com.core.scpwms.server.model.warehouse.Bin;
import com.core.scpwms.server.model.warehouse.Warehouse;
import com.core.scpwms.server.util.PrecisionUtils;

/**
 * 波次作业单明细
 * 从出库单明细引申过来；
 * 按照出库单号对商品作数量汇总；
 * 如果多单批件（忽略单据号），不使用出库单号，对所有出库单号商品作批件；
 * 凤凰毅力项目内：使用物理设备和流程分拣，所以还是按单拣选
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 *
 */
@SuppressWarnings("all")
public class WaveDocDetail extends Entity{

    /** 所属波次单 */
    private WaveDoc doc;
    
    private OutboundDelivery obd;
    
    private OutboundDeliveryDetail obdDetail;
    
	public WaveDoc getDoc() {
		return doc;
	}

	public void setDoc(WaveDoc doc) {
		this.doc = doc;
	}

	public OutboundDelivery getObd() {
		return obd;
	}

	public void setObd(OutboundDelivery obd) {
		this.obd = obd;
	}

	public OutboundDeliveryDetail getObdDetail() {
		return obdDetail;
	}

	public void setObdDetail(OutboundDeliveryDetail obdDetail) {
		this.obdDetail = obdDetail;
	}
    
}
