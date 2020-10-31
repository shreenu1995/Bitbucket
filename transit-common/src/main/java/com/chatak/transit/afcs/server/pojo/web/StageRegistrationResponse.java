package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;

public class StageRegistrationResponse extends WebResponse implements Serializable {

	private static final long serialVersionUID = -1881410074111306427L;

	private Long stageId;

	private String organizationName;

	private String ptoName;

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

	public Long getStageId() {
		return stageId;
	}

	public void setStageId(Long stageId) {
		this.stageId = stageId;
	}

}
