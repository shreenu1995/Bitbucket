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
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.env.Environment;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.chatak.transit.afcs.constants.TestConstants;
import com.chatak.transit.afcs.server.pojo.web.DeviceDetails;
import com.chatak.transit.afcs.server.pojo.web.DeviceListRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceListResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceModelExistCheckRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceModelExistCheckResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceModelProfileUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceModelResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceRegistrationResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeListViewRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeListViewResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeRegistrationResponse;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.service.DeviceTypeManagementService;

@RunWith(MockitoJUnitRunner.class)
public class DeviceTypeManagementRestControllerTest {

	@InjectMocks
	DeviceTypeManagementRestController deviceTypeManagementRestController;

	@Mock
	DeviceTypeRegistrationRequest request;
	
	@Mock
	DeviceTypeRegistrationResponse response;
			
	@Mock
	HttpServletResponse httpResponse;

	@Mock
	DeviceTypeManagementService deviceTypeManagementService;

	@Mock
	DeviceRegistrationResponse deviceRegistrationResponse;

	@Mock
	DeviceRegistrationRequest deviceRequest;

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
	public void deviceTypeRegistrationTest() throws IOException {
		request = new DeviceTypeRegistrationRequest();
		response = new DeviceTypeRegistrationResponse();
		request.setDescription(TestConstants.DESCRIPTION);
		response.setStatusMessage(TestConstants.STATUS_MESSAGE);
		Mockito.when(deviceTypeManagementService.deviceTypeRegistration(request, response, httpResponse)).thenReturn(response);
		DeviceTypeRegistrationResponse result = deviceTypeManagementRestController.deviceTypeRegistration(request,
				bindingResult, response, httpResponse);
		Assert.assertNotNull(result);
		Assert.assertEquals(TestConstants.STATUS_MESSAGE, result.getStatusMessage());
	}

	@Test
	public void testDeviceTypeRegistrationBindingResultErrors() throws IOException {
		request = new DeviceTypeRegistrationRequest();
		response = new DeviceTypeRegistrationResponse();
		request.setDescription(TestConstants.DESCRIPTION);
		response.setStatusMessage(TestConstants.STATUS_MESSAGE);
		setBindingResultErrorsCommonScript();
		DeviceTypeRegistrationResponse result = deviceTypeManagementRestController.deviceTypeRegistration(request,
				bindingResult, response, httpResponse);
		Assert.assertNotNull(result);
	}

	@Test
	public void testDeviceTypeListView() throws IOException {
		deviceTypeListViewRequest = new DeviceTypeListViewRequest();
		deviceTypeListViewResponse = new DeviceTypeListViewResponse();
		deviceTypeListViewResponse.setStatusCode(TestConstants.STATUS_CODE);
		deviceTypeListViewResponse.setStatusMessage(TestConstants.STATUS_MESSAGE);
		Mockito.when(deviceTypeManagementService.deviceTypeListView(deviceTypeListViewRequest, deviceTypeListViewResponse,
				httpResponse)).thenReturn(deviceTypeListViewResponse);
		deviceTypeListViewResponse = deviceTypeManagementRestController
				.deviceTypeListView(deviceTypeListViewRequest, bindingResult, deviceTypeListViewResponse, httpResponse);
		Assert.assertNotNull(deviceTypeListViewResponse);
		Assert.assertEquals(TestConstants.STATUS_CODE, deviceTypeListViewResponse.getStatusCode());
		Assert.assertEquals(TestConstants.STATUS_MESSAGE, deviceTypeListViewResponse.getStatusMessage());
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
	}

}
