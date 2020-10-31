package com.chatak.transit.afcs.server.pojo.web;

import java.util.List;

public class TransactionReportResponse extends WebResponse {

	private static final long serialVersionUID = 4908836173387892444L;

	private List<TransactionReportRequest> txnReportResponse;

	private int noOfRecords;

	public List<TransactionReportRequest> getTxnReportResponse() {
		return txnReportResponse;
	}

	public void setTxnReportResponse(List<TransactionReportRequest> txnReportResponse) {
		this.txnReportResponse = txnReportResponse;
	}

	public int getNoOfRecords() {
		return noOfRecords;
	}

	public void setNoOfRecords(int noOfRecords) {
		this.noOfRecords = noOfRecords;
	}
	
}
