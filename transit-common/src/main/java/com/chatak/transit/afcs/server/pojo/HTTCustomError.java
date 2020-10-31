package com.chatak.transit.afcs.server.pojo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chatak.transit.afcs.server.constants.ServerConstants;

public class HTTCustomError {
	private static Logger logger = LoggerFactory.getLogger(HTTCustomError.class);

	public static String getError(String request, String code, String message) {
		HttResponse response = new HttResponse();
		if (request != null) {
			try {
				Long.parseLong(request);
				return ServerConstants.STATUS;
			} catch (Exception e) {
				logger.error("Exception", e);
				response.setStatusCode(code);
				response.setStatusMessage(message);
				return response.toString();
			}
		}
		return message;
	}

	public static String getErrorEmpty(String request, String code, String message) {
		HttResponse response = new HttResponse();
		if (request.isEmpty()) {
			response.setStatusCode(code);
			response.setStatusMessage(message);
			return response.toString();
		}
		return message;
	}

	private HTTCustomError() {
		super();
	}
}
