package com.core.scpwms.server.service.common;

import java.util.Date;

import org.springframework.transaction.annotation.Transactional;

import com.core.db.server.service.BaseManager;
import com.core.scpwms.server.domain.InventoryInfo;
import com.core.scpwms.server.model.common.OrderType;
import com.core.scpwms.server.model.common.PackageDetail;
import com.core.scpwms.server.model.history.InventoryHistory;
import com.core.scpwms.server.model.inbound.AsnDetail;
import com.core.scpwms.server.model.inbound.InboundHistory;
import com.core.scpwms.server.model.inventory.Inventory;
import com.core.scpwms.server.model.inventory.Quant;
import com.core.scpwms.server.model.outbound.OutboundDeliveryDetail;
import com.core.scpwms.server.model.outbound.OutboundHistory;
import com.core.scpwms.server.model.task.WarehouseTask;
import com.core.scpwms.server.model.task.WtHistory;
import com.core.scpwms.server.model.warehouse.Bin;
import com.core.scpwms.server.model.warehouse.Labor;

/**
 * 
 * @description   库存履历辅助接口
 * @author         MBP:xiaoyan<br/>
 * @createDate    2014-2-13
 * @version        V1.0<br/>
 */
@Transactional(readOnly = false)
public interface InventoryHistoryHelper extends BaseManager {

    /**
     * 
     * <p>库存履历処理。</p>
     *
     * @param inv
     * @param moveQtyEA
     * @param iot
     * @param labor
     * @param hisType
     * @param invStatus
     * @param orderSeq
     * @param refId
     * @param memo
     * @param relatedBillNo
     * 
     * @return 
     */
    @Transactional
    InventoryHistory saveInvHistory(Inventory inv, Double moveQtyEA, OrderType iot, Labor labor, String hisType,
            String orderSeq, Long refId, String memo, String relatedBillNo1, String relatedBillNo2);

    /**
     * 
     * @param asnDetail
     * @param bin
     * @param container
     * @param pack
     * @param quant
     * @param receiveQty
     * @param invStatus
     * @param laborId
     * @return
     */
    @Transactional
    InboundHistory saveInboundHistory(AsnDetail asnDetail, Bin bin, String container, PackageDetail pack, Quant quant, double receiveQty, String invStatus, Long laborId);

    /**
     * 
     * <p>作业履历</p>
     *
     * @param wt
     * @param srcInv
     * @param descInv
     * @param labor
     * @param qty
     * @return
     */
    @Transactional
    WtHistory saveWtHistory(WarehouseTask wt, InventoryInfo srcInv, InventoryInfo descInv, Labor labor, double qty, Boolean freeStatus, Date operDate);

    /**
     * 
     * <p>发货履历</p>
     *
     * @param detail
     * @param invInfo
     * @param qty
     * @param labor
     * @return
     */
    @Transactional
    OutboundHistory saveShipHistory(OutboundDeliveryDetail detail, InventoryInfo invInfo, double qty, Labor labor, Boolean freeStatus);
    
}
