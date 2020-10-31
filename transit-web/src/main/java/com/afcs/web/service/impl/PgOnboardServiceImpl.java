package com.afcs.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.afcs.web.service.PgOnboardService;
import com.afcs.web.util.JsonUtil;
import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.PgRequest;
import com.chatak.transit.afcs.server.pojo.web.PgResponse;
import com.chatak.transit.afcs.server.pojo.web.PmOnboardingRequest;
import com.chatak.transit.afcs.server.pojo.web.PmOnboardingResponse;
import com.chatak.transit.afcs.server.util.StringUtil;

@Service
public class PgOnboardServiceImpl implements PgOnboardService {

	@Autowired
	private JsonUtil jsonUtil;

	@Autowired
	Environment properties;

	private static final String AFCS_SERVER_ERROR = "afcs.server.not.responding";

	@Override
	public PgResponse createPaygate(PgRequest pgRequest) throws AFCSException {
		PgResponse response = jsonUtil.postRequest(pgRequest, PgResponse.class,
				"pgOnboarding/createPaygate");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

	@Override
	public PgResponse searchPaygate(PgRequest pgRequest) throws AFCSException {
		PgResponse response = jsonUtil.postRequest(pgRequest, PgResponse.class,
				"pgOnboarding/searchPaygate");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

	@Override
	public PgResponse deletePaygate(PgRequest pgRequest) throws AFCSException {
		PgResponse response = jsonUtil.postRequest(pgRequest, PgResponse.class,
				"pgOnboarding/deletePaygate");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

	@Override
	public PmOnboardingResponse getPmByCurrency(PmOnboardingRequest onboardingRequest) throws AFCSException {
		PmOnboardingResponse response = jsonUtil.postRequest(onboardingRequest, PmOnboardingResponse.class,
				"merchantIsoOnboardingServices/fetchPMsByCurrency");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}
}
