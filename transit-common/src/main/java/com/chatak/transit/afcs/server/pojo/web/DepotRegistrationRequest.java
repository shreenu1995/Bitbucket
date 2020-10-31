package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;

public class DepotRegistrationRequest extends LoginResponseParameters implements Serializable {

	private static final long serialVersionUID = 7160647493750870311L;

	private Long organizationId;

	private String ptoName;

	private String depotName;

	private String depotShortName;

	private String depotIncharge;

	private String mobile;

	private String status;
	
	private Long ptoId;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPtoName() {
		return ptoName;
	}

	public void setPtoName(String ptoName) {
		this.ptoName = ptoName;
	}

	public String getDepotName() {
		return depotName;
	}

	public void setDepotName(String depotName) {
		this.depotName = depotName;
	}

	public String getDepotShortName() {
		return depotShortName;
	}

	public void setDepotShortName(String depotShortName) {
		this.depotShortName = depotShortName;
	}

	public String getDepotIncharge() {
		return depotIncharge;
	}

	public void setDepotIncharge(String depotIncharge) {
		this.depotIncharge = depotIncharge;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	public Long getPtoId() {
		return ptoId;
	}

	public void setPtoId(Long ptoId) {
		this.ptoId = ptoId;
	}

}
