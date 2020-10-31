package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;
import java.util.List;

public class TransactionResponse extends WebResponse implements Serializable {

	private static final long serialVersionUID = -3536097436553375077L;

	private String organizationId;
	private String rollId;
	private List<String> ptoOperationIdList;
	private String userName;
	private String userType;
	private String ptoId;
	private String ptoName;
	private Long orgId;
	private Long ptoMasterId;
	private List<Long> listOfExistingFeature;

	public List<Long> getListOfExistingFeature() {
		return listOfExistingFeature;
	}

	public void setListOfExistingFeature(List<Long> listOfExistingFeature) {
		this.listOfExistingFeature = listOfExistingFeature;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getRollId() {
		return rollId;
	}

	public void setRollId(String rollId) {
		this.rollId = rollId;
	}

	public List<String> getPtoOperationIdList() {
		return ptoOperationIdList;
	}

	public void setPtoOperationIdList(List<String> ptoOperationIdList) {
		this.ptoOperationIdList = ptoOperationIdList;
	}

	public String getPtoId() {
		return ptoId;
	}

	public void setPtoId(String ptoId) {
		this.ptoId = ptoId;
	}

	public String getPtoName() {
		return ptoName;
	}

	public void setPtoName(String ptoName) {
		this.ptoName = ptoName;
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

	@Override
	public String toString() {

		StringBuilder response = new StringBuilder();
		return response.append("TransactionResponse [ ").append(" StatusCode=").append(getStatusCode())
				.append(", StatusMessage=").append(getStatusMessage()).append("]").toString();

	}

}