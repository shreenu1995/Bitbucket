package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;

public class DeviceModelSearchRequest implements Serializable {

	private static final long serialVersionUID = -6599420579003603787L;

	private String deviceTypeName;

	private String deviceId;

	private String deviceManufacturer;

	private String deviceModel;

	private int indexPage;

	private String status;

	private Long deviceManufacturerCode;

	private Long deviceTypeId;

	private Long deviceModelId;
	
	private int pageSize;

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public Long getDeviceTypeId() {
		return deviceTypeId;
	}

	public void setDeviceTypeId(Long deviceTypeId) {
		this.deviceTypeId = deviceTypeId;
	}

	public Long getDeviceModelId() {
		return deviceModelId;
	}

	public void setDeviceModelId(Long deviceModelId) {
		this.deviceModelId = deviceModelId;
	}

	public Long getDeviceManufacturerCode() {
		return deviceManufacturerCode;
	}

	public void setDeviceManufacturerCode(Long deviceManufacturerCode) {
		this.deviceManufacturerCode = deviceManufacturerCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
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

	public int getIndexPage() {
		return indexPage;
	}

	public void setIndexPage(int indexPage) {
		this.indexPage = indexPage;

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
		builder.append("DeviceModelSearchRequest [deviceTypeName=");
		builder.append(deviceTypeName);
		builder.append(", deviceId=");
		builder.append(deviceId);
		builder.append(", deviceManufacturer=");
		builder.append(deviceManufacturer);
		builder.append(", deviceModel=");
		builder.append(deviceModel);
		builder.append(", indexPage=");
		builder.append(indexPage);
		builder.append(", status=");
		builder.append(status);
		builder.append("]");
		return builder.toString();
	}

}