package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SoftwareRegistrationRequest extends SoftwareInfoCheckDataRequest implements Serializable {

	private static final long serialVersionUID = 4177638234216148907L;

	@NotBlank(message = "chatak.afcs.service.device.id.required")
	@Size(max = 12, message = "chatak.afcs.service.device.id.invalid")
	private String deviceId;

	@Size(max = 2048, message = "chatak.afcs.service.description.invalid")
	private String description;

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
