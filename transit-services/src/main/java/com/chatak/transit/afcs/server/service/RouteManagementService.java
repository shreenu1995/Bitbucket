package com.chatak.transit.afcs.server.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.chatak.transit.afcs.server.dao.model.RouteMaster;
import com.chatak.transit.afcs.server.pojo.web.BulkUploadRequest;
import com.chatak.transit.afcs.server.pojo.web.RouteRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.RouteRegistrationResponse;
import com.chatak.transit.afcs.server.pojo.web.RouteSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.RouteSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.RouteStatusUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;

public interface RouteManagementService {

	RouteRegistrationResponse routeRegistartion(RouteRegistrationRequest request, RouteRegistrationResponse response,
			HttpServletResponse httpResponse) throws IOException;

	void validateRouteRegistrationErrors(RouteRegistrationRequest request, WebResponse response);

	RouteSearchResponse searchRoute(RouteSearchRequest request);

	RouteSearchResponse viewRoute(RouteSearchRequest request);

	RouteSearchResponse getViewStagesOnRole(RouteSearchRequest request);

	WebResponse updateRouteProfile(RouteRegistrationRequest request, WebResponse response,
			HttpServletResponse httpServletResponse);

	RouteSearchResponse updateRouteStatus(RouteStatusUpdateRequest request, WebResponse response);

	WebResponse deleteStage(RouteRegistrationRequest request, WebResponse response);

	List<RouteMaster> getAllRoutes(Long ptoId);
	
	RouteSearchResponse validateRouteCode(BulkUploadRequest bulkUploadRequest);
}
