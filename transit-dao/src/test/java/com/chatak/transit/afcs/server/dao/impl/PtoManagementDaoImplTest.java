package com.chatak.transit.afcs.server.dao.impl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.chatak.transit.afcs.constants.TestConstants;
import com.chatak.transit.afcs.server.dao.model.OrganizationMaster;
import com.chatak.transit.afcs.server.dao.repository.OrganizationMasterRepository;
import com.chatak.transit.afcs.server.dao.repository.UserCredentialsRepository;
import com.chatak.transit.afcs.server.pojo.web.OrganizationRegistrationRequest;

@RunWith(MockitoJUnitRunner.class)
public class PtoManagementDaoImplTest {

	@InjectMocks
	OrganizationManagementDaoImpl ptoManagementDaoImpl;
	
	@Mock
	OrganizationMasterRepository ptoMasterRepository;
	
	@Mock
	UserCredentialsRepository userCredentialsRepository;
	
	@Test
	public void savePtoRegistrationTest() {
		OrganizationMaster ptoMaster = new OrganizationMaster();
		Mockito.when(ptoMasterRepository.save(ptoMaster)).thenReturn(ptoMaster);
		String id = TestConstants.PTO_ID.substring(TestConstants.INT_ZERO, TestConstants.INT_TWO)+TestConstants.INT_ONE;
		Assert.assertNotNull(id);
	}
	
	@Test
	public void validatePtoRegistrationTest() {
		OrganizationRegistrationRequest request = new OrganizationRegistrationRequest();
		request.setUserId(TestConstants.USER_ID);
		Mockito.when(userCredentialsRepository.existsByEmail(Matchers.anyString())).thenReturn(true);
		boolean ptoResult = ptoManagementDaoImpl.validateOrganizationRegistration(request);
		Assert.assertEquals(true, ptoResult);
	}
	
	@Test
	public void validatePtoRegistrationUserIdTest() {
		OrganizationRegistrationRequest request = new OrganizationRegistrationRequest();
		request.setUserId(TestConstants.USER_ID);
		Mockito.when(userCredentialsRepository.existsByEmail(Matchers.anyString())).thenReturn(false);
		boolean ptoResult = ptoManagementDaoImpl.validateOrganizationRegistration(request);
		Assert.assertEquals(false, ptoResult);
	}
	
}
