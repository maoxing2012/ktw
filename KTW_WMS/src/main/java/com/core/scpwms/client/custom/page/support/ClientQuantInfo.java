package com.core.scpwms.client.custom.page.support;

import java.util.HashMap;
import java.util.Map;

import com.core.scpview.client.ui.table.RowData;
import com.google.gwt.user.client.rpc.IsSerializable;

public class ClientQuantInfo implements IsSerializable{
		
		public String id;
		//SKU AND SKU NAME
		public RowData rowData;
		
		//Package id and package's name
		public String pkgId;
		public String pkgName;

		//LotInfo
		public Map<String,String> lotInfo = new HashMap<String,String>();
		
	    public ClientQuantInfo(){
		}

		public String getId() {
			return this.id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public RowData getRowData() {
			return this.rowData;
		}

		public void setRowData(RowData rowData) {
			this.rowData = rowData;
		}

		public String getPkgId() {
			return this.pkgId;
		}

		public void setPkgId(String pkgId) {
			this.pkgId = pkgId;
		}

		public String getPkgName() {
			return this.pkgName;
		}

		public void setPkgName(String pkgName) {
			this.pkgName = pkgName;
		}

		public Map<String, String> getLotInfo() {
			return this.lotInfo;
		}

		public void setLotInfo(Map<String, String> lotInfo) {
			this.lotInfo = lotInfo;
		}

}
