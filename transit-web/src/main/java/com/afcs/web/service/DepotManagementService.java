package com.afcs.web.service;

import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.DepotProfileUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.DepotRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.DepotRegistrationResponse;
import com.chatak.transit.afcs.server.pojo.web.DepotSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.DepotSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.DepotStatusUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;

public interface DepotManagementService {
	
	DepotRegistrationResponse depotManagementRegistration(DepotRegistrationRequest request);

	DepotSearchResponse searchDepot(DepotSearchRequest depotSearchRequest) throws AFCSException;

	WebResponse updateDepotProfile(DepotProfileUpdateRequest request) throws AFCSException;

	DepotSearchResponse updateDepotStatus(DepotStatusUpdateRequest request) throws AFCSException;
}
