package com.chatak.transit.afcs.server.pojo.web;

import java.util.List;

public class DeviceOnboardListSearchResponse extends WebResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<DeviceOnboardSearchResponse> listDeviceOnboard;

	private int totalRecords;

	public List<DeviceOnboardSearchResponse> getListDeviceOnboard() {
		return listDeviceOnboard;
	}

	public void setListDeviceOnboard(List<DeviceOnboardSearchResponse> listDeviceOnboard) {
		this.listDeviceOnboard = listDeviceOnboard;
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
		builder.append("DeviceOnboardListSearchResponse [listDeviceOnboard=");
		builder.append(listDeviceOnboard);
		builder.append(", totalRecords=");
		builder.append(totalRecords);
		builder.append("]");
		return builder.toString();
	}
	
}
