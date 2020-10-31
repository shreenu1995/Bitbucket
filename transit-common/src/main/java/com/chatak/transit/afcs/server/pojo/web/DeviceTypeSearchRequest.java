package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;

public class DeviceTypeSearchRequest implements Serializable {

	private static final long serialVersionUID = -2812748334856629985L;

	private String deviceTypeName;

	private String userId;

	private String description;

	private int pageIndex;

	private String status;

	private Long deviceTypeId;
	
	private int pageSize;

	public Long getDeviceTypeId() {
		return deviceTypeId;
	}

	public void setDeviceTypeId(Long deviceTypeId) {
		this.deviceTypeId = deviceTypeId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
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
	
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DeviceTypeSearchRequest [deviceTypeName=");
		builder.append(deviceTypeName);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", description=");
		builder.append(description);
		builder.append(", pageIndex=");
		builder.append(pageIndex);
		builder.append(", deviceTypeId=");
		builder.append(deviceTypeId);
		builder.append(", status=");
		builder.append(status);
		builder.append("]");
		return builder.toString();
	}

}
