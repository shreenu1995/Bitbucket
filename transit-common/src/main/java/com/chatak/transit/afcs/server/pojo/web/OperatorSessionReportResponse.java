package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;
import java.sql.Timestamp;

public class OperatorSessionReportResponse  implements Serializable {

	private static final long serialVersionUID = 401170358675252182L;

	private Long id;
	private String userId;
	private Long ptoId;
	private String deviceId;
	private String transactionId;
	private Timestamp generateDateAndTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

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

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public Timestamp getGenerateDateAndTime() {
		return generateDateAndTime;
	}

	public void setGenerateDateAndTime(Timestamp generateDateAndTime) {
		this.generateDateAndTime = generateDateAndTime;
	}

}
