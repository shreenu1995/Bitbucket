package com.chatak.transit.afcs.server.pojo.web;

import java.util.List;

public class TransactionIdList extends WebResponse {

	private static final long serialVersionUID = -1881410074111306427L;

	private List<TransactionsData> txnIdList;

	public List<TransactionsData> getTransactionIdList() {
		return txnIdList;
	}

	public void setTransactionIdList(List<TransactionsData> transactionIdList) {
		this.txnIdList = transactionIdList;
	}

	@Override
	public String toString() {

		StringBuilder response = new StringBuilder();
		return response.append("TransactionIdList [").append(", getStatusCode()=").append(getStatusCode())
				.append(", getStatusMessage()=").append(getStatusMessage()).append(", getReservedResponse()=")
				.append(getReservedResponse()).append("]").toString();
	}

}
