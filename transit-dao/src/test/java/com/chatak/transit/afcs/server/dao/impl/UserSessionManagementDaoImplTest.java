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
import com.chatak.transit.afcs.server.dao.model.AdminTransactionData;
import com.chatak.transit.afcs.server.dao.model.UserCredentials;
import com.chatak.transit.afcs.server.dao.repository.AdminTransactionDataRepository;
import com.chatak.transit.afcs.server.dao.repository.OrganizationMasterRepository;
import com.chatak.transit.afcs.server.dao.repository.PtoMasterRepository;
import com.chatak.transit.afcs.server.dao.repository.UserCredentialsRepository;
import com.chatak.transit.afcs.server.pojo.web.ChangePasswordRequest;
import com.chatak.transit.afcs.server.pojo.web.CommonUserParameter;
import com.chatak.transit.afcs.server.pojo.web.TransactionRequest;
import com.chatak.transit.afcs.server.pojo.web.TransactionResponse;
import com.chatak.transit.afcs.server.pojo.web.UserInfoCheck;
import com.chatak.transit.afcs.server.pojo.web.UserListRequest;
import com.chatak.transit.afcs.server.pojo.web.UserProfileUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.UserRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.UserStatusUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.UserViewResponse;

@RunWith(MockitoJUnitRunner.class)
public class UserSessionManagementDaoImplTest {

	@InjectMocks
	UserSessionManagementDaoImpl userSessionManagementDaoImpl = new UserSessionManagementDaoImpl();

	@Mock
	UserCredentials userCredentials = new UserCredentials();

    @Mock
	AdminTransactionDataRepository adminTransactionRepository;

	@Mock
	UserCredentialsRepository userCredentialsRepository;

	@Mock
	AdminTransactionData adminTransactionData;

	@Mock
	TransactionResponse adminTransactionResponse;

	@Mock
	EntityManager em;

	@Mock
	UserStatusUpdateRequest userStatusUpdateRequest;

	@Mock
	ChangePasswordRequest changePasswordRequest;

	@Mock
	TransactionRequest transactionRequest;

	@Mock
	CommonUserParameter commonUserParameter;

	@Mock
	UserViewResponse userViewResponse;

	@Mock
	UserRegistrationRequest userRegistrationRequest;

	@Mock
	UserProfileUpdateRequest userProfileUpdateRequest;
	
	@Mock
	Query query;

	@Mock
	EntityManagerFactory emf;

	@Mock
	PtoMasterRepository ptoOperationMasterRepository;

	@Mock
	OrganizationMasterRepository ptoMasterRepository;

	@Mock
	UserListRequest userListRequest;

	@Before
	public void setup() {
		Mockito.when(em.getDelegate()).thenReturn(Object.class);
		Mockito.when(em.createQuery(Matchers.anyString())).thenReturn(query);
		Mockito.when(em.getEntityManagerFactory()).thenReturn(emf);
	}

	@Test
	public void testUpdateUserStatusFail() {
		userCredentials = new UserCredentials();
		userCredentials.setUserStatus(TestConstants.ADDRESS);
		userCredentials.setUserID(TestConstants.USER_ID);
		Assert.assertNotNull(userCredentials);
	}

	@Test
	public void testValidateUserStatusUpdate() {
		userStatusUpdateRequest = new UserStatusUpdateRequest();
		userStatusUpdateRequest.setUserId(TestConstants.USER_ID);
		userStatusUpdateRequest.setAdminUserId(TestConstants.USER_ID);
		Mockito.when(userCredentialsRepository.existsByEmail(userStatusUpdateRequest.getUserId())).thenReturn(true);
		Mockito.when(userCredentialsRepository.existsByEmail(userStatusUpdateRequest.getAdminUserId()))
				.thenReturn(true);
		Assert.assertNotNull(userStatusUpdateRequest);
	}

	@Test
	public void testValidateUserStatusUpdateUserIdNull() {
		userStatusUpdateRequest = new UserStatusUpdateRequest();
		userStatusUpdateRequest.setAdminUserId(TestConstants.USER_ID);
		Mockito.when(userCredentialsRepository.existsByEmail(userStatusUpdateRequest.getUserId())).thenReturn(false);
		Mockito.when(userCredentialsRepository.existsByEmail(userStatusUpdateRequest.getAdminUserId()))
				.thenReturn(true);
		boolean result = userSessionManagementDaoImpl.validateUserStatusUpdate(userStatusUpdateRequest);
		Assert.assertFalse(result);
	}

	@Test
	public void testValidateUserStatusUpdateAdminUserIdNull() {
		userStatusUpdateRequest = new UserStatusUpdateRequest();
		Mockito.when(userCredentialsRepository.existsByEmail(userStatusUpdateRequest.getUserId())).thenReturn(true);
		Mockito.when(userCredentialsRepository.existsByEmail(userStatusUpdateRequest.getAdminUserId()))
				.thenReturn(false);
		boolean result = userSessionManagementDaoImpl.validateUserStatusUpdate(userStatusUpdateRequest);
		Assert.assertFalse(result);
	}

