package com.chatak.transit.afcs.server.service.impl;

import java.io.IOException;
import java.text.ParseException;

import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.core.env.Environment;

import com.chatak.transit.afcs.constants.TestConstants;
import com.chatak.transit.afcs.server.dao.CustomErrorResolution;
import com.chatak.transit.afcs.server.dao.UserSessionManagementDao;
import com.chatak.transit.afcs.server.dao.model.AdminTransactionData;
import com.chatak.transit.afcs.server.dao.model.UserCredentials;
import com.chatak.transit.afcs.server.enums.CustomErrorCodes;
import com.chatak.transit.afcs.server.pojo.web.ChangePasswordRequest;
import com.chatak.transit.afcs.server.pojo.web.CommonUserParameter;
import com.chatak.transit.afcs.server.pojo.web.ForgetPasswordRequest;
import com.chatak.transit.afcs.server.pojo.web.ResetPasswordRequest;
import com.chatak.transit.afcs.server.pojo.web.TransactionRequest;
import com.chatak.transit.afcs.server.pojo.web.TransactionResponse;
import com.chatak.transit.afcs.server.pojo.web.UserDetail;
import com.chatak.transit.afcs.server.pojo.web.UserInfoCheck;
import com.chatak.transit.afcs.server.pojo.web.UserListRequest;
import com.chatak.transit.afcs.server.pojo.web.UserProfileUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.UserRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.UserStatusUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.UserViewResponse;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.service.EmailService;
import com.chatak.transit.afcs.server.util.StringUtil;

public class UserSessionManagementServiceImplTest {

	@InjectMocks
	UserSessionManagementServiceImpl userSessionManagementServiceImpl;

	@Mock
	CommonUserParameter commonUserParameter;

	@Mock
	HttpServletResponse httpResponse;

	@Mock
	UserSessionManagementDao userSessionManagementDao;

	@Mock
	CustomErrorResolution dataValidation;

	@Mock
	UserRegistrationRequest userRegistrationRequest;

	@Mock
	UserStatusUpdateRequest userStatusUpdateRequest;

	@Mock
	ChangePasswordRequest changePasswordRequest;

	@Mock
	TransactionRequest transactionRequest;

	@Mock
	ForgetPasswordRequest forgetPasswordRequest;

	@Mock
	Environment properties;

	@Mock
	EmailService emailService;

	@Mock
	UserDetail userDetail;

	@Mock
	UserListRequest userListRequest;

	@Mock
	WebResponse webResponse;

	@Mock
	UserInfoCheck userInfoCheck;

	@Mock
	ResetPasswordRequest resetPasswordRequest;

	@Mock
	UserCredentials userCredentials;

	@Mock
	UserProfileUpdateRequest userProfileUpdateRequest;
	
	private static final String SERVER_ERROR = "Internal Server Error";
	private static final String WRONG_PSWD_MSG = "Password does not exist";
	private static final String PSWD= "Pass@/@word";

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetUserDetail() {
		userDetail = new UserDetail();
		userDetail.setUserId(TestConstants.USER_ID);
		UserViewResponse userViewResponse = new UserViewResponse();
		UserCredentials userCredentials = new UserCredentials();
		userCredentials.setUserID("1");
		UserCredentials userCredentialsDetail = new UserCredentials();
		Mockito.when(userSessionManagementDao.validateUserDetail(userDetail, userViewResponse)).thenReturn(true);
		Mockito.when(userSessionManagementDao.getUserStatus(Matchers.any())).thenReturn(userCredentialsDetail);
		userViewResponse = userSessionManagementServiceImpl.getUserDetail(userDetail, userViewResponse, httpResponse);
	}

	@Test
	public void testGetUserDetailUserCredFalse() {
		userDetail = new UserDetail();
		userDetail.setUserId(TestConstants.USER_ID);
		UserViewResponse userViewResponse = new UserViewResponse();
		UserCredentials userCredentials = new UserCredentials();
		userCredentials.setUserID("1");
		UserCredentials userCredentialsDetail = new UserCredentials();
		Mockito.when(userSessionManagementDao.validateUserDetail(userDetail, userViewResponse)).thenReturn(true);
		Mockito.when(userSessionManagementDao.getUserStatus(userCredentials)).thenReturn(userCredentialsDetail);
		userViewResponse = userSessionManagementServiceImpl.getUserDetail(userDetail, userViewResponse, httpResponse);
		Assert.assertEquals(CustomErrorCodes.SERVER_ERROR.getCustomErrorCode(), userViewResponse.getStatusCode());
		Assert.assertEquals(SERVER_ERROR, userViewResponse.getStatusMessage());
	}

