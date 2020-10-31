package com.chatak.transit.afcs.server.pojo;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BatchUploadRequest extends AFCSCommonRequest {

	private static final long serialVersionUID = 3723943057014935595L;

	@NotEmpty(message = "DeviceId is required")
	@Size(max = 12)
	private String deviceId;

	@NotEmpty(message = "OperatorId is required")
	@Size(max = 12)
	private String operatorId;

	@NotNull
	private BatchSummary batchSummary;

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public BatchSummary getBatchSummary() {
		return batchSummary;
	}

	public void setBatchSummary(BatchSummary batchSummary) {
		this.batchSummary = batchSummary;
	}

}
