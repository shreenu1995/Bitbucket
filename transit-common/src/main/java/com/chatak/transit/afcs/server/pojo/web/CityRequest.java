package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;

public class CityRequest implements Serializable {

	private int cityId;

	private int stateId;

	private String cityName;

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public int getStateId() {
		return stateId;
	}

	public void setStateId(int stateId) {
		this.stateId = stateId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CityRequest [cityId=");
		builder.append(cityId);
		builder.append(", stateId=");
		builder.append(stateId);
		builder.append(", cityName=");
		builder.append(cityName);
		builder.append("]");
		return builder.toString();
	}
	
}
