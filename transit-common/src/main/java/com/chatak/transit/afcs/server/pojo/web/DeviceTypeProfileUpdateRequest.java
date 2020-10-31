package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;

public class DeviceTypeProfileUpdateRequest implements Serializable {

	private static final long serialVersionUID = -8320594170169936246L;

	private Long deviceTypeId;

	private String deviceTypeName;

	private String description;

	public Long getDeviceTypeId() {
		return deviceTypeId;
	}

	public void setDeviceTypeId(Long deviceTypeId) {
		this.deviceTypeId = deviceTypeId;
	}

	public String getDeviceTypeName() {
		return deviceTypeName;
	}

	public void setDeviceTypeName(String deviceTypeName) {
		this.deviceTypeName = deviceTypeName;
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
		builder.append("DeviceTypeProfileUpdateRequest [deviceTypeCode=");
		builder.append(deviceTypeId);
		builder.append(", deviceTypeName=");
		builder.append(deviceTypeName);
		builder.append(", description=");
		builder.append(description);
		builder.append("]");
		return builder.toString();
	}

}
