package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;

public class FareRegistrationRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long ptoId;

	private String fareName;

	private String fareType;

	private String difference;

	private double fareAmount;

	private Long fareId;

	private Long organizationId;

	public String getFareName() {
		return fareName;
	}

	public void setFareName(String fareName) {
		this.fareName = fareName;
	}

	public String getFareType() {
		return fareType;
	}

	public void setFareType(String fareType) {
		this.fareType = fareType;
	}

	public String getDifference() {
		return difference;
	}

	public void setDifference(String difference) {
		this.difference = difference;
	}

	public double getFareAmount() {
		return fareAmount;
	}

	public void setFareAmount(double fareAmount) {
		this.fareAmount = fareAmount;
	}

	public Long getPtoId() {
		return ptoId;
	}

	public void setPtoId(Long ptoId) {
		this.ptoId = ptoId;
	}

	public Long getFareId() {
		return fareId;
	}

	public void setFareId(Long fareId) {
		this.fareId = fareId;
	}

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FareRegistrationRequest [ptoId=");
		builder.append(ptoId);
		builder.append(", fareName=");
		builder.append(fareName);
		builder.append(", fareType=");
		builder.append(fareType);
		builder.append(", difference=");
		builder.append(difference);
		builder.append(", fareAmount=");
		builder.append(fareAmount);
		builder.append(", fareId=");
		builder.append(fareId);
		builder.append(", organizationId=");
		builder.append(organizationId);
		builder.append("]");
		return builder.toString();
	}

}
