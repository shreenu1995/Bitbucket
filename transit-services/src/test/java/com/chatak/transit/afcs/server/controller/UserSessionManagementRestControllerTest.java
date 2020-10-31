package com.chatak.transit.afcs.server.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.env.Environment;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.chatak.transit.afcs.constants.TestConstants;
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
import com.chatak.transit.afcs.server.pojo.web.UserListResponse;
import com.chatak.transit.afcs.server.pojo.web.UserProfileUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.UserRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.UserStatusUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.UserStatusUpdateResponse;
import com.chatak.transit.afcs.server.pojo.web.UserViewResponse;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.service.UserSessionManagementService;

@RunWith(MockitoJUnitRunner.Silent.class)
public class UserSessionManagementRestControllerTest {

	@InjectMocks
	UserSessionManagementRestController userSessionManagementRestController;

	@Mock
	UserSessionManagementService userSessionManagementService;

	@Mock
	CommonUserParameter commonUserParameter;

	@Mock
	HttpServletResponse httpResponse;

	@Mock
	WebResponse webResponse;

	@Mock
	BindingResult bindingResult;

	@Mock
	UserRegistrationRequest userRegistrationRequest;

	@Mock
	UserStatusUpdateRequest userStatusUpdateRequest;

	@Mock
	UserListResponse userListResponse;

	@Mock
	UserViewResponse userViewResponse;

	@Mock
	UserStatusUpdateResponse userStatusUpdateResponse;

	@Mock
	ChangePasswordRequest changePasswordRequest;

	@Mock
	TransactionRequest transactionRequest;

	@Mock
	TransactionResponse transactionResponse;

	@Mock
	ForgetPasswordRequest forgetPasswordRequest;

	@Mock
	ResetPasswordRequest resetPasswordRequest;

	@Mock
	UserListRequest userListRequest;

	@Mock
	Environment property;

	@Mock
	List<String> errorCodeList;

	@Mock
	List<ObjectError> objectErrors;

	@Mock
	UserInfoCheck UserInfoCheckRequest;

	@Mock
	UserDetail userDetailRequest;
	
	@Mock
	UserProfileUpdateRequest userProfileUpdateRequest;

	@Test
	public void testSaveAdminTransactionData() throws IOException {
		transactionRequest = new TransactionRequest();
		transactionRequest.setUserId(TestConstants.USER_ID);
		Mockito.when(userSessionManagementService.saveAdminTransactionData(transactionRequest, transactionResponse,
				httpResponse)).thenReturn(transactionResponse);
		transactionResponse = userSessionManagementRestController.saveAdminTransactionData(transactionRequest,
				bindingResult, transactionResponse, httpResponse);
		Assert.assertNotNull(transactionResponse);
	}

	@Test
	public void testSaveAdminTransactionDataBindingResultErrors() throws IOException {
		transactionResponse = new TransactionResponse();
		transactionResponse.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
		transactionResponse.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		setBindingResultErrorCommonScript();
		transactionResponse = userSessionManagementRestController.saveAdminTransactionData(transactionRequest,
				bindingResult, transactionResponse, httpResponse);
		Assert.assertNotNull(transactionResponse);
		Assert.assertEquals(TestConstants.STATUS_CODE, transactionResponse.getStatusCode());
	}

	@Test
	public void testCheckUserInfoBindingResultErrors() throws IOException {
		setWebResponse();
		setBindingResultErrorCommonScript();
		webResponse = userSessionManagementRestController.checkUserInfo(UserInfoCheckRequest, bindingResult,
				httpResponse, webResponse);
		Assert.assertNotNull(webResponse);
		Assert.assertEquals(TestConstants.STATUS_CODE, webResponse.getStatusCode());
	}

	@Test
	public void testCheckUserInfo() throws IOException {
		setWebResponse();
		Mockito.when(userSessionManagementService.checkUserInfo(UserInfoCheckRequest, httpResponse, webResponse))
				.thenReturn(webResponse);
		webResponse = userSessionManagementRestController.checkUserInfo(UserInfoCheckRequest, bindingResult,
				httpResponse, webResponse);
		Assert.assertNotNull(webResponse);
		Assert.assertEquals(TestConstants.STATUS_CODE, webResponse.getStatusCode());
	}

	private void setWebResponse() {
		webResponse = new WebResponse();
		webResponse.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
		webResponse.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
	}

	@Test
	public void testUserRegistration() throws IOException, ParseException {
		setWebResponse();
		Mockito.when(userSessionManagementService.userRegistration(userRegistrationRequest, httpResponse, webResponse))
				.thenReturn(webResponse);
		webResponse = userSessionManagementRestController.userRegistration(userRegistrationRequest, bindingResult,
				httpResponse, webResponse);
		Assert.assertNotNull(webResponse);
		Assert.assertEquals(TestConstants.STATUS_CODE, webResponse.getStatusCode());
	}

	private void setBindingResultErrorCommonScript() {
		ObjectError objectError = new ObjectError(TestConstants.STATUS_CODE, TestConstants.STATUS_MESSAGE);
		errorCodeList = new ArrayList<String>();
		objectErrors = new ArrayList<>();
		objectErrors.add(objectError);
		String errorCode = TestConstants.STATUS_CODE;
		errorCodeList.add(errorCode);
		Mockito.when(bindingResult.getAllErrors()).thenReturn(objectErrors);
		Mockito.when(property.getProperty(objectError.getDefaultMessage())).thenReturn(errorCode);
		Mockito.when(bindingResult.hasErrors()).thenReturn(true);
	}

