package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;
import java.sql.Timestamp;

public class DeviceSearchData implements Serializable {

	private static final long serialVersionUID = 1L;

	private String deviceId;

	private String stationCode;

	private String deviceModelNumber;

	private String deviceSerialNumber;

	private String additionalData;

	private String updateBy;

	private String description;

	private Timestamp createdDate;

	private Timestamp updatedDate;

	private int pageIndex;

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getStationCode() {
		return stationCode;
	}

	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}

	public String getDeviceModelNumber() {
		return deviceModelNumber;
	}

	public void setDeviceModelNumber(String deviceModelNumber) {
		this.deviceModelNumber = deviceModelNumber;
	}

	public String getDeviceSerialNumber() {
		return deviceSerialNumber;
	}

	public void setDeviceSerialNumber(String deviceSerialNumber) {
		this.deviceSerialNumber = deviceSerialNumber;
	}

	public String getAdditionalData() {
		return additionalData;
	}

	public void setAdditionalData(String additionalData) {
		this.additionalData = additionalData;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
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
		builder.append("DeviceSearchData [deviceId=");
		builder.append(deviceId);
		builder.append(", stationCode=");
		builder.append(stationCode);
		builder.append(", deviceModelNumber=");
		builder.append(deviceModelNumber);
		builder.append(", deviceSerialNumber=");
		builder.append(deviceSerialNumber);
		builder.append(", additionalData=");
		builder.append(additionalData);
		builder.append(", updateBy=");
		builder.append(updateBy);
		builder.append(", createdDate=");
		builder.append(createdDate);
		builder.append(", updatedDate=");
		builder.append(updatedDate);
		builder.append(", pageIndex=");
		builder.append(pageIndex);
		builder.append("]");
		return builder.toString();
	}

}
