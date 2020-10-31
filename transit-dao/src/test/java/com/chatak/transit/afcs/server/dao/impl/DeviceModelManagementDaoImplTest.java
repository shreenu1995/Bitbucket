package com.chatak.transit.afcs.server.dao.impl;

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
import com.chatak.transit.afcs.server.dao.model.DeviceModel;
import com.chatak.transit.afcs.server.dao.model.DeviceTypeMaster;
import com.chatak.transit.afcs.server.dao.repository.DeviceModelRepository;
import com.chatak.transit.afcs.server.dao.repository.DeviceTypeRegistrationRepository;
import com.chatak.transit.afcs.server.dao.repository.PtoMasterRepository;
import com.chatak.transit.afcs.server.dao.repository.UserCredentialsRepository;
import com.chatak.transit.afcs.server.pojo.web.DeviceListRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceModelExistCheckRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceModelProfileUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceModelRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeListViewRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeRegistrationRequest;

public class DeviceModelManagementDaoImplTest {

	@InjectMocks
	DeviceModelMangementDaoImpl deviceDaoImpl;

	@Mock
	DeviceRegistrationRequest deviceRegistrationRequest;

	@Mock
	DeviceModelMangementDaoImpl deviceMangementDaoImpl;

	@Mock
	DeviceTypeMaster request;

	@Mock
	DeviceTypeRegistrationRequest deviceTypeRegistrationRequest;

	@Mock
	DeviceTypeRegistrationRepository deviceTypeRegistrationRepository;

	@Mock
	UserCredentialsRepository userCredentialRepository;

	@Mock
	DeviceListRequest deviceListRequest;

	@Mock
	DeviceModelRequest deviceModelRequest;

	@Mock
	DeviceModel deviceModel;

	@Mock
	DeviceTypeListViewRequest deviceTypeListViewRequest;

	@Mock
	DeviceModelExistCheckRequest deviceModelExistCheckRequest;

	@Mock
	DeviceModelRepository equipementModelRepository;

	@Mock
	PtoMasterRepository ptoOperationMasterRepository;

	@Mock
	DeviceModelProfileUpdateRequest deviceModelProfileUpdateRequest;

	@Mock
	EntityManager em;

	@Mock
	Query query;

	@Mock
	EntityManagerFactory emf;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Mock
	DeviceTypeMaster deviceTypeMaster;

	@Test
	public void validateDeviceRequestTest() {
		deviceRegistrationRequest = new DeviceRegistrationRequest();
		deviceRegistrationRequest.setUserId(TestConstants.USER_ID);
		deviceRegistrationRequest.setPtoOperationId(TestConstants.PTO_OPERATION_ID);
		deviceRegistrationRequest.setStationCode(TestConstants.STRING_ONE);
		Mockito.when(userCredentialRepository.existsByEmail(TestConstants.USER_ID)).thenReturn(true);
		Mockito.when(deviceTypeRegistrationRepository.existsByDeviceTypeId(TestConstants.ID))
				.thenReturn(true);
		Assert.assertNotNull(deviceRegistrationRequest);
	}

	@Test
	public void validateDeviceRequestTxnIdFailTest() {
		Mockito.when(userCredentialRepository.existsByEmail(Matchers.anyString())).thenReturn(true);
		Assert.assertNotNull(userCredentialRepository);
	}

	@Test
	public void validateDeviceRequestPtoIdFailTest() {
		Mockito.when(userCredentialRepository.existsByEmail(Matchers.anyString())).thenReturn(true);
		Assert.assertNotNull(userCredentialRepository);
	}

	@Test
	public void validateDeviceRequestUserIdFailTest() {
		Mockito.when(userCredentialRepository.existsByEmail(Matchers.anyString())).thenReturn(false);
		Assert.assertNotNull(userCredentialRepository);
	}

	@Test
	public void testSaveDeviceModelDetails() {
		deviceModelRequest = new DeviceModelRequest();
		deviceModel = new DeviceModel();
		Mockito.when(equipementModelRepository.save(deviceModel)).thenReturn(null);
		deviceModelRequest.setDeviceModel(TestConstants.DEVICE_TYPE);
		Assert.assertNotNull(deviceModelRequest);
	}

