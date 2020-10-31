package com.chatak.transit.afcs.server.service.impl;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.chatak.transit.afcs.server.constants.ServerConstants;
import com.chatak.transit.afcs.server.dao.CustomErrorResolution;
import com.chatak.transit.afcs.server.dao.PtoManagementDao;
import com.chatak.transit.afcs.server.dao.RegionManagementServiceDao;
import com.chatak.transit.afcs.server.dao.model.PtoMaster;
import com.chatak.transit.afcs.server.dao.repository.OrganizationMasterRepository;
import com.chatak.transit.afcs.server.dao.repository.PtoMasterRepository;
import com.chatak.transit.afcs.server.enums.CustomErrorCodes;
import com.chatak.transit.afcs.server.enums.HttpErrorCodes;
import com.chatak.transit.afcs.server.enums.RoleLevel;
import com.chatak.transit.afcs.server.enums.Status;
import com.chatak.transit.afcs.server.pojo.web.PtoList;
import com.chatak.transit.afcs.server.pojo.web.PtoListRequest;
import com.chatak.transit.afcs.server.pojo.web.PtoListResponse;
import com.chatak.transit.afcs.server.pojo.web.PtoRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.PtoRegistrationResponse;
import com.chatak.transit.afcs.server.pojo.web.PtoSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.PtoSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.PtoStatusCheckRequest;
import com.chatak.transit.afcs.server.pojo.web.PtoStatusUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.PtoUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.service.PtoManagementService;
import com.chatak.transit.afcs.server.util.StringUtil;

@Service
public class PtoManagementServiceImpl extends ServerConstants implements PtoManagementService {

	@Autowired
	CustomErrorResolution dataValidation;

	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	PtoManagementDao ptoManagementDao;

	@Autowired
	RegionManagementServiceDao regionManagementDao;
	
	@Autowired
	OrganizationMasterRepository organizationMasterRepository;

	@Autowired
	PtoMasterRepository ptoMasterRepository;

	@Override
	public PtoRegistrationResponse savePtoRegistration(PtoRegistrationRequest ptoRegistrationRequest,
			HttpServletResponse httpResponse, HttpSession session, PtoRegistrationResponse response)
			throws IOException {

		if (ptoManagementDao.validatePtoRegistrationRequest(ptoRegistrationRequest)) {
			PtoMaster ptoMaster = new PtoMaster();
			ptoMaster.setOrgId(ptoRegistrationRequest.getOrgId());
			ptoMaster.setPtoName(ptoRegistrationRequest.getPtoName());
			ptoMaster.setUserId(ptoRegistrationRequest.getUserId());
			ptoMaster.setStatus(Status.ACTIVE.getValue());
			ptoMaster.setPtoName(ptoRegistrationRequest.getPtoName());
			ptoMaster.setCity(ptoRegistrationRequest.getCity());
			ptoMaster.setContactPerson(ptoRegistrationRequest.getContactPerson());
			ptoMaster.setPtoEmail(ptoRegistrationRequest.getPtoEmail());
			ptoMaster.setPtoMobile(ptoRegistrationRequest.getPtoMobile());
			ptoMaster.setPtoName(ptoRegistrationRequest.getPtoName());
			ptoMaster.setSiteUrl(ptoRegistrationRequest.getSiteUrl());
			ptoMaster.setCreatedBy(ptoRegistrationRequest.getUserId());
			ptoMaster.setUpdatedBy(ptoRegistrationRequest.getUserId());
			ptoMaster.setCreatedDateTime(Timestamp.from(Instant.now()));
			ptoMaster.setUpdatedDateTime(Timestamp.from(Instant.now()));
			ptoMaster.setState(ptoRegistrationRequest.getState());
			ptoManagementDao.savePtoRegistration(ptoMaster);
			response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
			return response;
		}
		validatePtoRegistrationErrors(ptoRegistrationRequest, response);
		return response;
	}

	@Override
	public void validatePtoRegistrationErrors(PtoRegistrationRequest request, PtoRegistrationResponse response) {
		if (!dataValidation.validateOrganizationId(request.getOrgId())) {
			response.setStatusCode(CustomErrorCodes.ORGANIZATION_NOT_EXIST.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.ORGANIZATION_NOT_EXIST.getCustomErrorMsg());
		} else 
		if (dataValidation.validatePtoName(request.getPtoName())) {
			response.setStatusCode(CustomErrorCodes.INVALID_PTO.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.INVALID_PTO.getCustomErrorMsg());
		}
	}

