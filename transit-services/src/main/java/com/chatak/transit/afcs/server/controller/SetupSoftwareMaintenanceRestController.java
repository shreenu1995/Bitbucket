package com.chatak.transit.afcs.server.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatak.transit.afcs.server.pojo.web.SetupSoftwareListDataResponse;
import com.chatak.transit.afcs.server.pojo.web.SetupSoftwareRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.SetupSoftwareRegistrationResponse;
import com.chatak.transit.afcs.server.pojo.web.SetupSoftwareSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.SetupSoftwareSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.SetupSoftwareUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.SetupSoftwareUpdateResponse;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.service.SetupSoftwareMaintenanceService;

@RestController
@RequestMapping(value = "/setupsoftwaremaintenance/")
public class SetupSoftwareMaintenanceRestController {

	@Autowired
	SetupSoftwareMaintenanceService setupSoftwareMaintenanceService;

	@PostMapping(value = "setupSoftwareMaintenanceRegistration")
	public SetupSoftwareRegistrationResponse softwareVersionRegistration(
			@RequestBody SetupSoftwareRegistrationRequest request, SetupSoftwareRegistrationResponse response) {

		return setupSoftwareMaintenanceService.setupSoftwareRegistrationMaintenance(request, response);
	}

	@PostMapping(value = "setupSoftwareMaintenanceSearch")
	public SetupSoftwareSearchResponse searchCard(@RequestBody SetupSoftwareSearchRequest request) {
		return setupSoftwareMaintenanceService.setupSoftwareSearchResponse(request);
	}

	@PostMapping(value = "updateSetupSoftwareMaintenance")
	public WebResponse userProfileUpdate(@RequestBody SetupSoftwareUpdateRequest request,
			HttpServletResponse httpResponse, WebResponse response) {

		return setupSoftwareMaintenanceService.updateSoftwareMaintenance(request, httpResponse, response);
	}

	@PostMapping(value = "updateSetupSoftwareStatus")
	public SetupSoftwareUpdateResponse setupSoftwareUpdateResponse(
			@RequestBody SetupSoftwareRegistrationRequest request, HttpServletResponse httpResponse) {

		return setupSoftwareMaintenanceService.updateSoftwareStatus(request, httpResponse);
	}

	@PostMapping(value = "getSoftwareData")
	public WebResponse getSoftwareData(@RequestBody SetupSoftwareSearchRequest request,
			HttpServletResponse httpResponse, WebResponse response) {
		return setupSoftwareMaintenanceService.getSoftwareData(request);
	}

	@PostMapping("getListInherit")
	public SetupSoftwareListDataResponse getInherit(@RequestBody SetupSoftwareSearchRequest ptoName) {

		return setupSoftwareMaintenanceService.getInheritList(ptoName.getPtoId());
	}
}
