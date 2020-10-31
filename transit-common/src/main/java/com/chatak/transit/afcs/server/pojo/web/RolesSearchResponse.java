package com.chatak.transit.afcs.server.pojo.web;

import java.util.List;

public class RolesSearchResponse extends WebResponse {

	private static final long serialVersionUID = 1L;

	private int totalNoOfRecords;
	private List<RoleRequest> listOfAfcsRole;

	public List<RoleRequest> getListOfAfcsRole() {
		return listOfAfcsRole;
	}

	public void setListOfAfcsRole(List<RoleRequest> listOfAfcsRole) {
		this.listOfAfcsRole = listOfAfcsRole;
	}

	public int getTotalNoOfRecords() {
		return totalNoOfRecords;
	}

	public void setTotalNoOfRecords(int totalNoOfRecords) {
		this.totalNoOfRecords = totalNoOfRecords;
	}

}
