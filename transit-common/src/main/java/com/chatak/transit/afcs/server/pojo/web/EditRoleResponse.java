package com.chatak.transit.afcs.server.pojo.web;

import java.util.List;
import java.util.Map;

public class EditRoleResponse extends WebResponse {

	private static final long serialVersionUID = 8519671020268336667L;

	private RoleRequest roleRequest;

	private Map<Long, List<TransitFeatureRequest>> transitFeatureMap;

	private List<Long> existingFeatures;

	public RoleRequest getRoleRequest() {
		return roleRequest;
	}

	public void setRoleRequest(RoleRequest roleRequest) {
		this.roleRequest = roleRequest;
	}

	public Map<Long, List<TransitFeatureRequest>> getTransitFeatureMap() {
		return transitFeatureMap;
	}

	public void setTransitFeatureMap(Map<Long, List<TransitFeatureRequest>> transitFeatureMap) {
		this.transitFeatureMap = transitFeatureMap;
	}

	public List<Long> getExistingFeatures() {
		return existingFeatures;
	}

	public void setExistingFeatures(List<Long> existingFeatures) {
		this.existingFeatures = existingFeatures;
	}

}
