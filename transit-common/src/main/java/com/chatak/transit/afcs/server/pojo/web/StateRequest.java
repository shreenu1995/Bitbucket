package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;

public class StateRequest implements Serializable {

	private static final long serialVersionUID = 9201020458471035119L;

	private int stateId;

	private int countryId;

	private String stateName;

	public int getStateId() {
		return stateId;
	}

	public void setStateId(int stateId) {
		this.stateId = stateId;
	}

	public int getCountryId() {
		return countryId;
	}

	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("StateRequest [stateId=");
		builder.append(stateId);
		builder.append(", countryId=");
		builder.append(countryId);
		builder.append(", stateName=");
		builder.append(stateName);
		builder.append("]");
		return builder.toString();
	}


}