	@Override
	public PtoListResponse getPtoList(PtoListRequest ptoListRequest, HttpServletResponse httpResponse,
			PtoListResponse ptoListResponse) throws IOException {
		List<PtoMaster> ptoMasterList = null;
		if (ptoListRequest.getUserType().equals(RoleLevel.SUPER_ADMIN.name())) {
			ptoMasterList = ptoManagementDao.getPtoList(ptoListRequest);
		} else if (ptoListRequest.getUserType().equals(RoleLevel.ORG_ADMIN.name())) {
			ptoMasterList = ptoManagementDao.getPtoByOrganizationId(ptoListRequest);
		} else {
			ptoMasterList = ptoManagementDao.getPtoListByPtoMasterId(ptoListRequest);
		}

		List<PtoList> ptoOperationList = new ArrayList<>();
		PtoListResponse listResponse = new PtoListResponse();
		for (PtoMaster ptoOperationMaster : ptoMasterList) {
			PtoList ptoOperation = new PtoList();
			ptoOperation.setPtoName(ptoOperationMaster.getPtoName());
			ptoOperation.setPtoMasterId(ptoOperationMaster.getPtoMasterId());
			ptoOperation.setStatus(ptoOperationMaster.getStatus());
			ptoOperationList.add(ptoOperation);
		}
		listResponse.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
		listResponse.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		listResponse.setPtoList(ptoOperationList);
		httpResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
		httpResponse.setCharacterEncoding(CHAR_ENCODING_CONS);
		return listResponse;

	}

	@Override
	public PtoListResponse getActivePtoList(@Valid PtoListRequest ptoListRequest, HttpServletResponse httpResponse,
			PtoListResponse response) {
		List<PtoMaster> ptoMasterList = null;
		ptoMasterList = ptoManagementDao.getActivePtoList(ptoListRequest);
		List<PtoList> ptoOperationList = new ArrayList<>();
		PtoListResponse listResponse = new PtoListResponse();
		for (PtoMaster ptoOperationMaster : ptoMasterList) {
			PtoList ptoOperation = new PtoList();
			ptoOperation.setPtoName(ptoOperationMaster.getPtoName());
			ptoOperation.setPtoMasterId(ptoOperationMaster.getPtoMasterId());
			ptoOperation.setStatus(ptoOperationMaster.getStatus());
			ptoOperationList.add(ptoOperation);
		}
		listResponse.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
		listResponse.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		listResponse.setPtoList(ptoOperationList);
		httpResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
		httpResponse.setCharacterEncoding(CHAR_ENCODING_CONS);
		return listResponse;
	}
	
	@Override
	public PtoListResponse getPtoByPtoId(PtoListRequest ptoListRequest, HttpServletResponse httpResponse,
			PtoListResponse ptoListResponse) throws IOException {
		List<PtoMaster> ptoMasterList = ptoManagementDao.getPtoListByPtoMasterId(ptoListRequest);
		List<PtoList> ptoOperationList = new ArrayList<>();
		PtoListResponse responseList = new PtoListResponse();
		for (PtoMaster ptoOperationMaster : ptoMasterList) {
			PtoList ptoOperation = new PtoList();
			ptoOperation.setOrgId(ptoOperationMaster.getOrgId());
			ptoOperation.setPtoName(ptoOperationMaster.getPtoName());
			ptoOperation.setPtoMasterId(ptoOperationMaster.getPtoMasterId());
			ptoOperation.setStatus(ptoOperationMaster.getStatus());
			ptoOperationList.add(ptoOperation);
		}
		responseList.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
		responseList.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		responseList.setPtoList(ptoOperationList);
		httpResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
		httpResponse.setCharacterEncoding(CHAR_ENCODING_CONS);
		return responseList;

	}

	@Override
	public void errorPtoOperationStatusUpdate(PtoStatusUpdateRequest request, WebResponse response) {
		if (!dataValidation.ptoIdValidation(Long.valueOf(request.getPtoId()))) {
			response.setStatusCode(CustomErrorCodes.ORGANIZATION_NOT_EXIST.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.ORGANIZATION_NOT_EXIST.getCustomErrorMsg());
		}
	}


	@Override
	public void errorPtoOperationStatusCheck(PtoStatusCheckRequest request, WebResponse response) {
		if (!dataValidation.isValidUser(request.getUserId())) {
			response.setStatusCode(CustomErrorCodes.USER_NOT_EXIST.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.USER_NOT_EXIST.getCustomErrorMsg());
		} else if (!dataValidation.ptoIdValidation(request.getPtoMasterId())) {
			response.setStatusCode(CustomErrorCodes.ORGANIZATION_NOT_EXIST.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.ORGANIZATION_NOT_EXIST.getCustomErrorMsg());
		}
	}

