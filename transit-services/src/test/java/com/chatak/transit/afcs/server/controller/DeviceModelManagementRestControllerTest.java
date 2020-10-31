package com.chatak.transit.afcs.server.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.env.Environment;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.chatak.transit.afcs.constants.TestConstants;
import com.chatak.transit.afcs.server.enums.CustomErrorCodes;
import com.chatak.transit.afcs.server.pojo.web.DeviceDetails;
import com.chatak.transit.afcs.server.pojo.web.DeviceListRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceListResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceModelExistCheckRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceModelExistCheckResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceModelProfileUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceModelRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceModelResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceRegistrationResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeListViewRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeListViewResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeRegistrationResponse;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.service.DeviceModelMangementService;

@RunWith(MockitoJUnitRunner.Silent.class)
public class DeviceModelManagementRestControllerTest {

	@InjectMocks
	DeviceModelManagementRestController deviceManagementRestController;

	@Mock
	DeviceTypeRegistrationRequest request;

	@Mock
	DeviceTypeRegistrationResponse response;

	@Mock
	HttpServletResponse httpResponse;

	@Mock
	DeviceModelMangementService deviceService;

	@Mock
	DeviceModelRequest deviceModelRequest;

	@Mock
	DeviceRegistrationResponse deviceRegistrationResponse;

	@Mock
	DeviceRegistrationRequest deviceRequest;

	@Mock
	DeviceRegistrationResponse registrationResponse;

	@Mock
	DeviceListResponse deviceListResponse;

	@Mock
	List<DeviceDetails> deviceList;

	@Mock
	DeviceListRequest deviceListRequest;

	@Mock
	DeviceDetails deviceDetails;

	@Mock
	DeviceModelExistCheckRequest deviceModelExistCheckRequest;

	@Mock
	DeviceTypeListViewRequest deviceTypeListViewRequest;

	@Mock
	DeviceTypeListViewResponse deviceTypeListViewResponse;

	@Mock
	DeviceModelResponse deviceModelResponse;

	@Mock
	DeviceModelExistCheckResponse deviceModelExistCheckResponse;

	@Mock
	BindingResult bindingResult;

	@Mock
	Environment property;

	@Mock
	List<String> errorCodeList;

	@Mock
	List<ObjectError> objectErrors;
	
	@Mock
	DeviceModelProfileUpdateRequest deviceModelProfileUpdateRequest;
	
	@Mock
	WebResponse webResponse;
	
	@Test
	public void testDeviceModelBindingResultErrors() throws IOException {
		deviceModelResponse = new DeviceModelResponse();
		deviceModelRequest = new DeviceModelRequest();
		deviceModelRequest.setUserId(TestConstants.USER_ID);
		deviceModelRequest.setDeviceTypeName(TestConstants.DEVICE_TYPE);
		deviceModelRequest.setDeviceModel(TestConstants.STRING_ONE);
		deviceModelResponse.setStatusMessage(TestConstants.STATUS_MESSAGE);
		setBindingResultErrorsCommonScript();
		deviceModelResponse = deviceManagementRestController.deviceModel(deviceModelRequest, bindingResult,
				deviceModelResponse, httpResponse);
		Assert.assertNotNull(deviceModelRequest);
	}

	private void setBindingResultErrorsCommonScript() {
		ObjectError objectError = new ObjectError(TestConstants.STATUS_CODE, TestConstants.STATUS_MESSAGE);
		errorCodeList = new ArrayList<String>();
		objectErrors = new ArrayList<>();
		objectErrors.add(objectError);
		String errorCode = TestConstants.STATUS_CODE;
		errorCodeList.add(errorCode);
		Mockito.when(bindingResult.getAllErrors()).thenReturn(objectErrors);
		Mockito.when(property.getProperty(objectError.getDefaultMessage())).thenReturn(errorCode);
		Mockito.when(bindingResult.hasErrors()).thenReturn(true);
	}	@Test
	public void testDeviceModel() throws IOException {
		deviceModelResponse = new DeviceModelResponse();
		deviceModelRequest = new DeviceModelRequest();
		deviceModelRequest.setUserId(TestConstants.USER_ID);
		deviceModelRequest.setDeviceTypeName(TestConstants.DEVICE_TYPE);
		deviceModelRequest.setDeviceModel(TestConstants.STRING_ONE);
		deviceModelResponse.setStatusMessage(TestConstants.STATUS_MESSAGE);
		Mockito.when(deviceService.saveDeviceModel(deviceModelRequest, deviceModelResponse, httpResponse))
				.thenReturn(deviceModelResponse);
		deviceModelResponse = deviceManagementRestController.deviceModel(deviceModelRequest, bindingResult,
				deviceModelResponse, httpResponse);
		Assert.assertEquals(TestConstants.STATUS_MESSAGE, deviceModelResponse.getStatusMessage());
	}

	@Test
	public void testDeviceModelProfileUpdate() throws IOException{
		deviceModelProfileUpdateRequest = new DeviceModelProfileUpdateRequest();
		deviceModelProfileUpdateRequest.setUserId(TestConstants.USER_ID);
		deviceModelProfileUpdateRequest.setDeviceTypeName(TestConstants.DEVICE_TYPE);
		deviceModelProfileUpdateRequest.setDeviceModel(TestConstants.STRING_ONE);
		webResponse = new WebResponse();
		webResponse.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
		setBindingResultErrorsCommonScript();
		Mockito.when(deviceService.deviceModelProfileUpdate(deviceModelProfileUpdateRequest, webResponse, httpResponse)).thenReturn(webResponse);
		webResponse = deviceManagementRestController.deviceModelProfileUpdate(deviceModelProfileUpdateRequest, bindingResult, webResponse, httpResponse);
		Assert.assertEquals(CustomErrorCodes.SUCCESS.getCustomErrorCode(),webResponse.getStatusCode());
	}
	
	@Test
	public void testDeviceModelProfileUpdate1() throws IOException{
		deviceModelProfileUpdateRequest = new DeviceModelProfileUpdateRequest();
		deviceModelProfileUpdateRequest.setUserId(TestConstants.USER_ID);
		deviceModelProfileUpdateRequest.setDeviceTypeName(TestConstants.DEVICE_TYPE);
		deviceModelProfileUpdateRequest.setDeviceModel(TestConstants.STRING_ONE);
		webResponse = new WebResponse();
		webResponse.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
		Mockito.when(deviceService.deviceModelProfileUpdate(deviceModelProfileUpdateRequest,webResponse,httpResponse)).thenReturn(webResponse);
		Mockito.when(deviceService.deviceModelProfileUpdate(deviceModelProfileUpdateRequest, webResponse, httpResponse)).thenReturn(webResponse);
		webResponse = deviceManagementRestController.deviceModelProfileUpdate(deviceModelProfileUpdateRequest, bindingResult, webResponse, httpResponse);
		Assert.assertEquals(CustomErrorCodes.SUCCESS.getCustomErrorCode(),webResponse.getStatusCode());
	}
	
}