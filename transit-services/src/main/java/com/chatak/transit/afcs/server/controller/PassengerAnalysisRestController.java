package com.chatak.transit.afcs.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatak.transit.afcs.server.pojo.web.PassengerAnalysisRequest;
import com.chatak.transit.afcs.server.pojo.web.PassengerAnalysisResponse;
import com.chatak.transit.afcs.server.service.PassengerAnalysisService;

@RestController
@RequestMapping("/online/")
public class PassengerAnalysisRestController {

	@Autowired
	PassengerAnalysisService passengerAnalysisService;

	@PostMapping(value = "searchPassengerAnalysisReport")
	public PassengerAnalysisResponse searchPassengerAnalysisReport(@RequestBody PassengerAnalysisRequest request) {
		return passengerAnalysisService.searchPassengerAnalysisReport(request);
	}
}
