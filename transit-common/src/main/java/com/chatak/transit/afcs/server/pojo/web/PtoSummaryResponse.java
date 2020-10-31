package com.chatak.transit.afcs.server.pojo.web;

import java.util.List;

public class PtoSummaryResponse extends WebResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<PtoSummaryRequest> listOfPtoSummaryRequest;

	private int totalNoOfRecords;

	public List<PtoSummaryRequest> getListOfPtoSummaryRequest() {
		return listOfPtoSummaryRequest;
	}

	public void setListOfPtoSummaryRequest(List<PtoSummaryRequest> listOfPtoSummaryRequest) {
		this.listOfPtoSummaryRequest = listOfPtoSummaryRequest;
	}

	public int getTotalNoOfRecords() {
		return totalNoOfRecords;
	}

	public void setTotalNoOfRecords(int totalNoOfRecords) {
		this.totalNoOfRecords = totalNoOfRecords;
	}

}
