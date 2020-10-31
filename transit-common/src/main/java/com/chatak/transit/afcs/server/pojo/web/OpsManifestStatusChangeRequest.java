package com.chatak.transit.afcs.server.pojo.web;

public class OpsManifestStatusChangeRequest {

	private Long opsManifestId;
	private String status;

	public Long getOpsManifestId() {
		return opsManifestId;
	}

	public void setOpsManifestId(Long opsManifestId) {
		this.opsManifestId = opsManifestId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