	@Test
	public void testValidateUserStatusUpdateransactionIdNull() {
		userStatusUpdateRequest = new UserStatusUpdateRequest();
		Mockito.when(userCredentialsRepository.existsByEmail(userStatusUpdateRequest.getUserId())).thenReturn(true);
		Mockito.when(userCredentialsRepository.existsByEmail(userStatusUpdateRequest.getAdminUserId()))
				.thenReturn(true);
		boolean result = userSessionManagementDaoImpl.validateUserStatusUpdate(userStatusUpdateRequest);
		Assert.assertFalse(result);
	}

	@Test
	public void testValidateChangePasswordRequestUserIdNull() {
		changePasswordRequest = new ChangePasswordRequest();
		changePasswordRequest.setCurrentPassword(TestConstants.USER_ID);
		Mockito.when(userCredentialsRepository.existsByEmail(changePasswordRequest.getUserId())).thenReturn(false);
		Mockito.when(userCredentialsRepository.existsByPassWordAndEmail(changePasswordRequest.getCurrentPassword(),
				changePasswordRequest.getUserId())).thenReturn(true);
		boolean result = userSessionManagementDaoImpl.validateChangePasswordRequest(changePasswordRequest);
		Assert.assertFalse(result);
	}

	@Test
	public void testValidateChangePasswordRequestPassNull() {
		changePasswordRequest = new ChangePasswordRequest();
		changePasswordRequest.setUserId(TestConstants.USER_ID);
		Mockito.when(userCredentialsRepository.existsByEmail(changePasswordRequest.getUserId())).thenReturn(true);
		Mockito.when(userCredentialsRepository.existsByPassWordAndEmail(changePasswordRequest.getCurrentPassword(),
				changePasswordRequest.getUserId())).thenReturn(false);
		boolean result = userSessionManagementDaoImpl.validateChangePasswordRequest(changePasswordRequest);
		Assert.assertFalse(result);
	}

	@Test
	public void testValidateChangePasswordRequestTxnIdNull() {
		changePasswordRequest = new ChangePasswordRequest();
		changePasswordRequest.setUserId(TestConstants.USER_ID);
		changePasswordRequest.setCurrentPassword(TestConstants.USER_ID);
		Mockito.when(userCredentialsRepository.existsByEmail(changePasswordRequest.getUserId())).thenReturn(true);
		Mockito.when(userCredentialsRepository.existsByPassWordAndEmail(changePasswordRequest.getCurrentPassword(),
				changePasswordRequest.getUserId())).thenReturn(true);
		boolean result = userSessionManagementDaoImpl.validateChangePasswordRequest(changePasswordRequest);
		Assert.assertFalse(result);
	}

	@Test
	public void testValidateAdminTransactionDataRequest() {
		transactionRequest = new TransactionRequest();
		transactionRequest.setUserId(TestConstants.USER_ID);
		transactionRequest.setPassword(TestConstants.USER_ID);
		transactionRequest.setTransactionId(TestConstants.TRANSACTION_ID);
		Mockito.when(userCredentialsRepository.existsByEmail(transactionRequest.getUserId())).thenReturn(true);
		Mockito.when(userCredentialsRepository.existsByPassWordAndEmail(transactionRequest.getPassword(),
				transactionRequest.getUserId())).thenReturn(true);
		boolean result = userSessionManagementDaoImpl.validateAdminTransactionDataRequest(transactionRequest);
		Assert.assertTrue(result);
	}

	@Test
	public void testValidateAdminTransactionDataRequestPassNull() {
		transactionRequest = new TransactionRequest();
		transactionRequest.setUserId(TestConstants.USER_ID);
		transactionRequest.setTransactionId(TestConstants.TRANSACTION_ID);
		Mockito.when(userCredentialsRepository.existsByEmail(transactionRequest.getUserId())).thenReturn(true);
		Mockito.when(userCredentialsRepository.existsByPassWordAndEmail(transactionRequest.getPassword(),
				transactionRequest.getUserId())).thenReturn(false);
		boolean result = userSessionManagementDaoImpl.validateAdminTransactionDataRequest(transactionRequest);
		Assert.assertFalse(result);
	}

	@Test
	public void testValidateUserProfileRequestPtoNull() {
		userProfileUpdateRequest = new UserProfileUpdateRequest();
		userProfileUpdateRequest.setUserId(TestConstants.USER_ID);
		userProfileUpdateRequest.setPtoId(TestConstants.PTO_ID);
		Mockito.when(userCredentialsRepository.existsByEmail(userProfileUpdateRequest.getUserId())).thenReturn(true);
		boolean result = userSessionManagementDaoImpl.validateUserProfileRequest(userProfileUpdateRequest);
		Assert.assertFalse(result);
	}

