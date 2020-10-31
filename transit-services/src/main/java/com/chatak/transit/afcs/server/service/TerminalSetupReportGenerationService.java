package com.chatak.transit.afcs.server.service;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import com.chatak.transit.afcs.server.pojo.web.TerminalSetupReportGenerationResponse;
import com.chatak.transit.afcs.server.pojo.web.TerminalsetupReportGenerationRequest;

public interface TerminalSetupReportGenerationService {
	public TerminalSetupReportGenerationResponse generateTerminalSetupReport(
			TerminalsetupReportGenerationRequest request, TerminalSetupReportGenerationResponse response,
			HttpServletResponse httpResponse) throws IOException;

	void checkTerminalSetupReportErrors(TerminalsetupReportGenerationRequest request,
			TerminalSetupReportGenerationResponse response);

}
