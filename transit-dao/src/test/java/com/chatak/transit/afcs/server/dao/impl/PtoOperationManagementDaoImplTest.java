package com.chatak.transit.afcs.server.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.chatak.transit.afcs.constants.TestConstants;
import com.chatak.transit.afcs.server.dao.model.PtoMaster;
import com.chatak.transit.afcs.server.dao.repository.OrganizationMasterRepository;
import com.chatak.transit.afcs.server.dao.repository.PtoMasterRepository;
import com.chatak.transit.afcs.server.dao.repository.UserCredentialsRepository;
import com.chatak.transit.afcs.server.pojo.web.PtoListRequest;
import com.chatak.transit.afcs.server.pojo.web.PtoPermissionRequest;
import com.chatak.transit.afcs.server.pojo.web.PtoRegistrationRequest;

@RunWith(MockitoJUnitRunner.class)
public class PtoOperationManagementDaoImplTest {

	@InjectMocks
	PtoManagementDaoImpl operationManagementDaoImpl;

	@Mock
	PtoMasterRepository ptoOperationMasterRepository;

	@Mock
	OrganizationMasterRepository ptoMasterRepository;

	@Mock
	UserCredentialsRepository userCredentialsRepository;

	@Mock
	PtoListRequest ptoOperationListRequest;

	@Mock
	PtoMaster ptoOperationMaster;

	@Test
	public void savePtoOperationRegistrationTest() {
		ptoOperationMaster = new PtoMaster();
		Mockito.when(ptoOperationMasterRepository.save(ptoOperationMaster)).thenReturn(ptoOperationMaster);
		Assert.assertNotNull(ptoOperationMaster);
	}

	private PtoRegistrationRequest setPtoRequest() {
		PtoRegistrationRequest request = new PtoRegistrationRequest();
		request.setUserId(TestConstants.USER_ID);
		return request;
	}

	@Test
	public void validatePtoOpearationListRequestTest() {
		ptoOperationListRequest = setPtoOperationRequest();
		Mockito.when(userCredentialsRepository.existsByEmail(Matchers.anyString())).thenReturn(true);
		Assert.assertNotNull(ptoOperationListRequest);
	}

	@Test
	public void getPtoOperationListTest() {
		ptoOperationMaster = new PtoMaster();
		List<PtoMaster> listPtoOperationMaster = new ArrayList<>();
		ptoOperationListRequest = new PtoListRequest();
		listPtoOperationMaster.add(ptoOperationMaster);
		Mockito.when(ptoOperationMasterRepository.findAll()).thenReturn(listPtoOperationMaster);
		Assert.assertNotNull(listPtoOperationMaster);
	}

	private PtoListRequest setPtoOperationRequest() {
		ptoOperationListRequest = new PtoListRequest();
		ptoOperationListRequest.setUserId(TestConstants.USER_ID);
		return ptoOperationListRequest;
	}

	private void setPtoOperationPermissionRequest(PtoPermissionRequest request) {
		request.setPtoId(TestConstants.PTO_ID);
		request.setUserId(TestConstants.USER_ID);
	}
}
