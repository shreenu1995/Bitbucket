package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;
import java.util.List;

public class DeviceOnboardingResponse extends WebResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8195196884866002482L;

	private List<DeviceTypeListViewRequest> deviceTypeListView;

	public List<DeviceTypeListViewRequest> getDeviceTypeListView() {
		return deviceTypeListView;
	}

	public void setDeviceTypeListView(List<DeviceTypeListViewRequest> deviceTypeListView) {
		this.deviceTypeListView = deviceTypeListView;
	}

	private int deviceOnboardId;

	public int getDeviceOnboardId() {
		return deviceOnboardId;
	}

	public void setDeviceOnboardId(int deviceOnboardId) {
		this.deviceOnboardId = deviceOnboardId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DeviceOnboardingResponse [deviceTypeListView=");
		builder.append(deviceTypeListView);
		builder.append(", deviceOnboardId=");
		builder.append(deviceOnboardId);
		builder.append("]");
		return builder.toString();
	}

}
