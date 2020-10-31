package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.chatak.transit.afcs.server.constants.Constants;
import com.chatak.transit.afcs.server.model.NotEmptyAndNull;
import com.chatak.transit.afcs.server.model.RegexCheck;

public class UserStatusUpdateRequest extends BaseRequest implements Serializable {

	private static final long serialVersionUID = -3536097436553375077L;

	private String userId;

	@NotEmpty(message = "chatak.afcs.service.adminuserid.required", groups = NotEmptyAndNull.class)
	@Pattern(regexp = Constants.EMAIL_REGXP, message = "chatak.afcs.service.adminuserid.size.invalid", groups = RegexCheck.class)
	private String adminUserId;

	@NotNull(message = "chatak.afcs.service.status.invalid")
	private String status;

	@Override
	public String getUserId() {
		return userId;
	}

	@Override
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAdminUserId() {
		return adminUserId;
	}

	public void setAdminUserId(String adminUserId) {
		this.adminUserId = adminUserId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {

		StringBuilder response = new StringBuilder();

		return response.append("UserStatusUpdateRequest [userid=").append(getUserId()).append(", adminuserid=").append(getAdminUserId()).append(", status=")
				.append(status).append("]").toString();

	}

}
