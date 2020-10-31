package com.chatak.transit.afcs.server.pojo.web;

public class SetupSoftwareSearchRequest extends LoginResponseParameters {

	private static final long serialVersionUID = 840354387931748551L;
	private Long softwareId;
	private Long ptoId;
	private Long organizationId;
	private String versionNumber;
	private String inherit;
	private String deliveryDate;
	private String applyDate;
	private String status;
	private String description;
	private int pageIndex;
	private String fullVersion;
	private String ptoName;
	private String organizationName;
	private int pageSize;
	private Long orgId;

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getPtoName() {
		return ptoName;
	}

	public void setPtoName(String ptoName) {
		this.ptoName = ptoName;
	}

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

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public String getFullVersion() {
		return fullVersion;
	}

	public void setFullVersion(String fullVersion) {
		this.fullVersion = fullVersion;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SetupSoftwareSearchRequest [softwareId=");
		builder.append(softwareId);
		builder.append(", ptoId=");
		builder.append(ptoId);
		builder.append(", organizationId=");
		builder.append(organizationId);
		builder.append(", versionNumber=");
		builder.append(versionNumber);
		builder.append(", inherit=");
		builder.append(inherit);
		builder.append(", deliveryDate=");
		builder.append(deliveryDate);
		builder.append(", applyDate=");
		builder.append(applyDate);
		builder.append(", status=");
		builder.append(status);
		builder.append(", description=");
		builder.append(description);
		builder.append(", pageIndex=");
		builder.append(pageIndex);
		builder.append(", fullVersion=");
		builder.append(fullVersion);
		builder.append(", ptoName=");
		builder.append(ptoName);
		builder.append(", organizationName=");
		builder.append(organizationName);
		builder.append("]");
		return builder.toString();
	}

}
