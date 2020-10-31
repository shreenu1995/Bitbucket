package com.chatak.transit.afcs.server.enums;

public enum HttpErrorCodes {
	CONTINUE(100, "Continue"),
	SWITCHING_PROTOCOLS(101, "Switching Protocols"),
	OK(200, "Ok"),
	CREATED(201, "No Content"),
	ACCEPTED(202, "Accepted"),
	NON_AUTENTICATIVE_INFO(203, "Non-Authoritative Information"),
	NO_CONTENT(204, "No Content"),
	RESET_CONTENT(205, "Reset Content"),
	PARTIAL_CONTENT(206, "Partial Content"),
	MULTIPLE_CHOICES(300, "Multiple Choices"),
	MOVED_PERMANENTLY(301, "Moved Permanently"),
	FOUND(302, "Found"),
	SEE_OTHER(303, "See Other"),
	NOT_MODIFIED(304, "Not Modified"),
	TEMPORARY_REDIRECT(307, "Temporary Redirect"),
	PERMANENT_REDIRECT(308, "Permanent Redirect"),
	BAD_REQUEST(400, "Bad Request"),
	UNAUTHORIZED(401, "Unauthorized"),
	FORBIDDEN(403, "Forbidden"),
	NOT_FOUND(404, "Not Found"),
	METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
	NOT_ACCEPTABLE(406, "Not Acceptable"),
	PROXY_AUTHENTICATION_REQUIRED(407, "Proxy Authentication Required"),
	REQUEST_TIMEOUT(408, "Request Timeout"),
	CONFLICT(409, "Conflict"),
	GONE(410, "Gone"),
	LENGTH_REQUIRED(411, "Length Required"),
	PRECONDITION_FAILED(412, "Precondition Failed"),
	PAYLOAD_TOO_LARGE(413, "Payload Too Large"),
	URI_TOO_LONG(414, "URI Too Long"),
	UNSUPPORTED_MEDIA_TYPE(415, "Unsupported Media Type"),
	RANGE_NOT_SATISFIABLE(416, "Range Not Satisfiable"),
	EXPECTATION_FAILED(417, "Expectation Failed"),
	I_AM_A_TEAPOT(418, "I'm a teapot"),
	UNPROCESSABLE_ENTITY(422, "Unprocessable Entity"),
	UPGRADE_REQUIRED(426, "Upgrade Required"),
	PRECONDITION_REQUIRED(428, "Precondition Required"),
	TOO_MANY_REQUESTS(429, "Too Many Requests"),
	REQUEST_HEADER_FIELD_TOO_LARGE(431, "Request Header Fields Too Large"),
	UNAVAILAVLE_FOR_LEGAL_REASON(451, "Unavailable For Legal Reasons"),
	INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
	NOT_IMPLEMENTED(501, "Not Implemented"),
	BAD_GATEWAY(502, "Bad Gateway"),
	SERVICE_UNAVAILABLE(503, "Service Unavailable"),
	GATEWAY_TIMEOUT(504, "Gateway Timeout"),
	HTTP_VERSION_NOT_SUPPORTED(505, "HTTP Version Not Supported"),
	NETWORK_AUTHENTICATION_REQUIRED(511, "Network Authentication Required");

	private int errorCode;
	private String errorMsg;

	HttpErrorCodes(int errorCode, String errorMsg) {
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	public int getHttpErrorCode() {
		return this.errorCode;
	}

	public String getHttpErrorMsg() {
		return this.errorMsg;
	}

	public static HttpErrorCodes getHttpErrorCode(int errorCode) {
		HttpErrorCodes[] allHttpErrorCodes = HttpErrorCodes.values();
		for (int i = 0, length = allHttpErrorCodes.length; i < length; i++) {
			if (allHttpErrorCodes[i].getHttpErrorCode() == errorCode)
				return allHttpErrorCodes[i];
		}
		return null;
	}

	public static HttpErrorCodes getHttpErrorMsg(String errorMsg) {
		HttpErrorCodes[] allHttpErrorCodes = HttpErrorCodes.values();
		for (int i = 0, length = allHttpErrorCodes.length; i < length; i++) {
			if (allHttpErrorCodes[i].getHttpErrorMsg().equalsIgnoreCase(errorMsg))
				return allHttpErrorCodes[i];
		}
		return null;
	}
}