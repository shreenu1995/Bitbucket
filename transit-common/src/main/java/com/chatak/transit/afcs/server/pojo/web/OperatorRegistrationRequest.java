package com.chatak.transit.afcs.server.pojo.web;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.chatak.transit.afcs.server.constants.Constants;

public class OperatorRegistrationRequest {

	@NotEmpty(message = "chatak.afcs.service.user.id.required")
	@Pattern(regexp = Constants.EMAIL_REGXP, message = "chatak.afcs.service.user.id.invalid")
	@Size(min = 8, max = 32, message = "chatak.afcs.service.user.id.length.invalid")
	private String userId;

	@NotBlank(message = "chatak.afcs.service.operator.name.required")
	private String operatorName;

	private Long organizationId;

	private Long operatorId;

	private Long ptoId;

	private String operatorContactNumber;

	private String status;

	private Integer pageIndex;

	private String operatorUserId;

	private String operatorPassword;

	public String getOperatorUserId() {
		return operatorUserId;
	}

	public void setOperatorUserId(String operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getOperatorContactNumber() {
		return operatorContactNumber;
	}

	public void setOperatorContactNumber(String operatorContactNumber) {
		this.operatorContactNumber = operatorContactNumber;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	public String getOperatorPassword() {
		return operatorPassword;
	}

	public void setOperatorPassword(String operatorPassword) {
		this.operatorPassword = operatorPassword;
	}

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	public Long getPtoId() {
		return ptoId;
	}

	public void setPtoId(Long ptoId) {
		this.ptoId = ptoId;
	}

}
