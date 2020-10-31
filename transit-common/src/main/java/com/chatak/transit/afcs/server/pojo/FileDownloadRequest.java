package com.chatak.transit.afcs.server.pojo;

import java.io.Serializable;
import java.sql.Timestamp;

public class FileDownloadRequest implements Serializable {

	private static final long serialVersionUID = -1881410074111306427L;

	private String ptoOperationId;
	private String deviceId;
	private String version;
	private String transactionId;
	private Timestamp dateTime;
	private String reservedSession;
	private String checkSum;
	private Long ptoMasterId;

	public String getPtoOperationId() {
		return ptoOperationId;
	}

	public void setPtoOperationId(String ptoOperationId) {
		this.ptoOperationId = ptoOperationId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public Timestamp getDateTime() {
		return dateTime;
	}

	public void setDateTime(Timestamp dateTime) {
		this.dateTime = dateTime;
	}

	public String getReservedSession() {
		return reservedSession;
	}

	public void setReservedSession(String reservedSession) {
		this.reservedSession = reservedSession;
	}

	public String getCheckSum() {
		return checkSum;
	}

	public void setCheckSum(String checkSum) {
		this.checkSum = checkSum;
	}

	public Long getPtoMasterId() {
		return ptoMasterId;
	}

	public void setPtoMasterId(Long ptoMasterId) {
		this.ptoMasterId = ptoMasterId;
	}

}
