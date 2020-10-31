package com.chatak.transit.afcs.server.dao.impl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.chatak.transit.afcs.server.dao.repository.OrganizationMasterRepository;
import com.chatak.transit.afcs.server.dao.repository.PtoMasterRepository;
import com.chatak.transit.afcs.server.dao.repository.UserCredentialsRepository;
import com.chatak.transit.afcs.server.pojo.web.RoleRegistrationRequest;

@RunWith(MockitoJUnitRunner.class)
public class RoleManagementDaoImplTest {

	@Mock
	RoleRegistrationRequest roleRegistrationRequest;

	@Mock
	UserCredentialsRepository userCredentialRepository;

	@Mock
	PtoMasterRepository ptoOperationMasterRepository;
	
	@Mock
	OrganizationMasterRepository ptoMasterRepository;;
	
	@Test
	public void testValidateRoleRegistration() {
		Mockito.when(userCredentialRepository.existsByEmail(roleRegistrationRequest.getUserId())).thenReturn(true);
		Assert.assertNotNull(userCredentialRepository);
	}

	@Test
	public void testValidateRoleRegistrationUserIdInvalid() {
		Mockito.when(userCredentialRepository.existsByEmail(roleRegistrationRequest.getUserId())).thenReturn(false);
		Assert.assertNotNull(userCredentialRepository);
	}

	@Test
	public void testValidateRoleRegistrationTransactionIdInvalid() {
		Mockito.when(userCredentialRepository.existsByEmail(roleRegistrationRequest.getUserId())).thenReturn(true);
		Assert.assertNotNull(ptoOperationMasterRepository);
	}

	@Test
	public void testValidateRoleRegistrationPtoIdInvalid() {
		Mockito.when(userCredentialRepository.existsByEmail(roleRegistrationRequest.getUserId())).thenReturn(true);
		Assert.assertNotNull(userCredentialRepository);
	}

}
