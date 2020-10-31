package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;

public class UserStatusUpdateResponse extends WebResponse implements Serializable {

	private static final long serialVersionUID = -3536097436553375077L;

	private String userName;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {

		StringBuilder response = new StringBuilder();

		return response.append("UserStatusUpdateResponse [reservedResponse=").append(getReservedResponse())
				.append(", getStatusCode()=").append(getStatusCode()).append(", getStatusMessage()=")
				.append(getStatusMessage()).append("]").toString();

	}

}
