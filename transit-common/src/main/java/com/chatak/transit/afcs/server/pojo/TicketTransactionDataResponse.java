/**
 * @author Girmiti Software
 *
 */
package com.chatak.transit.afcs.server.pojo;

public class TicketTransactionDataResponse extends HttResponse {

	private static final long serialVersionUID = -1881410074111306427L;

	private String reservedResponse;
	private String checksum;
	private String errorCode;

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getReservedResponse() {
		return reservedResponse;
	}

	public void setReservedResponse(String reservedResponse) {
		this.reservedResponse = String.format("%-75s", reservedResponse);
	}

	public String getChecksum() {
		return checksum;
	}

	public void setChecksum(String checksum) {
		this.checksum = String.format("%-1s", checksum);
	}

	@Override
	public String toString() {

		StringBuilder response = new StringBuilder();
		return response.append(getStatusCode()).append(getStatusMessage()).append(getReservedResponse())
				.append(getChecksum()).toString();
	}
}
