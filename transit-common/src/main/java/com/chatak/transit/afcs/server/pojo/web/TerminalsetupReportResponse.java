package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;
import java.sql.Timestamp;

import com.chatak.transit.afcs.server.util.DateUtil;
import com.fasterxml.jackson.annotation.JsonFormat;

public class TerminalsetupReportResponse implements Serializable {

	private static final long serialVersionUID = 6736195303247450950L;

	private Long ptoId;
	private String deviceId;
	private String eqipmentOemId;
	private String deviceModel;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp generationDateTime;

	public Long getPtoId() {
		return ptoId;
	}

	public void setPtoId(Long ptoId) {
		this.ptoId = ptoId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getEqipmentOemId() {
		return eqipmentOemId;
	}

	public void setEqipmentOemId(String eqipmentOemId) {
		this.eqipmentOemId = eqipmentOemId;
	}

	public String getDeviceModel() {
		return deviceModel;
	}

	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}

	public Timestamp getGenerationDateTime() {
		return generationDateTime;
	}

	public void setGenerationDateTime(Timestamp generationDateStart) {
		this.generationDateTime = DateUtil.setGenerationDateTimeResponse(generationDateStart);

	}

	@Override
	public String toString() {
		StringBuilder response = new StringBuilder();
		return response.append("TerminalsetupReportGenerationResponse [ptoId=").append(ptoId).append(", deviceId=")
				.append(deviceId).append(", eqipmentOemId=").append(eqipmentOemId).append(", deviceModel=")
				.append(deviceModel).append(", generationDateTime=").append(generationDateTime).append("]").toString();
	}
}
