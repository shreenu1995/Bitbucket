package com.chatak.transit.afcs.server.dao.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "stop_profile")
@IdClass(value = IdForStop.class)
public class StopProfile implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2438482581170364602L;

	@Id
	@SequenceGenerator(name = "seq_stop_profile", sequenceName = "seq_stop_profile")
	@GeneratedValue(generator = "seq_stop_profile")
	@Column(name = "stop_id")
	private Long stopId;

	@Column(name = "organization_id")
	private Long organizationId;

	@Column(name = "pto_id")
	private Long ptoId;

	@Column(name = "stage_id")
	private Long stageId;

	@Column(name = "route_Id")
	private Long routeId;

	@Column(name = "status")
	private String status;

	@Column(name = "stop_name")
	private String stopName;

	@Column(name = "stop_sequence_number")
	private int stopSequenceNumber;

	@Column(name = "distance")
	private double distance;

	@Column(name = "reason")
	private String reason;

	public Long getStopId() {
		return stopId;
	}

	public void setStopId(Long stopId) {
		this.stopId = stopId;
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

	public Long getStageId() {
		return stageId;
	}

	public void setStageId(Long stageId) {
		this.stageId = stageId;
	}

	public Long getRouteId() {
		return routeId;
	}

	public void setRouteId(Long routeId) {
		this.routeId = routeId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStopName() {
		return stopName;
	}

	public void setStopName(String stopName) {
		this.stopName = stopName;
	}

	public int getStopSequenceNumber() {
		return stopSequenceNumber;
	}

	public void setStopSequenceNumber(int stopSequenceNumber) {
		this.stopSequenceNumber = stopSequenceNumber;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}

class IdForStop implements Serializable {

	private static final long serialVersionUID = -3229506170759136584L;
	Long stopId;

}
