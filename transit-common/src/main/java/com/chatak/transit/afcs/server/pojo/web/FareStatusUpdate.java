package com.chatak.transit.afcs.server.pojo.web;

public class FareStatusUpdate {

	private static final long serialVersionUID = 7231730901914885529L;

	private Long ptoId;

	private Long organizationId;

	private String fareName;

	private String fareType;

	private String difference;

	private double fareAmount;

	private Long fareId;

	private Integer pageIndex;

	private String status;
	
	private String reason;

	public Long getPtoId() {
		return ptoId;
	}

	public void setPtoId(Long ptoId) {
		this.ptoId = ptoId;
	}

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

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

	public Long getFareId() {
		return fareId;
	}

	public void setFareId(Long fareId) {
		this.fareId = fareId;
	}

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}
