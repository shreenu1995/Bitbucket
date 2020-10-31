package com.afcs.web.service;

import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.FeeRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.FeeRegistrationResponse;
import com.chatak.transit.afcs.server.pojo.web.FeeSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.FeeSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.FeeUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.OrganisationNameList;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;

public interface FeeManagementService {

	FeeRegistrationResponse feeRegistration(FeeRegistrationRequest feeRegistrationRequest) throws AFCSException;

	FeeSearchResponse searchFee(FeeSearchRequest feeSearchRequest) throws AFCSException;
	
	FeeSearchResponse updateFeeStatus(FeeUpdateRequest request) throws AFCSException;
	
	WebResponse updateFeeProfile(FeeUpdateRequest request) throws AFCSException;
	
	OrganisationNameList getOrganisationList();

}