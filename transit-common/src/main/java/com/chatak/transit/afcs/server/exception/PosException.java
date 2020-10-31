/**
 * @author Girmiti Software
 */
package com.chatak.transit.afcs.server.exception;

@SuppressWarnings("serial")
public class PosException extends Exception {

	private final String httpErrorCode;

	private final int statusCode;

	public PosException(String httpErrorCode, int statusCode) {
		super();
		this.httpErrorCode = httpErrorCode;
		this.statusCode = statusCode;
	}

	public String getHttpErrorCode() {
		return httpErrorCode;
	}

	public int getStatusCode() {
		return statusCode;
	}

}
