package com.chatak.transit.afcs.server.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatak.transit.afcs.server.dao.CustomErrorResolution;
import com.chatak.transit.afcs.server.dao.OperatorManagementDao;
import com.chatak.transit.afcs.server.dao.PtoManagementDao;
import com.chatak.transit.afcs.server.dao.model.Operator;
import com.chatak.transit.afcs.server.dao.repository.PtoMasterRepository;
import com.chatak.transit.afcs.server.enums.CustomErrorCodes;
import com.chatak.transit.afcs.server.enums.Status;
import com.chatak.transit.afcs.server.pojo.web.OperatorRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.OperatorResponse;
import com.chatak.transit.afcs.server.pojo.web.OperatorSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.OperatorStatusChangeRequest;
import com.chatak.transit.afcs.server.pojo.web.OperatorUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.service.OperatorManagementService;
import com.chatak.transit.afcs.server.util.StringUtil;

@Service
public class OperatorManagementServiceImpl implements OperatorManagementService {

	@Autowired
	OperatorManagementDao operatorManagementDao;
	
	@Autowired
	PtoManagementDao ptoManagementDao;

	@Autowired
	CustomErrorResolution dataValidation;
	
	@Autowired
	PtoMasterRepository ptoMasterRepository;

	@Override
	public List<Operator> getAllOperator(Long ptoId) {
		return operatorManagementDao.getAllOperators(ptoId);
	}

	@Override
	public WebResponse operatorRegister(OperatorRegistrationRequest request) {
		Operator operator = new Operator();
		WebResponse response = new WebResponse();

		if (operatorManagementDao.validateOperatorRegistrationRequest(request)) {
			operator.setPtoId(request.getPtoId());
			operator.setOrganizationId(request.getOrganizationId());
			operator.setOperatorName(request.getOperatorName());
			operator.setOperatorContactNumber(request.getOperatorContactNumber());
			operator.setOperatorUserId(request.getOperatorUserId());
			operator.setOperatorPassword(request.getOperatorPassword());
			operator.setStatus(Status.ACTIVE.getValue());
			if (operatorManagementDao.saveOperatorDetails(operator)) {
				response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
				response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
			} else {
				response.setStatusCode(CustomErrorCodes.SERVER_ERROR.getCustomErrorCode());
				response.setStatusMessage(CustomErrorCodes.SERVER_ERROR.getCustomErrorMsg());
			}
		} else {
			validateOperatorRegistrationErrors(request, response);
		}

		return response;
	}

	@Override
	public void validateOperatorRegistrationErrors(OperatorRegistrationRequest request, WebResponse response) {
		if (!dataValidation.isValidUser(request.getUserId())) {
			response.setStatusCode(CustomErrorCodes.USER_NOT_EXIST.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.USER_NOT_EXIST.getCustomErrorMsg());
		} else if (dataValidation.validateOperatorName(request.getOperatorName())) {
			response.setStatusCode(CustomErrorCodes.INVALID_OPERTOR_NAME.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.INVALID_OPERTOR_NAME.getCustomErrorMsg());
		}
	}

	@Override
	public OperatorResponse searchOperators(OperatorSearchRequest request) {
		OperatorResponse response = operatorManagementDao.searchOperators(request);

		if (!StringUtil.isNull(response)) {
			response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		} else {
			response.setStatusCode(CustomErrorCodes.FAILURE.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.FAILURE.getCustomErrorMsg());
		}
		return response;
	}

	public void errorOperatorStatusUpdate(OperatorStatusChangeRequest request, WebResponse response) {
		if (!dataValidation.validateOperatorId(request.getOperatorId())) {
			response.setStatusCode(CustomErrorCodes.PRODUCTID_NOT_EXIST.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.PRODUCTID_NOT_EXIST.getCustomErrorMsg());
		}
	}

	@Override
	public WebResponse updateOperatorProfile(OperatorUpdateRequest request, WebResponse response,
			HttpServletResponse httpResponse) {
		if (operatorManagementDao.updateOperatorProfile(request)) {
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
	public OperatorResponse updateOperatorStatus(OperatorStatusChangeRequest request, WebResponse response) {
		OperatorResponse operatorResponse = new OperatorResponse();

		if (!StringUtil.isNull(request)) {
			Operator operator = operatorManagementDao.updateOperatorStatus(request);
			operatorResponse.setOperatorName(operator.getOperatorName());
			operatorResponse.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			operatorResponse.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
			return operatorResponse;
		} else {
			operatorResponse.setStatusCode(CustomErrorCodes.FAILURE.getCustomErrorCode());
			operatorResponse.setStatusCode(CustomErrorCodes.FAILURE.getCustomErrorCode());
			return operatorResponse;
		}
	}

}
