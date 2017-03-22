package com.core.scpwms.client.custom.page.support;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.smartgwt.client.core.BaseClass;
import com.smartgwt.client.data.Record;

/**
 * 
 * @description 库存信息反回值
 * @author MBP:xiaoyan<br/>
 * @createDate 2014-2-25
 * @version V1.0<br/>
 */
public class ClientBinDetailInfo implements IsSerializable {

	// 1.ID（隐）
	// 2.库位编号
	// 3.货主编号
	// 3.1货主名称（隐）
	// 4.商品编码
	// 5.商品名称
	// 6.批次号
	// 7.批次属性
	// 8.批次说明
	// 9.库存状态
	// 10.入库日期
	// 11.包装
	// 12.数量
	// 13.数量(EA)
	// 14.分配数(EA)
	// 15.托盘号（隐）
	// 16.容器号（隐）
	// 17.库位ID（隐）
	public String invId;
	public String ownerCode;
	public String ownerName;
	public String binCode;
	public String skucode;
	public String skuName;
	public String lotseq;
	public String lot;
	public String projectNo;
	public String invStats;
	public String invIbdDate;
	public String pkgName;
	public String pkgId;
	public String baseQty;
	public String pkgQty;
	public String allocatedQty;
	public String palletSeq;
	public String containerSeq;
	public Long binId;

	public ClientBinDetailInfo() {
	}

	public static ClientBinDetailInfo getInstance() {
		return new ClientBinDetailInfo();
	}

	public String getInvId() {
		return invId;
	}

	public void setInvId(String invId) {
		this.invId = invId;
	}

	public String getBinCode() {
		return binCode;
	}

	public void setBinCode(String binCode) {
		this.binCode = binCode;
	}

	public String getSkucode() {
		return skucode;
	}

	public void setSkucode(String skucode) {
		this.skucode = skucode;
	}

	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	public String getLotseq() {
		return lotseq;
	}

	public void setLotseq(String lotseq) {
		this.lotseq = lotseq;
	}

	public String getLot() {
		return lot;
	}

	public void setLot(String lot) {
		this.lot = lot;
	}

	public String getInvStats() {
		return invStats;
	}

	public void setInvStats(String invStats) {
		this.invStats = invStats;
	}

	public String getInvIbdDate() {
		return invIbdDate;
	}

	public void setInvIbdDate(String invIbdDate) {
		this.invIbdDate = invIbdDate;
	}

	public String getBaseQty() {
		return baseQty;
	}

	public void setBaseQty(String baseQty) {
		this.baseQty = baseQty;
	}

	public String getPkgName() {
		return pkgName;
	}

	public void setPkgName(String pkgName) {
		this.pkgName = pkgName;
	}

	public String getPkgQty() {
		return pkgQty;
	}

	public void setPkgQty(String pkgQty) {
		this.pkgQty = pkgQty;
	}

	public String getPalletSeq() {
		return palletSeq;
	}

	public void setPalletSeq(String palletSeq) {
		this.palletSeq = palletSeq;
	}

	public String getContainerSeq() {
		return containerSeq;
	}

	public void setContainerSeq(String containerSeq) {
		this.containerSeq = containerSeq;
	}

	public String getPkgId() {
		return pkgId;
	}

	public void setPkgId(String pkgId) {
		this.pkgId = pkgId;
	}

	public String getOwnerName() {
		return this.ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getProjectNo() {
		return this.projectNo;
	}

	public void setProjectNo(String projectNo) {
		this.projectNo = projectNo;
	}

	public String getAllocatedQty() {
		return this.allocatedQty;
	}

	public void setAllocatedQty(String allocatedQty) {
		this.allocatedQty = allocatedQty;
	}

	public Long getBinId() {
		return this.binId;
	}

	public void setBinId(Long binId) {
		this.binId = binId;
	}

	public String getOwnerCode() {
		return this.ownerCode;
	}

	public void setOwnerCode(String ownerCode) {
		this.ownerCode = ownerCode;
	}

}
