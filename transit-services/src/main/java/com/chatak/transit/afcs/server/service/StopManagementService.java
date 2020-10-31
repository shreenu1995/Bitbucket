package com.chatak.transit.afcs.server.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.chatak.transit.afcs.server.dao.model.StopProfile;
import com.chatak.transit.afcs.server.pojo.web.StageSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.StopRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.StopRegistrationResponse;
import com.chatak.transit.afcs.server.pojo.web.StopSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.StopSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.StopUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;

public interface StopManagementService {

	public StopRegistrationResponse stopRegistration(StopRegistrationRequest stopRegistrationRequest,
			HttpServletResponse httpResponse, StopRegistrationResponse stopRegistrationResponse) throws IOException;

	void validateStopRegistrationErrors(StopRegistrationRequest stopRegistrationRequest, WebResponse response);
	
	StopSearchResponse searchStop(StopSearchRequest request);

	WebResponse updateStop(StopUpdateRequest request, WebResponse response);

	StopSearchResponse updateStopStatus(StopUpdateRequest request, WebResponse response);
	
	List<StageSearchRequest> getStageNameList() throws InstantiationException, IllegalAccessException;
    
	List<StopProfile> getAllStops(Long ptoId, Long routeId);
	
	List<StopProfile> getAllStopsByPtoId(Long ptoId);
	
	StopSearchResponse validateStopCode(String routeCode);

	public List<StopSearchRequest> getStopNameList();
	
	StopSearchResponse getStageByRouteId(StopSearchRequest stopSearchRequest);

}
