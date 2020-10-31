package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;

public class DeviceManufacturerSearchData extends DeviceTypeSearchRequest implements Serializable {

	private static final long serialVersionUID = 8389752548238356389L;
	
	private Long deviceManufacturerCode;
	
	private String deviceManufacturer;

	private String deviceTypeName;

	private String description;
	
	@Override
	public String getDeviceTypeName() {
		return deviceTypeName;
	}

	@Override
	public void setDeviceTypeName(String deviceTypeName) {
		this.deviceTypeName = deviceTypeName;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	public Long getDeviceManufacturerCode() {
		return deviceManufacturerCode;
	}

	public void setDeviceManufacturerCode(Long deviceManufacturerCode) {
		this.deviceManufacturerCode = deviceManufacturerCode;
	}

	public String getDeviceManufacturer() {
		return deviceManufacturer;
	}

	public void setDeviceManufacturer(String deviceManufacturer) {
		this.deviceManufacturer = deviceManufacturer;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DeviceManufacturerSearchData [deviceManufacturerCode=");
		builder.append(deviceManufacturerCode);
		builder.append(", description=");
		builder.append(description);
		builder.append(", deviceManufacturer=");
		builder.append(deviceManufacturer);
		builder.append(", deviceTypeName=");
		builder.append(deviceTypeName);
		builder.append("]");
		return builder.toString();
	}

}
