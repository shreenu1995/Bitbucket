package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;

public class SetupSoftwareUpdateResponse extends WebResponse implements Serializable{

	private static final long serialVersionUID = -3536097436553375077L;
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SetupSoftwareUpdateResponse [getReservedResponse()=");
		builder.append(getReservedResponse());
		builder.append(", getStatusCode()=");
		builder.append(getStatusCode());
		builder.append(", getStatusMessage()=");
		builder.append(getStatusMessage());
		builder.append(", toString()=");
		builder.append(super.toString());
		builder.append(", getClass()=");
		builder.append(getClass());
		builder.append(", hashCode()=");
		builder.append(hashCode());
		builder.append("]");
		return builder.toString();
	}
}
