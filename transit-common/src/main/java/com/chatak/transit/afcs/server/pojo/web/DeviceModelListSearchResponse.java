package com.chatak.transit.afcs.server.pojo.web;

import java.util.List;

public class DeviceModelListSearchResponse extends WebResponse {

	private static final long serialVersionUID = -1817000096488076118L;

	private List<DeviceModelSearchResponse> listDeviceModelSearchResponse;

	private int noOfRecords;

	private String deviceModelName;

	public String getDeviceModelName() {
		return deviceModelName;
	}

	public void setDeviceModelName(String deviceModelName) {
		this.deviceModelName = deviceModelName;
	}

	public List<DeviceModelSearchResponse> getListDeviceModelSearchResponse() {
		return listDeviceModelSearchResponse;
	}

	public void setListDeviceModelSearchResponse(List<DeviceModelSearchResponse> listDeviceModelSearchResponse) {
		this.listDeviceModelSearchResponse = listDeviceModelSearchResponse;
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
		builder.append("DeviceModelListSearchResponse [listDeviceModelSearchResponse=");
		builder.append(listDeviceModelSearchResponse);
		builder.append("]");
		return builder.toString();
	}

}
