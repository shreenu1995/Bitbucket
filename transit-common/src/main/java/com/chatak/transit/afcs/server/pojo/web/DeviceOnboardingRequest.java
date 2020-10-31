package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;

public class DeviceOnboardingRequest implements Serializable {

	private static final long serialVersionUID = -6599420579003603787L;

	private String userId;

	private String organizationName;

	private String ptoName;

	private String deviceTypeName;

	private String deviceManufacturer;

	private String deviceModel;

	private String deviceOnboardId;

	private Long deviceTypeId;

	private Long deviceManufacturerCode;

	private Long deviceModelId;

	private Long ptoId;

	private Long orgId;

	private Long organizationId;

	private String userType;

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getDeviceTypeId() {
		return deviceTypeId;
	}

	public void setDeviceTypeId(Long deviceTypeId) {
		this.deviceTypeId = deviceTypeId;
	}

	public Long getDeviceManufacturerCode() {
		return deviceManufacturerCode;
	}

	public void setDeviceManufacturerCode(Long deviceManufacturerCode) {
		this.deviceManufacturerCode = deviceManufacturerCode;
	}

	public Long getDeviceModelId() {
		return deviceModelId;
	}

	public void setDeviceModelId(Long deviceModelId) {
		this.deviceModelId = deviceModelId;
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

	public String getDeviceOnboardId() {
		return deviceOnboardId;
	}

	public void setDeviceOnboardId(String deviceOnboardId) {
		this.deviceOnboardId = deviceOnboardId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DeviceOnboardingRequest [userId=");
		builder.append(userId);
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
		builder.append(", deviceOnboardId=");
		builder.append(deviceOnboardId);
		builder.append("]");
		return builder.toString();
	}

}
