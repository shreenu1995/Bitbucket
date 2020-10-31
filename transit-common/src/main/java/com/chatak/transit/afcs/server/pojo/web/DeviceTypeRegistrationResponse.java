package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;

public class DeviceTypeRegistrationResponse extends WebResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long deviceTypeId;

	public Long getDeviceTypeId() {
		return deviceTypeId;
	}

	public void setDeviceTypeId(Long deviceTypeId) {
		this.deviceTypeId = deviceTypeId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DeviceTypeRegistrationResponse [deviceTypeCode=");
		builder.append(deviceTypeId);
		builder.append(", getStatusCode()=");
		builder.append(getStatusCode());
		builder.append(", getStatusMessage()=");
		builder.append(getStatusMessage());
		builder.append("]");
		return builder.toString();
	}

}