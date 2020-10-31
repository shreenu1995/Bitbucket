package com.chatak.transit.afcs.server.pojo.web;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import com.chatak.transit.afcs.server.constants.Constants;

public class CommonUserParameter extends WebResponse {

	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "chatak.afcs.service.user.id.required")
	@Pattern(regexp = Constants.EMAIL_REGXP, message = "chatak.afcs.service.user.id.invalid")
	private String userId;

	@NotBlank(message = "chatak.afcs.service.ptooperation.id.required")
	@Size(min = 12, max = 12, message = "chatak.afcs.service.ptooperation.id.invalid")
	private String ptoOperationId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPtoOperationId() {
		return ptoOperationId;
	}

	public void setPtoOperationId(String ptoOperationId) {
		this.ptoOperationId = ptoOperationId;
	}

}
