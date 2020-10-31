package com.chatak.transit.afcs.server.pojo.web;

public class PtoListRequest extends LoginResponseParameters {

	private static final long serialVersionUID = 1L;

	private Long ptoMasterId;
	
	private Long orgId;

	public Long getPtoMasterId() {
		return ptoMasterId;
	}

	public void setPtoMasterId(Long ptoMasterId) {
		this.ptoMasterId = ptoMasterId;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

}
