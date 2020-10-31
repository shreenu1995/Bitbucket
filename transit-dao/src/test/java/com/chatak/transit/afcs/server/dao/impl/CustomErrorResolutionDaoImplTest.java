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
import com.chatak.transit.afcs.server.dao.model.DeviceTypeMaster;

@RunWith(MockitoJUnitRunner.class)
public class CustomErrorResolutionDaoImplTest {

	@InjectMocks
	CustomErrorResolutionImpl customErrorResolutionImpl;

	@Mock
	Query query;

	@Mock
	private EntityManagerFactory emf;

	@Mock
	private EntityManager em;

	@Mock
	DeviceTypeMaster deviceTypeMaster;

	@Before
	public void setup() {
		Mockito.when(em.getDelegate()).thenReturn(Object.class);
		Mockito.when(em.createQuery(Matchers.anyString())).thenReturn(query);
		Mockito.when(em.getEntityManagerFactory()).thenReturn(emf);

	}

	@Test
	public void testDeviceTypeNameValidationFalse() {
		boolean result = customErrorResolutionImpl.deviceTypeNameValidation(TestConstants.DEVICE_TYPE_NAME);
		Assert.assertNotNull(result);
		Assert.assertFalse(result);
	}

	@Test
	public void testEmployeeIDValidation() {
		List<Object[]> tupleAgentList = new ArrayList<>();
		Object[] objects = new Object[1];
		objects[0] = new String(TestConstants.DEVICE_TYPE_NAME);
		tupleAgentList.add(objects);
		Mockito.when(query.getResultList()).thenReturn(tupleAgentList);
		Assert.assertNotNull(tupleAgentList);
	}

	@Test
	public void testIsValidUser() {
		List<Object[]> tupleAgentList = new ArrayList<>();
		Object[] objects = new Object[1];
		objects[0] = new String(TestConstants.USER_ID);
		tupleAgentList.add(objects);
		Mockito.when(query.getResultList()).thenReturn(tupleAgentList);
		boolean result = customErrorResolutionImpl.isValidUser(TestConstants.USER_ID);
		Assert.assertNotNull(result);
	}

	@Test
	public void testIsValidUserFalse() {
		boolean result = customErrorResolutionImpl.isValidUser(TestConstants.USER_ID);
		Assert.assertNotNull(result);
		Assert.assertFalse(result);
	}

	@Test
	public void testIsValidAdminUserFalse() {
		boolean result = customErrorResolutionImpl.isValidAdminUser(TestConstants.ADMIN_USER_ID);
		Assert.assertNotNull(result);
		Assert.assertFalse(result);
	}

	@Test
	public void testTicketNoValidationFalse() {
		boolean result = customErrorResolutionImpl.ticketNoValidation(TestConstants.TICKET_NO);
		Assert.assertNotNull(result);
		Assert.assertFalse(result);
	}

	@Test
	public void testPaymentModeValidation() {
		List<Object[]> tupleAgentList = new ArrayList<>();
		Object[] objects = new Object[1];
		objects[0] = new String(TestConstants.SHIFT_CODE);
		tupleAgentList.add(objects);
		Mockito.when(query.getResultList()).thenReturn(tupleAgentList);
		Assert.assertNotNull(tupleAgentList);
	}

	@Test
	public void testIsValidDeviceModelNumberFalse() {
		boolean result = customErrorResolutionImpl.isValidDeviceModelNumber(TestConstants.MODEl_NUMBER);
		Assert.assertNotNull(result);
		Assert.assertFalse(result);
	}

	@Test
	public void testPasswordValidationFalse() {
		boolean result = customErrorResolutionImpl.passwordValidation(TestConstants.BASEPATH);
		Assert.assertNotNull(result);
		Assert.assertFalse(result);
	}

	@Test
	public void testDeviceModelName() {
		List<Object[]> tupleAgentList = new ArrayList<>();
		Object[] objects = new Object[1];
		objects[0] = new String(TestConstants.TICKET_NO);
		tupleAgentList.add(objects);
		Mockito.when(query.getResultList()).thenReturn(tupleAgentList);
		Assert.assertNotNull(tupleAgentList);
	}

	@Test
	public void testDeviceTypeFalse() {
		boolean result = customErrorResolutionImpl.deviceType(TestConstants.ID);
		Assert.assertNotNull(result);
		Assert.assertFalse(result);
	}

	@Test
	public void testRouteNameValidationFalse() {
		boolean result = customErrorResolutionImpl.routeNameValidation(TestConstants.ROUTE_NAME);
		Assert.assertNotNull(result);
		Assert.assertFalse(result);
	}

	@Test
	public void testValidatePtoIdFalse() {
		boolean result = customErrorResolutionImpl.validateOrganizationId(TestConstants.ID);
		Assert.assertNotNull(result);
		Assert.assertFalse(result);
	}

	@Test
	public void testValidateStationCode() {
		List<Object[]> tupleAgentList = new ArrayList<>();
		Object[] objects = new Object[1];
		tupleAgentList.add(objects);
		Mockito.when(query.getResultList()).thenReturn(tupleAgentList);
		Assert.assertNotNull(tupleAgentList);
	}

	@Test
	public void testDeviceTypeIdValidation() {
		List<Object[]> tupleAgentList = new ArrayList<>();
		Object[] objects = new Object[1];
		objects[0] = new String(TestConstants.ADMIN_USER_ID);
		tupleAgentList.add(objects);
		Mockito.when(query.getResultList()).thenReturn(tupleAgentList);
		Assert.assertNotNull(tupleAgentList);
	}

	@Test
	public void testDeviceModelId() {
		List<Object[]> tupleAgentList = new ArrayList<>();
		Object[] objects = new Object[1];
		objects[0] = new String(TestConstants.USER_ID);
		tupleAgentList.add(objects);
		Mockito.when(query.getResultList()).thenReturn(tupleAgentList);
		Assert.assertNotNull(tupleAgentList);
	}

}
