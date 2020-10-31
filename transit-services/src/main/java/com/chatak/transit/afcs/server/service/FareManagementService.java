package com.chatak.transit.afcs.server.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.chatak.transit.afcs.server.dao.model.BulkUploadDetails;
import com.chatak.transit.afcs.server.pojo.web.BulkUploadRequest;
import com.chatak.transit.afcs.server.pojo.web.FareRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.FareRegistrationResponse;
import com.chatak.transit.afcs.server.pojo.web.FareSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.FareSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.FareStatusUpdate;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;

public interface FareManagementService {
	void validateFareRegistrationErrors(FareRegistrationRequest request, FareRegistrationResponse response);

	FareRegistrationResponse saveFareRegistrationRequest(FareRegistrationRequest request,
			HttpServletResponse httpResponse, FareRegistrationResponse response);

	FareSearchResponse searchFare(FareSearchRequest request);

	FareSearchResponse fareStatusUpdate(FareStatusUpdate fareStatusUpdate, WebResponse response);

	WebResponse fareUpdate(FareStatusUpdate fareUpdate, WebResponse response);
	
	FareSearchResponse processBulkUpload(List<BulkUploadRequest> request);
	
	List<BulkUploadDetails> getAllBulkFares(Long ptoId);
	
	List<BulkUploadDetails> getFareByStopId(String stopId);
}
