package com.afcs.web.service;

import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.PtoSummaryRequest;
import com.chatak.transit.afcs.server.pojo.web.PtoSummaryResponse;

public interface PtoSummaryService {

	public PtoSummaryResponse searchPTOSummaryReport(PtoSummaryRequest request) throws AFCSException;
}
