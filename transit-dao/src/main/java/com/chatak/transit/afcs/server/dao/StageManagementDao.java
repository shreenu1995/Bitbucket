package com.chatak.transit.afcs.server.dao;

import java.util.List;

import com.chatak.transit.afcs.server.dao.model.StageMaster;
import com.chatak.transit.afcs.server.dao.model.StopProfile;
import com.chatak.transit.afcs.server.pojo.web.RouteSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.RouteSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.StageRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.StageResponse;
import com.chatak.transit.afcs.server.pojo.web.StageSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.StageStatusUpdateRequest;

public interface StageManagementDao {

	boolean validateStageRegistrationRequest(StageRegistrationRequest request);

	StageResponse searchStage(StageSearchRequest request);

	List<RouteSearchRequest> getRouteNameList();

	boolean stageProfileUpdate(StageRegistrationRequest request);

	boolean validateStageStatusUpdate(StageStatusUpdateRequest request);

	StageMaster updateStageStatus(StageStatusUpdateRequest request);

	StageMaster saveStage(StageMaster stageMaster);

	StageResponse getStageViewList(StageSearchRequest request);

	StageResponse getViewStop(StageSearchRequest request);

	boolean deleteStop(StageRegistrationRequest request);
	
	void saveStopByStage(StopProfile map);
	
	RouteSearchResponse getRouteByPto(RouteSearchRequest request);

}
