package com.chatak.transit.afcs.server.dao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "country")
public class Country {
	
	@Id
	@SequenceGenerator(name = "seq_country_id", sequenceName = "seq_country_id")
	@GeneratedValue(generator = "seq_country_id")
	@Column(name = "country_id")
	private int id;
	
	@Column(name = "country_name")
	private String countryName;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

}
