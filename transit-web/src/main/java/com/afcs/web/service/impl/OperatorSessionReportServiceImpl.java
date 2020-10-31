package com.afcs.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.afcs.web.service.OperatorSessionReportService;
import com.afcs.web.util.JsonUtil;
import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.OperatorSessionReportGenerationRequest;
import com.chatak.transit.afcs.server.pojo.web.OperatorSessionReportGenerationResponse;
import com.chatak.transit.afcs.server.util.StringUtil;

@Service
public class OperatorSessionReportServiceImpl implements OperatorSessionReportService{
	
	@Autowired
	JsonUtil jsonUtil;
	
	@Autowired
	Environment properties;

	@Override
	public OperatorSessionReportGenerationResponse generateOperatorSessionReport(
			OperatorSessionReportGenerationRequest request) throws AFCSException {
		OperatorSessionReportGenerationResponse response = jsonUtil.postRequest(request,
				OperatorSessionReportGenerationResponse.class, "offline/operatorSessionReport");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty("afcs.server.not.responding"));
		}
		return response;
	}

}
