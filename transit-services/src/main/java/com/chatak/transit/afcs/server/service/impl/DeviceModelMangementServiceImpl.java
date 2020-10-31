package com.chatak.transit.afcs.server.service.impl;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.chatak.transit.afcs.server.constants.ServerConstants;
import com.chatak.transit.afcs.server.dao.CustomErrorResolution;
import com.chatak.transit.afcs.server.dao.DeviceModelMangementDao;
import com.chatak.transit.afcs.server.dao.model.DeviceModel;
import com.chatak.transit.afcs.server.enums.CustomErrorCodes;
import com.chatak.transit.afcs.server.enums.Status;
import com.chatak.transit.afcs.server.pojo.web.DeviceModelListResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceModelListSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceModelProfileUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceModelRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceModelResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceModelSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceModelStatusUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.service.DeviceModelMangementService;
import com.chatak.transit.afcs.server.util.StringUtil;

@Service
public class DeviceModelMangementServiceImpl implements DeviceModelMangementService {

	@Autowired
	DeviceModelMangementDao deviceModelMangementDao;

	@Autowired
	CustomErrorResolution dataValidation;

	@Override
	public DeviceModelResponse saveDeviceModel(DeviceModelRequest request, DeviceModelResponse response,
			HttpServletResponse httpResponse) throws IOException {
		Timestamp timeStamp = Timestamp.from(Instant.now());
		if (deviceModelMangementDao.validateDeviceModel(request)) {
			DeviceModel deviceModel = new DeviceModel();
			response = new DeviceModelResponse();
			deviceModel.setDeviceIMEINumber(request.getDeviceIMEINumber());
			deviceModel.setDeviceTypeId(request.getDeviceTypeId());
			deviceModel.setDeviceManufacturerId(request.getDeviceManufacturerCode());
			deviceModel.setDescription(request.getDescription());
			deviceModel.setDeviceModelName(request.getDeviceModel());
			deviceModel.setCreatedTime(timeStamp);
			deviceModel.setCreatedBy(request.getUserId());
			deviceModel.setUpdatedTime(timeStamp);
			deviceModel.setUpdatedBy(request.getUserId());
			deviceModel.setStatus(Status.ACTIVE.getValue());
			Long deviceId = deviceModelMangementDao.saveDeviceModelDetails(deviceModel);
			response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
			httpResponse.setCharacterEncoding(ServerConstants.CHAR_ENCODING_CONS);
			httpResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
			response.setDeviceId(deviceId);
			return response;
		} else {
			checkeDeviceModelRegistrationErrors(request, response);
			return response;
		}
	}

	private void checkeDeviceModelRegistrationErrors(DeviceModelRequest request,
			DeviceModelResponse response) {
		if (dataValidation.deviceModel(request.getDeviceModel())) {
			response.setStatusCode(CustomErrorCodes.DEVICE_MODEL_NAME.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.DEVICE_MODEL_NAME.getCustomErrorMsg());
		} else if (dataValidation.deviceIMEINumber(request.getDeviceIMEINumber())) {
			response.setStatusCode(CustomErrorCodes.DEVICE_IMEI_NUMBER.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.DEVICE_IMEI_NUMBER.getCustomErrorMsg());
		} else {
			response.setStatusCode(CustomErrorCodes.FAILURE.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.FAILURE.getCustomErrorMsg());
		}
	}

	@Override
	public DeviceModelListSearchResponse searchDeviceModel(DeviceModelSearchRequest request,
			HttpServletResponse httpResponse) {
		DeviceModelListSearchResponse response = null;
		response = deviceModelMangementDao.getEquipementModelSearchList(request);
		if (response != null) {
			response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
			return response;
		}
		return response;
	}

	@Override
	public WebResponse deviceModelProfileUpdate(DeviceModelProfileUpdateRequest request, WebResponse webResponse,
			HttpServletResponse httpResponse) throws IOException {
		if (deviceModelMangementDao.validateDeviceModelProfileUpdate(request)) {
			if (deviceModelMangementDao.updateDeviceModelProfile(request)) {
				webResponse.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
				webResponse.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
				return webResponse;
			} else {
				webResponse.setStatusCode(CustomErrorCodes.SERVER_ERROR.getCustomErrorCode());
				webResponse.setStatusMessage(CustomErrorCodes.SERVER_ERROR.getCustomErrorMsg());
				return webResponse;
			}
		}
		validateDeviceModelProfileUpdate(request, webResponse);
		return webResponse;
	}

	private void validateDeviceModelProfileUpdate(DeviceModelProfileUpdateRequest request, WebResponse response) {
		if (!dataValidation.deviceType(request.getDeviceTypeId())) {
			response.setStatusCode(CustomErrorCodes.DEVICE_TYPE_NAME.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.DEVICE_TYPE_NAME.getCustomErrorMsg());
		} else if (!dataValidation.deviceModel(request.getDeviceModel())) {
			response.setStatusCode(CustomErrorCodes.DEVICE_MODEL_NAME_NOT_EXIST.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.DEVICE_MODEL_NAME_NOT_EXIST.getCustomErrorMsg());
		} else {
			response.setStatusCode(CustomErrorCodes.FAILURE.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.FAILURE.getCustomErrorMsg());
		}
	}

	@Override
	public DeviceModelListResponse deviceModelList(DeviceModelSearchRequest request) {
		DeviceModelListResponse response = deviceModelMangementDao.getDeviceModelList(request);
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
	public DeviceModelListSearchResponse updateDeviceModelStatus(DeviceModelStatusUpdateRequest request,
			WebResponse response) {
		DeviceModelListSearchResponse deviceModelListResponse = new DeviceModelListSearchResponse();

		if (!StringUtil.isNull(request)) {
			DeviceModel deviceModel = deviceModelMangementDao.updateDeviceModelStatusUpdate(request);
			deviceModelListResponse.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			deviceModelListResponse.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
			deviceModelListResponse.setDeviceModelName(deviceModel.getDeviceModelName());
			return deviceModelListResponse;
		} else {
			deviceModelListResponse.setStatusCode(CustomErrorCodes.SERVER_ERROR.getCustomErrorCode());
			deviceModelListResponse.setStatusMessage(CustomErrorCodes.SERVER_ERROR.getCustomErrorMsg());
			return deviceModelListResponse;
		}

	}
}