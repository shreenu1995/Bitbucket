package com.chatak.transit.afcs.server.pojo.web;

import java.util.List;

public class DeviceTypeListViewResponse extends WebResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5102571227276538818L;
	
	private List<DeviceTypeListViewRequest> deviceTypeListView;
	
	public List<DeviceTypeListViewRequest> getDeviceTypeListView() {
		return deviceTypeListView;
	}

	public void setDeviceTypeListView(List<DeviceTypeListViewRequest> deviceTypeListView) {
		this.deviceTypeListView = deviceTypeListView;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DeviceTypeListViewResponse [DeviceTypeListView=");
		builder.append(deviceTypeListView);
		builder.append(", getStatusCode()=");
		builder.append(getStatusCode());
		builder.append(", getStatusMessage()=");
		builder.append(getStatusMessage());
		builder.append("]");
		return builder.toString();
	}

}
