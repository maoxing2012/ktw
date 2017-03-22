package com.core.scpwms.server.domain;

import com.core.scpwms.server.model.common.OrderType;
import com.core.scpwms.server.model.user.Plant;
import com.core.scpwms.server.model.warehouse.Bin;
import com.core.scpwms.server.model.warehouse.Warehouse;

/**
 * 
 * <p>需要上架分配的明细（上架履历，加工履历，质检履历，移位明细等）</p>
 *
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *  2014/07/29<br/>
 */
@SuppressWarnings("all")
public interface AllocatePutawayDetail {
    public Warehouse getWh();

    public Plant getPlant();

    public OrderTrackingEntityWithStatus getDoc();

    public String getDocSequence();

    public double getUnPlanPutawayQty();

    public double getUnPutawayQty();

    public double getPlanedUnPutawayQty();

    public InventoryInfo getInvInfo();

    public void setPutawayDocSequence(String putawayDocSequence);

    public Double getPlanPutawayQty();

    public void addPlanPutawayQty(double qty);

    public void removePlanPutawayQty(double qty);

    public Double getPutawayQty();

    public void addPutawayQty(double qty);

    public void removePutawayQty(double qty);

}
