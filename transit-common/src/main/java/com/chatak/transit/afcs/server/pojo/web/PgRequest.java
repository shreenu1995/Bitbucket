package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;

public class PgRequest implements Serializable {

	private static final long serialVersionUID = 12312L;

	private Long pgId;

	private String pgName;

	private String country;

	private String state;

	private String ptoName;

	private String serviceUrl;

	private String city;

	private String ptoId;

	private String userType;

	private int pageSize;

	private String countryId;

	private int pageIndex;

	private String ptoMasterId;
	
	private String currency;

	private String mid;

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	public String getPtoMasterId() {
		return ptoMasterId;
	}

	public void setPtoMasterId(String ptoMasterId) {
		this.ptoMasterId = ptoMasterId;
	}

	public Long getPgId() {
		return pgId;
	}

	public void setPgId(Long pgId) {
		this.pgId = pgId;
	}

	public String getCountryId() {
		return countryId;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public String getPgName() {
		return pgName;
	}

	public String getCountry() {
		return country;
	}

	public String getState() {
		return state;
	}

	public String getPtoName() {
		return ptoName;
	}

	public String getServiceUrl() {
		return serviceUrl;
	}

	public String getCity() {
		return city;
	}

	public String getPtoId() {
		return ptoId;
	}

	public String getUserType() {
		return userType;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPgName(String pgName) {
		this.pgName = pgName;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setPtoName(String ptoName) {
		this.ptoName = ptoName;
	}

	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setPtoId(String ptoId) {
		this.ptoId = ptoId;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}
