package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;
import java.util.List;

public class CityResponse extends WebResponse implements Serializable {
	
	private static final long serialVersionUID = 1648897243111855675L;
	private List<CityRequest> cityList;

	public List<CityRequest> getCityList() {
		return cityList;
	}

	public void setCityList(List<CityRequest> cityList) {
		this.cityList = cityList;
	}
	
}
