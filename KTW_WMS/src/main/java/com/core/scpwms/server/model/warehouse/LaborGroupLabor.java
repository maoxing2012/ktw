package com.core.scpwms.server.model.warehouse;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.core.db.server.model.Entity;

/**
 * 
 * <p>作业人员和作业组Mapping关系维护</p>
 *
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *  2014/06/26<br/>
 */
@SuppressWarnings("all")
public class LaborGroupLabor extends Entity {
	
	/** 作业人员 */
	private Labor labor;
	
	/** 作业组 */
	private LaborGroup group;
	
	public LaborGroupLabor(){
		
	}
	
	public LaborGroupLabor(Labor labor,LaborGroup group){
		this.labor = labor;
		this.group = group;
	}

	public Labor getLabor() {
		return this.labor;
	}

	public void setLabor(Labor labor) {
		this.labor = labor;
	}

	public LaborGroup getGroup() {
		return this.group;
	}

	public void setGroup(LaborGroup group) {
		this.group = group;
	}
	
	public boolean equals(final Object other){
		if(!(other instanceof LaborGroupLabor))
			return false;
			LaborGroupLabor castOther = (LaborGroupLabor)other;
			return new EqualsBuilder().append(labor, castOther.getLabor()).append(
					group,castOther.getGroup()).isEquals();
	}
	
	public int hashCode(){
		return new HashCodeBuilder().append(labor).append(group).toHashCode();
	}
	
}
