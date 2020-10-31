package com.chatak.transit.afcs.server.pojo.web;

import java.util.List;

public class PassengerAnalysisResponse extends WebResponse {

	private static final long serialVersionUID = 1L;

	private List<PassengerAnalysisRequest> listOfPassengerAnalysisRequest;

	private int totalNoOfRecords;

	public List<PassengerAnalysisRequest> getListOfPassengerAnalysisRequest() {
		return listOfPassengerAnalysisRequest;
	}

	public void setListOfPassengerAnalysisRequest(List<PassengerAnalysisRequest> listOfPassengerAnalysisRequest) {
		this.listOfPassengerAnalysisRequest = listOfPassengerAnalysisRequest;
	}

	public int getTotalNoOfRecords() {
		return totalNoOfRecords;
	}

	public void setTotalNoOfRecords(int totalNoOfRecords) {
		this.totalNoOfRecords = totalNoOfRecords;
	}

}
