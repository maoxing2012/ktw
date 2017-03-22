package com.core.scpwms.client.custom.page.support;

import java.util.ArrayList;
import java.util.List;

import com.core.scpview.client.utils.LocaleUtils;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class ClientWhTaskRecords {
	private static ClientWhTaskRecords taskRecords = null;
	private static List<ClientTaskDetail> taskDetail = null;

	public ClientWhTaskRecords() {
	}

	public static ClientWhTaskRecords getInstance(
			List<ClientTaskDetail> taskDetail) {
		if (taskRecords == null) {
			taskRecords = new ClientWhTaskRecords();
		}
		if (taskDetail == null) {
			taskDetail = new ArrayList<ClientTaskDetail>();
		}
		ClientWhTaskRecords.taskDetail = taskDetail;
		return taskRecords;

	}

	public int count = 0;

	public int getCounter() {
		return count++;
	}

	public Record[] getRecords() {
		List<ListGridRecord> list = new ArrayList<ListGridRecord>();
		for (ClientTaskDetail data : taskDetail) {

			ListGridRecord updataDetailRecords = new ListGridRecord();
			updataDetailRecords.setAttribute("pk", getCounter());

			// 0.ID（隐）
			// 1.作业号
			// 2.状态
			// 3.货主编号
			// 4.货主名称（隐）
			// 5.商品编号
			// 6.商品名称
			// 7.批次属性
			// 8.批次说明
			// 9.库存状态
			// 10.包装
			// 11.计划上架数
			// 12.计划上架数(EA)
			// 13.已上架数（隐）
			// 14.已上架数(EA)
			// 15.目标库位
			// 16.目标托盘号（隐）
			// 17.目标容器号（隐）
			// 18.原库位
			// 19.原托盘号（隐）
			// 20.原容器号（隐）
			updataDetailRecords.setAttribute("WarehouseTaskDS0",data.getTaskIds());
			updataDetailRecords.setAttribute("WarehouseTaskDS1",data.getTaskSequence());
			updataDetailRecords.setAttribute("WarehouseTaskDS2",getWtStatus(data.getStatus()));
			updataDetailRecords.setAttribute("WarehouseTaskDS3",data.getOwnerCode());
			updataDetailRecords.setAttribute("WarehouseTaskDS4",data.getOwnerName());
			updataDetailRecords.setAttribute("WarehouseTaskDS5",data.getSkuCode());
			updataDetailRecords.setAttribute("WarehouseTaskDS6",data.getSkuName());
			updataDetailRecords.setAttribute("WarehouseTaskDS7",data.getLotInfo());
			updataDetailRecords.setAttribute("WarehouseTaskDS8",data.getProjectNo());
			updataDetailRecords.setAttribute("WarehouseTaskDS9",getInvStatus(data.getInvStatus()));
			updataDetailRecords.setAttribute("WarehouseTaskDS10",data.getPkgName());
			updataDetailRecords.setAttribute("WarehouseTaskDS11",data.getPutawayPackQty());
			updataDetailRecords.setAttribute("WarehouseTaskDS12",data.getPlanQty());
			updataDetailRecords.setAttribute("WarehouseTaskDS13",data.getExecutePackQty());
			updataDetailRecords.setAttribute("WarehouseTaskDS14",data.getExecuteQty());
			updataDetailRecords.setAttribute("WarehouseTaskDS15",data.getDescBin());
			updataDetailRecords.setAttribute("WarehouseTaskDS16",data.getDescPalletSeq());
			updataDetailRecords.setAttribute("WarehouseTaskDS17",data.getDescContainerSeq());
			updataDetailRecords.setAttribute("WarehouseTaskDS18",data.getSrcBin());
			updataDetailRecords.setAttribute("WarehouseTaskDS19",data.getSrcPalletSeq());
			updataDetailRecords.setAttribute("WarehouseTaskDS20",data.getSrcContainerSeq());
			list.add(updataDetailRecords);
		}
		ListGridRecord[] clientTaskDetail = new ListGridRecord[taskDetail
				.size()];
		return list.toArray(clientTaskDetail);

	}

	private String getInvStatus(String invStatus) {
		return LocaleUtils.getText("EnuInvStatus." + invStatus);
	}
	
	private String getWtStatus(Long status) {
		return LocaleUtils.getText("EnuWarehouseOrderStatus." + status);
	}
}
