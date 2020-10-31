package com.chatak.transit.afcs.server.pojo.web;

public class BaseRequest extends LoginResponseParameters {

	private static final long serialVersionUID = 1L;

	private String organizationId;

	private String ptoId;

	private String reason;

	private Long orgId;
	
	private Long ptoMasterId;

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getPtoId() {
		return ptoId;
	}

	public void setPtoId(String ptoId) {
		this.ptoId = ptoId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public Long getPtoMasterId() {
		return ptoMasterId;
	}

	public void setPtoMasterId(Long ptoMasterId) {
		this.ptoMasterId = ptoMasterId;
	}

}
