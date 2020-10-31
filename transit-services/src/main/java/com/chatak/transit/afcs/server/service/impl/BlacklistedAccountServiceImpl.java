package com.chatak.transit.afcs.server.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatak.transit.afcs.server.dao.BlacklistedAccountDao;
import com.chatak.transit.afcs.server.dao.model.BlacklistedAccount;
import com.chatak.transit.afcs.server.enums.CustomErrorCodes;
import com.chatak.transit.afcs.server.pojo.web.BlacklistedAccountRequest;
import com.chatak.transit.afcs.server.pojo.web.BlacklistedAccountResponse;
import com.chatak.transit.afcs.server.service.BlacklistedAccountService;
import com.chatak.transit.afcs.server.util.StringUtil;

@Service
public class BlacklistedAccountServiceImpl implements BlacklistedAccountService{
	
	@Autowired
	BlacklistedAccountDao blacklistedAccountDao;

	@Override
	public BlacklistedAccountResponse searchBlacklistedAccount(BlacklistedAccountRequest blacklistedAccountRequest) {
		BlacklistedAccountResponse response = blacklistedAccountDao.getblackAccountList(blacklistedAccountRequest);
		if (!StringUtil.isNull(response)) {
			response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		} else {
			response.setStatusCode(CustomErrorCodes.FAILURE.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.FAILURE.getCustomErrorMsg());
		}
		return response;
	}

	@Override
	public BlacklistedAccountResponse createBlacklistedAccount(
			List<BlacklistedAccountRequest> blacklistedAccountRequest) {
 		BlacklistedAccountResponse blacklistedAccountResponse = new BlacklistedAccountResponse();
		if (!StringUtil.isListNullNEmpty(blacklistedAccountRequest)) {
			for (BlacklistedAccountRequest blacklistedData : blacklistedAccountRequest) {
				 validateBlackListedAccount(blacklistedData,blacklistedAccountResponse);
			}
		} else {
			blacklistedAccountResponse.setStatusCode(CustomErrorCodes.BLACKLISTED_ACCOUNT_FILE.getCustomErrorCode());
			blacklistedAccountResponse.setStatusMessage(CustomErrorCodes.BLACKLISTED_ACCOUNT_FILE.getCustomErrorMsg());
		}
		return blacklistedAccountResponse;
	}
	
	private BlacklistedAccountResponse validateBlackListedAccount(BlacklistedAccountRequest request,BlacklistedAccountResponse response) {
		BlacklistedAccount blacklistedAccount = new BlacklistedAccount();
		if(!StringUtil.isNull(request) && blacklistedAccountDao.validateIssuerName(request)) {
			blacklistedAccount.setAccount(request.getAccount());
			blacklistedAccount.setReason(request.getReason());
			blacklistedAccount.setIssuerId(request.getIssuerId());
			blacklistedAccount.setIssuerName(request.getIssuerName());
			blacklistedAccount.setPto(request.getPtoName());
			blacklistedAccountDao.saveBlacklistedDetails(blacklistedAccount);	
			response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		}else {
			validateBlacklistAccounteErrors(response);
		}
		return response;
		
	}
     
	private BlacklistedAccountResponse validateBlacklistAccounteErrors(BlacklistedAccountResponse response) {
		response.setStatusCode(CustomErrorCodes.ISSUER_EXIST.getCustomErrorCode());
		response.setStatusMessage(CustomErrorCodes.ISSUER_EXIST.getCustomErrorMsg());
		return response;
	}
}
