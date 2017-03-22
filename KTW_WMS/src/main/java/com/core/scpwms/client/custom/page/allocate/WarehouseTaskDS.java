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

public class WarehouseTaskDS extends DataSource {

	/** 数据源 */
	private static WarehouseTaskDS instance = null;

	/**
	 * 
	 * <p>
	 * 获取数据源
	 * </p>
	 * 
	 * @return
	 */
	public static WarehouseTaskDS getInstance() {
		if (instance == null) {
			instance = new WarehouseTaskDS("WarehouseTaskDS");
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
	public WarehouseTaskDS(String id) {

		setID(id);

		DataSourceIntegerField pkField = new DataSourceIntegerField("pk");
		pkField.setHidden(Boolean.TRUE);
		pkField.setPrimaryKey(Boolean.TRUE);
		addField(pkField);

		// 1.ID
		// 2.作业号
		// 3.货主名称
		// 4.商品编号
		// 5.商品名称
		// 6.批次属性
		// 7.批次说明
		// 8.库存状态
		// 9.包装
		// 10.计划上架数
		// 11.计划上架数(EA)
		// 12.已上架数
		// 13.已上架数(EA)
		// 14.目标库位
		// 15.目标托盘号
		// 16.目标容器号
		// 17.原库位
		// 18.原托盘号
		// 19.原容器号
		for (int i = 0; i < DispatchUIFactory.WT_WIDTH.length; i++) {
			DataSourceTextField field = new DataSourceTextField(
					"WarehouseTaskDS" + i,
					LocaleUtils.getText("WarehouseTaskDS" + i));
			addField(field);
		}

		setClientOnly(Boolean.TRUE);
	}

}
