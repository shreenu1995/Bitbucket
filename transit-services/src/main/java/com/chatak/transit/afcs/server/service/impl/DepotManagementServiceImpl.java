/**
 * @author Girmiti Software
 *
 */
package com.chatak.transit.afcs.server.service.impl;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import com.chatak.transit.afcs.server.constants.ServerConstants;
import com.chatak.transit.afcs.server.dao.CustomErrorResolution;
import com.chatak.transit.afcs.server.dao.DepotManagementDao;
import com.chatak.transit.afcs.server.dao.model.DepotMaster;
import com.chatak.transit.afcs.server.enums.CustomErrorCodes;
import com.chatak.transit.afcs.server.enums.HttpErrorCodes;
import com.chatak.transit.afcs.server.enums.Status;
import com.chatak.transit.afcs.server.pojo.web.DepotData;
import com.chatak.transit.afcs.server.pojo.web.DepotListViewRequest;
import com.chatak.transit.afcs.server.pojo.web.DepotListViewResponse;
import com.chatak.transit.afcs.server.pojo.web.DepotProfileUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.DepotRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.DepotRegistrationResponse;
import com.chatak.transit.afcs.server.pojo.web.DepotSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.DepotSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.DepotStatusCheckRequest;
import com.chatak.transit.afcs.server.pojo.web.DepotStatusUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.service.DepotManagementService;
import com.chatak.transit.afcs.server.util.StringUtil;

@Service("DepotServices")
public class DepotManagementServiceImpl implements DepotManagementService {

	@Autowired
	CustomErrorResolution dataValidation;

	@Autowired
	DepotManagementDao depotManagementDao;

	@PersistenceContext
	private EntityManager em;

	Logger logger = LoggerFactory.getLogger(DepotManagementServiceImpl.class);

	@Override
	@Transactional
	public DepotRegistrationResponse saveDepotRegistrationrequest(DepotRegistrationRequest request,
			HttpServletResponse httpResponse, DepotRegistrationResponse response) throws IOException {
		if (depotManagementDao.validateDepotName(request)){
			DepotMaster depotMaster = new DepotMaster();
			depotMaster.setDepotName(request.getDepotName());
			depotMaster.setOrganizationId(request.getOrganizationId());
			depotMaster.setPtoId(request.getPtoId());
			depotMaster.setStatus(Status.ACTIVE.getValue());
			depotMaster.setDepotIncharge(request.getDepotIncharge());
			depotMaster.setDepotShortName(request.getDepotShortName());
			depotMaster.setCreatedDateTime(Timestamp.from(Instant.now()));
			depotMaster.setUpdatedDateTime(Timestamp.from(Instant.now()));
			depotMaster.setDepotMobile(request.getMobile());
			depotMaster = depotManagementDao.saveDepotRegistration(depotMaster);
			if (!StringUtil.isNull(depotMaster)) {
				response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
				response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
				response.setReservedResponse("");
				return response;
			} else {
				response.setStatusCode(CustomErrorCodes.SERVER_ERROR.getCustomErrorCode());
				response.setStatusMessage(CustomErrorCodes.SERVER_ERROR.getCustomErrorMsg());
				return response;
			}
		}

		validateDepotRegistrationErrors(request, response);
		return response;
	}

	@Override
	public void validateDepotRegistrationErrors(DepotRegistrationRequest request, DepotRegistrationResponse response) {
		if (dataValidation.validateDepotName(request.getDepotName())) {
			response.setStatusCode(CustomErrorCodes.INVALID_DEPOT_NAME.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.INVALID_DEPOT_NAME.getCustomErrorMsg());
		} else {
			response.setStatusCode(CustomErrorCodes.SERVER_ERROR.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.SERVER_ERROR.getCustomErrorMsg());
		}
	}

	@Override
	public DepotListViewResponse getDepotListViewRequest(DepotListViewRequest request, DepotListViewResponse response,
			HttpServletResponse httpServletResponse, BindingResult bindingResult) throws IOException {
		if (depotManagementDao.validateGetDepotListView(request)) {
			DepotMaster depotMaster = new DepotMaster();
			List<DepotMaster> depotList = depotManagementDao.getDepotListView(depotMaster);
			List<DepotData> depotListView = new ArrayList<>();

			for (DepotMaster dpotMaster : depotList) {
				DepotData data = new DepotData();
				data.setDepotId(dpotMaster.getDepotId());
				data.setDepotName(dpotMaster.getDepotName());
				depotListView.add(data);
			}
			response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
			response.setListOfDepotInformation(depotListView);
			httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
			httpServletResponse.setCharacterEncoding(ServerConstants.CHAR_ENCODING_CONS);
			return response;
		} else {
			checkDepotListErrors(request, response);
			httpServletResponse.sendError(HttpErrorCodes.NOT_FOUND.getHttpErrorCode(), response.toString());
			return response;
		}
	}

	private void checkDepotListErrors(DepotListViewRequest request, DepotListViewResponse response) {
		if (!dataValidation.isValidUser(request.getUserId())) {
			response.setStatusCode(CustomErrorCodes.USER_NOT_EXIST.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.USER_NOT_EXIST.getCustomErrorMsg());
		} 
	}

