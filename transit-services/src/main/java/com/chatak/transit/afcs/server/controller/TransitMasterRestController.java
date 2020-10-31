package com.chatak.transit.afcs.server.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatak.transit.afcs.server.pojo.web.TransitMasterListDataResponse;
import com.chatak.transit.afcs.server.pojo.web.TransitMasterRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.TransitMasterRegistrationResponse;
import com.chatak.transit.afcs.server.pojo.web.TransitMasterSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.TransitMasterSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.TransitMasterUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.service.TransitMasterService;

@RestController
@RequestMapping(value = "/setuptransitmaster/")
public class TransitMasterRestController {

	@Autowired
	TransitMasterService transitMasterService;

	@PostMapping(value = "transitMasterRegistration")
	public TransitMasterRegistrationResponse transitMasterRegistrationResponse(
			@RequestBody TransitMasterRegistrationRequest request, TransitMasterRegistrationResponse response) {

		return transitMasterService.transitMasterRegistrationResponse(request, response);
	}

	@PostMapping(value = "transitMastersearch")
	public TransitMasterSearchResponse searchCard(@RequestBody TransitMasterSearchRequest request) {
		return transitMasterService.transitMasterSearchResponse(request);
	}

	@PostMapping(value = "updateTransitMaster")
	public WebResponse transitMasterUpdate(@RequestBody TransitMasterUpdateRequest request,
			HttpServletResponse httpResponse, WebResponse response) {

		return transitMasterService.updateTransitMaster(request, httpResponse, response);
	}

	@PostMapping(value = "updateTransitMasterStatus")
	public WebResponse transitMasterUpdateResponse(@RequestBody TransitMasterRegistrationRequest request,
			WebResponse response) {
		return transitMasterService.updateTransitMasterStatus(request, response);
	}

	@PostMapping(value = "getTransitMasterData")
	public WebResponse getSoftwareData(@RequestBody TransitMasterSearchRequest request,
			HttpServletResponse httpResponse, WebResponse response) {

		return transitMasterService.searchTransitMasterData(request);
	}

	@PostMapping("getListInherit")
	public TransitMasterListDataResponse getInherit(@RequestBody TransitMasterSearchRequest request) {

		return transitMasterService.getInheritList(request.getPtoId());

	}
}