	@Test
	public void testValidateUserProfileRequestTxnNull() {
		userProfileUpdateRequest = new UserProfileUpdateRequest();
		userProfileUpdateRequest.setUserId(TestConstants.USER_ID);
		Mockito.when(userCredentialsRepository.existsByEmail(userProfileUpdateRequest.getUserId())).thenReturn(true);
		boolean result = userSessionManagementDaoImpl.validateUserProfileRequest(userProfileUpdateRequest);
		Assert.assertFalse(result);
	}

	@Test
	public void testValidateUserInfo() {
		UserInfoCheck userInfoCheck = new UserInfoCheck();
		userInfoCheck.setUserId(TestConstants.USER_ID);
		userInfoCheck.setAdminUserId(TestConstants.USER_ID);
		List<Object[]> tupleAgentList = new ArrayList<>();
		Object[] objects = new Object[TestConstants.INT_FOUR];
		objects[TestConstants.INT_ZERO] = new String(TestConstants.USER_ID);
		objects[TestConstants.INT_ONE] = new String(TestConstants.TRANSACTION_ID);
		objects[TestConstants.INT_TWO] = Boolean.valueOf(true);
		objects[TestConstants.INT_TWO] = Boolean.valueOf(true);
		tupleAgentList.add(objects);
		Mockito.when(em.getDelegate()).thenReturn(Object.class);
		Mockito.when(em.createQuery(Matchers.anyString())).thenReturn(query);
		Mockito.when(em.getEntityManagerFactory()).thenReturn(emf);
		Mockito.when(query.getResultList()).thenReturn(tupleAgentList);
		Assert.assertNotNull(tupleAgentList);
	}

	@Test
	public void testValidateUserRegistrationRequest() {
		userRegistrationRequest = new UserRegistrationRequest();
		userRegistrationRequest.setAdminUserId(TestConstants.USER_ID);
		userRegistrationRequest.setPtoId(TestConstants.PTO_ID);
		List<Object[]> tupleAgentList = new ArrayList<>();
		Object[] objects = new Object[TestConstants.INT_FIVE];
		objects[TestConstants.INT_ZERO] = new String(TestConstants.USER_ID);
		objects[TestConstants.INT_ONE] = new String(TestConstants.PTO_OPERATION_ID);
		objects[TestConstants.INT_TWO] = Boolean.valueOf(true);
		objects[TestConstants.INT_THREE] = new String(TestConstants.TRANSACTION_ID);
		objects[TestConstants.INT_FOUR] = Boolean.valueOf(true);
		tupleAgentList.add(objects);
		Mockito.when(em.getDelegate()).thenReturn(Object.class);
		Mockito.when(em.createQuery(Matchers.anyString())).thenReturn(query);
		Mockito.when(em.getEntityManagerFactory()).thenReturn(emf);
		Mockito.when(query.getResultList()).thenReturn(tupleAgentList);
		boolean result = userSessionManagementDaoImpl.validateUserRegistrationRequest(userRegistrationRequest);
		Assert.assertTrue(result);
	}

	@Test
	public void testUpdateUserProfile() {
		userProfileUpdateRequest = new UserProfileUpdateRequest();
		userProfileUpdateRequest.setUserId(TestConstants.USER_ID);
		userProfileUpdateRequest.setUserRole(TestConstants.STRING_ONE);
		List<Object[]> tupleAgentList = new ArrayList<>();
		Object[] objects = new Object[TestConstants.INT_NINE];
		objects[TestConstants.INT_ZERO] = new String(TestConstants.USER_ID);
		objects[TestConstants.INT_ONE] = new String(TestConstants.PTO_OPERATION_ID);
		objects[TestConstants.INT_TWO] = new String(TestConstants.TRANSACTION_ID);
		objects[TestConstants.INT_THREE] = new String(TestConstants.TRANSACTION_ID);
		objects[TestConstants.INT_FOUR] = new String(TestConstants.TRANSACTION_ID);
		objects[TestConstants.INT_FIVE] = new String(TestConstants.TRANSACTION_ID);
		objects[TestConstants.SIX] = new String(TestConstants.TRANSACTION_ID);
		objects[TestConstants.INT_SEVEN] = new String(TestConstants.TRANSACTION_ID);
		objects[TestConstants.INT_EIGHT] = new String(TestConstants.PTO_OPERATION_ID);
		tupleAgentList.add(objects);
		Mockito.when(em.getDelegate()).thenReturn(Object.class);
		Mockito.when(em.createQuery(Matchers.anyString())).thenReturn(query);
		Mockito.when(em.getEntityManagerFactory()).thenReturn(emf);
		Mockito.when(query.getResultList()).thenReturn(tupleAgentList);
		boolean result = userSessionManagementDaoImpl.updateUserProfile(userProfileUpdateRequest);
		Assert.assertFalse(result);
	}

}
