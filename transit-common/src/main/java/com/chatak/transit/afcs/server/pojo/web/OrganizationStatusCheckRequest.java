package com.chatak.transit.afcs.server.pojo.web;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.chatak.transit.afcs.server.constants.Constants;

public class OrganizationStatusCheckRequest {
	@NotEmpty(message = "chatak.afcs.service.user.id.required")
	@Pattern(regexp = Constants.EMAIL_REGXP, message = "chatak.afcs.service.user.id.invalid")
	private String userId;

	private Long orgId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

}
