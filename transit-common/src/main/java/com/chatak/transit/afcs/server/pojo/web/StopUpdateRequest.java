package com.chatak.transit.afcs.server.pojo.web;

public class StopUpdateRequest extends StopRegistrationRequest {

	private static final long serialVersionUID = 4890973718812015991L;
	
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
