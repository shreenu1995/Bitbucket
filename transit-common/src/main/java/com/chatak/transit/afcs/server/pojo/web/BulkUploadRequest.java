package com.chatak.transit.afcs.server.pojo.web;

public class BulkUploadRequest extends BaseRequest {

	private String routeCode;

	private String startStopCode;

	private String endStopCode;

	private String fareAmount;

	private String bulkErrorCause;

	private Integer pageIndex;

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public String getBulkErrorCause() {
		return bulkErrorCause;
	}

	public void setBulkErrorCause(String bulkErrorCause) {
		this.bulkErrorCause = bulkErrorCause;
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

	public String getFareAmount() {
		return fareAmount;
	}

	public void setFareAmount(String fareAmount) {
		this.fareAmount = fareAmount;
	}

	public String getEndStopCode() {
		return endStopCode;
	}

	public void setEndStopCode(String endStopCode) {
		this.endStopCode = endStopCode;
	}

}
