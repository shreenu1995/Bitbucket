package com.chatak.transit.afcs.server.service.impl;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.chatak.transit.afcs.server.constants.ServerConstants;
import com.chatak.transit.afcs.server.dao.CustomErrorResolution;
import com.chatak.transit.afcs.server.dao.OrganizationManagementDao;
import com.chatak.transit.afcs.server.dao.model.OrganizationMaster;
import com.chatak.transit.afcs.server.enums.CustomErrorCodes;
import com.chatak.transit.afcs.server.enums.HttpErrorCodes;
import com.chatak.transit.afcs.server.enums.RoleLevel;
import com.chatak.transit.afcs.server.enums.Status;
import com.chatak.transit.afcs.server.pojo.web.OrganizationRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.OrganizationRegistrationResponse;
import com.chatak.transit.afcs.server.pojo.web.OrganizationSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.OrganizationSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.OrganizationStatusUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.OrganizationUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.service.OrganizationManagementService;
import com.chatak.transit.afcs.server.util.StringUtil;

@Service
public class OrganizationManagementServiceImpl implements OrganizationManagementService {

	@Autowired
	CustomErrorResolution dataValidation;

	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	OrganizationManagementDao organizationManagementDao;

	@Override
	public OrganizationRegistrationResponse createOrganization(OrganizationRegistrationRequest request,
			HttpServletResponse httpResponse, OrganizationRegistrationResponse response) throws IOException {
		if (organizationManagementDao.validateOrganizationRegistration(request)) {
			OrganizationMaster organizationMaster = new OrganizationMaster();
			organizationMaster.setOrganizationName(request.getOrganizationName());
			organizationMaster.setStatus(Status.ACTIVE.getValue());
			organizationMaster.setCreatedBy(request.getUserId());
			organizationMaster.setUpdatedBy(request.getUserId());
			organizationMaster.setCreatedDateTime(Timestamp.from(Instant.now()));
			organizationMaster.setUpdatedDateTime(Timestamp.from(Instant.now()));
			organizationMaster.setState(request.getState());
			organizationMaster.setCity(request.getCity());
			organizationMaster.setUpdatedDateTime(Timestamp.from(Instant.now()));
			organizationMaster.setSiteUrl(request.getSiteUrl());
			organizationMaster.setOrganizationMobileNumber(request.getOrganizationMobile());
			organizationMaster.setContactPerson(request.getContactPerson());
			organizationMaster.setOrganizationEmail(request.getOrganizationEmail());

			OrganizationMaster orgMaster = organizationManagementDao.saveOrganization(organizationMaster);
			if (!StringUtil.isNull(orgMaster)) {
				response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
				response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
				httpResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
				httpResponse.setCharacterEncoding(ServerConstants.CHAR_ENCODING_CONS);
				return response;
			} else {
				response.setStatusCode(CustomErrorCodes.SERVER_ERROR.getCustomErrorCode());
				response.setStatusMessage(CustomErrorCodes.SERVER_ERROR.getCustomErrorMsg());
				response.setOrganizationId("");
				httpResponse.sendError(HttpErrorCodes.INTERNAL_SERVER_ERROR.getHttpErrorCode(), response.toString());
				return response;
			}
		}
		validateOrganizationRegistrationErrors(request, response);
		response.setOrganizationId("");
		return response;
	}

	public void validateOrganizationRegistrationErrors(OrganizationRegistrationRequest request,
			OrganizationRegistrationResponse response) {
		if (!dataValidation.isValidUser(request.getUserId())) {
			response.setStatusCode(CustomErrorCodes.USER_NOT_EXIST.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.USER_NOT_EXIST.getCustomErrorMsg());
		} else if (dataValidation.validateOrganizationName(request.getOrganizationName())) {
			response.setStatusCode(CustomErrorCodes.INVALID_ORGANIZATION_NAME.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.INVALID_ORGANIZATION_NAME.getCustomErrorMsg());
		}
	}

	@Override
	public WebResponse updateOrganization(OrganizationUpdateRequest request, WebResponse response,
			HttpServletResponse httpResponse) throws IOException {

		
		if (organizationManagementDao.validateOrganizationMasterUpdate(request)) {
			if (organizationManagementDao.updateOrganization(request)) {
				response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
				response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
				return response;
			} else {
				response.setStatusCode(CustomErrorCodes.SERVER_ERROR.getCustomErrorCode());
				response.setStatusMessage(CustomErrorCodes.SERVER_ERROR.getCustomErrorMsg());
				return response;
			}
		} else {
			validateOrganizationUpdate(request, response);
			return response;
		}
	}

