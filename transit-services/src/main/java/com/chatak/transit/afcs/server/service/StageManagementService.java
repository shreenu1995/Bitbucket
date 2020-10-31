package com.chatak.transit.afcs.server.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.chatak.transit.afcs.server.pojo.web.RouteSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.RouteSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.StageRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.StageRegistrationResponse;
import com.chatak.transit.afcs.server.pojo.web.StageResponse;
import com.chatak.transit.afcs.server.pojo.web.StageSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.StageStatusUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;

public interface StageManagementService {
	
	StageRegistrationResponse stageRegistration(StageRegistrationRequest request, 
			HttpServletResponse httpResponse, StageRegistrationResponse response) throws IOException;
	
	StageResponse searchStage(StageSearchRequest request);
	
	void validateStageRegistrationErrors(StageRegistrationRequest request, WebResponse response);

	List<RouteSearchRequest> getRouteName();
	
	public WebResponse stageProfileUpdate(@Valid StageRegistrationRequest request, WebResponse response, HttpServletResponse httpServletResponse);

	public StageResponse stageStatusUpdate(StageStatusUpdateRequest request, WebResponse response);

	StageResponse viewStage(StageSearchRequest request);

	StageResponse getViewStop(StageSearchRequest request);

    WebResponse deleteStop(StageRegistrationRequest request, WebResponse response);
    
    public RouteSearchResponse getRouteByPto(RouteSearchRequest request);
		
}


