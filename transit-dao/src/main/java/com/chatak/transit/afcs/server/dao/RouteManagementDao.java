package com.chatak.transit.afcs.server.dao;

import java.util.List;

import com.chatak.transit.afcs.server.dao.model.RouteMaster;
import com.chatak.transit.afcs.server.pojo.web.BulkUploadRequest;
import com.chatak.transit.afcs.server.pojo.web.RouteRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.RouteSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.RouteSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.RouteStatusUpdateRequest;

public interface RouteManagementDao {

	boolean validateRouteRegistrationRequest(RouteRegistrationRequest request);

	RouteSearchResponse getRouteSearchList(RouteSearchRequest request);

	RouteSearchResponse getRouteViewList(RouteSearchRequest request);

	RouteSearchResponse getViewStagesOnRole(RouteSearchRequest request);

	boolean isUpdateRouteProfile(RouteRegistrationRequest request);

	RouteMaster isUpdateRouteStatus(RouteStatusUpdateRequest request);

	boolean deleteStage(RouteRegistrationRequest request);
	
	List<RouteMaster> getAllRoutes(Long ptoId);
	
	RouteSearchResponse getRouteSearchListForPtoResult(RouteSearchRequest request);
	
	RouteMaster validateRouteCode(BulkUploadRequest bulkUploadRequest);
	
	boolean validateRouteId(BulkUploadRequest bulkUploadRequest);
	
	

}
