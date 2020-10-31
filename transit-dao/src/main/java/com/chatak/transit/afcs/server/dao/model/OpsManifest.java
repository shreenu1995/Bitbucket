package com.chatak.transit.afcs.server.dao.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "ops_manifest")
@IdClass(value = IdForOpsManifest.class)
public class OpsManifest implements Serializable {

	private static final long serialVersionUID = 6648630344900471873L;

	@Id
	@SequenceGenerator(name = "seq_ops_manifest_id", sequenceName = "seq_ops_manifest_id")
	@GeneratedValue(generator = "seq_ops_manifest_id")
	@Column(name = "opsManifest_id")
	private Long opsManifestId;

	@Id
	@SequenceGenerator(name = "seq_ops_manifest_depotCode", sequenceName = "seq_ops_manifest_depotCode")
	@GeneratedValue(generator = "seq_ops_manifest_depotCode")
	@Column(name = "depot_code")
	private Long depotCode;

	@Column(name = "organization_id")
	private Long organizationId;

	@Column(name = "pto_id")
	private Long ptoId;

	@Column(name = "device_no")
	private Long deviceNo;

	@Column(name = "operator_name")
	private Long operatorId;

	@Column(name = "depot_name")
	private Long depotId;

	@Column(name = "date")
	private Timestamp date;

	@Column(name = "status")
	private String status;

	public Long getOpsManifestId() {
		return opsManifestId;
	}

	public void setOpsManifestId(Long opsManifestId) {
		this.opsManifestId = opsManifestId;
	}

	public Long getDepotCode() {
		return depotCode;
	}

	public void setDepotCode(Long depotCode) {
		this.depotCode = depotCode;
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

	public Long getDeviceNo() {
		return deviceNo;
	}

	public void setDeviceNo(Long deviceNo) {
		this.deviceNo = deviceNo;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	public Long getDepotId() {
		return depotId;
	}

	public void setDepotId(Long depotId) {
		this.depotId = depotId;
	}

}

class IdForOpsManifest implements Serializable {

	private static final long serialVersionUID = 6589116798984075145L;

	Long opsManifestId;
	Long depotCode;
}