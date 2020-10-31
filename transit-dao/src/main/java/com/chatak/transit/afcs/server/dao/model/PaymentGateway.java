package com.chatak.transit.afcs.server.dao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "PaymentGateway")
public class PaymentGateway {

	@Id
	@SequenceGenerator(name = "seq_pg_id", sequenceName = "seq_pg_id")
	@GeneratedValue(generator = "seq_pg_id")
	@Column(name = "pg_id")
	private Long id;

	@Column(name = "pg_name")
	private String pgName;

	@Column(name = "pto_master_id")
	private Long ptoMasterId;

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

	public String getPgName() {
		return pgName;
	}

	public String getServiceUrl() {
		return serviceUrl;
	}

	public int getCountryId() {
		return countryId;
	}

	public Long getPtoMasterId() {
		return ptoMasterId;
	}

	public void setPtoMasterId(Long ptoMasterId) {
		this.ptoMasterId = ptoMasterId;
	}
	
	public int getStateId() {
		return stateId;
	}

	public int getCityId() {
		return cityId;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setPgName(String pgName) {
		this.pgName = pgName;
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
