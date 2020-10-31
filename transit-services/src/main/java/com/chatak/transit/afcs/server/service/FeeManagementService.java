package com.chatak.transit.afcs.server.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.chatak.transit.afcs.server.pojo.web.FeeRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.FeeRegistrationResponse;
import com.chatak.transit.afcs.server.pojo.web.FeeSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.FeeSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.FeeUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;

public interface FeeManagementService {

	FeeRegistrationResponse feeRegistration(FeeRegistrationRequest feeRegistrationRequest,
			HttpServletResponse httpResponse, FeeRegistrationResponse feeRegistrationResponse);

	FeeSearchResponse searchFee(FeeSearchRequest request);
	
	FeeSearchResponse updateFeeStatus(FeeRegistrationRequest request, WebResponse response);
	
	WebResponse updateFeeProfile(FeeUpdateRequest request, WebResponse response);
	
	List<String> getOrganisationList();

}