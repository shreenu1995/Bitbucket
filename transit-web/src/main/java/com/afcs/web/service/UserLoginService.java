package com.afcs.web.service;

import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.ChangePasswordRequest;
import com.chatak.transit.afcs.server.pojo.web.ForgetPasswordRequest;
import com.chatak.transit.afcs.server.pojo.web.LoginSessionDetailsRequest;
import com.chatak.transit.afcs.server.pojo.web.LoginSessionDetailsResponse;
import com.chatak.transit.afcs.server.pojo.web.ResetPasswordRequest;
import com.chatak.transit.afcs.server.pojo.web.TransactionRequest;
import com.chatak.transit.afcs.server.pojo.web.TransactionResponse;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;

public interface UserLoginService {

	TransactionResponse userLoginRequest(TransactionRequest request) throws AFCSException;

	WebResponse forgotPassword(ForgetPasswordRequest request) throws AFCSException;

	WebResponse resetPassword(ResetPasswordRequest request) throws AFCSException;

	boolean validateEmailToken(ResetPasswordRequest request) throws AFCSException;

	WebResponse changePassword(ChangePasswordRequest request) throws AFCSException;
	
	WebResponse saveLoginSessionDetails(LoginSessionDetailsRequest request) throws AFCSException;

	LoginSessionDetailsResponse getSessionDetails(LoginSessionDetailsRequest loginSessionDetailsRequest) throws AFCSException;

	WebResponse updateLoginSessionDetails(LoginSessionDetailsRequest loginSessionRequestData) throws AFCSException;

}
