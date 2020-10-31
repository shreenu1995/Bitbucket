package com.chatak.transit.afcs.server.pojo.web;

public class OrganizationRegistrationResponse extends WebResponse {

	private static final long serialVersionUID = -3770506109499759658L;

	private String organizationId;

	private String organizationName;

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

}
