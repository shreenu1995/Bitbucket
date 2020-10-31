package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.chatak.transit.afcs.server.constants.Constants;

public class DeviceModelStatusUpdateRequest extends BaseRequest implements Serializable {

	private static final long serialVersionUID = -6030655717460896052L;

	@NotEmpty(message = "chatak.afcs.service.user.id.required")
	@Pattern(regexp = Constants.EMAIL_REGXP, message = "chatak.afcs.service.user.id.invalid")
	private String userId;

	private Long deviceId;

	private String status;

	@Override	
	public String getUserId() {
		return userId;
	}

	@Override
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
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
		builder.append("DeviceModelStatusUpdateRequest [userId=");
		builder.append(userId);
		builder.append(", deviceId=");
		builder.append(deviceId);
		builder.append(", status=");
		builder.append(status);
		builder.append("]");
		return builder.toString();
	}

}