	@Override
	public WebResponse updatePtoMaster(PtoUpdateRequest request, WebResponse response, HttpServletResponse httpResponse)
			throws IOException {

		if (ptoManagementDao.validatePtoOperationProfileUpdate(request)) {
			if (ptoManagementDao.updatePtoMaster(request)) {
				response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
				response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
				response.setReservedResponse("");
				httpResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
				httpResponse.setCharacterEncoding(ServerConstants.CHAR_ENCODING_CONS);
				return response;
			} else {
				response.setStatusCode(CustomErrorCodes.SERVER_ERROR.getCustomErrorCode());
				response.setStatusMessage(CustomErrorCodes.SERVER_ERROR.getCustomErrorMsg());
				response.setReservedResponse("");
				httpResponse.sendError(HttpErrorCodes.INTERNAL_SERVER_ERROR.getHttpErrorCode(), response.toString());
				return response;
			}
		} else {
			validatePtoOperationProfileUpdate(request, response);
			response.setReservedResponse("");
			httpResponse.sendError(HttpErrorCodes.NOT_FOUND.getHttpErrorCode(), response.toString());
			return response;
		}
	}

	@Override
	public void validatePtoOperationProfileUpdate(PtoUpdateRequest request, WebResponse response) {
		if (!dataValidation.ptoIdValidation(request.getPtoMasterId())) {
			response.setStatusCode(CustomErrorCodes.PTO_OPERATION_NOT_EXIST.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.PTO_OPERATION_NOT_EXIST.getCustomErrorMsg());
		}
	}

