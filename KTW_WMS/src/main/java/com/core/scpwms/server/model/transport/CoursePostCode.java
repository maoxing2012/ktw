package com.core.scpwms.server.model.transport;

import com.core.business.model.TrackingEntity;

public class CoursePostCode extends TrackingEntity{
	private Course course;
	
	private PostCode postCode;

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public PostCode getPostCode() {
		return postCode;
	}

	public void setPostCode(PostCode postCode) {
		this.postCode = postCode;
	}
	
}
