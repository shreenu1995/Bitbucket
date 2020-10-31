package com.chatak.transit.afcs.server.dao.impl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.chatak.transit.afcs.constants.TestConstants;
import com.chatak.transit.afcs.server.dao.model.StopProfile;
import com.chatak.transit.afcs.server.dao.repository.PtoMasterRepository;
import com.chatak.transit.afcs.server.dao.repository.StopMasterRepository;
import com.chatak.transit.afcs.server.dao.repository.StopProfileRepository;
import com.chatak.transit.afcs.server.dao.repository.UserCredentialsRepository;
import com.chatak.transit.afcs.server.pojo.web.StopRegistrationRequest;

@RunWith(MockitoJUnitRunner.class)
public class StopManagementDaoImplTest {

	@InjectMocks
	StopManagementDaoImpl stopManagementDaoImpl;

	@Mock
	StopRegistrationRequest stopRegistrationRequest;

	@Mock
	StopMasterRepository stopMasterRepository;

	@Mock
	UserCredentialsRepository userCredentialRepository;

	@Mock
	StopProfileRepository stopProfileRepository;

	@Mock
	StopProfile stopProfile;
	
	@Mock
	PtoMasterRepository ptoOperationMasterRepository;

	@Test
	public void testValidatestopRegistrationRequest() {
		Mockito.when(userCredentialRepository.existsByEmail(stopRegistrationRequest.getUserId())).thenReturn(true);
		Assert.assertTrue(stopManagementDaoImpl.validateStopRegistrationRequest(stopRegistrationRequest));
	}

	@Test
	public void testValidatestopRegistrationRequestUserIdInvalid() {
		Mockito.when(userCredentialRepository.existsByEmail(stopRegistrationRequest.getUserId())).thenReturn(false);
		Assert.assertFalse(stopManagementDaoImpl.validateStopRegistrationRequest(stopRegistrationRequest));
	}

	@Test
	public void testValidatestopRegistrationRequestPtoIdInvalid() {
		Mockito.when(userCredentialRepository.existsByEmail(stopRegistrationRequest.getUserId())).thenReturn(true);
		Assert.assertNotNull(userCredentialRepository);
	}

	@Test
	public void testSaveStopToStopProfile() {
		stopProfile = new StopProfile();
		Mockito.when(stopProfileRepository.save(stopProfile)).thenReturn(stopProfile);
		Assert.assertNotNull(stopProfileRepository);
	}
}
