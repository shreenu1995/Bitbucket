package com.chatak.transit.afcs.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatak.transit.afcs.server.pojo.web.PgRequest;
import com.chatak.transit.afcs.server.pojo.web.PgResponse;
import com.chatak.transit.afcs.server.service.PgOnboardingService;

@RestController
@RequestMapping(value = "/pgOnboarding/")
public class PgOnboardingRestController {

	@Autowired
	private PgOnboardingService pgOnboardingService;
	
	@PostMapping(value = "createPaygate")
	public PgResponse createPaygate(@RequestBody PgRequest pgRequest) {
		return pgOnboardingService.createPaygate(pgRequest);
	}
	
	@PostMapping(value = "searchPaygate")
	public PgResponse searchPaygate(@RequestBody PgRequest pgRequest) {
		return pgOnboardingService.searchPaygate(pgRequest);
	}
	
	@PostMapping(value = "deletePaygate")
	public PgResponse deletePaygate(@RequestBody PgRequest pgRequest) {
		return pgOnboardingService.deletePaygate(pgRequest);
	}
}
