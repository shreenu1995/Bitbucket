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
import com.chatak.transit.afcs.server.dao.DeviceTypeManagementDao;
import com.chatak.transit.afcs.server.dao.model.DeviceTypeMaster;
import com.chatak.transit.afcs.server.enums.CustomErrorCodes;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeData;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeListViewRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeListViewResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeRegistrationResponse;

public class DeviceTypeManagementServiceImplTest {

	@InjectMocks
	DeviceTypeMangementServiceImpl deviceTypeMangementServiceImpl;

	@Mock
	HttpServletResponse httpResponse;

	@Mock
	CustomErrorResolution dataValidation;

	@Mock
	DeviceTypeRegistrationRequest request;

	@Mock
	DeviceTypeRegistrationResponse response;

	@Mock
	DeviceTypeManagementDao deviceTypeManagementDao;

	@Mock
	DeviceTypeRegistrationResponse result;

	@Mock
	DeviceTypeListViewRequest deviceTypeListViewRequest;

	@Mock
	DeviceTypeListViewResponse deviceTypeListViewResponse;

	@Mock
	DeviceTypeMaster deviceTypeMaster;

	@Mock
	DeviceTypeData deviceTypeData;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void deviceTypeRegistrationTest() throws IOException {
		request = new DeviceTypeRegistrationRequest();
		response = new DeviceTypeRegistrationResponse();
		request.setDeviceTypeName(TestConstants.DEVICE_TYPE_NAME);
		response.setStatusCode(TestConstants.STATUS_CODE);
		Mockito.when(deviceTypeManagementDao.validateDeviceTypeRegistrationRequest(request)).thenReturn(true);
		result = deviceTypeMangementServiceImpl.deviceTypeRegistration(request, response, httpResponse);
		Assert.assertNotNull(result);
		Assert.assertEquals(TestConstants.STATUS_CODE, result.getStatusCode());
		Assert.assertEquals(TestConstants.STATUS_SUCCESS, result.getStatusMessage());
	}

	@Test
	public void deviceTypeRegistrationTransactionIdTest() throws IOException {
		request = new DeviceTypeRegistrationRequest();
		response = new DeviceTypeRegistrationResponse();
		request.setUserId(TestConstants.USER_ID);
		request.setDeviceTypeName(TestConstants.DEVICE_TYPE_NAME);
		response.setStatusCode(TestConstants.STATUS_CODE);
		response.setStatusMessage(CustomErrorCodes.INVALID_TRANSACTION_ID.getCustomErrorMsg());
		Mockito.when(deviceTypeManagementDao.validateDeviceTypeRegistrationRequest(request)).thenReturn(false);
		response = deviceTypeMangementServiceImpl.deviceTypeRegistration(request, response, httpResponse);
		Assert.assertNotNull(result);
	}

	@Test
	public void deviceTypeRegistrationUserIdTest() throws IOException {
		request = new DeviceTypeRegistrationRequest();
		response = new DeviceTypeRegistrationResponse();
		request.setDeviceTypeName(TestConstants.DEVICE_TYPE_NAME);
		response.setStatusCode(TestConstants.STATUS_CODE);
		Mockito.when(deviceTypeManagementDao.validateDeviceTypeRegistrationRequest(request)).thenReturn(false);
		result = deviceTypeMangementServiceImpl.deviceTypeRegistration(request, response, httpResponse);
		Assert.assertNotNull(result);
	}

	@Test
	public void deviceTypeRegistrationPtoOperationIdTest() throws IOException {
		request = new DeviceTypeRegistrationRequest();
		response = new DeviceTypeRegistrationResponse();
		request.setDeviceTypeName(TestConstants.DEVICE_TYPE_NAME);
		response.setStatusCode(TestConstants.STATUS_CODE);
		Mockito.when(deviceTypeManagementDao.validateDeviceTypeRegistrationRequest(request)).thenReturn(false);
		Mockito.when(dataValidation.isValidUser(request.getUserId())).thenReturn(true);
		result = deviceTypeMangementServiceImpl.deviceTypeRegistration(request, response, httpResponse);
		Assert.assertNotNull(result);
	}

