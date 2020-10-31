package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.chatak.transit.afcs.server.constants.Constants;
import com.chatak.transit.afcs.server.model.NotEmptyAndNull;
import com.chatak.transit.afcs.server.model.RegexCheck;
import com.chatak.transit.afcs.server.model.SizeCheck;

public class DeviceModelRequest implements Serializable {

	private static final long serialVersionUID = -6599420579003603787L;

	@NotBlank(message = "chatak.afcs.service.user.id.required", groups = NotEmptyAndNull.class)
	@Size(max = 35, message = "chatak.afcs.service.userid.size.invalid", groups = SizeCheck.class)
	@Pattern(regexp = Constants.EMAIL_REGXP, message = "chatak.afcs.service.user.id.invalid", groups = RegexCheck.class)
	private String userId;

	private String deviceTypeName;

	private String deviceManufacturer;

	private String deviceModel;

	private String deviceIMEINumber;

	private String description;

	private Long deviceId;

	private Long deviceTypeId;

	private Long deviceManufacturerCode;

	private Long deviceModelId;

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

	public Long getDeviceTypeId() {
		return deviceTypeId;
	}

	public void setDeviceTypeId(Long deviceTypeId) {
		this.deviceTypeId = deviceTypeId;
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

	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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
		builder.append("DeviceModelRequest [userId=");
		builder.append(userId);
		builder.append(", deviceTypeName=");
		builder.append(deviceTypeName);
		builder.append(", deviceManufacturer=");
		builder.append(deviceManufacturer);
		builder.append(", deviceModel=");
		builder.append(deviceModel);
		builder.append(", deviceIMEINumber=");
		builder.append(deviceIMEINumber);
		builder.append(", description=");
		builder.append(description);
		builder.append(", deviceId=");
		builder.append(deviceId);
		builder.append("]");
		return builder.toString();
	}

}
