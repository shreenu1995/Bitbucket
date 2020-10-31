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
import com.chatak.transit.afcs.server.dao.RouteManagementDao;
import com.chatak.transit.afcs.server.dao.model.PtoMaster;
import com.chatak.transit.afcs.server.dao.model.RouteMaster;
import com.chatak.transit.afcs.server.dao.model.StageMaster;
import com.chatak.transit.afcs.server.dao.repository.PtoMasterRepository;
import com.chatak.transit.afcs.server.dao.repository.RouteManagementRepository;
import com.chatak.transit.afcs.server.enums.CustomErrorCodes;
import com.chatak.transit.afcs.server.enums.Status;
import com.chatak.transit.afcs.server.pojo.web.AddStages;
import com.chatak.transit.afcs.server.pojo.web.BulkUploadRequest;
import com.chatak.transit.afcs.server.pojo.web.RouteRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.RouteRegistrationResponse;
import com.chatak.transit.afcs.server.pojo.web.RouteSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.RouteSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.RouteStatusUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.service.RouteManagementService;
import com.chatak.transit.afcs.server.util.StringUtil;

@Service
public class RouteManagementServiceImpl implements RouteManagementService {
	
	private static final Logger logger = LoggerFactory.getLogger(RouteManagementServiceImpl.class);

	@Autowired
	RouteManagementDao routeMangementDao;

	@Autowired
	RouteManagementRepository routeManagementRepository;
	
	@Autowired
	CustomErrorResolution dataValidation;

	@Autowired
	OrganizationManagementDao organizationManagementDao;
	
	@Autowired
	PtoManagementDao ptoManagementDao;
	
	@Autowired
	PtoMasterRepository ptoMasterRepository;
	
	@Override
	public List<RouteMaster> getAllRoutes(Long ptoId) {
		return routeMangementDao.getAllRoutes(ptoId);
	}
	
	@Override
	public RouteRegistrationResponse routeRegistartion(RouteRegistrationRequest request,
			RouteRegistrationResponse response, HttpServletResponse httpResponse) throws IOException {
		RouteMaster routeMaster = new RouteMaster();
		if (routeMangementDao.validateRouteRegistrationRequest(request)) {

			routeMaster.setRouteName(request.getRouteName());
			routeMaster.setOrganizationId(request.getOrganizationId());
			routeMaster.setPtoId(request.getPtoId());
			routeMaster.setFromRoute(request.getFromRoute());
			routeMaster.setToRoute(request.getToRoute());
			routeMaster.setStatus(Status.ACTIVE.getValue());
			routeMaster.setRouteCode(request.getRouteCode());
			if (!StringUtil.isNull(routeMaster.getPtoId())) {
				Long ptoId = routeMaster.getPtoId();
				try {
					PtoMaster ptoMaster = ptoMasterRepository.findByPtoMasterId(ptoId);
					response.setPtoName(ptoMaster.getPtoName());
				} catch (NullPointerException e) {
					logger.error("ERROR :: ProductDaoImpl class :: searchProduct method :: exception", e);
				}
			}

		} else {
			validateRouteRegistrationErrors(request, response);
			response.setReservedResponse("");
		}

		if (StringUtil.isListNotNullNEmpty(request.getDataFieldList())) {
			List<StageMaster> stageMap = new ArrayList<>();
			for (AddStages addStages : request.getDataFieldList()) {
				StageMaster map = new StageMaster();
				map.setOrganizationId(request.getOrganizationId());
				map.setStageSequenceNumber(addStages.getStageSequenceNumber());
				map.setStageName(addStages.getStageName());
				map.setDistance(addStages.getDistance());
				map.setStatus(Status.ACTIVE.getValue());
				map.setPtoId(request.getPtoId());
				map.setRouteId(request.getRouteId());
				if (!StringUtil.isNull(request.getRouteId())) {
					map.setRouteId(request.getRouteId());
				}
				stageMap.add(map);
			}
			routeMaster.setStageMap(stageMap);
			RouteMaster routeMaster1 = routeManagementRepository.save(routeMaster);
			response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
			response.setRouteId(routeMaster1.getRouteId());
		}
		return response;
	}

	@Override
	public void validateRouteRegistrationErrors(RouteRegistrationRequest request, WebResponse response) {
		if (!dataValidation.isValidUser(request.getUserId())) {
			response.setStatusCode(CustomErrorCodes.USER_NOT_EXIST.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.USER_NOT_EXIST.getCustomErrorMsg());
			response.setStatusMessage(CustomErrorCodes.USER_NOT_EXIST.getCustomErrorMsg());
		}
	}

	@Override
	public RouteSearchResponse searchRoute(RouteSearchRequest request) {
		RouteSearchResponse response = null;
		response = routeMangementDao.getRouteSearchList(request);
		if (response != null) {
			response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
			return response;
		}
		validateSearchRouteRequest(request, response);
		return response;
	}

	private void validateSearchRouteRequest(RouteSearchRequest request, RouteSearchResponse response) {
		if (dataValidation.routeNameValidation(request.getRouteName())) {
			response.setStatusCode(CustomErrorCodes.ROUTE_NAME_ALREADY_EXIST.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.ROUTE_NAME_ALREADY_EXIST.getCustomErrorMsg());
		}
	}

	@Override
	public RouteSearchResponse viewRoute(RouteSearchRequest request) {
		RouteSearchResponse response = null;
		response = routeMangementDao.getRouteViewList(request);
		if (response != null) {
			response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
			return response;
		}
		validateSearchRouteRequest(request, response);
		return response;
	}

	@Override
	public RouteSearchResponse getViewStagesOnRole(RouteSearchRequest request) {
		RouteSearchResponse response = routeMangementDao.getViewStagesOnRole(request);
		if (response != null) {
			response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
			return response;
		}
		return response;
	}

	@Override
	public WebResponse updateRouteProfile(RouteRegistrationRequest request, WebResponse response,
			HttpServletResponse httpServletResponse) {

		if (routeMangementDao.isUpdateRouteProfile(request)) {
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
	public WebResponse deleteStage(RouteRegistrationRequest request, WebResponse response) {

		if (routeMangementDao.deleteStage(request)) {
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
	public RouteSearchResponse updateRouteStatus(RouteStatusUpdateRequest request, WebResponse response) {
		RouteSearchResponse routeSearchResponse = new RouteSearchResponse();
		
		if(!StringUtil.isNull(request)) {
			RouteMaster routeMaster = routeMangementDao.isUpdateRouteStatus(request);
			routeSearchResponse.setRouteName(routeMaster.getRouteName());
			routeSearchResponse.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			routeSearchResponse.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
			return routeSearchResponse;
		} else {
			routeSearchResponse.setStatusCode(CustomErrorCodes.SERVER_ERROR.getCustomErrorCode());
			routeSearchResponse.setStatusMessage(CustomErrorCodes.SERVER_ERROR.getCustomErrorMsg());
			return routeSearchResponse;
		}
		
	}
	
	@Override
	public RouteSearchResponse validateRouteCode(BulkUploadRequest bulkUploadRequest) {
		RouteSearchResponse response = new RouteSearchResponse();
		if (routeMangementDao.validateRouteId(bulkUploadRequest)) {
			RouteMaster routeMaster = routeMangementDao.validateRouteCode(bulkUploadRequest);
			response.setPtoId(routeMaster.getPtoId());
			response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
			return response;
		} else {
			response.setStatusCode(CustomErrorCodes.ROUTE_CODE_NOT_EXIST.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.ROUTE_CODE_NOT_EXIST.getCustomErrorMsg());
			return response;
		}
	}
}
