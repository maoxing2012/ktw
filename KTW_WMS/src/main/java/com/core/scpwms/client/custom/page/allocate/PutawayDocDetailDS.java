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

/**
 * @description 上架计划手工分配画面的计划明细部DataSource
 * @version V1.0<br/>
 */
public class PutawayDocDetailDS extends DataSource {

	/** 数据源 */
	private static PutawayDocDetailDS instance = null;

	/**
	 * 
	 * <p>
	 * 获取数据源
	 * </p>
	 * 
	 * @return
	 */
	public static PutawayDocDetailDS getInstance() {
		if (instance == null) {
			instance = new PutawayDocDetailDS("PutawayDocDetailDS");
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
	public PutawayDocDetailDS(String id) {

		setID(id);

		DataSourceIntegerField pkField = new DataSourceIntegerField("pk");
		pkField.setHidden(Boolean.TRUE);
		pkField.setPrimaryKey(Boolean.TRUE);
		addField(pkField);

		// 1.ID
		// 2.货主名称
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
		// 14.原托盘号
		// 15.原容器号
		for (int i = 0; i < DispatchUIFactory.PUTAWAYDOCDETAILDS_WIDTH.length; i++) {
			DataSourceTextField field = new DataSourceTextField(
					"PutawayDocDetailDS" + i,LocaleUtils.getText("PutawayDocDetailDS" + i));
			addField(field);
		}

		setClientOnly(Boolean.TRUE);
	}

}
