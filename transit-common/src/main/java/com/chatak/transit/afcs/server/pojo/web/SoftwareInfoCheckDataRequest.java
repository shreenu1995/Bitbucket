package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import com.chatak.transit.afcs.server.constants.Constants;

public class SoftwareInfoCheckDataRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@NotEmpty(message = "chatak.afcs.service.user.id.required")
	@Pattern(regexp = Constants.EMAIL_REGXP, message = "chatak.afcs.service.user.id.invalid")
	@Size(min= 8, max = 32, message = "chatak.afcs.service.user.id.length.invalid")
	private String userId;
	
	@NotBlank(message = "chatak.afcs.service.transaction.id.required")
	@Size(min = 4, max = 4, message = "chatak.afcs.service.transaction.id.invalid")
	private String transactionId;
	
	@NotBlank(message = "chatak.afcs.service.software.name.required")
	@Size(max = 35, message = "chatak.afcs.service.software.name.invalid")
	private String softwareName;
	
	@NotBlank(message = "chatak.afcs.service.ptooperation.id.required")
	@Size(min=12,max = 12, message = "chatak.afcs.service.ptooperation.id.invalid")
	private String ptoOperationId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getSoftwareName() {
		return softwareName;
	}

	public void setSoftwareName(String softwareName) {
		this.softwareName = softwareName;
	}

	public String getPtoOperationId() {
		return ptoOperationId;
	}

	public void setPtoOperationId(String ptoOperationId) {
		this.ptoOperationId = ptoOperationId;
	}

}
