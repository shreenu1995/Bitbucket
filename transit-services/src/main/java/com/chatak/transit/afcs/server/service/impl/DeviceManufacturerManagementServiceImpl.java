package com.chatak.transit.afcs.server.service.impl;

import java.sql.Timestamp;
import java.time.Instant;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.chatak.transit.afcs.server.constants.ServerConstants;
import com.chatak.transit.afcs.server.dao.CustomErrorResolution;
import com.chatak.transit.afcs.server.dao.DeviceManufacturerManagementDao;
import com.chatak.transit.afcs.server.dao.model.DeviceManufacturerMaster;
import com.chatak.transit.afcs.server.enums.CustomErrorCodes;
import com.chatak.transit.afcs.server.enums.Status;
import com.chatak.transit.afcs.server.pojo.web.DeviceManufacturerListSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceManufacturerListViewRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceManufacturerProfileUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceManufacturerRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceManufacturerRegistrationResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceManufacturerSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceManufacturerStatusUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeListViewRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeListViewResponse;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.service.DeviceManufacturerManagementService;
import com.chatak.transit.afcs.server.util.StringUtil;

@Service
public class DeviceManufacturerManagementServiceImpl implements DeviceManufacturerManagementService{

	@Autowired
	DeviceManufacturerManagementDao deviceManufacturerManagementDao;
	
	@Autowired
	CustomErrorResolution dataValidation;
	
	@Override
	public DeviceManufacturerRegistrationResponse deviceManufacturerRegistration(
			@Valid DeviceManufacturerRegistrationRequest request, DeviceManufacturerRegistrationResponse response,
			HttpServletResponse httpResponse) {
		Timestamp timeStamp = Timestamp.from(Instant.now());
		if (deviceManufacturerManagementDao.validateDeviceManufacturerRegistrationRequest(request)) {
			DeviceManufacturerMaster deviceManufacturerMaster = new DeviceManufacturerMaster();
			deviceManufacturerMaster.setDeviceTypeId(request.getDeviceTypeId());
			deviceManufacturerMaster.setDeviceManufacturer(request.getDeviceManufacturer());
			deviceManufacturerMaster.setDescription(request.getDescription());
			deviceManufacturerMaster.setCreatedTime(timeStamp);
			deviceManufacturerMaster.setCreatedBy(request.getUserId());
			deviceManufacturerMaster.setUpdatedTime(timeStamp);
			deviceManufacturerMaster.setUpdatedBy(request.getUserId());
			deviceManufacturerMaster.setStatus(Status.ACTIVE.getValue());
			Long deviceManufacturerId = deviceManufacturerManagementDao.saveDeviceManufacturerRegistrationDetails(deviceManufacturerMaster);
			response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
			httpResponse.setCharacterEncoding(ServerConstants.CHAR_ENCODING_CONS);
			httpResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
			response.setDeviceManufacturerCode(deviceManufacturerId);
			return response;
		} 
		checkeDeviceManufacturerRegistrationErrors(request,response);
		return response;
		
	}
	
	private void checkeDeviceManufacturerRegistrationErrors(DeviceManufacturerRegistrationRequest request, DeviceManufacturerRegistrationResponse response) {
		 if (dataValidation.deviceManufacturerValidation(request.getDeviceManufacturer())) {
			response.setStatusCode(CustomErrorCodes.DEVICE_MANUFACTURER_ALREADY_EXIST.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.DEVICE_MANUFACTURER_ALREADY_EXIST.getCustomErrorMsg());
		} else {
			response.setStatusCode(CustomErrorCodes.FAILURE.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.FAILURE.getCustomErrorMsg());
		}
	}

	@Override
	public DeviceManufacturerListSearchResponse searchDeviceManufacturer(
			DeviceManufacturerSearchRequest request) {
		DeviceManufacturerListSearchResponse respons = deviceManufacturerManagementDao.searchEquipementManufacturer(request);
		if(!StringUtil.isNull(respons)) {
			respons.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			respons.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		} else {
			respons.setStatusCode(CustomErrorCodes.FAILURE.getCustomErrorCode());
			respons.setStatusMessage(CustomErrorCodes.FAILURE.getCustomErrorMsg());
		}
		return respons;
	}
	
