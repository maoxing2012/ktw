package com.core.scpwms.server.domain;

import com.core.scpwms.server.model.common.OrderType;
import com.core.scpwms.server.model.user.Plant;
import com.core.scpwms.server.model.warehouse.Bin;
import com.core.scpwms.server.model.warehouse.Warehouse;

/**
 * 
 * <p>需要分配库存的订单接口</p>
 *
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *  2014/07/24<br/>
 */
@SuppressWarnings("all")
public interface AllocateDoc {

    public Bin getDescBin();

    public Plant getPlant();

    public OrderType getOrderType();

    public Warehouse getWh();

}
