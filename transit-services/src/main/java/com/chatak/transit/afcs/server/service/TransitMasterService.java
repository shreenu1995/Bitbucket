package com.chatak.transit.afcs.server.service;

import javax.servlet.http.HttpServletResponse;

import com.chatak.transit.afcs.server.pojo.web.TransitMasterListDataResponse;
import com.chatak.transit.afcs.server.pojo.web.TransitMasterRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.TransitMasterRegistrationResponse;
import com.chatak.transit.afcs.server.pojo.web.TransitMasterSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.TransitMasterSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.TransitMasterUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;

public interface TransitMasterService {

	TransitMasterRegistrationResponse transitMasterRegistrationResponse(TransitMasterRegistrationRequest request,
			TransitMasterRegistrationResponse response);

	TransitMasterSearchResponse transitMasterSearchResponse(TransitMasterSearchRequest request);

	WebResponse updateTransitMaster(TransitMasterUpdateRequest request, HttpServletResponse httpResponse,
			WebResponse response);

	WebResponse updateTransitMasterStatus(TransitMasterRegistrationRequest request, WebResponse response);

	TransitMasterSearchResponse searchTransitMasterData(TransitMasterSearchRequest request);

	TransitMasterListDataResponse getInheritList(Long ptoId);
}
