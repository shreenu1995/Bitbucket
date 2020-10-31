package com.chatak.transit.afcs.server.pojo.web;

import java.util.List;

public class StageSearchRequest extends StopRegistrationRequest {

	private static final long serialVersionUID = -4309805500218867725L;

	private String stageName;

	private int pageIndex;

	private String status;

	private List<StopRegistrationRequest> dataFieldList;
	
	private int pageSize;
	
	private String organizationName;
	
	private String ptoName;
	
	private String routeName;

	@Override
	public String getRouteName() {
		return routeName;
	}

	@Override
	public void setRouteName(String routeName) {
		this.routeName = routeName;
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

	@Override
	public String getStageName() {
		return stageName;
	}

	@Override
	public void setStageName(String stageName) {
		this.stageName = stageName;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<StopRegistrationRequest> getDataFieldList() {
		return dataFieldList;
	}

	public void setDataFieldList(List<StopRegistrationRequest> dataFieldList) {
		this.dataFieldList = dataFieldList;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}
