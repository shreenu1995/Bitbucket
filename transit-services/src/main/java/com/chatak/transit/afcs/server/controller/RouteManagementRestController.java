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

import com.chatak.transit.afcs.server.pojo.web.BulkUploadRequest;
import com.chatak.transit.afcs.server.pojo.web.RouteRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.RouteRegistrationResponse;
import com.chatak.transit.afcs.server.pojo.web.RouteSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.RouteSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.RouteStatusUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.service.RouteManagementService;

@RestController
@RequestMapping(value = "/online/route/")
public class RouteManagementRestController extends ServiceHelper {

	@Autowired
	RouteManagementService routeManagementService;

	@PostMapping(value = "routeRegistration")
	public RouteRegistrationResponse routeRegistration(@Valid @RequestBody RouteRegistrationRequest request,
			BindingResult bindingResult, RouteRegistrationResponse response, HttpServletResponse httpResponse)
			throws IOException {
		if (bindingResult.hasErrors()) {
			WebResponse webResponse = bindingResultErrorDetails(bindingResult);
			response.setStatusCode(webResponse.getStatusCode());
			response.setStatusMessage(webResponse.getStatusMessage());
			return response;
		}
		return routeManagementService.routeRegistartion(request, response, httpResponse);
	}

	@PostMapping(value = "searchRouteRegistration")
	public RouteSearchResponse searchRoute(@RequestBody RouteSearchRequest request) {
		return routeManagementService.searchRoute(request);
	}

	@PostMapping(value = "viewRouteRegistration")
	public RouteSearchResponse viewRoute(@RequestBody RouteSearchRequest request) {
		return routeManagementService.viewRoute(request);
	}

	@PostMapping(value="getStageByRouteId")
	public RouteSearchResponse viewStagesOnRole(@RequestBody RouteSearchRequest request) {
		return routeManagementService.getViewStagesOnRole(request);
	}

	@PostMapping(value = "updateRouteProfile")
	public WebResponse updateRouteProfile(@RequestBody RouteRegistrationRequest request, WebResponse response,
			HttpServletResponse httpServletResponse) {

		return routeManagementService.updateRouteProfile(request, response, httpServletResponse);
	}

	@PostMapping(value = "updateRouteStatus")
	public WebResponse updateRouteStatus(@RequestBody RouteStatusUpdateRequest request, WebResponse response,
			BindingResult bindingResult) {
		return routeManagementService.updateRouteStatus(request, response);
	}
	
	@PostMapping(value = "deleteStage")
	public WebResponse deleteStage(@RequestBody RouteRegistrationRequest request, WebResponse response,
			BindingResult bindingResult) {
		
		return routeManagementService.deleteStage(request, response);
	}
	
	@PostMapping(value = "validateRouteCode")
	public WebResponse validateRouteCode(@RequestBody BulkUploadRequest bulkUploadRequest) {
		return routeManagementService.validateRouteCode(bulkUploadRequest);
	}

}
