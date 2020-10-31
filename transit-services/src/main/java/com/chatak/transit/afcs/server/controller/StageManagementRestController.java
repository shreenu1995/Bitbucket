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

import com.chatak.transit.afcs.server.pojo.web.RouteNameList;
import com.chatak.transit.afcs.server.pojo.web.RouteSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.RouteSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.StageRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.StageRegistrationResponse;
import com.chatak.transit.afcs.server.pojo.web.StageResponse;
import com.chatak.transit.afcs.server.pojo.web.StageSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.StageStatusUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.service.StageManagementService;

@RestController
@RequestMapping(value = "/stage/")
public class StageManagementRestController extends ServiceHelper {
	
	@Autowired
	StageManagementService stageManagementService;

	@PostMapping(value = "stageRegistration")
	public StageRegistrationResponse stageRegistration(@RequestBody StageRegistrationRequest request,
			BindingResult bindingResult, HttpServletResponse httpResponse, StageRegistrationResponse response) throws IOException {
		if (bindingResult.hasErrors()) {
			WebResponse webResponse = bindingResultErrorDetails(bindingResult);
			response.setStatusCode(webResponse.getStatusCode());
			response.setStatusMessage(webResponse.getStatusMessage());
			return response;
		}
		return stageManagementService.stageRegistration(request, httpResponse, response);
	}
	
	@PostMapping(value = "searchStage")
   public StageResponse stageSearch(@RequestBody StageSearchRequest request) {
		return stageManagementService.searchStage(request);
   }
   
   @PostMapping(value = "getRouteName")
	public RouteNameList getAllRouteName() {
	   RouteNameList routeNameList = new RouteNameList();
	   routeNameList.setListOfRouteName(stageManagementService.getRouteName());
		return routeNameList;
	}
   
   @PostMapping(value = "stageProfileUpdate")
   public WebResponse stageProfileUpdate(@RequestBody StageRegistrationRequest request, WebResponse response, HttpServletResponse httpServletResponse) {
	   return stageManagementService.stageProfileUpdate(request, response, httpServletResponse);
	   
   }
   
	@PostMapping(value = "stageStatusUpdate")
	public WebResponse stageStatusUpdate(@RequestBody @Valid StageStatusUpdateRequest request,
			BindingResult bindingResult, WebResponse response, HttpServletResponse httpResponse) {
		if (bindingResult.hasErrors()) {
			response = bindingResultErrorDetails(bindingResult);
			response.setStatusCode(response.getStatusCode());
			response.setStatusMessage(response.getStatusMessage());
			return response;
		}
		return stageManagementService.stageStatusUpdate(request, response);
	}
   
   @PostMapping(value = "viewStageRegistration")
   public StageResponse viewStage(@RequestBody StageSearchRequest request) {
	   return stageManagementService.viewStage(request);
   }
   
   @PostMapping(value = "getStopByStageId")
   public StageResponse viewStop(@RequestBody StageSearchRequest request) {
	   return stageManagementService.getViewStop(request);
   }
   
   @PostMapping(value = "deleteStop")
   public WebResponse deleteStop(@RequestBody StageRegistrationRequest request, WebResponse response, 
		   BindingResult bindingResult) {
	   return stageManagementService.deleteStop(request, response);
	   
   }
   
   @PostMapping(value = "getRouteByPto")
	public RouteSearchResponse getRouteByPto(@RequestBody RouteSearchRequest request) {
	   return stageManagementService.getRouteByPto(request);
	}
}
