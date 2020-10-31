package com.chatak.transit.afcs.server.pojo;

import java.io.Serializable;

public class AdminSessionResponse extends HttResponse implements Serializable {

	private static final long serialVersionUID = -1881410074111306427L;

	private String checkSum;

	private String reservedResp;

	public String getCheckSum() {
		return checkSum;
	}

	public void setCheckSum(String checkSum) {
		this.checkSum = String.format("%-1s", checkSum);
	}

	public String getReservedResp() {
		return reservedResp;
	}

	public void setReservedResp(String reservedResp) {
		this.reservedResp = String.format("%-75s", reservedResp);
	}

	@Override
	public String toString() {

		StringBuilder response = new StringBuilder();
		return response.append(getStatusCode()).append(getStatusMessage()).append(reservedResp).append(checkSum)
				.toString();

	}

}
