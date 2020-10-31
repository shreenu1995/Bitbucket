package com.chatak.transit.afcs.server.dao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "Issuer")
public class Issuer {

	@Id
	@SequenceGenerator(name = "seq_issuer_id", sequenceName = "seq_issuer_id")
	@GeneratedValue(generator = "seq_issuer_id")
	@Column(name = "issuer_id")
	private Long id;

	@Column(name = "issuer_name")
	private String issuerName;

	@Column(name = "pto_master_id")
	private Long ptoMaterId;

	@Column(name = "service_url")
	private String serviceUrl;

	@Column(name = "country_id")
	private int countryId;

	@Column(name = "state_id")
	private int stateId;

	@Column(name = "city_id")
	private int cityId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPtoMaterId() {
		return ptoMaterId;
	}

	public void setPtoMaterId(Long ptoMaterId) {
		this.ptoMaterId = ptoMaterId;
	}
	
	public String getIssuerName() {
		return issuerName;
	}

	public String getServiceUrl() {
		return serviceUrl;
	}

	public int getCountryId() {
		return countryId;
	}

	public int getStateId() {
		return stateId;
	}

	public int getCityId() {
		return cityId;
	}

	public void setIssuerName(String issuerName) {
		this.issuerName = issuerName;
	}

	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}

	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}

	public void setStateId(int stateId) {
		this.stateId = stateId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
}
