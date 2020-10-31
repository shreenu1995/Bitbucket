package com.chatak.transit.afcs.server.pojo.web;

public class SetupSoftwareRegistrationResponse extends WebResponse {

	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SetupSoftwareRegistrationResponse [getReservedResponse()=");
		builder.append(getReservedResponse());
		builder.append(", getStatusCode()=");
		builder.append(getStatusCode());
		builder.append(", getStatusMessage()=");
		builder.append(getStatusMessage());
		builder.append("]");
		return builder.toString();
	}

}
