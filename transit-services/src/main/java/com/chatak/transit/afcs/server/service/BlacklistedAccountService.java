package com.chatak.transit.afcs.server.service;

import java.util.List;

import com.chatak.transit.afcs.server.pojo.web.BlacklistedAccountRequest;
import com.chatak.transit.afcs.server.pojo.web.BlacklistedAccountResponse;

public interface BlacklistedAccountService {

	BlacklistedAccountResponse searchBlacklistedAccount(BlacklistedAccountRequest blacklistedAccountRequest);
	
	BlacklistedAccountResponse createBlacklistedAccount(List<BlacklistedAccountRequest> blacklistedAccountRequest);
			
}
