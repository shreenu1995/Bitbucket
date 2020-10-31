package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

public class SoftwareVersionCheckRequest implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1182785274673610597L;
	
	private String ptoOperationId;
	
	private String deviceId;
	
	private int softwareId;
	
	private String softwareVersion;
	
	private String transactionId;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp dateTime;
	
	private String operatorId;
	
	private String reserverd;

	private String checkSum;

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

	public int getSoftwareId() {
		return softwareId;
	}

	public void setSoftwareId(int softwareId) {
		this.softwareId = softwareId;
	}

	public String getSoftwareVersion() {
		return softwareVersion;
	}

	public void setSoftwareVersion(String softwareVersion) {
		this.softwareVersion = softwareVersion;
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

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getReserverd() {
		return reserverd;
	}

	public void setReserverd(String reserverd) {
		this.reserverd = reserverd;
	}

	public String getCheckSum() {
		return checkSum;
	}

	public void setCheckSum(String checkSum) {
		this.checkSum = checkSum;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(" SoftwareVersionCheckRequest  [  ");
		builder.append(" ptoOperationId = ");
		builder.append(getPtoOperationId());
		builder.append(" ,  deviceId = ");
		builder.append(getDeviceId());
		builder.append(" ,  softwareId = ");
		builder.append(getSoftwareId());
		builder.append(" softwareVersion = ");
		builder.append(getSoftwareVersion());
		builder.append(" ,  transactionId = ");
		builder.append(getTransactionId());
		builder.append(" ,  dateTime = ");
		builder.append(getDateTime());
		builder.append(" operatorId = ");
		builder.append(getOperatorId());
		builder.append(" ,  reserverd = ");
		builder.append(getReserverd());
		builder.append(" ,  checksum = ");
		builder.append(getCheckSum());
		builder.append("   ]");
		return builder.toString();
	}
}
