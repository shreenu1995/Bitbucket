package com.chatak.transit.afcs.server.pojo.web;

import java.util.List;

public class OrganizationSearchResponse extends WebResponse {

	private static final long serialVersionUID = -5114428012729346097L;

	private List<OrganizationSearchRequest> organizationList;

	private int totalRecords;

	private String organizationName;

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public List<OrganizationSearchRequest> getOrganizationList() {
		return organizationList;
	}

	public void setOrganizationList(List<OrganizationSearchRequest> organizationList) {
		this.organizationList = organizationList;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

}
