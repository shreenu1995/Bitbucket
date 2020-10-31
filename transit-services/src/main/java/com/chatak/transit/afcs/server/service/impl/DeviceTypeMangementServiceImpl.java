package com.chatak.transit.afcs.server.service.impl;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.chatak.transit.afcs.server.constants.ServerConstants;
import com.chatak.transit.afcs.server.dao.CustomErrorResolution;
import com.chatak.transit.afcs.server.dao.DeviceTypeManagementDao;
import com.chatak.transit.afcs.server.dao.model.DeviceTypeMaster;
import com.chatak.transit.afcs.server.enums.CustomErrorCodes;
import com.chatak.transit.afcs.server.enums.Status;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeData;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeListViewRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeListViewResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeProfileUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeRegistrationResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeStatusUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.service.DeviceTypeManagementService;
import com.chatak.transit.afcs.server.util.StringUtil;

@Service
public class DeviceTypeMangementServiceImpl implements DeviceTypeManagementService {

	@Autowired
	DeviceTypeManagementDao deviceTypeManagementDao;

	@Autowired
	CustomErrorResolution dataValidation;
		
	@Override
	public DeviceTypeRegistrationResponse deviceTypeRegistration(DeviceTypeRegistrationRequest request,
			DeviceTypeRegistrationResponse response, HttpServletResponse httpResponse) throws IOException {
		Timestamp timeStamp = Timestamp.from(Instant.now());
		if (deviceTypeManagementDao.validateDeviceTypeRegistrationRequest(request)) {
			DeviceTypeMaster deviceTypeMaster = new DeviceTypeMaster();
			deviceTypeMaster.setDeviceTypeName(request.getDeviceTypeName());
			deviceTypeMaster.setDescription(request.getDescription());
			deviceTypeMaster.setCreatedTime(timeStamp);
			deviceTypeMaster.setCreatedBy(request.getUserId());
			deviceTypeMaster.setUpdatedTime(timeStamp);
			deviceTypeMaster.setUpdatedBy(request.getUserId());
			deviceTypeMaster.setUpdatedBy(request.getUserId());
			deviceTypeMaster.setStatus(Status.ACTIVE.getValue());
			Long deviceId = deviceTypeManagementDao.saveDeviceTypeRegistrationDetails(deviceTypeMaster);
			response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
			httpResponse.setCharacterEncoding(ServerConstants.CHAR_ENCODING_CONS);
			httpResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
			response.setDeviceTypeId(deviceId);
			return response;
		} else {
			validateDeviceTypeRegistrationErrors(request, response);
			return response;
		}
	}

	@Override
	public void validateDeviceTypeRegistrationErrors(DeviceTypeRegistrationRequest request,
			DeviceTypeRegistrationResponse response) {
		if (dataValidation.deviceTypeNameValidation(request.getDeviceTypeName())) {
			response.setStatusCode(CustomErrorCodes.DEVICE_NAME_ALREADY_EXIST.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.DEVICE_NAME_ALREADY_EXIST.getCustomErrorMsg());
		}
		else {
			response.setStatusCode(CustomErrorCodes.FAILURE.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.FAILURE.getCustomErrorMsg());
		}
	}

	@Override
	public DeviceTypeSearchResponse deviceTypeSearch(DeviceTypeSearchRequest request) {
		DeviceTypeSearchResponse response = deviceTypeManagementDao.searchDeviceType(request);
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
	public WebResponse deviceTypeProfileUpdate(@Valid DeviceTypeProfileUpdateRequest request,
			WebResponse response, HttpServletResponse httpResponse) throws IOException {
		if (deviceTypeManagementDao.validateDeviceTypeProfileUpdate(request)) {
			if (deviceTypeManagementDao.updateDeviceTypeProfile(request)) {
				response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
				response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
				return response;
			} else {
				response.setStatusCode(CustomErrorCodes.SERVER_ERROR.getCustomErrorCode());
				response.setStatusMessage(CustomErrorCodes.SERVER_ERROR.getCustomErrorMsg());
				return response;
			}
		}
		checkeDeviceTypeProfileUpdateErrors(request, response);
		return response;
	}

	private void checkeDeviceTypeProfileUpdateErrors(DeviceTypeProfileUpdateRequest request, WebResponse response) {
		 if (!dataValidation.deviceTypeIdValidation(request.getDeviceTypeId())) {
			response.setStatusCode(CustomErrorCodes.DEVICE_TYPE_ID_NOT_EXISTS.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.DEVICE_TYPE_ID_NOT_EXISTS.getCustomErrorMsg());
		} else {
			response.setStatusCode(CustomErrorCodes.FAILURE.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.FAILURE.getCustomErrorMsg());
		}
	}
	
	@Override
	public WebResponse deviceTypeStatusuUpdate(DeviceTypeStatusUpdateRequest request, WebResponse response,
			HttpServletResponse httpResponse) {

		if (deviceTypeManagementDao.validateDeviceTypeStatusUpdate(request)) {
			if (deviceTypeManagementDao.updateDeviceTypeStatus(request)) {
				response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
				response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
				return response;
			} else {
				response.setStatusCode(CustomErrorCodes.SERVER_ERROR.getCustomErrorCode());
				response.setStatusMessage(CustomErrorCodes.SERVER_ERROR.getCustomErrorMsg());
				return response;
			}
		}
		checkeDeviceTypeStatusuUpdateErrors(request, response);
		return response;
	}

	private void checkeDeviceTypeStatusuUpdateErrors(DeviceTypeStatusUpdateRequest request,
			WebResponse response) {
		if (!dataValidation.deviceTypeIdValidation(request.getDeviceTypeId())) {
			response.setStatusCode(CustomErrorCodes.DEVICE_TYPE_ID_NOT_EXISTS.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.DEVICE_TYPE_ID_NOT_EXISTS.getCustomErrorMsg());
		} else {
			response.setStatusCode(CustomErrorCodes.FAILURE.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.FAILURE.getCustomErrorMsg());
		}
	}
	
	@Override
	public DeviceTypeListViewResponse deviceTypeListView(DeviceTypeListViewRequest request,
			DeviceTypeListViewResponse response, HttpServletResponse httpResponse) throws IOException {
			List<DeviceTypeMaster> deviceTypeList = deviceTypeManagementDao
					.getDeviceTypeListView(request);
			List<DeviceTypeData> deviceViewList = new ArrayList<>();
			for (DeviceTypeMaster eqpmentTypeMaster : deviceTypeList) {
				DeviceTypeData data = new DeviceTypeData();
				data.setDeviceTypeId(eqpmentTypeMaster.getDeviceTypeId());
				data.setDeviceTypeName(eqpmentTypeMaster.getDeviceTypeName());
				deviceViewList.add(data);
			}
			response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
			return response;
	}
}
