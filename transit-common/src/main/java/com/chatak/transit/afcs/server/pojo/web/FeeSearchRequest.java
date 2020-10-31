package com.chatak.transit.afcs.server.pojo.web;

public class FeeSearchRequest extends LoginResponseParameters {

	private String organizationName;
	private String ptoName;
	private String feeName;
	private Long feeId;
	private String ptoFeeType;
	private String ptoFeeValue;
	private String orgFeeType;
	private String orgFeeValue;
	private String ptoShareType;
	private String ptoshareValue;
	private String orgShareType;
	private String orgShareValue;
	private String status;
	private String reason;
	private Long organizationId;
	private Long ptoId;
	private int pageIndex;
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

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getPtoFeeType() {
		return ptoFeeType;
	}

	public void setPtoFeeType(String ptoFeeType) {
		this.ptoFeeType = ptoFeeType;
	}

	public String getPtoFeeValue() {
		return ptoFeeValue;
	}

	public void setPtoFeeValue(String ptoFeeValue) {
		this.ptoFeeValue = ptoFeeValue;
	}

	public String getOrgFeeType() {
		return orgFeeType;
	}

	public void setOrgFeeType(String orgFeeType) {
		this.orgFeeType = orgFeeType;
	}

	public String getOrgFeeValue() {
		return orgFeeValue;
	}

	public void setOrgFeeValue(String orgFeeValue) {
		this.orgFeeValue = orgFeeValue;
	}

	public String getPtoShareType() {
		return ptoShareType;
	}

	public void setPtoShareType(String ptoShareType) {
		this.ptoShareType = ptoShareType;
	}

	public String getPtoshareValue() {
		return ptoshareValue;
	}

	public void setPtoshareValue(String ptoshareValue) {
		this.ptoshareValue = ptoshareValue;
	}

	public String getOrgShareType() {
		return orgShareType;
	}

	public void setOrgShareType(String orgShareType) {
		this.orgShareType = orgShareType;
	}

	public String getOrgShareValue() {
		return orgShareValue;
	}

	public void setOrgShareValue(String orgShareValue) {
		this.orgShareValue = orgShareValue;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getPtoName() {
		return ptoName;
	}

	public void setPtoName(String ptoName) {
		this.ptoName = ptoName;
	}

	public String getFeeName() {
		return feeName;
	}

	public void setFeeName(String feeName) {
		this.feeName = feeName;
	}

	public Long getFeeId() {
		return feeId;
	}

	public void setFeeId(Long feeId) {
		this.feeId = feeId;
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

}
