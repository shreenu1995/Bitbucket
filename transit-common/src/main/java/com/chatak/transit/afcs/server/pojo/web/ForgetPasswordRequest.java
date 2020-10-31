package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.chatak.transit.afcs.server.constants.Constants;

public class ForgetPasswordRequest implements Serializable {

	private static final long serialVersionUID = -7011235727847780298L;

	@NotEmpty(message = "chatak.afcs.service.email.id.required")
	@Pattern(regexp = Constants.EMAIL_REGXP, message = "chatak.afcs.service.email.id.invalid.format")
	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ForgetPasswordRequest [userId=");
		builder.append(userId);
		builder.append("]");
		return builder.toString();
	}

}
