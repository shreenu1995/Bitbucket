package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;
import java.util.List;

public class DeviceModelResponse extends WebResponse implements Serializable {


	private static final long serialVersionUID = -8768033083641218908L;

	private Long deviceId;

	private List<DeviceTypeListViewRequest> deviceTypeListView;

	private List<DeviceManuFacturerListViewResponse> listDeviceManuFacturer;

	private String totalRecords;

	public String getTotalRecords() {
		return totalRecords;
	}

	public List<DeviceManuFacturerListViewResponse> getListDeviceManuFacturer() {
		return listDeviceManuFacturer;
	}

	public void setListDeviceManuFacturer(List<DeviceManuFacturerListViewResponse> listDeviceManuFacturer) {
		this.listDeviceManuFacturer = listDeviceManuFacturer;
	}

	public void setTotalRecords(String totalRecords) {
		this.totalRecords = totalRecords;
	}

	public List<DeviceTypeListViewRequest> getDeviceTypeListView() {
		return deviceTypeListView;
	}

	public void setDeviceTypeListView(List<DeviceTypeListViewRequest> deviceTypeListView) {
		this.deviceTypeListView = deviceTypeListView;
	}

	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DeviceModelResponse [deviceId=");
		builder.append(deviceId);
		builder.append(", deviceTypeListView=");
		builder.append(deviceTypeListView);
		builder.append(", listDeviceManuFacturer=");
		builder.append(listDeviceManuFacturer);
		builder.append(", totalRecords=");
		builder.append(totalRecords);
		builder.append("]");
		return builder.toString();
	}

}
