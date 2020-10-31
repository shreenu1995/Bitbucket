package com.chatak.transit.afcs.server.pojo.web;

import java.util.List;

public class RouteSearchResponse extends WebResponse {

	private static final long serialVersionUID = 1L;

	private List<RouteSearchRequest> listORoutes;

	private int totalRecords;

	private String routeName;

	private Long ptoId;

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

	public List<RouteSearchRequest> getListORoutes() {
		return listORoutes;
	}

	public void setListORoutes(List<RouteSearchRequest> listORoutes) {
		this.listORoutes = listORoutes;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

}
