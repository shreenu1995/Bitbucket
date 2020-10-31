package com.chatak.transit.afcs.server.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatak.transit.afcs.server.dao.CustomErrorResolution;
import com.chatak.transit.afcs.server.dao.FareManagementDao;
import com.chatak.transit.afcs.server.dao.model.BulkUploadDetails;
import com.chatak.transit.afcs.server.dao.model.FareMaster;
import com.chatak.transit.afcs.server.dao.repository.OrganizationMasterRepository;
import com.chatak.transit.afcs.server.enums.CustomErrorCodes;
import com.chatak.transit.afcs.server.enums.Status;
import com.chatak.transit.afcs.server.pojo.web.BulkUploadRequest;
import com.chatak.transit.afcs.server.pojo.web.FareRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.FareRegistrationResponse;
import com.chatak.transit.afcs.server.pojo.web.FareSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.FareSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.FareStatusUpdate;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.service.FareManagementService;
import com.chatak.transit.afcs.server.util.StringUtil;

@Service
public class FareManagementServiceImpl implements FareManagementService {

	@Autowired
	FareManagementDao fareManagementDao;

	@Autowired
	CustomErrorResolution dataValidation;
	
	@Autowired
	OrganizationMasterRepository organizationMasterRepository;
	
	@Override
	public FareRegistrationResponse saveFareRegistrationRequest(FareRegistrationRequest request,
			HttpServletResponse httpResponse, FareRegistrationResponse response) {

		FareMaster fareMaster = new FareMaster();
		fareMaster.setFareId(request.getFareId());
		fareMaster.setPtoId(request.getPtoId());
		fareMaster.setFareName(request.getFareName());
		fareMaster.setFareType(request.getFareType());
		fareMaster.setDifference(request.getDifference());
		fareMaster.setFareAmount(request.getFareAmount());
		fareMaster.setOrganizationId(request.getOrganizationId());
		fareMaster.setStatus(Status.ACTIVE.getValue());
		fareMaster.setOrganizationId(request.getOrganizationId());
		Long fareId = fareManagementDao.saveFareRegistrationDetails(fareMaster);
		response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
		response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		response.setFareCode(fareId);
		return response;

	}

	@Override
	public void validateFareRegistrationErrors(FareRegistrationRequest request, FareRegistrationResponse response) {
		if (dataValidation.fareNameValidation(request.getFareName())) {
			response.setStatusCode(CustomErrorCodes.FARE_NAME_ALREADY_EXIST.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.FARE_NAME_ALREADY_EXIST.getCustomErrorMsg());
		} else {
			response.setStatusCode(CustomErrorCodes.FAILURE.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.FAILURE.getCustomErrorMsg());
		}
	}

	@Override
	public FareSearchResponse searchFare(FareSearchRequest request) {
		FareSearchResponse response = fareManagementDao.getFareList(request);
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
	public WebResponse fareUpdate(FareStatusUpdate fareUpdate, WebResponse response) {
		if (fareManagementDao.validateFare(fareUpdate)) {
			if (fareManagementDao.updateFare(fareUpdate)) {
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
	public FareSearchResponse fareStatusUpdate(FareStatusUpdate request, WebResponse response) {
		FareSearchResponse fareSearchResponse = new FareSearchResponse();

		if (!StringUtil.isNull(request)) {
			FareMaster fareMaster = fareManagementDao.updateFareStatus(request);
			fareSearchResponse.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			fareSearchResponse.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
			fareSearchResponse.setFareName(fareMaster.getFareName());
			return fareSearchResponse;
		} else {
			fareSearchResponse.setStatusCode(CustomErrorCodes.SERVER_ERROR.getCustomErrorCode());
			fareSearchResponse.setStatusMessage(CustomErrorCodes.SERVER_ERROR.getCustomErrorMsg());
			return fareSearchResponse;
		}
	}

	@Override
	public FareSearchResponse processBulkUpload(List<BulkUploadRequest> request) {
		FareSearchResponse fareSearchResponse = new FareSearchResponse();
		if (!StringUtil.isListNullNEmpty(request)) {
			for (BulkUploadRequest fareData : request) {
				fareSearchResponse = validateBulkFare(fareData);
			}
			fareSearchResponse.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			fareSearchResponse.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		} else {
			fareSearchResponse.setStatusCode(CustomErrorCodes.SERVER_ERROR.getCustomErrorCode());
			fareSearchResponse.setStatusMessage(CustomErrorCodes.SERVER_ERROR.getCustomErrorMsg());
		}
		return fareSearchResponse;
	}
	
	private FareSearchResponse validateBulkFare(BulkUploadRequest request) {
		FareSearchResponse responseSearchFare = new FareSearchResponse();
		BulkUploadDetails bulkUploadDetails = new BulkUploadDetails();
		if(!StringUtil.isNull(request)) {
			bulkUploadDetails.setPtoId(Long.valueOf(request.getPtoId()));
			bulkUploadDetails.setRouteCode(request.getRouteCode());
			bulkUploadDetails.setStartStopCode(request.getStartStopCode());
			bulkUploadDetails.setEndStopCode(request.getEndStopCode());
			bulkUploadDetails.setFareAmount(request.getFareAmount());
			fareManagementDao.saveBulkUpload(bulkUploadDetails);
		}
		
		return responseSearchFare;
	}

	@Override
	public List<BulkUploadDetails> getAllBulkFares(Long ptoId) {
		return fareManagementDao.getAllBulkFares(ptoId);
	}

	@Override
	public List<BulkUploadDetails> getFareByStopId(String stopId) {
		return fareManagementDao.getFareByStopId(stopId);
	}

}
