package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.chatak.transit.afcs.server.constants.Constants;

public class RouteRegistrationRequest extends AddStages implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "chatak.afcs.service.user.id.required")
	@Pattern(regexp = Constants.EMAIL_REGXP, message = "chatak.afcs.service.user.id.invalid")
	@Size(min = 8, max = 32, message = "chatak.afcs.service.user.id.length.invalid")
	private String userId;
	private String routeName;
	private String fromRoute;
	private String toRoute;
	private String status;
	private Long routeId;
	private Long organizationId;
	private Long ptoId;
	private String routeCode;
	private List<AddStages> dataFieldList;
	private String ptoName;
	
	public String getPtoName() {
		return ptoName;
	}

	public void setPtoName(String ptoName) {
		this.ptoName = ptoName;
	}

	public void setDataFieldList(List<AddStages> dataFieldList) {
		this.dataFieldList = dataFieldList;
	}

	public List<AddStages> getDataFieldList() {
		return dataFieldList;
	}

	@Override
	public String getUserId() {
		return userId;
	}

	@Override
	public void setUserId(String userId) {
		this.userId = userId;
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

	public String getRouteCode() {
		return routeCode;
	}

	public void setRouteCode(String routeCode) {
		this.routeCode = routeCode;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RouteRegistrationRequest [userId=");
		builder.append(userId);
		builder.append(", routeName=");
		builder.append(routeName);
		builder.append(", fromRoute=");
		builder.append(fromRoute);
		builder.append(", toRoute=");
		builder.append(toRoute);
		builder.append(", status=");
		builder.append(status);
		builder.append(", routeId=");
		builder.append(routeId);
		builder.append(", organizationId=");
		builder.append(organizationId);
		builder.append(", ptoId=");
		builder.append(ptoId);
		builder.append(", routeCode=");
		builder.append(routeCode);
		builder.append("]");
		return builder.toString();
	}
}