	@Override
	public PtoSearchResponse searchPto(PtoSearchRequest request) {
		PtoSearchResponse response = ptoManagementDao.searchPto(request);
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
	public PtoListResponse getPtoByOrganizationId(@Valid PtoListRequest ptoOperationListRequest,
			HttpServletResponse httpResponse, PtoListResponse response) {
		PtoListResponse listResponsePto = new PtoListResponse();
		List<PtoMaster> ptoOperationList = ptoManagementDao.getPtoByOrganizationId(ptoOperationListRequest);
		List<PtoList> ptoOperList = new ArrayList<>();

		for (PtoMaster ptoOperationMaster : ptoOperationList) {
			PtoList ptoOperation = new PtoList();
			ptoOperation.setOrgId(ptoOperationMaster.getOrgId());
			ptoOperation.setPtoName(ptoOperationMaster.getPtoName());
			ptoOperation.setPtoMasterId(ptoOperationMaster.getPtoMasterId());
			ptoOperList.add(ptoOperation);
		}
		if (!StringUtil.isNull(ptoOperationList)) {
			listResponsePto.setPtoList(ptoOperList);
			listResponsePto.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			listResponsePto.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		} else {
			listResponsePto.setStatusCode(CustomErrorCodes.FAILURE.getCustomErrorCode());
			listResponsePto.setStatusMessage(CustomErrorCodes.FAILURE.getCustomErrorMsg());
		}
		return listResponsePto;
	}

	@Override
	public PtoListResponse getPtoListWithStatusNotTerminated(@Valid PtoListRequest ptoListRequest,
			HttpServletResponse httpResponse, PtoListResponse response) throws IOException {
		List<PtoMaster> ptoMasterList = null;
		if (ptoListRequest.getUserType().equals(RoleLevel.SUPER_ADMIN.name())) {
			ptoMasterList = ptoManagementDao.getPtoListWithStatusNotTerminated(ptoListRequest);
		} else if (ptoListRequest.getUserType().equals(RoleLevel.ORG_ADMIN.name())) {
			ptoMasterList = ptoManagementDao.getPtoByOrganizationId(ptoListRequest);
		} else {
			ptoMasterList = ptoManagementDao.getPtoByPtoMasterId(ptoListRequest);
		}

		List<PtoList> ptoListWithStatusNotTerminated = new ArrayList<>();
		PtoListResponse responseListPto = new PtoListResponse();
		for (PtoMaster ptoOperationMaster : ptoMasterList) {
			PtoList ptoOperation = new PtoList();
			ptoOperation.setOrgId(ptoOperationMaster.getOrgId());
			ptoOperation.setPtoName(ptoOperationMaster.getPtoName());
			ptoOperation.setStatus(ptoOperationMaster.getStatus());
			ptoListWithStatusNotTerminated.add(ptoOperation);
		}
		responseListPto.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
		responseListPto.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		responseListPto.setPtoList(ptoListWithStatusNotTerminated);
		httpResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
		httpResponse.setCharacterEncoding(CHAR_ENCODING_CONS);
		return responseListPto;

	}

	@Override
	public PtoSearchResponse updatePtoStatus(PtoStatusUpdateRequest request, WebResponse response) throws IOException {
		PtoSearchResponse ptoSearchResponse = new PtoSearchResponse();
		if (!StringUtil.isNull(request)) {
			PtoMaster ptoMaster = ptoManagementDao.updatePtoOperationStatus(request);
			ptoSearchResponse.setPtoName(ptoMaster.getPtoName());
			ptoSearchResponse.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			ptoSearchResponse.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
			return ptoSearchResponse;
		} else {
			ptoSearchResponse.setStatusCode(CustomErrorCodes.SERVER_ERROR.getCustomErrorCode());
			ptoSearchResponse.setStatusMessage(CustomErrorCodes.SERVER_ERROR.getCustomErrorMsg());
			return ptoSearchResponse;
		}
	}

	@Override
	public PtoListResponse getPtoListByOrganizationId(@Valid PtoListRequest ptoListRequest,
			HttpServletResponse httpResponse, PtoListResponse response) {
			List<PtoMaster> ptoOperationList = ptoManagementDao.getPtoListByOrganizationId(ptoListRequest);
			List<PtoList> ptoOperList = new ArrayList<>();

			for (PtoMaster ptoOperationMaster : ptoOperationList) {
				PtoList ptoOperation = new PtoList();
				ptoOperation.setOrgId(ptoOperationMaster.getOrgId());
				ptoOperation.setPtoMasterId(ptoOperationMaster.getPtoMasterId());
				ptoOperation.setPtoName(ptoOperationMaster.getPtoName());
				ptoOperList.add(ptoOperation);
			}
			if (!StringUtil.isNull(ptoOperationList)) {
				response.setPtoList(ptoOperList);
				response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
				response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
			} else {
				response.setStatusCode(CustomErrorCodes.FAILURE.getCustomErrorCode());
				response.setStatusMessage(CustomErrorCodes.FAILURE.getCustomErrorMsg());
			}
			return response;
	}

	@Override
	public PtoListResponse getActivePtoListByOrganizationId(@Valid PtoListRequest ptoListRequest,
			HttpServletResponse httpResponse, PtoListResponse response) {
			List<PtoMaster> ptoOperationList = ptoManagementDao.getActivePtoListByOrganizationId(ptoListRequest);
			List<PtoList> ptoOperList = new ArrayList<>();

			for (PtoMaster ptoOperationMaster : ptoOperationList) {
				PtoList ptoOperation = new PtoList();
				ptoOperation.setOrgId(ptoOperationMaster.getOrgId());
				ptoOperation.setPtoMasterId(ptoOperationMaster.getPtoMasterId());
				ptoOperation.setPtoName(ptoOperationMaster.getPtoName());
				ptoOperList.add(ptoOperation);
			}
			if (!StringUtil.isNull(ptoOperationList)) {
				response.setPtoList(ptoOperList);
				response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
				response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
			} else {
				response.setStatusCode(CustomErrorCodes.FAILURE.getCustomErrorCode());
				response.setStatusMessage(CustomErrorCodes.FAILURE.getCustomErrorMsg());
			}
			return response;
	}
	
	@Override
	public WebResponse validatePtoId(String id) {
		WebResponse response = new WebResponse();
		String ptoId =  id.replace("\"", "");
		if (!dataValidation.validatePtoMasterID(ptoId)) {
			response.setStatusCode(CustomErrorCodes.PTO_NOT_EXIST.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.PTO_NOT_EXIST.getCustomErrorMsg());
			return response;
		} else {
			response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
			return response;
		}
	}
	
	@Override
	public PtoListResponse getPtoDataByPtoId(@Valid PtoListRequest ptoListRequest, HttpServletResponse httpResponse,
			PtoListResponse response) {
		List<PtoMaster> ptoMasterList = ptoManagementDao.getPtoDataByPtoMasterId(ptoListRequest);
		List<PtoList> ptoOperationList = new ArrayList<>();
		PtoListResponse responseList = new PtoListResponse();
		for (PtoMaster ptoOperationMaster : ptoMasterList) {
			PtoList ptoOperation = new PtoList();
			ptoOperation.setOrgId(ptoOperationMaster.getOrgId());
			ptoOperation.setPtoName(ptoOperationMaster.getPtoName());
			ptoOperation.setPtoMasterId(ptoOperationMaster.getPtoMasterId());
			ptoOperation.setStatus(ptoOperationMaster.getStatus());
			ptoOperationList.add(ptoOperation);
		}
		responseList.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
		responseList.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		responseList.setPtoList(ptoOperationList);
		httpResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
		httpResponse.setCharacterEncoding(CHAR_ENCODING_CONS);
		return responseList;
	}

	@Override
	public PtoListResponse getPtoByPtoMasterId(Long ptoMasterId) {
		PtoListResponse listResponse = new PtoListResponse();
		if (!StringUtil.isNull(ptoMasterId)) {
			PtoMaster ptoMaster = ptoManagementDao.getPtoByPtoMasterId(ptoMasterId);
			listResponse.setPtoMasterId(ptoMaster.getPtoMasterId());
			listResponse.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			listResponse.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
			return listResponse;
		} else {
			listResponse.setStatusCode(CustomErrorCodes.FAILURE.getCustomErrorCode());
			listResponse.setStatusMessage(CustomErrorCodes.FAILURE.getCustomErrorMsg());
			return listResponse;
		}

	}

}