package com.afcs.web.service;

import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.OperatorSessionReportGenerationRequest;
import com.chatak.transit.afcs.server.pojo.web.OperatorSessionReportGenerationResponse;

public interface OperatorSessionReportService {
	
	OperatorSessionReportGenerationResponse generateOperatorSessionReport(OperatorSessionReportGenerationRequest request) throws AFCSException;
}
