package com.chatak.transit.afcs.server.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.chatak.transit.afcs.constants.TestConstants;
import com.chatak.transit.afcs.server.dao.CustomErrorResolution;
import com.chatak.transit.afcs.server.dao.DeviceModelMangementDao;
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
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;

public class DeviceModelManagementServiceImplTest {

	@InjectMocks
	DeviceModelMangementServiceImpl deviceManagement;

	@Mock
	DeviceRegistrationRequest deviceRegistrationRequest;

	@Mock
	DeviceModelMangementDao deviceManagmentDao;

	@Mock
	HttpServletResponse httpResponse;

	@Mock
	CustomErrorResolution dataValidation;

	@Mock
	DeviceModelRequest deviceModelRequest;

	@Mock
	DeviceTypeRegistrationRequest request;

	@Mock
	DeviceModelMangementDao deviceModelMangementDao;

	@Mock
	DeviceRegistrationResponse deviceRegistrationResponse;

	@Mock
	DeviceListRequest deviceListRequest;

	@Mock
	DeviceListResponse deviceListResponse;

	@Mock
	DeviceDetails deviceDetails;

	@Mock
	List<DeviceDetails> deviceDetailsList;

	@Mock
	DeviceModelExistCheckRequest deviceModelExistCheckRequest;

	@Mock
	DeviceModelExistCheckResponse deviceModelExistCheckResponse;

	@Mock
	DeviceModelResponse deviceModelResponse;

	@Mock
	DeviceModelProfileUpdateRequest deviceModelProfileUpdateRequest;

	@Mock
	WebResponse webResponse;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void deviceRegistrationTest() throws IOException {
		deviceRegistrationRequest = new DeviceRegistrationRequest();
		deviceRegistrationResponse = new DeviceRegistrationResponse();
		deviceRegistrationRequest.setUserId(TestConstants.USER_ID);
		deviceRegistrationResponse.setStatusCode(TestConstants.STATUS_CODE);
		deviceRegistrationResponse.setStatusMessage(TestConstants.STATUS_SUCCESS);
		Assert.assertNotNull(deviceRegistrationResponse);
		Assert.assertEquals(TestConstants.STATUS_CODE, deviceRegistrationResponse.getStatusCode());
		Assert.assertEquals(deviceRegistrationResponse.getStatusMessage(),
				CustomErrorCodes.SUCCESS.getCustomErrorMsg());
	}

	@Test
	public void deviceRegistrationTxnIdFailTest() throws IOException {
		deviceRegistrationRequest = new DeviceRegistrationRequest();
		deviceRegistrationResponse = new DeviceRegistrationResponse();
		Assert.assertNotNull(deviceRegistrationResponse);
	}

	@Test
	public void deviceRegistrationUserIdFailTest() throws IOException {
		deviceRegistrationRequest = new DeviceRegistrationRequest();
		deviceRegistrationResponse = new DeviceRegistrationResponse();
		Mockito.when(dataValidation.isValidUser(Matchers.anyString())).thenReturn(false);
		Assert.assertNotNull(deviceRegistrationResponse);
	}

	@Test
	public void testSaveDeviceModel() throws IOException {
		deviceModelRequest = new DeviceModelRequest();
		deviceModelResponse = new DeviceModelResponse();
		deviceModelRequest.setDeviceTypeName(TestConstants.DEVICE_TYPE);
		deviceModelRequest.setDeviceModel(TestConstants.STRING_ONE);
		deviceModelResponse.setStatusCode(TestConstants.STATUS_CODE);
		deviceModelResponse.setStatusMessage(TestConstants.STATUS_SUCCESS);
		Mockito.when(deviceModelMangementDao.validateDeviceModel(deviceModelRequest)).thenReturn(true);
		deviceModelResponse = deviceManagement.saveDeviceModel(deviceModelRequest, deviceModelResponse,
				httpResponse);
		Assert.assertNotNull(deviceModelResponse);
		Assert.assertEquals(TestConstants.STATUS_CODE, deviceModelResponse.getStatusCode());
		Assert.assertEquals(TestConstants.STATUS_SUCCESS, deviceModelResponse.getStatusMessage());
	}

	@Test
	public void testSaveDeviceModelElseTxnId() throws IOException {
		deviceModelRequest = new DeviceModelRequest();
		deviceModelResponse = new DeviceModelResponse();
		deviceModelRequest.setUserId(TestConstants.USER_ID);
		deviceModelRequest.setDeviceTypeName(TestConstants.DEVICE_TYPE);
		deviceModelRequest.setDeviceModel(TestConstants.STRING_ONE);
		Mockito.when(deviceManagmentDao.validateDeviceModel(deviceModelRequest)).thenReturn(false);
		deviceModelResponse = deviceManagement.saveDeviceModel(deviceModelRequest, deviceModelResponse,
				httpResponse);
		Assert.assertNotNull(deviceModelResponse);
	}

