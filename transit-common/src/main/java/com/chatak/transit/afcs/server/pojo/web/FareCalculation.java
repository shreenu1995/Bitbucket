package com.chatak.transit.afcs.server.pojo.web;

public class FareCalculation {

	@Override
	public String toString() {
		return "FareCalculation [ptoId=" + ptoId + ", startStop=" + startStop + ", endStop=" + endStop + ", actualFare="
				+ actualFare + "]";
	}

	private String ptoId;
	private String startStop;
	private String endStop;
	private int actualFare;

	public String getPtoId() {
		return ptoId;
	}

	public void setPtoId(String ptoId) {
		this.ptoId = ptoId;
	}

	public String getStartStop() {
		return startStop;
	}

	public void setStartStop(String startStop) {
		this.startStop = startStop;
	}

	public String getEndStop() {
		return endStop;
	}

	public void setEndStop(String endStop) {
		this.endStop = endStop;
	}

	public int getActualFare() {
		return actualFare;
	}

	public void setActualFare(int actualFare) {
		this.actualFare = actualFare;
	}

}