package com.chatak.transit.afcs.server.pojo.web;

import java.sql.Timestamp;

public class PassengerAnalysisRequest extends LoginResponseParameters {

	private static final long serialVersionUID = 1L;

	private String organizationName;
	private String ptoName;
	private Timestamp date;
	private Long generalCount;
	private Long childrenCount;
	private Long seniorCitizenCount;
	private Long studentCount;
	private Long baggageCount;
	private int indexPage;
	private int pageSize;
	private Long orgId;
	private Long ptoMasterId;

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

	public Long getGeneralCount() {
		return generalCount;
	}

	public void setGeneralCount(Long generalCount) {
		this.generalCount = generalCount;
	}

	public Long getChildrenCount() {
		return childrenCount;
	}

	public void setChildrenCount(Long childrenCount) {
		this.childrenCount = childrenCount;
	}

	public Long getSeniorCitizenCount() {
		return seniorCitizenCount;
	}

	public void setSeniorCitizenCount(Long seniorCitizenCount) {
		this.seniorCitizenCount = seniorCitizenCount;
	}

	public Long getStudentCount() {
		return studentCount;
	}

	public void setStudentCount(Long studentCount) {
		this.studentCount = studentCount;
	}

	public Long getBaggageCount() {
		return baggageCount;
	}

	public void setBaggageCount(Long baggageCount) {
		this.baggageCount = baggageCount;
	}

	public int getIndexPage() {
		return indexPage;
	}

	public void setIndexPage(int indexPage) {
		this.indexPage = indexPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}
