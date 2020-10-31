package com.afcs.web.service;

import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.PassengerAnalysisRequest;
import com.chatak.transit.afcs.server.pojo.web.PassengerAnalysisResponse;

public interface PassengerAnalysisService {

	public PassengerAnalysisResponse searchPassengerAnalysisReport(PassengerAnalysisRequest passengerAnalysisRequest) throws AFCSException; 
}
