package com.chatak.transit.afcs.server.pojo.web;

import java.util.List;

public class StopSearchResponse extends WebResponse {

	private static final long serialVersionUID = 1L;

	private List<StopSearchRequest> stopSearchList;

	private int noOfRecords;

	private String stopName;

	private String ptoId;

	public String getPtoId() {
		return ptoId;
	}

	public void setPtoId(String ptoId) {
		this.ptoId = ptoId;
	}

	public String getStopName() {
		return stopName;
	}

	public void setStopName(String stopName) {
		this.stopName = stopName;
	}

	public List<StopSearchRequest> getStopSearchList() {
		return stopSearchList;
	}

	public void setStopSearchList(List<StopSearchRequest> stopSearchList) {
		this.stopSearchList = stopSearchList;
	}

	public int getNoOfRecords() {
		return noOfRecords;
	}

	public void setNoOfRecords(int noOfRecords) {
		this.noOfRecords = noOfRecords;
	}

}
