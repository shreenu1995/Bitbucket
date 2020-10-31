package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.chatak.transit.afcs.server.constants.Constants;

public class PtoPermissionRequest implements Serializable {

	private static final long serialVersionUID = 6492103838187292021L;

	@NotEmpty(message = "chatak.afcs.service.user.id.required")
	@Pattern(regexp = Constants.EMAIL_REGXP, message = "chatak.afcs.service.user.id.invalid")
	private String userId;

	private String ptoId;

	@NotBlank(message = "chatak.afcs.service.adminuserid.required")
	@Pattern(regexp = Constants.EMAIL_REGXP, message = "chatak.afcs.service.adminuserid.size.invalid")
	private String adminUserId;

	private String organizationId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPtoId() {
		return ptoId;
	}

	public void setPtoId(String ptoId) {
		this.ptoId = ptoId;
	}

	public String getAdminUserId() {
		return adminUserId;
	}

	public void setAdminUserId(String adminUserId) {
		this.adminUserId = adminUserId;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PtoOperationPermissionRequest [userId=");
		builder.append(userId);
		builder.append(", ptoOperationId=");
		builder.append(ptoId);
		builder.append(", adminUserId=");
		builder.append(adminUserId);
		builder.append(", ptoId=");
		builder.append(ptoId);
		builder.append("]");
		return builder.toString();
	}

}
