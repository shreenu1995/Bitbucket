package com.chatak.transit.afcs.server.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatak.transit.afcs.server.dao.CustomErrorResolution;
import com.chatak.transit.afcs.server.dao.OrganizationManagementDao;
import com.chatak.transit.afcs.server.dao.PtoManagementDao;
import com.chatak.transit.afcs.server.dao.StageManagementDao;
import com.chatak.transit.afcs.server.dao.model.PtoMaster;
import com.chatak.transit.afcs.server.dao.model.StageMaster;
import com.chatak.transit.afcs.server.dao.model.StopProfile;
import com.chatak.transit.afcs.server.dao.repository.PtoMasterRepository;
import com.chatak.transit.afcs.server.dao.repository.StageManagementRepository;
import com.chatak.transit.afcs.server.dao.repository.StopMappingRepository;
import com.chatak.transit.afcs.server.enums.CustomErrorCodes;
import com.chatak.transit.afcs.server.enums.Status;
import com.chatak.transit.afcs.server.pojo.web.RouteSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.RouteSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.StageRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.StageRegistrationResponse;
import com.chatak.transit.afcs.server.pojo.web.StageResponse;
import com.chatak.transit.afcs.server.pojo.web.StageSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.StageStatusUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.StopRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.service.StageManagementService;
import com.chatak.transit.afcs.server.util.StringUtil;

@Service
public class StageManagementServiceImpl implements StageManagementService {

	Logger logger = LoggerFactory.getLogger(StageManagementServiceImpl.class);

	@Autowired
	StageManagementDao stageManagementDao;

	@Autowired
	CustomErrorResolution dataValidation;

	@Autowired
	OrganizationManagementDao organizationManagementDao;

	@Autowired
	StageManagementRepository stageManagementRepository;

	@Autowired
	StopMappingRepository stopMappingRepository;
	
	@Autowired
	PtoManagementDao ptoManagementDao;

	@Autowired
	PtoMasterRepository ptoMasterRepository;
	
	@Override
	public StageRegistrationResponse stageRegistration(StageRegistrationRequest request,
			HttpServletResponse httpResponse, StageRegistrationResponse response) throws IOException {
		StageMaster stageMaster = new StageMaster();
		if (stageManagementDao.validateStageRegistrationRequest(request)) {
			stageMaster.setOrganizationId(request.getOrganizationId());
			stageMaster.setPtoId(request.getPtoId());
			stageMaster.setStageName(request.getStageName());
			stageMaster.setRouteId(request.getRouteId());
			stageMaster.setStatus(Status.ACTIVE.getValue());
			if (!StringUtil.isNull(stageMaster.getPtoId())) {
				Long ptoId = stageMaster.getPtoId();
				try {
					PtoMaster ptoMaster = ptoMasterRepository.findByPtoMasterId(ptoId);
					response.setPtoName(ptoMaster.getPtoName());
				} catch (NullPointerException e) {
					logger.error("ERROR :: ProductDaoImpl class :: searchProduct method :: exception", e);
				}
			}

		} else {
			validateStageRegistrationErrors(request, response);
			response.setReservedResponse("");
		}

		if (StringUtil.isListNotNullNEmpty(request.getDataFieldList())) {
			List<StopProfile> stopMap = new ArrayList<>();
			for (StopRegistrationRequest addStops : request.getDataFieldList()) {
				StopProfile map = new StopProfile();
				map.setOrganizationId(request.getOrganizationId());
				map.setStopSequenceNumber(addStops.getStopSequenceNumber());
				map.setStopName(addStops.getStopName());
				map.setDistance(addStops.getDistance());
				map.setStageId(request.getStageId());
				map.setStatus(Status.ACTIVE.getValue());
				map.setPtoId(request.getPtoId());
				map.setRouteId(request.getRouteId());
				if (!StringUtil.isNull(request.getStageId())) {
					map.setStageId(request.getStageId());
				}
				stopMap.add(map);
			}
			stageMaster.setStopMap(stopMap);
			    StageMaster stageMaster1 = stageManagementDao.saveStage(stageMaster);
				response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
				response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
				response.setStageId(stageMaster1.getStageId());
			}
		return response;
	}

	@Override
	public StageResponse searchStage(StageSearchRequest request) {
		StageResponse response = stageManagementDao.searchStage(request);

		if (!StringUtil.isNull(response)) {
			response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		} else {
			response.setStatusCode(CustomErrorCodes.FAILURE.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.FAILURE.getCustomErrorMsg());
		}

		return response;
	}

	@Override
	public void validateStageRegistrationErrors(StageRegistrationRequest request, WebResponse response) {
		if (!dataValidation.isValidUser(request.getUserId())) {
			response.setStatusCode(CustomErrorCodes.USER_NOT_EXIST.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.USER_NOT_EXIST.getCustomErrorMsg());
			response.setStatusMessage(CustomErrorCodes.USER_NOT_EXIST.getCustomErrorMsg());
		}
	}

	@Override
	public List<RouteSearchRequest> getRouteName() {
		return stageManagementDao.getRouteNameList();
	}

	@Override
	public WebResponse stageProfileUpdate(StageRegistrationRequest request, WebResponse response,
			HttpServletResponse httpServletResponse) {

		if (stageManagementDao.stageProfileUpdate(request)) {
			response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
			response.setReservedResponse("");
			return response;
		} else {
			response.setStatusCode(CustomErrorCodes.SERVER_ERROR.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.SERVER_ERROR.getCustomErrorMsg());
			response.setReservedResponse("");
			return response;

		}
	}

	@Override
	public StageResponse viewStage(StageSearchRequest request) {
		StageResponse response = null;
		response = stageManagementDao.getStageViewList(request);
		if (response != null) {
			response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
			return response;
		}
		return response;
	}

	@Override
	public StageResponse getViewStop(StageSearchRequest request) {
		StageResponse response = stageManagementDao.getViewStop(request);
		if (response != null) {
			response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
			return response;
		}
		return response;
	}

	@Override
	public WebResponse deleteStop(StageRegistrationRequest request, WebResponse response) {
		if (stageManagementDao.deleteStop(request)) {
			response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
			return response;
		} else {
			response.setStatusCode(CustomErrorCodes.SERVER_ERROR.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.SERVER_ERROR.getCustomErrorMsg());
			return response;
		}
	}

	@Override
	public StageResponse stageStatusUpdate(StageStatusUpdateRequest request, WebResponse response) {
		StageResponse stageResponse = new StageResponse();

		if (!StringUtil.isNull(request)) {
			StageMaster stageMaster = stageManagementDao.updateStageStatus(request);
			stageResponse.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			stageResponse.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
			stageResponse.setStageName(stageMaster.getStageName());
			return stageResponse;
		} else {
			stageResponse.setStatusCode(CustomErrorCodes.FAILURE.getCustomErrorCode());
			stageResponse.setStatusMessage(CustomErrorCodes.FAILURE.getCustomErrorMsg());
			return stageResponse;

		}
	}
	
	@Override
	public RouteSearchResponse getRouteByPto(RouteSearchRequest request) {
		RouteSearchResponse routeSearchResponse = stageManagementDao.getRouteByPto(request);
		if (!StringUtil.isNull(routeSearchResponse)) {
			routeSearchResponse.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			routeSearchResponse.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		} else {
			routeSearchResponse.setStatusCode(CustomErrorCodes.FAILURE.getCustomErrorCode());
			routeSearchResponse.setStatusMessage(CustomErrorCodes.FAILURE.getCustomErrorMsg());
		}
		return routeSearchResponse;
	}

}