package com.chatak.transit.afcs.server.pojo.web;

import java.util.List;

public class OpsManifestSearchResponse extends WebResponse {

	private static final long serialVersionUID = -9107970405717117728L;

	private List<OpsManifestSearchRequest> opsManifestList;

	private int totalRecords;

	public List<OpsManifestSearchRequest> getOpsManifestList() {
		return opsManifestList;
	}

	public void setOpsManifestList(List<OpsManifestSearchRequest> opsManifestList) {
		this.opsManifestList = opsManifestList;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

}
