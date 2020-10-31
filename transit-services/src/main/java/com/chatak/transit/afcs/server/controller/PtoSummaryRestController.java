package com.chatak.transit.afcs.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatak.transit.afcs.server.pojo.web.PtoSummaryRequest;
import com.chatak.transit.afcs.server.pojo.web.PtoSummaryResponse;
import com.chatak.transit.afcs.server.service.PtoSummaryService;

@RestController
@RequestMapping("/online/")
public class PtoSummaryRestController {

	@Autowired
	PtoSummaryService ptoSummaryService;

	@PostMapping(value = "searchPTOSummaryReport")
	public PtoSummaryResponse searchPTOSummaryReport(@RequestBody PtoSummaryRequest request) {
		return ptoSummaryService.searchPto(request);
	}

}
