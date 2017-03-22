package com.core.scpwms.client.custom.page.allocate;

import com.core.scpview.client.utils.LocaleUtils;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;



/**
 * 
 * @description   移位计划分配画面的计划明细部DataSource
 * @author         MBP:xiaoyan<br/>
 * @createDate    2014-2-20
 * @version        V1.0<br/>
 */
public class MovePlanDocDetailDS extends DataSource {
	
	/**
	 * 数据源
	 */
	private static MovePlanDocDetailDS instance = null;
	
	/**
	 * 
	 * <p>构造函数</p>
	 *
	 * @param id
	 */
	public MovePlanDocDetailDS(String id){
		
		setID(id);

        DataSourceIntegerField pkField = new DataSourceIntegerField("pk");
        pkField.setHidden(Boolean.TRUE);
        pkField.setPrimaryKey(Boolean.TRUE);
        addField(pkField);
        

        /**
         * 显示列数：序号、货主名称、原库位、原托盘、状态、书号、书名、包装单位、
         * 			计划数量、包装数量、移位数量、分配数量、批次号、
         * 			批次号、批次属性、目标库位
         */
        for (int i = 0; i < DispatchUIFactory.MOVEPLANDOCDETAILDS_WIDTH.length; i++) {
			DataSourceTextField field = new DataSourceTextField("MovePlanDocDetailDS" + i, LocaleUtils.getText("MovePlanDocDetailDS" + i));
			addField(field);
		}        
        
        
        setClientOnly(Boolean.TRUE);
		
	}
	
	/**
	 * 
	 * <p>获取数据源</p>
	 *
	 * @return
	 */
	public static MovePlanDocDetailDS getInstance(){
		
		if(instance == null){
			instance = new MovePlanDocDetailDS("MovePlanDocDetailDS");
		}
		
		return instance;
		
	}
	
	

}
