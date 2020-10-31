package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;

public class SoftwareUploadRequest<MultipartFile> implements Serializable {

	private static final long serialVersionUID = -922916490155827284L;

	private String userId;
	private String transactionId;
	private int softwareId;
	private String ptoOperationId;
	private String deviceType;
	private String softwareVersion;
	private transient MultipartFile softwareZipFile;

	public MultipartFile getSoftwareZipFile() {
		return softwareZipFile;
	}

	public void setSoftwareZipFile(MultipartFile softwareZipFile) {
		this.softwareZipFile = softwareZipFile;
	}

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

	public int getSoftwareId() {
		return softwareId;
	}

	public void setSoftwareId(int softwareId) {
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

	@Override
	public String toString() {
		return "SoftwareUploadRequest [userId=" + userId + ", transactionId=" + transactionId + ", softwareId="
				+ softwareId + ", ptoOperationId=" + ptoOperationId + ", deviceType=" + deviceType
				+ ", softwareVersion=" + softwareVersion + ", softwareZipFile=" + softwareZipFile + "]";
	}
	
}
