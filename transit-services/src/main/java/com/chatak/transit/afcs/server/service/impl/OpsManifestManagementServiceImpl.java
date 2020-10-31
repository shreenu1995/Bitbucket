package com.chatak.transit.afcs.server.service.impl;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatak.transit.afcs.server.dao.CustomErrorResolution;
import com.chatak.transit.afcs.server.dao.OpsManifestManagementDao;
import com.chatak.transit.afcs.server.dao.model.OpsManifest;
import com.chatak.transit.afcs.server.enums.CustomErrorCodes;
import com.chatak.transit.afcs.server.enums.HttpErrorCodes;
import com.chatak.transit.afcs.server.enums.Status;
import com.chatak.transit.afcs.server.pojo.web.OpsManifestRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.OpsManifestRegistrationResponse;
import com.chatak.transit.afcs.server.pojo.web.OpsManifestSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.OpsManifestSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.OpsManifestStatusChangeRequest;
import com.chatak.transit.afcs.server.pojo.web.OpsManifestUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.service.OpsManifestManagementService;
import com.chatak.transit.afcs.server.util.DateUtil;
import com.chatak.transit.afcs.server.util.StringUtil;

@Service
public class OpsManifestManagementServiceImpl implements OpsManifestManagementService{
	
	@Autowired
	OpsManifestManagementDao opsManifestManagementDao;
	
	@Autowired
	CustomErrorResolution dataValidation;

	@PersistenceContext
	EntityManager entityManager;
	
	@Override
	public OpsManifestRegistrationResponse opsManifestRegistration(
			@Valid OpsManifestRegistrationRequest opsManifestRegistrationRequest, HttpServletResponse httpResponse,
			OpsManifestRegistrationResponse opsManifestRegistrationResponse) {
		OpsManifest opsManifest = new OpsManifest();
		opsManifest.setOrganizationId(opsManifestRegistrationRequest.getOrganizationId());
		opsManifest.setDepotId(opsManifestRegistrationRequest.getDepotId());
		opsManifest.setOperatorId(opsManifestRegistrationRequest.getOperatorId());
		opsManifest.setDeviceNo(opsManifestRegistrationRequest.getDeviceNo());
		opsManifest.setPtoId(opsManifestRegistrationRequest.getPtoId());
		opsManifest.setDate(DateUtil.convertStringToTimestamp(opsManifestRegistrationRequest.getDate()));
		opsManifest.setStatus(Status.ACTIVE.getValue());
		opsManifestManagementDao.saveOpsManifest(opsManifest);
		opsManifestRegistrationResponse.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
		opsManifestRegistrationResponse.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		return opsManifestRegistrationResponse;
	}

	@Override
	public OpsManifestSearchResponse searchOpsManifest(OpsManifestSearchRequest request) {
		OpsManifestSearchResponse response =  opsManifestManagementDao.getOpsManifestList(request);
		if(!StringUtil.isNull(response)) {
			response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		} else {
			response.setStatusCode(CustomErrorCodes.FAILURE.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.FAILURE.getCustomErrorMsg());
		}
		return response;
	
	}

	@Override
	public WebResponse updateOpsManifestStatus(@Valid OpsManifestStatusChangeRequest request, WebResponse response,
			HttpServletResponse httpResponse) throws IOException {
		if (opsManifestManagementDao.validateOpsManifestStatusUpdate(request)) {
			if (opsManifestManagementDao.updateOpsManifestStatus(request)) {
				response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
				response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
				return response;
			} else {
				response.setStatusCode(CustomErrorCodes.SERVER_ERROR.getCustomErrorCode());
				response.setStatusMessage(CustomErrorCodes.SERVER_ERROR.getCustomErrorMsg());
				httpResponse.sendError(HttpErrorCodes.INTERNAL_SERVER_ERROR.getHttpErrorCode(), response.toString());
				return response;
			}
		} else {
			return response;
		}
	}
	
	@Override
	public WebResponse updateOpsManifestProfile(OpsManifestUpdateRequest request, WebResponse response,
			HttpServletResponse httpResponse) {
		if (opsManifestManagementDao.updateOpsManifestProfile(request)) {
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
	public boolean deviceOnboardIdExistCheck(OpsManifestRegistrationRequest opsManifestRegistrationRequest) {
		return opsManifestManagementDao.validateExistingDeviceNumber(opsManifestRegistrationRequest);

	}
}
