package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;

public class DeviceModelProfileUpdateRequest extends DeviceModelRequest implements Serializable  {

	private static final long serialVersionUID = 8077847347581890890L;
	
	private String status;
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DeviceModelProfileUpdateRequest [status=");
		builder.append(status);
		builder.append("]");
		return builder.toString();
	}

}
