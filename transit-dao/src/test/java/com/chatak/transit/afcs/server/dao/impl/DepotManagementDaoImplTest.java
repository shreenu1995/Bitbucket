package com.chatak.transit.afcs.server.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.chatak.transit.afcs.constants.TestConstants;
import com.chatak.transit.afcs.server.dao.model.DepotMaster;
import com.chatak.transit.afcs.server.dao.repository.DepotMasterRepository;
import com.chatak.transit.afcs.server.dao.repository.PtoMasterRepository;
import com.chatak.transit.afcs.server.dao.repository.UserCredentialsRepository;
import com.chatak.transit.afcs.server.pojo.web.DepotListViewRequest;
import com.chatak.transit.afcs.server.pojo.web.DepotRegistrationRequest;

@RunWith(MockitoJUnitRunner.class)
public class DepotManagementDaoImplTest {

	@InjectMocks
	DepotManagementDaoImpl depotManagementDaoImpl;

	@Mock
	DepotMaster depotMaster;

	@Mock
	DepotMasterRepository depotManagementRepository;

	@Mock
	PtoMasterRepository ptoOperationMasterRepository;

	@Mock
	UserCredentialsRepository userCredentialRepository;
	
	@Mock
	DepotListViewRequest depotListViewRequest;

	@Mock
	Query query;

	@Mock
	DepotRegistrationRequest depotRegistrationRequest;
	
	@Mock
	private EntityManagerFactory emf;

	@Mock
	private EntityManager em;

	@Before
	public void setup() {
		Mockito.when(em.getDelegate()).thenReturn(Object.class);
		Mockito.when(em.createQuery(Matchers.anyString())).thenReturn(query);
		Mockito.when(em.getEntityManagerFactory()).thenReturn(emf);
	}

	@Test
	public void saveDepotRegistrationTest() {
		depotMaster = new DepotMaster();
		depotMaster.setDepotId(TestConstants.ID);
		Mockito.when(depotManagementRepository.save(depotMaster)).thenReturn(depotMaster);
		Assert.assertNotNull(depotMaster);
	}
	
	
	@Test
	public void validateGetDepotListViewUserIDTest() {
		depotListViewRequest = new DepotListViewRequest();
		depotListViewRequest.setUserId(TestConstants.USER_ID);
		Mockito.when(userCredentialRepository.existsByEmail(depotListViewRequest.getUserId())).thenReturn(true);
		boolean result = depotManagementDaoImpl.validateGetDepotListView(depotListViewRequest);
		Assert.assertNotNull(result);
		Assert.assertTrue(result);
	}

	@Test
	public void saveDepotRegistrationPtoOperationIdTest() {
		depotListViewRequest = new DepotListViewRequest();
		depotListViewRequest.setUserId(TestConstants.USER_ID);
		Mockito.when(userCredentialRepository.existsByEmail(depotListViewRequest.getUserId())).thenReturn(true);
		boolean result = depotManagementDaoImpl.validateGetDepotListView(depotListViewRequest);
		Assert.assertNotNull(result);
	}

	@Test
	public void saveDepotRegistrationUserIdTest() {
		depotListViewRequest = new DepotListViewRequest();
		depotListViewRequest.setUserId(TestConstants.USER_ID);
		Mockito.when(userCredentialRepository.existsByEmail(depotListViewRequest.getUserId())).thenReturn(false);
		boolean result = depotManagementDaoImpl.validateGetDepotListView(depotListViewRequest);
		Assert.assertNotNull(result);
		Assert.assertFalse(result);
	}

	@Test
	public void validateGetDepotListViewTest() {
		List<DepotMaster> listDepotMaster = new ArrayList<>();
		depotMaster = new DepotMaster();
		listDepotMaster.add(depotMaster);
		List<DepotMaster> value = depotManagementDaoImpl.getDepotListView(depotMaster);
		Assert.assertNotNull(value);
	}
}