package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;

public class SoftwareInfoCheckDataResponse extends WebResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private Boolean isExist;

	public Boolean getIsExist() {
		return isExist;
	}

	public void setIsExist(Boolean isExist) {
		this.isExist = isExist;
	}

	@Override
	public String toString() {
		StringBuilder response = new StringBuilder();
		return response.append("SoftwareInfoCheckDataResponse [").append(" getStatusCode()=").append(getStatusCode())
				.append(", getStatusMessage()=").append(getStatusMessage()).append("]").toString();
	}

}
