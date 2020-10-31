package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;
import java.sql.Timestamp;

public class DeviceDetails implements Serializable {

	private static final long serialVersionUID = -5931354868040496750L;

	private Long id;

	private String deviceId;

	private String ptoOperationId;

	private String description;

	private String model;

	private String serialNumber;

	private String assignedSoftwareVersion;

	private String additionalSpecialData;

	private String assignedMasterVersion;

	private Timestamp createdTime;

	private Timestamp updatedTime;

	private String createdBy;

	private String updatedBy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getPtoOperationId() {
		return ptoOperationId;
	}

	public void setPtoOperationId(String ptoOperationId) {
		this.ptoOperationId = ptoOperationId;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getAdditionalSpecialData() {
		return additionalSpecialData;
	}

	public void setAdditionalSpecialData(String additionalSpecialData) {
		this.additionalSpecialData = additionalSpecialData;
	}

	public String getAssignedSoftwareVersion() {
		return assignedSoftwareVersion;
	}

	public void setAssignedSoftwareVersion(String assignedSoftwareVersion) {
		this.assignedSoftwareVersion = assignedSoftwareVersion;
	}

	public String getAssignedMasterVersion() {
		return assignedMasterVersion;
	}

	public void setAssignedMasterVersion(String assignedMasterVersion) {
		this.assignedMasterVersion = assignedMasterVersion;
	}

	public Timestamp getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Timestamp updatedTime) {
		this.updatedTime = updatedTime;
	}

	public Timestamp getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Timestamp createdTime) {
		this.createdTime = createdTime;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
}
