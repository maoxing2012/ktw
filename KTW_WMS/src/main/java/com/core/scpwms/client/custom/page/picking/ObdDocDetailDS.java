/**
 * (C)2012 MBP Corporation. All rights reserved.
 */

package com.core.scpwms.client.custom.page.picking;

import com.core.scpview.client.utils.LocaleUtils;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;

/**
 * @description   发货单明细
 * @version        V1.0<br/>
 */
public class ObdDocDetailDS extends DataSource {
	
    /** 数据源 */
    private static ObdDocDetailDS instance = null;

    /**
     * 
     * <p>获取数据源</p>
     *
     * @return
     */
    public static ObdDocDetailDS getInstance() {
        if (instance == null) {
            instance = new ObdDocDetailDS("ObdDocDetailDS");
        }

        return instance;
    }

    /**
     * 
     * <p>构造函数</p>
     *
     * @param id
     */
    public ObdDocDetailDS(String id) {

        setID(id);

        DataSourceIntegerField pkField = new DataSourceIntegerField("pk");
        pkField.setHidden(Boolean.TRUE);
        pkField.setPrimaryKey(Boolean.TRUE);
        addField(pkField);
        
		for (int i = 0; i < PickingUIFactory.DOC_DETAIL_DS_WIDTH.length; i++) {
			DataSourceTextField field = new DataSourceTextField("ObdDocDetailDS_" + i, LocaleUtils.getText("ObdDocDetailDS_" + i));
			addField(field);
		}        
        
        setClientOnly(Boolean.TRUE);
    }

}
