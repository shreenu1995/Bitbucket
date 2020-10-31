package com.chatak.transit.afcs.server.pojo.web;

public class DepotStatusCheckRequest extends CommonUserParameter {

	private static final long serialVersionUID = 2724226772174177473L;

	private Long depotId;
	
	private Long ptoMasterId;

	public Long getDepotId() {
		return depotId;
	}

	public void setDepotId(Long depotId) {
		this.depotId = depotId;
	}

	public Long getPtoMasterId() {
		return ptoMasterId;
	}

	public void setPtoMasterId(Long ptoMasterId) {
		this.ptoMasterId = ptoMasterId;
	}

}