	@Test
	public void testUpdateUserStatus() {
		userStatusUpdateResponse = new UserStatusUpdateResponse();
		userStatusUpdateResponse.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
		userStatusUpdateResponse.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		userStatusUpdateResponse = userSessionManagementRestController.updateUserStatus(userStatusUpdateRequest,
				bindingResult, httpResponse, userStatusUpdateResponse);
		Assert.assertNotNull(userSessionManagementRestController);
	}

	@Test
	public void testUpdateUserStatusBindingResultErrors() {
		userStatusUpdateResponse = new UserStatusUpdateResponse();
		userStatusUpdateResponse.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
		userStatusUpdateResponse.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		setBindingResultErrorCommonScript();
		userStatusUpdateResponse = userSessionManagementRestController.updateUserStatus(userStatusUpdateRequest,
				bindingResult, httpResponse, userStatusUpdateResponse);
		Assert.assertNotNull(userStatusUpdateResponse);
		Assert.assertEquals(TestConstants.STATUS_CODE, userStatusUpdateResponse.getStatusCode());
	}

	@Test
	public void testChangePassword() throws IOException {
		setWebResponse();
		Mockito.when(userSessionManagementService.changePassword(changePasswordRequest, httpResponse, webResponse))
				.thenReturn(webResponse);
		webResponse = userSessionManagementRestController.changePassword(changePasswordRequest, bindingResult,
				httpResponse, webResponse);
		Assert.assertNotNull(webResponse);
		Assert.assertEquals(TestConstants.STATUS_CODE, webResponse.getStatusCode());
	}
	
	@Test
	public void testGetUserDetail() {
		userViewResponse = new UserViewResponse();
		userViewResponse.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
		userViewResponse.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		Mockito.when(userSessionManagementService.getUserDetail(userDetailRequest, userViewResponse, httpResponse))
				.thenReturn(userViewResponse);
		userViewResponse = userSessionManagementRestController.getUserDetail(userDetailRequest, bindingResult,
				userViewResponse, httpResponse);
		Assert.assertNotNull(userViewResponse);
		Assert.assertEquals(TestConstants.STATUS_CODE, userViewResponse.getStatusCode());
	}

	@Test
	public void testGetUserDetailBindingResultErrors() {
		userViewResponse = new UserViewResponse();
		userViewResponse.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
		userViewResponse.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		setBindingResultErrorCommonScript();
		Mockito.when(userSessionManagementService.getUserDetail(userDetailRequest, userViewResponse, httpResponse))
				.thenReturn(userViewResponse);
		userViewResponse = userSessionManagementRestController.getUserDetail(userDetailRequest, bindingResult,
				userViewResponse, httpResponse);
		Assert.assertNotNull(userViewResponse);
		Assert.assertEquals(TestConstants.STATUS_CODE, userViewResponse.getStatusCode());
	}

	@Test
	public void testUserProfileUpdate() throws IOException {
		setWebResponse();
		Mockito.when(userSessionManagementService.updateUserProfile(userProfileUpdateRequest, httpResponse, webResponse)).thenReturn(webResponse);
		webResponse = userSessionManagementRestController.userProfileUpdate(userProfileUpdateRequest, bindingResult, httpResponse,
				webResponse);
		Assert.assertNotNull(webResponse);
		Assert.assertEquals(TestConstants.STATUS_CODE, webResponse.getStatusCode());
	}
	
	@Test
	public void testResetUserPassword() throws IOException {
		setWebResponse();
		Mockito.when(userSessionManagementService.resetPassword(resetPasswordRequest, httpResponse)).thenReturn(webResponse);
		webResponse = userSessionManagementRestController.resetUserPassword(resetPasswordRequest, bindingResult, httpResponse);
		Assert.assertNotNull(webResponse);
		Assert.assertEquals(TestConstants.STATUS_CODE, webResponse.getStatusCode());
	}
	
	@Test
	public void testResetUserPasswordBindingResultErrors() throws IOException {
		setWebResponse();
		setBindingResultErrorCommonScript();
		webResponse = userSessionManagementRestController.resetUserPassword(resetPasswordRequest, bindingResult, httpResponse);
		Assert.assertNotNull(webResponse);
		Assert.assertEquals(TestConstants.STATUS_CODE, webResponse.getStatusCode());
	}
	
	@Test
	public void testForgetUserPassword() throws IOException {
		setWebResponse();
		Mockito.when(userSessionManagementService.forgetPassword(forgetPasswordRequest, httpResponse)).thenReturn(webResponse);
		webResponse = userSessionManagementRestController.forgetUserPassword(forgetPasswordRequest, bindingResult, httpResponse, webResponse);
		Assert.assertNotNull(webResponse);
		Assert.assertEquals(TestConstants.STATUS_CODE, webResponse.getStatusCode());
	}
	
	@Test
	public void testForgetUserPasswordBindingResultErrors() throws IOException {
		setWebResponse();
		setBindingResultErrorCommonScript();
		webResponse = userSessionManagementRestController.forgetUserPassword(forgetPasswordRequest, bindingResult, httpResponse, webResponse);
		Assert.assertNotNull(webResponse);
		Assert.assertEquals(TestConstants.STATUS_CODE, webResponse.getStatusCode());
	}
}
