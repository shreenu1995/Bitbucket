package com.afcs.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.afcs.web.service.TransactionReportService;
import com.afcs.web.util.JsonUtil;
import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.TransactionReportGenerationRequest;
import com.chatak.transit.afcs.server.pojo.web.TransactionReportGenerationResponse;
import com.chatak.transit.afcs.server.util.StringUtil;

@Service
public class TransactionReportServiceImpl implements TransactionReportService {
	
	@Autowired
	JsonUtil jsonUtil;
	
	@Autowired
	Environment properties;
	
	private static final String SERVER_RESPONSE = "afcs.server.not.responding";
	
	@Override
	public TransactionReportGenerationResponse generateTransactionReport(TransactionReportGenerationRequest request) throws AFCSException {
		TransactionReportGenerationResponse response = jsonUtil.postRequest(request, TransactionReportGenerationResponse.class, "online/generateTransactionReport");
		if(StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(SERVER_RESPONSE));
		}
		return response; 
	}
	
}
