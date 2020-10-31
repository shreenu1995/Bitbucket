package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;

public class IssuerRequest implements Serializable {

	private static final long serialVersionUID = 12312L;

	private Long issuerId;

	private String issuerName;

	private String ptoId;

	private String userType;

	private int pageSize;

	private String ptoName;

	private String serviceUrl;

	private String country;

	private String state;

	private String city;

	private String countryId;

	private int pageIndex;

	private String ptoMasterId;
	
	public String getPtoMasterId() {
		return ptoMasterId;
	}

	public void setPtoMasterId(String ptoMasterId) {
		this.ptoMasterId = ptoMasterId;
	}

	public Long getIssuerId() {
		return issuerId;
	}

	public void setIssuerId(Long issuerId) {
		this.issuerId = issuerId;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
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

	public String getPtoName() {
		return ptoName;
	}

	public void setPtoName(String ptoName) {
		this.ptoName = ptoName;
	}

	public String getServiceUrl() {
		return serviceUrl;
	}

	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getPtoId() {
		return ptoId;
	}

	public void setPtoId(String ptoId) {
		this.ptoId = ptoId;
	}

	public String getIssuerName() {
		return issuerName;
	}

	public void setIssuerName(String issuerName) {
		this.issuerName = issuerName;
	}
}