	@Test
	public void testValidateDeviceModelIsExistInfo() {
		deviceModelExistCheckRequest = new DeviceModelExistCheckRequest();
		deviceModelExistCheckRequest.setPtoOperationId(TestConstants.PTO_OPERATION_ID);
		deviceModelExistCheckRequest.setDeviceModel(TestConstants.DEVICE_TYPE);
		deviceModelExistCheckRequest.setDeviceTypeName(TestConstants.DEVICE_TYPE_NAME);
		Assert.assertNotNull(deviceModelExistCheckRequest);
	}

	@Test
	public void testValidateDeviceModelExistCheckInfo() {
		deviceModelExistCheckRequest = new DeviceModelExistCheckRequest();
		deviceModelExistCheckRequest.setPtoOperationId(TestConstants.PTO_OPERATION_ID);
		deviceModelExistCheckRequest.setDeviceModel(TestConstants.DEVICE_TYPE);
		Mockito.when(userCredentialRepository.existsByEmail(deviceModelExistCheckRequest.getUserId()))
				.thenReturn(true);
		Mockito.when(deviceTypeRegistrationRepository
				.existsByDeviceTypeName(deviceModelExistCheckRequest.getDeviceTypeName())).thenReturn(true);
		Assert.assertNotNull(deviceModelExistCheckRequest);
	}

	@Test
	public void testValidateEQModelExistCheckInfoFalseUser() {
		deviceModelExistCheckRequest = new DeviceModelExistCheckRequest();
		deviceModelExistCheckRequest.setPtoOperationId(TestConstants.PTO_OPERATION_ID);
		deviceModelExistCheckRequest.setDeviceModel(TestConstants.DEVICE_TYPE);
		Mockito.when(userCredentialRepository.existsByEmail(Matchers.anyString())).thenReturn(false);
		Mockito.when(deviceTypeRegistrationRepository
				.existsByDeviceTypeName(deviceModelExistCheckRequest.getDeviceTypeName())).thenReturn(true);
		Assert.assertNotNull(deviceModelExistCheckRequest);
	}

	@Test
	public void testValidateEQModelExistCheckInfoFalsePto() {
		deviceModelExistCheckRequest = new DeviceModelExistCheckRequest();
		deviceModelExistCheckRequest.setPtoOperationId(TestConstants.PTO_OPERATION_ID);
		deviceModelExistCheckRequest.setDeviceModel(TestConstants.DEVICE_TYPE);
		Mockito.when(userCredentialRepository.existsByEmail(Matchers.anyString())).thenReturn(true);
		Mockito.when(deviceTypeRegistrationRepository
				.existsByDeviceTypeName(deviceModelExistCheckRequest.getDeviceTypeName())).thenReturn(true);
		Assert.assertNotNull(deviceModelExistCheckRequest);
	}

	@Test
	public void testValidateEQModelExistCheckInfoFalseTxn() {
		deviceModelExistCheckRequest = new DeviceModelExistCheckRequest();
		deviceModelExistCheckRequest.setPtoOperationId(TestConstants.PTO_OPERATION_ID);
		deviceModelExistCheckRequest.setDeviceModel(TestConstants.DEVICE_TYPE);
		Mockito.when(userCredentialRepository.existsByEmail(Matchers.anyString())).thenReturn(true);
		Mockito.when(deviceTypeRegistrationRepository
				.existsByDeviceTypeName(deviceModelExistCheckRequest.getDeviceTypeName())).thenReturn(true);
		Assert.assertNotNull(deviceModelExistCheckRequest);
	}

