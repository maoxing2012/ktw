package com.core.scpwms.client.custom.page.support;

import java.util.ArrayList;
import java.util.List;

import com.core.scpview.client.utils.LocaleUtils;
import com.google.gwt.user.client.rpc.IsSerializable;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class ClientAsnUpdataDetailRecord implements IsSerializable {

	private static ClientAsnUpdataDetailRecord putUpdataDetail = null;
	private static List<ClientAsnUpdataDetail> detailData = null;

	public ClientAsnUpdataDetailRecord() {
	}

	public static ClientAsnUpdataDetailRecord getInstance(List<ClientAsnUpdataDetail> detailData) {
		if (putUpdataDetail == null) {
			putUpdataDetail = new ClientAsnUpdataDetailRecord();
		}
		ClientAsnUpdataDetailRecord.detailData = detailData;
		return putUpdataDetail;

	}

	public int count = 0;

	public int getCounter() {
		return count++;
	}

	public Record[] getRecords() {
		List<ListGridRecord> list = new ArrayList<ListGridRecord>();
		for (ClientAsnUpdataDetail data : detailData) {
			
			// 0.ID（隐）
			// 1.货主编号
			// 2.货主名称（隐）
			// 3.商品编号
			// 4.商品名称
			// 5.批次属性
			// 6.批次说明
			// 7.库存状态
			// 8.包装
			// 9.计划上架数量
			// 10.计划上架数量(EA)
			// 11.分配数(EA)
			// 12.上架数(EA)
			// 13.原库位
			// 14.原托盘号（隐）
			// 15.原容器号（隐）
			
			ListGridRecord updataDetailRecords = new ListGridRecord();
			updataDetailRecords.setAttribute("pk", getCounter());
			updataDetailRecords.setAttribute("PutawayDocDetailDS0",data.getIndexId());
			updataDetailRecords.setAttribute("PutawayDocDetailDS1",data.getOwnerCode());
			updataDetailRecords.setAttribute("PutawayDocDetailDS2",data.getOwnerName());
			updataDetailRecords.setAttribute("PutawayDocDetailDS3",data.getSkuCode());
			updataDetailRecords.setAttribute("PutawayDocDetailDS4",data.getSkuName());
			updataDetailRecords.setAttribute("PutawayDocDetailDS5",data.getLotInfo());
			updataDetailRecords.setAttribute("PutawayDocDetailDS6",data.getProjectNo());
			updataDetailRecords.setAttribute("PutawayDocDetailDS7",getInvStatus(data.getInvStatus()));
			updataDetailRecords.setAttribute("PutawayDocDetailDS8",data.getPlanPackage());
			updataDetailRecords.setAttribute("PutawayDocDetailDS9",data.getPlanPackQty());
			updataDetailRecords.setAttribute("PutawayDocDetailDS10",data.getPlanQty());
			updataDetailRecords.setAttribute("PutawayDocDetailDS11",data.getAllocateQty());
			updataDetailRecords.setAttribute("PutawayDocDetailDS12",data.getExecuteQty());
			updataDetailRecords.setAttribute("PutawayDocDetailDS13",data.getSrcBin());
			updataDetailRecords.setAttribute("PutawayDocDetailDS14",data.getPalletSeq());
			updataDetailRecords.setAttribute("PutawayDocDetailDS15",data.getContainerSeq());
			list.add(updataDetailRecords);
		}
		ListGridRecord[] clientputUpdataRds = new ListGridRecord[detailData.size()];
		return list.toArray(clientputUpdataRds);
	}

	private String getInvStatus(String invStatus) {
		return LocaleUtils.getText("EnuInvStatus." + invStatus);
	}
}
