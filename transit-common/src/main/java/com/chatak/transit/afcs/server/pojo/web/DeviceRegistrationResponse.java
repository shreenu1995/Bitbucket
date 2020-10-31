package com.chatak.transit.afcs.server.pojo.web;

public class DeviceRegistrationResponse extends WebResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1638431835284323042L;

	private String deviceID;

	public String getDeviceID() {
		return deviceID;
	}

	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DeviceRegistrationResponse [deviceID=");
		builder.append(deviceID);
		builder.append(", StatusCode=");
		builder.append(getStatusCode());
		builder.append(", StatusMessage=");
		builder.append(getStatusMessage());
		builder.append("]");
		return builder.toString();
	}

	
}
