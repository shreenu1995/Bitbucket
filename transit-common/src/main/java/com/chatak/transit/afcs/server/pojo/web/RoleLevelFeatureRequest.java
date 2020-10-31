package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;

public class RoleLevelFeatureRequest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String roleLevel;

	public String getRoleLevel() {
		return roleLevel;
	}

	public void setRoleLevel(String roleLevel) {
		this.roleLevel = roleLevel;
	}

}
