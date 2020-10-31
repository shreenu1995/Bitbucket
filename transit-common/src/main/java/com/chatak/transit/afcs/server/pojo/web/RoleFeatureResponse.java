package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;
import java.util.List;

public class RoleFeatureResponse extends WebResponse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<TransitFeatureRequest> featureList;

	public List<TransitFeatureRequest> getFeatureList() {
		return featureList;
	}

	public void setFeatureList(List<TransitFeatureRequest> featureList) {
		this.featureList = featureList;
	}

}
