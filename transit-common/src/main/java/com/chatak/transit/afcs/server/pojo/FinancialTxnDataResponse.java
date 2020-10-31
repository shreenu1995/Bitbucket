package com.chatak.transit.afcs.server.pojo;

import java.io.Serializable;

public class FinancialTxnDataResponse extends HttResponse implements Serializable {

	private static final long serialVersionUID = -6556962830617836040L;

	private String checksum;

	private String reservedResponse;

	public String getChecksum() {
		return checksum;
	}

	public void setChecksum(String checksum) {
		this.checksum = String.format("%-1s", checksum);
	}

	public String getReservedResponse() {
		return reservedResponse;
	}

	public void setReservedResponse(String reservedResponse) {
		this.reservedResponse = String.format("%-75s", reservedResponse);
	}

	@Override
	public String toString() {

		StringBuilder response = new StringBuilder();

		return response.append(getStatusCode()).append(getStatusMessage()).append(getReservedResponse())
				.append(getChecksum()).toString();

	}

}
