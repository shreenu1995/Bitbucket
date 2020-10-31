package com.afcs.web.service;

import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.BulkUploadRequest;
import com.chatak.transit.afcs.server.pojo.web.RouteRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.RouteRegistrationResponse;
import com.chatak.transit.afcs.server.pojo.web.RouteSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.RouteSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.RouteStatusUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;

public interface RouteRegistrationManagementService {

	RouteRegistrationResponse routeRegistration(RouteRegistrationRequest request) throws AFCSException;
	
	RouteSearchResponse searchRoute(RouteSearchRequest request) throws AFCSException;

	WebResponse updateRouteProfile(RouteRegistrationRequest request) throws AFCSException;

	RouteSearchResponse updateRouteStatus(RouteStatusUpdateRequest request) throws AFCSException;

	RouteSearchResponse viewRoute(RouteSearchRequest request) throws AFCSException;

	RouteSearchResponse viewStageOnRoute(RouteSearchRequest request) throws AFCSException;

	WebResponse deleteStageById(RouteRegistrationRequest request) throws AFCSException;
	
	RouteSearchResponse validateRouteCode(BulkUploadRequest bulkUploadRequest) throws AFCSException;

}
