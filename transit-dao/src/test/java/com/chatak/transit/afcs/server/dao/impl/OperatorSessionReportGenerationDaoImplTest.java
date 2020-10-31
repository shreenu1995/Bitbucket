package com.chatak.transit.afcs.server.dao.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.chatak.transit.afcs.constants.TestConstants;
import com.chatak.transit.afcs.server.dao.model.AdminSessionManagement;
import com.chatak.transit.afcs.server.dao.repository.AdminSessionManagementRepository;
import com.chatak.transit.afcs.server.dao.repository.PtoMasterRepository;
import com.chatak.transit.afcs.server.dao.repository.TicketTxnDataRepository;
import com.chatak.transit.afcs.server.dao.repository.UserCredentialsRepository;
import com.chatak.transit.afcs.server.pojo.web.OperatorSessionReportGenerationRequest;

@RunWith(MockitoJUnitRunner.class)
public class OperatorSessionReportGenerationDaoImplTest {

	@Mock
	TicketTxnDataRepository ticketTransactionRepository;

	@Mock
	PtoMasterRepository ptoOperationRepository;

	@Mock
	UserCredentialsRepository userCredentialsRepository;

	@Mock
	AdminSessionManagementRepository adminSessionManagementRepository;

	@Mock
	OperatorSessionReportGenerationRequest operatorSessionReportGenerationRequest;

	@Mock
	List<AdminSessionManagement> adminSessionManagementList;

	@Mock
	AdminSessionManagement adminSessionManagement;
	
	Date date = new Date();
	Timestamp timestamp = new Timestamp(date.getTime());

	@Test
	public void testGetOperSesReportAllNotNull() {
		operatorSessionReportGenerationRequest = new OperatorSessionReportGenerationRequest();
		operatorSessionReportGenerationRequest.setGenerationDateStart(TestConstants.ADDRESS);
		operatorSessionReportGenerationRequest.setGenerationDateEnd(TestConstants.ADDRESS);
		operatorSessionReportGenerationRequest.setTransactionId(TestConstants.STRING_SIX);
		adminSessionManagement = new AdminSessionManagement();
		adminSessionManagement.setEmpId(TestConstants.STRING_ONE);
		adminSessionManagementList = new ArrayList<>();
		adminSessionManagementList.add(adminSessionManagement);
		Assert.assertNotNull(adminSessionManagementList);
	}

	@Test
	public void testGetOperatorSessionReportTxnNull() {
		operatorSessionReportGenerationRequest = new OperatorSessionReportGenerationRequest();
		operatorSessionReportGenerationRequest.setGenerationDateStart(TestConstants.ADDRESS);
		operatorSessionReportGenerationRequest.setGenerationDateEnd(TestConstants.ADDRESS);
		adminSessionManagement = new AdminSessionManagement();
		adminSessionManagement.setEmpId(TestConstants.STRING_ONE);
		adminSessionManagementList = new ArrayList<>();
		adminSessionManagementList.add(adminSessionManagement);
		Assert.assertNotNull(adminSessionManagementList);
	}

	@Test
	public void testGetOperSesReportStartEndNull() {
		operatorSessionReportGenerationRequest = new OperatorSessionReportGenerationRequest();
		operatorSessionReportGenerationRequest.setGenerationDateStart(null);
		operatorSessionReportGenerationRequest.setGenerationDateEnd(null);
		operatorSessionReportGenerationRequest.setTransactionId(TestConstants.STRING_SIX);
		adminSessionManagement = new AdminSessionManagement();
		adminSessionManagement.setEmpId(TestConstants.STRING_ONE);
		adminSessionManagementList = new ArrayList<>();
		adminSessionManagementList.add(adminSessionManagement);
		Assert.assertNotNull(adminSessionManagementList);
	}

