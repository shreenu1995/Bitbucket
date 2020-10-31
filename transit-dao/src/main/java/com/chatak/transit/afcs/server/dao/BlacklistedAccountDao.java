package com.chatak.transit.afcs.server.dao;

import com.chatak.transit.afcs.server.dao.model.BlacklistedAccount;
import com.chatak.transit.afcs.server.pojo.web.BlacklistedAccountRequest;
import com.chatak.transit.afcs.server.pojo.web.BlacklistedAccountResponse;

public interface BlacklistedAccountDao {
 
	BlacklistedAccountResponse getblackAccountList(BlacklistedAccountRequest request);
	
	boolean saveBlacklistedDetails(BlacklistedAccount request);
	
	boolean validateIssuerName(BlacklistedAccountRequest request);
	
}
