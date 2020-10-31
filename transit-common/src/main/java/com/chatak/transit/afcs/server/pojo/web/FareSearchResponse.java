package com.chatak.transit.afcs.server.pojo.web;

import java.util.List;

public class FareSearchResponse extends WebResponse {

	private static final long serialVersionUID = -9107970405717117728L;

	private List<FareSearchRequest> fareList;

	private int totalRecords;

	private String status;

	private String fareName;

	public String getFareName() {
		return fareName;
	}

	public void setFareName(String fareName) {
		this.fareName = fareName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<FareSearchRequest> getFareList() {
		return fareList;
	}

	public void setFareList(List<FareSearchRequest> fareList) {
		this.fareList = fareList;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

}
