package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;

public class DeviceModelSearchResponse implements Serializable {

	private static final long serialVersionUID = -7592210976576253386L;

	private Long deviceId;

	private String deviceManufacturer;

	private String deviceTypeName;

	private String deviceModel;

	private String deviceIMEINumber;

	private String description;

	private String status;

	private Long deviceTypeId;

	private Long deviceManufacturerId;

	public Long getDeviceTypeId() {
		return deviceTypeId;
	}

	public void setDeviceTypeId(Long deviceTypeId) {
		this.deviceTypeId = deviceTypeId;
	}

	public Long getDeviceManufacturerId() {
		return deviceManufacturerId;
	}

	public void setDeviceManufacturerId(Long deviceManufacturerId) {
		this.deviceManufacturerId = deviceManufacturerId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceManufacturer() {
		return deviceManufacturer;
	}

	public void setDeviceManufacturer(String deviceManufacturer) {
		this.deviceManufacturer = deviceManufacturer;
	}

	public String getDeviceModel() {
		return deviceModel;
	}

	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}

	public String getDeviceIMEINumber() {
		return deviceIMEINumber;
	}

	public void setDeviceIMEINumber(String deviceIMEINumber) {
		this.deviceIMEINumber = deviceIMEINumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDeviceTypeName() {
		return deviceTypeName;
	}

	public void setDeviceTypeName(String deviceTypeName) {
		this.deviceTypeName = deviceTypeName;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DeviceModelSearchResponse [deviceId=");
		builder.append(deviceId);
		builder.append(", deviceManufacturer=");
		builder.append(deviceManufacturer);
		builder.append(", deviceTypeName=");
		builder.append(deviceTypeName);
		builder.append(", deviceModel=");
		builder.append(deviceModel);
		builder.append(", deviceIMEINumber=");
		builder.append(deviceIMEINumber);
		builder.append(", description=");
		builder.append(description);
		builder.append(", status=");
		builder.append(status);
		builder.append("]");
		return builder.toString();
	}

}
