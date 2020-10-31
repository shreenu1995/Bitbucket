package com.chatak.transit.afcs.server.dao.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.chatak.transit.afcs.constants.TestConstants;
import com.chatak.transit.afcs.server.dao.model.DeviceSetupManagement;
import com.chatak.transit.afcs.server.dao.repository.DeviceSetupManagementRepository;
import com.chatak.transit.afcs.server.dao.repository.OrganizationMasterRepository;
import com.chatak.transit.afcs.server.dao.repository.PtoMasterRepository;
import com.chatak.transit.afcs.server.dao.repository.UserCredentialsRepository;
import com.chatak.transit.afcs.server.pojo.web.TerminalsetupReportGenerationRequest;

@RunWith(MockitoJUnitRunner.class)
public class TerminalSetupReportGenerationDaoImplTest {

	@InjectMocks
	TerminalSetupReportGenerationDaoImpl terminalSetupReportGeneration;

	@Mock
	UserCredentialsRepository userCredentialsRepository;

	@Mock
	PtoMasterRepository ptoOperationMasterRepository;

	@Mock
	OrganizationMasterRepository ptoMasterRepository;

	@Mock
	DeviceSetupManagementRepository deviceSetupManagementRepository;

	@Mock
	TerminalsetupReportGenerationRequest terminalsetupRequest;

	@Mock
	List<DeviceSetupManagement> deviceSetupManagementList;

	@Mock
	DeviceSetupManagement deviceSetupManagement;

	Date date = new Date();
	Timestamp timestamp = new Timestamp(date.getTime());

	@Test
	public void getTerminalSetupReportTest() {
		List<DeviceSetupManagement> list = new ArrayList<>();
		setDeviceList(list);
		Timestamp time = Timestamp.valueOf("2007-09-23 10:10:10.0");
		terminalsetupRequest = new TerminalsetupReportGenerationRequest();
		terminalsetupRequest.setGenerationDateStart(time);
		terminalsetupRequest.setGenerationDateEnd(time);
		terminalsetupRequest.setEquimentOemId(TestConstants.DEVICE_ID);
		terminalsetupRequest.setEquimentModelNumber(TestConstants.DEVICE_TYPE);
		Assert.assertNotNull(terminalsetupRequest);
	}

	private void setDeviceList(List<DeviceSetupManagement> list) {
		DeviceSetupManagement management = new DeviceSetupManagement();
		management.setDeviceId(TestConstants.DEVICE_IDS);
		list.add(management);
	}

	@Test
	public void getTerminalSetupReportOemIdNullTest() {
		List<DeviceSetupManagement> list = new ArrayList<>();
		setDeviceList(list);
		Timestamp time = Timestamp.valueOf("2007-09-23 10:10:10.0");
		terminalsetupRequest = new TerminalsetupReportGenerationRequest();
		terminalsetupRequest.setGenerationDateEnd(time);
		terminalsetupRequest.setGenerationDateStart(time);
		Assert.assertNotNull(terminalsetupRequest);
	}

	@Test
	public void getTerminalSetupReportDateNullTest() {
		List<DeviceSetupManagement> list = new ArrayList<>();
		setDeviceList(list);
		terminalsetupRequest = new TerminalsetupReportGenerationRequest();
		Mockito.when(deviceSetupManagementRepository.findAll()).thenReturn(list);
		Assert.assertNotNull(terminalsetupRequest);
	}

	@Test
	public void validationTerminalSetUpReportTest() {
		terminalsetupRequest = new TerminalsetupReportGenerationRequest();
		terminalsetupRequest.setUserId(TestConstants.USER_ID);
		terminalsetupRequest.setPtoOperationId(TestConstants.PTO_OPERATION_ID);
		Mockito.when(userCredentialsRepository.existsByEmail(terminalsetupRequest.getUserId())).thenReturn(true);
		Assert.assertNotNull(terminalsetupRequest);
	}

	@Test
	public void validationTerminalSetUpReportUserIdTest() {
		terminalsetupRequest = new TerminalsetupReportGenerationRequest();
		terminalsetupRequest.setUserId(TestConstants.USER_ID);
		terminalsetupRequest.setPtoOperationId(TestConstants.PTO_OPERATION_ID);
		Mockito.when(userCredentialsRepository.existsByEmail(terminalsetupRequest.getUserId())).thenReturn(false);
		boolean response = terminalSetupReportGeneration.validationTerminalSetUpReport(terminalsetupRequest);
		Assert.assertFalse(response);
	}

	@Test
	public void validationTerminalSetUpReportPtoOperationIdTest() {
		terminalsetupRequest = new TerminalsetupReportGenerationRequest();
		terminalsetupRequest.setUserId(TestConstants.USER_ID);
		terminalsetupRequest.setPtoOperationId(TestConstants.PTO_OPERATION_ID);
		Mockito.when(userCredentialsRepository.existsByEmail(terminalsetupRequest.getUserId())).thenReturn(true);
		boolean response = terminalSetupReportGeneration.validationTerminalSetUpReport(terminalsetupRequest);
		Assert.assertFalse(response);
	}

	@Test
	public void testGetTerminalSetupReportPartStart() {
		terminalsetupRequest = new TerminalsetupReportGenerationRequest();
		terminalsetupRequest.setGenerationDateStart(timestamp);
		terminalsetupRequest.setGenerationDateEnd(null);
		Assert.assertNotNull(terminalsetupRequest);
	}

	@Test
	public void testGetTerminalSetupReportPartStartOem() {
		terminalsetupRequest = new TerminalsetupReportGenerationRequest();
		terminalsetupRequest.setGenerationDateStart(null);
		terminalsetupRequest.setGenerationDateEnd(timestamp);
		terminalsetupRequest.setEquimentOemId(TestConstants.STRING_ONE);
		terminalsetupRequest.setEquimentModelNumber(TestConstants.STRING_ONE);
		Assert.assertNotNull(terminalsetupRequest);
	}

	@Test
	public void testGetTerminalSetupReportPartEnd() {
		terminalsetupRequest = new TerminalsetupReportGenerationRequest();
		terminalsetupRequest.setGenerationDateStart(null);
		terminalsetupRequest.setGenerationDateEnd(timestamp);
		Assert.assertNotNull(terminalsetupRequest);
	}

}
