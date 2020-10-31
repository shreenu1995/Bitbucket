package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;

public class DeviceManufacturerListViewRequest extends DeviceTypeRegistrationRequest implements Serializable {

	private static final long serialVersionUID = -7739318178375001714L;

	private String deviceManufacturer;

	private Long deviceManufacturerId;

	private Long deviceTypeId;

	public Long getDeviceTypeId() {
		return deviceTypeId;
	}

	public void setDeviceTypeId(Long deviceTypeId) {
		this.deviceTypeId = deviceTypeId;
	}

	public String getDeviceManufacturer() {
		return deviceManufacturer;
	}

	public void setDeviceManufacturer(String deviceManufacturer) {
		this.deviceManufacturer = deviceManufacturer;
	}

	public Long getDeviceManufacturerId() {
		return deviceManufacturerId;
	}

	public void setDeviceManufacturerId(Long deviceManufacturerId) {
		this.deviceManufacturerId = deviceManufacturerId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DeviceManufacturerListViewRequest [deviceManufacturer=");
		builder.append(deviceManufacturer);
		builder.append(", deviceManufacturerId=");
		builder.append(deviceManufacturerId);
		builder.append("]");
		return builder.toString();
	}

}