package com.chatak.transit.afcs.server.pojo.web;

import java.util.List;

public class DeviceTrackingResponse extends WebResponse{
	
	private static final long serialVersionUID = -6903014104997345203L;

	private List<DeviceTrackingRequest> deviceTrackingRequest;

	private int noOfRecords;

	public List<DeviceTrackingRequest> getDeviceTrackingRequest() {
		return deviceTrackingRequest;
	}

	public void setDeviceTrackingRequest(List<DeviceTrackingRequest> deviceTrackingRequest) {
		this.deviceTrackingRequest = deviceTrackingRequest;
	}

	public int getNoOfRecords() {
		return noOfRecords;
	}

	public void setNoOfRecords(int noOfRecords) {
		this.noOfRecords = noOfRecords;
	}
	
	
}
