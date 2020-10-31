package com.afcs.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.afcs.web.service.DeviceOnboardService;
import com.afcs.web.util.JsonUtil;
import com.chatak.transit.afcs.server.enums.CustomErrorCodes;
import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.DevcieTypeList;
import com.chatak.transit.afcs.server.pojo.web.DeviceManufacturerListResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceModelList;
import com.chatak.transit.afcs.server.pojo.web.DeviceModelListResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceModelSearchData;
import com.chatak.transit.afcs.server.pojo.web.DeviceOnboardListSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceOnboardProfileUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceOnboardSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceOnboardSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceOnboardingRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceOnboardingResponse;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.util.StringUtil;

@Service
public class DeviceOnboardServiceImpl implements DeviceOnboardService {

	@Autowired
	private JsonUtil jsonUtil;

	@Autowired
	Environment properties;

	private static final String AFCS_SERVER_ERROR = "afcs.server.not.responding";

	@Override
	public DeviceOnboardingResponse saveDeviceOnboard(DeviceOnboardingRequest request)
			throws AFCSException {
		DeviceOnboardingResponse response = jsonUtil.postRequest(request, DeviceOnboardingResponse.class,
				"online/device/deviceOnboardingRegistration");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;

	}

	@Override
	public DeviceOnboardListSearchResponse searchDeviceOnboard(DeviceOnboardSearchRequest request)
			throws AFCSException {
		DeviceOnboardListSearchResponse response = jsonUtil.postRequest(request,
				DeviceOnboardListSearchResponse.class, "online/device/searchDeviceOnboard");
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
	public DeviceOnboardingResponse getDeviceTypeList(DeviceOnboardingRequest deviceOnboardingRequest)
			throws AFCSException {
		DeviceOnboardingResponse response = jsonUtil.postRequest(deviceOnboardingRequest,
				DeviceOnboardingResponse.class, "online/device/deviceTypeList");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

	@Override
	public DeviceModelListResponse geteEuipmentModelListForManufacturer(
			DeviceModelSearchData deviceModelSearchData) throws AFCSException {
		DeviceModelListResponse response = jsonUtil.postRequest(deviceModelSearchData,
				DeviceModelListResponse.class, "online/device/deviceModelList");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

	@Override
	public WebResponse updateDeviceOnboard(DeviceOnboardingRequest request) throws AFCSException {
		WebResponse response = jsonUtil.postRequest(request, WebResponse.class,
				"online/device/deviceOnboardProfileUpdate");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

	@Override
	public DeviceOnboardSearchResponse updateDeviceOnboardStatus(DeviceOnboardProfileUpdateRequest request) throws AFCSException {
		DeviceOnboardSearchResponse response = jsonUtil.postRequest(request, DeviceOnboardSearchResponse.class,
				"online/device/deviceOnboardStatusUpdate");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

	@Override
	public DevcieTypeList getDeviceTypeList() throws AFCSException {
		DevcieTypeList listOfDeviceName= jsonUtil.postRequest(DevcieTypeList.class, "online/device/getDeviceTypeName");
		if (StringUtil.isNull(listOfDeviceName)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return listOfDeviceName;
	}

	@Override
	public DeviceManufacturerListResponse getDeviceManuf() throws AFCSException {
		DeviceManufacturerListResponse listOfDeviceManu= jsonUtil.postRequest(DeviceManufacturerListResponse.class, "online/device/getDeviceManuName");
		if (StringUtil.isNull(listOfDeviceManu)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return listOfDeviceManu;
	}

	@Override
	public DeviceModelList getDeviceModel() throws AFCSException {
		DeviceModelList listOfDeviceModel= jsonUtil.postRequest(DeviceModelList.class, "online/device/getDeviceModelName");
		if (StringUtil.isNull(listOfDeviceModel)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return listOfDeviceModel;
	}

}
