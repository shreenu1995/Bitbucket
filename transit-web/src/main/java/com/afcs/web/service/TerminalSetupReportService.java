package com.afcs.web.service;

import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.TerminalSetupReportGenerationResponse;
import com.chatak.transit.afcs.server.pojo.web.TerminalsetupReportGenerationRequest;

public interface TerminalSetupReportService {
	
	TerminalSetupReportGenerationResponse generateTerminalSetupReport(TerminalsetupReportGenerationRequest request) throws AFCSException;

}
