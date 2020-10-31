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
@Table(name = "route_master")
@IdClass(value = IdForRoute.class)
public class RouteMaster implements Serializable {

	private static final long serialVersionUID = -1840023577939172431L;

	@Id
	@SequenceGenerator(name = "for_route_id", sequenceName = "for_route_id")
	@GeneratedValue(generator = "for_route_id")
	@Column(name = "route_id")
	private Long routeId;

	@Column(name = "organization_id")
	private Long organizationId;

	@Column(name = "pto_Id")
	private Long ptoId;
	
	@Column(name = "route_name")
	private String routeName;

	@Column(name = "from_route")
	private String fromRoute;

	@Column(name = "to_route")
	private String toRoute;

	@Column(name = "status")
	private String status;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "route_id", referencedColumnName = "route_id")
	private List<StageMaster> stageMap;

	@Column(name = "reason")
	private String reason;
	
	@Column(name = "route_code")
	private String routeCode;

	public Long getRouteId() {
		return routeId;
	}

	public void setRouteId(Long routeId) {
		this.routeId = routeId;
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

	public String getRouteName() {
		return routeName;
	}

	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}

	public String getFromRoute() {
		return fromRoute;
	}

	public void setFromRoute(String fromRoute) {
		this.fromRoute = fromRoute;
	}

	public String getToRoute() {
		return toRoute;
	}

	public void setToRoute(String toRoute) {
		this.toRoute = toRoute;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<StageMaster> getStageMap() {
		return stageMap;
	}

	public void setStageMap(List<StageMaster> stageMap) {
		this.stageMap = stageMap;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getRouteCode() {
		return routeCode;
	}

	public void setRouteCode(String routeCode) {
		this.routeCode = routeCode;
	}

}

class IdForRoute implements Serializable {

	private static final long serialVersionUID = -3229506170759136584L;
	Long routeId;

}