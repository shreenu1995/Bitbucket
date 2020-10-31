package com.afcs.web.service;

import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.RouteNameList;
import com.chatak.transit.afcs.server.pojo.web.StageNameList;
import com.chatak.transit.afcs.server.pojo.web.StopNameList;
import com.chatak.transit.afcs.server.pojo.web.StopRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.StopRegistrationResponse;
import com.chatak.transit.afcs.server.pojo.web.StopSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.StopSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.StopUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;

public interface StopManagementService {

	StopRegistrationResponse stopRegistration(StopRegistrationRequest request) throws AFCSException;
	
	StopSearchResponse stopSearch(StopSearchRequest request) throws AFCSException;

	WebResponse updateStopProfile(StopUpdateRequest request) throws AFCSException;

	StopSearchResponse updateStopStatus(StopSearchRequest request) throws AFCSException;
	
	RouteNameList getRouteName();
	
	StageNameList getStageNameList();
	
	StopSearchResponse validateStopCode(String stopCode) throws AFCSException;

	StopNameList getStopNameList();
	
	StopSearchResponse getStageByRoute(StopSearchRequest searchRequest) throws AFCSException;

}
