package com.chatak.transit.afcs.server.dao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "bulk_upload_data")
public class BulkUploadDetails {

	@Id
	@SequenceGenerator(name = "seq_afcs_bulk_id", sequenceName = "seq_afcs_bulk_id")
	@GeneratedValue(generator = "seq_afcs_bulk_id")
	@Column(name = "id")
	private Long id;

	@Column(name = "pto_id")
	private Long ptoId;

	@Column(name = "route_code")
	private String routeCode;

	@Column(name = "start_stop_code")
	private String startStopCode;

	@Column(name = "end_stop_code")
	private String endStopCode;

	@Column(name = "fare_amount")
	private String fareAmount;

	public Long getPtoId() {
		return ptoId;
	}

	public void setPtoId(Long ptoId) {
		this.ptoId = ptoId;
	}

	public String getRouteCode() {
		return routeCode;
	}

	public void setRouteCode(String routeCode) {
		this.routeCode = routeCode;
	}

	public String getStartStopCode() {
		return startStopCode;
	}

	public void setStartStopCode(String startStopCode) {
		this.startStopCode = startStopCode;
	}

	public String getEndStopCode() {
		return endStopCode;
	}

	public void setEndStopCode(String endStopCode) {
		this.endStopCode = endStopCode;
	}

	public String getFareAmount() {
		return fareAmount;
	}

	public void setFareAmount(String fareAmount) {
		this.fareAmount = fareAmount;
	}

}
