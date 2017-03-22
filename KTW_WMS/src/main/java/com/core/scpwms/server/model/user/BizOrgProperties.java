package com.core.scpwms.server.model.user;

import com.core.business.model.TrackingEntity;

/**
 * 收货方/销售客户 作业设定，包含如下内容
 * 1、默认优先级别
 * 2、发货数量限定
 * 3、发货批次限定
 * 4、不同的收件人，使用不同的打印标签制定
 * 5、预分配设定（发货单据生效时、自动预分配库存）
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 *
 */
@SuppressWarnings("all")
public class BizOrgProperties extends TrackingEntity {

	/** 所属业务角色 */
	private BizOrg customer;
	
	/** 
	 * 收货方默认优先级别 
	 * 若发货单指定单据优先级，以单据优先级为主
	 * */
	private Integer priority = 0;
	
	/**
	 * 发货数量限定
	 * @see EnuShipQtyConf
	 */
	private String shipQtyConf;
	
	/**
	 * 发货批次限定
	 * @See EnuShipLotConf
	 */
	private String shipLotConf;

	/**
	 * 不同的收件人，使用不同的打印标签制定
	 */
	private String outboundLabel;
	
	/**
	 * 预分配设定（发货单据生效时、自动预分配库存）
	 */
	private Boolean preAllocate=Boolean.TRUE;	
	
	/**
	 * 是否失效
	 **/
	private Boolean disabled=Boolean.FALSE;
	
	/**
	 * 按单包箱(一般用于电商，按客户头来把几个订单入一个箱子里发货,默认是)
	 */
	private Boolean orderPck = Boolean.TRUE;
	
	/**
	 * 抽检比例（%）
	 */
	private Double qcRate= 0D;

	public BizOrg getCustomer() {
		return customer;
	}

	public void setCustomer(BizOrg customer) {
		this.customer = customer;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public String getShipQtyConf() {
		return shipQtyConf;
	}

	public void setShipQtyConf(String shipQtyConf) {
		this.shipQtyConf = shipQtyConf;
	}

	public String getShipLotConf() {
		return shipLotConf;
	}

	public void setShipLotConf(String shipLotConf) {
		this.shipLotConf = shipLotConf;
	}

	public String getOutboundLabel() {
		return outboundLabel;
	}

	public void setOutboundLabel(String outboundLabel) {
		this.outboundLabel = outboundLabel;
	}

	public Boolean getPreAllocate() {
		return preAllocate;
	}

	public void setPreAllocate(Boolean preAllocate) {
		this.preAllocate = preAllocate;
	}

	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	public Boolean getOrderPck() {
		return this.orderPck;
	}

	public void setOrderPck(Boolean orderPck) {
		this.orderPck = orderPck;
	}

	public Double getQcRate() {
		return this.qcRate;
	}

	public void setQcRate(Double qcRate) {
		this.qcRate = qcRate;
	}	
	
}
