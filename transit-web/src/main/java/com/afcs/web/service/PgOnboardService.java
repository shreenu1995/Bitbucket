package com.afcs.web.service;

import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.PgRequest;
import com.chatak.transit.afcs.server.pojo.web.PgResponse;
import com.chatak.transit.afcs.server.pojo.web.PmOnboardingRequest;
import com.chatak.transit.afcs.server.pojo.web.PmOnboardingResponse;

public interface PgOnboardService {

	public PgResponse createPaygate(PgRequest pgRequest) throws AFCSException;
	
	public PgResponse searchPaygate(PgRequest pgRequest) throws AFCSException;
	
	public PgResponse deletePaygate(PgRequest pgRequest) throws AFCSException;
	
	public PmOnboardingResponse getPmByCurrency(PmOnboardingRequest onboardingRequest) throws AFCSException;
}
