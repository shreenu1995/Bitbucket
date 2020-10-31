package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;

public class DeviceManufacturerRegistrationResponse extends WebResponse implements Serializable {

	private static final long serialVersionUID = 5199707997711564586L;

	private Long deviceManufacturerCode;

	public Long getDeviceManufacturerCode() {
		return deviceManufacturerCode;
	}

	public void setDeviceManufacturerCode(Long deviceManufacturerCode) {
		this.deviceManufacturerCode = deviceManufacturerCode;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DeviceManufacturerRegistrationResponse [deviceManufacturerCode=");
		builder.append(deviceManufacturerCode);
		builder.append(", getStatusCode()=");
		builder.append(getStatusCode());
		builder.append(", getStatusMessage()=");
		builder.append(getStatusMessage());
		builder.append("]");
		return builder.toString();
	}
	
}
