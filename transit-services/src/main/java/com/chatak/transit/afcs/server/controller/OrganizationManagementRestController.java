package com.chatak.transit.afcs.server.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatak.transit.afcs.server.dao.model.OrganizationMaster;
import com.chatak.transit.afcs.server.model.OrderChecks;
import com.chatak.transit.afcs.server.pojo.web.OrganizationRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.OrganizationRegistrationResponse;
import com.chatak.transit.afcs.server.pojo.web.OrganizationResponse;
import com.chatak.transit.afcs.server.pojo.web.OrganizationSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.OrganizationSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.OrganizationStatusUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.OrganizationUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.service.OrganizationManagementService;

@RestController
@RequestMapping(value = "/online/")
public class OrganizationManagementRestController extends ServiceHelper {

	Logger logger = LoggerFactory.getLogger(OrganizationManagementRestController.class);

	@Autowired
	OrganizationManagementService organizationManagementService;

	@PostMapping(value = "organizationRegistration")
	public OrganizationRegistrationResponse createOrganization(
			@Valid @RequestBody OrganizationRegistrationRequest request, BindingResult bindingResult,
			HttpServletResponse httpResponse, OrganizationRegistrationResponse response) throws IOException {
		if (bindingResult.hasErrors()) {
			WebResponse webResponse = bindingResultErrorDetails(bindingResult);
			response.setStatusCode(webResponse.getStatusCode());
			response.setStatusMessage(webResponse.getStatusMessage());
			return response;
		}
		return organizationManagementService.createOrganization(request, httpResponse, response);
	}

	@PostMapping(value = "updateOrganizationMaster")
	public WebResponse updateOrganization(
			@RequestBody @Validated(value = OrderChecks.class) OrganizationUpdateRequest request,
			BindingResult bindingResult, HttpServletResponse httpResponse, WebResponse response) throws IOException {
		if (bindingResult.hasErrors()) {
			return bindingResultErrorDetails(bindingResult);
		}
		return organizationManagementService.updateOrganization(request, response, httpResponse);

	}

	@PostMapping(value = "updateOrganizationStatus")
	public WebResponse updateOrganizationStatus(@Valid @RequestBody OrganizationStatusUpdateRequest request,
			BindingResult bindingResult, WebResponse response, HttpServletResponse httpResponse) {
		if (bindingResult.hasErrors()) {
			return bindingResultErrorDetails(bindingResult);
		}
		return organizationManagementService.updateOrganizationStatus(request, response);
	}

	@PostMapping(value = "searchOrganization")
	public OrganizationSearchResponse searchOrganization(@RequestBody OrganizationSearchRequest request) {
		return organizationManagementService.searchOrganization(request);
	}

	@PostMapping(value = "getOrganizationIdByOrganizationName")
	public OrganizationResponse getOrganizationIdByOrganizationName(@RequestBody OrganizationSearchRequest request,
			OrganizationResponse response) {
		OrganizationResponse organizationResponseRest = new OrganizationResponse();
		OrganizationMaster organizationMaster = organizationManagementService.getOrganizationIdByOrganizationName(request);
		organizationResponseRest.setOrgId(organizationMaster.getOrgId());
		return organizationResponseRest;

	}
	
	@PostMapping(value = "getOrganizationList")
	public OrganizationSearchResponse getOrganizationList(@RequestBody OrganizationSearchRequest request) {
		return organizationManagementService.getOrganizationList(request);
	}
	
	@PostMapping(value = "getOrganizationListWithStatusNotTerminated")
	public OrganizationSearchResponse getOrganizationListWithStatusNotTerminated(@RequestBody OrganizationSearchRequest request) {
		return organizationManagementService.getOrganizationListWithStatusNotTerminated(request);
	}
	
	@PostMapping(value = "getOrganizationListByOrgId")
	public OrganizationSearchResponse getOrganizationListByOrgId(@RequestBody OrganizationSearchRequest request) {
		return organizationManagementService.getOrganizationListByOrgId(request);
	}
	
}
