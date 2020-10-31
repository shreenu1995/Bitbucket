package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;

public class RouteRegistrationResponse extends WebResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long routeId;

	private String organizationName;
	
	private String ptoName;
	
	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getPtoName() {
		return ptoName;
	}

	public void setPtoName(String ptoName) {
		this.ptoName = ptoName;
	}

	public Long getRouteId() {
		return routeId;
	}

	public void setRouteId(Long routeId) {
		this.routeId = routeId;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RouteRegistrationResponse [routeId=");
		builder.append(routeId);
		builder.append(", getStatusCode()=");
		builder.append(getStatusCode());
		builder.append(", getStatusMessage()=");
		builder.append(getStatusMessage());
		builder.append("]");
		return builder.toString();
	}
}
