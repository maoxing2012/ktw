package com.core.scpwms.server.domain;

import java.util.Date;

import com.core.business.utils.DoubleUtil;
import com.core.db.server.model.DomainModel;
import com.core.scpwms.server.model.inventory.Inventory;
import com.core.scpwms.server.model.task.WarehouseTask;
import com.core.scpwms.server.util.PrecisionUtils;

@SuppressWarnings("all")
public class PrintInfo extends DomainModel {
	/** 打印机IP */
	private String printIp;
	
	/** 端口号 */
	private String printPort;
	
	/** 打印机名称 */
	private String printName;
	
	/** 打印机型号 */
	private String printType;

	public String getPrintIp() {
		return this.printIp;
	}

	public void setPrintIp(String printIp) {
		this.printIp = printIp;
	}

	public String getPrintPort() {
		return this.printPort;
	}

	public void setPrintPort(String printPort) {
		this.printPort = printPort;
	}

	public String getPrintName() {
		return this.printName;
	}

	public void setPrintName(String printName) {
		this.printName = printName;
	}

	public String getPrintType() {
		return this.printType;
	}

	public void setPrintType(String printType) {
		this.printType = printType;
	}
	
}
