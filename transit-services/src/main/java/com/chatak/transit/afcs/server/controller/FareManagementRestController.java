package com.chatak.transit.afcs.server.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatak.transit.afcs.server.model.OrderChecks;
import com.chatak.transit.afcs.server.pojo.web.BulkUploadRequest;
import com.chatak.transit.afcs.server.pojo.web.FareRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.FareRegistrationResponse;
import com.chatak.transit.afcs.server.pojo.web.FareSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.FareSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.FareStatusUpdate;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.service.FareManagementService;

@RestController
@RequestMapping(value = "/online/fare/")
public class FareManagementRestController extends ServiceHelper {

	@Autowired
	FareManagementService fareManagementService;

	@PostMapping(value = "fareRegistration")
	public FareRegistrationResponse fareRegistration(
			@RequestBody @Validated(value = OrderChecks.class) FareRegistrationRequest request,
			BindingResult bindingResult, HttpServletResponse httpResponse, FareRegistrationResponse response) {

		if (bindingResult.hasErrors()) {
			WebResponse webResponse = bindingResultErrorDetails(bindingResult);
			response.setStatusCode(webResponse.getStatusCode());
			response.setStatusMessage(webResponse.getStatusMessage());
			return response;
		}
		return fareManagementService.saveFareRegistrationRequest(request, httpResponse, response);
	}

	@PostMapping(value = "searchFare")
	public FareSearchResponse searchFare(@RequestBody FareSearchRequest request) {
		return fareManagementService.searchFare(request);
	}

	@PostMapping(value = "fareStatusUpdate")
	public WebResponse updateFareStatus(@RequestBody FareStatusUpdate fareStatusUpdate, BindingResult bindingResult,
			WebResponse response) {

		if (bindingResult.hasErrors()) {
			response = bindingResultErrorDetails(bindingResult);
			response.setStatusCode(response.getStatusCode());
			response.setStatusMessage(response.getStatusMessage());
			return response;
		}
		return fareManagementService.fareStatusUpdate(fareStatusUpdate, response);
	}

	@PostMapping(value = "fareUpdate")
	public WebResponse updateFare(@RequestBody FareStatusUpdate fareUpdate, BindingResult bindingResult,
			WebResponse response, HttpServletResponse httpResponse) {

		if (bindingResult.hasErrors()) {
			response = bindingResultErrorDetails(bindingResult);
			response.setStatusCode(response.getStatusCode());
			response.setStatusMessage(response.getStatusMessage());
			return response;
		}
		return fareManagementService.fareUpdate(fareUpdate, response);
	}
	
	@PostMapping(value = "bulkUpload")
	public FareSearchResponse processBulkUpload(@RequestBody List<BulkUploadRequest> request) {
		return fareManagementService.processBulkUpload(request);
	}
	
}
