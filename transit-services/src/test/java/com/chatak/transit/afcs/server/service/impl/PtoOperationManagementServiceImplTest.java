package com.chatak.transit.afcs.server.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.chatak.transit.afcs.constants.TestConstants;
import com.chatak.transit.afcs.server.dao.CustomErrorResolution;
import com.chatak.transit.afcs.server.dao.PtoManagementDao;
import com.chatak.transit.afcs.server.dao.model.PtoMaster;
import com.chatak.transit.afcs.server.enums.CustomErrorCodes;
import com.chatak.transit.afcs.server.pojo.web.PtoList;
import com.chatak.transit.afcs.server.pojo.web.PtoListRequest;
import com.chatak.transit.afcs.server.pojo.web.PtoListResponse;
import com.chatak.transit.afcs.server.pojo.web.PtoRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.PtoRegistrationResponse;

@RunWith(MockitoJUnitRunner.Silent.class)
public class PtoOperationManagementServiceImplTest {

	@InjectMocks
	PtoManagementServiceImpl ptoOperationManagementServiceImpl;

	@Mock
	CustomErrorResolution dataValidation;

	@Mock
	EntityManager entityManager;

	@Mock
	PtoManagementDao ptoOperationManagementDao;

	@Mock
	PtoListRequest ptoOperationListRequest;

	@Mock
	PtoListResponse ptoOperationListResponse;

	@Mock
	HttpServletResponse httpResponse;

	@Mock
	PtoList ptoOperation;

	@Mock
	PtoMaster ptoOperationMaster;

	@Mock
	List<PtoList> ptoOperationList;

	@Mock
	List<PtoMaster> ptoOperationMasterList;

	@Mock
	PtoRegistrationRequest ptoOperationRegistrationRequest;

	@Mock
	PtoRegistrationResponse ptoOperationRegistrationResponse;

	private static final String SERVER_ERROR_STATUS_MESSAGE = "Internal Server Error";
	private static final String INVALID_TRANSACTION_ID = "Invalid Transaction ID";
	private static final String USER_NOT_EXIST = "User ID does not exist";
	private static final String SUCCESS = "Success";
	private static final String STRING_VALUE = "155";

	@Test
	public void testSavePtoOperationRegistrationPtoOperationIdNull() throws IOException {
		ptoOperationRegistrationRequestCommonScript();
		ptoOperationRegistrationResponse = new PtoRegistrationResponse();
		ptoOperationRegistrationResponse.setStatusCode(CustomErrorCodes.SERVER_ERROR.getCustomErrorCode());
		ptoOperationRegistrationResponse.setStatusMessage(CustomErrorCodes.SERVER_ERROR.getCustomErrorMsg());
		Mockito.when(ptoOperationManagementDao.validatePtoRegistrationRequest(ptoOperationRegistrationRequest))
				.thenReturn(true);
		Assert.assertEquals(SERVER_ERROR_STATUS_MESSAGE, ptoOperationRegistrationResponse.getStatusMessage());
	}

	@Test
	public void testSavePtoOperationRegistrationSuccess() throws IOException {
		ptoOperationRegistrationRequestCommonScript();
		ptoOperationRegistrationResponse = new PtoRegistrationResponse();
		ptoOperationRegistrationResponse.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
		ptoOperationRegistrationResponse.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		ptoOperationMaster = new PtoMaster();
		Mockito.when(ptoOperationManagementDao.validatePtoRegistrationRequest(ptoOperationRegistrationRequest))
				.thenReturn(true);
		Assert.assertEquals(SUCCESS, ptoOperationRegistrationResponse.getStatusMessage());
	}

	@Test
	public void testSavePtoOperationRegistrationTransactionIdNotExist() throws IOException {
		ptoOperationRegistrationRequestCommonScript();
		ptoOperationRegistrationResponse = new PtoRegistrationResponse();
		ptoOperationRegistrationResponse.setStatusCode(CustomErrorCodes.INVALID_TRANSACTION_ID.getCustomErrorCode());
		ptoOperationRegistrationResponse.setStatusMessage(CustomErrorCodes.INVALID_TRANSACTION_ID.getCustomErrorMsg());
		ptoOperationMaster = new PtoMaster();
		Mockito.when(ptoOperationManagementDao.validatePtoRegistrationRequest(ptoOperationRegistrationRequest))
				.thenReturn(false);
		Assert.assertEquals(INVALID_TRANSACTION_ID, ptoOperationRegistrationResponse.getStatusMessage());
	}

	@Test
	public void testSavePtoOperationRegistrationPtoNotExist() throws IOException {
		ptoOperationRegistrationRequestCommonScript();
		ptoOperationRegistrationResponse = new PtoRegistrationResponse();
		ptoOperationRegistrationResponse.setStatusCode(CustomErrorCodes.PTO_OPERATION_NOT_EXIST.getCustomErrorCode());
		ptoOperationRegistrationResponse.setStatusMessage(CustomErrorCodes.PTO_OPERATION_NOT_EXIST.getCustomErrorMsg());
		ptoOperationMaster = new PtoMaster();
		Mockito.when(ptoOperationManagementDao.validatePtoRegistrationRequest(ptoOperationRegistrationRequest))
				.thenReturn(false);
		Mockito.when(dataValidation.isValidUser(ptoOperationRegistrationRequest.getUserId())).thenReturn(true);
	}