	private void validateOrganizationUpdate(OrganizationUpdateRequest request, WebResponse response) {
		if (!dataValidation.isValidUser(request.getUserId())) {
			response.setStatusCode(CustomErrorCodes.USER_NOT_EXIST.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.USER_NOT_EXIST.getCustomErrorMsg());
		} else if (!dataValidation.validateOrganizationId(request.getOrgId())) {
			response.setStatusCode(CustomErrorCodes.ORGANIZATION_NOT_EXIST.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.ORGANIZATION_NOT_EXIST.getCustomErrorMsg());
		}
	}

	@Override
	public OrganizationSearchResponse searchOrganization(OrganizationSearchRequest request) {
		OrganizationSearchResponse response = organizationManagementDao.searchOrganizationList(request);
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
	public OrganizationMaster getOrganizationIdByOrganizationName(OrganizationSearchRequest request) {
		return organizationManagementDao.getOrganizationIdByOrganizationName(request);

	}

	@Override
	public OrganizationSearchResponse getOrganizationList(OrganizationSearchRequest request) {
		List<OrganizationMaster> organizationList = null;

		if (request.getUserType().equals(RoleLevel.SUPER_ADMIN.name())) {
			organizationList = organizationManagementDao.getOrganizationList(request);
		} else if (request.getUserType().equals(RoleLevel.ORG_ADMIN.name())) {
			organizationList = organizationManagementDao.getOrganizationByOrgId(request);
		} else {
			organizationList = organizationManagementDao.getOrganizationByOrgId(request);
		}

		List<OrganizationSearchRequest> organizationsearchList = new ArrayList<>();
		OrganizationSearchResponse organizationSearchResponse = new OrganizationSearchResponse();
		for (OrganizationMaster organizationMaster : organizationList) {
			request = new OrganizationSearchRequest();
			request.setOrgId(organizationMaster.getOrgId());
			request.setOrganizationName(organizationMaster.getOrganizationName());
			organizationsearchList.add(request);
		}
		organizationSearchResponse.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
		organizationSearchResponse.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		organizationSearchResponse.setOrganizationList(organizationsearchList);
		return organizationSearchResponse;
	}

	@Override
	public OrganizationSearchResponse getOrganizationListWithStatusNotTerminated(OrganizationSearchRequest request) {
		List<OrganizationMaster> orgList = null;
		orgList = organizationManagementDao.getOrganizationListWithStatusNotTerminated(request);
		List<OrganizationSearchRequest> organizationsearchList = new ArrayList<>();
		OrganizationSearchResponse ptoSearchResponse = new OrganizationSearchResponse();
		for (OrganizationMaster ptoMaster : orgList) {
			request = new OrganizationSearchRequest();
			request.setOrgId(ptoMaster.getOrgId());
			request.setOrganizationName(ptoMaster.getOrganizationName());
			request.setOrgId(ptoMaster.getOrgId());
			organizationsearchList.add(request);
		}
		ptoSearchResponse.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
		ptoSearchResponse.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		ptoSearchResponse.setOrganizationList(organizationsearchList);
		return ptoSearchResponse;
	}

	@Override
	public OrganizationSearchResponse updateOrganizationStatus(OrganizationStatusUpdateRequest request,
			WebResponse response) {
		OrganizationSearchResponse organizationSearchResponse = new OrganizationSearchResponse();
		if (!StringUtil.isNull(request)) {
			OrganizationMaster orgMaster = organizationManagementDao.updateOrganizationStatus(request);
			organizationSearchResponse.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			organizationSearchResponse.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
			organizationSearchResponse.setOrganizationName(orgMaster.getOrganizationName());
			return organizationSearchResponse;
		} else {
			organizationSearchResponse.setStatusCode(CustomErrorCodes.SERVER_ERROR.getCustomErrorCode());
			organizationSearchResponse.setStatusMessage(CustomErrorCodes.SERVER_ERROR.getCustomErrorMsg());
			return organizationSearchResponse;
		}

	}

	@Override
	public OrganizationSearchResponse getOrganizationListByOrgId(OrganizationSearchRequest request) {
		List<OrganizationMaster> organizationList = null;
		organizationList = organizationManagementDao.getOrganizationListByOrgId(request);
		List<OrganizationSearchRequest> organizationsearchList = new ArrayList<>();
		OrganizationSearchResponse organizationSearchResponse = new OrganizationSearchResponse();
		for (OrganizationMaster organizationMaster : organizationList) {
			request = new OrganizationSearchRequest();
			request.setOrgId(organizationMaster.getOrgId());
			request.setOrganizationName(organizationMaster.getOrganizationName());
			organizationsearchList.add(request);
		}
		organizationSearchResponse.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
		organizationSearchResponse.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		organizationSearchResponse.setOrganizationList(organizationsearchList);
		return organizationSearchResponse;
	}

}
