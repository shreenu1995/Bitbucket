package com.chatak.transit.afcs.server.pojo.web;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class DeviceProfileUpdateRequest extends DeviceRegistrationRequest {

	private static final long serialVersionUID = -5625594361055398162L;

	@NotEmpty(message = "chatak.afcs.service.device.id.required")
	@Size(min = 10, max = 12, message = "chatak.afcs.service.device.id.length.invalid")
	private String deviceId;

	private String deviceManufacturer;

	public String getDeviceManufacturer() {
		return deviceManufacturer;
	}

	public void setDeviceManufacturer(String deviceManufacturer) {
		this.deviceManufacturer = deviceManufacturer;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DeviceProfileUpdateRequest [deviceId=");
		builder.append(deviceId);
		builder.append("]");
		return builder.toString();
	}

}
