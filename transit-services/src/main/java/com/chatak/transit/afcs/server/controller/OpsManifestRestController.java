package com.chatak.transit.afcs.server.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatak.transit.afcs.server.enums.CustomErrorCodes;
import com.chatak.transit.afcs.server.model.OrderChecks;
import com.chatak.transit.afcs.server.pojo.Response;
import com.chatak.transit.afcs.server.pojo.web.OpsManifestRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.OpsManifestRegistrationResponse;
import com.chatak.transit.afcs.server.pojo.web.OpsManifestSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.OpsManifestSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.OpsManifestStatusChangeRequest;
import com.chatak.transit.afcs.server.pojo.web.OpsManifestUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.service.OpsManifestManagementService;

@RestController
@RequestMapping(value = "/online/")
public class OpsManifestRestController extends ServiceHelper {

	@Autowired
	private OpsManifestManagementService opsManifestManagementService;

	@PostMapping(value = "opsManifestRegistration")
	public OpsManifestRegistrationResponse opsManifestRegistration(@RequestBody OpsManifestRegistrationRequest opsManifestRegistrationRequest,
			BindingResult bindingResult, HttpServletResponse httpResponse,
			OpsManifestRegistrationResponse opsManifestRegistrationResponse) {
		return opsManifestManagementService.opsManifestRegistration(opsManifestRegistrationRequest, httpResponse,
				opsManifestRegistrationResponse);
	}

	@PostMapping(value = "searchOpsManifest")
	public OpsManifestSearchResponse searchOpsManifest(@RequestBody OpsManifestSearchRequest request) {
		return opsManifestManagementService.searchOpsManifest(request);
	}

	@PostMapping(value = "updateOpsManifestStatus")
	public WebResponse updateOpsManifestStatus(@Valid @RequestBody OpsManifestStatusChangeRequest request,
			BindingResult bindingResult, WebResponse response, HttpServletResponse httpResponse) throws IOException {
		if (bindingResult.hasErrors()) {
			return bindingResultErrorDetails(bindingResult);
		}
		return opsManifestManagementService.updateOpsManifestStatus(request, response, httpResponse);
	}

	@PostMapping(value = "updateOpsManifestProfile")
	public WebResponse updateOpsManifestProfile(
			@RequestBody @Validated(value = OrderChecks.class) OpsManifestUpdateRequest request,
			BindingResult bindingResult, HttpServletResponse httpResponse, WebResponse response) {
		if (bindingResult.hasErrors()) {
			return bindingResultErrorDetails(bindingResult);
		}
		return opsManifestManagementService.updateOpsManifestProfile(request, response, httpResponse);

	}
	
	@PostMapping(value = "deviceOnboardIdExistCheck", consumes = MediaType.APPLICATION_JSON_VALUE,
		      produces = MediaType.APPLICATION_JSON_VALUE)
	public Response deviceOnboardIdExistCheck(@RequestBody OpsManifestRegistrationRequest opsManifestRegistrationRequest) {
		boolean isDeviceNoExists = opsManifestManagementService.deviceOnboardIdExistCheck(opsManifestRegistrationRequest);
		Response response = new Response();
		if(isDeviceNoExists) {
			response.setResult(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		} else {
			response.setResult(CustomErrorCodes.FAILURE.getCustomErrorMsg());
		}
		return response;
	}
}
