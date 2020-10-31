package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;
import java.util.List;

public class StateListResponse  extends WebResponse implements Serializable {

	private static final long serialVersionUID = 7275924277509200648L;
	
	private List<StateRequest> stateList;

	public List<StateRequest> getStateList() {
		return stateList;
	}

	public void setStateList(List<StateRequest> stateList) {
		this.stateList = stateList;
	}
}