	@Override
	public WebResponse updateDepotProfile(DepotProfileUpdateRequest request, WebResponse response,
			HttpServletResponse httpResponse) throws IOException {
		if (depotManagementDao.validateDepotProfileUpdateRequest(request)) {
			if (depotManagementDao.updateDepotProfile(request)) {
				response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
				response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
				httpResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
				httpResponse.setCharacterEncoding(ServerConstants.CHAR_ENCODING_CONS);
				return response;
			} else {
				response.setStatusCode(CustomErrorCodes.SERVER_ERROR.getCustomErrorCode());
				response.setStatusMessage(CustomErrorCodes.SERVER_ERROR.getCustomErrorMsg());
				httpResponse.sendError(HttpErrorCodes.INTERNAL_SERVER_ERROR.getHttpErrorCode(), response.toString());
				return response;
			}
		} else {
			validateDepotProfileUpdate(request, response);
			httpResponse.sendError(HttpErrorCodes.NOT_FOUND.getHttpErrorCode(), response.toString());
			return response;
		}
	}

	@Override
	public void validateDepotProfileUpdate(DepotProfileUpdateRequest request, WebResponse response) {
		if (!dataValidation.isValidUser(request.getUserId())) {
			response.setStatusCode(CustomErrorCodes.USER_NOT_EXIST.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.USER_NOT_EXIST.getCustomErrorMsg());
		} else if (!dataValidation.validateDepotId(request.getDepotId())) {
			response.setStatusCode(CustomErrorCodes.DEPOT_NOT_EXIST.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.DEPOT_NOT_EXIST.getCustomErrorMsg());
		}
	}


	@Override
	public void errorDepotStatusUpdate(DepotStatusUpdateRequest request, WebResponse response) {
		if (!dataValidation.validateDepotId(request.getDepotId())) {
			response.setStatusCode(CustomErrorCodes.DEPOT_NOT_EXIST.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.DEPOT_NOT_EXIST.getCustomErrorMsg());
		}
	}

	@Override
	public WebResponse checkDepotStatus(DepotStatusCheckRequest request, WebResponse response,
			HttpServletResponse httpResponse) throws IOException {

		if (depotManagementDao.validateDepotStatusCheck(request)) {
			if (depotManagementDao.checkDepotStatus(request)) {
				response.setStatusCode(CustomErrorCodes.DEPOT_ACTIVE.getCustomErrorCode());
				response.setStatusMessage(CustomErrorCodes.DEPOT_ACTIVE.getCustomErrorMsg());
				httpResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
				httpResponse.setCharacterEncoding(ServerConstants.CHAR_ENCODING_CONS);
				return response;
			} else {
				response.setStatusCode(CustomErrorCodes.DEPOT_NOT_ACTIVE.getCustomErrorCode());
				response.setStatusMessage(CustomErrorCodes.DEPOT_NOT_ACTIVE.getCustomErrorMsg());
				httpResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
				httpResponse.setCharacterEncoding(ServerConstants.CHAR_ENCODING_CONS);
				return response;

			}
		} else {
			errorDepotStatusCheck(request, response);
			httpResponse.sendError(HttpErrorCodes.NOT_FOUND.getHttpErrorCode(), response.toString());
			return response;
		}
	}

	@Override
	public void errorDepotStatusCheck(DepotStatusCheckRequest request, WebResponse response) {
		if (!dataValidation.validateDepotId(request.getDepotId())) {
			response.setStatusCode(CustomErrorCodes.DEPOT_NOT_EXIST.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.DEPOT_NOT_EXIST.getCustomErrorMsg());
		} else if (!dataValidation.ptoIdValidation(request.getPtoMasterId())) {
			response.setStatusCode(CustomErrorCodes.PTO_OPERATION_NOT_EXIST.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.PTO_OPERATION_NOT_EXIST.getCustomErrorMsg());
		}
	}

	@Override
	public DepotSearchResponse searchDepot(DepotSearchRequest request) {
		 DepotSearchResponse response = depotManagementDao.searchdepot(request);
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
	public DepotSearchResponse updateDepotStatus(DepotStatusUpdateRequest request, WebResponse response,
			HttpServletResponse httpResponse) throws IOException {
		DepotSearchResponse depotSearchResponse = new DepotSearchResponse();
		
		if(!StringUtil.isNull(request)) {
			DepotMaster depotMaster = depotManagementDao.updateDepotStatus(request);
			depotSearchResponse.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			depotSearchResponse.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
			depotSearchResponse.setDepotName(depotMaster.getDepotName());
			return depotSearchResponse;
		} else {
			depotSearchResponse.setStatusCode(CustomErrorCodes.SERVER_ERROR.getCustomErrorCode());
			depotSearchResponse.setStatusMessage(CustomErrorCodes.SERVER_ERROR.getCustomErrorMsg());
			return depotSearchResponse;
		}
		
	}
}