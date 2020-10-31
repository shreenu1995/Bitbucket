package com.chatak.transit.afcs.server.pojo.web;

import java.sql.Timestamp;

public class PtoSummaryRequest extends LoginResponseParameters {

	private static final long serialVersionUID = 1L;

	private String organizationName;
	private String ptoName;
	private Timestamp date;
	private Long noOfRoutes;
	private Long ticketsIssued;
	private Long totalRevenue;
	private int indexPage;
	private int pageSize;
	private Long orgId;
	private Long ptoMasterId;

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getPtoName() {
		return ptoName;
	}

	public void setPtoName(String ptoName) {
		this.ptoName = ptoName;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public Long getNoOfRoutes() {
		return noOfRoutes;
	}

	public void setNoOfRoutes(Long noOfRoutes) {
		this.noOfRoutes = noOfRoutes;
	}

	public Long getTicketsIssued() {
		return ticketsIssued;
	}

	public void setTicketsIssued(Long ticketsIssued) {
		this.ticketsIssued = ticketsIssued;
	}

	public Long getTotalRevenue() {
		return totalRevenue;
	}

	public void setTotalRevenue(Long totalRevenue) {
		this.totalRevenue = totalRevenue;
	}

	public int getIndexPage() {
		return indexPage;
	}

	public void setIndexPage(int indexPage) {
		this.indexPage = indexPage;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getPtoMasterId() {
		return ptoMasterId;
	}

	public void setPtoMasterId(Long ptoMasterId) {
		this.ptoMasterId = ptoMasterId;
	}

}
