package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.chatak.transit.afcs.server.constants.Constants;

public class DeviceTypeStatusUpdateRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5984295940541845347L;

	@NotBlank(message = "chatak.afcs.service.user.id.required")
	@Pattern(regexp = Constants.EMAIL_REGXP, message = "chatak.afcs.service.user.id.invalid")
	@Size(max = 35, message = "chatak.afcs.service.userid.size.invalid")
	private String userId;

	private Long deviceTypeId;

	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Long getDeviceTypeId() {
		return deviceTypeId;
	}

	public void setDeviceTypeId(Long deviceTypeId) {
		this.deviceTypeId = deviceTypeId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DeviceTypeStatusUpdateRequest [userId=");
		builder.append(userId);
		builder.append(", deviceTypeCode=");
		builder.append(deviceTypeId);
		builder.append(", status=");
		builder.append(status);
		builder.append("]");
		return builder.toString();
	}
}
