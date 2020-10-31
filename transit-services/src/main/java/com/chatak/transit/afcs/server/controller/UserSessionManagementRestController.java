/**
 * @author Girmiti Software
 */
package com.chatak.transit.afcs.server.controller;

import java.io.IOException;
import java.text.ParseException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatak.transit.afcs.server.enums.CustomErrorCodes;
import com.chatak.transit.afcs.server.model.OrderChecks;
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
import com.chatak.transit.afcs.server.service.UserSessionManagementService;

@RestController
@RequestMapping(value = "/online/")
public class UserSessionManagementRestController extends ServiceHelper {

	private static final Logger logger = LoggerFactory.getLogger(UserSessionManagementRestController.class);

	@Autowired
	private UserSessionManagementService userSessionManagementService;

	@PostMapping(value = "userInfoCheck")
	public WebResponse checkUserInfo(@Valid @RequestBody UserInfoCheck request, BindingResult bindingResult,
			HttpServletResponse httpResponse, WebResponse response) throws IOException {
		if (bindingResult.hasErrors()) {
			return bindingResultErrorDetails(bindingResult);
		}
		return userSessionManagementService.checkUserInfo(request, httpResponse, response);
	}

	@PostMapping(value = "userRegistration")
	public WebResponse userRegistration(@RequestBody UserRegistrationRequest request,
			BindingResult bindingResult, HttpServletResponse httpResponse, WebResponse response) throws IOException, ParseException {

		try {
			return userSessionManagementService.userRegistration(request, httpResponse, response);
		} catch (ParseException e) {
			response.setStatusCode(CustomErrorCodes.INVALID_TIMESTAMP_FORMAT.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.INVALID_TIMESTAMP_FORMAT.getCustomErrorMsg());
			response.setReservedResponse("");
			return response;
		}
	}

	@PostMapping(value = "updateUserStatus")
	public UserStatusUpdateResponse updateUserStatus(@RequestBody UserStatusUpdateRequest request,
			BindingResult bindingResult, HttpServletResponse httpResponse, UserStatusUpdateResponse response) {
		WebResponse webResponse = null;
		if (bindingResult.hasErrors()) {
			webResponse = bindingResultErrorDetails(bindingResult);
			response.setStatusCode(webResponse.getStatusCode());
			response.setStatusMessage(webResponse.getStatusMessage());
			return response;
		}
		return userSessionManagementService.updateUserStatus(request,response);
	}

	@PostMapping(value = "changePassword")
	public WebResponse changePassword(@RequestBody  @Validated(value=OrderChecks.class) ChangePasswordRequest request, BindingResult bindingResult,
			HttpServletResponse httpResponse, WebResponse response) {
		return userSessionManagementService.changePassword(request, httpResponse, response);
	}

	@PostMapping(value = "transactionAdminData")
	public TransactionResponse saveAdminTransactionData(@RequestBody TransactionRequest request,
			BindingResult bindingResult, TransactionResponse response, HttpServletResponse httpResponse)
			throws IOException {
		logger.debug("Enter into Transaction Admin Data");
		if (bindingResult.hasErrors()) {
			WebResponse webResponse = bindingResultErrorDetails(bindingResult);
			response.setStatusCode(webResponse.getStatusCode());
			response.setStatusMessage(webResponse.getStatusMessage());
			return response;
		}
		return userSessionManagementService.saveAdminTransactionData(request, response, httpResponse);
	}

	@PostMapping(value = "userDetail")
	public UserViewResponse getUserDetail(@Valid @RequestBody UserDetail request, BindingResult bindingResult,
			UserViewResponse response, HttpServletResponse httpResponse) {
		logger.debug("inside into userDetail");

		if (bindingResult.hasErrors()) {
			WebResponse webResponse = bindingResultErrorDetails(bindingResult);
			response.setStatusCode(webResponse.getStatusCode());
			response.setStatusMessage(webResponse.getStatusMessage());
			return response;
		}

		return userSessionManagementService.getUserDetail(request, response, httpResponse);
	}

	@PostMapping(value = "updateUserProfile")
	public WebResponse userProfileUpdate(@RequestBody UserProfileUpdateRequest request,
			BindingResult bindingResult, HttpServletResponse httpResponse, WebResponse response) throws IOException {
		return userSessionManagementService.updateUserProfile(request, httpResponse, response);
	}

	@PostMapping(value = "resetPassword")
	public WebResponse resetUserPassword(@Valid @RequestBody ResetPasswordRequest request, BindingResult bindingResult,
			HttpServletResponse httpResponse) throws IOException {
		if (bindingResult.hasErrors()) {
			return bindingResultErrorDetails(bindingResult);
		}
		return userSessionManagementService.resetPassword(request, httpResponse);
	}
	
	@PostMapping(value = "validateEmailToken")
	public Response validateEmailToken(@RequestBody ResetPasswordRequest request) {
		return userSessionManagementService.validateEmailToken(request);
	}

	@PostMapping(value = "forgetPassword")
	public WebResponse forgetUserPassword(@Valid @RequestBody ForgetPasswordRequest request,
			BindingResult bindingResult, HttpServletResponse httpResponse, WebResponse response) throws IOException {
		if (bindingResult.hasErrors()) {
			return bindingResultErrorDetails(bindingResult);
		}
		return userSessionManagementService.forgetPassword(request, httpResponse);
	}
	
	@PostMapping(value = "emailExistsCheck", consumes = MediaType.APPLICATION_JSON_VALUE,
		      produces = MediaType.APPLICATION_JSON_VALUE)
	public Response emailExistsCheck(@RequestBody UserInfoCheck userInfoCheck) {
		boolean isEmailExists = userSessionManagementService.emailExistsCheck(userInfoCheck);
		Response response = new Response();
		if(isEmailExists) {
			response.setResult(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		} else {
			response.setResult(CustomErrorCodes.FAILURE.getCustomErrorMsg());
		}
		return response;
	}
	
	@PostMapping(value = "searchUser")
	public SearchUserResponse searchUser(@RequestBody UserData request) {
		return userSessionManagementService.searchUser(request);
	}
	
	@PostMapping(value = "saveLoginSessionDetails")
	public WebResponse saveLoginSessionDetails(@RequestBody LoginSessionDetailsRequest loginSessionDetailsRequest) {
		return userSessionManagementService.saveLoginSessionDetails(loginSessionDetailsRequest);
	}

	@PostMapping(value = "getLoginSessionDetails")
	public LoginSessionDetailsResponse getSessionDetails(@RequestBody LoginSessionDetailsRequest loginSessionDetailsRequest) {
		return userSessionManagementService.getSessionDetails(loginSessionDetailsRequest);
	}
	
	@PostMapping(value = "updateLoginSessionDetails")
	public WebResponse updateLoginSessionDetails(@RequestBody LoginSessionDetailsRequest loginSessionDetailsRequest) {
		return userSessionManagementService.updateLoginSessionDetails(loginSessionDetailsRequest);
	}
}
