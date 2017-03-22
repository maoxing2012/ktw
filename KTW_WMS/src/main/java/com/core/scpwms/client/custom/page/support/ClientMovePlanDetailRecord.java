package com.core.scpwms.client.custom.page.support;

import java.util.ArrayList;
import java.util.List;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class ClientMovePlanDetailRecord {
	
	private static ClientMovePlanDetailRecord mpDetail = null;
	private static List<ClientMovePlanDetailInfo> detailData = null;
	
	public ClientMovePlanDetailRecord(){
		
	}
	
	public static ClientMovePlanDetailRecord getInstance(
			List<ClientMovePlanDetailInfo> detailData) {
		if (mpDetail == null) {
			mpDetail = new ClientMovePlanDetailRecord();
		}
		if (detailData == null) {
			detailData = new ArrayList<ClientMovePlanDetailInfo>();
			
		}
		ClientMovePlanDetailRecord.detailData = detailData;
		return mpDetail;

	}
	
	public Record[] getRecords(){
		List<ListGridRecord> list=new ArrayList<ListGridRecord>();
		for(ClientMovePlanDetailInfo data : detailData){
			ListGridRecord mpDetailRecords = new ListGridRecord();
			
			mpDetailRecords.setAttribute("MovePlanDocDetailDS0", data.getMpDetailId());
			mpDetailRecords.setAttribute("MovePlanDocDetailDS1", data.getPlantName());
			mpDetailRecords.setAttribute("MovePlanDocDetailDS2", data.getSrcBinCd());
			mpDetailRecords.setAttribute("MovePlanDocDetailDS3", data.getDescBinCd());
			mpDetailRecords.setAttribute("MovePlanDocDetailDS4", data.containerSeq);
			mpDetailRecords.setAttribute("MovePlanDocDetailDS5", setStatus(data.getStatus()));
			mpDetailRecords.setAttribute("MovePlanDocDetailDS6", data.getSkuCode());
			mpDetailRecords.setAttribute("MovePlanDocDetailDS7", data.getSkuName());
			mpDetailRecords.setAttribute("MovePlanDocDetailDS8", data.getPkgName());
			mpDetailRecords.setAttribute("MovePlanDocDetailDS9", data.getPlanQty());
			mpDetailRecords.setAttribute("MovePlanDocDetailDS10", data.getPkgQty());
			mpDetailRecords.setAttribute("MovePlanDocDetailDS11", data.getExecuteQty());
			mpDetailRecords.setAttribute("MovePlanDocDetailDS12", data.getAllocateQty());
			mpDetailRecords.setAttribute("MovePlanDocDetailDS13", data.getLotSequence());
			mpDetailRecords.setAttribute("MovePlanDocDetailDS14", data.getDispLot());
			list.add(mpDetailRecords);
		}
		
		ListGridRecord[] clientputUpdataRds=new ListGridRecord[detailData.size()];
		return list.toArray(clientputUpdataRds);
	}

	/**
	 * 
	 * <p>状态设定</p>
	 *
	 * @param status
	 * @return
	 */
	private String setStatus(String status) {
		if("1".equals(status)){
			return "草案";
		}else if ("2".equals(status)) {
			return "发行";
		} else if ("3".equals(status)) {
			return "分配中";
		} else if ("4".equals(status)) {
			return "分配";
		} else if ("5".equals(status)) {
			return "移位中";
		} else if ("6".equals(status)) {
			return "移位完成";
		} else if ("9".equals(status)) {
			return "关闭";
		} else if ("0".equals(status)) {
			return "取消";
		}
		return "";
	}

}
