package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.chatak.transit.afcs.server.constants.Constants;
import com.chatak.transit.afcs.server.model.NotEmptyAndNull;
import com.chatak.transit.afcs.server.model.RegexCheck;
import com.chatak.transit.afcs.server.model.SizeCheck;

public class DeviceManufacturerRegistrationRequest implements Serializable {

	private static final long serialVersionUID = 5482801276204380268L;

	@NotBlank(message = "chatak.afcs.service.user.id.required", groups = NotEmptyAndNull.class)
	@Size(max = 35, message = "chatak.afcs.service.userid.size.invalid", groups = SizeCheck.class)
	@Pattern(regexp = Constants.EMAIL_REGXP, message = "chatak.afcs.service.user.id.invalid", groups = RegexCheck.class)
	private String userId;

	private String deviceManufacturer;

	private String description;

	private Long deviceTypeId;

	private int deviceManufacturerId;

	public Long getDeviceTypeId() {
		return deviceTypeId;
	}

	public void setDeviceTypeId(Long deviceTypeId) {
		this.deviceTypeId = deviceTypeId;
	}

	public int getDeviceManufacturerId() {
		return deviceManufacturerId;
	}

	public void setDeviceManufacturerId(int deviceManufacturerId) {
		this.deviceManufacturerId = deviceManufacturerId;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DeviceManufacturerRegistrationRequest [userId=");
		builder.append(userId);
		builder.append(", deviceManufacturer=");
		builder.append(deviceManufacturer);
		builder.append(", description=");
		builder.append(description);
		builder.append(", deviceTypeId=");
		builder.append(deviceTypeId);
		builder.append(", deviceManufacturerId=");
		builder.append(deviceManufacturerId);
		builder.append("]");
		return builder.toString();
	}

}
