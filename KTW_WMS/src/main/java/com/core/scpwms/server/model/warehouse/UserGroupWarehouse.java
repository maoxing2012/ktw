package com.core.scpwms.server.model.warehouse;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.core.business.model.security.Group;
import com.core.db.server.model.Entity;

/**
 * 仓库用户组授权类
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 *
 */
@SuppressWarnings("all")
public class UserGroupWarehouse extends Entity {
	
	/** 描述 */
	private String description;
	
	/** 用户组 */
	private Group userGroup;
	
	/** 仓库 */
	private Warehouse warehouse;
	
	protected UserGroupWarehouse(){
		
	}
	
	public UserGroupWarehouse(Group userGroup,Warehouse warehouse,String description){
		this.userGroup = userGroup;
		this.warehouse = warehouse;
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Warehouse getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}

	public boolean equals(final Object other) {
		if (!(other instanceof UserGroupWarehouse))
			return false;
		UserGroupWarehouse castOther = (UserGroupWarehouse) other;
		return new EqualsBuilder().append(userGroup, castOther.getUserGroup()).append(
				warehouse, castOther.getWarehouse()).isEquals();
	}
	public int hashCode() {
		return new HashCodeBuilder().append(userGroup).append(warehouse)
				.toHashCode();
	}

	public Group getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(Group userGroup) {
		this.userGroup = userGroup;
	}
}
