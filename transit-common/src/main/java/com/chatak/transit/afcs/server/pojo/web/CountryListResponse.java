package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;
import java.util.List;

public class CountryListResponse extends WebResponse implements Serializable {

	private static final long serialVersionUID = 7275924277509200648L;

	private List<CountryRequest> countryList;

	public List<CountryRequest> getCountryList() {
		return countryList;
	}

	public void setCountryList(List<CountryRequest> countryList) {
		this.countryList = countryList;
	}
}
