package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;

public class SoftwareDownloadRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2185078215033173725L;

	private String userId;
	private String transactionId;
	private String softwareId;
	private String ptoOperationId;
	private String deviceType;
	private String softwareVersion;
	private String deviceId;
	private String operatorId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getSoftwareId() {
		return softwareId;
	}

	public void setSoftwareId(String softwareId) {
		this.softwareId = softwareId;
	}

	public String getPtoOperationId() {
		return ptoOperationId;
	}

	public void setPtoOperationId(String ptoOperationId) {
		this.ptoOperationId = ptoOperationId;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getSoftwareVersion() {
		return softwareVersion;
	}

	public void setSoftwareVersion(String softwareVersion) {
		this.softwareVersion = softwareVersion;
	}

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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SoftwareDownloadRequest [userId=");
		builder.append(userId);
		builder.append(", transactionId=");
		builder.append(transactionId);
		builder.append(", softwareId=");
		builder.append(softwareId);
		builder.append(", ptoOperationId=");
		builder.append(ptoOperationId);
		builder.append(", deviceType=");
		builder.append(deviceType);
		builder.append(", softwareVersion=");
		builder.append(softwareVersion);
		builder.append(", deviceId=");
		builder.append(deviceId);
		builder.append(", operatorId=");
		builder.append(operatorId);
		builder.append("]");
		return builder.toString();
	}

}
