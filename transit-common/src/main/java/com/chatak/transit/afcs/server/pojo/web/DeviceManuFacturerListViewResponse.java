package com.chatak.transit.afcs.server.pojo.web;

import java.util.List;

public class DeviceManuFacturerListViewResponse extends WebResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3146828440420553455L;

	private List<DeviceManufacturerRegistrationRequest> listViewDeviceManuFacturer;

	private List<DeviceManufacturerSearchResponse> listDeviceManuFacturer;

	private String status;

	private int totalRecords;

	public List<DeviceManufacturerSearchResponse> getListDeviceManuFacturer() {
		return listDeviceManuFacturer;
	}

	public void setListDeviceManuFacturer(List<DeviceManufacturerSearchResponse> listDeviceManuFacturer) {
		this.listDeviceManuFacturer = listDeviceManuFacturer;
	}

	public List<DeviceManufacturerRegistrationRequest> getListViewDeviceManuFacturer() {
		return listViewDeviceManuFacturer;
	}

	public void setListViewDeviceManuFacturer(List<DeviceManufacturerRegistrationRequest> listViewDeviceManuFacturer) {
		this.listViewDeviceManuFacturer = listViewDeviceManuFacturer;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DeviceManufacturerListSearchResponse [listDeviceManuFacturer=");
		builder.append(listDeviceManuFacturer);
		builder.append(", totalRecords=");
		builder.append(totalRecords);
		builder.append(", listViewDeviceManuFacturer=");
		builder.append(listViewDeviceManuFacturer);
		builder.append(", status=");
		builder.append(status);
		builder.append("]");
		return builder.toString();
	}

}
