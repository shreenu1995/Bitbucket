package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;

public class TransactionsData implements Serializable {

	private static final long serialVersionUID = 8879822967838561410L;
	
	private String transactionId;
	private String transactionName;

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getTransactionName() {
		return transactionName;
	}

	public void setTransactionName(String transactionName) {
		this.transactionName = transactionName;
	}
}
