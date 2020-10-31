package com.afcs.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.afcs.web.service.UserLoginService;
import com.afcs.web.util.JsonUtil;
import com.chatak.transit.afcs.server.enums.CustomErrorCodes;
import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.Response;
import com.chatak.transit.afcs.server.pojo.web.ChangePasswordRequest;
import com.chatak.transit.afcs.server.pojo.web.ForgetPasswordRequest;
import com.chatak.transit.afcs.server.pojo.web.LoginSessionDetailsRequest;
import com.chatak.transit.afcs.server.pojo.web.LoginSessionDetailsResponse;
import com.chatak.transit.afcs.server.pojo.web.ResetPasswordRequest;
import com.chatak.transit.afcs.server.pojo.web.TransactionRequest;
import com.chatak.transit.afcs.server.pojo.web.TransactionResponse;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.util.StringUtil;

@Service
public class UserLoginServiceImpl implements UserLoginService {

	@Autowired
	JsonUtil jsonUtil;

	@Autowired
	Environment properties;

	private static final String SERVER_NOT_RESPONDING = "afcs.server.not.responding";

	@Override
	public TransactionResponse userLoginRequest(TransactionRequest request) throws AFCSException {
		TransactionResponse response = jsonUtil.postRequest(request, TransactionResponse.class,
				"online/transactionAdminData");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(SERVER_NOT_RESPONDING));
		}
		return response;
	}

	@Override
	public WebResponse forgotPassword(ForgetPasswordRequest request) throws AFCSException {
		WebResponse response = jsonUtil.postRequest(request, WebResponse.class, "online/forgetPassword");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(SERVER_NOT_RESPONDING));
		}
		return response;
	}

	@Override
	public WebResponse resetPassword(ResetPasswordRequest request) throws AFCSException {
		WebResponse response = jsonUtil.postRequest(request, WebResponse.class, "online/resetPassword");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(SERVER_NOT_RESPONDING));
		}
		return response;
	}

	@Override
	public boolean validateEmailToken(ResetPasswordRequest request) throws AFCSException {
		Response response = jsonUtil.postRequest(request, Response.class, "online/validateEmailToken");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(SERVER_NOT_RESPONDING));
		}
		return (response.getResult().equals(CustomErrorCodes.SUCCESS.getCustomErrorCode()));
	}

	@Override
	public WebResponse changePassword(ChangePasswordRequest request) throws AFCSException {
		WebResponse response = jsonUtil.postRequest(request, WebResponse.class, "online/changePassword");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(SERVER_NOT_RESPONDING));
		}
		return response;
	}

	@Override
	public WebResponse saveLoginSessionDetails(LoginSessionDetailsRequest request) throws AFCSException {
		WebResponse response = jsonUtil.postRequest(request, WebResponse.class, "online/saveLoginSessionDetails");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(SERVER_NOT_RESPONDING));
		}
		return response;
	}

	@Override
	public LoginSessionDetailsResponse getSessionDetails(LoginSessionDetailsRequest loginSessionDetailsRequest)
			throws AFCSException {
		LoginSessionDetailsResponse responseList = jsonUtil.postRequest(loginSessionDetailsRequest,
				LoginSessionDetailsResponse.class, "online/getLoginSessionDetails");
		if (responseList == null) {
			throw new AFCSException(properties.getProperty(SERVER_NOT_RESPONDING));
		}
		return responseList;
	}

	@Override
	public WebResponse updateLoginSessionDetails(LoginSessionDetailsRequest loginSessionRequestData) throws AFCSException {
		WebResponse response= jsonUtil.postRequest(loginSessionRequestData, WebResponse.class, "online/updateLoginSessionDetails");		
	      if (response==null) {
	    	  throw new AFCSException(properties.getProperty(SERVER_NOT_RESPONDING));
	     }
	   return response;
	    }
}