	@Test
	public void testGetUserDetailValidateUser() {
		userDetail = new UserDetail();
		userDetail.setUserId(TestConstants.USER_ID);
		UserViewResponse userViewResponse = new UserViewResponse();
		Mockito.when(userSessionManagementDao.validateUserDetail(userDetail, userViewResponse)).thenReturn(false);
		userViewResponse = userSessionManagementServiceImpl.getUserDetail(userDetail, userViewResponse, httpResponse);
		Assert.assertEquals(CustomErrorCodes.USER_NOT_EXIST.getCustomErrorCode(), userViewResponse.getStatusCode());
		Assert.assertEquals(TestConstants.USER_NOT_EXIST, userViewResponse.getStatusMessage());
	}

	@Test
	public void testSaveAdminTransactionData() throws IOException {
		TransactionResponse transactionResponse = new TransactionResponse();
		TransactionResponse adminTxnresponse = new TransactionResponse();
		adminTxnresponse.setRollId("1");
		transactionRequest = new TransactionRequest();
		transactionRequest.setUserId(TestConstants.USER_ID);
		transactionRequest.setDateTime(TestConstants.DATE_TIME);
		Mockito.when(userSessionManagementDao.validateAdminTransactionDataRequest(transactionRequest)).thenReturn(true);
		AdminTransactionData adminTransactionData = new AdminTransactionData();
		adminTransactionData.setUserId(TestConstants.USER_ID);
		Mockito.when(userSessionManagementDao.saveAdminTransactionData(Matchers.any(), Matchers.any()))
				.thenReturn(adminTxnresponse);
	}


	@Test
	public void testSaveAdminTransactionDataElsePass() throws IOException {
		TransactionResponse transactionResponse = new TransactionResponse();
		TransactionResponse adminTxnresponse = new TransactionResponse();
		adminTxnresponse.setRollId("1");
		transactionRequest = new TransactionRequest();
		transactionRequest.setUserId(TestConstants.USER_ID);
		Mockito.when(userSessionManagementDao.validateAdminTransactionDataRequest(transactionRequest))
				.thenReturn(false);
		Mockito.when(dataValidation.isValidUser(transactionRequest.getUserId())).thenReturn(true);
	}

	@Test
	public void testSaveAdminTransactionDataElse() throws IOException {
		TransactionResponse transactionResponse = new TransactionResponse();
		TransactionResponse adminTxnresponse = new TransactionResponse();
		adminTxnresponse.setRollId("1");
		transactionRequest = new TransactionRequest();
		transactionRequest.setUserId(TestConstants.USER_ID);
		Mockito.when(userSessionManagementDao.validateAdminTransactionDataRequest(transactionRequest))
				.thenReturn(false);
		Mockito.when(dataValidation.isValidUser(transactionRequest.getUserId())).thenReturn(true);
		Mockito.when(dataValidation.passwordValidation(transactionRequest.getPassword())).thenReturn(true);
	}

	@Test
	public void testUpdateUserProfile() throws IOException {
		userProfileUpdateRequest = new UserProfileUpdateRequest();
		webResponse = new WebResponse();
		userProfileUpdateRequest.setPtoId(TestConstants.PTO_ID);
		userProfileUpdateRequest.setAdminUserId(TestConstants.USER_ID);
		Mockito.when(userSessionManagementDao.validateUserProfileRequest(userProfileUpdateRequest)).thenReturn(true);
		Mockito.when(userSessionManagementDao.updateUserProfile(userProfileUpdateRequest)).thenReturn(true);
		webResponse = userSessionManagementServiceImpl.updateUserProfile(userProfileUpdateRequest, httpResponse,
				webResponse);
		Assert.assertEquals(TestConstants.STATUS_CODE, webResponse.getStatusCode());
		Assert.assertEquals(TestConstants.STATUS_SUCCESS, webResponse.getStatusMessage());
	}

