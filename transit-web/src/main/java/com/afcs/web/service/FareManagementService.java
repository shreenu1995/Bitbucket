package com.afcs.web.service;

import java.util.List;

import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.BulkUploadRequest;
import com.chatak.transit.afcs.server.pojo.web.FareRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.FareRegistrationResponse;
import com.chatak.transit.afcs.server.pojo.web.FareSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.FareSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.FareUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;

public interface FareManagementService {
	
	FareRegistrationResponse fareRegistration(FareRegistrationRequest fareRegistrationRequest) throws AFCSException;
	
	FareSearchResponse searchFare(FareSearchRequest fareSearchRequest) throws AFCSException;

	WebResponse updateFareManagement(FareUpdateRequest request)throws AFCSException;
	
	FareSearchResponse updateFareManagementStatus(FareUpdateRequest request) throws AFCSException;

	FareSearchResponse bulkFareCreate(List<BulkUploadRequest> bulkUploadRequest) throws AFCSException;
}
