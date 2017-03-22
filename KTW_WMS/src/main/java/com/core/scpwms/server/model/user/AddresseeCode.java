package com.core.scpwms.server.model.user;

import com.core.business.model.TrackingEntity;
import com.core.business.model.domain.ContractInfo;


@SuppressWarnings("all")
public class AddresseeCode extends TrackingEntity{
	
	private Plant plant;
	
	private String code;
	
	private String provinceCode;
	
	private String cityCode;
	
	private String provinceName;
	
	private String cityName;

	public Plant getPlant() {
		return plant;
	}

	public void setPlant(Plant plant) {
		this.plant = plant;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
}
