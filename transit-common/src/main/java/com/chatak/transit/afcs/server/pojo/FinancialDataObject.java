package com.chatak.transit.afcs.server.pojo;

import java.io.Serializable;

public class FinancialDataObject implements Serializable {

	private static final long serialVersionUID = -6871050348644723582L;
	private String txnType;
	private String count;
	private String amount;

	public FinancialDataObject(String txnType, String count, String amount) {
		this.txnType = txnType;
		this.count = count;
		this.amount = amount;
	}

	public String gettxnType() {
		return txnType;
	}

	public void settxnType(String txnType) {
		this.txnType = txnType;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {

		StringBuilder response = new StringBuilder();
		return response.append("FinancialDataObject [txnType=").append(txnType).append(", count=").append(count)
				.append(", amount=").append(amount).append("]").toString();

	}

}
