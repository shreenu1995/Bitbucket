package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;

public class WebResponse implements Serializable {

	private static final long serialVersionUID = -1881410074111306427L;

	private String statusCode;
	private String statusMessage;
	private String reservedResponse;

	public String getReservedResponse() {
		return reservedResponse;
	}

	public void setReservedResponse(String reservedResponse) {
		this.reservedResponse = reservedResponse;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	@Override
	public String toString() {
		return "[statusCode=" + statusCode + ", statusMessage=" + statusMessage + "]";
	}
}
