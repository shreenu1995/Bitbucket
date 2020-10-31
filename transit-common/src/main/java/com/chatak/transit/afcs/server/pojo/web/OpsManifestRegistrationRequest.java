package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;

public class OpsManifestRegistrationRequest implements Serializable {

	private static final long serialVersionUID = 7858280360045975857L;

	private Long opsManifestId;
	private Long organizationId;
	private Long ptoId;
	private Long depotId;
	private Long deviceNo;
	private String depotName;
	private String date;
	private String operatorName;
	private Long operatorId;
	private String status;

	public Long getOpsManifestId() {
		return opsManifestId;
	}

	public void setOpsManifestId(Long opsManifestId) {
		this.opsManifestId = opsManifestId;
	}

	public Long getOrganizationId() {
		return organizationId;
	}

	public Long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
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

	public Long getDepotId() {
		return depotId;
	}

	public void setDepotId(Long depotId) {
		this.depotId = depotId;
	}

	public Long getDeviceNo() {
		return deviceNo;
	}

	public void setDeviceNo(Long deviceNo) {
		this.deviceNo = deviceNo;
	}

	public String getDepotName() {
		return depotName;
	}

	public void setDepotName(String depotName) {
		this.depotName = depotName;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
