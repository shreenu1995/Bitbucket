/**
 * @author Girmiti Software
 */
package com.chatak.transit.afcs.server.service;

import java.io.IOException;
import java.text.ParseException;

import javax.servlet.http.HttpServletResponse;

import com.chatak.transit.afcs.server.pojo.Response;
import com.chatak.transit.afcs.server.pojo.web.ChangePasswordRequest;
import com.chatak.transit.afcs.server.pojo.web.ForgetPasswordRequest;
import com.chatak.transit.afcs.server.pojo.web.LoginSessionDetailsRequest;
import com.chatak.transit.afcs.server.pojo.web.LoginSessionDetailsResponse;
import com.chatak.transit.afcs.server.pojo.web.ResetPasswordRequest;
import com.chatak.transit.afcs.server.pojo.web.SearchUserResponse;
import com.chatak.transit.afcs.server.pojo.web.TransactionRequest;
import com.chatak.transit.afcs.server.pojo.web.TransactionResponse;
import com.chatak.transit.afcs.server.pojo.web.UserData;
import com.chatak.transit.afcs.server.pojo.web.UserDetail;
import com.chatak.transit.afcs.server.pojo.web.UserInfoCheck;
import com.chatak.transit.afcs.server.pojo.web.UserProfileUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.UserRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.UserStatusUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.UserStatusUpdateResponse;
import com.chatak.transit.afcs.server.pojo.web.UserViewResponse;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;

public interface UserSessionManagementService {

	WebResponse checkUserInfo(UserInfoCheck request, HttpServletResponse httpResponse, WebResponse response)
			throws IOException;

	WebResponse userRegistration(UserRegistrationRequest request, HttpServletResponse httpResponse,
			WebResponse response) throws IOException, ParseException;

	UserStatusUpdateResponse updateUserStatus(UserStatusUpdateRequest request, UserStatusUpdateResponse response);

	WebResponse changePassword(ChangePasswordRequest request, HttpServletResponse httpResponse, WebResponse response);

	void changePasswordErrors(ChangePasswordRequest request, WebResponse response);

	TransactionResponse saveAdminTransactionData(TransactionRequest request, TransactionResponse response,
			HttpServletResponse httpResponse) throws IOException;

	void checkAdminTransactionErrors(TransactionRequest request, TransactionResponse response);

	UserViewResponse getUserDetail(UserDetail userViewRequest, UserViewResponse response,
			HttpServletResponse httpResponse);

	void checkUserDetailErrors(UserDetail request, UserViewResponse response);

	WebResponse updateUserProfile(UserProfileUpdateRequest request, HttpServletResponse httpResponse,
			WebResponse respomse) throws IOException;

	void errorsUserUpdateProfile(UserProfileUpdateRequest request, WebResponse response);

	WebResponse forgetPassword(ForgetPasswordRequest request, HttpServletResponse httpResponse) throws IOException;

	WebResponse resetPassword(ResetPasswordRequest request, HttpServletResponse httpResponse) throws IOException;

	void infoCheckErrors(UserInfoCheck request, WebResponse response);

	void registrationErrors(UserRegistrationRequest request, WebResponse response);

	boolean emailExistsCheck(UserInfoCheck userInfoCheck);

	Response validateEmailToken(ResetPasswordRequest request);

	SearchUserResponse searchUser(UserData request);
	
	WebResponse saveLoginSessionDetails(LoginSessionDetailsRequest request);

	LoginSessionDetailsResponse getSessionDetails(LoginSessionDetailsRequest loginSessionDetailsRequest);

	WebResponse updateLoginSessionDetails(LoginSessionDetailsRequest loginSessionDetailsRequest);

}
