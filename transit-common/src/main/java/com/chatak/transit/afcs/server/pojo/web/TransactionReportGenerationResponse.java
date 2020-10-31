package com.chatak.transit.afcs.server.pojo.web;

import java.util.List;

public class TransactionReportGenerationResponse extends WebResponse {

	private static final long serialVersionUID = 1L;
	private List<TicketTxnDataResponse> txnResponse;

	private int noOfRecords;
	
	public List<TicketTxnDataResponse> getTxnResponse() {
		return txnResponse;
	}

	public void setTxnResponse(List<TicketTxnDataResponse> txnResponse) {
		this.txnResponse = txnResponse;
	}

	public int getNoOfRecords() {
		return noOfRecords;
	}

	public void setNoOfRecords(int noOfRecords) {
		this.noOfRecords = noOfRecords;
	}
	
	@Override
	public String toString() {
		StringBuilder response = new StringBuilder();
		return response.append(" statusCode=").append(getStatusCode()).append(", statusMessage=")
				.append(getStatusMessage()).append("TransactionReportGenerationResponse [txnResponse=")
				.append(txnResponse).append("]").toString();
	}

}