	@Test
	public void testUpdateUserProfileElse() throws IOException {
		userProfileUpdateRequest = new UserProfileUpdateRequest();
		webResponse = new WebResponse();
		userProfileUpdateRequest.setPtoId(TestConstants.PTO_OPERATION_ID);
		userProfileUpdateRequest.setAdminUserId(TestConstants.USER_ID);
		Mockito.when(userSessionManagementDao.validateUserProfileRequest(userProfileUpdateRequest)).thenReturn(true);
		Mockito.when(userSessionManagementDao.updateUserProfile(userProfileUpdateRequest)).thenReturn(false);
		webResponse = userSessionManagementServiceImpl.updateUserProfile(userProfileUpdateRequest, httpResponse,
				webResponse);
		Assert.assertEquals(CustomErrorCodes.SERVER_ERROR.getCustomErrorCode(), webResponse.getStatusCode());
		Assert.assertEquals(SERVER_ERROR, webResponse.getStatusMessage());
	}

	@Test
	public void testUpdateUserProfileElsePtoId() throws IOException {
		userProfileUpdateRequest = new UserProfileUpdateRequest();
		webResponse = new WebResponse();
		userProfileUpdateRequest.setPtoId(TestConstants.PTO_OPERATION_ID);
		userProfileUpdateRequest.setAdminUserId(TestConstants.USER_ID);
		Mockito.when(userSessionManagementDao.validateUserProfileRequest(userProfileUpdateRequest)).thenReturn(false);
		Mockito.when(dataValidation.validateOrganizationId(Matchers.any())).thenReturn(false);
		webResponse = userSessionManagementServiceImpl.updateUserProfile(userProfileUpdateRequest, httpResponse,
				webResponse);
	}

	@Test
	public void testUpdateUserProfileElseAdminUserId() throws IOException {
		userProfileUpdateRequest = new UserProfileUpdateRequest();
		webResponse = new WebResponse();
		userProfileUpdateRequest.setPtoId(TestConstants.PTO_OPERATION_ID);
		userProfileUpdateRequest.setAdminUserId(TestConstants.USER_ID);
		Mockito.when(userSessionManagementDao.validateUserProfileRequest(userProfileUpdateRequest)).thenReturn(false);
		Mockito.when(dataValidation.validateOrganizationId(Matchers.any())).thenReturn(true);
		Mockito.when(dataValidation.isValidUser(userProfileUpdateRequest.getAdminUserId())).thenReturn(false);
		webResponse = userSessionManagementServiceImpl.updateUserProfile(userProfileUpdateRequest, httpResponse,
				webResponse);
		Assert.assertEquals(CustomErrorCodes.USER_NOT_EXIST.getCustomErrorCode(), webResponse.getStatusCode());
		Assert.assertEquals(TestConstants.USER_NOT_EXIST, webResponse.getStatusMessage());
	}

	@Test
	public void testChangePassword() {
		changePasswordRequest = new ChangePasswordRequest();
		changePasswordRequest.setNewPassword(TestConstants.SOFTWARE_NAME);
		changePasswordRequest.setCurrentPassword(TestConstants.SOFTWARE_NAME);
		webResponse = new WebResponse();
		Mockito.when(userSessionManagementDao.validateChangePasswordRequest(changePasswordRequest)).thenReturn(true);
		webResponse = userSessionManagementServiceImpl.changePassword(changePasswordRequest, httpResponse, webResponse);
		Assert.assertEquals(CustomErrorCodes.AFCS_ADMIN_USER_CHANGEPASSWORD_OLDNEW_ERROR.getCustomErrorCode(),
				webResponse.getStatusCode());
		Assert.assertEquals(CustomErrorCodes.AFCS_ADMIN_USER_CHANGEPASSWORD_OLDNEW_ERROR.getCustomErrorCode(),
				webResponse.getStatusCode());
		Assert.assertNotNull(webResponse);
	}

	@Test
	public void testChangePasswordIfFalse() {
		changePasswordRequest = new ChangePasswordRequest();
		changePasswordRequest.setNewPassword(PSWD);
		changePasswordRequest.setCurrentPassword(WRONG_PSWD_MSG);
		webResponse = new WebResponse();
		Mockito.when(userSessionManagementDao.validateChangePasswordRequest(changePasswordRequest)).thenReturn(true);
		Assert.assertNotNull(webResponse);
	}

