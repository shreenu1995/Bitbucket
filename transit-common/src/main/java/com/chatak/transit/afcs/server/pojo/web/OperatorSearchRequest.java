package com.chatak.transit.afcs.server.pojo.web;

public class OperatorSearchRequest extends LoginResponseParameters {

	private Long organizationId;

	private Long ptoId;

	private String operatorName;

	private String operatorContactNumber;

	private Long operatorId;

	private String status;

	private Integer pageIndex;

	private String operatorUserId;

	private String ptoName;

	private Long orgId;

	private int pageSize;

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

	public String getOperatorUserId() {
		return operatorUserId;
	}

	public void setOperatorUserId(String operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	public Long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOperatorContactNumber() {
		return operatorContactNumber;
	}

	public void setOperatorContactNumber(String operatorContactNumber) {
		this.operatorContactNumber = operatorContactNumber;
	}

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	public Long getPtoId() {
		return ptoId;
	}

	public void setPtoId(Long ptoId) {
		this.ptoId = ptoId;
	}

	public String getPtoName() {
		return ptoName;
	}

	public void setPtoName(String ptoName) {
		this.ptoName = ptoName;
	}

}
