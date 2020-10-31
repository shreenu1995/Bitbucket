package com.chatak.transit.afcs.server.pojo.web;

import java.util.List;

public class DeviceModelListResponse  extends WebResponse{

	private static final long serialVersionUID = 3315029281650506663L;

	private List<DeviceModelSearchResponse> listDeviceModel;

	private int totalRecords;

	private String status;

	public List<DeviceModelSearchResponse> getListDeviceModel() {
		return listDeviceModel;
	}

	public void setListDeviceModel(List<DeviceModelSearchResponse> listDeviceModel) {
		this.listDeviceModel = listDeviceModel;
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
		builder.append("DeviceModelListResponse [listDeviceModel=");
		builder.append(listDeviceModel);
		builder.append(", totalRecords=");
		builder.append(totalRecords);
		builder.append(", status=");
		builder.append(status);
		builder.append("]");
		return builder.toString();
	}
	
}
