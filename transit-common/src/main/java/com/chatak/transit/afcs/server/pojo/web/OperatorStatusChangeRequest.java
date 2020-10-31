package com.chatak.transit.afcs.server.pojo.web;

public class OperatorStatusChangeRequest extends BaseRequest{

	private Long operatorId;

	private String status;

	public Long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
