package com.chatak.transit.afcs.server.pojo.web;

import java.util.List;

public class DeviceListResponse extends WebResponse {
	private static final long serialVersionUID = 9178605767510131709L;
	private List<DeviceDetails> deviceList;

	public List<DeviceDetails> getDeviceList() {
		return deviceList;
	}

	public void setDeviceList(List<DeviceDetails> deviceList) {
		this.deviceList = deviceList;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DeviceListResponse [deviceList=");
		builder.append(deviceList);
		builder.append(", getStatusCode()=");
		builder.append(getStatusCode());
		builder.append(", getStatusMessage()=");
		builder.append(getStatusMessage());
		builder.append("]");
		return builder.toString();
	}
}
