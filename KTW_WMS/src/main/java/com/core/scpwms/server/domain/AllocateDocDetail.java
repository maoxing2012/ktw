package com.core.scpwms.server.domain;

import java.util.Date;
import java.util.Set;

import com.core.scpwms.server.model.common.OrderType;
import com.core.scpwms.server.model.common.PackageDetail;
import com.core.scpwms.server.model.common.Sku;
import com.core.scpwms.server.model.inventory.Inventory;
import com.core.scpwms.server.model.lot.LotInputData;
import com.core.scpwms.server.model.task.WarehouseTask;
import com.core.scpwms.server.model.user.Owner;
import com.core.scpwms.server.model.warehouse.Bin;
import com.core.scpwms.server.model.warehouse.Warehouse;

/**
 * 
 * <p>需要分配库存的订单明细接口</p>
 *
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *  2014/07/24<br/>
 */
@SuppressWarnings("all")
public interface AllocateDocDetail {

    public abstract Sku getSku();

    public abstract PackageDetail getPackageDetail();

    public abstract AllocateDoc getAllocateDoc();

    public abstract Double getUnAllocateQty();

    public abstract LotInputData getLotData();

    public abstract Set<WarehouseTask> getTasks();

    // public abstract Double getPreAllocatedQty();

    // public abstract void setPreAllocatedQty(Double preAllocatedQty);

    public abstract void allocate(Inventory inventory, double quantity);
    
    public abstract void unallocate(double quantity);

    public abstract Bin getBin();

    public abstract OrderType OrderType();

    // public abstract String getRefNo();

    public abstract Warehouse getWh();

    public abstract String getTaskProcessType();

//    public abstract String getProjectNo();

    public abstract Owner getOwner();
    
    public abstract String getInvStatus();
    
    public abstract Date getExpDateMin();
    
    public abstract Date getExpDateMax();
    
    public abstract Boolean getCanMixExp();

}