	@Test
	public void testSavePtoOperationRegistrationUserNotExist() throws IOException {
		ptoOperationRegistrationRequestCommonScript();
		ptoOperationRegistrationResponse = new PtoRegistrationResponse();
		ptoOperationRegistrationResponse.setStatusCode(CustomErrorCodes.USER_NOT_EXIST.getCustomErrorCode());
		ptoOperationRegistrationResponse.setStatusMessage(CustomErrorCodes.USER_NOT_EXIST.getCustomErrorMsg());
		ptoOperationMaster = new PtoMaster();
		Mockito.when(ptoOperationManagementDao.validatePtoRegistrationRequest(ptoOperationRegistrationRequest))
				.thenReturn(false);
	}

	private void ptoOperationRegistrationRequestCommonScript() {
		ptoOperationRegistrationRequest = new PtoRegistrationRequest();
		ptoOperationRegistrationRequest.setUserId(TestConstants.STRING_ONE);
	}

	@Test
	public void testGetPtoOperationListSuccess() throws IOException {
		ptoOperation = new PtoList();
		ptoOperationMaster = new PtoMaster();
		ptoOperationList = new ArrayList<>();
		ptoOperationList.add(ptoOperation);
		ptoOperationListRequest = new PtoListRequest();
		ptoOperationListRequest.setUserId(TestConstants.STRING_ONE);
		ptoOperationListResponse = new PtoListResponse();
		ptoOperationListResponse.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
		ptoOperationListResponse.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		ptoOperationMasterList = new ArrayList<>();
		ptoOperationMasterList.add(ptoOperationMaster);
		Mockito.when(ptoOperationManagementDao.getPtoList(ptoOperationListRequest))
				.thenReturn(ptoOperationMasterList);
		Assert.assertNotNull(ptoOperationListResponse);
		Assert.assertEquals(CustomErrorCodes.SUCCESS.getCustomErrorMsg(), ptoOperationListResponse.getStatusMessage());
		Assert.assertEquals(CustomErrorCodes.SUCCESS.getCustomErrorCode(), ptoOperationListResponse.getStatusCode());
	}

	@Test
	public void testGetPtoOperationListTransactionIdInvalid() throws IOException {
		ptoOperationListResponse = new PtoListResponse();
		ptoOperationListResponse.setStatusCode(CustomErrorCodes.INVALID_TRANSACTION_ID.getCustomErrorCode());
		ptoOperationListResponse.setStatusMessage(CustomErrorCodes.INVALID_TRANSACTION_ID.getCustomErrorMsg());
		Assert.assertNotNull(ptoOperationListResponse);
	}

	@Test
	public void testGetPtoOperationListPTOIdInvalid() throws IOException {
		ptoOperationListRequest = new PtoListRequest();
		Mockito.when(dataValidation.isValidUser(ptoOperationListRequest.getUserId())).thenReturn(true);
		ptoOperationListResponse = new PtoListResponse();
		ptoOperationListResponse.setStatusCode(CustomErrorCodes.PTO_OPERATION_NOT_EXIST.getCustomErrorCode());
		ptoOperationListResponse.setStatusMessage(CustomErrorCodes.PTO_OPERATION_NOT_EXIST.getCustomErrorMsg());
		Assert.assertEquals(CustomErrorCodes.PTO_OPERATION_NOT_EXIST.getCustomErrorMsg(),
				ptoOperationListResponse.getStatusMessage());
		Assert.assertEquals(CustomErrorCodes.PTO_OPERATION_NOT_EXIST.getCustomErrorCode(),
				ptoOperationListResponse.getStatusCode());
	}

	@Test
	public void testSavePtoOperationRegistrationTXnIdInvalid() throws IOException {
		ptoOperationRegistrationResponse = new PtoRegistrationResponse();
		ptoOperationRegistrationResponse.setStatusCode(CustomErrorCodes.INVALID_TRANSACTION_ID.getCustomErrorCode());
		ptoOperationRegistrationResponse.setStatusMessage(CustomErrorCodes.INVALID_TRANSACTION_ID.getCustomErrorMsg());
		Assert.assertEquals(CustomErrorCodes.INVALID_TRANSACTION_ID.getCustomErrorMsg(),
				ptoOperationRegistrationResponse.getStatusMessage());
		Assert.assertEquals(CustomErrorCodes.INVALID_TRANSACTION_ID.getCustomErrorCode(),
				ptoOperationRegistrationResponse.getStatusCode());
	}

	@Test
	public void testSavePtoOperationRegistrationPTOIdInvalid() throws IOException {
		ptoOperationRegistrationRequest = new PtoRegistrationRequest();
		Mockito.when(dataValidation.isValidUser(ptoOperationListRequest.getUserId())).thenReturn(true);
		ptoOperationRegistrationResponse = new PtoRegistrationResponse();
		ptoOperationRegistrationResponse.setStatusCode(CustomErrorCodes.ORGANIZATION_NOT_EXIST.getCustomErrorCode());
		ptoOperationRegistrationResponse.setStatusMessage(CustomErrorCodes.ORGANIZATION_NOT_EXIST.getCustomErrorMsg());
		Assert.assertEquals(CustomErrorCodes.ORGANIZATION_NOT_EXIST.getCustomErrorMsg(),
				ptoOperationRegistrationResponse.getStatusMessage());
		Assert.assertEquals(CustomErrorCodes.ORGANIZATION_NOT_EXIST.getCustomErrorCode(),
				ptoOperationRegistrationResponse.getStatusCode());
	}

}
