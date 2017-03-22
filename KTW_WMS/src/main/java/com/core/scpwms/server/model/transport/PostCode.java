package com.core.scpwms.server.model.transport;

import com.core.business.model.TrackingEntity;

public class PostCode extends TrackingEntity{
	private City city;
	
	private String addr;
	
	private String postcode;

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	
}