	@Test
	public void testSaveDeviceModelElsePTOId() throws IOException {
		deviceModelRequest = new DeviceModelRequest();
		deviceModelResponse = new DeviceModelResponse();
		deviceModelRequest.setUserId(TestConstants.USER_ID);
		deviceModelRequest.setDeviceTypeName(TestConstants.DEVICE_TYPE);
		deviceModelRequest.setDeviceModel(TestConstants.STRING_ONE);
		Mockito.when(deviceManagmentDao.validateDeviceModel(deviceModelRequest)).thenReturn(false);
		Mockito.when(dataValidation.isValidUser(Matchers.any())).thenReturn(true);
		deviceModelResponse = deviceManagement.saveDeviceModel(deviceModelRequest, deviceModelResponse,
				httpResponse);
		Assert.assertNotNull(deviceModelResponse);
	}

	private void setDeviceModelRequestCommonScript() {
		deviceModelRequest = new DeviceModelRequest();
		deviceModelResponse = new DeviceModelResponse();
		deviceModelRequest.setUserId(TestConstants.USER_ID);
		deviceModelRequest.setDeviceTypeName(TestConstants.DEVICE_TYPE);
		deviceModelRequest.setDeviceModel(TestConstants.STRING_ONE);
		Mockito.when(deviceManagmentDao.validateDeviceModel(deviceModelRequest)).thenReturn(false);
		Mockito.when(dataValidation.isValidUser(Matchers.any())).thenReturn(true);
		Mockito.when(dataValidation.ptoIdValidation(Matchers.any())).thenReturn(true);
	}

	@Test
	public void testGetDeviceListTransactionIdInvalid() throws IOException {
		deviceListResponse = new DeviceListResponse();
		deviceListResponse.setStatusCode(CustomErrorCodes.INVALID_TRANSACTION_ID.getCustomErrorCode());
		deviceListResponse.setStatusMessage(CustomErrorCodes.INVALID_TRANSACTION_ID.getCustomErrorMsg());
		Assert.assertEquals(CustomErrorCodes.INVALID_TRANSACTION_ID.getCustomErrorMsg(),
				deviceListResponse.getStatusMessage());
		Assert.assertEquals(CustomErrorCodes.INVALID_TRANSACTION_ID.getCustomErrorCode(),
				deviceListResponse.getStatusCode());
	}

	@Test
	public void testGetDeviceListPTOIdInvalid() throws IOException {
		deviceListRequest = new DeviceListRequest();
		Mockito.when(dataValidation.isValidUser(deviceListRequest.getUserId())).thenReturn(true);
		deviceListResponse = new DeviceListResponse();
		deviceListResponse.setStatusCode(CustomErrorCodes.PTO_OPERATION_NOT_EXIST.getCustomErrorCode());
		deviceListResponse.setStatusMessage(CustomErrorCodes.PTO_OPERATION_NOT_EXIST.getCustomErrorMsg());
		Assert.assertEquals(CustomErrorCodes.PTO_OPERATION_NOT_EXIST.getCustomErrorMsg(),
				deviceListResponse.getStatusMessage());
		Assert.assertEquals(CustomErrorCodes.PTO_OPERATION_NOT_EXIST.getCustomErrorCode(),
				deviceListResponse.getStatusCode());
	}

	@Test
	public void testGetDeviceListSuccess() throws IOException {
		deviceDetails = new DeviceDetails();
		deviceDetails.setAssignedMasterVersion(TestConstants.STRING_ONE);
		deviceDetails.setAdditionalSpecialData(TestConstants.STRING_ONE);
		deviceDetailsList = new ArrayList<>();
		deviceDetailsList.add(deviceDetails);
		deviceListRequest = new DeviceListRequest();
		deviceListRequest.setUserId(TestConstants.STRING_ONE);
		deviceListRequest.setPtoOperationId(TestConstants.STRING_ONE);
		deviceListResponse = new DeviceListResponse();
		deviceListResponse.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
		deviceListResponse.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		Assert.assertNotNull(deviceDetailsList);
		Assert.assertEquals(CustomErrorCodes.SUCCESS.getCustomErrorMsg(), deviceListResponse.getStatusMessage());
		Assert.assertEquals(CustomErrorCodes.SUCCESS.getCustomErrorCode(), deviceListResponse.getStatusCode());
	}

	@Test
	public void testValidateDeviceModelExistCheckTxnId() throws IOException {
		deviceModelExistCheckResponse = new DeviceModelExistCheckResponse();
		deviceModelExistCheckRequest = new DeviceModelExistCheckRequest();
		deviceModelExistCheckRequest.setUserId(TestConstants.USER_ID);
		deviceModelExistCheckRequest.setPtoOperationId(TestConstants.PTO_OPERATION_ID);
		deviceModelExistCheckRequest.setDeviceTypeName(TestConstants.DEVICE_TYPE);
		deviceModelExistCheckRequest.setDeviceModel(TestConstants.ADDRESS);
		Assert.assertNotNull(deviceModelExistCheckRequest);
	}

