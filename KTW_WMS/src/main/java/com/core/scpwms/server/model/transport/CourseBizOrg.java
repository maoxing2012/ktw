package com.core.scpwms.server.model.transport;

import com.core.business.model.TrackingEntity;
import com.core.scpwms.server.model.user.BizOrg;

public class CourseBizOrg extends TrackingEntity{
	private Course course;
	
	private BizOrg customer;
	
	private Long inx;

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public BizOrg getCustomer() {
		return customer;
	}

	public void setCustomer(BizOrg customer) {
		this.customer = customer;
	}

	public Long getInx() {
		return inx;
	}

	public void setInx(Long inx) {
		this.inx = inx;
	}
	
}
