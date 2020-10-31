package com.chatak.transit.afcs.server.service;

import com.chatak.transit.afcs.server.pojo.web.PgRequest;
import com.chatak.transit.afcs.server.pojo.web.PgResponse;

public interface PgOnboardingService {

	public PgResponse createPaygate(PgRequest pgRequest);
	
	public PgResponse searchPaygate(PgRequest pgRequest);
	
	public PgResponse deletePaygate(PgRequest pgRequest);
}
