package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.chatak.transit.afcs.server.constants.Constants;
import com.chatak.transit.afcs.server.model.NotEmptyAndNull;
import com.chatak.transit.afcs.server.model.RegexCheck;
import com.chatak.transit.afcs.server.model.SizeCheck;

public class DeviceManufacturerProfileUpdateRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3192745914896810629L;

	@NotBlank(message = "chatak.afcs.service.user.id.required", groups = NotEmptyAndNull.class)
	@Size(max = 35, message = "chatak.afcs.service.userid.size.invalid", groups = SizeCheck.class)
	@Pattern(regexp = Constants.EMAIL_REGXP, message = "chatak.afcs.service.user.id.invalid", groups = RegexCheck.class)
	private String userId;

	private Long deviceManufacturerCode;

	private String deviceTypeName;

	private String deviceManufacturer;

	private String description;

	private Timestamp updatedTime;

	private String status;

	private String updatedBy;

	private Long deviceTypeId;

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

	public String getDeviceManufacturer() {
		return deviceManufacturer;
	}

	public void setDeviceManufacturer(String deviceManufacturer) {
		this.deviceManufacturer = deviceManufacturer;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Timestamp getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Timestamp updatedTime) {
		this.updatedTime = updatedTime;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
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

	public String getDeviceTypeName() {
		return deviceTypeName;
	}

	public void setDeviceTypeName(String deviceTypeName) {
		this.deviceTypeName = deviceTypeName;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DeviceManufacturerProfileUpdateRequest [userId=");
		builder.append(userId);
		builder.append(", deviceManufacturerCode=");
		builder.append(deviceManufacturerCode);
		builder.append(", deviceTypeName=");
		builder.append(deviceTypeName);
		builder.append(", deviceManufacturer=");
		builder.append(deviceManufacturer);
		builder.append(", description=");
		builder.append(description);
		builder.append(", updatedTime=");
		builder.append(updatedTime);
		builder.append(", updatedBy=");
		builder.append(updatedBy);
		builder.append(", status=");
		builder.append(status);
		builder.append("]");
		return builder.toString();
	}

}
