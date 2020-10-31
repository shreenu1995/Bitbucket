package com.chatak.transit.afcs.server.dao.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "stage_master")
@IdClass(value = IdForStage.class)
public class StageMaster implements Serializable {

	private static final long serialVersionUID = -1840023577939172431L;

	@Id
	@SequenceGenerator(name = "for_stage_id", sequenceName = "for_stage_id")
	@GeneratedValue(generator = "for_stage_id")
	@Column(name = "stage_id")
	private Long stageId;

	@Column(name = "organization_id")
	private Long organizationId;

	@Column(name = "pto_Id")
	private Long ptoId;
	
	@Column(name = "route_id")
	private Long routeId;
	
	@Column(name = "stage_sequence_number")
	private Integer stageSequenceNumber;
	
	@Column(name = "stage_name")
	private String stageName;

	@Column(name = "distance")
	private Double distance;

	@Column(name = "status")
	private String status;

	@Column(name = "reason")
	private String reason;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "stage_id", referencedColumnName = "stage_id")
	private List<StopProfile> stopMap;

	public Long getStageId() {
		return stageId;
	}

	public void setStageId(Long stageId) {
		this.stageId = stageId;
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

	public Long getRouteId() {
		return routeId;
	}

	public void setRouteId(Long routeId) {
		this.routeId = routeId;
	}

	public Integer getStageSequenceNumber() {
		return stageSequenceNumber;
	}

	public void setStageSequenceNumber(Integer stageSequenceNumber) {
		this.stageSequenceNumber = stageSequenceNumber;
	}

	public String getStageName() {
		return stageName;
	}

	public void setStageName(String stageName) {
		this.stageName = stageName;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public List<StopProfile> getStopMap() {
		return stopMap;
	}

	public void setStopMap(List<StopProfile> stopMap) {
		this.stopMap = stopMap;
	}
	
}

class IdForStage implements Serializable {

	private static final long serialVersionUID = -3229506170759136584L;
	Long stageId;

}
