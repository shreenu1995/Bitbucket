package com.chatak.transit.afcs.server.pojo.web;

import java.util.List;

public class VehicleOnboardingSearchResponse extends WebResponse {

	private static final long serialVersionUID = 1L;
	
	private List<VehicleOnboardingResponse> listOfOnboarding;
	
	private List<VehicleOnboardingSearchRequest> orgnizationList;
	
	private int totalNoOfCount;

	public List<VehicleOnboardingSearchRequest> getOrgnizationList() {
		return orgnizationList;
	}

	public void setOrgnizationList(List<VehicleOnboardingSearchRequest> orgnizationList) {
		this.orgnizationList = orgnizationList;
	}

	public List<VehicleOnboardingResponse> getListOfOnboarding() {
		return listOfOnboarding;
	}

	public void setListOfOnboarding(List<VehicleOnboardingResponse> listOfOnboarding) {
		this.listOfOnboarding = listOfOnboarding;
	}

	public int getTotalNoOfCount() {
		return totalNoOfCount;
	}

	public void setTotalNoOfCount(int totalNoOfCount) {
		this.totalNoOfCount = totalNoOfCount;
	}

}
