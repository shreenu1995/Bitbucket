package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;

public class RoleRegistrationResponse extends WebResponse implements Serializable {

	private static final long serialVersionUID = -5947650371593599907L;

	private String reserved;

	public String getReserved() {
		return reserved;
	}

	public void setReserved(String reserved) {
		this.reserved = reserved;
	}

	@Override
	public String toString() {

		StringBuilder response = new StringBuilder();

		return response.append("RoleRegistrationResponse [reserved=").append(reserved).append(", getStatusCode()=")
				.append(getStatusCode()).append(", getStatusMessage()=").append(getStatusMessage()).append("]")
				.toString();

	}

}
