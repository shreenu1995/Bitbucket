package com.chatak.transit.afcs.server.pojo.web;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.chatak.transit.afcs.server.constants.Constants;

public class OrganizationStatusUpdateRequest extends BaseRequest {

	private static final long serialVersionUID = 9196710662051264576L;

	@NotEmpty(message = "chatak.afcs.service.user.id.required")
	@Pattern(regexp = Constants.EMAIL_REGXP, message = "chatak.afcs.service.user.id.invalid")
	private String userId;

	private String status;
	
	@Override
	public String getUserId() {
		return userId;
	}

	@Override
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
