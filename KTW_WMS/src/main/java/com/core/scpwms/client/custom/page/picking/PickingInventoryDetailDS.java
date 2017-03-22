/**
 * (C)2012 MBP Corporation. All rights reserved.
 * 系统名称 : MBP-WMS
 * 文件名   : ManualAllocateDetailDS.java
 */

package com.core.scpwms.client.custom.page.picking;

import com.core.scpview.client.utils.LocaleUtils;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;

public class PickingInventoryDetailDS extends DataSource {
	
    /** 数据源 */
    private static PickingInventoryDetailDS instance = null;

    /**
     * 
     * <p>获取数据源</p>
     *
     * @return
     */
    public static PickingInventoryDetailDS getInstance() {
        if (instance == null) {
            instance = new PickingInventoryDetailDS("PickingInventoryDetailDS");
        }

        return instance;
    }

    /**
     * 
     * <p>构造函数</p>
     *
     * @param id
     */
    public PickingInventoryDetailDS(String id) {

        setID(id);

        DataSourceIntegerField pkField = new DataSourceIntegerField("pk");
        pkField.setHidden(Boolean.TRUE);
        pkField.setPrimaryKey(Boolean.TRUE);
        addField(pkField);

		for (int i = 0; i < PickingUIFactory.INVENTORYDETAILDS_WIDTH.length; i++) {
			DataSourceTextField field = new DataSourceTextField("InventoryDetailDS" + i, LocaleUtils.getText("InventoryDetailDS" + i));
			addField(field);
		}        
        
        setClientOnly(Boolean.TRUE);
    }

}
