package com.core.scpwms.server.domain;

import java.util.Date;

import com.core.business.utils.DoubleUtil;
import com.core.db.server.model.DomainModel;
import com.core.scpwms.server.model.inventory.Inventory;
import com.core.scpwms.server.model.task.WarehouseTask;
import com.core.scpwms.server.util.PrecisionUtils;

@SuppressWarnings("all")
public class BinDpsInfo extends DomainModel {
	/** DPS的地址号，4位 */
	private String dpsAddress;
	
	/** 巷道灯的地址号，4位 */
	private String roadLightAddress1;
	
	/** 巷道灯的地址号，4位 */
	private String roadLightAddress2;
	
	/** 控制器的端口号 */
	private String controllerPort;
	
	/** 控制器的IP地址 */
	private String controllerIp;

	public String getDpsAddress() {
		return this.dpsAddress;
	}

	public void setDpsAddress(String dpsAddress) {
		this.dpsAddress = dpsAddress;
	}

	public String getRoadLightAddress1() {
		return this.roadLightAddress1;
	}

	public void setRoadLightAddress1(String roadLightAddress1) {
		this.roadLightAddress1 = roadLightAddress1;
	}

	public String getRoadLightAddress2() {
		return this.roadLightAddress2;
	}

	public void setRoadLightAddress2(String roadLightAddress2) {
		this.roadLightAddress2 = roadLightAddress2;
	}

	public String getControllerPort() {
		return this.controllerPort;
	}

	public void setControllerPort(String controllerPort) {
		this.controllerPort = controllerPort;
	}

	public String getControllerIp() {
		return this.controllerIp;
	}

	public void setControllerIp(String controllerIp) {
		this.controllerIp = controllerIp;
	}

}
