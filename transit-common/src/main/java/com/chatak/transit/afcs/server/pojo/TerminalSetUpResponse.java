/**
 * @author Girmiti Software
 *
 */
package com.chatak.transit.afcs.server.pojo;

public class TerminalSetUpResponse extends HttResponse {

	private static final long serialVersionUID = -1881410074111306427L;

	private String ptoOperationId;
	private String deviceId;
	private String reserverd;
	private String checksum;
	private String ptoName;

	public String getPtoOperationId() {
		return ptoOperationId;
	}

	public void setPtoOperationId(String ptoOperationId) {
		this.ptoOperationId = String.format("%-12s", ptoOperationId);
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = String.format("%-12s", deviceId);
	}

	public String getReserverd() {
		return reserverd;
	}

	public void setReserverd(String reserverd) {
		this.reserverd = String.format("%-26s", reserverd);
	}

	public String getChecksum() {
		return checksum;
	}

	public void setChecksum(String checksum) {
		this.checksum = String.format("%-1s", checksum);
	}

	public String getPtoName() {
		ptoName=String.format("%-25s", ptoName);
		return ptoName;
	}

	public void setPtoName(String ptoName) {
		this.ptoName = ptoName;
	}

	@Override
	public String toString() {

		StringBuilder response = new StringBuilder();
		return response.append(getStatusCode()).append(getStatusMessage()).append(getPtoName()).append(getPtoOperationId())
				.append(getDeviceId()).append(getReserverd()).append(getChecksum()).toString();
	}
}
