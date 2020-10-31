package com.chatak.transit.afcs.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatak.transit.afcs.server.pojo.web.IssuerRequest;
import com.chatak.transit.afcs.server.pojo.web.IssuerResponse;
import com.chatak.transit.afcs.server.service.IssuerOnboardingService;

@RestController
@RequestMapping(value = "/issuerOnboarding/")
public class IssuerOnboardingRestController {

	@Autowired
	private IssuerOnboardingService issuerOnboardingService; 
	
	@PostMapping(value = "createIssuer")
	public IssuerResponse createIssuer(@RequestBody IssuerRequest issuerRequest) {
		return issuerOnboardingService.createIssuer(issuerRequest);
	}
	
	@PostMapping(value = "searchIssuer")
	public IssuerResponse searchIssuer(@RequestBody IssuerRequest issuerRequest) {
		return issuerOnboardingService.searchIssuer(issuerRequest);
	}
	
	@PostMapping(value = "deleteIssuer")
	public IssuerResponse deleteIssuer(@RequestBody IssuerRequest issuerRequest) {
		return issuerOnboardingService.deleteIssuer(issuerRequest);
	}
	
	@PostMapping(value = "getAllIssuers")
	public IssuerResponse getAllIssuers() {
		return issuerOnboardingService.getAllIssuers();
	}
	
	@PostMapping(value = "getIssuerById")
	public IssuerResponse getIssuerById(@RequestBody IssuerRequest issuerRequest) {
		return issuerOnboardingService.getIssuerById(issuerRequest);
	}
}
