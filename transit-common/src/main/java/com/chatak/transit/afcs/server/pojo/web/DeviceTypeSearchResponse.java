package com.chatak.transit.afcs.server.pojo.web;

import java.util.List;

public class DeviceTypeSearchResponse extends WebResponse{
	
	private static final long serialVersionUID = 1L;

	private List<DeviceTypeSearchRequest> listDeviceType;

	private int totalRecords;

	public List<DeviceTypeSearchRequest> getListDeviceType() {
		return listDeviceType;
	}

	public void setListDeviceType(List<DeviceTypeSearchRequest> listDeviceType) {
		this.listDeviceType = listDeviceType;
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
		builder.append("DeviceTypeSearchResponse [listDeviceType=");
		builder.append(listDeviceType);
		builder.append(", totalRecords=");
		builder.append(totalRecords);
		builder.append("]");
		return builder.toString();
	}


}
