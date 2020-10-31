package com.chatak.transit.afcs.server.pojo.web;

import java.util.List;

public class RoleResponse  extends WebResponse {

	private List<RoleRequest> roleList;

	public List<RoleRequest> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<RoleRequest> roleList) {
		this.roleList = roleList;
	}

}
