package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;

public class CountryRequest implements Serializable {

	private int countryId;

	private String countryName;
	
	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public int getCountryId() {
		return countryId;
	}

	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}
	
}
