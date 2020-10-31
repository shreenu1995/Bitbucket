package com.chatak.transit.afcs.server.pojo.web;

public class RouteStatusUpdateRequest extends BaseRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1721487363101479647L;
	private Long routeId;
	private String status;

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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RouteStatusUpdateRequest [routeId=");
		builder.append(routeId);
		builder.append(", status=");
		builder.append(status);
		builder.append("]");
		return builder.toString();
	}

}
