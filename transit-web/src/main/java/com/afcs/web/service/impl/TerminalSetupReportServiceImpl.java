package com.afcs.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.afcs.web.service.TerminalSetupReportService;
import com.afcs.web.util.JsonUtil;
import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.TerminalSetupReportGenerationResponse;
import com.chatak.transit.afcs.server.pojo.web.TerminalsetupReportGenerationRequest;
import com.chatak.transit.afcs.server.util.StringUtil;

@Service
public class TerminalSetupReportServiceImpl implements TerminalSetupReportService {

	@Autowired
	JsonUtil jsonUtil;

	@Autowired
	Environment properties;

	@Override
	public TerminalSetupReportGenerationResponse generateTerminalSetupReport(
			TerminalsetupReportGenerationRequest request) throws AFCSException {

		TerminalSetupReportGenerationResponse response = jsonUtil.postRequest(request,
				TerminalSetupReportGenerationResponse.class, "online/generateTerminalSetupReport");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty("afcs.server.not.responding"));
		}
		return response;
	}

}