	@Test
	public void deviceTypeRegistrationdeviceTypeNameTest() throws IOException {
		request = new DeviceTypeRegistrationRequest();
		response = new DeviceTypeRegistrationResponse();
		request.setDeviceTypeName(TestConstants.DEVICE_TYPE_NAME);
		response.setStatusCode(TestConstants.STATUS_CODE);
		Mockito.when(deviceTypeManagementDao.validateDeviceTypeRegistrationRequest(request)).thenReturn(false);
		Mockito.when(dataValidation.isValidUser(request.getUserId())).thenReturn(true);
		Mockito.when(dataValidation.deviceTypeNameValidation(request.getDeviceTypeName())).thenReturn(true);
		result = deviceTypeMangementServiceImpl.deviceTypeRegistration(request, response, httpResponse);
		Assert.assertNotNull(result);
		Assert.assertEquals(TestConstants.DEVICE_NAME_ALREADY_EXIST, result.getStatusMessage());
	}

	@Test
	public void testDeviceTypeListView() throws IOException {
		deviceTypeListViewRequest = new DeviceTypeListViewRequest();
		deviceTypeListViewResponse = new DeviceTypeListViewResponse();
		deviceTypeListViewResponse.setStatusCode(TestConstants.STATUS_CODE);
		deviceTypeListViewResponse.setStatusMessage(TestConstants.STATUS_MESSAGE);
		List<DeviceTypeMaster> deviceTypeList = new ArrayList<>();
		deviceTypeMaster = new DeviceTypeMaster();
		deviceTypeList.add(deviceTypeMaster);
		List<DeviceTypeData> deviceViewList = new ArrayList<>();
		deviceTypeData = new DeviceTypeData();
		deviceTypeData.setDeviceTypeName(TestConstants.DEVICE_TYPE_NAME);
		deviceViewList.add(deviceTypeData);
		Mockito.when(deviceTypeManagementDao.validateDeviceTypeListView(deviceTypeListViewRequest))
				.thenReturn(true);
		Mockito.when(deviceTypeManagementDao.getDeviceTypeListView(Matchers.any())).thenReturn(deviceTypeList);
		deviceTypeMangementServiceImpl.deviceTypeListView(deviceTypeListViewRequest, deviceTypeListViewResponse,
				httpResponse);
	}

	@Test
	public void testDeviceTypeListViewFalse() throws IOException {
		deviceTypeListViewRequest = new DeviceTypeListViewRequest();
		deviceTypeListViewResponse = new DeviceTypeListViewResponse();
		Mockito.when(deviceTypeManagementDao.validateDeviceTypeListView(deviceTypeListViewRequest))
				.thenReturn(false);
		deviceTypeListViewResponse = deviceTypeMangementServiceImpl.deviceTypeListView(deviceTypeListViewRequest,
				deviceTypeListViewResponse, httpResponse);
		Assert.assertNotNull(deviceTypeListViewResponse);
	}

	@Test
	public void testDeviceTypeListViewPtoOperationIdByRelationValidation() throws IOException {
		deviceTypeListViewRequest = new DeviceTypeListViewRequest();
		deviceTypeListViewResponse = new DeviceTypeListViewResponse();
		Mockito.when(deviceTypeManagementDao.validateDeviceTypeListView(deviceTypeListViewRequest))
				.thenReturn(false);
		deviceTypeListViewResponse = deviceTypeMangementServiceImpl.deviceTypeListView(deviceTypeListViewRequest,
				deviceTypeListViewResponse, httpResponse);
		Assert.assertNotNull(deviceTypeListViewResponse);
	}

	@Test
	public void testDeviceTypeListViewPtoOperationIdByRelationFalse() throws IOException {
		deviceTypeListViewRequest = new DeviceTypeListViewRequest();
		deviceTypeListViewResponse = new DeviceTypeListViewResponse();
		deviceTypeListViewResponse.setStatusCode(TestConstants.STATUS_CODE);
		deviceTypeListViewResponse.setStatusMessage(TestConstants.STATUS_SUCCESS);
		Mockito.when(deviceTypeManagementDao.validateDeviceTypeListView(deviceTypeListViewRequest))
				.thenReturn(false);
		deviceTypeListViewResponse = deviceTypeMangementServiceImpl.deviceTypeListView(deviceTypeListViewRequest,
				deviceTypeListViewResponse, httpResponse);
		Assert.assertNotNull(deviceTypeListViewResponse);
		Assert.assertEquals(TestConstants.STATUS_CODE, deviceTypeListViewResponse.getStatusCode());
		Assert.assertEquals(TestConstants.STATUS_SUCCESS, deviceTypeListViewResponse.getStatusMessage());
	}
	
}
