package com.chatak.transit.afcs.server.pojo.web;

public class PtoSearchRequest extends LoginResponseParameters {

	private static final long serialVersionUID = -1776737882208811744L;

	private String ptoName;

	private String state;

	private String city;

	private String contactPerson;

	private String ptoMobile;

	private String ptoEmail;

	private String siteUrl;

	private String status;

	private String organizationName;

	private Integer pageIndex;
	
	private Integer pageSize;
	
	private Long orgId;
	
	private Long ptoMasterId;
	
	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Long getPtoMasterId() {
		return ptoMasterId;
	}

	public void setPtoMasterId(Long ptoMasterId) {
		this.ptoMasterId = ptoMasterId;
	}

	public String getPtoName() {
		return ptoName;
	}

	public void setPtoName(String ptoName) {
		this.ptoName = ptoName;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getPtoMobile() {
		return ptoMobile;
	}

	public void setPtoMobile(String ptoMobile) {
		this.ptoMobile = ptoMobile;
	}

	public String getPtoEmail() {
		return ptoEmail;
	}

	public void setPtoEmail(String ptoEmail) {
		this.ptoEmail = ptoEmail;
	}

	public String getSiteUrl() {
		return siteUrl;
	}

	public void setSiteUrl(String siteUrl) {
		this.siteUrl = siteUrl;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

}
