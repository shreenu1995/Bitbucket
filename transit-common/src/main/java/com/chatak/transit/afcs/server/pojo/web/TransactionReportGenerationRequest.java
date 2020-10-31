package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;

public class TransactionReportGenerationRequest extends LoginResponseParameters implements Serializable {

	private static final long serialVersionUID = 5268952577722245998L;

	private Long ptoId;
	private String userId;
	private String transactionId;
	private String generationDateStart;
	private String generationDateEnd;
	private String organizationId;
	private Integer indexPage;
	private Integer pageIndex;
	private Integer pageSize;
	private Long operatorId;

	public Long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public Long getPtoId() {
		return ptoId;
	}

	public void setPtoId(Long ptoId) {
		this.ptoId = ptoId;
	}

	@Override
	public String getUserId() {
		return userId;
	}

	@Override
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public Integer getIndexPage() {
		return indexPage;
	}

	public void setIndexPage(Integer indexPage) {
		this.indexPage = indexPage;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
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

	@Override
	public String toString() {
		StringBuilder request = new StringBuilder();
		return request.append("TransactionReportGenerationRequest [ptoId=").append(ptoId).append(", userName=")
				.append(userId).append(", transactionId=").append(transactionId).append(", generationDateStart=")
				.append(generationDateStart).append(", generationDateEnd=").append(generationDateEnd).append("]")
				.toString();
	}
}
