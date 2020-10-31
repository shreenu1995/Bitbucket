package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;

public class FareRegistrationResponse extends WebResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long fareCode;

	public Long getFareCode() {
		return fareCode;
	}

	public void setFareCode(Long fareCode) {
		this.fareCode = fareCode;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FareRegistrationResponse [fareCode=");
		builder.append(fareCode);
		builder.append(", getStatusCode()=");
		builder.append(getStatusCode());
		builder.append(", getStatusMessage()=");
		builder.append(getStatusMessage());
		builder.append("]");
		return builder.toString();
	}
}