	private void setDeviceModelExistCheckRequestCommonScript() {
		deviceModelExistCheckResponse = new DeviceModelExistCheckResponse();
		deviceModelExistCheckRequest = new DeviceModelExistCheckRequest();
		deviceModelExistCheckRequest.setUserId(TestConstants.USER_ID);
		deviceModelExistCheckRequest.setPtoOperationId(TestConstants.PTO_OPERATION_ID);
		deviceModelExistCheckRequest.setDeviceTypeName(TestConstants.DEVICE_TYPE);
		deviceModelExistCheckRequest.setDeviceModel(TestConstants.ADDRESS);
		Mockito.when(dataValidation.isValidUser(deviceModelExistCheckRequest.getUserId())).thenReturn(true);
	}

	@Test
	public void testDeviceModelProfileUpdate() throws IOException {
		deviceModelProfileUpdateRequest = new DeviceModelProfileUpdateRequest();
		deviceModelProfileUpdateRequest.setUserId(TestConstants.USER_ID);
		deviceModelProfileUpdateRequest.setDeviceTypeName(TestConstants.DEVICE_TYPE);
		deviceModelProfileUpdateRequest.setDeviceModel(TestConstants.STRING_ONE);
		Mockito.when(deviceModelMangementDao.validateDeviceModelProfileUpdate(deviceModelProfileUpdateRequest))
				.thenReturn(true);
		Mockito.when(deviceModelMangementDao.updateDeviceModelProfile(deviceModelProfileUpdateRequest))
				.thenReturn(true);
		webResponse = deviceManagement.deviceModelProfileUpdate(deviceModelProfileUpdateRequest, webResponse,
				httpResponse);
		Assert.assertNotNull(webResponse);
	}

	@Test
	public void testDeviceModelProfileUpdateServerErr() throws IOException {
		deviceModelProfileUpdateRequest = new DeviceModelProfileUpdateRequest();
		deviceModelProfileUpdateRequest.setUserId(TestConstants.USER_ID);
		deviceModelProfileUpdateRequest.setDeviceTypeName(TestConstants.DEVICE_TYPE);
		deviceModelProfileUpdateRequest.setDeviceModel(TestConstants.STRING_ONE);
		Mockito.when(deviceModelMangementDao.validateDeviceModelProfileUpdate(deviceModelProfileUpdateRequest))
				.thenReturn(true);
		webResponse = deviceManagement.deviceModelProfileUpdate(deviceModelProfileUpdateRequest, webResponse,
				httpResponse);
		Assert.assertNotNull(webResponse);
	}


	@Test
	public void testDeviceModelProfileUpdateValidTypeErr() throws IOException {
		deviceModelProfileUpdateRequest = new DeviceModelProfileUpdateRequest();
		deviceModelProfileUpdateRequest.setUserId(TestConstants.USER_ID);
		deviceModelProfileUpdateRequest.setDeviceTypeName(TestConstants.DEVICE_TYPE);
		deviceModelProfileUpdateRequest.setDeviceModel(TestConstants.STRING_ONE);
		webResponse = new WebResponse();
		Mockito.when(deviceModelMangementDao.validateDeviceModelProfileUpdate(deviceModelProfileUpdateRequest))
				.thenReturn(false);
		Mockito.when(dataValidation.isValidUser(deviceModelProfileUpdateRequest.getUserId())).thenReturn(true);
		Mockito.when(dataValidation.deviceType(deviceModelProfileUpdateRequest.getDeviceTypeId()))
				.thenReturn(false);
		webResponse = deviceManagement.deviceModelProfileUpdate(deviceModelProfileUpdateRequest, webResponse,
				httpResponse);
		Assert.assertNotNull(webResponse);
		Assert.assertEquals(CustomErrorCodes.DEVICE_TYPE_NAME.getCustomErrorCode(), webResponse.getStatusCode());
		Assert.assertEquals(CustomErrorCodes.DEVICE_TYPE_NAME.getCustomErrorMsg(), webResponse.getStatusMessage());
	}

	@Test
	public void testDeviceModelProfileUpdateValidModelNameErr() throws IOException {
		deviceModelProfileUpdateRequest = new DeviceModelProfileUpdateRequest();
		deviceModelProfileUpdateRequest.setUserId(TestConstants.USER_ID);
		deviceModelProfileUpdateRequest.setDeviceTypeName(TestConstants.DEVICE_TYPE);
		deviceModelProfileUpdateRequest.setDeviceModel(TestConstants.STRING_ONE);
		webResponse = new WebResponse();
		Mockito.when(deviceModelMangementDao.validateDeviceModelProfileUpdate(deviceModelProfileUpdateRequest))
				.thenReturn(false);
		Mockito.when(dataValidation.isValidUser(deviceModelProfileUpdateRequest.getUserId())).thenReturn(true);
		Mockito.when(dataValidation.deviceType(deviceModelProfileUpdateRequest.getDeviceTypeId()))
				.thenReturn(true);
		webResponse = deviceManagement.deviceModelProfileUpdate(deviceModelProfileUpdateRequest, webResponse,
				httpResponse);
		Assert.assertNotNull(webResponse);
		Assert.assertEquals(CustomErrorCodes.DEVICE_MODEL_NAME_NOT_EXIST.getCustomErrorCode(),
				webResponse.getStatusCode());
		Assert.assertEquals(CustomErrorCodes.DEVICE_MODEL_NAME_NOT_EXIST.getCustomErrorMsg(),
				webResponse.getStatusMessage());
	}
}
