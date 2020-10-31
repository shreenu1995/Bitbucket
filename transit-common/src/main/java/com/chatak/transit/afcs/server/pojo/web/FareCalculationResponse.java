package com.chatak.transit.afcs.server.pojo.web;

import java.util.List;

public class FareCalculationResponse {

	private List<FareCalculation> fareCalculationList;

	public List<FareCalculation> getFareCalculationList() {
		return fareCalculationList;
	}

	public void setFareCalculationList(List<FareCalculation> fareCalculationList) {
		this.fareCalculationList = fareCalculationList;
	}

}