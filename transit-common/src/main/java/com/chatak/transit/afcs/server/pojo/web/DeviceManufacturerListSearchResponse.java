package com.chatak.transit.afcs.server.pojo.web;

import java.util.List;

public class DeviceManufacturerListSearchResponse extends WebResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8974587699925018095L;

	private List<DeviceManufacturerSearchResponse> listDeviceManuFacturer;

	private List<DeviceManufacturerRegistrationRequest> listViewDeviceManuFacturer;

	private int totalRecords;

	private String status;
	
	public List<DeviceManufacturerRegistrationRequest> getListViewDeviceManuFacturer() {
		return listViewDeviceManuFacturer;
	}

	public void setListViewDeviceManuFacturer(
			List<DeviceManufacturerRegistrationRequest> listViewDeviceManuFacturer) {
		this.listViewDeviceManuFacturer = listViewDeviceManuFacturer;
	}

	public List<DeviceManufacturerSearchResponse> getListDeviceManuFacturer() {
		return listDeviceManuFacturer;
	}

	public void setListDeviceManuFacturer(List<DeviceManufacturerSearchResponse> listDeviceManuFacturer) {
		this.listDeviceManuFacturer = listDeviceManuFacturer;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DeviceManufacturerListSearchResponse [listDeviceManuFacturer=");
		builder.append(listDeviceManuFacturer);
		builder.append(", listViewDeviceManuFacturer=");
		builder.append(listViewDeviceManuFacturer);
		builder.append(", totalRecords=");
		builder.append(totalRecords);
		builder.append(", status=");
		builder.append(status);
		builder.append("]");
		return builder.toString();
	}

}
