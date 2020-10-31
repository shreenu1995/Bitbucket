package com.chatak.transit.afcs.server.pojo.web;

import java.util.List;

public class DeviceSearchResponse extends WebResponse {

	private static final long serialVersionUID = 1L;

	private transient List<DeviceSearchData> deviceListResponse;
	
	private int noOfRecords;

	public List<DeviceSearchData> getDeviceListResponse() {
		return deviceListResponse;
	}

	public void setDeviceListResponse(List<DeviceSearchData> deviceListResponse) {
		this.deviceListResponse = deviceListResponse;
	}

	public int getNoOfRecords() {
		return noOfRecords;
	}

	public void setNoOfRecords(int noOfRecords) {
		this.noOfRecords = noOfRecords;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DeviceSearchResponse [deviceListResponse=");
		builder.append(deviceListResponse);
		builder.append(", noOfRecords=");
		builder.append(noOfRecords);
		builder.append("]");
		return builder.toString();
	}
	
}
