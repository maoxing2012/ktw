package com.core.scpwms.client.custom.page.support;

import java.util.HashMap;
import java.util.Map;

import com.core.scpview.client.utils.LocaleUtils;
import com.google.gwt.user.client.rpc.IsSerializable;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class ClientBinDetailRecords implements IsSerializable {
	private static ClientBinDetailInfo binInfo;
	private static ClientBinDetailRecords cbdrecord = null;
	private static Map<String, String> temBinId = new HashMap<String, String>();;

	public ClientBinDetailRecords(ClientBinDetailInfo binInfo) {
		this.binInfo = binInfo;
	}

	public static ClientBinDetailRecords getInstance(ClientBinDetailInfo binInfo) {
		if (cbdrecord == null) {
			cbdrecord = new ClientBinDetailRecords(binInfo);
		}
		ClientBinDetailRecords.binInfo = binInfo;
		return cbdrecord;

	}

	public int count = 0;

	public int getCounter() {
		return count++;
	}

	public ListGridRecord getRecord() {
		ListGridRecord record = new ListGridRecord();
		record.setAttribute("pk", getCounter());

		// 0.ID（隐）
		// 1.库位编号
		// 2.货主编号
		// 3.货主名称（隐）
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
		record.setAttribute("InventoryDetailDS0", binInfo.getInvId());
		record.setAttribute("InventoryDetailDS1", binInfo.getBinCode());
		record.setAttribute("InventoryDetailDS2", binInfo.getOwnerCode());
		record.setAttribute("InventoryDetailDS3", binInfo.getOwnerName());
		record.setAttribute("InventoryDetailDS4", binInfo.getSkucode());
		record.setAttribute("InventoryDetailDS5", binInfo.getSkuName());
		record.setAttribute("InventoryDetailDS6", binInfo.getLotseq());
		record.setAttribute("InventoryDetailDS7", binInfo.getLot());
		record.setAttribute("InventoryDetailDS8", binInfo.getProjectNo());
		record.setAttribute("InventoryDetailDS9", getStatus(binInfo.getInvStats()));
		record.setAttribute("InventoryDetailDS10", binInfo.getInvIbdDate());
		record.setAttribute("InventoryDetailDS11", binInfo.getPkgName());
		record.setAttribute("InventoryDetailDS12", binInfo.getPkgQty());
		record.setAttribute("InventoryDetailDS13", binInfo.getBaseQty());
		record.setAttribute("InventoryDetailDS14", binInfo.getAllocatedQty());
		record.setAttribute("InventoryDetailDS15", binInfo.getPalletSeq());
		record.setAttribute("InventoryDetailDS16", binInfo.getContainerSeq());

		temBinId.clear();
		temBinId.put(binInfo.getInvId(), String.valueOf(binInfo.getBinId()));
		return record;
	}

	public Map<String, String> getTemBinId() {
		return temBinId;
	}

	public String getStatus(String statusValue) {
		return LocaleUtils.getText("EnuInvStatus." + statusValue);
	}

}
