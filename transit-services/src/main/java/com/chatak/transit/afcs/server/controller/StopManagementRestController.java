package com.chatak.transit.afcs.server.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatak.transit.afcs.server.pojo.web.StageNameList;
import com.chatak.transit.afcs.server.pojo.web.StopNameList;
import com.chatak.transit.afcs.server.pojo.web.StopRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.StopRegistrationResponse;
import com.chatak.transit.afcs.server.pojo.web.StopSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.StopSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.StopUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.service.StopManagementService;

@RestController
@RequestMapping("/online/")
public class StopManagementRestController extends ServiceHelper {

	@Autowired
	StopManagementService stopManagementService;

	@PostMapping(value = "stopRegistration")
	public StopRegistrationResponse stopRegistration(
			@Valid @RequestBody StopRegistrationRequest stopRegistrationRequest, BindingResult bindingResult,
			HttpServletResponse httpResponse, StopRegistrationResponse response) throws IOException {
		if (bindingResult.hasErrors()) {
			WebResponse webResponse = bindingResultErrorDetails(bindingResult);
			response.setStatusCode(webResponse.getStatusCode());
			response.setStatusMessage(webResponse.getStatusMessage());
			return response;
		}
		return stopManagementService.stopRegistration(stopRegistrationRequest, httpResponse, response);
	}

	@PostMapping(value = "searchStop")
	public StopSearchResponse searchStop(@RequestBody StopSearchRequest request) {
		return stopManagementService.searchStop(request);
	}

	@PostMapping(value = "updateStop")
	public WebResponse updateStop(@RequestBody StopUpdateRequest request, WebResponse response) {
		return stopManagementService.updateStop(request, response);
	}

	@PostMapping(value = "updateStopStatus")
	public WebResponse updateStopStatus(@RequestBody StopUpdateRequest request, WebResponse response) {
		return stopManagementService.updateStopStatus(request, response);
	}
	
	@PostMapping(value = "getStageName")
	public StageNameList getAllStageName() throws InstantiationException, IllegalAccessException {
		StageNameList stageNameList = new StageNameList();
		stageNameList.setListOfStageNames(stopManagementService.getStageNameList());
		return stageNameList;
	}
	
	@PostMapping(value = "validateStopCode")
	public WebResponse validateStopCode(@RequestBody String stopcode) {
		return stopManagementService.validateStopCode(stopcode);
	}
	
	@PostMapping(value = "getStopName")
	public StopNameList getStopName() {
		StopNameList stopNameList = new StopNameList();
		stopNameList.setListOfStopNames(stopManagementService.getStopNameList());
		return stopNameList;
	}
	
	@PostMapping(value = "getStageByRouteId")
	public StopSearchResponse getStageByRouteId(@RequestBody StopSearchRequest searchRequest) {
		return stopManagementService.getStageByRouteId(searchRequest);
	}
}
