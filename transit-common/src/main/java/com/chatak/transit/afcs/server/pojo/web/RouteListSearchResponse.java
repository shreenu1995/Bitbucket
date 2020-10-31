package com.chatak.transit.afcs.server.pojo.web;

import java.util.List;

public class RouteListSearchResponse extends WebResponse {

	private static final long serialVersionUID = -8597927689578375597L;
	
	private List<RouteSearchResponse> listRouteSearchResponse;
	
	private int noOfRecords;

	public List<RouteSearchResponse> getListRouteSearchResponse() {
		return listRouteSearchResponse;
	}

	public void setListRouteSearchResponse(List<RouteSearchResponse> listRouteSearchResponse) {
		this.listRouteSearchResponse = listRouteSearchResponse;
	}

	public int getNoOfRecords() {
		return noOfRecords;
	}

	public void setNoOfRecords(int noOfRecords) {
		this.noOfRecords = noOfRecords;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RouteListSearchResponse [listRouteSearchResponse=");
		builder.append(listRouteSearchResponse);
		builder.append(", noOfRecords=");
		builder.append(noOfRecords);
		builder.append("]");
		return builder.toString();
	}
	
}
