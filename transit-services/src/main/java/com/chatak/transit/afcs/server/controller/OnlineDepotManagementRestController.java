/**
 * @author Girmiti Software
 *
 */
package com.chatak.transit.afcs.server.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatak.transit.afcs.server.pojo.web.DepotListViewRequest;
import com.chatak.transit.afcs.server.pojo.web.DepotListViewResponse;
import com.chatak.transit.afcs.server.pojo.web.DepotProfileUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.DepotRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.DepotRegistrationResponse;
import com.chatak.transit.afcs.server.pojo.web.DepotSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.DepotSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.DepotStatusCheckRequest;
import com.chatak.transit.afcs.server.pojo.web.DepotStatusUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.service.DepotManagementService;

@RestController
@RequestMapping(value = "/online/")
public class OnlineDepotManagementRestController extends ServiceHelper {

	Logger logger = LoggerFactory.getLogger(OnlineDepotManagementRestController.class);

	@Autowired
	private DepotManagementService depotManagementService;

	@PostMapping(value = "depotRegistration")
	public DepotRegistrationResponse depotRegistration(
			@RequestBody DepotRegistrationRequest request,
			BindingResult bindingResult, HttpServletResponse httpResponse, DepotRegistrationResponse response)
			throws IOException {
		if (bindingResult.hasErrors()) {
			WebResponse webResponse = bindingResultErrorDetails(bindingResult);
			response.setStatusCode(webResponse.getStatusCode());
			response.setStatusMessage(webResponse.getStatusMessage());
			return response;
		}
		return depotManagementService.saveDepotRegistrationrequest(request, httpResponse, response);

	}

	@PostMapping(value = "depotListView")
	public DepotListViewResponse depotListView(@Valid @RequestBody DepotListViewRequest request,
			BindingResult bindingResult, DepotListViewResponse response, HttpServletResponse httpServletResponse)
			throws IOException {
		if (bindingResult.hasErrors()) {
			WebResponse webResponse = bindingResultErrorDetails(bindingResult);
			response.setStatusCode(webResponse.getStatusCode());
			response.setStatusMessage(webResponse.getStatusMessage());
			return response;
		}
		return depotManagementService.getDepotListViewRequest(request, response, httpServletResponse, bindingResult);

	}

	@PostMapping(value = "updateDepotProfile")
	public WebResponse depotProfileUpdate(@Valid @RequestBody DepotProfileUpdateRequest request,
			BindingResult bindingResult, WebResponse response, HttpServletResponse httpResponse) throws IOException {

		if (bindingResult.hasErrors()) {
			return bindingResultErrorDetails(bindingResult);
		}
		return depotManagementService.updateDepotProfile(request, response, httpResponse);
	}

	@PostMapping(value = "updateDepotStatus")
	public WebResponse depotStatusUpdate(@Valid @RequestBody DepotStatusUpdateRequest request,
			BindingResult bindingResult, WebResponse response, HttpServletResponse httpResponse) throws IOException {

		if (bindingResult.hasErrors()) {
			return bindingResultErrorDetails(bindingResult);
		}

		return depotManagementService.updateDepotStatus(request, response, httpResponse);
	}

	@PostMapping(value = "checkDepotStatus")
	public WebResponse checkDepotStatus(@Valid @RequestBody DepotStatusCheckRequest request,
			BindingResult bindingResult, WebResponse response, HttpServletResponse httpResponse) throws IOException {
		if (bindingResult.hasErrors()) {
			return bindingResultErrorDetails(bindingResult);
		}
		return depotManagementService.checkDepotStatus(request, response, httpResponse);

	}

	@PostMapping(value = "searchDepot")
	public DepotSearchResponse searchDepot(@RequestBody DepotSearchRequest request) {
		return depotManagementService.searchDepot(request);
	}

}