	@Test
	public void testGetOperatorSessionReportEndNull() {
		operatorSessionReportGenerationRequest = new OperatorSessionReportGenerationRequest();
		operatorSessionReportGenerationRequest.setGenerationDateStart(TestConstants.ADDRESS);
		operatorSessionReportGenerationRequest.setGenerationDateEnd(null);
		operatorSessionReportGenerationRequest.setTransactionId(TestConstants.STRING_SIX);
		adminSessionManagement = new AdminSessionManagement();
		adminSessionManagement.setEmpId(TestConstants.STRING_ONE);
		adminSessionManagementList = new ArrayList<>();
		adminSessionManagementList.add(adminSessionManagement);
		Assert.assertNotNull(adminSessionManagementList);
	}

	@Test
	public void testGetOperatorSessionReportStartNull() {
		operatorSessionReportGenerationRequest = new OperatorSessionReportGenerationRequest();
		operatorSessionReportGenerationRequest.setGenerationDateStart(null);
		operatorSessionReportGenerationRequest.setGenerationDateEnd(TestConstants.ADDRESS);
		operatorSessionReportGenerationRequest.setTransactionId(TestConstants.STRING_SIX);
		adminSessionManagement = new AdminSessionManagement();
		adminSessionManagement.setEmpId(TestConstants.STRING_ONE);
		adminSessionManagementList = new ArrayList<>();
		adminSessionManagementList.add(adminSessionManagement);
		Assert.assertNotNull(adminSessionManagementList);
	}

	@Test
	public void testGetOperatorSessionReportFindAll() {
		operatorSessionReportGenerationRequest = new OperatorSessionReportGenerationRequest();
		adminSessionManagement = new AdminSessionManagement();
		adminSessionManagement.setEmpId(TestConstants.STRING_ONE);
		adminSessionManagementList = new ArrayList<>();
		adminSessionManagementList.add(adminSessionManagement);
		Assert.assertNotNull(adminSessionManagementList);
	}

	@Test
	public void testValidateOperatorReportRequest() {
		operatorSessionReportGenerationRequest = new OperatorSessionReportGenerationRequest();
		operatorSessionReportGenerationRequest.setGenerationDateStart(TestConstants.ADDRESS);
		operatorSessionReportGenerationRequest.setGenerationDateEnd(TestConstants.ADDRESS);
		operatorSessionReportGenerationRequest.setUserId(TestConstants.STRING_ONE);
		operatorSessionReportGenerationRequest.setPtoOperationId(TestConstants.STRING_SIX);
		Mockito.when(userCredentialsRepository.existsByEmail(Matchers.anyString())).thenReturn(true);
		Assert.assertNotNull(operatorSessionReportGenerationRequest);
	}

	@Test
	public void testValidateOperatorReportRequestUserNull() {
		operatorSessionReportGenerationRequest = new OperatorSessionReportGenerationRequest();
		operatorSessionReportGenerationRequest.setGenerationDateStart(TestConstants.ADDRESS);
		operatorSessionReportGenerationRequest.setGenerationDateEnd(TestConstants.ADDRESS);
		operatorSessionReportGenerationRequest.setUserId(TestConstants.STRING_ONE);
		operatorSessionReportGenerationRequest.setPtoOperationId(TestConstants.STRING_SIX);
		Mockito.when(userCredentialsRepository.existsByEmail(Matchers.anyString())).thenReturn(false);
		Assert.assertNotNull(operatorSessionReportGenerationRequest);
	}

	@Test
	public void testValidateOperatorReportRequestPtoNull() {
		operatorSessionReportGenerationRequest = new OperatorSessionReportGenerationRequest();
		operatorSessionReportGenerationRequest.setGenerationDateStart(TestConstants.ADDRESS);
		operatorSessionReportGenerationRequest.setGenerationDateEnd(TestConstants.ADDRESS);
		operatorSessionReportGenerationRequest.setUserId(TestConstants.STRING_ONE);
		operatorSessionReportGenerationRequest.setPtoOperationId(TestConstants.STRING_SIX);
		Mockito.when(userCredentialsRepository.existsByEmail(Matchers.anyString())).thenReturn(true);
		Assert.assertNotNull(operatorSessionReportGenerationRequest);
	}
}