	@Override
	public DeviceTypeListViewResponse deviceTypeList(DeviceTypeListViewRequest request) {
		DeviceTypeListViewResponse response = deviceManufacturerManagementDao.getDeviceTypeList(request);
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
	public DeviceManufacturerListSearchResponse deviceManufacturerList(DeviceManufacturerListViewRequest request) {
		DeviceManufacturerListSearchResponse response = deviceManufacturerManagementDao.getDeviceManufacturerList(request);
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
	public WebResponse deviceManufacturerProfileUpdate(DeviceManufacturerProfileUpdateRequest request,
			WebResponse response, HttpServletResponse httpResponse) {
		if (deviceManufacturerManagementDao.validateDeviceManufacturerProfileUpdate(request)) {
			if (deviceManufacturerManagementDao.updateDeviceManufacturerProfile(request)) {
				response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
				response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
				return response;
			} else {
				response.setStatusCode(CustomErrorCodes.SERVER_ERROR.getCustomErrorCode());
				response.setStatusMessage(CustomErrorCodes.SERVER_ERROR.getCustomErrorMsg());
				return response;
			}
		}
		checkeDeviceManufacturerProfileUpdateErrors(request, response);
		return response;
	}

	private void checkeDeviceManufacturerProfileUpdateErrors(DeviceManufacturerProfileUpdateRequest request, WebResponse response) {
		 if (!dataValidation.deviceManufacturerIdValidation(request.getDeviceManufacturerCode())) {
			response.setStatusCode(CustomErrorCodes.DEVICE_MANUFACTURER_ID_NOT_EXISTS.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.DEVICE_MANUFACTURER_ID_NOT_EXISTS.getCustomErrorMsg());
		} else {
			response.setStatusCode(CustomErrorCodes.FAILURE.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.FAILURE.getCustomErrorMsg());
		}
	}

	@Override
	public WebResponse deviceManufacturerStatusuUpdate(@Valid DeviceManufacturerStatusUpdateRequest request,
			WebResponse response, HttpServletResponse httpResponse) {
		Timestamp timeStamp = Timestamp.from(Instant.now());
		if (deviceManufacturerManagementDao.validateDeviceManufacturerStatusUpdate(request)) {
			DeviceManufacturerMaster deviceManufacturerMaster = new DeviceManufacturerMaster();
			deviceManufacturerMaster.setDeviceManufacturerId(request.getDeviceManufacturerCode());
			deviceManufacturerMaster.setStatus(request.getStatus());
			deviceManufacturerMaster.setUpdatedTime(timeStamp);
			deviceManufacturerMaster.setUpdatedBy(request.getUserId());
			if (deviceManufacturerManagementDao.updateDeviceManufacturerStatus(deviceManufacturerMaster)) {
				response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
				response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
				return response;
			} else {
				response.setStatusCode(CustomErrorCodes.SERVER_ERROR.getCustomErrorCode());
				response.setStatusMessage(CustomErrorCodes.SERVER_ERROR.getCustomErrorMsg());
				return response;
			}
		}
		checkeDeviceManufacurerStatusuUpdateErrors(request, response);
		return response;
	}

	private void checkeDeviceManufacurerStatusuUpdateErrors(DeviceManufacturerStatusUpdateRequest request,
			WebResponse response) {
		if (!dataValidation.deviceManufacturerIdValidation(request.getDeviceManufacturerCode())) {
			response.setStatusCode(CustomErrorCodes.DEVICE_MANUFACTURER_ID_NOT_EXISTS.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.DEVICE_MANUFACTURER_ID_NOT_EXISTS.getCustomErrorMsg());
		} else {
			response.setStatusCode(CustomErrorCodes.FAILURE.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.FAILURE.getCustomErrorMsg());
		}
	}

}
