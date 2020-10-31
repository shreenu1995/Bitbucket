package com.chatak.transit.afcs.server.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatak.transit.afcs.server.model.OrderChecks;
import com.chatak.transit.afcs.server.pojo.web.OperatorRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.OperatorResponse;
import com.chatak.transit.afcs.server.pojo.web.OperatorSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.OperatorStatusChangeRequest;
import com.chatak.transit.afcs.server.pojo.web.OperatorUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.service.OperatorManagementService;

@RestController
@RequestMapping(value = "/operator/")
public class OperatorManagementRestController extends ServiceHelper {

	@Autowired
	OperatorManagementService operatorManagementService;

	@PostMapping(value = "operatorRegistration")
	public WebResponse operatorRegistration(@Valid @RequestBody OperatorRegistrationRequest request,
			BindingResult bindingResult, HttpServletResponse httpResponse, WebResponse response) {
		if (bindingResult.hasErrors()) {
			WebResponse webResponse = bindingResultErrorDetails(bindingResult);
			response.setStatusCode(webResponse.getStatusCode());
			response.setStatusMessage(webResponse.getStatusMessage());
			return response;
		}
		return operatorManagementService.operatorRegister(request);
	}

	@PostMapping(value = "searchOperator")
	public OperatorResponse searchOperator(@RequestBody OperatorSearchRequest operatorSearchRequest) {
		return operatorManagementService.searchOperators(operatorSearchRequest);
	}

	@PostMapping(value = "updateOperatorStatus")
	public WebResponse updateOperatorStatus(@Valid @RequestBody OperatorStatusChangeRequest request,
			BindingResult bindingResult, WebResponse response, HttpServletResponse httpResponse) {
		if (bindingResult.hasErrors()) {
			return bindingResultErrorDetails(bindingResult);
		}
		return operatorManagementService.updateOperatorStatus(request, response);
	}

	@PostMapping(value = "updateOperatorProfile")
	public WebResponse updateOperatorProfile(
			@RequestBody @Validated(value = OrderChecks.class) OperatorUpdateRequest request,
			BindingResult bindingResult, HttpServletResponse httpResponse, WebResponse response) {
		if (bindingResult.hasErrors()) {
			return bindingResultErrorDetails(bindingResult);
		}
		return operatorManagementService.updateOperatorProfile(request, response, httpResponse);

	}
}
