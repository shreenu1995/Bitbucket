package com.chatak.transit.afcs.server.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatak.transit.afcs.server.dao.CustomErrorResolution;
import com.chatak.transit.afcs.server.dao.DeviceOnboardManagementDao;
import com.chatak.transit.afcs.server.dao.OrganizationManagementDao;
import com.chatak.transit.afcs.server.dao.model.DeviceOnboardMaster;
import com.chatak.transit.afcs.server.dao.repository.OrganizationMasterRepository;
import com.chatak.transit.afcs.server.enums.CustomErrorCodes;
import com.chatak.transit.afcs.server.enums.Status;
import com.chatak.transit.afcs.server.pojo.web.DeviceManufacturerSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceModelRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceOnboardListSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceOnboardProfileUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceOnboardSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceOnboardSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceOnboardingRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.service.DeviceOnboardingService;
import com.chatak.transit.afcs.server.util.StringUtil;

@Service
public class DeviceOnboardingServiceImpl implements DeviceOnboardingService {

	@Autowired
	DeviceOnboardManagementDao deviceOnboardManagementDao;

	@Autowired
	CustomErrorResolution dataValidation;

	@Autowired
	OrganizationManagementDao organizationManagementDao;
	
	@Autowired
	OrganizationMasterRepository organizationMasterRepository;

	@Override
	public WebResponse saveDeviceOnboarding(DeviceOnboardingRequest request, WebResponse response) {
		DeviceOnboardMaster deviceOnboardMaster = new DeviceOnboardMaster();
		if (deviceOnboardManagementDao.validateDeviceModel(request.getDeviceModelId())) {
			deviceOnboardMaster.setDeviceTypeId(request.getDeviceTypeId());
			deviceOnboardMaster.setDeviceManufacturerId(request.getDeviceManufacturerCode());
			deviceOnboardMaster.setDeviceModelId(request.getDeviceModelId());
			deviceOnboardMaster.setOrganizationId(request.getOrganizationId());
			deviceOnboardMaster.setPtoId(request.getPtoId());
			deviceOnboardMaster.setStatus(Status.ACTIVE.getValue());
			deviceOnboardManagementDao.saveDeviceOnboardingRegistrations(deviceOnboardMaster);
			response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		} else {
			deviceOnBoardingErrors(request, response);
		}
		return response;
	}

	public void deviceOnBoardingErrors(DeviceOnboardingRequest request, WebResponse response) {
		if (dataValidation.isDeviceModelExists(request.getDeviceModelId())) {
			response.setStatusCode(CustomErrorCodes.INVALID_DEVICE_MODEL.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.INVALID_DEVICE_MODEL.getCustomErrorMsg());
		}
	}

	@Override
	public DeviceOnboardListSearchResponse searchDeviceOnboarding(DeviceOnboardSearchRequest request,
			HttpServletResponse httpResponse) {
		DeviceOnboardListSearchResponse response = deviceOnboardManagementDao.getDeviceOnboardSearchList(request);
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
	public WebResponse updatDeviceOnboardProfile(DeviceOnboardProfileUpdateRequest request, WebResponse webResponse) {
		if (deviceOnboardManagementDao.updateDeviceOnboardProfile(request)) {
			webResponse.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
			webResponse.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			return webResponse;
		} else {
			webResponse.setStatusCode(CustomErrorCodes.SERVER_ERROR.getCustomErrorCode());
			webResponse.setStatusMessage(CustomErrorCodes.SERVER_ERROR.getCustomErrorMsg());
			return webResponse;
		}
	}

	@Override
	public List<DeviceTypeSearchRequest> getDeviceTypeName() {
		return deviceOnboardManagementDao.getDeviceTypeName();
	}

	@Override
	public List<DeviceManufacturerSearchRequest> getDeviceManuName() {
		return deviceOnboardManagementDao.getDeviceManuName();
	}

	@Override
	public List<DeviceModelRequest> getDeviceModelName() {
		return deviceOnboardManagementDao.getDeviceModelName();
	}

	@Override
	public DeviceOnboardSearchResponse updateDeviceOnBoardStatus(DeviceOnboardProfileUpdateRequest request) {
		DeviceOnboardSearchResponse response = new DeviceOnboardSearchResponse();
		if (deviceOnboardManagementDao.validateDeviceOnboardStatusUpdate(request)) {
			DeviceOnboardMaster deviceOnboardMaster = deviceOnboardManagementDao
					.updateDeviceOnboardStatusUpdate(request);
			if (!StringUtil.isNull(deviceOnboardMaster)) {
				response.setDeviceOnboardId(String.valueOf(deviceOnboardMaster.getDeviceOnboardId()));
				response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
				response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
			} else {
				response.setStatusCode(CustomErrorCodes.FAILURE.getCustomErrorCode());
				response.setStatusMessage(CustomErrorCodes.FAILURE.getCustomErrorMsg());
			}
		}
		return response;
	}
}
