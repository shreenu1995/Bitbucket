package com.chatak.transit.afcs.server.service;

import com.chatak.transit.afcs.server.pojo.web.PtoSummaryRequest;
import com.chatak.transit.afcs.server.pojo.web.PtoSummaryResponse;

public interface PtoSummaryService {

	public PtoSummaryResponse searchPto(PtoSummaryRequest request);

}
