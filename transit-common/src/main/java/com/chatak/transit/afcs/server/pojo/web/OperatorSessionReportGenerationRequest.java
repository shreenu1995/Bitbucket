package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;


public class OperatorSessionReportGenerationRequest  implements Serializable {

	private static final long serialVersionUID = 5211693984523497756L;
	private String userId;
	private String transactionId;
	private String ptoOperationId;
	private String generationDateStart;
	private String generationDateEnd;
	private int pageIndex;
	private int pageSize;

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getGenerationDateStart() {
		return generationDateStart;
	}

	public void setGenerationDateStart(String generationDateStart) {
		this.generationDateStart = generationDateStart;
	}

	public String getGenerationDateEnd() {
		return generationDateEnd;
	}

	public void setGenerationDateEnd(String generationDateEnd) {
		this.generationDateEnd = generationDateEnd;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {

		this.transactionId = transactionId;

	}

	public String getPtoOperationId() {
		return ptoOperationId;
	}

	public void setPtoOperationId(String ptoOperationId) {

		this.ptoOperationId = ptoOperationId;

	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

}
