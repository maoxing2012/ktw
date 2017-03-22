package com.core.scpwms.server.model.rules;

import com.core.db.server.model.Entity;
import com.core.scpwms.server.model.common.OrderType;

/**
 * 
 * <p>策略和订单的关联联系</p>
 *
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *  2014/07/04<br/>
 */
@SuppressWarnings("all")
public class OrderTypeRule extends Entity {
    /** 上架策略 */
    private PutAwayRule putAwayRule;

    /** 拣货策略 */
    private PickUpRule pickUpRule;

    /** 订单类型 */
    private OrderType orderType;

    public PutAwayRule getPutAwayRule() {
        return this.putAwayRule;
    }

    public void setPutAwayRule(PutAwayRule putAwayRule) {
        this.putAwayRule = putAwayRule;
    }

    public PickUpRule getPickUpRule() {
        return this.pickUpRule;
    }

    public void setPickUpRule(PickUpRule pickUpRule) {
        this.pickUpRule = pickUpRule;
    }

    public OrderType getOrderType() {
        return this.orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

}
