package com.chatak.transit.afcs.server.exception;

@SuppressWarnings("serial")
public class AfcsHttpClientException extends Exception{
	
	private final String errorCode;
	
	private final int statusCode;

	public AfcsHttpClientException(String httpErrorCode, int statusCode) {
		super();
		errorCode = httpErrorCode;
		this.statusCode = statusCode;
	}

	public String getHttpErrorCode() {
		return errorCode;
	}

	public int getStatusCode() {
		return statusCode;
	}

}
