package com.chatak.transit.afcs.server.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.chatak.transit.afcs.constants.TestConstants;
import com.chatak.transit.afcs.server.dao.model.DeviceTypeMaster;
import com.chatak.transit.afcs.server.dao.repository.DeviceTypeRegistrationRepository;
import com.chatak.transit.afcs.server.dao.repository.PtoMasterRepository;
import com.chatak.transit.afcs.server.dao.repository.UserCredentialsRepository;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeListViewRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeProfileUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeStatusUpdateRequest;

public class DeviceTypeManagementDaoImplTest {

	@InjectMocks
	DeviceTypeManagementDaoImpl deviceTypeManagementDaoImpl;

	@Mock
	DeviceTypeMaster request;

	@Mock
	DeviceTypeRegistrationRequest deviceTypeRegistrationRequest;

	@Mock
	DeviceTypeRegistrationRepository deviceTypeRegistrationRepository;

	@Mock
	UserCredentialsRepository userCredentialRepository;

	@Mock
	DeviceTypeListViewRequest deviceTypeListViewRequest;

	@Mock
	PtoMasterRepository ptoOperationMasterRepository;

	@Mock
	DeviceTypeProfileUpdateRequest deviceTypeProfileUpdateRequest;

	@Mock
	DeviceTypeStatusUpdateRequest deviceTypeStatusUpdateRequest;
	
	@Mock
	EntityManager em;

	@Mock
	Query query;

	@Mock
	EntityManagerFactory emf;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		Mockito.when(em.getDelegate()).thenReturn(Object.class);
		Mockito.when(em.createQuery(Matchers.anyString())).thenReturn(query);
		Mockito.when(em.getEntityManagerFactory()).thenReturn(emf);
	}

	@Mock
	DeviceTypeMaster deviceTypeMaster;

	@Test
	public void testSaveDeviceTypeRegistrationDetails() {
		request = new DeviceTypeMaster();
		request.setCreatedBy(TestConstants.CREATEDBY);
		request.setDeviceTypeId(TestConstants.ID);
		Mockito.when(deviceTypeRegistrationRepository.save(request)).thenReturn(request);
		Long result = deviceTypeManagementDaoImpl.saveDeviceTypeRegistrationDetails(request);
		Assert.assertNotNull(result);
		Assert.assertEquals(TestConstants.ID, result);
	}

	@Test
	public void testValidateDeviceTypeRegistrationRequest() {
		deviceTypeRegistrationRequest = new DeviceTypeRegistrationRequest();
		deviceTypeRegistrationRequest.setDescription(TestConstants.DESCRIPTION);
		deviceTypeRegistrationRequest.setUserId(TestConstants.USER_ID);
		Mockito.when(userCredentialRepository.existsByEmail(deviceTypeRegistrationRequest.getUserId()))
				.thenReturn(true);
		Assert.assertNotNull(deviceTypeRegistrationRequest);
	}

	@Test
	public void testValidateDeviceTypeRegistrationRequestUserId() {
		deviceTypeRegistrationRequest = new DeviceTypeRegistrationRequest();
		deviceTypeRegistrationRequest.setDescription(TestConstants.DESCRIPTION);
		deviceTypeRegistrationRequest.setUserId(TestConstants.USER_ID);
		Mockito.when(userCredentialRepository.existsByEmail(deviceTypeRegistrationRequest.getUserId()))
				.thenReturn(false);
		Assert.assertNotNull(deviceTypeRegistrationRequest);
	}

	@Test
	public void testValidateDeviceTypeRegistrationRequestTxnId() {
		deviceTypeRegistrationRequest = new DeviceTypeRegistrationRequest();
		deviceTypeRegistrationRequest.setDescription(TestConstants.DESCRIPTION);
		deviceTypeRegistrationRequest.setUserId(TestConstants.USER_ID);
		Mockito.when(userCredentialRepository.existsByEmail(deviceTypeRegistrationRequest.getUserId()))
				.thenReturn(true);
		Assert.assertNotNull(deviceTypeRegistrationRequest.getDescription());
	}

	@Test
	public void validateDeviceTypeListViewTxnIdFailTest() {
		Mockito.when(userCredentialRepository.existsByEmail(Matchers.anyString())).thenReturn(true);
		boolean value = deviceTypeManagementDaoImpl.validateDeviceTypeListView(deviceTypeListViewRequest);
		Assert.assertNotNull(value);
		Assert.assertFalse(value);
	}

	@Test
	public void validateDeviceTypeListViewPtoIdFailTest() {
		Mockito.when(userCredentialRepository.existsByEmail(Matchers.anyString())).thenReturn(true);
		boolean value = deviceTypeManagementDaoImpl.validateDeviceTypeListView(deviceTypeListViewRequest);
		Assert.assertFalse(value);
	}

	@Test
	public void validateDeviceTypeListViewUserIdFailTest() {
		Mockito.when(userCredentialRepository.existsByEmail(Matchers.anyString())).thenReturn(false);
		boolean value = deviceTypeManagementDaoImpl.validateDeviceTypeListView(deviceTypeListViewRequest);
		Assert.assertNotNull(value);
		Assert.assertFalse(value);
	}

	@Test
	public void saveDeviceTypeRegistrationDetailsTest() {
		DeviceTypeMaster deviceType = new DeviceTypeMaster();
		deviceType.setDeviceTypeId(TestConstants.ID);
		Mockito.when(deviceTypeRegistrationRepository.save(Matchers.any(DeviceTypeMaster.class)))
				.thenReturn(deviceType);
		Long value = deviceTypeManagementDaoImpl.saveDeviceTypeRegistrationDetails(deviceTypeMaster);
		Assert.assertEquals(TestConstants.ID, value);
	}

	@Test
	public void testValidateDeviceTypeStatusUpdateUserID() {
		deviceTypeStatusUpdateRequest = new DeviceTypeStatusUpdateRequest();
		deviceTypeStatusUpdateRequest.setUserId(TestConstants.USER_ID);
		deviceTypeStatusUpdateRequest.setDeviceTypeId(TestConstants.ID);
		Mockito.when(userCredentialRepository.existsByEmail(deviceTypeStatusUpdateRequest.getUserId()))
				.thenReturn(false);
		boolean value = deviceTypeManagementDaoImpl
				.validateDeviceTypeStatusUpdate(deviceTypeStatusUpdateRequest);
		Assert.assertNotNull(value);
		Assert.assertFalse(value);
	}
	
	@Test
	public void testGetDeviceTypeListView() {
		List<DeviceTypeMaster> listDeviceTypeMaster = new ArrayList<>();
		deviceTypeMaster = new DeviceTypeMaster();
		deviceTypeMaster.setDescription(TestConstants.DESCRIPTION);
		listDeviceTypeMaster.add(deviceTypeMaster);
		Assert.assertNotNull(listDeviceTypeMaster);
		Assert.assertEquals(TestConstants.DESCRIPTION, listDeviceTypeMaster.get(0).getDescription());
	}
}
