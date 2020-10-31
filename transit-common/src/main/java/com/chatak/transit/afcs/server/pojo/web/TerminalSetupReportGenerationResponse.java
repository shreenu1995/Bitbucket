package com.chatak.transit.afcs.server.pojo.web;

import java.util.List;

public class TerminalSetupReportGenerationResponse extends WebResponse {

	private static final long serialVersionUID = 7083508861668124489L;
	
	private List<TerminalsetupReportResponse> listOfterminalSetupResponse;
	
	private int noOfRecords;
	
	public int getNoOfRecords() {
		return noOfRecords;
	}

	public void setNoOfRecords(int noOfRecords) {
		this.noOfRecords = noOfRecords;
	}

	public List<TerminalsetupReportResponse> getListOfterminalSetupResponse() {
		return listOfterminalSetupResponse;
	}

	public void setListOfterminalSetupResponse(List<TerminalsetupReportResponse> listOfterminalSetupResponse) {
		this.listOfterminalSetupResponse = listOfterminalSetupResponse;
	}

}
