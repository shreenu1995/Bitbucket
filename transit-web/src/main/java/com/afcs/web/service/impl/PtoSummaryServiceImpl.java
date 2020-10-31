package com.afcs.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.afcs.web.service.PtoSummaryService;
import com.afcs.web.util.JsonUtil;
import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.PtoSummaryRequest;
import com.chatak.transit.afcs.server.pojo.web.PtoSummaryResponse;
import com.chatak.transit.afcs.server.util.StringUtil;

@Service
public class PtoSummaryServiceImpl implements PtoSummaryService {

	@Autowired
	private JsonUtil jsonUtil;

	@Autowired
	Environment properties;

	private static final String AFCS_SERVER_ERROR = "afcs.server.not.responding";

	@Override
	public PtoSummaryResponse searchPTOSummaryReport(PtoSummaryRequest request) throws AFCSException {
		PtoSummaryResponse ptoSummaryResponse = jsonUtil.postRequest(request, PtoSummaryResponse.class,
				"online/searchPTOSummaryReport");
		if (StringUtil.isNull(ptoSummaryResponse)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return ptoSummaryResponse;
	}

}
