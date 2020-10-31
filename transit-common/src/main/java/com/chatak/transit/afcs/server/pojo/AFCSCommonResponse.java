package com.chatak.transit.afcs.server.pojo;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(value = AccessLevel.PUBLIC)
@Setter(value = AccessLevel.PUBLIC)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AFCSCommonResponse {

	@NotEmpty(message = "StatusCode is required")
	@Size(max = 32)
	private String statusCode;

	@NotEmpty(message = "StatusMessage is required")
	@Size(max = 64)
	private String statusMessage;

	@Size(max = 64)
	private String requestID;

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

	public String getRequestID() {
		return requestID;
	}

	public void setRequestID(String requestID) {
		this.requestID = requestID;
	}

}
