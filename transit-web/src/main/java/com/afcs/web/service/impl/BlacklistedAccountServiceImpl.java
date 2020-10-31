package com.afcs.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.afcs.web.service.BlacklistedAccountService;
import com.afcs.web.util.JsonUtil;
import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.BlacklistedAccountRequest;
import com.chatak.transit.afcs.server.pojo.web.BlacklistedAccountResponse;
import com.chatak.transit.afcs.server.util.StringUtil;

@Service
public class BlacklistedAccountServiceImpl implements BlacklistedAccountService {

	@Autowired
	private JsonUtil jsonUtil;

	@Autowired
	Environment properties;

	private static final String AFCS_SERVER_ERROR = "afcs.server.not.responding";

	@Override
	public BlacklistedAccountResponse searchBlacklistedAccount(BlacklistedAccountRequest blacklistedAccountRequest)
			throws AFCSException {
		BlacklistedAccountResponse response = jsonUtil.postRequest(blacklistedAccountRequest,
				BlacklistedAccountResponse.class, "online/blacklistedAccount/searchBlacklistedAccount");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

	@Override
	public BlacklistedAccountResponse blacklistedAccountCreate(List<BlacklistedAccountRequest> blacklistedAccountList) throws AFCSException {
		BlacklistedAccountResponse response = jsonUtil.postRequest(blacklistedAccountList, BlacklistedAccountResponse.class, "online/blacklistedAccount/createBlacklistedAccount");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

}
