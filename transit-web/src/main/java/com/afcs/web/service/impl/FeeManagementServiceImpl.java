package com.afcs.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.afcs.web.service.FeeManagementService;
import com.afcs.web.util.JsonUtil;
import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.FeeRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.FeeRegistrationResponse;
import com.chatak.transit.afcs.server.pojo.web.FeeSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.FeeSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.FeeUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.OrganisationNameList;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.util.StringUtil;

@Service
public class FeeManagementServiceImpl implements FeeManagementService {

	@Autowired
	private JsonUtil jsonUtil;

	@Autowired
	Environment properties;

	private static final String AFCS_SERVER_ERROR = "afcs.server.not.responding";

	@Override
	public FeeRegistrationResponse feeRegistration(FeeRegistrationRequest feeRegistrationRequest) throws AFCSException {
		FeeRegistrationResponse response = jsonUtil.postRequest(feeRegistrationRequest, FeeRegistrationResponse.class,
				"online/feeRegistration");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

	@Override
	public FeeSearchResponse searchFee(FeeSearchRequest feeSearchRequest) throws AFCSException {
		FeeSearchResponse response = jsonUtil.postRequest(feeSearchRequest, FeeSearchResponse.class,
				"online/searchFee");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

	@Override
	public FeeSearchResponse updateFeeStatus(FeeUpdateRequest request) throws AFCSException {
		FeeSearchResponse response = jsonUtil.postRequest(request, FeeSearchResponse.class,
				"online/updateFeeStatus");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

	@Override
	public WebResponse updateFeeProfile(FeeUpdateRequest request) throws AFCSException {
		WebResponse response = jsonUtil.postRequest(request, WebResponse.class,
				"online/updateFeeProfile");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

	@Override
	public OrganisationNameList getOrganisationList() {
		return jsonUtil.postRequest(OrganisationNameList.class, "online/getOrganisationList");
	}

}