/**
 * @author Girmiti Software
 *
 */
package com.chatak.transit.afcs.server.pojo;

import java.io.Serializable;

public class HttResponse implements Serializable {

	private static final long serialVersionUID = -1881410074111306427L;

	private String statusCode;
	private String statusMessage;

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {

		this.statusCode = String.format("%-8s", statusCode);
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = String.format("%-44s", statusMessage);
	}

	@Override
	public String toString() {

		StringBuilder response = new StringBuilder();
		return response.append(statusCode).append(statusMessage).toString();

	}

}