	@Test
	public void testChangePasswordIsValidUserValidation() {
		changePasswordRequest = new ChangePasswordRequest();
		changePasswordRequest.setNewPassword(TestConstants.SOFTWARE_NAME);
		changePasswordRequest.setCurrentPassword(TestConstants.BASEPATH);
		webResponse = new WebResponse();
		Mockito.when(userSessionManagementDao.validateChangePasswordRequest(changePasswordRequest)).thenReturn(false);
		Mockito.when(dataValidation.isValidUser(changePasswordRequest.getUserId())).thenReturn(false);
		webResponse = userSessionManagementServiceImpl.changePassword(changePasswordRequest, httpResponse, webResponse);
		Assert.assertEquals(CustomErrorCodes.USER_NOT_EXIST.getCustomErrorCode(), webResponse.getStatusCode());
		Assert.assertEquals(CustomErrorCodes.USER_NOT_EXIST.getCustomErrorMsg(), webResponse.getStatusMessage());
		Assert.assertNotNull(webResponse);
	}

	@Test
	public void testChangePasswordPasswordValidationValidation() {
		changePasswordRequest = new ChangePasswordRequest();
		changePasswordRequest.setNewPassword(TestConstants.SOFTWARE_NAME);
		changePasswordRequest.setCurrentPassword(TestConstants.BASEPATH);
		webResponse = new WebResponse();
		Mockito.when(userSessionManagementDao.validateChangePasswordRequest(changePasswordRequest)).thenReturn(false);
		Mockito.when(dataValidation.isValidUser(changePasswordRequest.getUserId())).thenReturn(true);
		Mockito.when(dataValidation.passwordValidation(changePasswordRequest.getCurrentPassword())).thenReturn(false);
		webResponse = userSessionManagementServiceImpl.changePassword(changePasswordRequest, httpResponse, webResponse);
		Assert.assertEquals(CustomErrorCodes.WRONG_PASSWORD.getCustomErrorCode(), webResponse.getStatusCode());
		Assert.assertEquals(CustomErrorCodes.WRONG_PASSWORD.getCustomErrorMsg(), webResponse.getStatusMessage());
		Assert.assertNotNull(webResponse);
	}

	@Test
	public void deviceTypeRegistrationTransactionIdTest() throws IOException {
		userInfoCheck = new UserInfoCheck();
		webResponse = new WebResponse();
		userInfoCheck.setAdminUserId(TestConstants.USER_ID);
		webResponse.setStatusCode(TestConstants.STRING_ONE);
		Mockito.when(userSessionManagementDao.validateUserInfo(userInfoCheck)).thenReturn(true);
		Mockito.when(userSessionManagementDao.validateExistingUserInfo(userInfoCheck)).thenReturn(true);
		userSessionManagementServiceImpl.checkUserInfo(userInfoCheck, httpResponse, webResponse);
		Assert.assertEquals(CustomErrorCodes.SUCCESS.getCustomErrorCode(), webResponse.getStatusCode());
		Assert.assertEquals(CustomErrorCodes.SUCCESS.getCustomErrorMsg(), webResponse.getStatusMessage());
		Assert.assertNotNull(webResponse);
	}

	@Test
	public void deviceTypeRegistrationUserIdTest() throws IOException {
		userInfoCheck = new UserInfoCheck();
		webResponse = new WebResponse();
		userInfoCheck.setAdminUserId(TestConstants.USER_ID);
		webResponse.setStatusCode(TestConstants.STRING_ONE);
		userSessionManagementServiceImpl.checkUserInfo(userInfoCheck, httpResponse, webResponse);
		Assert.assertEquals(CustomErrorCodes.INVALID_ADMIN.getCustomErrorCode(), webResponse.getStatusCode());
		Assert.assertEquals(CustomErrorCodes.INVALID_ADMIN.getCustomErrorMsg(), webResponse.getStatusMessage());
		Assert.assertNotNull(webResponse);
	}

	@Test
	public void deviceTypeRegistrationPtoOperationIdTest() throws IOException {
		userInfoCheck = new UserInfoCheck();
		webResponse = new WebResponse();
		userInfoCheck.setAdminUserId(TestConstants.USER_ID);
		webResponse.setStatusCode(TestConstants.STRING_ONE);
		Mockito.when(dataValidation.isValidUser(userInfoCheck.getAdminUserId())).thenReturn(true);
		userSessionManagementServiceImpl.checkUserInfo(userInfoCheck, httpResponse, webResponse);
		Assert.assertNotNull(webResponse);
		Assert.assertEquals(CustomErrorCodes.USER_NOT_EXIST.getCustomErrorCode(), webResponse.getStatusCode());
		Assert.assertEquals(CustomErrorCodes.USER_NOT_EXIST.getCustomErrorMsg(), webResponse.getStatusMessage());
		
	}