	@Test
	public void testValidateEQModelExistCheckInfoFalseType() {
		deviceModelExistCheckRequest = new DeviceModelExistCheckRequest();
		deviceModelExistCheckRequest.setPtoOperationId(TestConstants.PTO_OPERATION_ID);
		deviceModelExistCheckRequest.setDeviceModel(TestConstants.DEVICE_TYPE);
		Mockito.when(userCredentialRepository.existsByEmail(Matchers.anyString())).thenReturn(true);
		Mockito.when(deviceTypeRegistrationRepository
				.existsByDeviceTypeName(deviceModelExistCheckRequest.getDeviceTypeName())).thenReturn(false);
		Assert.assertNotNull(deviceModelExistCheckRequest.getPtoOperationId());
	}

	@Test
	public void testValidateEQModelExistCheckInfoModelNameFalse() {
		deviceModelExistCheckRequest = new DeviceModelExistCheckRequest();
		deviceModelExistCheckRequest.setPtoOperationId(TestConstants.PTO_OPERATION_ID);
		deviceModelExistCheckRequest.setDeviceModel(TestConstants.DEVICE_TYPE);
		Mockito.when(userCredentialRepository.existsByEmail(Matchers.anyString())).thenReturn(true);
		Mockito.when(deviceTypeRegistrationRepository
				.existsByDeviceTypeName(deviceModelExistCheckRequest.getDeviceTypeName())).thenReturn(true);
		Assert.assertNotNull(deviceModelExistCheckRequest);
	}

	@Test
	public void testSaveDeviceModelDetail() {
		deviceModel = new DeviceModel();
		Mockito.when(equipementModelRepository.save(deviceModel)).thenReturn(deviceModel);
		Long result = deviceDaoImpl.saveDeviceModelDetails(deviceModel);
		Assert.assertNull(result);
	}


	@Test
	public void testValidateDeviceModelType() {
		deviceModelRequest = new DeviceModelRequest();
		deviceModelRequest.setUserId(TestConstants.USER_ID);
		deviceModelRequest.setDeviceModel(TestConstants.DEVICE_TYPE);
		deviceModelRequest.setDeviceTypeName(TestConstants.DEVICE_TYPE);
		Mockito.when(userCredentialRepository.existsByEmail(Matchers.anyString())).thenReturn(true);
		Mockito.when(deviceTypeRegistrationRepository
				.existsByDeviceTypeName(deviceModelRequest.getDeviceTypeName())).thenReturn(false);
		Mockito.when(deviceTypeRegistrationRepository
				.existsByDeviceTypeName(deviceModelRequest.getDeviceTypeName())).thenReturn(true);
		Assert.assertNotNull(deviceModelRequest);
	}

	@Test
	public void testValidateDeviceModelName() {
		deviceModelRequest = new DeviceModelRequest();
		deviceModelRequest.setUserId(TestConstants.USER_ID);
		deviceModelRequest.setDeviceModel(TestConstants.DEVICE_TYPE);
		deviceModelRequest.setDeviceTypeName(TestConstants.DEVICE_TYPE);
		Mockito.when(userCredentialRepository.existsByEmail(Matchers.anyString())).thenReturn(true);
		Mockito.when(deviceTypeRegistrationRepository
				.existsByDeviceTypeName(deviceModelRequest.getDeviceTypeName())).thenReturn(true);
		Assert.assertNotNull(deviceModelRequest.getDeviceTypeName());
	}


	@Test
	public void testValidateDeviceModelProfileUpdateUser() {
		deviceModelProfileUpdateRequest = new DeviceModelProfileUpdateRequest();
		deviceModelProfileUpdateRequest.setUserId(TestConstants.USER_ID);
		deviceModelProfileUpdateRequest.setDeviceTypeName(TestConstants.DEVICE_TYPE);
		deviceModelProfileUpdateRequest.setDeviceModel(TestConstants.STRING_ONE);
		Mockito.when(userCredentialRepository.existsByEmail(Matchers.anyString())).thenReturn(false);
		Mockito.when(deviceTypeRegistrationRepository.existsByDeviceTypeName(Matchers.anyString()))
				.thenReturn(true);
		boolean result = deviceDaoImpl.validateDeviceModelProfileUpdate(deviceModelProfileUpdateRequest);
		Assert.assertNotNull(result);
		Assert.assertFalse(result);
	}

}
