package com.chatak.transit.afcs.server.pojo.web;

public class DepotStatusUpdateRequest extends BaseRequest{

	private static final long serialVersionUID = 8145158236837124480L;

	private Long depotId;

	private String status;

	public Long getDepotId() {
		return depotId;
	}

	public void setDepotId(Long depotId) {
		this.depotId = depotId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
