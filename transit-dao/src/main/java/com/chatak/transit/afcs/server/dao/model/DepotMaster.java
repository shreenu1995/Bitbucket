/**
 * @author Girmiti Software
 *
 */
package com.chatak.transit.afcs.server.dao.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "depot_master")
public class DepotMaster implements Serializable {

	private static final long serialVersionUID = -1840023577939172431L;

	@Id
	@SequenceGenerator(name = "seq_depot_master", sequenceName = "seq_depot_master")
	@GeneratedValue(generator = "seq_depot_master")

	@Column(name = "depot_id")
	private Long depotId;

	@Column(name = "created_date_time")
	private Timestamp createdDateTime;

	@Column(name = "status")
	private String status;

	@Column(name = "depot_name")
	private String depotName;

	@Column(name = "organization_id")
	private Long organizationId;

	@Column(name = "pto_id")
	private Long ptoId;

	@Column(name = "updated_date_time")
	private Timestamp updatedDateTime;

	@Column(name = "reason")
	private String reason;
	
	@Column(name = "depot_short_name")
	private String depotShortName;

	@Column(name = "depot_incharge")
	private String depotIncharge;
	
	@Column(name = "depot_mobile")
	private String depotMobile;

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	

	public Timestamp getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(Timestamp createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDepotName() {
		return depotName;
	}

	public void setDepotName(String depotName) {
		this.depotName = depotName;
	}

	public Timestamp getUpdatedDateTime() {
		return updatedDateTime;
	}

	public void setUpdatedDateTime(Timestamp updatedDateTime) {
		this.updatedDateTime = updatedDateTime;
	}

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
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

	public String getDepotMobile() {
		return depotMobile;
	}

	public void setDepotMobile(String depotMobile) {
		this.depotMobile = depotMobile;
	}

	public Long getDepotId() {
		return depotId;
	}

	public void setDepotId(Long depotId) {
		this.depotId = depotId;
	}

	public Long getPtoId() {
		return ptoId;
	}

	public void setPtoId(Long ptoId) {
		this.ptoId = ptoId;
	}

}

