package com.afcs.web.service;

import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.RouteNameList;
import com.chatak.transit.afcs.server.pojo.web.RouteSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.RouteSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.StageRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.StageRegistrationResponse;
import com.chatak.transit.afcs.server.pojo.web.StageResponse;
import com.chatak.transit.afcs.server.pojo.web.StageSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.StageStatusUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;

public interface StageManagementService {
	
	StageRegistrationResponse stageRegister(StageRegistrationRequest request) throws AFCSException;
	
	StageResponse searchStage(StageSearchRequest request) throws AFCSException;
	
	RouteNameList getRouteName();
	
	WebResponse updateStage(StageRegistrationRequest request) throws AFCSException;
	
	StageResponse updateStageStatus(StageStatusUpdateRequest request) throws AFCSException;

	StageResponse viewStopOnStage(StageSearchRequest request) throws AFCSException;

	WebResponse deleteStopById(StageRegistrationRequest request) throws AFCSException;
	
	RouteSearchResponse getRouteAccordingOrgAndPto(RouteSearchRequest routeSearchRequest) throws AFCSException;
}
