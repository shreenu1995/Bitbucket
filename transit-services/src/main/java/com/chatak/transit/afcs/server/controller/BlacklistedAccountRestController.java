package com.chatak.transit.afcs.server.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatak.transit.afcs.server.pojo.web.BlacklistedAccountRequest;
import com.chatak.transit.afcs.server.pojo.web.BlacklistedAccountResponse;
import com.chatak.transit.afcs.server.service.BlacklistedAccountService;

@RestController
@RequestMapping(value = "/online/blacklistedAccount/")
public class BlacklistedAccountRestController {
	
	@Autowired
	BlacklistedAccountService blacklistedAccountService;
	
	@PostMapping(value = "searchBlacklistedAccount")
	public BlacklistedAccountResponse searchBlacklistedAccount(@RequestBody BlacklistedAccountRequest request) {
		return blacklistedAccountService.searchBlacklistedAccount(request);
	}
	
	@PostMapping(value = "createBlacklistedAccount")
	public BlacklistedAccountResponse createBlacklistedAccount(@RequestBody List<BlacklistedAccountRequest> request) {
		return blacklistedAccountService.createBlacklistedAccount(request);
	}

}
