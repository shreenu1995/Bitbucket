package com.afcs.web.service;

import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.TransitMasterListDataResponse;
import com.chatak.transit.afcs.server.pojo.web.TransitMasterRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.TransitMasterRegistrationResponse;
import com.chatak.transit.afcs.server.pojo.web.TransitMasterSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.TransitMasterSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.TransitMasterUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;

public interface TransitMasterService {

	TransitMasterRegistrationResponse transitMasterRegistrationResponse(
		TransitMasterRegistrationRequest transitMasterRegistrationRequest) throws AFCSException;

	TransitMasterSearchResponse searchTransitMaster(TransitMasterSearchRequest transitMasterSearchRequest)
			throws AFCSException;

	TransitMasterListDataResponse getTransitMasterListDataResponse(TransitMasterRegistrationRequest transitMasterRegistrationRequest) throws AFCSException;

	TransitMasterSearchResponse getTransitMasterEditViewData(TransitMasterSearchRequest searchRequest)throws AFCSException;

	WebResponse updateTransitMaster(TransitMasterUpdateRequest request)throws AFCSException;

	WebResponse updateTransitMasterStatus(TransitMasterRegistrationRequest request)throws AFCSException;

	TransitMasterListDataResponse getVersionNumberByPtoName(
			TransitMasterSearchRequest transitMasterSearchRequest) throws AFCSException;
	
}
