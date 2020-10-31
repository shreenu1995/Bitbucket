package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;

public class DeviceManufacturerSearchResponse extends WebResponse implements Serializable {

	private static final long serialVersionUID = -572482848528080072L;

	private Long deviceManufacturerId;

	private String deviceTypeName;

	private String deviceManufacturer;

	private String description;

	private String status;

	private Long deviceTypeId;

	public Long getDeviceTypeId() {
		return deviceTypeId;
	}

	public void setDeviceTypeId(Long deviceTypeId) {
		this.deviceTypeId = deviceTypeId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDeviceTypeName() {
		return deviceTypeName;
	}

	public void setDeviceTypeName(String deviceTypeName) {
		this.deviceTypeName = deviceTypeName;
	}

	public Long getDeviceManufacturerId() {
		return deviceManufacturerId;
	}

	public void setDeviceManufacturerId(Long deviceManufacturerId) {
		this.deviceManufacturerId = deviceManufacturerId;
	}

	public String getDeviceManufacturer() {
		return deviceManufacturer;
	}

	public void setDeviceManufacturer(String deviceManufacturer) {
		this.deviceManufacturer = deviceManufacturer;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DeviceManufacturerSearchResponse [deviceManufacturerId=");
		builder.append(deviceManufacturerId);
		builder.append(", deviceTypeName=");
		builder.append(deviceTypeName);
		builder.append(", deviceManufacturer=");
		builder.append(deviceManufacturer);
		builder.append(", description=");
		builder.append(description);
		builder.append(", status=");
		builder.append(status);
		builder.append("]");
		return builder.toString();
	}

}
