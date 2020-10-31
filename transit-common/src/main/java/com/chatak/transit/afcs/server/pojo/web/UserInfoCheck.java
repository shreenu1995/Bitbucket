package com.chatak.transit.afcs.server.pojo.web;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.chatak.transit.afcs.server.constants.Constants;

public class UserInfoCheck {

	private String userId;

	@NotEmpty(message = "chatak.afcs.service.adminuserid.required")
	@Pattern(regexp = Constants.EMAIL_REGXP, message = "chatak.afcs.service.adminuserid.size.invalid")
	private String adminUserId;

	private String email;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAdminUserId() {
		return adminUserId;
	}

	public void setAdminUserId(String adminUserId) {
		this.adminUserId = adminUserId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
