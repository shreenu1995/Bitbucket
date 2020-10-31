package com.chatak.transit.afcs.server.dao;

import java.util.List;

import com.chatak.transit.afcs.server.dao.model.StopProfile;
import com.chatak.transit.afcs.server.pojo.web.StageSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.StopRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.StopSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.StopSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.StopUpdateRequest;

public interface StopManagementDao {
	boolean validateStopRegistrationRequest(StopRegistrationRequest stopRegistrationRequest);

	Long saveStop(StopProfile stopProfile);

	Long saveStopToStopProfile(StopProfile stopProfile);

	public StopSearchResponse searchStop(StopSearchRequest request);

	boolean updateStop(StopUpdateRequest request);

	List<StageSearchRequest> getStageNameList();
	
	StopProfile updateStopStatus(StopUpdateRequest request);
	
	List<StopProfile> getAllStopsByPtoIdFromTerminal(Long ptoId, Long routeId);

	List<StopProfile> getAllStopsByPtoId(Long ptoId);
	
	StopProfile validateStopCode(String stopCode);
	
	boolean validateStopId(String stopCode);

	List<StopSearchRequest> getStopNameList();
	
	StopSearchResponse getStageByRouteId(StopSearchRequest request);
	
}
