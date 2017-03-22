package com.core.scpwms.client.custom.page.picking;

import com.core.scpview.client.utils.LocaleUtils;
import com.google.gwt.user.client.rpc.IsSerializable;

public class ClientObdDoc implements IsSerializable {
	
	/** 发货单ID  */
	private Long docId;
	
	/** 发货单号 */
	private String code;
	
	/** 发货库位 */
	private String binCode;
	
	/** 订单数量 */
	private double orderQuantity;
	
	/** 已分配数量 */
	private double allocatedQuantity;
	
	/** 拣货数量 */
	private double pickedQuantity;

	/** 预分配数量 */
	private double sortQty;
	
	public ClientObdDoc(){}
	
//	public  ClientObdDoc(Long id,Object code,Object binCode,Object orderQuantity,Object sortQty,Object allocatedQuantity,Object pickedQuantity){
//		this.docId = (Long)id;
//		this.code = (String)code;
//		this.binCode = (String)binCode;
//		this.orderQuantity = (Double)orderQuantity;
//		this.sortQty = (Double)sortQty;
//		this.allocatedQuantity = (Double)allocatedQuantity;
//		this.pickedQuantity = (Double)pickedQuantity;
//	}	
	
	public Long getDocId() {
		return docId;
	}
	public void setDocId(Long docId) {
		this.docId = docId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public double getOrderQuantity() {
		return orderQuantity;
	}
	public void setOrderQuantity(double orderQuantity) {
		this.orderQuantity = orderQuantity;
	}
	public double getAllocatedQuantity() {
		return allocatedQuantity;
	}
	public void setAllocatedQuantity(double allocatedQuantity) {
		this.allocatedQuantity = allocatedQuantity;
	}
	public double getPickedQuantity() {
		return pickedQuantity;
	}
	public void setPickedQuantity(double pickedQuantity) {
		this.pickedQuantity = pickedQuantity;
	}
	
	public String getBinCode() {
		return binCode;
	}

	public void setBinCode(String binCode) {
		this.binCode = binCode;
	}

	public double getSortQty() {
		return sortQty;
	}

	public void setSortQty(double sortQty) {
		this.sortQty = sortQty;
	}

	public String geneTitle(){
		return LocaleUtils.getText("allocateForPicking.table.allocate") ;
//		+ "  " 
//		+ LocaleUtils.getText("ObdDocDetailDS_12") + "：" + orderQuantity 
//		+ "  " 
//		+ LocaleUtils.getText("ObdDocDetailDS_13") + "：" + allocatedQuantity  ;
	}
	
	
}
