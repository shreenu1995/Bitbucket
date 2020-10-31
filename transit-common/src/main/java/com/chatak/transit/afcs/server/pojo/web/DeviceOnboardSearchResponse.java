package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;
import java.util.List;

public class DeviceOnboardSearchResponse extends WebResponse implements Serializable {

	private static final long serialVersionUID = 3832636121555539559L;

	private String deviceOnboardId;

	private String organizationName;

	private String ptoName;

	private String deviceTypeName;

	private String deviceManufacturer;

	private String deviceModel;

	private String status;

	private Long organizationId;

	private Long ptoId;

	private Long deviceTypeId;

	private Long deviceManufacturerId;

	private Long deviceModelId;

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

	public Long getDeviceModelId() {
		return deviceModelId;
	}

	public void setDeviceModelId(Long deviceModelId) {
		this.deviceModelId = deviceModelId;
	}

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	public Long getPtoId() {
		return ptoId;
	}

	public void setPtoId(Long ptoId) {
		this.ptoId = ptoId;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getPtoName() {
		return ptoName;
	}

	public void setPtoName(String ptoName) {
		this.ptoName = ptoName;
	}

	private List<DeviceOnboardSearchRequest> orgnizationList;

	public List<DeviceOnboardSearchRequest> getOrgnizationList() {
		return orgnizationList;
	}

	public void setOrgnizationList(List<DeviceOnboardSearchRequest> orgnizationList) {
		this.orgnizationList = orgnizationList;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDeviceOnboardId() {
		return deviceOnboardId;
	}

	public void setDeviceOnboardId(String deviceOnboardId) {
		this.deviceOnboardId = deviceOnboardId;
	}

	public String getDeviceTypeName() {
		return deviceTypeName;
	}

	public void setDeviceTypeName(String deviceTypeName) {
		this.deviceTypeName = deviceTypeName;
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DeviceOnboardSearchResponse [deviceOnboardId=");
		builder.append(deviceOnboardId);
		builder.append(", organizationName=");
		builder.append(organizationName);
		builder.append(", ptoName=");
		builder.append(ptoName);
		builder.append(", deviceTypeName=");
		builder.append(deviceTypeName);
		builder.append(", deviceManufacturer=");
		builder.append(deviceManufacturer);
		builder.append(", deviceModel=");
		builder.append(deviceModel);
		builder.append(", status=");
		builder.append(status);
		builder.append(", orgnizationList=");
		builder.append(orgnizationList);
		builder.append("]");
		return builder.toString();
	}

}
