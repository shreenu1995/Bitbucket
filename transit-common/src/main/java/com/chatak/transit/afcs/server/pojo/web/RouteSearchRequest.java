package com.chatak.transit.afcs.server.pojo.web;

import java.util.List;

public class RouteSearchRequest extends AddStages {

	private static final long serialVersionUID = 2969139458557927593L;

	private Long routeId;
	private Long organizationId;
	private Long ptoId;
	private String routeName;
	private String fromRoute;
	private String toRoute;
	private String status;
	private Integer pageIndex;
	private List<AddStages> dataFieldList;
	private String routeCode;
	private int pageSize;
	private String organizationName;
	private String ptoName;
	private Long stageId;

	public Long getStageId() {
		return stageId;
	}

	public void setStageId(Long stageId) {
		this.stageId = stageId;
	}

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

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public List<AddStages> getDataFieldList() {
		return dataFieldList;
	}

	public void setDataFieldList(List<AddStages> dataFieldList) {
		this.dataFieldList = dataFieldList;
	}

	public String getRouteCode() {
		return routeCode;
	}

	public void setRouteCode(String routeCode) {
		this.routeCode = routeCode;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RouteSearchRequest [routeId=");
		builder.append(routeId);
		builder.append(", organizationId=");
		builder.append(organizationId);
		builder.append(", ptoId=");
		builder.append(ptoId);
		builder.append(", routeName=");
		builder.append(routeName);
		builder.append(", fromRoute=");
		builder.append(fromRoute);
		builder.append(", toRoute=");
		builder.append(toRoute);
		builder.append(", status=");
		builder.append(status);
		builder.append(", pageIndex=");
		builder.append(pageIndex);
		builder.append(", dataFieldList=");
		builder.append(dataFieldList);
		builder.append(", routeCode=");
		builder.append(routeCode);
		builder.append(", pageSize=");
		builder.append(pageSize);
		builder.append(", getRouteId()=");
		builder.append(getRouteId());
		builder.append(", getOrganizationId()=");
		builder.append(getOrganizationId());
		builder.append(", getPtoId()=");
		builder.append(getPtoId());
		builder.append(", getRouteName()=");
		builder.append(getRouteName());
		builder.append(", getFromRoute()=");
		builder.append(getFromRoute());
		builder.append(", getToRoute()=");
		builder.append(getToRoute());
		builder.append(", getStatus()=");
		builder.append(getStatus());
		builder.append(", getPageIndex()=");
		builder.append(getPageIndex());
		builder.append(", getDataFieldList()=");
		builder.append(getDataFieldList());
		builder.append(", getRouteCode()=");
		builder.append(getRouteCode());
		builder.append(", getPageSize()=");
		builder.append(getPageSize());
		builder.append(", getStageName()=");
		builder.append(getStageName());
		builder.append(", getStageSequenceNumber()=");
		builder.append(getStageSequenceNumber());
		builder.append(", getDistance()=");
		builder.append(getDistance());
		builder.append(", getId()=");
		builder.append(getId());
		builder.append(", getMessage()=");
		builder.append(getMessage());
		builder.append(", getEmail()=");
		builder.append(getEmail());
		builder.append(", getUserId()=");
		builder.append(getUserId());
		builder.append(", getWalletId()=");
		builder.append(getWalletId());
		builder.append(", getAccessToken()=");
		builder.append(getAccessToken());
		builder.append(", getRefreshToken()=");
		builder.append(getRefreshToken());
		builder.append(", getServiceProviderId()=");
		builder.append(getServiceProviderId());
		builder.append(", getSubServiceProviderId()=");
		builder.append(getSubServiceProviderId());
		builder.append(", getExistingFeature()=");
		builder.append(getExistingFeature());
		builder.append(", getUserRoleId()=");
		builder.append(getUserRoleId());
		builder.append(", getMakerCheckerRequired()=");
		builder.append(getMakerCheckerRequired());
		builder.append(", getUserType()=");
		builder.append(getUserType());
		builder.append(", getUserName()=");
		builder.append(getUserName());
		builder.append(", getLoginMode()=");
		builder.append(getLoginMode());
		builder.append(", getReservedResponse()=");
		builder.append(getReservedResponse());
		builder.append(", getStatusCode()=");
		builder.append(getStatusCode());
		builder.append(", getStatusMessage()=");
		builder.append(getStatusMessage());
		builder.append(", toString()=");
		builder.append(super.toString());
		builder.append(", getClass()=");
		builder.append(getClass());
		builder.append(", hashCode()=");
		builder.append(hashCode());
		builder.append("]");
		return builder.toString();
	}

}