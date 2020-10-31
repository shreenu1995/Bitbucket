package com.chatak.transit.afcs.server.pojo.web;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.chatak.transit.afcs.server.constants.Constants;

public class DeviceManufacturerStatusUpdateRequest {

	@NotBlank(message = "chatak.afcs.service.user.id.required")
	@Pattern(regexp = Constants.EMAIL_REGXP, message = "chatak.afcs.service.user.id.invalid")
	@Size(max = 35, message = "chatak.afcs.service.userid.size.invalid")
	private String userId;

	private Long deviceManufacturerCode;

	private String status;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DeviceManufacturerStatusUpdateRequest [userId=");
		builder.append(userId);
		builder.append(", deviceManufacturerCode=");
		builder.append(deviceManufacturerCode);
		builder.append(", status=");
		builder.append(status);
		builder.append("]");
		return builder.toString();
	}
	
}
