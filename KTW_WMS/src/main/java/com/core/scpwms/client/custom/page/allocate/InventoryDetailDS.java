/**
 * (C)2012 MBP Corporation. All rights reserved.
 * 系统名称 : MBP-WMS
 * 文件名   : ManualAllocateDetailDS.java
 */

package com.core.scpwms.client.custom.page.allocate;

import com.core.scpview.client.utils.LocaleUtils;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;

public class InventoryDetailDS extends DataSource {

	/** 数据源 */
	private static InventoryDetailDS instance = null;

	/**
	 * 
	 * <p>
	 * 获取数据源
	 * </p>
	 * 
	 * @return
	 */
	public static InventoryDetailDS getInstance() {
		if (instance == null) {
			instance = new InventoryDetailDS("InventoryDetailDS");
		}

		return instance;
	}

	/**
	 * 
	 * <p>
	 * 构造函数
	 * </p>
	 * 
	 * @param id
	 */
	public InventoryDetailDS(String id) {

		setID(id);

		DataSourceIntegerField pkField = new DataSourceIntegerField("pk");
		pkField.setHidden(Boolean.TRUE);
		pkField.setPrimaryKey(Boolean.TRUE);
		addField(pkField);

		// 1.ID
		// 2.库位编号
		// 3.货主
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
		// 15.托盘号
		// 16.容器号
		// 17.库位ID
		for (int i = 0; i < DispatchUIFactory.INVENTORYDETAILDS_WIDTH.length; i++) {
			DataSourceTextField field = new DataSourceTextField(
					"InventoryDetailDS" + i,
					LocaleUtils.getText("InventoryDetailDS" + i));
			addField(field);
		}

		setClientOnly(Boolean.TRUE);
	}

}
