package com.core.scpwms.server.model.edi;

import com.core.business.model.TrackingEntity;
import com.core.scpwms.server.enumerate.EnuSoapInfoType;

public class SoapInfo extends TrackingEntity{
	private String accessIp;

	private String xmlContent;
	
	private Long status = 0L;
	
	private Long relatedSoapInfoId;
	
	/**
	 * @see EnuSoapInfoType
	 * */
	private String type;
	
	public String getXmlContent() {
		return this.xmlContent;
	}

	public void setXmlContent(String xmlContent) {
		this.xmlContent = xmlContent;
	}

	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public String getAccessIp() {
		return this.accessIp;
	}

	public void setAccessIp(String accessIp) {
		this.accessIp = accessIp;
	}

	public Long getRelatedSoapInfoId() {
		return this.relatedSoapInfoId;
	}

	public void setRelatedSoapInfoId(Long relatedSoapInfoId) {
		this.relatedSoapInfoId = relatedSoapInfoId;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
