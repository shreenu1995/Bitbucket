package com.chatak.transit.afcs.server.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatak.transit.afcs.server.dao.FeeManagementDao;
import com.chatak.transit.afcs.server.dao.model.FeeMaster;
import com.chatak.transit.afcs.server.enums.CustomErrorCodes;
import com.chatak.transit.afcs.server.enums.Status;
import com.chatak.transit.afcs.server.pojo.web.FeeRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.FeeRegistrationResponse;
import com.chatak.transit.afcs.server.pojo.web.FeeSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.FeeSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.FeeUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.service.FeeManagementService;
import com.chatak.transit.afcs.server.util.StringUtil;

@Service
public class FeeManagementServiceImpl implements FeeManagementService {

	@Autowired
	FeeManagementDao feeManagementDao;

	@Override
	public FeeRegistrationResponse feeRegistration(FeeRegistrationRequest feeRegistrationRequest,
			HttpServletResponse httpResponse, FeeRegistrationResponse feeRegistrationResponse) {

		FeeMaster feeMaster = new FeeMaster();

		feeMaster.setOrganizationId(feeRegistrationRequest.getOrganizationId());
		feeMaster.setPtoId(feeRegistrationRequest.getPtoId());
		feeMaster.setFeeName(feeRegistrationRequest.getFeeName());
		feeMaster.setFeeId(feeRegistrationRequest.getFeeId());
		feeMaster.setPtoFeeType(feeRegistrationRequest.getPtoFeeType());
		feeMaster.setPtoFeeValue(feeRegistrationRequest.getPtoFeeValue());
		feeMaster.setOrgFeeType(feeRegistrationRequest.getOrgFeeType());
		feeMaster.setOrgFeeValue(feeRegistrationRequest.getOrgFeeValue());
		feeMaster.setPtoShareType(feeRegistrationRequest.getPtoShareType());
		feeMaster.setPtoShareValue(feeRegistrationRequest.getPtoshareValue());
		feeMaster.setOrgShareType(feeRegistrationRequest.getOrgShareType());
		feeMaster.setOrgShareValue(feeRegistrationRequest.getOrgShareValue());
		feeMaster.setStatus(Status.ACTIVE.getValue());
		feeMaster.setReason(feeRegistrationRequest.getReason());
		feeMaster.setOrganizationId(feeRegistrationRequest.getOrganizationId());
		feeMaster = feeManagementDao.saveFee(feeMaster);
		if (!StringUtil.isNull(feeMaster)) {
			feeRegistrationResponse.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			feeRegistrationResponse.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
			
		}
		return feeRegistrationResponse;
	}

	@Override
	public FeeSearchResponse searchFee(FeeSearchRequest request) {
		FeeSearchResponse response = feeManagementDao.getFeeList(request);
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
	public WebResponse updateFeeProfile(FeeUpdateRequest request, WebResponse response) {
		if(feeManagementDao.validateFeeStatusUpdate(request)) {
			if(feeManagementDao.updateFeeProfileUpdate(request)) {
				response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
				response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
				return response;
			} else {
				response.setStatusCode(CustomErrorCodes.SERVER_ERROR.getCustomErrorCode());
				response.setStatusMessage(CustomErrorCodes.SERVER_ERROR.getCustomErrorMsg());
				return response;
			}
		}
		return response;
	}

	@Override
	public List<String> getOrganisationList() {
		return feeManagementDao.getOrganisationList();
	}

	@Override
	public FeeSearchResponse updateFeeStatus(FeeRegistrationRequest request, WebResponse response) {
		FeeSearchResponse feeSearchResponse = new FeeSearchResponse();
		
		if(!StringUtil.isNull(request)) {
			FeeMaster feeMaster = feeManagementDao.updateFeeStatus(request);
			feeSearchResponse.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			feeSearchResponse.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
			feeSearchResponse.setFeeName(feeMaster.getFeeName());
			return feeSearchResponse;
		}  else {
			feeSearchResponse.setStatusCode(CustomErrorCodes.SERVER_ERROR.getCustomErrorCode());
			feeSearchResponse.setStatusMessage(CustomErrorCodes.SERVER_ERROR.getCustomErrorMsg());
			return feeSearchResponse;
		}
	}
}