package com.chatak.transit.afcs.server.pojo.web;

public class DepotProfileUpdateRequest extends DepotRegistrationRequest {

	private static final long serialVersionUID = 4935901163887049220L;
	
	private Long depotId;

	public Long getDepotId() {
		return depotId;
	}

	public void setDepotId(Long depotId) {
		this.depotId = depotId;
	}
	
}
