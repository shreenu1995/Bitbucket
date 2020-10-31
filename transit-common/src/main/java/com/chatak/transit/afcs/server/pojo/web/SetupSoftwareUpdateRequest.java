package com.chatak.transit.afcs.server.pojo.web;

public class SetupSoftwareUpdateRequest {

	private Long softwareId;
	private Long ptoId;
	private Long organizationId;
	private String versionNumber;
	private String inherit;
	private String deliveryDate;
	private String applyDate;
	private String status;
	private String description;
	private String fullVersion;
	
	public Long getSoftwareId() {
		return softwareId;
	}
	public void setSoftwareId(Long softwareId) {
		this.softwareId = softwareId;
	}
	public Long getPtoId() {
		return ptoId;
	}
	public void setPtoId(Long ptoId) {
		this.ptoId = ptoId;
	}
	public Long getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}
	public String getVersionNumber() {
		return versionNumber;
	}
	public void setVersionNumber(String versionNumber) {
		this.versionNumber = versionNumber;
	}
	public String getInherit() {
		return inherit;
	}
	public void setInherit(String inherit) {
		this.inherit = inherit;
	}
	public String getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public String getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getFullVersion() {
		return fullVersion;
	}
	public void setFullVersion(String fullVersion) {
		this.fullVersion = fullVersion;
	}
	
}
