package com.chatak.transit.afcs.server.pojo.web;

import java.util.List;

public class PtoListResponse extends WebResponse {

	private static final long serialVersionUID = -5728704798795283733L;
	private List<PtoList> ptoList;

	private String ptoId;
	
	private Long ptoMasterId;

	public String getPtoId() {
		return ptoId;
	}

	public void setPtoId(String ptoId) {
		this.ptoId = ptoId;
	}

	public List<PtoList> getPtoList() {
		return ptoList;
	}

	public void setPtoList(List<PtoList> ptoList) {
		this.ptoList = ptoList;
	}

	public Long getPtoMasterId() {
		return ptoMasterId;
	}

	public void setPtoMasterId(Long ptoMasterId) {
		this.ptoMasterId = ptoMasterId;
	}

}
