package com.chatak.transit.afcs.server.dao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "state")
public class State {

	@Id
	@SequenceGenerator(name = "seq_state_id", sequenceName = "seq_state_id")
	@GeneratedValue(generator = "seq_state_id")
	@Column(name = "state_id")
	private int id;
	
	@Column(name = "state_name")
	private String stateName;
	
	@Column(name = "country_id")
	private int countryId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public int getCountryId() {
		return countryId;
	}

	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}
}
