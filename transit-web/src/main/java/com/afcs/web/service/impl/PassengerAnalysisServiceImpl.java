package com.afcs.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.afcs.web.service.PassengerAnalysisService;
import com.afcs.web.util.JsonUtil;
import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.PassengerAnalysisRequest;
import com.chatak.transit.afcs.server.pojo.web.PassengerAnalysisResponse;
import com.chatak.transit.afcs.server.util.StringUtil;

@Service
public class PassengerAnalysisServiceImpl implements PassengerAnalysisService {

	@Autowired
	private JsonUtil jsonUtil;
	
	@Autowired
	Environment properties;

	private static final String AFCS_SERVER_ERROR = "afcs.server.not.responding";

	@Override
	public PassengerAnalysisResponse searchPassengerAnalysisReport(PassengerAnalysisRequest request) throws AFCSException {
		PassengerAnalysisResponse passengerAnalysisResponse = jsonUtil.postRequest(request,
				PassengerAnalysisResponse.class, "online/searchPassengerAnalysisReport");
		if(StringUtil.isNull(passengerAnalysisResponse)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return passengerAnalysisResponse;
	}

}
