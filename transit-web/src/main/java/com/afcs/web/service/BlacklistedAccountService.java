package com.afcs.web.service;

import java.util.List;

import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.BlacklistedAccountRequest;
import com.chatak.transit.afcs.server.pojo.web.BlacklistedAccountResponse;

public interface BlacklistedAccountService {

	BlacklistedAccountResponse searchBlacklistedAccount(BlacklistedAccountRequest blacklistedAccountRequest)
			throws AFCSException;

	BlacklistedAccountResponse blacklistedAccountCreate(List<BlacklistedAccountRequest> blacklistedAccountList)throws AFCSException;

}
