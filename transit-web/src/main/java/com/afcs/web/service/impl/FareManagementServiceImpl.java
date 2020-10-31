package com.afcs.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.afcs.web.service.FareManagementService;
import com.afcs.web.util.JsonUtil;
import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.BulkUploadRequest;
import com.chatak.transit.afcs.server.pojo.web.FareRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.FareRegistrationResponse;
import com.chatak.transit.afcs.server.pojo.web.FareSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.FareSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.FareUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.util.StringUtil;

@Service
public class FareManagementServiceImpl implements FareManagementService {

	@Autowired
	private JsonUtil jsonUtil;

	@Autowired
	Environment properties;

	private static final String AFCS_SERVER_ERROR = "afcs.server.not.responding";

	@Override
	public FareRegistrationResponse fareRegistration(FareRegistrationRequest fareRegistrationRequest)
			throws AFCSException {
		FareRegistrationResponse response = jsonUtil.postRequest(fareRegistrationRequest,
				FareRegistrationResponse.class, "online/fare/fareRegistration");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

	@Override
	public FareSearchResponse searchFare(FareSearchRequest fareSearchRequest) throws AFCSException {
		FareSearchResponse response = jsonUtil.postRequest(fareSearchRequest, FareSearchResponse.class,
				"online/fare/searchFare");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}
	@Override
	public WebResponse updateFareManagement(FareUpdateRequest request) throws AFCSException {
		WebResponse response = jsonUtil.postRequest(request, WebResponse.class, "/online/fare/fareUpdate");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}
	@Override
	public FareSearchResponse updateFareManagementStatus(FareUpdateRequest request) throws AFCSException {
		FareSearchResponse response = jsonUtil.postRequest(request, FareSearchResponse.class, "/online/fare/fareStatusUpdate");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

	@Override
	public FareSearchResponse bulkFareCreate(List<BulkUploadRequest> request) throws AFCSException {
		FareSearchResponse response = jsonUtil.postRequest(request, FareSearchResponse.class, "/online/fare/bulkUpload");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}
}
