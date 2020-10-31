package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;

public class DepotRegistrationResponse extends WebResponse implements Serializable {

	private static final long serialVersionUID = -1881410074111306427L;

	private String depotId;

	public String getDepotId() {
		return depotId;
	}

	public void setDepotId(String depotId) {
		this.depotId = depotId;
	}

	@Override
	public String toString() {

		StringBuilder response = new StringBuilder();
		return response.append("DepotRegistrationResponse [depotId=").append(depotId).append(", getStatusCode()=")
				.append(getStatusCode()).append(", getStatusMessage()=").append(getStatusMessage()).append("]")
				.toString();

	}

}
