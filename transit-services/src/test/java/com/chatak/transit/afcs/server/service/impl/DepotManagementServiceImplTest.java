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
import org.springframework.validation.BindingResult;

import com.chatak.transit.afcs.constants.TestConstants;
import com.chatak.transit.afcs.server.dao.CustomErrorResolution;
import com.chatak.transit.afcs.server.dao.DepotManagementDao;
import com.chatak.transit.afcs.server.dao.model.DepotMaster;
import com.chatak.transit.afcs.server.enums.CustomErrorCodes;
import com.chatak.transit.afcs.server.pojo.web.DepotData;
import com.chatak.transit.afcs.server.pojo.web.DepotListViewRequest;
import com.chatak.transit.afcs.server.pojo.web.DepotListViewResponse;
import com.chatak.transit.afcs.server.pojo.web.DepotRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.DepotRegistrationResponse;

public class DepotManagementServiceImplTest {

	@InjectMocks
	private DepotManagementServiceImpl depotManagementServiceImpl;

	@Mock
	DepotListViewRequest request;

	@Mock
	DepotListViewResponse response;

	@Mock
	HttpServletResponse httpServletResponse;

	@Mock
	BindingResult bindingResult;

	@Mock
	CustomErrorResolution dataValidation;

	@Mock
	DepotManagementDao depotManagementDao;
	
	@Mock
	DepotRegistrationRequest depotRegistrationRequest;
	
	@Mock
	DepotRegistrationResponse depotRegistrationResponse;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getDepotListViewRequestTest() throws IOException {
		request = new DepotListViewRequest();
		response = new DepotListViewResponse();
		request.setUserId(TestConstants.USER_ID);
		response.setStatusCode(TestConstants.STATUS_CODE);
		List<DepotMaster> depotList = new ArrayList<>();
		DepotMaster depotMaster = new DepotMaster();
		depotList.add(depotMaster);
		List<DepotData> depotListView = new ArrayList();
		DepotData depotData = new DepotData();
		depotListView.add(depotData);
		Mockito.when(depotManagementDao.getDepotListView(Matchers.any())).thenReturn(depotList);
		Mockito.when(depotManagementDao.validateGetDepotListView(request)).thenReturn(true);
		response = depotManagementServiceImpl.getDepotListViewRequest(request, response, httpServletResponse,
				bindingResult);
		Assert.assertNotNull(response);
		Assert.assertEquals(TestConstants.STATUS_CODE, response.getStatusCode());
		Assert.assertEquals(TestConstants.STATUS_SUCCESS, response.getStatusMessage());
	}

	@Test
	public void getDepotListViewRequestFalseTest() throws IOException {
		request = new DepotListViewRequest();
		response = new DepotListViewResponse();
		request.setUserId(TestConstants.USER_ID);
		Mockito.when(depotManagementDao.validateGetDepotListView(request)).thenReturn(false);
		Mockito.when(dataValidation.isValidUser(request.getUserId())).thenReturn(false);
		response = depotManagementServiceImpl.getDepotListViewRequest(request, response, httpServletResponse,
				bindingResult);
		Assert.assertNotNull(response);
		Assert.assertEquals(CustomErrorCodes.USER_NOT_EXIST.getCustomErrorCode(), response.getStatusCode());
		Assert.assertEquals(CustomErrorCodes.USER_NOT_EXIST.getCustomErrorMsg(), response.getStatusMessage());
	}

	@Test
	public void testSaveDepotRegistrationrequest() throws IOException {
		depotRegistrationRequest = new DepotRegistrationRequest();
		depotRegistrationRequest.setUserId(TestConstants.STRING_ONE);
		depotRegistrationResponse = depotManagementServiceImpl.saveDepotRegistrationrequest(depotRegistrationRequest,
				httpServletResponse, depotRegistrationResponse);
		Assert.assertNotNull(depotRegistrationResponse);
	}

	@Test
	public void testSaveDepotRegistrationrequestZero() throws IOException {
		depotRegistrationRequest = new DepotRegistrationRequest();
		depotRegistrationRequest.setUserId(TestConstants.STRING_ONE);
		depotRegistrationResponse = depotManagementServiceImpl.saveDepotRegistrationrequest(depotRegistrationRequest,
				httpServletResponse, depotRegistrationResponse);
		Assert.assertNotNull(depotRegistrationResponse);
	}

	@Test
	public void testSaveDepotRegistrationrequestUser() throws IOException {
		depotRegistrationRequest = new DepotRegistrationRequest();
		depotRegistrationResponse = new DepotRegistrationResponse();
		Mockito.when(dataValidation.isValidUser(depotRegistrationRequest.getUserId())).thenReturn(false);
		depotRegistrationResponse = depotManagementServiceImpl.saveDepotRegistrationrequest(depotRegistrationRequest,
				httpServletResponse, depotRegistrationResponse);
		Assert.assertNotNull(depotRegistrationResponse);
	}

	@Test
	public void testSaveDepotRegistrationrequestPto() throws IOException {
		depotRegistrationRequest = new DepotRegistrationRequest();
		depotRegistrationResponse = new DepotRegistrationResponse();
		Mockito.when(dataValidation.isValidUser(depotRegistrationRequest.getUserId())).thenReturn(true);
		depotRegistrationResponse = depotManagementServiceImpl.saveDepotRegistrationrequest(depotRegistrationRequest,
				httpServletResponse, depotRegistrationResponse);
		Assert.assertNotNull(depotRegistrationResponse);
	}
}
