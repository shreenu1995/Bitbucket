package com.chatak.transit.afcs.server.dao;

import com.chatak.transit.afcs.server.pojo.web.TerminalSetupReportGenerationResponse;
import com.chatak.transit.afcs.server.pojo.web.TerminalsetupReportGenerationRequest;

public interface TerminalSetupReportGenerationDao {

	boolean validationTerminalSetUpReport(TerminalsetupReportGenerationRequest request);

	public TerminalSetupReportGenerationResponse getReportAll(TerminalsetupReportGenerationRequest request);

}