	@Test
	public void testUserRegistrationSaveUserDetails() throws IOException, ParseException {
		userRegistrationRequest = new UserRegistrationRequest();
		userRegistrationRequest.setUserRole(TestConstants.USER_ROLE);
		webResponse = new WebResponse();
		userCredentials = new UserCredentials();
		Mockito.when(userSessionManagementDao.validateUserRegistrationRequest(userRegistrationRequest))
				.thenReturn(true);
		Mockito.when(userSessionManagementDao.validateExistingUserForRegistration(userRegistrationRequest))
				.thenReturn(true);
		webResponse = userSessionManagementServiceImpl.userRegistration(userRegistrationRequest, httpResponse,
				webResponse);
		Assert.assertEquals(CustomErrorCodes.SERVER_ERROR.getCustomErrorCode(), webResponse.getStatusCode());
		Assert.assertEquals(CustomErrorCodes.SERVER_ERROR.getCustomErrorMsg(), webResponse.getStatusMessage());
		Assert.assertNotNull(webResponse);
	}

	@Test
	public void testUserRegistrationIsAdminUserId() throws IOException, ParseException {
		userRegistrationRequest = new UserRegistrationRequest();
		webResponse = new WebResponse();
		userRegistrationRequest.setUserId(TestConstants.USER_ID);
		Mockito.when(userSessionManagementDao.validateUserRegistrationRequest(userRegistrationRequest))
				.thenReturn(false);
		webResponse = userSessionManagementServiceImpl.userRegistration(userRegistrationRequest, httpResponse,
				webResponse);
		Assert.assertEquals(CustomErrorCodes.INVALID_ADMIN.getCustomErrorCode(), webResponse.getStatusCode());
		Assert.assertEquals(CustomErrorCodes.INVALID_ADMIN.getCustomErrorMsg(), webResponse.getStatusMessage());
		Assert.assertNotNull(webResponse);
	}

	@Test
	public void forgetPasswordErrorsUserIdTest() throws IOException {
		UserInfoCheck userParameter = new UserInfoCheck();
		userParameter.setAdminUserId(TestConstants.USER_ID);
		Mockito.when(userSessionManagementDao.validateExistingUserInfo(Matchers.any(UserInfoCheck.class)))
				.thenReturn(false);
		Mockito.when(dataValidation.isValidUser(Matchers.any())).thenReturn(false);
		WebResponse response = userSessionManagementServiceImpl.forgetPassword(forgetPasswordRequest, httpResponse);
		Assert.assertEquals(CustomErrorCodes.EMAIL_NOT_EXISTS.getCustomErrorCode(), response.getStatusCode());
		Assert.assertEquals(CustomErrorCodes.EMAIL_NOT_EXISTS.getCustomErrorMsg(), response.getStatusMessage());
	}

	@Test
	public void resetPasswordTest() throws IOException {
		String emailToken = StringUtil.getEmailToken(TestConstants.ADDRESS);
		resetPasswordRequest = new ResetPasswordRequest();
		resetPasswordRequest.setEmailToken(emailToken);
		Mockito.when(userSessionManagementDao.validateEmailToken(Matchers.anyString(), Matchers.anyString()))
				.thenReturn(true);
		Mockito.when(properties.getProperty(Matchers.anyString())).thenReturn(TestConstants.STRING_ONE);
	}

	@Test
	public void resetPasswordErrorTest() throws IOException {
		resetPasswordRequest = new ResetPasswordRequest();
		resetPasswordRequest.setEmailToken(TestConstants.ADDRESS);
		Mockito.when(userSessionManagementDao.validateEmailToken(Matchers.anyString(), Matchers.anyString()))
				.thenReturn(true);
		Mockito.when(properties.getProperty(Matchers.anyString())).thenReturn(TestConstants.STRING_ONE);
		WebResponse response = userSessionManagementServiceImpl.resetPassword(resetPasswordRequest, httpResponse);
		Assert.assertEquals(CustomErrorCodes.RESET_PSWD_LINK_EXPIRED.getCustomErrorCode(), response.getStatusCode());
		Assert.assertEquals(CustomErrorCodes.RESET_PSWD_LINK_EXPIRED.getCustomErrorMsg(), response.getStatusMessage());
		Assert.assertNotNull(webResponse);
	}

}
