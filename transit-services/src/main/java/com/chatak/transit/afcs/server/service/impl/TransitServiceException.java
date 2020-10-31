package com.chatak.transit.afcs.server.service.impl;

import java.util.List;

public class TransitServiceException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2446109897083994416L;
	private final String errorCode;
	private final transient List<Object> errorParams;
	private final String message;

	public TransitServiceException(Exception ex, String errorCode,
			String errorMessage, List<Object> errorParams) {
		initCause(ex);
		this.errorCode = errorCode;
		this.message = errorMessage;
		this.errorParams = errorParams;
	}
	
	public TransitServiceException(String errorCode, String errorMessage,
			List<Object> errorParams) {
		this.errorCode = errorCode;
		this.message = errorMessage;
		this.errorParams = errorParams;
	}
	
	public String getErrorCode() {
		return errorCode;
	}

	public List<Object> getErrorParams() {
		return errorParams;
	}

	@Override
	public String getMessage() {
		return message;
	}

}
