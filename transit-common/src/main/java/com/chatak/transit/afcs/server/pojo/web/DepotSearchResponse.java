package com.chatak.transit.afcs.server.pojo.web;

import java.util.List;

public class DepotSearchResponse extends WebResponse {

	private static final long serialVersionUID = 598024726402461318L;

	private List<DepotSearchRequest> depotListResponse;

	private int noOfRecords;

	private String depotName;

	public String getDepotName() {
		return depotName;
	}

	public void setDepotName(String depotName) {
		this.depotName = depotName;
	}

	public List<DepotSearchRequest> getDepotListResponse() {
		return depotListResponse;
	}

	public void setDepotListResponse(List<DepotSearchRequest> depotListResponse) {
		this.depotListResponse = depotListResponse;
	}

	public int getNoOfRecords() {
		return noOfRecords;
	}

	public void setNoOfRecords(int noOfRecords) {
		this.noOfRecords = noOfRecords;
	}

}
