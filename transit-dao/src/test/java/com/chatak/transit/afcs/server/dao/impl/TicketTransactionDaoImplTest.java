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
import com.chatak.transit.afcs.server.dao.model.TicketsTxnData;
import com.chatak.transit.afcs.server.dao.repository.TicketTxnDataRepository;
import com.chatak.transit.afcs.server.pojo.TicketTransactionDataRequest;

@RunWith(MockitoJUnitRunner.class)
public class TicketTransactionDaoImplTest {

	@InjectMocks
	TicketTransactionDaoImpl ticketTransactionDaoImpl;

	@Mock
	TicketTxnDataRepository ticketTxnDataRepository;

	@Mock
	TicketsTxnData ticketTxnData;

	@Mock
	TicketTransactionDataRequest ticketTransactionDataRequest;

	@Mock
	Query query;
	
	@Mock
	private EntityManagerFactory emf;

	@Mock
	private EntityManager em;

	private static final String STRING_ONE = "1";
	private static final String STRING_TWO = "2";
	private static final String STRING_TEN = "10";

	@Before
	public void setup() {
		Mockito.when(em.getDelegate()).thenReturn(Object.class);
		Mockito.when(em.createQuery(Matchers.anyString())).thenReturn(query);
		Mockito.when(em.getEntityManagerFactory()).thenReturn(emf);
	}

	@Test
	public void testIsValidTicketNumber() {
		ticketTransactionDataRequest = new TicketTransactionDataRequest();
		ticketTransactionDataRequest.setTicketNumber(STRING_TWO);
		List<Object> list = new ArrayList<>();
		Object[] obj = new Object[Integer.parseInt(STRING_ONE)];
		obj[0] = TestConstants.INT_ONE;
		list.add(obj);
		Assert.assertTrue(ticketTransactionDaoImpl.isValidTicketNumber(ticketTransactionDataRequest));
	}

	@Test
	public void testIsTicketRequestValid() {
		ticketTransactionDataRequest = new TicketTransactionDataRequest();
		ticketTransactionDataRequest.setTicketNumber(STRING_TEN);
		ticketTransactionDataRequest.setTransactionId(STRING_TEN);
		ticketTransactionDataRequest.setPtoOperationId(STRING_TEN);
		ticketTransactionDataRequest.setTicketPaymentMode(TestConstants.DEVICE_TYPE);
		ticketTransactionDataRequest.setDeviceId(STRING_TEN);
		ticketTransactionDataRequest.setConductorEmployeeId(STRING_TEN);
		ticketTransactionDataRequest.setShiftCode(STRING_TEN);
		List<Object> list = new ArrayList<>();
		Object[] obj = new Object[Integer.parseInt(STRING_ONE)];
		obj[0] = TestConstants.INT_ONE;
		list.add(obj);
		Assert.assertFalse(ticketTransactionDaoImpl.isTicketRequestValid(ticketTransactionDataRequest));
	}

}
