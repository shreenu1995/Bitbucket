package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.chatak.transit.afcs.server.constants.Constants;
import com.chatak.transit.afcs.server.model.NotEmptyAndNull;
import com.chatak.transit.afcs.server.model.RegexCheck;
import com.chatak.transit.afcs.server.model.SizeCheck;

public class DepotListViewRequest implements Serializable {

	private static final long serialVersionUID = 6713527469019259863L;

	@NotEmpty(message = "chatak.afcs.service.user.id.required", groups = NotEmptyAndNull.class)
	@Size(min = 8, max = 32, message = "chatak.afcs.service.user.id.length.invalid", groups = SizeCheck.class)
	@Pattern(regexp = Constants.EMAIL_REGXP, message = "chatak.afcs.service.user.id.invalid", groups = RegexCheck.class)
	private String userId;

	private String organizationId;

	private String ptoId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getPtoId() {
		return ptoId;
	}

	public void setPtoId(String ptoId) {
		this.ptoId = ptoId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DepotListViewRequest [userId=");
		builder.append(userId);
		builder.append(", ptoOperationId=");
		builder.append(ptoId);
		builder.append("]");
		return builder.toString();
	}

}
