package com.chatak.transit.afcs.server.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatak.transit.afcs.server.pojo.web.FeeRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.FeeRegistrationResponse;
import com.chatak.transit.afcs.server.pojo.web.FeeSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.FeeSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.FeeUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.OrganisationNameList;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.service.FeeManagementService;

@RestController
@RequestMapping(value = "/online/")
public class FeeManagementRestController {

	@Autowired
	private FeeManagementService feeManagementService;

	@PostMapping(value = "feeRegistration")
	public FeeRegistrationResponse feeRegistration(@RequestBody FeeRegistrationRequest feeRegistrationRequest,
			BindingResult bindingResult, HttpServletResponse httpResponse,
			FeeRegistrationResponse feeRegistrationResponse) {
		return feeManagementService.feeRegistration(feeRegistrationRequest, httpResponse, feeRegistrationResponse);
	}

	@PostMapping(value = "searchFee")
	public FeeSearchResponse searchFee(@RequestBody FeeSearchRequest request) {
		return feeManagementService.searchFee(request);
	}
	
	@PostMapping(value = "updateFeeStatus")
	public WebResponse updateFeeStatus(@RequestBody FeeRegistrationRequest request, WebResponse response) {
		return feeManagementService.updateFeeStatus(request, response);
	}
	
	@PostMapping(value = "updateFeeProfile")
	public WebResponse updateFeeProfile(@RequestBody FeeUpdateRequest request, WebResponse response) {
		return feeManagementService.updateFeeProfile(request, response);
	}
	
	@PostMapping(value="getOrganisationList")
	public OrganisationNameList getOrganisationList() {
		OrganisationNameList organisationNameList = new OrganisationNameList();
		organisationNameList.setListOfOrganisationName(feeManagementService.getOrganisationList());
		return organisationNameList;
	}

}