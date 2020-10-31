package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;

public class DeviceTypeListViewRequest implements Serializable {

	private static final long serialVersionUID = 1122832533677761636L;
	
	private Long deviceTypeId;
	
	private String deviceTypeName;

	public String getDeviceTypeName() {
		return deviceTypeName;
	}

	public void setDeviceTypeName(String deviceTypeName) {
		this.deviceTypeName = deviceTypeName;
	}

	public Long getDeviceTypeId() {
		return deviceTypeId;
	}

	public void setDeviceTypeId(Long deviceTypeId) {
		this.deviceTypeId = deviceTypeId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DeviceTypeListViewRequest [deviceTypeId=");
		builder.append(deviceTypeId);
		builder.append(", deviceTypeName=");
		builder.append(deviceTypeName);
		builder.append("]");
		return builder.toString();
	}

}
