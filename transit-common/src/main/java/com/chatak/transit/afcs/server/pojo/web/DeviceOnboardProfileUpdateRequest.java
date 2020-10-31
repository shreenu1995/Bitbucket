package com.chatak.transit.afcs.server.pojo.web;

public class DeviceOnboardProfileUpdateRequest extends LoginResponseParameters {

	private static final long serialVersionUID = -2906102086104696428L;

	private String userId;

	private Long organizationId;

	private String organizationName;

	private String ptoName;

	private String deviceTypeName;

	private String deviceManufacturer;

	private String deviceModel;

	private Long deviceOnboardId;

	private String reason;

	private String status;

	private Long ptoId;

	private Long deviceTypeId;

	private Long deviceManufacturerCode;

	private Long deviceModelId;

	public Long getPtoId() {
		return ptoId;
	}

	public void setPtoId(Long ptoId) {
		this.ptoId = ptoId;
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

	@Override
	public String getUserId() {
		return userId;
	}

	@Override
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
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

	public Long getDeviceOnboardId() {
		return deviceOnboardId;
	}

	public void setDeviceOnboardId(Long deviceOnboardId) {
		this.deviceOnboardId = deviceOnboardId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DeviceOnboardProfileUpdateRequest [userId=");
		builder.append(userId);
		builder.append(", organizationId=");
		builder.append(organizationId);
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
		builder.append(", reason=");
		builder.append(reason);
		builder.append(", status=");
		builder.append(status);
		builder.append("]");
		return builder.toString();
	}

}
