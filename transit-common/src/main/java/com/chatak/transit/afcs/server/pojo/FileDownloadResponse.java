package com.chatak.transit.afcs.server.pojo;

public class FileDownloadResponse extends HttResponse {

	private static final long serialVersionUID = -1881410074111306427L;

	private String reservedResp;
	private String checkSum;

	public String getReservedResp() {
		return reservedResp;
	}

	public void setReservedResp(String reservedResp) {
		this.reservedResp = String.format("%-75s", reservedResp);
	}

	public String getCheckSum() {
		return checkSum;
	}

	public void setCheckSum(String checkSum) {
		this.checkSum = String.format("%-1s", checkSum);
	}

	@Override
	public String toString() {

		StringBuilder response = new StringBuilder();
		return response.append(getStatusCode()).append(getStatusMessage()).append(reservedResp).append(checkSum)
				.toString();

	}

}
