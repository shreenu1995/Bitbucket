package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;

public class DeviceModelSearchData extends DeviceManufacturerSearchData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6160342411060193293L;
	private String deviceModel;

	public String getDeviceModel() {
		return deviceModel;
	}

	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DeviceModelSearchData [deviceModel=");
		builder.append(deviceModel);
		builder.append("]");
		return builder.toString();
	}
	
}
