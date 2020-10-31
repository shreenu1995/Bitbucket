package com.chatak.transit.afcs.server.service;

import com.chatak.transit.afcs.server.pojo.web.PassengerAnalysisRequest;
import com.chatak.transit.afcs.server.pojo.web.PassengerAnalysisResponse;

public interface PassengerAnalysisService {

	public PassengerAnalysisResponse searchPassengerAnalysisReport(PassengerAnalysisRequest request);
}
