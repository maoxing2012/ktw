package com.core.scpwms.server.model.document;

import com.core.business.model.TrackingEntityWithVer;

/**
 * 库内作业单据基类
 * 库内作业流程是
 * 
 * 作业登记 --〉生成Doc --〉分配库位（可选）-->生成WT ---〉组装WO --〉人员分配
 * 
 * 注：目前收货流程没有走此流程，按照ASN进行收货
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 *
 */
public abstract class BaseWarehouseDocument extends TrackingEntityWithVer{
	
	
	
}
