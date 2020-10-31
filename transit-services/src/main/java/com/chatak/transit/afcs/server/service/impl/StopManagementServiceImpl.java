package com.chatak.transit.afcs.server.service.impl;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatak.transit.afcs.server.dao.CustomErrorResolution;
import com.chatak.transit.afcs.server.dao.OrganizationManagementDao;
import com.chatak.transit.afcs.server.dao.PtoManagementDao;
import com.chatak.transit.afcs.server.dao.StopManagementDao;
import com.chatak.transit.afcs.server.dao.model.StopProfile;
import com.chatak.transit.afcs.server.enums.CustomErrorCodes;
import com.chatak.transit.afcs.server.enums.Status;
import com.chatak.transit.afcs.server.pojo.web.StageProfileData;
import com.chatak.transit.afcs.server.pojo.web.StageSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.StopRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.StopRegistrationResponse;
import com.chatak.transit.afcs.server.pojo.web.StopSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.StopSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.StopUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.service.StopManagementService;
import com.chatak.transit.afcs.server.util.StringUtil;
import com.chatak.transit.afcs.server.util.Utility;

@Service
public class StopManagementServiceImpl implements StopManagementService {
	
	@Autowired
	StopManagementDao stopManagementDao;

	@Autowired
	CustomErrorResolution dataValidation;

	@Autowired
	OrganizationManagementDao organizationManagementDao;

	@Autowired
	PtoManagementDao ptoManagementDao;

	@Override
	public StopRegistrationResponse stopRegistration(StopRegistrationRequest stopRegistrationRequest,
			HttpServletResponse httpResponse, StopRegistrationResponse stopRegistrationResponse) throws IOException {
		if (stopManagementDao.validateStopRegistrationRequest(stopRegistrationRequest)) {
			StopProfile stopProfile = new StopProfile();
			stopProfile.setRouteId(stopRegistrationRequest.getRouteId());
			stopProfile.setStageId(stopRegistrationRequest.getStageId());
			stopProfile.setStopName(stopRegistrationRequest.getStopName());
			stopProfile.setPtoId(stopRegistrationRequest.getPtoId());
			stopProfile.setStatus(Status.ACTIVE.getValue());
			stopProfile.setOrganizationId(stopRegistrationRequest.getOrganizationId());
			Long stopId = stopManagementDao.saveStopToStopProfile(stopProfile);
			if (stopId > 0) {
				stopRegistrationResponse.setStopId(stopId);
				stopRegistrationResponse.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
				stopRegistrationResponse.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
				stopRegistrationResponse.setReservedResponse("");
				return stopRegistrationResponse;
			} else {
				stopRegistrationResponse.setStatusCode(CustomErrorCodes.SERVER_ERROR.getCustomErrorCode());
				stopRegistrationResponse.setStatusMessage(CustomErrorCodes.SERVER_ERROR.getCustomErrorMsg());
				stopRegistrationResponse.setReservedResponse("");
				return stopRegistrationResponse;
			}
		} else {
			validateStopRegistrationErrors(stopRegistrationRequest, stopRegistrationResponse);
			stopRegistrationResponse.setReservedResponse("");
		}
		return stopRegistrationResponse;
	}

	@Override
	public void validateStopRegistrationErrors(StopRegistrationRequest stopRegistrationRequest, WebResponse response) {
		if (!dataValidation.isValidUser(stopRegistrationRequest.getUserId())) {
			response.setStatusCode(CustomErrorCodes.USER_NOT_EXIST.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.USER_NOT_EXIST.getCustomErrorMsg());
			response.setStatusMessage(CustomErrorCodes.USER_NOT_EXIST.getCustomErrorMsg());
		} else if (!dataValidation.validateStopName(stopRegistrationRequest.getStopName())) {
			response.setStatusCode(CustomErrorCodes.INVALID_STOP_NAME.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.INVALID_STOP_NAME.getCustomErrorMsg());
		}
	}

	@Override
	public StopSearchResponse searchStop(StopSearchRequest request) {

		StopSearchResponse response = null;
		response = stopManagementDao.searchStop(request);

		if (response != null) {
			response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
			return response;
		}

		return response;
	}

	@Override
	public WebResponse updateStop(StopUpdateRequest request, WebResponse response) {

		if (stopManagementDao.updateStop(request)) {
			response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
			response.setReservedResponse("");
			return response;
		}
		response.setStatusCode(CustomErrorCodes.SERVER_ERROR.getCustomErrorCode());
		response.setStatusMessage(CustomErrorCodes.SERVER_ERROR.getCustomErrorMsg());
		response.setReservedResponse("");
		return response;
	}

	@Override
	public List<StageSearchRequest> getStageNameList() throws InstantiationException, IllegalAccessException {
		return Utility.copyListBeanProperty(stopManagementDao.getStageNameList(), StageProfileData.class);
	}
	
	@Override
	public List<StopSearchRequest> getStopNameList() {
		return stopManagementDao.getStopNameList();
	}

	@Override
	public StopSearchResponse updateStopStatus(StopUpdateRequest request, WebResponse response) {
		StopSearchResponse searchResponse = new StopSearchResponse();

		if (!StringUtil.isNull(request)) {
			StopProfile stopProfile = stopManagementDao.updateStopStatus(request);
			searchResponse.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			searchResponse.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
			searchResponse.setStopName(stopProfile.getStopName());
			return searchResponse;
		} else {
			searchResponse.setStatusCode(CustomErrorCodes.SERVER_ERROR.getCustomErrorCode());
			searchResponse.setStatusMessage(CustomErrorCodes.SERVER_ERROR.getCustomErrorMsg());
			return searchResponse;
		}
	}

	@Override
	public List<StopProfile> getAllStops(Long ptoId, Long routeId) {
		return stopManagementDao.getAllStopsByPtoIdFromTerminal(ptoId, routeId);
	}

	@Override
	public List<StopProfile> getAllStopsByPtoId(Long ptoId) {
		return stopManagementDao.getAllStopsByPtoId(ptoId);
	}

	@Override
	public StopSearchResponse validateStopCode(String code) {
		StopSearchResponse stopSearchResponse = new StopSearchResponse();
		String stopCode =  code.replace("\"", "");
		
		if(stopManagementDao.validateStopId(stopCode)) {
			StopProfile stopProfile = stopManagementDao.validateStopCode(stopCode);
			stopSearchResponse.setPtoId(String.valueOf(stopProfile.getPtoId()));
			stopSearchResponse.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			stopSearchResponse.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
			return stopSearchResponse;
		} else {
			stopSearchResponse.setStatusCode(CustomErrorCodes.STOP_CODE_NOT_EXIST.getCustomErrorCode());
			stopSearchResponse.setStatusMessage(CustomErrorCodes.STOP_CODE_NOT_EXIST.getCustomErrorMsg());
			return stopSearchResponse;
		}
	}

	@Override
	public StopSearchResponse getStageByRouteId(StopSearchRequest stopSearchRequest) {
		StopSearchResponse stopSearchResponse = stopManagementDao.getStageByRouteId(stopSearchRequest);
		if(!StringUtil.isNull(stopSearchRequest)) {
			stopSearchResponse.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			stopSearchResponse.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
			return stopSearchResponse;
		} else {
			stopSearchResponse.setStatusCode(CustomErrorCodes.FAILURE.getCustomErrorCode());
			stopSearchResponse.setStatusCode(CustomErrorCodes.FAILURE.getCustomErrorMsg());
			return stopSearchResponse;
		}
	}

}
